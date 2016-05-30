package com.data;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 软件数据 sp 文件数据
 * Created by YeFeiHu on 2016/3/28.
 */
public class AppData {
    /**
     * sp 文件名
     */
    private static final String SP_NAME = "AppBaseData.xml";
    /**
     * 当前版本
     */
    private static final String VERSION_CODE = "VERSION_CODE";
    /**
     * 捕捉数据
     */
    private static final String HAS_CATCH = "HAS_CATCH";

    /**
     * 得到版本码 默认 0
     * @param context
     * @return
     */
    public static int getVersionCode(Context context) {
        return getSP(context).getInt(VERSION_CODE, 0);
    }

    /**
     * 设置版本代码
     * @param context
     * @param version
     */
    public static void setVersionCode(Context context, int version){
        toEditor(context, VERSION_CODE, version);

    }

    /**
     * 是否抛出过一次  default false
     * @return
     */
    public static boolean isHasCatch(Context context) {
        return getSP(context).getBoolean(HAS_CATCH, false);
    }


    /**
     * 得到欢迎图片路径
     * @return
     */
    public static String getWlcomeImagePath(Context context) {
        return context.getFilesDir().getAbsolutePath()+"/image/welcome.png";
    }


    /**
     * 服务端做
     *
     * 欢迎界面 第一 图片 格式 png  比例 16*9
     *
     * https://www.fting.cc/androidapp/welcome.php
     *
     * 例：https://www.fting.cc/androidapp/welcome.php?flag=love&time=-1524564325
     *
     *  requestParam
     *
     * 参数1  flag  value(String){"" or 服务端的图片名} 第一次请求 空参数 "" 多次请求 服务端返回的state flag 为了保证 不重复请求图片， flag 与服务端的 相同 返回 state 值为 -1 否则返回 0
     * 参数2 time value(int) 客户端请求的时间 服务端可以什么也不干
     *
     *
     *  resultParm json
     *  参数1 state value(int){失败返回 -1， 成功返回 0}
     *
     * 参数 2 flag = value(String){服务端判断的标识，请求时再把这个flag发回去}
     *  参数3 url = value(String){下载图片的正确地址}
     *
     * 请求数据 以json 方式返回
     *
     * 例：{"state":"0","url":"http://baudu.com/logo.png"}
     *
     * @param context
     * @return
     */
    public static String getWelcomeImageUrl(Context context) {
        return "https://www.fting.cc/androidapp/welcome.php?flag="+getCurWelcomeRequestName(context)+"&time="+System.currentTimeMillis();
    }

    private static final String WELCOME_IMAGE_DATA = "WELCOME_IMAGE_DATA";
    public static String getCurWelcomeRequestName(Context context) {
        return getSP(context).getString(WELCOME_IMAGE_DATA, "");
    }

    /**
     * 设置 欢迎页面状态
     * @param context
     * @param state
     */
    public static void setCurWelcomeREquestState(Context context, String state) {
        toEditor(context, WELCOME_IMAGE_DATA, state);
    }


    private static SharedPreferences getSP(Context context) {
        return context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
    }


    private static void toEditor(Context context, String key, Object value) {
        SharedPreferences sp = getSP(context);
        SharedPreferences.Editor editor = sp.edit();
        if(value instanceof String) {
            editor.putString(key, (String) value);
        }else if(value instanceof Integer) {
            editor.putInt(key, (Integer) value);
        }else if(value instanceof Long) {
            editor.putLong(key, (Long) value);
        }else if(value instanceof Boolean) {
            editor.putBoolean(key, (Boolean) value);
        }else if(value instanceof Float) {
            editor.putFloat(key, (Float) value);
        }
        editor.commit();
    }


}
