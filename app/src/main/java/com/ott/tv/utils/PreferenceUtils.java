package com.ott.tv.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;

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

    public static PreferenceUtils getInstance() {
        return INSTANCE;
    }

    public static boolean isActivePlan(Context context) {
        String status = getSubscriptionStatus(context);
        Log.e("Status", status);
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
                .getString(windowHeight,"true");
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
}
