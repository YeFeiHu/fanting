package com.fanting.client;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.Window;
import android.view.WindowManager;

import com.data.Constants;
import com.util.WindowInfo;

public abstract class MyActivity extends AppCompatActivity {
    private boolean isBroadcastRegister;
    private MsgBroadcastReceiver msgBroadcastReceiver = null;

    /**
     * 广播接收者，接收GetMsgService发送过来的消息
     */
    private class MsgBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            getMessage(intent);
        }
    }

    /**
     * 抽象方法，用于子类处理消息，
     *
     * @param intent 传递给子类的消息对象
     */
    public abstract void getMessage(Intent intent);

    /**
     * 关闭程序
     **/
    public void close() {
        Intent i = new Intent();
        i.setAction(Constants.RECEIVER_ACTION);
        sendBroadcast(i);
        finish();

    }

    /**
     * 注册接收广播
     **/
    public void registerBroadcastReceiver() {
        //if(msgBroadcastReceiver == null) {
        msgBroadcastReceiver = new MsgBroadcastReceiver();
        //}
        //if(!isBroadcastRegister) {
        //	isBroadcastRegister = true;
        IntentFilter intentFilter = new IntentFilter(Constants.RECEIVER_ACTION);
        registerReceiver(msgBroadcastReceiver, intentFilter);
        //}
    }

    public void unregisterBroadcastReceiver() {
        //	if(isBroadcastRegister) {
        //		if(msgBroadcastReceiver != null) {
        unregisterReceiver(msgBroadcastReceiver);
        //			msgBroadcastReceiver = null;
        //			isBroadcastRegister = false;
        //		}
        //	}
    }


    /**
     * 当某个activity变得“容易”被系统销毁时，该activity的onSaveInstanceState就会被执行，
     * 除非该activity是被用户主动销毁的，例如当用户按BACK键的时候
     * 一个原则：即当系统“未经你许可”时销毁了你的activity，则onSaveInstanceState会被系统调用
     * 情景：
     * 1. 当用户按下HOME键时
     * 2. 长按HOME键，选择运行其他的程序时。
     * 3. 按下电源按键（关闭屏幕显示）时。
     * 4. 从activity A中启动一个新的activity时。
     * 5. 屏幕方向切换时，例如从竖屏切换到横屏时。
     * 以上情景触发该函数，并且开发者可以保存一些数据状态
     */
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        Log.d("HELLO", "HELLO：当Activity被销毁的时候，不是用户主动按back销毁，例如按了home键");
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString("username", "initphp"); //这里保存一个用户名
    }

    /**
     * onSaveInstanceState方法和onRestoreInstanceState方法“不一定”是成对的被调用的，
     * onRestoreInstanceState被调用的前提是，
     * activity A“确实”被系统销毁了，而如果仅仅是停留在有这种可能性的情况下，
     * 则该方法不会被调用，例如，当正在显示activity A的时候，用户按下HOME键回到主界面，
     * 然后用户紧接着又返回到activity A，这种情况下activity A一般不会因为内存的原因被系统销毁，
     * 故activity A的onRestoreInstanceState方法不会被执行
     */
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.d("HELLO", "HELLO:如果应用进程被系统咔嚓，则再次打开应用的时候会进入");
    }

    /**
     * 判断是否有导航菜单显示
     * @param activity
     * @return
     */
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



    /**
     * 设置沉浸式式状态栏
     *
     * @param view Activity 布局跟布局view
     */
    private  void setImmerseStyle(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
                /*window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);*/
            /**
             * 设置透明状态栏
             */
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            /**
             * 设置透明导航栏
             */
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            int statusBarHeight = WindowInfo.getStatusBarHeight(this.getBaseContext());
            int navigiationBarHeight = WindowInfo.getNavigationBarHeight(this.getBaseContext());
            if(!checkDeviceHasNavigationBar(getApplicationContext())){
                navigiationBarHeight = 0;
            }
            view.setPadding(0, statusBarHeight, 0, navigiationBarHeight);
        }
    }

    /**
     *
     * @param view
     * @param color
     */
    public void setImmerseColor(View view, int color) {
        if (Constants.API >= Build.VERSION_CODES.LOLLIPOP) {
            view.setBackgroundColor(color);

            setImmerseStyle(view);
        } else if (Constants.API >= Build.VERSION_CODES.KITKAT) {
            int alpha = Color.alpha(color);
            int red = Color.red(color);
            int green = Color.green(color);
            int bule = Color.blue(color);
            float degree = 0.80f;
            if (red > 0) {
                red = (int) (red * degree);
            }
            if (green > 0) {
                green = (int) (green * degree);
            }
            if (bule > 0) {
                bule = (int) (bule * degree);
            }
            color = Color.argb(alpha, red, green, bule);
            view.setBackgroundColor(color);
            setImmerseStyle(view);
        }
    }


    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransition(R.anim.right_to_left_in, R.anim.fade_out);

    }

    @Override
    public void finish() {
        super.finish();

        overridePendingTransition(R.anim.fade_out, R.anim.left_to_right_out);
    }
}
