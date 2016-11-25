package com.wjc.jokerwanshoppingmall.type.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.flyco.tablayout.listener.OnTabSelectListener;
import com.wjc.jokerwanshoppingmall.R;
import com.wjc.jokerwanshoppingmall.base.BaseFragment;
import com.wjc.jokerwanshoppingmall.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by ${万嘉诚} on 2016/11/17.
 * WeChat：wjc398556712
 * Function：分类的Fragment
 */

public class TypeFragment extends BaseFragment {


    @Bind(R.id.tl_1)
    com.flyco.tablayout.SegmentTabLayout segmentTabLayout;
    @Bind(R.id.iv_type_search)
    ImageView iv_type_search;
    @Bind(R.id.fl_type)
    FrameLayout fl_type;

    private List<BaseFragment> fragmentList;
    private Fragment fragment;
    public ListFragment listFragment;
    public TagFragment tagFragment;
    private int currentTab;

    @Override
    public int getViewLayoutId() {
        return R.layout.fragment_type;
    }

    @Override
    public void initData() {
        super.initData();

        initFragment();

        String[] titles = {"分类", "标签"};
        segmentTabLayout.setTabData(titles);

        segmentTabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                switchFragment(fragment, fragmentList.get(position));
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
    }

    private void switchFragment(Fragment fromFragment, BaseFragment nextFragment) {
        if (segmentTabLayout != null) {
            currentTab = segmentTabLayout.getCurrentTab();
        }
        if (fragment != nextFragment) {
            fragment = nextFragment;
            if (nextFragment != null) {
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                //判断nextFragment是否添加
                if (!nextFragment.isAdded()) {
                    //隐藏当前Fragment
                    if (fromFragment != null) {
                        transaction.hide(fromFragment);
                    }

                    transaction.add(R.id.fl_container, nextFragment, "tagFragment").commit();
                } else {
                    //隐藏当前Fragment
                    if (fromFragment != null) {
                        transaction.hide(fromFragment);
                    }
                    transaction.show(nextFragment).commit();
                }
            }
        }
    }

    private void initFragment() {
        fragmentList = new ArrayList<>();
        listFragment = new ListFragment();
        tagFragment = new TagFragment();

        fragmentList.add(listFragment);
        fragmentList.add(tagFragment);

        switchFragment(fragment, fragmentList.get(0));
    }

    public void hideFragment() {
        if (listFragment != null && tagFragment != null) {
            LogUtil.e("getActivity()---------------->" + getActivity() );
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            transaction.hide(listFragment).hide(tagFragment).commit();
        }
    }

    public int getCurrentTab() {
        return currentTab;
    }
}
