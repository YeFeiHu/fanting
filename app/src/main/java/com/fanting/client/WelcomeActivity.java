package com.fanting.client;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.ImageView;

import com.data.AppData;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.util.HttpUtil;
import com.util.ImgUtil;
import com.util.PhoneUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * 欢迎页 第一界面和引导页
 * Created by YeFeiHu on 2016/3/28.
 */
public class WelcomeActivity extends MyActivity {

    private ImageView mIvSpread;
    private Bitmap mBitmap;
    private int mDelay = 2000;
    private static final int MSG_SKIP = 1;

    private String mImagePath;
    private String mRequestImageUrl;

    private static final int MSG_CLOSE = 2;
    private TaskHandler mHandler;
    class TaskHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {
                case MSG_SKIP:
                    toSkip();
                    break;
                case MSG_CLOSE:
                    WelcomeActivity.this.finish();
                    break;
            }
        }
    }


    private void toSkip() {
        int oldVersion = AppData.getVersionCode(this);
        int curVersion = PhoneUtil.getVersion(this.getApplicationContext());
        Intent intent = null;
        if (oldVersion < curVersion) {
            intent = new Intent(this.getApplicationContext(), IntroActivity.class);
        }else {
            intent = new Intent(this.getApplicationContext(), MusicActivity.class);

        }
        this.startActivity(intent);
        //overridePendingTransition(R.anim.right_to_left_in, android.support.design.R.anim.design_fab_out);
        mHandler.sendEmptyMessageDelayed(MSG_CLOSE, 300);
    }

    @Override
    public void finish() {
        super.finish();
        if(mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
            mHandler = null;
        }

    }

    @Override
    protected void onNewIntent(Intent intent) {

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_activity);

        mIvSpread = (ImageView) findViewById(R.id.ivWelcome);

        mImagePath = AppData.getWlcomeImagePath(this.getApplicationContext());

        mBitmap = ImgUtil.getBitmapByPath(mImagePath);
        if (mBitmap != null) {
            mIvSpread.setImageBitmap(mBitmap);
        } else {
            AppData.setCurWelcomeREquestState(this.getApplicationContext(), "");
        }
        mHandler = new TaskHandler();

        mHandler.sendEmptyMessageDelayed(MSG_SKIP, mDelay);

        mRequestImageUrl = AppData.getWelcomeImageUrl(getApplicationContext());
        toLoadImage(mRequestImageUrl);

    }

    private void toLoadImage(String url) {
        try {
            new Thread(new DownLoadMottoThread(this.getApplicationContext(),url, mImagePath)).start();
        } catch (RuntimeException e) {
            return;
        }
    }

    @Override
    public void getMessage(Intent intent) {


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
            mHandler = null;
        }
    }


    /**
     * 下载格言类
     **/
    class DownLoadMottoThread implements Runnable {
        public String requestAddr;
        public String savePath;
        private Context mContext;

        public DownLoadMottoThread(Context context, String requestAddr, String saveAddr) {
            this.requestAddr = requestAddr;
            this.savePath = saveAddr;
            this.mContext = context;
        }

        @Override
        public void run() {
            load();
        }

        public void load() {
            try {

                OkHttpClient client = new OkHttpClient();

                Request request = new Request.Builder().url(requestAddr).build();
                Call call = client.newCall(request);

                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Request request, IOException e) {

                    }

                    @Override
                    public void onResponse(Response response) throws IOException {
                        if (response.isSuccessful()) {
                            String data = response.body().string();
                            JSONObject obj = null;
                            try {
                                obj = new JSONObject(data);
                                String state = obj.getString("state");
                                String flag = obj.getString("flag");
                                String url = obj.getString("url");

                                int stateCode = Integer.valueOf(state);

                                if (stateCode == 0) {
                                    byte[] flush = HttpUtil.getInstance().getContent(url, null);
                                    Bitmap bitmap = ImgUtil.getBitmap(flush);

                                    ImgUtil.saveImageToPath(bitmap, savePath);
                                    AppData.setCurWelcomeREquestState(mContext,flag);
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });

            } catch (Exception e) {
                Log.v("LOG", "WelecomeActivity DownLoadMottoThread 更新格言失败 e: " + e.getMessage());
                return;
            }
        }
    }

}
