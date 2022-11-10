package com.ott.tv.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.ott.tv.Config;
import com.ott.tv.Constants;
import com.ott.tv.R;
import com.ott.tv.database.DatabaseHelper;
import com.ott.tv.model.ActiveStatus;
import com.ott.tv.model.User;
import com.ott.tv.network.RetrofitClient;
import com.ott.tv.network.api.FirebaseAuthApi;
import com.ott.tv.network.api.SubscriptionApi;
import com.ott.tv.utils.ToastMsg;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginChooserActivity extends Activity {
    private static final String TAG = "ActivityLoginChooser";
    private static final int RC_PHONE_SIGN_IN = 124;
    private FirebaseAuth firebaseAuth;
    private static int RC_GOOGLE_SIGN_IN = 123;
    private ProgressBar progressBar;
    private Button googleSignInButton, phoneSignInButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_chooser_with_qrcode);

        progressBar = findViewById(R.id.progress_bar);
        googleSignInButton = findViewById(R.id.google_signIn_button);
        phoneSignInButton = findViewById(R.id.phone_signIn_button);

        firebaseAuth = FirebaseAuth.getInstance();
        DatabaseHelper db = new DatabaseHelper(LoginChooserActivity.this);
        User user = db.getUserData();
        if (user.getUserId() != null) {
            updateSubscriptionStatus(user.getUserId());
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!Config.ENABLE_GOOGLE_LOGIN) {
            googleSignInButton.setVisibility(View.GONE);
        }
        if (!Config.ENABLE_PHONE_LOGIN) {
            phoneSignInButton.setVisibility(View.GONE);

        }

        googleSignInButton.setOnClickListener(v -> googleSignIn());

        phoneSignInButton.setOnClickListener(v -> phoneSignIn());
    }

    /*@Override
    public String customView() {
        return Config.PURCHASE_CODE;
    }

    @Override
    public String packageName() {
        return BuildConfig.APPLICATION_ID;
    }*/

    public void signUpBtn(View view) {
        Intent intent = new Intent(LoginChooserActivity.this, SignUpActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.enter, R.anim.exit);
    }

    public void emailSignInBtn(View view) {
        Intent intent = new Intent(LoginChooserActivity.this, LoginActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.enter, R.anim.exit);
    }

    public void mobileSignInBtn(View view) {
        Intent intent = new Intent(LoginChooserActivity.this, LoginMobileActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.enter, R.anim.exit);
    }


    private void phoneSignIn() {
        progressBar.setVisibility(View.VISIBLE);
        if (firebaseAuth.getCurrentUser() != null) {
            /*if (!FirebaseAuth.getInstance().getCurrentUser().getUid().isEmpty()) {
                final String phone = FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber();
                //already signed in
                if (phone != null)
                    sendPhoneSignInDataToServer();
            }*/

            sendPhoneSignInDataToServer();

        } else {
            progressBar.setVisibility(View.GONE);
            // Choose authentication providers
            List<AuthUI.IdpConfig> providers = Arrays.asList(
                    new AuthUI.IdpConfig.PhoneBuilder().build());
            // Create and launch sign-in intent
            startActivityForResult(
                    AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .setAvailableProviders(providers)
                            .build(),
                    RC_PHONE_SIGN_IN);
        }
    }

    private void googleSignIn() {
        progressBar.setVisibility(View.VISIBLE);
        if (firebaseAuth.getCurrentUser() != null) {
            if (!Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid().isEmpty()) {
                final String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
                //already signed in
                if (email != null)
                    sendGoogleSignInDataToServer();
            }

        } else {
            progressBar.setVisibility(View.GONE);
            // Choose authentication providers
            List<AuthUI.IdpConfig> providers = Arrays.asList(
                    new AuthUI.IdpConfig.GoogleBuilder().build());
            // Create and launch sign-in intent
            startActivityForResult(
                    AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .setAvailableProviders(providers)
                            .build(),
                    RC_GOOGLE_SIGN_IN);
        }
    }

    private void sendGoogleSignInDataToServer() {
        progressBar.setVisibility(View.VISIBLE);
        String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        String name = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
        Log.e("FireAuth", email + ", " + uid + ", " + name);
        Retrofit retrofit = RetrofitClient.getRetrofitInstance();
        FirebaseAuthApi api = retrofit.create(FirebaseAuthApi.class);
        Call<User> call = api.getGoogleAuthStatus(Config.API_KEY, uid, email, name);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
                if (response.code() == 200) {
                    if (response.body().getStatus().equalsIgnoreCase("success")) {
                        User user = response.body();
                        DatabaseHelper db = new DatabaseHelper(LoginChooserActivity.this);

                        db.deleteUserData();
                        db.insertUserData(user);

                        //save user login time, expire time
                        updateSubscriptionStatus(user.getUserId());
                    }
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(LoginChooserActivity.this, "failed", Toast.LENGTH_SHORT).show();
                Log.e("LoginTV", "response: " + t.getLocalizedMessage());
            }
        });
    }

    private void sendPhoneSignInDataToServer() {
        progressBar.setVisibility(View.VISIBLE);
        String phone = "";
        String uid = "";
        try {
            phone = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getPhoneNumber();
            uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        } catch (NullPointerException e) {
            new ToastMsg(LoginChooserActivity.this).toastIconError(getResources().getString(R.string.something_went_wrong));
            return;
        }
        Retrofit retrofit = RetrofitClient.getRetrofitInstance();

        FirebaseAuthApi api = retrofit.create(FirebaseAuthApi.class);
        Call<User> call = api.getPhoneAuthStatus(Config.API_KEY, uid, phone);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.code() == 200) {
                    if (response.body().getStatus().equalsIgnoreCase("success")) {
                        User user = response.body();
                        DatabaseHelper db = new DatabaseHelper(LoginChooserActivity.this);

                        db.deleteUserData();
                        db.insertUserData(user);

                        //save user login time, expire time
                        updateSubscriptionStatus(user.getUserId());
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<User> call, @NonNull Throwable t) {
                Toast.makeText(LoginChooserActivity.this, "failed", Toast.LENGTH_SHORT).show();
                Log.e("LoginTV", "response: " + t.getLocalizedMessage());
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_GOOGLE_SIGN_IN) {
            final IdpResponse response = IdpResponse.fromResultIntent(data);
            if (resultCode == RESULT_OK) {
                // Successfully signed in
                final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) {
                    sendGoogleSignInDataToServer();
                } else {
                    //empty
                    googleSignIn();
                }
            } else {
                // sign in failed
                if (response == null) {
                    Toast.makeText(this, "Canceled", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (Objects.requireNonNull(response.getError()).getErrorCode() == ErrorCodes.NO_NETWORK) {
                    Toast.makeText(this, "No internet", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (response.getError().getErrorCode() == ErrorCodes.UNKNOWN_ERROR) {
                    Toast.makeText(this, "Error !!", Toast.LENGTH_SHORT).show();
                }
            }
        } else if (requestCode == RC_PHONE_SIGN_IN) {
            final IdpResponse response = IdpResponse.fromResultIntent(data);
            if (resultCode == RESULT_OK) {
                // Successfully signed in
                final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) {
                    sendPhoneSignInDataToServer();
                } else {
                    //empty
                    phoneSignIn();
                }
            } else {
                // sign in failed
                if (response == null) {
                    Toast.makeText(this, "Canceled", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (Objects.requireNonNull(response.getError()).getErrorCode() == ErrorCodes.NO_NETWORK) {
                    Toast.makeText(this, "No internet", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (response.getError().getErrorCode() == ErrorCodes.UNKNOWN_ERROR) {
                    Toast.makeText(this, "Error !!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    public void updateSubscriptionStatus(String userId) {
        progressBar.setVisibility(View.VISIBLE);
        //get saved user id
        Retrofit retrofit = RetrofitClient.getRetrofitInstance();

        Log.d("kranti", "" + this.getClass().getSimpleName());
        SubscriptionApi subscriptionApi = retrofit.create(SubscriptionApi.class);

        Call<ActiveStatus> call = subscriptionApi.getActiveStatus(Config.API_KEY, userId);
        call.enqueue(new Callback<ActiveStatus>() {
            @Override
            public void onResponse(Call<ActiveStatus> call, Response<ActiveStatus> response) {
                if (response.code() == 200) {
                    if (response.body() != null) {
                        ActiveStatus activeStatus = response.body();
                        DatabaseHelper db = new DatabaseHelper(LoginChooserActivity.this);

                        if (db.getActiveStatusCount() > 1) {
                            db.deleteAllActiveStatusData();
                        } else {

                            if (db.getActiveStatusCount() == 0) {
                                db.insertActiveStatusData(activeStatus);
                            } else {
                                db.updateActiveStatus(activeStatus, 1);
                            }
                        }
                        Intent intent = new Intent(LoginChooserActivity.this, LeanbackActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

                        SharedPreferences.Editor sp = getSharedPreferences(Constants.USER_LOGIN_STATUS, MODE_PRIVATE).edit();
                        sp.putBoolean(Constants.USER_LOGIN_STATUS, true);
                        sp.apply();
                        sp.commit();

                        startActivity(intent);
                        finish();
                        progressBar.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onFailure(Call<ActiveStatus> call, Throwable t) {
                t.printStackTrace();
                progressBar.setVisibility(View.GONE);
                Toast.makeText(LoginChooserActivity.this, R.string.something_went_wrong, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Log.e("LoginChooserActivity", "***** keyCode =" + keyCode + "event :" + event);
        /*   return super.onKeyDown(keyCode, event);*/

        // When using DPad, show all the OSD so that focus can move freely
        // from/to ActionBar to/from PlayerController
        switch (keyCode) {

            case KeyEvent.KEYCODE_DPAD_CENTER:
                return false;
            case KeyEvent.KEYCODE_DPAD_LEFT:
                return false;
            case KeyEvent.KEYCODE_DPAD_RIGHT:
                return false;
            case KeyEvent.KEYCODE_DPAD_UP:
                Log.e("LoginChooserActivity", "movieIndex : ");

                return false;

            case KeyEvent.KEYCODE_DPAD_UP_LEFT:
                return false;
            case KeyEvent.KEYCODE_DPAD_UP_RIGHT:
                return false;
            case KeyEvent.KEYCODE_DPAD_DOWN:
                return false;
            case KeyEvent.KEYCODE_DPAD_DOWN_LEFT:
                return false;
            case KeyEvent.KEYCODE_DPAD_DOWN_RIGHT:
                return false;
            case KeyEvent.KEYCODE_BACK:
                Log.d(TAG, "keyCode List" + keyCode);
                onBackPressed();
                return true;
        }


        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
