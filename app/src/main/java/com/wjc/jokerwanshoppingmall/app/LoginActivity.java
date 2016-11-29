package com.wjc.jokerwanshoppingmall.app;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;
import com.tencent.connect.UserInfo;
import com.tencent.connect.common.Constants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import com.wjc.jokerwanshoppingmall.R;
import com.wjc.jokerwanshoppingmall.utils.BitmapUtils;
import com.wjc.jokerwanshoppingmall.utils.LogUtil;
import com.wjc.jokerwanshoppingmall.utils.MyConstants;
import com.wjc.jokerwanshoppingmall.utils.PreferenceUtils;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.wjc.jokerwanshoppingmall.R.id.ib_login_visible;


public class LoginActivity extends Activity {


    @Bind(R.id.ib_login_back)//返回键
    ImageButton ibLoginBack;
    @Bind(R.id.iv_avator)//登录头像
    ImageView ivAvator;
    @Bind(R.id.et_login_phone)//登录账号
    EditText etLoginPhone;
    @Bind(R.id.et_login_pwd)//登录密码
    EditText etLoginPwd;
    @Bind(ib_login_visible)//密码是否可见
    ImageButton ibLoginVisible;
    @Bind(R.id.btn_login)//登录键
    Button btnLogin;
    @Bind(R.id.tv_login_register)
    TextView tvLoginRegister;
    @Bind(R.id.tv_login_forget_pwd)
    TextView tvLoginForgetPwd;
    @Bind(R.id.ib_weibo)
    ImageButton ibWeibo;//微博登录
    @Bind(R.id.ib_qq)
    ImageButton ibQq;//QQ登录
    @Bind(R.id.ib_wechat)
    ImageButton ibWechat;//微信登录

    //腾讯的
    private static Tencent mTencent;
    private UserInfo mInfo = null;

    //用户头像
    private String nickname = "";//昵称或登录账号
    private String imgurl = "";//用户头像的url
    private int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        initData();

    }

    private void initData() {

        String userName = PreferenceUtils.getString(LoginActivity.this, MyConstants.USER_NAME);
        String userPassword = PreferenceUtils.getString(LoginActivity.this, MyConstants.USER_PASSWORD);
        String imageAvatorUrl = PreferenceUtils.getString(LoginActivity.this, MyConstants.IMAGE_URL);

        if (!TextUtils.isEmpty(userName)) {
            etLoginPhone.setText(userName);
        }
        if (!TextUtils.isEmpty(userPassword)) {
            etLoginPwd.setText(userPassword);
        }
        if (!TextUtils.isEmpty(imageAvatorUrl)) {
            setAvator(imageAvatorUrl);
        }

        //QQ的初始化
        mTencent = Tencent.createInstance("1105704769", this.getApplicationContext());
        mInfo = new UserInfo(this, mTencent.getQQToken());
    }

    @OnClick(R.id.ib_qq)
    void qq_login_click() {
        mTencent.login(this, "all", loginListener);
    }

    @OnClick(R.id.ib_login_back)
    void ib_login_back() {
        finish();
    }

    @OnClick(R.id.btn_login)
    void btn_login() {
        //去服务器登录
        finish();
    }

    @OnClick(ib_login_visible)
    void ib_login_visible() {
        count++;
        if (count % 2 == 0) {
            ibLoginVisible.setBackgroundResource(R.drawable.new_password_drawable_invisible);
            etLoginPwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        } else {
            ibLoginVisible.setBackgroundResource(R.drawable.new_password_drawable_visible);
            etLoginPwd.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);

        }
    }

    IUiListener loginListener = new BaseUiListener() {
        @Override
        protected void doComplete(JSONObject values) {

            initOpenidAndToken(values);

            //下面的这个必须放到这个地方，要不然就会出错
            updateUserInfo();

        }
    };

    private void updateUserInfo() {
        if (mTencent != null && mTencent.isSessionValid()) {
            IUiListener listener = new IUiListener() {

                @Override
                public void onError(UiError e) {

                }

                @Override
                public void onComplete(final Object response) {
                    Message msg = new Message();
                    msg.obj = response;
                    msg.what = 0;
                    mHandler.sendMessage(msg);

                }

                @Override
                public void onCancel() {

                }
            };

            mInfo = new UserInfo(this, mTencent.getQQToken());
            mInfo.getUserInfo(listener);

        } else {

        }
    }

    Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {

            JSONObject response = (JSONObject) msg.obj;

            try {
                LogUtil.e("response==========" + response);
                imgurl = response.getString("figureurl_qq_2");
                nickname = response.getString("nickname");
                String password = "123456";

                //设置 用户名 密码
                setAvator(imgurl);
                etLoginPhone.setText(nickname);
                etLoginPwd.setText(password);
                //保存
                PreferenceUtils.putString(LoginActivity.this,
                        MyConstants.USER_NAME, nickname);
                PreferenceUtils.putString(LoginActivity.this,
                        MyConstants.USER_PASSWORD, "123456");
                PreferenceUtils.putString(LoginActivity.this,
                        MyConstants.IMAGE_URL, imgurl);

                //设置返回的结果
                Intent intent = getIntent();
                intent.putExtra("screen_name", nickname);
                intent.putExtra("profile_image_url", imgurl);
                setResult(RESULT_OK, intent);

                LogUtil.e("nickname=========" + nickname);

                finish();

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

    private void setAvator(String url) {
        Picasso.with(LoginActivity.this).load(url).transform(new Transformation() {
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
        }).into(ivAvator);
    }


    public static void initOpenidAndToken(JSONObject jsonObject) {
        try {
            String token = jsonObject.getString(Constants.PARAM_ACCESS_TOKEN);
            String expires = jsonObject.getString(Constants.PARAM_EXPIRES_IN);
            String openId = jsonObject.getString(Constants.PARAM_OPEN_ID);
            if (!TextUtils.isEmpty(token) && !TextUtils.isEmpty(expires)
                    && !TextUtils.isEmpty(openId)) {
                mTencent.setAccessToken(token, expires);
                mTencent.setOpenId(openId);
            }
        } catch (Exception e) {
        }
    }

    private class BaseUiListener implements IUiListener {

        @Override
        public void onComplete(Object response) {
            if (null == response) {
                return;
            }
            JSONObject jsonResponse = (JSONObject) response;
            if (null != jsonResponse && jsonResponse.length() == 0) {
                return;
            }
            doComplete((JSONObject) response);
        }

        @Override
        public void onError(UiError e) {

        }

        @Override
        public void onCancel() {

        }

        protected void doComplete(JSONObject values) {

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("ruolan", "-->onActivityResult " + requestCode + " resultCode=" + resultCode);
        if (requestCode == Constants.REQUEST_LOGIN ||
                requestCode == Constants.REQUEST_APPBAR) {
            Tencent.onActivityResultData(requestCode, resultCode, data, loginListener);
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

}
