package com.wjc.jokerwanshoppingmall.community.fragment;

import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.wjc.jokerwanshoppingmall.R;
import com.wjc.jokerwanshoppingmall.base.BaseFragment;
import com.wjc.jokerwanshoppingmall.community.adapter.HotPostListViewAdapter;
import com.wjc.jokerwanshoppingmall.community.bean.HotPostBean;
import com.wjc.jokerwanshoppingmall.utils.MyConstants;
import com.wjc.jokerwanshoppingmall.utils.LogUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import butterknife.Bind;
import okhttp3.Call;
import okhttp3.Request;

/**
 * Created by ${万嘉诚} on 2016/11/24.
 * WeChat：wjc398556712
 * Function：
 */

public class HotPostFragment extends BaseFragment {

    @Bind(R.id.lv_hot_post)
    ListView lvHotPost;
    private List<HotPostBean.ResultBean> result;

    @Override
    public int getViewLayoutId() {
        return R.layout.fragment_hot_post;
    }

    @Override
    public void initData() {
        getDataFromNet();
    }

    public void getDataFromNet() {
        OkHttpUtils
                .get()
                .url(MyConstants.HOT_POST_URL)
                .id(100)
                .build()
                .execute(new MyStringCallback());
    }

    public class MyStringCallback extends StringCallback {


        @Override
        public void onBefore(Request request, int id) {
        }

        @Override
        public void onAfter(int id) {
        }

        @Override
        public void onError(Call call, Exception e, int id) {

            LogUtil.e("联网失败——————" + e.getMessage());
        }

        @Override
        public void onResponse(String response, int id) {

            switch (id) {
                case 100:
//                    Toast.makeText(mContext, "http", Toast.LENGTH_SHORT).show();
                    if (response != null) {
                        processData(response);
                        HotPostListViewAdapter adapter = new HotPostListViewAdapter(mContext, result);
                        lvHotPost.setAdapter(adapter);
                    }
                    break;
                case 101:
                    Toast.makeText(mContext, "https", Toast.LENGTH_SHORT).show();
                    break;
            }
        }

    }

    private void processData(String json) {
        Gson gson = new Gson();
        HotPostBean hotPostBean = gson.fromJson(json, HotPostBean.class);
        result = hotPostBean.getResult();
    }
}
