package com.example.caipu.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class NetworkUtil {
    public static String KEY = "66fa9922197ddc6d";

    /**
     * 判断当前手机网络状态
     *
     *
     */
    public static boolean getNetState(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        if (info == null || !info.isAvailable()) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * pn	数据返回起始下标，默认0
     * rn	数据返回条数，最大30，默认10
     */
    public static String getURL(int id, int pn, int rn) {
        String url;


        url = "https://api.jisuapi.com/recipe/byclass?classid=" + id + "&start=" + pn + "&num=" + rn+"&appkey="+KEY;

        return url;
    }

//    public static String getURL(int id, int pn) {
//        String url = "https://api.jisuapi.com/recipe/byclass?classid=" + id + "&start=" + pn + "&num=" + rn+"&appkey="+KEY;
//        return url;
//    }
//
//    public static String getURL(int id) {
//        String url = "https://api.jisuapi.com/recipe/byclass?classid=" + id + "&start=" + pn + "&num=" + rn+"&appkey="+KEY;
//        return url;
//    }


}
