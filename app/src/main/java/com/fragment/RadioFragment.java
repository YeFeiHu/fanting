package com.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fanting.client.R;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItem;

/**
 * 电台主页面
 */
public class RadioFragment extends Fragment {

    private UltimateRecyclerView mUrvRecyclerView;
    private View mHeadView;
    private View mFooterView;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.radio_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        int position = FragmentPagerItem.getPosition(getArguments());
        mUrvRecyclerView = (UltimateRecyclerView) view.findViewById(R.id.rvContent);

        mFooterView = LayoutInflater.from(getContext().getApplicationContext()).inflate(R.layout.head_flush_view,null);

    }

}
