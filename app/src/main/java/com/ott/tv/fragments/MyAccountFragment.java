package com.ott.tv.fragments;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.ott.tv.Config;
import com.ott.tv.Constants;
import com.ott.tv.model.home_content.HomeContent;
import com.ott.tv.model.home_content.Video;
import com.ott.tv.model.phando.ShowWatchlist;
import com.ott.tv.model.phando.UserProfile;
import com.ott.tv.network.RetrofitClient;
import com.ott.tv.network.api.Dashboard;
import com.ott.tv.network.api.HomeApi;
import com.ott.tv.ui.activity.LoginActivity;
import com.ott.tv.utils.CMHelper;
import com.ott.tv.utils.PreferenceUtils;
import com.ott.tv.R;
import com.ott.tv.database.DatabaseHelper;
import com.ott.tv.model.ActiveStatus;
import com.ott.tv.ui.activity.LoginChooserActivity;
import com.ott.tv.utils.ToastMsg;

import static android.content.Context.MODE_PRIVATE;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyAccountFragment extends Fragment {
    private Button sign_out;
    private TextView user_name,tv_mobileno;
    private TextView user_email;
    private TextView expire_date;
    private TextView active_plan;
    private DatabaseHelper db;
    private UserProfile userProfile;

    public MyAccountFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_account, container, false);

        db = new DatabaseHelper(getContext());
        initViews(view);

        sign_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signOut();
            }
        });
        return view;
    }

    private void initViews(View view) {
        sign_out = view.findViewById(R.id.sign_out_button);
        user_name = view.findViewById(R.id.userNameTv);
        tv_mobileno = view.findViewById(R.id.tv_mobileno);
        user_email = view.findViewById(R.id.userEmailTv);
    //    active_plan = view.findViewById(R.id.activePlanTv);
        expire_date = view.findViewById(R.id.expireDateTv);
        if (getContext() != null) {
            if (PreferenceUtils.isLoggedIn(getContext())) {
                user_name.setText(new DatabaseHelper(getContext()).getUserData().getName());
                user_email.setText(new DatabaseHelper(getContext()).getUserData().getEmail());
            }
        }

        ActiveStatus activeStatus = db.getActiveStatusData();

      //  active_plan.setText(activeStatus.getPackageTitle());
        expire_date.setText(activeStatus.getExpireDate());
        getUserProfileDataFromServer();
    }


    private void signOut() {
        if (getContext() != null && getActivity() != null) {
            DatabaseHelper databaseHelper = new DatabaseHelper(getContext());
        /*    String userId = databaseHelper.getUserData().getUserId();
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if (user != null) {
                FirebaseAuth.getInstance().signOut();
            }*/

            if (PreferenceUtils.getInstance().getAccessTokenPref(getContext()) != "") {
                SharedPreferences.Editor editor = getContext().getSharedPreferences(Constants.USER_LOGIN_STATUS, MODE_PRIVATE).edit();
                editor.putBoolean(Constants.USER_LOGIN_STATUS, false);
                editor.apply();
                databaseHelper.deleteUserData();
                PreferenceUtils.clearSubscriptionSavedData(getContext());
                PreferenceUtils.getInstance().setAccessTokenNPref(getContext(), "");
                startActivity(new Intent(getContext(), LoginChooserActivity.class));
                getActivity().finish();
            }
        }
    }


    private void getUserProfileDataFromServer() {
        if (getActivity() != null) {
            Constants.IS_FROM_HOME = false;
            String userId = new DatabaseHelper(requireContext()).getUserData().getUserId();
            Retrofit retrofit = RetrofitClient.getRetrofitInstance();
            Dashboard api = retrofit.create(Dashboard.class);


            String accessToken = "Bearer " + PreferenceUtils.getInstance().getAccessTokenPref(getContext());


            Call<UserProfile> call = api.getUserProfileAPI(accessToken);
            call.enqueue(new Callback<UserProfile>() {
                @Override
                public void onResponse(@NotNull Call<UserProfile> call, @NotNull retrofit2.Response<UserProfile> response) {
                    if (response.isSuccessful()) {
                        if (response.code() == 200 && response.body() != null) {
                            userProfile = response.body();
                            user_name.setText(userProfile.getUser().getName());
                            user_email.setText(userProfile.getUser().getEmail());
                            tv_mobileno.setText(userProfile.getUser().getMobile());

                        } else if (response.errorBody() != null) {
                            Toast.makeText(getContext(), response.errorBody().toString(), Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(getContext(), "Sorry! Something went wrong. Please try again after some time", Toast.LENGTH_SHORT).show();

                            //          CMHelper.setSnackBar(requireView(), "Sorry! Something went wrong. Please try again after some time", 2);
                        }

                    } else {
                        if (response.code() == 401) {
                            //   CMHelper.setSnackBar(this.getCurrentFocus(), String.valueOf("Please Enter OTP"), 2, 10000);
                            signOut();
                            //     Toast.makeText(getContext(), "Please Login Again", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(), "Sorry! Something went wrong. Please try again after some time", Toast.LENGTH_SHORT).show();
                        }


                        Toast.makeText(getContext(), "Sorry! Something went wrong. Please try again after some time", Toast.LENGTH_SHORT).show();

                        //CMHelper.setSnackBar(requireView(), "Sorry! Something went wrong. Please try again after some time", 2);
                    }


                }

                @Override
                public void onFailure(@NonNull Call<UserProfile> call, @NonNull Throwable t) {
                    t.printStackTrace();
                    CMHelper.setSnackBar(requireView(), t.getMessage(), 2);

                }
            });
        }
    }

}
