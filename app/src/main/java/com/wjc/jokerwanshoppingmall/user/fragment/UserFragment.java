package com.wjc.jokerwanshoppingmall.user.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;
import com.wjc.jokerwanshoppingmall.R;
import com.wjc.jokerwanshoppingmall.app.LoginActivity;
import com.wjc.jokerwanshoppingmall.base.BaseFragment;
import com.wjc.jokerwanshoppingmall.user.activity.MessageCenterActivity;
import com.wjc.jokerwanshoppingmall.utils.BitmapUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ${万嘉诚} on 2016/11/17.
 * WeChat：wjc398556712
 * Function：
 */

public class UserFragment extends BaseFragment {

    @Bind(R.id.ib_user_icon_avator)
    ImageButton ibUserIconAvator;
    @Bind(R.id.tv_username)
    TextView tvUsername;
    @Bind(R.id.tv_all_order)
    TextView tvAllOrder;
    @Bind(R.id.tv_user_pay)
    TextView tvUserPay;
    @Bind(R.id.tv_user_receive)
    TextView tvUserReceive;
    @Bind(R.id.tv_user_finish)
    TextView tvUserFinish;
    @Bind(R.id.tv_user_drawback)
    TextView tvUserDrawback;
    @Bind(R.id.tv_user_location)
    TextView tvUserLocation;
    @Bind(R.id.tv_user_collect)
    TextView tvUserCollect;
    @Bind(R.id.tv_user_coupon)
    TextView tvUserCoupon;
    @Bind(R.id.tv_user_score)
    TextView tvUserScore;
    @Bind(R.id.tv_user_prize)
    TextView tvUserPrize;
    @Bind(R.id.tv_user_ticket)
    TextView tvUserTicket;
    @Bind(R.id.tv_user_invitation)
    TextView tvUserInvitation;
    @Bind(R.id.tv_user_callcenter)
    TextView tvUserCallcenter;
    @Bind(R.id.tv_user_feedback)
    TextView tvUserFeedback;
    @Bind(R.id.scrollview)
    ScrollView scrollview;
    @Bind(R.id.tv_usercenter)
    TextView tvUsercenter;
    @Bind(R.id.ib_user_setting)
    ImageButton ibUserSetting;
    @Bind(R.id.ib_user_message)
    ImageButton ibUserMessage;

    @Override
    public int getViewLayoutId() {
        return R.layout.fragment_user;
    }

    @Override
    public void initData() {
        super.initData();
        tvUsercenter.setAlpha(0);

        scrollview.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int[] location = new int[2];
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        break;
                    case MotionEvent.ACTION_MOVE://下滑是正，上滑是负
                        ibUserIconAvator.getLocationOnScreen(location);//初始状态为125,即最大值是125，全部显示不透明是（40？）
                        float i = (location[1] - 40) / 85f;
                        tvUsercenter.setAlpha(1 - i);
                        break;
                }
                return false;
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0) {
            String screen_name = data.getStringExtra("screen_name");
            String profile_image_url = data.getStringExtra("profile_image_url");

            Picasso.with(mContext).load(profile_image_url).transform(new Transformation() {
                @Override
                public Bitmap transform(Bitmap bitmap) {
                    //先对图片进行压缩
//                Bitmap zoom = BitmapUtils.zoom(bitmap, DensityUtil.dip2px(mContext, 62), DensityUtil.dip2px(mContext, 62));
                    Bitmap zoom = BitmapUtils.zoom(bitmap, 90, 90);
                    //对请求回来的Bitmap进行圆形处理
                    Bitmap ciceBitMap = BitmapUtils.circleBitmap(zoom);
                    bitmap.recycle();//必须队更改之前的进行回收
                    return ciceBitMap;
                }

                @Override
                public String key() {
                    return "";
                }
            }).into(ibUserIconAvator);

            tvUsername.setText(screen_name);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.ib_user_icon_avator,R.id.ib_user_setting,R.id.ib_user_message})
    public void onClick(View v){
        if (v == ibUserIconAvator) {
            Intent intent = new Intent(mContext, LoginActivity.class);
            startActivityForResult(intent, 0);

        } else if (v == ibUserSetting) {
            Toast.makeText(mContext, "设置", Toast.LENGTH_SHORT).show();
        } else if (v == ibUserMessage) {
            Intent intent = new Intent(mContext, MessageCenterActivity.class);
            startActivity(intent);
        }
    }
}
