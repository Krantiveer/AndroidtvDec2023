package com.ott.tv.ui.activity;


import static com.ott.tv.ui.activity.SearchActivity_Phando.progressBar;
import static com.ott.tv.utils.CMHelper.hideKeyboard;

import androidx.annotation.NonNull;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.ott.tv.BuildConfig;
import com.ott.tv.Config;
import com.ott.tv.Constants;
import com.ott.tv.R;
import com.ott.tv.countrycodepicker.CountryCodeActivity;
import com.ott.tv.database.DatabaseHelper;
import com.ott.tv.model.CouponModel;
import com.ott.tv.model.User;
import com.ott.tv.model.phando.UserProfile;
import com.ott.tv.network.RetrofitClient;
import com.ott.tv.network.api.Dashboard;
import com.ott.tv.network.api.SendOTPApi;

import com.ott.tv.utils.CMHelper;
import com.ott.tv.utils.PreferenceUtils;
import com.ott.tv.utils.ToastMsg;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginMobileActivity extends Activity {
    private EditText editMobileNumber, editVerifiedOTP, editCouponCode;
    private ProgressBar progressBar, progress_login_resend;
    private Button send_otp, bt_verified_login, bt_resend, bt_skip, bt_coupon,login_email;
    private String mob_number, country_code;

    private String countryCode, countryName;
    private TextView mobile_code_in, tv_verify_otp_mobileNo, timer_txt, timer, tv_timer;
    private LinearLayout ll_send_otp, ll_verify_otp, ll_coupon_code;
    private CountDownTimer cTimer;
    private UserProfile userProfile;
    private  String contrycode="91";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_mobile);
        ll_send_otp = findViewById(R.id.ll_send_otp);
        ll_verify_otp = findViewById(R.id.ll_verify_otp);
        progressBar = findViewById(R.id.progress_login);
        progress_login_resend = findViewById(R.id.progress_login_resend);
        send_otp = findViewById(R.id.send_otp);
        bt_verified_login = findViewById(R.id.bt_verified_login);
        editMobileNumber = findViewById(R.id.editMobileNumber);
        send_otp.setOnClickListener(view -> checkMobEdittxt());
        bt_verified_login.setOnClickListener(view -> check_verified_OTP());
        bt_verified_login.setOnFocusChangeListener((v, hasFocus) -> hideKeyboard(v));
        send_otp.setOnFocusChangeListener((v, hasFocus) -> hideKeyboard(v));
        mobile_code_in = findViewById(R.id.mobile_code_in);
        tv_verify_otp_mobileNo = findViewById(R.id.tv_verify_otp_mobileNo);
        editVerifiedOTP = findViewById(R.id.editVerifiedOTP);
        editCouponCode = findViewById(R.id.editCouponCode);
        login_email=findViewById(R.id.login_email);

        ll_coupon_code = findViewById(R.id.ll_coupon_code);

        editMobileNumber.requestFocus();
        if (PreferenceUtils.getInstance().getENABLE_EMAIL_LOGINPref(getApplicationContext()).equalsIgnoreCase("0")) {
            login_email.setVisibility(View.GONE);
        }

        mobile_code_in.setOnClickListener(view -> {
            if(BuildConfig.FLAVOR.equalsIgnoreCase("vtv")){

            }else{
            Intent i = new Intent(this, CountryCodeActivity.class);
            startActivity(i);}
        });

        countryCode = PreferenceUtils.getInstance().getCountyCodePref(getApplicationContext());
        countryName = PreferenceUtils.getInstance().getCountyNamePref(getApplicationContext());
     /*   if(BuildConfig.FLAVOR.equalsIgnoreCase("vtv")){
            countryCode = "+255";
            countryName = "Tanzania";

        }*/

        mobile_code_in.setText(countryName + " (" + " " + countryCode + ")");

        timer = findViewById(R.id.timer);
        timer_txt = findViewById(R.id.timer_txt);
        timer_txt.setText("Didn't receive OTP? Resend in ");
        bt_resend = findViewById(R.id.bt_resend);
        bt_skip = findViewById(R.id.bt_skip);
        bt_coupon = findViewById(R.id.bt_coupon);

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

        bt_resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timer.setVisibility(View.VISIBLE);
                bt_resend.setVisibility(View.GONE);
                //   startTimer();
                getSendOTP(mob_number, countryCode);

            }
        });

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
    }

    void startTimer() {
        cTimer = new CountDownTimer(59000, 1000) {
            public void onTick(long millisUntilFinished) {
                timer_txt.setText("Didn't receive OTP? Resend in");
                timer.setText(" 00:" + String.valueOf(millisUntilFinished / 1000));
            }

            public void onFinish() {
                timer_txt.setText("Didn't receive OTP?");
                timer.setVisibility(View.GONE);
                bt_resend.setVisibility(View.VISIBLE);
                //  tv_timer.setVisibility(View.VISIBLE);
                //   resend.setEnabled(true);
            }
        };
        cTimer.start();
    }

    public void check_verified_OTP() {
        if (editVerifiedOTP.getText().toString().trim().equals("")) {
            CMHelper.setSnackBar(this.getCurrentFocus(), String.valueOf("Please Enter OTP"), 2, 7000);
        } else {
            if (editVerifiedOTP.getText().length() < 6) {
                CMHelper.setSnackBar(this.getCurrentFocus(), String.valueOf("The OTP Must be 6 Digits."), 2, 7000);
            } else if(editVerifiedOTP.getText().length()==6){
                getVerifyOTP(mob_number, editVerifiedOTP.getText().toString());
            }else {
                 CMHelper.setSnackBar(this.getCurrentFocus(), String.valueOf("The OTP Must be 6 Digits."), 2, 7000);

            }
        }
    }

    public void checkMobEdittxt() {
        if(BuildConfig.FLAVOR.equalsIgnoreCase("vtv")){
            if (editMobileNumber.getText().toString().trim().equals("") || editMobileNumber.getText().toString().trim().equals("")) {
                CMHelper.setSnackBar(this.getCurrentFocus(), String.valueOf("Please Enter Mobile Number"), 2, 10000);
            } else if (editMobileNumber.getText().length() <= 8) {
                CMHelper.setSnackBar(this.getCurrentFocus(), String.valueOf("Please Enter Valid Mobile Number"), 2, 10000);
            }

            else {
                countryCode = countryCode.replace("+", "");
                // country_code
                mob_number = editMobileNumber.getText().toString();
                getSendOTP(editMobileNumber.getText().toString(), countryCode);
            }

        }else{
            if (editMobileNumber.getText().toString().trim().equals("") || editMobileNumber.getText().toString().trim().equals("")) {
                CMHelper.setSnackBar(this.getCurrentFocus(), String.valueOf("Please Enter Mobile Number"), 2, 10000);
            }
            else if (editMobileNumber.getText().length() <= 9) {
                CMHelper.setSnackBar(this.getCurrentFocus(), String.valueOf("Please Enter Valid Mobile Number"), 2, 10000);
            }
            else {
                countryCode = countryCode.replace("+", "");
                // country_code
                mob_number = editMobileNumber.getText().toString();
                getSendOTP(editMobileNumber.getText().toString(), countryCode);
            }
        }

    }


    /*
        void getOtpp() {
            startTimer()
            val stringRequest = object : StringRequest(
                    Request.Method.POST, Api.baseUrlApi + "send-otp",
                    Response.Listener { response ->
                try {
                    val jsonObject = JSONObject(response)
    //                    Log.d("aaa", "response" + response)
                        */
