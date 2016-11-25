package com.wjc.jokerwanshoppingmall.community.fragment;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageButton;

import com.wjc.jokerwanshoppingmall.R;
import com.wjc.jokerwanshoppingmall.base.BaseFragment;
import com.wjc.jokerwanshoppingmall.community.adapter.CommunityViewPagerAdapter;
import com.wjc.jokerwanshoppingmall.user.activity.MessageCenterActivity;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by ${万嘉诚} on 2016/11/17.
 * WeChat：wjc398556712
 * Function：
 */

public class CommunityFragment extends BaseFragment {

    @Bind(R.id.ib_community_icon)
    ImageButton ibCommunityIcon;
    @Bind(R.id.pager_indicator)
    com.viewpagerindicator.TabPageIndicator pagerIndicator;
    @Bind(R.id.ib_community_message)
    ImageButton ibCommunityMessage;
    @Bind(R.id.view_pager)
    ViewPager viewPager;

    @Override
    public int getViewLayoutId() {
        return R.layout.fragment_community;
    }

    @Override
    public void initData() {
        super.initData();

        CommunityViewPagerAdapter adapter = new CommunityViewPagerAdapter(getFragmentManager());
        viewPager.setAdapter(adapter);
        pagerIndicator.setVisibility(View.VISIBLE);
        pagerIndicator.setViewPager(viewPager);
    }

    @OnClick(R.id.ib_community_message)
    public void message(){
        Intent intent = new Intent(getActivity(), MessageCenterActivity.class);
        startActivity(intent);
    }
}
