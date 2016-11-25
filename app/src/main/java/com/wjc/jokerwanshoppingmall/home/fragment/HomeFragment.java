package com.wjc.jokerwanshoppingmall.home.fragment;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wjc.jokerwanshoppingmall.R;
import com.wjc.jokerwanshoppingmall.base.BaseFragment;
import com.wjc.jokerwanshoppingmall.home.adapter.HomeFragmentAdapter;
import com.wjc.jokerwanshoppingmall.home.bean.ResultBean;
import com.wjc.jokerwanshoppingmall.user.activity.MessageCenterActivity;
import com.wjc.jokerwanshoppingmall.utils.Constants;
import com.wjc.jokerwanshoppingmall.utils.LogUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import okhttp3.Call;

import static com.wjc.jokerwanshoppingmall.R.id.ib_top;
import static com.wjc.jokerwanshoppingmall.R.id.tv_message_home;
import static com.wjc.jokerwanshoppingmall.R.id.tv_search_home;

/**
 * Created by ${万嘉诚} on 2016/11/17.
 * WeChat：wjc398556712
 * Function：
 */

public class HomeFragment extends BaseFragment {


    @Bind(tv_search_home)
    EditText tvSearchHome;
    @Bind(tv_message_home)
    TextView tvMessageHome;
    @Bind(R.id.rv_home)
    RecyclerView rvHome;
    @Bind(ib_top)
    ImageButton ibTop;

    /**
     * 返回的数据
     */
    private ResultBean resultBean;
    private HomeFragmentAdapter adapter;

    @Override
    public int getViewLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public void initData() {
        LogUtil.e("主页数据初始化");
        //请求网络
        getDataFromNet();
    }

    @OnClick({tv_message_home, R.id.ib_top})
    public void onClick(View view) {
        switch (view.getId()) {
            case tv_message_home:
                Toast.makeText(mContext, "进入消息中心", Toast.LENGTH_SHORT).show();
                break;
            case R.id.ib_top:
                //回到顶部
                rvHome.scrollToPosition(0);
                break;
        }
    }

    private void getDataFromNet() {
        OkHttpUtils
                .get()
                .url(Constants.HOME_URL)
                .build()
                .execute(new MyStringCallback());
    }

    public class MyStringCallback extends StringCallback {
        /**
         * 当请求失败的时候回调
         *
         * @param call
         * @param e
         * @param id
         */
        @Override
        public void onError(Call call, Exception e, int id) {

            LogUtil.e("首页请求失败==" + e.getMessage());
        }

        /**
         * 当联网成功的时候回调
         *
         * @param response 请求成功的数据
         * @param id
         */
        @Override
        public void onResponse(String response, int id) {
            LogUtil.e("首页请求成功==" + response);

            if (response != null) {

                processData(response);

                if (resultBean != null) {
                    //有数据
                    //设置适配器
                    adapter = new HomeFragmentAdapter(mContext, resultBean);
                    rvHome.setAdapter(adapter);
                    GridLayoutManager manager = new GridLayoutManager(mContext, 1);// 1 为列数
                    //设置跨度大小监听
                    manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                        @Override
                        public int getSpanSize(int position) {
                            if (position <= 3) {
                                //隐藏
                                ibTop.setVisibility(View.GONE);
                            } else {
                                //显示
                                ibTop.setVisibility(View.VISIBLE);
                            }
                            //只能返回1
                            return 1;
                        }
                    });
                    //设置布局管理者
                    rvHome.setLayoutManager(manager);

                    initListener();

                }

            }

        }
    }

    private void initListener() {
        //置顶的监听
        ibTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rvHome.scrollToPosition(0);
            }
        });

        //搜素的监听
        tvSearchHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "搜索", Toast.LENGTH_SHORT).show();
            }
        });

        //消息的监听
        tvMessageHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, MessageCenterActivity.class);
                mContext.startActivity(intent);
            }
        });

    }


    private void processData(String json) {
        //不用手动解析
//        HomeBean homeBean = JSON.parseObject(json, HomeBean.class);
//        String image = homeBean.getResult().getBanner_info().get(0).getImage();
//        LogUtil.e("image----------------->" + image);

//        使用FastJson手动解析数据

        if (!TextUtils.isEmpty(json)) {
            JSONObject jsonObject = JSON.parseObject(json);
            //得到状态码
            String code = jsonObject.getString("code");
            String msg = jsonObject.getString("msg");
            String result = jsonObject.getString("result");


            //得到resultBean的数据
            JSONObject ResultBean = JSON.parseObject(result, ResultBean.class);
            String banner_info = ResultBean.getString("banner_info");
            String act_info = ResultBean.getString("act_info");
            String channel_info = ResultBean.getString("channel_info");
            String hot_info = ResultBean.getString("hot_info");
            String recommend_info = ResultBean.getString("recommend_info");
            String seckill_info = ResultBean.getString("seckill_info");


            resultBean = new ResultBean();

            //设置BannerInfoBean数据
            List<ResultBean.BannerInfoBean> bannerInfoBeans = JSON.parseArray(banner_info, ResultBean.BannerInfoBean.class);
            resultBean.setBanner_info(bannerInfoBeans);
            String value = jsonObject.getString("value");
            com.wjc.jokerwanshoppingmall.home.bean.ResultBean.BannerInfoBean.ValueBean valueBean = JSON.parseObject(value, com.wjc.jokerwanshoppingmall.home.bean.ResultBean.BannerInfoBean.ValueBean.class);


            //设置actInfoBeans数据
            List<ResultBean.ActInfoBean> actInfoBeans = JSON.parseArray(act_info, ResultBean.ActInfoBean.class);
            resultBean.setAct_info(actInfoBeans);

            //设置channelInfoBeans的数据
            List<ResultBean.ChannelInfoBean> channelInfoBeans = JSON.parseArray(channel_info, ResultBean.ChannelInfoBean.class);
            resultBean.setChannel_info(channelInfoBeans);

            //设置hotInfoBeans的数据
            List<ResultBean.HotInfoBean> hotInfoBeans = JSON.parseArray(hot_info, ResultBean.HotInfoBean.class);
            resultBean.setHot_info(hotInfoBeans);

            //设置recommendInfoBeans的数据
            List<ResultBean.RecommendInfoBean> recommendInfoBeans = JSON.parseArray(recommend_info, ResultBean.RecommendInfoBean.class);
            resultBean.setRecommend_info(recommendInfoBeans);

            //设置seckillInfoBean的数据
            ResultBean.SeckillInfoBean seckillInfoBean = JSON.parseObject(seckill_info, ResultBean.SeckillInfoBean.class);
            resultBean.setSeckill_info(seckillInfoBean);

        }
    }

}
