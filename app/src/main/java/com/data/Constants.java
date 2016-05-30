package com.data;

import android.os.Build;
import android.os.Environment;

import java.io.File;

/**
 * Created by YeFeiHu on 2016/3/28.
 */
public class Constants {
    /**
     * 是否在DEBUG 模式下  上架前 要改为 false
     */
    public static final boolean IS_DEBUG = true;
    /**
     * 是否检查程序被修改  上架前 要改为 true
     */
    public static final boolean IS_CHECK_BREAK = false;
    /**
     * 当前系统版本api
     */
    public static final int API = Build.VERSION.SDK_INT;
    /**
     * 是否有引导页
     */
    public static final boolean HAS_INDEX_PAGE = true;
    /**
     * 是否有欢迎页
     */
    public static final boolean HAS_WELCOME_PAGE = true;


    /**BroadcastReceiver**/
    public static final String RECEIVER_ACTION = "com.alarm.broadcastReceiver.action";


    /**
     * 应用在内存卡主目录
     */
    public static final String FTING_MAIN_DIR = getSdcardPath()+"/FanTing";




    /*** 得到sdcard路径 ***/
    public static String getSdcardPath() {
        File tempFile;
        boolean IsSDcardInstall = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        if(IsSDcardInstall) {
            tempFile = Environment.getExternalStorageDirectory();
//				tempFile = tempFile.getParentFile();
//				File[] fileList = tempFile.listFiles();
//				if(fileList.length <= 3) {
//					return tempFile;
//				}
            return tempFile.getPath();
        }
        return null;
    }


}
