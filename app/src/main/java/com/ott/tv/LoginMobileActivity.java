package com.ott.tv;

import static com.ott.tv.utils.CMHelper.hideKeyboard;
import static com.ott.tv.utils.PreferenceUtils.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
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
import android.widget.TextView;
import android.widget.Toast;


import com.ott.tv.countrycodepicker.CountryCodeActivity;
import com.ott.tv.database.DatabaseHelper;
import com.ott.tv.model.ActiveStatus;
import com.ott.tv.model.User;
import com.ott.tv.network.RetrofitClient;
import com.ott.tv.network.api.LoginApi;
import com.ott.tv.network.api.SendOTPApi;
import com.ott.tv.network.api.SubscriptionApi;
import com.ott.tv.ui.activity.LeanbackActivity;

import com.ott.tv.ui.activity.LoginActivity;
import com.ott.tv.ui.activity.LoginChooserActivity;
import com.ott.tv.utils.CMHelper;
import com.ott.tv.utils.PreferenceUtils;
import com.ott.tv.utils.ToastMsg;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginMobileActivity extends Activity {
    private EditText editMobileNumber,editVerifiedOTP;
    private ProgressBar progressBar;
    private Button send_otp,bt_verified_login;
    private String mob_number, country_code;

    private String countryCode, countryName;
    private TextView mobile_code_in,tv_verify_otp_mobileNo;
    private LinearLayout ll_send_otp,ll_verify_otp;

    /*@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i("krantiveer", "onActivityResult: GETDATA");
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 1000) {
            }
            Log.i("krantiveer", "onActivityResult: NO   GETDATA");
        }
    }*/

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_mobile);
        ll_send_otp=findViewById(R.id.ll_send_otp);
        ll_verify_otp=findViewById(R.id.ll_verify_otp);
        progressBar = findViewById(R.id.progress_login);
        send_otp = findViewById(R.id.send_otp);
        bt_verified_login = findViewById(R.id.bt_verified_login);
        editMobileNumber = findViewById(R.id.editMobileNumber);
        send_otp.setOnClickListener(view -> checkMobEdittxt());
        bt_verified_login.setOnClickListener(view -> check_verified_OTP());
        mobile_code_in = findViewById(R.id.mobile_code_in);
        tv_verify_otp_mobileNo=findViewById(R.id.tv_verify_otp_mobileNo);
        editVerifiedOTP=findViewById(R.id.editVerifiedOTP);
        mobile_code_in.setOnClickListener(view -> {

            Intent i = new Intent(this, CountryCodeActivity.class);
            startActivity(i);


        });
        countryCode = PreferenceUtils.getInstance().getCountyCodePref(getApplicationContext());
        countryName = PreferenceUtils.getInstance().getCountyNamePref(getApplicationContext());

        mobile_code_in.setText(countryName + " (" + " " + countryCode + ")");



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
    public void check_verified_OTP() {
        if (editVerifiedOTP.getText().toString().trim().equals("")) {
            CMHelper.setSnackBar(this.getCurrentFocus(), String.valueOf("Please Enter OTP"), 2, 10000);
        }else {
            getVerifyOTP(mob_number, editVerifiedOTP.getText().toString());
        }
    }
    public void checkMobEdittxt() {
        if (editMobileNumber.getText().toString().trim().equals("") || editMobileNumber.getText().toString().trim().equals("")) {
            CMHelper.setSnackBar(this.getCurrentFocus(), String.valueOf("Please Enter Mobile Number"), 2, 10000);
        }else {
            countryCode = countryCode.replace("+", "");
            // country_code
            mob_number=editMobileNumber.getText().toString();
            getSendOTP(editMobileNumber.getText().toString(), countryCode);
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
    private void getVerifyOTP(String mob_number, String otp) {
        progressBar.setVisibility(View.VISIBLE);
        Retrofit retrofit = RetrofitClient.getRetrofitInstance();
        SendOTPApi api = retrofit.create(SendOTPApi.class);
        Call<User> call = api.postVerifyOTP(Config.API_KEY, mob_number, otp);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
                if (response.code() == 200) {
                    assert response.body() != null;
                    if (response.body().getStatus().equalsIgnoreCase("success")) {
                        User user = response.body();
                       /* DatabaseHelper db = new DatabaseHelper(getApplicationContext());
                        if (db.getUserDataCount() > 1) {
                            db.deleteUserData();
                        } else {
                            if (db.getUserDataCount() == 0) {
                                db.insertUserData(user);
                            } else {
                                db.updateUserData(user, 1);
                            }
                        }*/
                        PreferenceUtils.getInstance().setAccessTokenNPref(getApplicationContext(),response.body().getAccess_token());


                        SharedPreferences.Editor preferences = getSharedPreferences(Constants.USER_LOGIN_STATUS, MODE_PRIVATE).edit();
                        preferences.putBoolean(Constants.USER_LOGIN_STATUS, true);
                        preferences.apply();

                        //save user login time, expire time
                       // updateSubscriptionStatus(user.getUserId());

                        Intent intent = new Intent(getApplicationContext(), LeanbackActivity.class);
                        startActivity(intent);
                        finishAffinity();
                        overridePendingTransition(R.anim.enter, R.anim.exit);

                        progressBar.setVisibility(View.GONE);

                    } else {
                        new ToastMsg(getApplicationContext()).toastIconError(response.body().getMessage());
                        progressBar.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<User> call, @NonNull Throwable t) {
                progressBar.setVisibility(View.GONE);
                new ToastMsg(getApplicationContext()).toastIconError(getString(R.string.error_toast));
            }
        });
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

                        tv_verify_otp_mobileNo.setText( "+" + countryCode+" "+mob_number);

                       /* SharedPreferences.Editor preferences = getSharedPreferences(Constants.USER_LOGIN_STATUS, MODE_PRIVATE).edit();
                        preferences.putBoolean(Constants.USER_LOGIN_STATUS, true);
                        preferences.apply();*/

                        //save user login time, expire time
                       // updateSubscriptionStatus(user.getUserId());
                        progressBar.setVisibility(View.GONE);
                        ll_send_otp.setVisibility(View.GONE);
                        ll_verify_otp.setVisibility(View.VISIBLE);
                    } else {
                        new ToastMsg(getApplicationContext()).toastIconError(response.body().getData());
                        progressBar.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<User> call, @NonNull Throwable t) {
                progressBar.setVisibility(View.GONE);
                new ToastMsg(getApplicationContext()).toastIconError(getString(R.string.error_toast));
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
                        Intent intent = new Intent(getApplicationContext(), LeanbackActivity.class);
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
        mobile_code_in.setText(countryName + " (" + " " + countryCode + ")");
    }
}