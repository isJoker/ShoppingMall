package com.wjc.jokerwanshoppingmall.type.fragment;

import android.util.Log;
import android.widget.GridView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.wjc.jokerwanshoppingmall.R;
import com.wjc.jokerwanshoppingmall.base.BaseFragment;
import com.wjc.jokerwanshoppingmall.type.adapter.TagGridViewAdapter;
import com.wjc.jokerwanshoppingmall.type.bean.TagBean;
import com.wjc.jokerwanshoppingmall.utils.Constants;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import butterknife.Bind;
import okhttp3.Call;
import okhttp3.Request;

import static com.wjc.jokerwanshoppingmall.R.id.gv_tag;

/**
 * Created by ${万嘉诚} on 2016/11/22.
 * WeChat：wjc398556712
 * Function：标签页
 */

public class TagFragment extends BaseFragment {

    @Bind(gv_tag)
    GridView gvTag;
    private TagGridViewAdapter adapter;
    private List<TagBean.ResultBean> result;

    @Override
    public int getViewLayoutId() {
        return R.layout.fragment_tag;
    }

    @Override
    public void initData() {
        getDataFromNet();

    }

    public void getDataFromNet() {
        OkHttpUtils
                .get()
                .url(Constants.TAG_URL)
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
            Log.e("TAG", "联网失败" + e.getMessage());
        }

        @Override
        public void onResponse(String response, int id) {

            switch (id) {
                case 100:
//                    Toast.makeText(mContext, "http", Toast.LENGTH_SHORT).show();
                    if (response != null) {
                        processData(response);
                        adapter = new TagGridViewAdapter(mContext, result);
                        gvTag.setAdapter(adapter);
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
        TagBean tagBean = gson.fromJson(json, TagBean.class);
        result = tagBean.getResult();
    }

}
