package com.wjc.jokerwanshoppingmall.app;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.wjc.jokerwanshoppingmall.R;
import com.wjc.jokerwanshoppingmall.base.BaseFragment;
import com.wjc.jokerwanshoppingmall.community.fragment.CommunityFragment;
import com.wjc.jokerwanshoppingmall.home.fragment.HomeFragment;
import com.wjc.jokerwanshoppingmall.shoppingcart.fragment.ShoppingCartFragment;
import com.wjc.jokerwanshoppingmall.type.fragment.TypeFragment;
import com.wjc.jokerwanshoppingmall.user.fragment.UserFragment;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends FragmentActivity {

    @Bind(R.id.fl_container)
    FrameLayout flContainer;
    @Bind(R.id.rb_home)
    RadioButton rbHome;
    @Bind(R.id.rb_type)
    RadioButton rbType;
    @Bind(R.id.rb_community)
    RadioButton rbCommunity;
    @Bind(R.id.rb_cart)
    RadioButton rbCart;
    @Bind(R.id.rb_user)
    RadioButton rbUser;
    @Bind(R.id.rg_main)
    RadioGroup rgMain;

    private ArrayList<BaseFragment> fragments;
    private int position;
    private TypeFragment typeFragment;
    private BaseFragment mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initFragment();

        initListener();
    }

    private void initListener() {
        rgMain.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checked) {
                switch (checked) {
                    case  R.id.rb_home:
                        position = 0;

                        typeFragment.hideFragment();
                        break;
                    case  R.id.rb_type:
                        position = 1;

                        //初始化数据
                        int currentTab = typeFragment.getCurrentTab();
                        if (currentTab == 0) {
                            if (typeFragment.listFragment != null) {
                                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                                transaction.show(typeFragment.listFragment).commit();
                            }
                        } else {
                            if (typeFragment.tagFragment != null) {
                                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                                transaction.show(typeFragment.tagFragment).commit();
                            }
                        }

                        break;
                    case  R.id.rb_community:
                        position = 2;

                        typeFragment.hideFragment();
                        break;
                    case  R.id.rb_cart:
                        position = 3;

                        typeFragment.hideFragment();
                        break;
                    case  R.id.rb_user:
                        position = 4;

                        typeFragment.hideFragment();
                        break;
                }
                BaseFragment baseFragment = getFragment(position);
                switchFragment(mContext, baseFragment);
            }
        });

        //默认选择首页  要放在设置监听的后面
        rgMain.check(R.id.rb_home);

    }

    /**
     *
     * @param position
     * @return
     */
    private BaseFragment getFragment(int position) {
        if (fragments != null && fragments.size() > 0) {
            BaseFragment baseFragment = fragments.get(position);
            return baseFragment;
        }
        return null;
    }

    /**
     * 切换Fragment
     * @param fromFragment
     * @param nextFragment
     */
    private void switchFragment(Fragment fromFragment, BaseFragment nextFragment) {
        if (mContext != nextFragment) {
            mContext = nextFragment;
            if (nextFragment != null) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                //判断nextFragment是否添加
                if (!nextFragment.isAdded()) {
                    //隐藏当前Fragment
                    if (fromFragment != null) {
                        transaction.hide(fromFragment);
                    }
                    //添加下一个Fragment
                    transaction.add(R.id.fl_container, nextFragment).commit();
                } else {
                    //隐藏当前Fragment
                    if (fromFragment != null) {
                        transaction.hide(fromFragment);
                    }
                    //显示下一个Fragment
                    transaction.show(nextFragment).commit();
                }
            }
        }
    }

    private void initFragment() {
        fragments = new ArrayList<>();
        typeFragment = new TypeFragment();
        fragments.add(new HomeFragment());
        fragments.add(typeFragment);
        fragments.add(new CommunityFragment());
        fragments.add(new ShoppingCartFragment());
        fragments.add(new UserFragment());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
