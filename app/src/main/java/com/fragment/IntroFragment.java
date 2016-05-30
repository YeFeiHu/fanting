package com.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fanting.client.R;

/**
 * 引导页碎片
 * Created by YeFeiHu on 2016/3/28.
 */
public class IntroFragment extends Fragment{

    private static final String ARG_LAYOUT_RES_ID = "layoutResId";

    private View rootView;
    private String mTitle;
    private String mInfo;
    private int mColor = -1;
    private int mImageId = -1;

    public static IntroFragment getInstance(int layoutId){
        IntroFragment sampleSlide = new IntroFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_LAYOUT_RES_ID, layoutId);
        sampleSlide.setArguments(args);
        return sampleSlide;
    }


    public IntroFragment setTitle(@NonNull String title) {
        mTitle = title;
        if(title != null && rootView != null){
            TextView textView = (TextView) rootView.findViewById(R.id.tvTitle);
            textView.setText(title);
        }

        return this;
    }

    public IntroFragment setInfo(@NonNull String info) {
        mInfo = info;
        if (info != null && rootView != null) {
            TextView textView = (TextView) rootView.findViewById(R.id.tvInfo);
            textView.setText(info);
        }

        return this;
    }

    public IntroFragment setImageResource(int id) {
        if(id != -1){
            mImageId = id;
        }
        if (rootView != null && id != -1) {
            ImageView imageView = (ImageView) rootView.findViewById(R.id.rivPoster);
            imageView.setImageResource(id);
        }
        return this;
    }


    public IntroFragment setBackgroundColor(int color) {
        if(color != -1) {
            mColor = color;
        }
        if (rootView != null && color != -1) {
            View view = rootView.findViewById(R.id.rlRoot);
            view.setBackgroundColor(color);
        }

        return  this;

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.intro_fragment, container, false);
        setTitle(mTitle);
        setInfo(mInfo);
        setImageResource(mImageId);
        setBackgroundColor(mColor);
        return rootView;
    }

}