/*
                      Toast.makeText(
                            this,
                            jsonObject.getString("message") + " to your mobile",
                            Toast.LENGTH_SHORT
                        ).show()*//*

                otp_layout.visibility = View.VISIBLE
                mobileNo.setText("Enter OTP sent to "+mobile_code_in.text + mob_number)
                layout_mobile.visibility = View.GONE
                bottomLayout.visibility = View.GONE
                et_otp1.requestFocus()

            } catch (e: JSONException) {
                Log.e("@@1", e.message.toString())
                e.printStackTrace()
            }
        }, Response.ErrorListener {



            Toast.makeText(
                    applicationContext,
                    getString(R.string.somthing_went_wrong),
                    Toast.LENGTH_LONG
            ).show()
        }) {
            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()
                params["mobile"] = mob_number
                params["country_code"] = countryCode
                params["device_type"] = "tv"
                return params
            }
        }
        val requestQueue = Volley.newRequestQueue(applicationContext)
        requestQueue.add(stringRequest)
    }
*/
    private void gotoMainScreen() {


        Intent intent = new Intent(getApplicationContext(), NewMainActivity.class);
        startActivity(intent);
        finishAffinity();
        overridePendingTransition(R.anim.enter, R.anim.exit);


    }

    private void gotoCouponScreen() {

        String couponCode = editCouponCode.getText().toString();
        if (couponCode.isEmpty() || couponCode.trim().isEmpty()) {
            new ToastMsg(getApplicationContext()).toastIconError("Please Enter CouponCode");

        } else {
            getVerifiedCoupon(couponCode);
        }


    }

    private void getVerifiedCoupon(String couponCode) {
        progressBar.setVisibility(View.VISIBLE);
        Retrofit retrofit = RetrofitClient.getRetrofitInstance();
        String accessToken = "Bearer " + PreferenceUtils.getInstance().getAccessTokenPref(this);

        SendOTPApi api = retrofit.create(SendOTPApi.class);
        Call<CouponModel> call = api.assignCoupon(Config.API_KEY,accessToken, couponCode);

        call.enqueue(new Callback<CouponModel>() {
            @Override
            public void onResponse(@NonNull Call<CouponModel> call, @NonNull Response<CouponModel> response) {
                if (response.code() == 200) {
                    assert response.body() != null;
                    if (response.body().getStatus().equalsIgnoreCase("success")) {

                        gotoMainScreen();
                        progressBar.setVisibility(View.GONE);

                    } else {

                        new ToastMsg(getApplicationContext()).toastIconError(response.body().getMessage());

                        progressBar.setVisibility(View.GONE);
                    }
                } else {
                    if (response.code() == 401) {
                        new ToastMsg(getApplicationContext()).toastIconError(response.message());
                    } else {

                        new ToastMsg(getApplicationContext()).toastIconError(response.message());
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

    private void getVerifyOTP(String mob_number, String otp) {
        progress_login_resend.setVisibility(View.VISIBLE);
        Retrofit retrofit = RetrofitClient.getRetrofitInstance();
        SendOTPApi api = retrofit.create(SendOTPApi.class);

        if(BuildConfig.FLAVOR.equalsIgnoreCase("vtv")){
            contrycode="255";
        }else{
            contrycode="91";

        }


        Call<User> call = api.postVerifyOTP(Config.API_KEY, mob_number, otp,contrycode);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
                if (response.code() == 200) {
                    assert response.body() != null;
                    if (response.body().getStatus().equalsIgnoreCase("success")) {
                        User user = response.body();
                        PreferenceUtils.getInstance().setAccessTokenNPref(getApplicationContext(), response.body().getAccess_token());
                        SharedPreferences.Editor preferences = getSharedPreferences(Constants.USER_LOGIN_STATUS, MODE_PRIVATE).edit();
                        preferences.putBoolean(Constants.USER_LOGIN_STATUS, true);
                        preferences.apply();

                        //save user login time, expire time
                        // updateSubscriptionStatus(user.getUserId());
                        if(BuildConfig.FLAVOR.equalsIgnoreCase("solidtv")){
                            getUserProfileDataFromServer();
                        }else{
                            gotoMainScreen();

                        }


                        progress_login_resend.setVisibility(View.GONE);

                    } else {
                        new ToastMsg(getApplicationContext()).toastIconError(response.body().getMessage());
                        progress_login_resend.setVisibility(View.GONE);
                    }
                } else {
                    if (response.code() == 401) {
                        //   CMHelper.setSnackBar(this.getCurrentFocus(), String.valueOf("Please Enter OTP"), 2, 10000);
                        new ToastMsg(getApplicationContext()).toastIconError(response.message());
                    } else {
                        new ToastMsg(getApplicationContext()).toastIconError(response.message());
                    }
                    progress_login_resend.setVisibility(View.GONE);

                }
            }

            @Override
            public void onFailure(@NonNull Call<User> call, @NonNull Throwable t) {
                progress_login_resend.setVisibility(View.GONE);
                new ToastMsg(getApplicationContext()).toastIconError(getString(R.string.error_toast) + t);
            }
        });
    }
    private void getUserProfileDataFromServer() {
        if (getApplicationContext() != null) {
            Constants.IS_FROM_HOME = false;
            Retrofit retrofit = RetrofitClient.getRetrofitInstance();
            Dashboard api = retrofit.create(Dashboard.class);
            String accessToken = "Bearer " + PreferenceUtils.getInstance().getAccessTokenPref(getApplicationContext());
            Call<UserProfile> call = api.getUserProfileAPI(accessToken);
            call.enqueue(new Callback<UserProfile>() {
                @Override
                public void onResponse(@NotNull Call<UserProfile> call, @NotNull retrofit2.Response<UserProfile> response) {
                    if (response.isSuccessful()) {
                        if (response.code() == 200 && response.body() != null) {
                            userProfile = response.body();

                            if(userProfile.getIs_subscribed()!=0){
                                PreferenceUtils.getInstance().setAccessCouponPref(getApplication(),"1");
                                PreferenceUtils.getInstance().setLogin_With_CouponsPref(getApplicationContext(), "1");
                            }
                            if (userProfile.getIs_subscribed() == 0) {

                                    ll_coupon_code.setVisibility(View.VISIBLE);
                                    ll_verify_otp.setVisibility(View.GONE);


                             }else{
                                gotoMainScreen();

                            }
                        } else if (response.errorBody() != null) {
                            Toast.makeText(getApplicationContext(), response.errorBody().toString(), Toast.LENGTH_SHORT).show();

                        } else {
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(getApplicationContext(), "Sorry! Something went wrong. Please try again after some time", Toast.LENGTH_SHORT).show();


                        }
                        if (userProfile.getIs_review() == 1) {


                        } else if (userProfile.getIs_review() == 0) {

                        }

                    } else {
                        if (response.code() == 401) {
                            //   CMHelper.setSnackBar(this.getCurrentFocus(), String.valueOf("Please Enter OTP"), 2, 10000);
                            signOut();
                            //     Toast.makeText(getContext(), "Please Login Again", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Sorry! Something went wrong. Please try again after some time", Toast.LENGTH_SHORT).show();
                        }


                        //  Toast.makeText(getContext(), "Sorry! Something went wrong. Please try again after some time", Toast.LENGTH_SHORT).show();

                        //CMHelper.setSnackBar(requireView(), "Sorry! Something went wrong. Please try again after some time", 2);
                    }
                }

                @Override
                public void onFailure(@NonNull Call<UserProfile> call, @NonNull Throwable t) {
                    t.printStackTrace();


                }
            });
        }
    }

    private void signOut() {
        if (getApplicationContext() != null ) {
            DatabaseHelper databaseHelper = new DatabaseHelper(getApplicationContext());

            if (PreferenceUtils.getInstance().getAccessTokenPref(getApplicationContext()) != "") {
                SharedPreferences.Editor editor = getApplicationContext().getSharedPreferences(Constants.USER_LOGIN_STATUS, MODE_PRIVATE).edit();
                editor.putBoolean(Constants.USER_LOGIN_STATUS, false);
                editor.apply();
                databaseHelper.deleteUserData();
                PreferenceUtils.clearSubscriptionSavedData(getApplicationContext());
                PreferenceUtils.getInstance().setAccessTokenNPref(getApplicationContext(), "");
                startActivity(new Intent(getApplicationContext(), LoginChooserActivity.class));
                this.finish();
            }
        }
    }

    private void getSendOTP(String mob_number, String country_code) {
        progressBar.setVisibility(View.VISIBLE);
        Retrofit retrofit = RetrofitClient.getRetrofitInstance();
        SendOTPApi api = retrofit.create(SendOTPApi.class);
        Call<User> call = api.postSendOTP(Config.API_KEY, mob_number, country_code);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
                if (response.code() == 200) {
                    assert response.body() != null;
                    if (response.body().getStatus().equalsIgnoreCase("success")) {
                        User user = response.body();
                        /*DatabaseHelper db = new DatabaseHelper(getApplicationContext());
                        if (db.getUserDataCount() > 1) {
                            db.deleteUserData();
                        } else {
                            if (db.getUserDataCount() == 0) {
                                db.insertUserData(user);
                            } else {
                                db.updateUserData(user, 1);
                            }
                        }*/

                        tv_verify_otp_mobileNo.setText("+" + countryCode + " " + mob_number);

                       /* SharedPreferences.Editor preferences = getSharedPreferences(Constants.USER_LOGIN_STATUS, MODE_PRIVATE).edit();
                        preferences.putBoolean(Constants.USER_LOGIN_STATUS, true);
                        preferences.apply();*/

                        //save user login time, expire time
                        // updateSubscriptionStatus(user.getUserId());
                        progressBar.setVisibility(View.GONE);
                        ll_send_otp.setVisibility(View.GONE);
                        ll_verify_otp.setVisibility(View.VISIBLE);
                        editVerifiedOTP.requestFocus();
                        startTimer();

                    } else {
                        new ToastMsg(getApplicationContext()).toastIconError(response.body().getData());
                        progressBar.setVisibility(View.GONE);
                    }
                } else {
                    if (response.code() == 401) {
                        //   CMHelper.setSnackBar(this.getCurrentFocus(), String.valueOf("Please Enter OTP"), 2, 10000);
                        new ToastMsg(getApplicationContext()).toastIconError(response.message());
                    } else {

                        new ToastMsg(getApplicationContext()).toastIconError("Please Try Again Getting" + response.message());
                    }
                    progressBar.setVisibility(View.GONE);

                }
            }

            @Override
            public void onFailure(@NonNull Call<User> call, @NonNull Throwable t) {
                progressBar.setVisibility(View.GONE);
                new ToastMsg(getApplicationContext()).toastIconError(getString(R.string.error_toast) + t);
            }
        });
    }


    public void emailSignInBtnPhone(View view) {
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.enter, R.anim.exit);
    }

    public void mobileSignInBtn(View view) {
        Intent intent = new Intent(getApplicationContext(), LoginMobileActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.enter, R.anim.exit);
    }


