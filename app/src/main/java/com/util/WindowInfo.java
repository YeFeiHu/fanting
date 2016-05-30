package com.util;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.ViewConfiguration;

/**窗体屏幕信息**/
public class WindowInfo {

	/**
	 * 得到屏幕宽度
	 * @param context
	 * @return
	 */
	public static int getScreenWidth(Context context) {
		DisplayMetrics metrics = new DisplayMetrics();
		metrics = context.getResources().getDisplayMetrics();
		int screenWidthDips = metrics.widthPixels;
		return screenWidthDips;
	}
	
	/**
	 * 得到屏幕高度除去菜单栏
	 * @return
	 */
	public static int getScreenHeight(Context context) {
		DisplayMetrics metrics = new DisplayMetrics();
		metrics = context.getResources().getDisplayMetrics();
		int screenHeightDips = metrics.heightPixels - getStatusHeight(context);
		
		return screenHeightDips;
		//return screenHeightDips = metrics
	}
	
	/**
	 * 获得状态栏的高度
	 * 
	 * @param context
	 * @return
	 */
	public static int getStatusHeight(Context context) {
	    int statusHeight = 0;
//        Rect localRect = new Rect();
//        ((Activity) context).getWindow().getDecorView().getWindowVisibleDisplayFrame(localRect);
//        statusHeight = localRect.top;
//        
//        if(statusHeight == 0) {
//        	return 0;
//        }
	    
	    try {
	        Class<?> clazz = Class.forName("com.android.internal.R$dimen");
	        Object object = clazz.newInstance();
	        int height = Integer.parseInt(clazz.getField("status_bar_height")
	                .get(object).toString());
	        statusHeight = context.getResources().getDimensionPixelSize(height);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return statusHeight;
	}
	
	
	/**
	 * 得到屏幕高度
	 * @param context
	 * @return
	 */
	public static int getFallScreenHeight(Context context) {
		DisplayMetrics metrics = new DisplayMetrics();
		metrics = context.getResources().getDisplayMetrics();
		return metrics.heightPixels;
	}


	/**
	 * 用于获取状态栏的高度。 使用Resource对象获取（推荐这种方式）
	 *
	 * @return 返回状态栏高度的像素值。
	 */
	public static int getStatusBarHeight(Context context) {
		int result = 0;
		int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen",
				"android");
		if (resourceId > 0) {
			result = context.getResources().getDimensionPixelSize(resourceId);
		}else {
			return getStatusBarHeight(context);
		}
		return result;
	}

	/**
	 * 得到导航栏高度
	 * @param context
	 * @return
     */
	public static int getNavigationBarHeight(Context context) {
		Resources resources = context.getResources();
		try {
			int resourceId = resources.getIdentifier("navigation_bar_height","dimen", "android");
			//获取NavigationBar的高度
			int height = resources.getDimensionPixelSize(resourceId);
		    if(height <0) {
				height = 0;
			}
			return  height;
		}catch (Exception e) {
			return 0;
		}

	}

	public static boolean checkDeviceHasNavigationBar(Context activity) {

		//通过判断设备是否有返回键、菜单键(不是虚拟键,是手机屏幕外的按键)来确定是否有navigation bar
		boolean hasMenuKey = ViewConfiguration.get(activity)
				.hasPermanentMenuKey();
		boolean hasBackKey = KeyCharacterMap
				.deviceHasKey(KeyEvent.KEYCODE_BACK);

		if (!hasMenuKey && !hasBackKey) {
			// 做任何你需要做的,这个设备有一个导航栏
			return true;
		}
		return false;
	}


}
