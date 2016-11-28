package com.wjc.jokerwanshoppingmall.community.fragment;

import android.widget.ListView;

import com.google.gson.Gson;
import com.wjc.jokerwanshoppingmall.R;
import com.wjc.jokerwanshoppingmall.base.BaseFragment;
import com.wjc.jokerwanshoppingmall.community.adapter.NewPostListViewAdapter;
import com.wjc.jokerwanshoppingmall.community.bean.NewPostBean;
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

public class NewPostFragment extends BaseFragment {

    @Bind(R.id.lv_new_post)
    ListView lvNewPost;
    private List<NewPostBean.ResultBean> result;

    @Override
    public int getViewLayoutId() {
        return R.layout.fragment_new_post;
    }

    @Override
    public void initData() {
        super.initData();
        getDataFromNet();
    }

    private void getDataFromNet() {
        OkHttpUtils
                .get()
                .url(MyConstants.NEW_POST_URL)
                .id(100)
                .build()
                .execute(new MyStringCallback());
    }

    public class MyStringCallback extends StringCallback {

        @Override
        public void onBefore(Request request, int id) {
            super.onBefore(request, id);
        }

        @Override
        public void onAfter(int id) {
            super.onAfter(id);
        }

        @Override
        public void onError(Call call, Exception e, int id) {
            LogUtil.e("联网失败————————" + e.getMessage());
        }

        @Override
        public void onResponse(String response, int id) {
            switch (id) {
                case 100 :
                    LogUtil.e("http请求");
                    if (response != null) {
                        processData(response);
                        NewPostListViewAdapter adapter = new NewPostListViewAdapter(mContext, result);
                        lvNewPost.setAdapter(adapter);
                    }
                    break;
                case 101 :
                    LogUtil.e("https请求");

                    break;
            }
        }
    }

    private void processData(String json) {
        Gson gson = new Gson();
        NewPostBean newPostBean = gson.fromJson(json, NewPostBean.class);
        result = newPostBean.getResult();
    }
}
