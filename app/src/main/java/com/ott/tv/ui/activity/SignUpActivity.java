package com.ott.tv.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;

import com.ott.tv.Config;
import com.ott.tv.Constants;
import com.ott.tv.R;
import com.ott.tv.database.DatabaseHelper;
import com.ott.tv.model.ActiveStatus;
import com.ott.tv.model.User;
import com.ott.tv.network.RetrofitClient;
import com.ott.tv.network.api.SignUpApi;
import com.ott.tv.network.api.SubscriptionApi;
import com.ott.tv.utils.ToastMsg;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SignUpActivity extends Activity {
    private EditText nameEt, emailEt, passwordEt, phoneNumberEt;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        nameEt = findViewById(R.id.name_edit_text);
        emailEt = findViewById(R.id.email_edit_text);
        passwordEt = findViewById(R.id.password_edit_text);
        phoneNumberEt = findViewById(R.id.phoneNumber_edit_text);
        progressBar = findViewById(R.id.progress_sign_up);
    }

    public void signUpBtn(View view) {
        if (!isValidEmailAddress(emailEt.getText().toString())) {
            new ToastMsg(SignUpActivity.this).toastIconError("please enter valid email");
        } else if (passwordEt.getText().toString().equals("")) {
            new ToastMsg(SignUpActivity.this).toastIconError("please enter password");
        } else if (nameEt.getText().toString().equals("")) {
            new ToastMsg(SignUpActivity.this).toastIconError("please enter name");
        } else if (phoneNumberEt.getText().toString().equals("")) {
            new ToastMsg(SignUpActivity.this).toastIconError("please enter phonenumber");
        } else {
            String email = emailEt.getText().toString();
            String pass = passwordEt.getText().toString();
            String name = nameEt.getText().toString();
            String phone = phoneNumberEt.getText().toString();
            signUp(email, pass, name, phone);
        }
    }

    private void signUp(String email, String pass, String name, String phone) {
        progressBar.setVisibility(View.VISIBLE);
        Retrofit retrofit = RetrofitClient.getRetrofitInstance();

        Log.d("kranti", "" + this.getClass().getSimpleName());
        SignUpApi signUpApi = retrofit.create(SignUpApi.class);
        Call<User> call = signUpApi.signUp(Config.API_KEY, email, pass, name, phone, "+91");
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
                if (response.code() == 200 && response.body() != null) {
                    if (response.body().getStatus().equals("success")) {
                        new ToastMsg(SignUpActivity.this).toastIconSuccess("Successfully registered");
                        User user = response.body();
                        DatabaseHelper db = new DatabaseHelper(SignUpActivity.this);
                        if (db.getUserDataCount() > 1) {
                            db.deleteUserData();
                        } else {
                            if (db.getUserDataCount() == 0) {
                                db.insertUserData(user);
                            } else {
                                db.updateUserData(user, 1);
                            }
                        }
                        SharedPreferences.Editor preferences = getSharedPreferences(Constants.USER_LOGIN_STATUS, MODE_PRIVATE).edit();
                        preferences.putBoolean(Constants.USER_LOGIN_STATUS, true);
                        preferences.apply();

                        //save user login time, expire time
                        updateSubscriptionStatus(user.getUserId());
                    } else if (response.body().getStatus().equals("error")) {
                        new ToastMsg(SignUpActivity.this).toastIconError(response.body().getData());
                    }
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(@NonNull Call<User> call, @NonNull Throwable t) {
                progressBar.setVisibility(View.GONE);
                new ToastMsg(SignUpActivity.this).toastIconError(getString(R.string.error_toast));
            }
        });
    }

    public boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }

    public void updateSubscriptionStatus(String userId) {
        //get saved user id
        Retrofit retrofit = RetrofitClient.getRetrofitInstance();

        Log.d("kranti", "" + this.getClass().getSimpleName());
        SubscriptionApi subscriptionApi = retrofit.create(SubscriptionApi.class);
        Call<ActiveStatus> call = subscriptionApi.getActiveStatus(Config.API_KEY, userId);
        call.enqueue(new Callback<ActiveStatus>() {
            @Override
            public void onResponse(@NonNull Call<ActiveStatus> call, @NonNull Response<ActiveStatus> response) {
                progressBar.setVisibility(View.GONE);
                if (response.code() == 200) {
                    if (response.body() != null) {
                        ActiveStatus activeStatus = response.body();
                        DatabaseHelper db = new DatabaseHelper(SignUpActivity.this);

                        if (db.getActiveStatusCount() > 1) {
                            db.deleteAllActiveStatusData();
                        } else {

                            if (db.getActiveStatusCount() == 0) {
                                db.insertActiveStatusData(activeStatus);
                            } else {
                                db.updateActiveStatus(activeStatus, 1);
                            }
                        }
                        Intent intent = new Intent(SignUpActivity.this, NewMainActivity.class);
                        startActivity(intent);
                        finishAffinity();
                        overridePendingTransition(R.anim.enter, R.anim.exit);
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<ActiveStatus> call, @NonNull Throwable t) {
                t.printStackTrace();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.pop_enter, R.anim.pop_exit);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Log.e("LoginActivity", "***** keyCode =" + keyCode + "event :" + event);
        /*   return super.onKeyDown(keyCode, event);*/

        // When using DPad, show all the OSD so that focus can move freely
        // from/to ActionBar to/from PlayerController
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                onBackPressed();
                return true;
            case KeyEvent.KEYCODE_DPAD_CENTER:
                return false;
            case KeyEvent.KEYCODE_DPAD_LEFT:
                return false;
            case KeyEvent.KEYCODE_DPAD_RIGHT:
                return false;
            case KeyEvent.KEYCODE_DPAD_UP:
                Log.e("LoginActivity", "movieIndex : ");
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
        }


        return super.onKeyDown(keyCode, event);
    }
}
