package com.data;

import java.io.Serializable;

/**
 *
 * 欢迎页面数据
 * Created by YeFeiHu on 2016/3/28.
 */
public class WelcomeData implements Serializable{
    private String flag;
    private long time;
    private long lastTime;
    private String savePath;

    public static WelcomeData Build() {
        return new WelcomeData();
    }


    public String getFlag() {
        return flag;
    }

    public WelcomeData setFlag(String flag) {
        this.flag = flag;
        return this;
    }

    public long getTime() {
        return time;
    }

    public WelcomeData setTime(long time) {
        this.time = time;
        return this;
    }

    public long getLastTime() {
        return lastTime;
    }

    public WelcomeData setLastTime(long lastTime) {
        this.lastTime = lastTime;
        return this;
    }

    public String getSavePath() {
        return savePath;
    }

    public WelcomeData setSavePath(String savePath) {
        this.savePath = savePath;
        return this;
    }
}
