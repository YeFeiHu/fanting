package com.fanting.client;

import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;

import com.ogaclejapan.smarttablayout.SmartTabLayout;

import net.simonvt.menudrawer.MenuDrawer;

/**
 * 音乐播放类侧滑菜单管理
 * Created by YeFeiHu on 2016/3/29.
 */
public class MusicMainMenuManager {

    private MusicActivity mActivity;
    private SmartTabLayout tabLayout;
    private MenuDrawer mMenuDrawer;
    private SmartTabLayout viewPagerTab;

    public MusicMainMenuManager(MusicActivity activity) {
        mActivity = activity;
        mMenuDrawer = MenuDrawer.attach(activity, MenuDrawer.Type.BEHIND);
        mMenuDrawer.setContentView(R.layout.music_activity);

        setTouchMode(0);

        mMenuDrawer.setSlideDrawable(R.drawable.ic_drawer);

        mMenuDrawer.setDrawerIndicatorEnabled(false);
    }


    public MenuDrawer getMenuDrawer() {

        return mMenuDrawer;
    }

    public void setViewPager(SmartTabLayout tabLayout, ViewPager viewPager) {
        viewPagerTab = tabLayout;
        viewPagerTab.setViewPager(viewPager);

        viewPagerTab.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                View tab = viewPagerTab.getTabAt(position);
                   setTouchMode(position);

            }
        });
    }

    /**
     * 设置触摸模式
     *
     * @param pagePosition
     */
    public void setTouchMode(int pagePosition) {
        Log.e("LOG","点击位置"+pagePosition);
        if (pagePosition == 0) {
            mMenuDrawer.setTouchMode(MenuDrawer.TOUCH_MODE_FULLSCREEN);
        } else {
            mMenuDrawer.setTouchMode(MenuDrawer.TOUCH_MODE_NONE);
        }
    }


}
