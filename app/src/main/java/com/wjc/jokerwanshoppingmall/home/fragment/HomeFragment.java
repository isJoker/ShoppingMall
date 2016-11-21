package com.wjc.jokerwanshoppingmall.home.fragment;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.wjc.jokerwanshoppingmall.R;
import com.wjc.jokerwanshoppingmall.base.BaseFragment;
import com.wjc.jokerwanshoppingmall.home.adapter.HomeFragmentAdapter;
import com.wjc.jokerwanshoppingmall.home.bean.ResultBeanData;
import com.wjc.jokerwanshoppingmall.utils.Constants;
import com.wjc.jokerwanshoppingmall.utils.LogUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import butterknife.Bind;
import butterknife.OnClick;
import okhttp3.Call;

import static com.wjc.jokerwanshoppingmall.R.id.ib_top;
import static com.zhy.http.okhttp.log.LoggerInterceptor.TAG;

/**
 * Created by ${万嘉诚} on 2016/11/17.
 * WeChat：wjc398556712
 * Function：
 */

public class HomeFragment extends BaseFragment {


    @Bind(R.id.tv_search_home)
    EditText tvSearchHome;
    @Bind(R.id.tv_message_home)
    TextView tvMessageHome;
    @Bind(R.id.rv_home)
    RecyclerView rvHome;
    @Bind(ib_top)
    ImageButton ibTop;

    /**
     * 返回的数据
     */
    private ResultBeanData.ResultBean resultBean;
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

    @OnClick({R.id.tv_message_home,R.id.ib_top})
    public void onClick(View view){
        switch (view.getId()) {
            case R.id.tv_message_home :
                Toast.makeText(mContext, "进入消息中心", Toast.LENGTH_SHORT).show();
                break;
            case R.id.ib_top :
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
         * @param call
         * @param e
         * @param id
         */
        @Override
        public void onError(Call call, Exception e, int id) {

            LogUtil.e("首页请求失败=="+e.getMessage());
        }

        /**
         * 当联网成功的时候回调
         * @param response 请求成功的数据
         * @param id
         */
        @Override
        public void onResponse(String response, int id) {
            LogUtil.e("首页请求成功=="+response);
            //解析数据
            processData(response);
        }
    }


    private void processData(String json) {
        //使用FastJson解析数据
        ResultBeanData resultBeanData = JSON.parseObject(json,ResultBeanData.class);
        resultBean = resultBeanData.getResult();
        if(resultBean != null){
            //有数据
            //设置适配器
            adapter = new HomeFragmentAdapter(mContext,resultBean);
            rvHome.setAdapter(adapter);
            GridLayoutManager manager =  new GridLayoutManager(mContext,1);// 1 为列数
            //设置跨度大小监听
            manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    if(position <= 3){
                        //隐藏
                        ibTop.setVisibility(View.GONE);
                    }else{
                        //显示
                        ibTop.setVisibility(View.VISIBLE);
                    }
                    //只能返回1
                    return 1;
                }
            });
            //设置布局管理者
            rvHome.setLayoutManager(manager);

        }else{
            //没有数据
        }
        Log.e(TAG,"解析成功=="+resultBean.getHot_info().get(0).getName());
    }

}