/*
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
                        DatabaseHelper db = new DatabaseHelper(getApplicationContext());

                        if (db.getActiveStatusCount() > 1) {
                            db.deleteAllActiveStatusData();
                        } else {

                            if (db.getActiveStatusCount() == 0) {
                                db.insertActiveStatusData(activeStatus);
                            } else {
                                db.updateActiveStatus(activeStatus, 1);
                            }
                        }
                        Intent intent = new Intent(getApplicationContext(), NewMainActivity.class);
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
*/

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

            case KeyEvent.KEYCODE_DPAD_UP_LEFT:

            case KeyEvent.KEYCODE_DPAD_UP_RIGHT:

            case KeyEvent.KEYCODE_DPAD_DOWN:

            case KeyEvent.KEYCODE_DPAD_DOWN_LEFT:

            case KeyEvent.KEYCODE_DPAD_DOWN_RIGHT:
                return false;
            case KeyEvent.KEYCODE_DPAD_LEFT:

            case KeyEvent.KEYCODE_DPAD_RIGHT:

            case KeyEvent.KEYCODE_DPAD_UP:
                Log.e("LoginActivity", "movieIndex : ");
                return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onResume() {
        super.onResume();
        countryCode = PreferenceUtils.getInstance().getCountyCodePref(getApplicationContext());
        countryName = PreferenceUtils.getInstance().getCountyNamePref(getApplicationContext());
     /*   if(BuildConfig.FLAVOR.equalsIgnoreCase("vtv")){
            countryCode = "+255";
            countryName = "Tanzania";
            mobile_code_in.setText(countryName + " (" + " " + countryCode + ")");

        }
 */   }
}