package com.util;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

/**提示信息类**/
public class Hint {
	public static void showHint(Context context,String msg) {
		Toast toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.CENTER, 0,0);
		toast.show();
	}

	public static void showLong(Context context, String msg){
		Toast toast = Toast.makeText(context, msg, Toast.LENGTH_LONG) ;
		toast.setGravity(Gravity.CENTER, 0,0);
		toast.show();
	}


}
