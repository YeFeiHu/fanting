package com.fanting.client;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;

import com.fragment.RadioFragment;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItem;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;
import com.util.Hint;

import net.simonvt.menudrawer.MenuDrawer;

/**
 * 主页面
 * Created by YeFeiHu on 2016/3/28.
 */
public class MusicActivity extends MyActivity {

    MusicMainMenuManager mMenuManager;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.music_activity);

        mMenuManager = new MusicMainMenuManager(this);


        initToolbar();

        ViewGroup tab = (ViewGroup) findViewById(R.id.tab);

        tab.addView(LayoutInflater.from(this).inflate(R.layout.demo_indicator_trick1, tab, false));


        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);



        FragmentPagerItems pages = new FragmentPagerItems(this);
        String[] titles = new String[]{"电台", "音乐", "论坛"};
        for (String titleResId : titles) {
            pages.add(FragmentPagerItem.of(titleResId, RadioFragment.class));
        }


        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
                getSupportFragmentManager(), pages);

        viewPager.setAdapter(adapter);


        SmartTabLayout viewPagerTab = (SmartTabLayout) findViewById(R.id.viewpagertab);
        mMenuManager.setViewPager(viewPagerTab, viewPager);

    }


    @Override
    public void onBackPressed() {
        final int drawerState = mMenuManager.getMenuDrawer().getDrawerState();
        if (drawerState == MenuDrawer.STATE_OPEN || drawerState == MenuDrawer.STATE_OPENING) {
            mMenuManager.getMenuDrawer().closeMenu();
            return;
        }


        super.onBackPressed();
    }

    private void initToolbar() {
        setImmerseColor(findViewById(R.id.prlRoot), getResources().getColor(R.color.GREEN_PRIMARY));


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        toolbar.setOnMenuItemClickListener(onMenuItemClick);
        setSupportActionBar(toolbar);


        android.support.v7.app.ActionBar bar = getSupportActionBar();
        if (bar != null) {
            bar.setDisplayHomeAsUpEnabled(true);
            bar.setDisplayShowCustomEnabled(true);
            bar.setDefaultDisplayHomeAsUpEnabled(true);
        }
    }


    private Toolbar.OnMenuItemClickListener onMenuItemClick = new Toolbar.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            Hint.showHint(MusicActivity.this, "弹出菜单");
            String msg = "";
            switch (menuItem.getItemId()) {
                case R.id.ab_search:
                    msg += "action_share";
                    break;
            }
            return true;
        }
    };


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.music_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                MenuDrawer menu = mMenuManager.getMenuDrawer();
                int state = menu.getDrawerState();
                if(state == MenuDrawer.STATE_OPENING){
                      menu.closeMenu();
                }else {
                    menu.openMenu();
                }
                break;
        }
        return super.onOptionsItemSelected(item);


    }

    @Override
    public void getMessage(Intent intent) {

    }


    @Override
    public void finish() {
        super.finish();


    }

    private boolean isAwaitAgainBack;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_BACK) {

            if (isAwaitAgainBack) {
                this.finish();
            } else {
                isAwaitAgainBack = true;
                new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        super.handleMessage(msg);
                        isAwaitAgainBack = false;
                    }
                }.sendEmptyMessageDelayed(0, 2000);
            }
        }

        return super.onKeyDown(keyCode, event);
    }
}
