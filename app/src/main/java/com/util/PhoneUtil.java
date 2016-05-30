package com.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 *
 * Created by YeFeiHu on 2016/3/28.
 */
public class PhoneUtil {

    /**
     * 得到自己的软件版本
     * @param context
     * @return
     */
    public static int getVersion(Context context) {
        PackageManager manager = context.getPackageManager();
        try {
            PackageInfo  info = manager.getPackageInfo(context.getPackageName(),0);
            return  info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return 0;
        }

    }

}
