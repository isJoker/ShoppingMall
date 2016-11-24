package com.wjc.jokerwanshoppingmall.community.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.wjc.jokerwanshoppingmall.community.fragment.HotPostFragment;
import com.wjc.jokerwanshoppingmall.community.fragment.NewPostFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ${万嘉诚} on 2016/11/24.
 * WeChat：wjc398556712
 * Function：
 */

public class CommunityViewPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragmentList = new ArrayList<>();
    private String[] titles = new String[]{"新帖", "热帖"};

    public CommunityViewPagerAdapter(FragmentManager fm) {
        super(fm);
        initFragments();
    }

    private void initFragments() {
        NewPostFragment newPostFragment = new NewPostFragment();
        HotPostFragment hotPostFragment = new HotPostFragment();
        fragmentList.add(newPostFragment);
        fragmentList.add(hotPostFragment);
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList != null ? fragmentList.get(position) : null;
    }

    @Override
    public int getCount() {
        return fragmentList == null ? 0 : fragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
