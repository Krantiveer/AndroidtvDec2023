package com.ott.tv.fragments;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.ott.tv.Config;
import com.ott.tv.Constants;
import com.ott.tv.databinding.FragmentMyAccountBinding;
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
import com.squareup.picasso.Picasso;

import static android.content.Context.MODE_PRIVATE;

import static com.ott.tv.ui.activity.SearchActivity_Phando.progressBar;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;

public class MyAccountFragment extends Fragment {


    private UserProfile userProfile;
    private FragmentMyAccountBinding binding;

    public MyAccountFragment() {

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_my_account, container, false);
        View view = binding.getRoot();

        binding.lytTopCard.setVisibility(View.GONE);
        binding.progressBar.setVisibility(View.VISIBLE);
        binding.signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signOut();
            }
        });
        getUserProfileDataFromServer();
        return view;

    }

    private void signOut() {
        if (getContext() != null && getActivity() != null) {
            DatabaseHelper databaseHelper = new DatabaseHelper(getContext());

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
        binding.lytTopCard.setVisibility(View.GONE);
        binding.progressBar.setVisibility(View.VISIBLE);
        if (getActivity() != null) {
            Constants.IS_FROM_HOME = false;
            Retrofit retrofit = RetrofitClient.getRetrofitInstance();
            Dashboard api = retrofit.create(Dashboard.class);
            String accessToken = "Bearer " + PreferenceUtils.getInstance().getAccessTokenPref(getContext());
            Call<UserProfile> call = api.getUserProfileAPI(accessToken);
            call.enqueue(new Callback<UserProfile>() {
                @Override
                public void onResponse(@NotNull Call<UserProfile> call, @NotNull retrofit2.Response<UserProfile> response) {
                    if (response.isSuccessful()) {
                        binding.progressBar.setVisibility(View.GONE);
                        binding.lytTopCard.setVisibility(View.VISIBLE);
                        if (response.code() == 200 && response.body() != null) {
                            userProfile = response.body();

                            Glide.with(requireActivity())
                                    .load(userProfile.getUser().getImage())
                                    .error(R.drawable.user_image)
                                    .placeholder(R.drawable.user_image)
                                    .into(binding.imguser);

                            binding.userName.setText(userProfile.getUser().getName());
                            if (userProfile.getUser().getEmail() != null) {
                                binding.userEmailTv.setVisibility(View.VISIBLE);
                                binding.userEmailTv.setText(userProfile.getUser().getEmail());

                            }
                            if (userProfile.getIs_subscribed() ==0) {
                                binding.tvnoPlan.setVisibility(View.VISIBLE);
                                binding.userSubId.setVisibility(View.INVISIBLE);
                                binding.lytPlan.setVisibility(View.GONE);
                                binding.lytPlan2.setVisibility(View.GONE);
                                binding.myPackageName.setVisibility(View.GONE);
                                binding.myPackagePrice.setVisibility(View.GONE);
                            }
                            else
                            {
                                binding.userSubId.setVisibility(View.VISIBLE);
                                binding.myPackageName.setVisibility(View.VISIBLE);
                                binding.myPackagePrice.setVisibility(View.VISIBLE);
                                binding.userSubId.setText("Subscriber ID : " +userProfile.getCurrent_subscription().getId());
                                binding.myPackageName.setText(userProfile.getPackage_name());
                                binding.myPackagePrice.setText("Rs. " +userProfile.getCurrent_subscription().getPrice().toString());
                                binding.tvnoPlan.setVisibility(View.GONE);
                                binding.lytPlan.setVisibility(View.VISIBLE);
                                binding.lytPlan2.setVisibility(View.VISIBLE);
                                if(userProfile.getsubscription_end_date() != null){
                                    binding.myPackageDateTime.setText("Expires on " + userProfile.getsubscription_end_date());
                                }
                            }
                            if (userProfile.getUser().getMobile() != null) {
                                binding.tvMobileNumber.setText(userProfile.getUser().getMobile());
                            }
                        } else if (response.errorBody() != null) {
                            Toast.makeText(getContext(), response.errorBody().toString(), Toast.LENGTH_SHORT).show();

                        } else {
                            progressBar.setVisibility(View.GONE);
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
                    binding.progressBar.setVisibility(View.GONE);
                    binding.lytTopCard.setVisibility(View.GONE);

                }
            });
        }
    }

}
