package com.wjc.jokerwanshoppingmall.user.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.design.widget.CollapsingToolbarLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;
import com.wjc.jokerwanshoppingmall.R;
import com.wjc.jokerwanshoppingmall.app.LoginActivity;
import com.wjc.jokerwanshoppingmall.base.BaseFragment;
import com.wjc.jokerwanshoppingmall.utils.BitmapUtils;
import com.wjc.jokerwanshoppingmall.utils.LogUtil;
import com.wjc.jokerwanshoppingmall.utils.MyConstants;
import com.wjc.jokerwanshoppingmall.utils.PreferenceUtils;

import butterknife.Bind;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;

/**
 * Created by ${万嘉诚} on 2016/11/17.
 * WeChat：wjc398556712
 * Function：
 */

public class UserFragment extends BaseFragment {

    @Bind(R.id.ib_user_icon_avator)
    ImageView ibUserIconAvator;
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
//    @Bind(R.id.scrollview)
//    ScrollView scrollview;
//    @Bind(R.id.tv_usercenter)
//    TextView tvUsercenter;
//    @Bind(R.id.ib_user_setting)
//    ImageButton ibUserSetting;
//    @Bind(R.id.ib_user_message)
//    ImageButton ibUserMessage;
    @Bind(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsing_toolbar;

    @Override
    public int getViewLayoutId() {
        return R.layout.fragment_user;
    }

    @Override
    public void initData() {
        super.initData();
        //设置头像和用户名
        String imageurl = PreferenceUtils.getString(getActivity(), MyConstants.IMAGE_URL);
        setImageAvator(imageurl);

        String userName = PreferenceUtils.getString(getActivity(), MyConstants.USER_NAME);
        if (!TextUtils.isEmpty(userName)) {
            tvUsername.setText(userName);
        }

        collapsing_toolbar.setTitle("个人中心");

//        tvUsercenter.setAlpha(0);

//        scrollview.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                int[] location = new int[2];
//                switch (event.getAction()) {
//                    case MotionEvent.ACTION_DOWN:
//                        break;
//                    case MotionEvent.ACTION_MOVE://下滑是正，上滑是负
//                        ibUserIconAvator.getLocationOnScreen(location);//初始状态为125,即最大值是125，全部显示不透明是（40？）
//                        float i = (location[1] - 40) / 85f;
////                        tvUsercenter.setAlpha(1 - i);
//                        break;
//                }
//                return false;
//            }
//        });
    }

    /**
     * 设置用户头像
     * @param imageurl
     */
    private void setImageAvator(String imageurl) {
        if(!TextUtils.isEmpty(imageurl)) {
            Picasso.with(mContext).load(imageurl).transform(new Transformation() {
                @Override
                public Bitmap transform(Bitmap bitmap) {
                    //先对图片进行压缩
//                Bitmap zoom = BitmapUtils.zoom(bitmap, DensityUtil.dip2px(mContext, 62), DensityUtil.dip2px(mContext, 62));
                    Bitmap zoom = BitmapUtils.zoom(bitmap, 140, 140);
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
        }
    }

//    @OnClick({R.id.ib_user_icon_avator,R.id.ib_user_setting,R.id.ib_user_message})
@OnClick(R.id.ib_user_icon_avator)
    public void onClick(View v){
        if (v == ibUserIconAvator) {
            Intent intent = new Intent(mContext, LoginActivity.class);
            startActivityForResult(intent,0);

        } /*else if (v == ibUserSetting) {
            Toast.makeText(mContext, "设置", Toast.LENGTH_SHORT).show();
        } else if (v == ibUserMessage) {
            Intent intent = new Intent(mContext, MessageCenterActivity.class);
            startActivity(intent);
        }*/
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        LogUtil.e("data============================>"+data);
        if(data != null) {
            if (requestCode == 0 && resultCode == RESULT_OK) {
                String screen_name = data.getStringExtra("screen_name");
                String profile_image_url = data.getStringExtra("profile_image_url");

                LogUtil.e("screen_name=====>" + screen_name);
                LogUtil.e("profile_image_url======>" + profile_image_url);

                setImageAvator(profile_image_url);
                tvUsername.setText(screen_name);
            }
        }
    }
}
