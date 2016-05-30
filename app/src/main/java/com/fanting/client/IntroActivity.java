package com.fanting.client;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;

import com.data.AppData;
import com.github.paolorotolo.appintro.AppIntro2;
import com.github.paolorotolo.appintro.AppIntroFragment;
import com.util.PhoneUtil;

/**
 * 引导页和欢迎页
 * Created by YeFeiHu on 2016/3/28.
 */
public class IntroActivity extends AppIntro2{



    public static final int MSG_CLOSE = 2;
    private TaskHandler mHandler;
    class TaskHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {
                case MSG_CLOSE:
                    IntroActivity.this.finish();
                    break;
            }
        }
    }


    @Override
    public void init(@Nullable Bundle savedInstanceState) {
              initIntro();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void initIntro() {
        Resources rs = getResources();

        String[] titles = new String[]{"好用得到","好用得到1","好用得到2"};
        String[] infos = new String[]{"好用得到好用得到好用得到好用得到","好用得到好用得到好用得到好用得到1","好用得到好用得到好用得到好用得到2"};
        int[] rsIds = new int[]{R.drawable.intro_one,R.drawable.intro_one,R.drawable.intro_one};


        mHandler = new TaskHandler();

        int[] bgColors = new int[]{
                rs.getColor(R.color.GREEN),
                rs.getColor(R.color.RED),
                rs.getColor(R.color.BULE)
        };

        addSlide(AppIntroFragment.newInstance(titles[0],infos[0],rsIds[0],bgColors[0]));
        addSlide(AppIntroFragment.newInstance(titles[1],infos[1],rsIds[1],bgColors[1]));
        addSlide(AppIntroFragment.newInstance(titles[2],infos[2],rsIds[2],bgColors[2]));
        setFlowAnimation();
        setProgressButtonEnabled(true);

    }

    @Override
    public void onDonePressed() {

        AppData.setVersionCode(this.getApplicationContext(), PhoneUtil.getVersion(getApplicationContext()));
        Intent intent = new Intent(getApplicationContext(), MusicActivity.class);
        this.startActivity(intent);
        overridePendingTransition(R.anim.right_to_left_in, android.support.design.R.anim.design_fab_out);


        mHandler.sendEmptyMessageDelayed(MSG_CLOSE, 300);


    }

    @Override
    public void onNextPressed() {

    }

    @Override
    public void onSlideChanged() {

    }




}
