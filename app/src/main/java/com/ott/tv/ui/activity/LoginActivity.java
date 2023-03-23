package com.ott.tv.ui.activity;

import static com.ott.tv.utils.CMHelper.hideKeyboard;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;

import com.ott.tv.Config;
import com.ott.tv.Constants;
import com.ott.tv.R;
import com.ott.tv.database.DatabaseHelper;
import com.ott.tv.model.ActiveStatus;
import com.ott.tv.model.CouponModel;
import com.ott.tv.model.User;
import com.ott.tv.network.RetrofitClient;
import com.ott.tv.network.api.LoginApi;
import com.ott.tv.network.api.SendOTPApi;
import com.ott.tv.network.api.SubscriptionApi;
import com.ott.tv.utils.PreferenceUtils;
import com.ott.tv.utils.ToastMsg;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginActivity extends Activity {
    private EditText etEmail = null, etPass = null,editCouponCode;
    private ProgressBar progressBar;
    private LinearLayout ll_emailLogin,ll_coupon_code;
    private Button bt_skip,bt_coupon;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        etEmail = findViewById(R.id.email_edit_text);
        etPass = findViewById(R.id.password_edit_text);
        progressBar = findViewById(R.id.progress_login);
        ll_emailLogin=findViewById(R.id.ll_emailLogin);
        editCouponCode=findViewById(R.id.editCouponCodeEmail);
        ll_coupon_code=findViewById(R.id.ll_coupon_code);
        bt_skip=findViewById(R.id.bt_skip);
        bt_coupon=findViewById(R.id.bt_coupon);

        bt_skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoMainScreen();
            }
        });
        bt_coupon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoCouponScreen();
            }
        });
// in onCreate method
/*
        etEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                // if edittext has 10chars & this is not called yet, add new line
                String etmail = etEmail.getText().toString();
                if (etmail.contains(".com")) {
                    if(etPass!=null){
                    etPass.requestFocus();
                }}
            }
        });
*/

    }
    private void gotoMainScreen(){


        Intent intent = new Intent(getApplicationContext(), NewMainActivity.class);
        startActivity(intent);
        finishAffinity();
        overridePendingTransition(R.anim.enter, R.anim.exit);


    }
    private void gotoCouponScreen(){

        String couponCode=editCouponCode.getText().toString();
        if(couponCode.isEmpty()||couponCode.trim().isEmpty()){
            new ToastMsg(getApplicationContext()).toastIconError("Please Enter CouponCode" );

        }else {
            getVerifiedCoupon(couponCode);
        }



    }
    private void getVerifiedCoupon(String couponCode) {
        progressBar.setVisibility(View.VISIBLE);
        Retrofit retrofit = RetrofitClient.getRetrofitInstance();

        SendOTPApi api = retrofit.create(SendOTPApi.class);
        Call<CouponModel> call = api.assignCoupon(Config.API_KEY, couponCode);

        call.enqueue(new Callback<CouponModel>() {
            @Override
            public void onResponse(@NonNull Call<CouponModel> call, @NonNull Response<CouponModel> response) {
                if (response.code() == 200) {
                    assert response.body() != null;
                    if (response.body().getStatus().equalsIgnoreCase("success")) {

                        gotoMainScreen();
                        progressBar.setVisibility(View.GONE);

                    } else {
                        progressBar.setVisibility(View.GONE);
                    }
                } else {
                    if (response.code() == 401) {
                        new ToastMsg(getApplicationContext()).toastIconError(response.message());
                    } else {

                        new ToastMsg(getApplicationContext()).toastIconError(response.message() );
                    }
                    progressBar.setVisibility(View.GONE);

                }
            }

            @Override
            public void onFailure(@NonNull Call<CouponModel> call, @NonNull Throwable t) {
                progressBar.setVisibility(View.GONE);
                new ToastMsg(getApplicationContext()).toastIconError(getString(R.string.error_toast) + t);
            }
        });
    }

    public void loginBtn(View view) {
        hideKeyboard(view);
        if (!isValidEmailAddress(etEmail.getText().toString())) {
            new ToastMsg(LoginActivity.this).toastIconError("Please enter valid email");
        } else if (etPass.getText().toString().isEmpty()) {
            new ToastMsg(LoginActivity.this).toastIconError("Please enter password");
        } else {
            String email = etEmail.getText().toString();
            String pass = etPass.getText().toString();
            login(email, pass);
        }
    }

    public void mobileSignInBtnMobile(View view) {
        Intent intent = new Intent(getApplicationContext(), LoginMobileActivity.class);
        startActivity(intent);
        finishAffinity();
        overridePendingTransition(R.anim.enter, R.anim.exit);
    }

    private void login(String email, final String password) {
        progressBar.setVisibility(View.VISIBLE);
        Retrofit retrofit = RetrofitClient.getRetrofitInstance();
        LoginApi api = retrofit.create(LoginApi.class);
        Call<User> call = api.postLoginStatus(email, password);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {

                if (response.code() == 200) {
                    progressBar.setVisibility(View.GONE);
                    assert response.body() != null;
                    if (response.body().getStatus().equalsIgnoreCase("success")) {
                        User user = response.body();
                        DatabaseHelper db = new DatabaseHelper(LoginActivity.this);
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

                        PreferenceUtils.getInstance().setAccessTokenNPref(getApplicationContext(), response.body().getAccess_token());

                     /*   //save user login time, expire time
                        updateSubscriptionStatus(user.getUserId());*/

                        ll_emailLogin.setVisibility(View.GONE);
                        ll_coupon_code.setVisibility(View.VISIBLE);
                        /*    Intent intent = new Intent(getApplicationContext(), NewMainActivity.class);
                        startActivity(intent);
                        finishAffinity();
                        overridePendingTransition(R.anim.enter, R.anim.exit);*/

                    } else {
                        new ToastMsg(LoginActivity.this).toastIconError(response.body().getData());
                        progressBar.setVisibility(View.GONE);
                    }
                } else {
                    if (response.code() == 401) {
                        //   CMHelper.setSnackBar(this.getCurrentFocus(), String.valueOf("Please Enter OTP"), 2, 10000);
                        new ToastMsg(LoginActivity.this).toastIconError(response.message());
                    } else {
                        new ToastMsg(LoginActivity.this).toastIconError("Please Try Again Getting" + response.code());
                    }
                    progressBar.setVisibility(View.GONE);

                }
            }

            @Override
            public void onFailure(@NonNull Call<User> call, @NonNull Throwable t) {
                progressBar.setVisibility(View.GONE);
                new ToastMsg(LoginActivity.this).toastIconError(getString(R.string.error_toast));
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

        SubscriptionApi subscriptionApi = retrofit.create(SubscriptionApi.class);

        Call<ActiveStatus> call = subscriptionApi.getActiveStatus(Config.API_KEY, userId);
        call.enqueue(new Callback<ActiveStatus>() {
            @Override
            public void onResponse(@NonNull Call<ActiveStatus> call, @NonNull Response<ActiveStatus> response) {
                progressBar.setVisibility(View.GONE);
                if (response.code() == 200) {
                    if (response.body() != null) {
                        ActiveStatus activeStatus = response.body();
                        DatabaseHelper db = new DatabaseHelper(LoginActivity.this);

                        if (db.getActiveStatusCount() > 1) {
                            db.deleteAllActiveStatusData();
                        } else {

                            if (db.getActiveStatusCount() == 0) {
                                db.insertActiveStatusData(activeStatus);
                            } else {
                                db.updateActiveStatus(activeStatus, 1);
                            }
                        }
                        Intent intent = new Intent(LoginActivity.this, NewMainActivity.class);
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
