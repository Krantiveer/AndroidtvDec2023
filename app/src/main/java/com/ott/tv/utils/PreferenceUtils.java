package com.ott.tv.utils;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;
import com.ott.tv.Config;
import com.ott.tv.Constants;
import com.ott.tv.R;
import com.ott.tv.database.DatabaseHelper;
import com.ott.tv.model.ActiveStatus;
import com.ott.tv.network.RetrofitClient;
import com.ott.tv.network.api.SubscriptionApi;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class PreferenceUtils {
    public static final String TAG = "PreferenceUtils";
    private static final PreferenceUtils INSTANCE = new PreferenceUtils();
    private static final String current_video_quality = "current_video_quality";
    private static final String windowHeight = "window_height";
    private static final String current_sub_title = "current_sub_title";
    private static final String shared_preferences = "shared_preferences";
    public static final String country_code = "country_code";
    public static final String country_name = "country_name";
    public static final String uvtv_state_name = "uvtv_state_name";
    public static final String access_token = "access_token";
    public static final String access_coupon = "access_coupon";
    public static final String state_name = "state_name";
    public static final Integer watch_list = 0;
    public static final String npawAccountKey = "";
    public static final String watermarklogourl = "watermarklogourl";
    public static final String watermarkEnable = "";
    public static final String websiteUrl = "";
    public static final String ENABLE_EMAIL_LOGIN = "ENABLE_EMAIL_LOGIN";
    public static final String ENABLE_MOBILE_LOGIN = "ENABLE_MOBILE_LOGIN";
    public static final String ENABLE_QR_LOGIN = "ENABLE_QR_LOGIN";
    public static final String ENABLE_Coupons = "ENABLE_Coupons";
    public static final String Login_With_Coupons = "Login_With_Coupons";
    public static final Boolean focusFromWatchNow = false;
    public static final String LOGIN_DISABLE = "LOGIN_DISABLE";
    public static final String G0TO_LOGIN = "G0TO_LOGIN";


    // public static final Boolean focusFromWatchNow = "";

    public static PreferenceUtils getInstance() {
        return INSTANCE;
    }

    public static boolean isActivePlan(Context context) {
        String status = getSubscriptionStatus(context);

        if (status != null) {
            return status.equals("active");
        } else {
            return false;
        }
    }

    public static boolean isLoggedIn(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(Constants.USER_LOGIN_STATUS, Context.MODE_PRIVATE);
        return preferences.getBoolean(Constants.USER_LOGIN_STATUS, false);
    }

    public static boolean isMandatoryLogin(Context context) {
        DatabaseHelper db = new DatabaseHelper(context);
        return db.getConfigurationData().getAppConfig().getMandatoryLogin();
    }

    public static String getSubscriptionStatus(Context context) {
        DatabaseHelper db = new DatabaseHelper(context);
        return db.getActiveStatusData().getStatus();
    }

    public static long getCurrentTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy:MM:dd:HH:mm");
        String currentDateAndTime = sdf.format(new Date());

        Date date = null;
        try {
            date = sdf.parse(currentDateAndTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        return calendar.getTimeInMillis();
    }

    public static long getExpireTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy:MM:dd:HH:mm");
        String currentDateAndTime = sdf.format(new Date());

        Date date = null;
        try {
            date = sdf.parse(currentDateAndTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR, 2);

        return calendar.getTimeInMillis();
    }

    public static boolean isValid(Context context) {
        String savedTime = getUpdateTime(context);
        long currentTime = getCurrentTime();
        return Long.parseLong(savedTime) > currentTime;
    }

    private static String getUpdateTime(Context context) {
        DatabaseHelper db = new DatabaseHelper(context);
        return String.valueOf(db.getActiveStatusData().getExpireTime());
    }

    public static void updateSubscriptionStatus(final Context context) {
        String userId = getUserId(context);
        Retrofit retrofit = RetrofitClient.getRetrofitInstance();
        SubscriptionApi subscriptionApi = retrofit.create(SubscriptionApi.class);
        Call<ActiveStatus> call = subscriptionApi.getActiveStatus(Config.API_KEY, userId);
        call.enqueue(new Callback<ActiveStatus>() {
            @Override
            public void onResponse(@NonNull Call<ActiveStatus> call, @NonNull Response<ActiveStatus> response) {
                if (response.code() == 200) {
                    ActiveStatus activeStatus = response.body();
                    if (activeStatus != null) {
                        DatabaseHelper db = new DatabaseHelper(context);
                        db.deleteAllActiveStatusData();
                        db.insertActiveStatusData(activeStatus);
                    }

                }
            }

            @Override
            public void onFailure(@NonNull Call<ActiveStatus> call, @NonNull Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public static void clearSubscriptionSavedData(Context context) {
        //now save to sharedPreference
        DatabaseHelper db = new DatabaseHelper(context);
        db.deleteAllActiveStatusData();
    }

    public static String getUserId(Context context) {
        DatabaseHelper db = new DatabaseHelper(context);
        return db.getUserData().getUserId();

    }

    public String getUsersIdActionOTT(Context context) {
        return context.getSharedPreferences(TAG, Context.MODE_PRIVATE)
                .getString(Constants.USERS_ID, "");
    }


    public void setWindowHeightPref(Context context, String hight) {
        SharedPreferences prefs = context.getSharedPreferences(shared_preferences, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(current_video_quality, hight);
        editor.apply();
    }

    public String getWindowHeightPref(Context context) {

        return context.getSharedPreferences(shared_preferences, Context.MODE_PRIVATE)
                .getString(windowHeight, "true");
    }

    public void setVideoQualityPref(Context context, String videoQuality) {
        SharedPreferences prefs = context.getSharedPreferences(shared_preferences, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(current_video_quality, videoQuality);
        editor.apply();
    }

    public String getVideoQualityPref(Context context) {
        return context.getSharedPreferences(shared_preferences, Context.MODE_PRIVATE)
                .getString(current_video_quality, context.getString(R.string.hint_auto));
    }

    public void setSubtitlePref(Context context, String subtitle) {
        SharedPreferences prefs = context.getSharedPreferences(shared_preferences, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(current_sub_title, subtitle);
        editor.apply();
    }

    public String getSubtitlePref(Context context) {
        return context.getSharedPreferences(shared_preferences, Context.MODE_PRIVATE)
                .getString(current_sub_title, "None");
    }

    public void setCountyCodePref(Context context, String countryCode) {
        SharedPreferences prefs = context.getSharedPreferences(shared_preferences, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(country_code, countryCode);
        editor.apply();
    }

    public String getCountyCodePref(Context context) {
        return context.getSharedPreferences(shared_preferences, Context.MODE_PRIVATE)
                .getString(country_code, context.getString(R.string.country_code_default));
    }

    public void setCountyNamePref(Context context, String countryName) {
        SharedPreferences prefs = context.getSharedPreferences(shared_preferences, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(country_name, countryName);
        editor.apply();
    }

    public String getCountyNamePref(Context context) {
        return context.getSharedPreferences(shared_preferences, Context.MODE_PRIVATE)
                .getString(country_name, context.getString(R.string.country_name_default));
    }

    public void setUvtv_state_namePref(Context context, String stateName) {
        SharedPreferences prefs = context.getSharedPreferences(shared_preferences, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(uvtv_state_name, stateName);
        editor.apply();
    }

    public String getUvtv_state_namePref(Context context) {
        return context.getSharedPreferences(shared_preferences, Context.MODE_PRIVATE)
                .getString(uvtv_state_name, context.getString(R.string.uvtv_state_name_default));
    }

    public void setAccessTokenNPref(Context context, String accessToken) {
        SharedPreferences prefs = context.getSharedPreferences(shared_preferences, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(access_token, accessToken);
        editor.apply();
    }

    public String getAccessTokenPref(Context context) {
        return context.getSharedPreferences(shared_preferences, Context.MODE_PRIVATE)
                .getString(access_token, "");
    }

    public void setAccessCouponPref(Context context, String accessToken) {
        SharedPreferences prefs = context.getSharedPreferences(shared_preferences, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(access_coupon, accessToken);
        editor.apply();
    }

    public String getAccessCouponPref(Context context) {
        return context.getSharedPreferences(shared_preferences, Context.MODE_PRIVATE)
                .getString(access_coupon, "");
    }

    public void setStateNamePref(Context context, String name) {
        SharedPreferences prefs = context.getSharedPreferences(shared_preferences, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(state_name, name);
        editor.apply();
    }

    public String getStateNamePref(Context context) {
        return context.getSharedPreferences(shared_preferences, Context.MODE_PRIVATE)
                .getString(state_name, "");
    }

    public void setWatchListPref(Context context, Integer name) {
        SharedPreferences prefs = context.getSharedPreferences(shared_preferences, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(String.valueOf(watch_list), name);
        editor.apply();
    }

    public Integer getWatchListPref(Context context) {
        return context.getSharedPreferences(shared_preferences, Context.MODE_PRIVATE)
                .getInt(String.valueOf(watch_list), 0);
    }

    public void setWatermarkEnablePref(Context context, String str) {
        SharedPreferences prefs = context.getSharedPreferences(shared_preferences, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(watermarkEnable, str);
        editor.apply();
    }

    public String getWatermarkEnablePref(Context context) {
        return context.getSharedPreferences(shared_preferences, Context.MODE_PRIVATE)
                .getString(watermarkEnable, "1");
    }

    public void setWebsiteUrlPref(Context context, String str) {
        SharedPreferences prefs = context.getSharedPreferences(shared_preferences, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(websiteUrl, str);
        editor.apply();
    }

    public String getwebsiteUrlPref(Context context) {
        return context.getSharedPreferences(shared_preferences, Context.MODE_PRIVATE)
                .getString(websiteUrl, "");
    }

    public void setWatermarkLogoUrlPref(Context context, String str) {
        SharedPreferences prefs = context.getSharedPreferences(shared_preferences, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(watermarklogourl, str);
        editor.apply();
    }

    public String getWatermarkLogoUrlPref(Context context) {
        return context.getSharedPreferences(shared_preferences, Context.MODE_PRIVATE)
                .getString(watermarklogourl, "data");
    }

    public void setNpawAccountKeyPref(Context context, String accessToken) {
        SharedPreferences prefs = context.getSharedPreferences(shared_preferences, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(npawAccountKey, accessToken);
        editor.apply();
    }

    public String getNpawAccountKeyPref(Context context) {
        return context.getSharedPreferences(shared_preferences, Context.MODE_PRIVATE)
                .getString(npawAccountKey, "");
    }


    public void setNpawEnablePref(Context context, Boolean name) {
        SharedPreferences prefs = context.getSharedPreferences(shared_preferences, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("isnpawEnable", name);
        editor.apply();
    }

    public Boolean getNpawEnablePref(Context context) {
        return context.getSharedPreferences(shared_preferences, Context.MODE_PRIVATE)
                .getBoolean("isnpawEnable", false);
    }

    public void setFocusFromWatchNowPref(Context context, Boolean name) {
        SharedPreferences prefs = context.getSharedPreferences(shared_preferences, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(String.valueOf(focusFromWatchNow), name);
        editor.apply();
    }

    public Boolean getFocusFromWatchNowPref(Context context) {
        return context.getSharedPreferences(shared_preferences, Context.MODE_PRIVATE)
                .getBoolean(String.valueOf(focusFromWatchNow), false);
    }

    public void setENABLE_EMAIL_LOGINPref(Context context, String str) {
        SharedPreferences prefs = context.getSharedPreferences(shared_preferences, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(ENABLE_EMAIL_LOGIN, str);
        editor.apply();
    }

    public String getENABLE_EMAIL_LOGINPref(Context context) {
        return context.getSharedPreferences(shared_preferences, Context.MODE_PRIVATE)
                .getString(ENABLE_EMAIL_LOGIN, "0");
    }

    public void setENABLE_MOBILE_LOGINPref(Context context, String str) {
        SharedPreferences prefs = context.getSharedPreferences(shared_preferences, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(ENABLE_MOBILE_LOGIN, str);
        editor.apply();
    }

    public String getENABLE_MOBILE_LOGINPref(Context context) {
        return context.getSharedPreferences(shared_preferences, Context.MODE_PRIVATE)
                .getString(ENABLE_MOBILE_LOGIN, "0");
    }

    public void setENABLE_QR_LOGINPref(Context context, String str) {
        SharedPreferences prefs = context.getSharedPreferences(shared_preferences, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(ENABLE_QR_LOGIN, str);
        editor.apply();
    }

    public String getENABLE_QR_LOGINPref(Context context) {
        return context.getSharedPreferences(shared_preferences, Context.MODE_PRIVATE)
                .getString(ENABLE_QR_LOGIN, "0");
    }

    public void setLogin_With_CouponsPref(Context context, String str) {
        SharedPreferences prefs = context.getSharedPreferences(shared_preferences, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(Login_With_Coupons, str);
        editor.apply();
    }

    public String getLogin_With_CouponsPref(Context context) {
        return context.getSharedPreferences(shared_preferences, Context.MODE_PRIVATE)
                .getString(Login_With_Coupons, "0");
    }

    public void setENABLE_CouponsPref(Context context, String str) {
        SharedPreferences prefs = context.getSharedPreferences(shared_preferences, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(ENABLE_Coupons, str);
        editor.apply();
    }

    public String getENABLE_CouponsPref(Context context) {
        return context.getSharedPreferences(shared_preferences, Context.MODE_PRIVATE)
                .getString(ENABLE_Coupons, "0");
    }

    public void setLOGIN_DISABLEPref(Context context, String str) {
        SharedPreferences prefs = context.getSharedPreferences(shared_preferences, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(LOGIN_DISABLE, str);
        editor.apply();
    }

    public String getLOGIN_DISABLEPref(Context context) {
        return context.getSharedPreferences(shared_preferences, Context.MODE_PRIVATE)
                .getString(LOGIN_DISABLE, "1");
    }

    public void setG0TO_LOGINPref(Context context, String str) {
        SharedPreferences prefs = context.getSharedPreferences(shared_preferences, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(G0TO_LOGIN, str);
        editor.apply();
    }

    public String getG0TO_LOGINPref(Context context) {
        return context.getSharedPreferences(shared_preferences, Context.MODE_PRIVATE)
                .getString(G0TO_LOGIN, "false");
    }



}
