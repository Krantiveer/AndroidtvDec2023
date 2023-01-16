package com.ott.tv;

import com.ott.tv.model.VideoContent;

import java.util.ArrayList;
import java.util.List;

public class Constants {
    public static final String IMAGE_TYPE_COVERPOSTER = "coverposter";
    public static boolean IS_ENABLE_PROGRAM_GUIDE = false;
    public static final String USER_LOGIN_STATUS = "login_status";

    public static final String USERS_ID = "USERS_ID";
    public static final String POSITION = "position";
    public static List<VideoContent> movieList = new ArrayList<>();
    public static boolean IS_FROM_HOME=false;
    public static boolean IS_UVTV_STATE_OPEN=false;

    public interface WishListType {
        String fav = "fav";
        String watch_later = "watch_later";
    }
}
