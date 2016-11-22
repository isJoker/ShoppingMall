package com.wjc.jokerwanshoppingmall.type.fragment;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.wjc.jokerwanshoppingmall.R;
import com.wjc.jokerwanshoppingmall.base.BaseFragment;
import com.wjc.jokerwanshoppingmall.type.adapter.TypeLeftAdapter;
import com.wjc.jokerwanshoppingmall.type.adapter.TypeRightAdapter;
import com.wjc.jokerwanshoppingmall.type.bean.TypeBean;
import com.wjc.jokerwanshoppingmall.utils.Constants;
import com.wjc.jokerwanshoppingmall.utils.LogUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import butterknife.Bind;
import okhttp3.Call;
import okhttp3.Request;

import static com.wjc.jokerwanshoppingmall.R.id.lv_left;
import static com.wjc.jokerwanshoppingmall.R.id.rv_right;

/**
 * Created by ${万嘉诚} on 2016/11/21.
 * WeChat：wjc398556712
 * Function：分类界面
 */

public class ListFragment extends BaseFragment {

    @Bind(lv_left)
    ListView lvLeft;
    @Bind(rv_right)
    RecyclerView rvRight;
    private String[] urls = new String[]{Constants.SKIRT_URL, Constants.JACKET_URL, Constants.PANTS_URL, Constants.OVERCOAT_URL,
            Constants.ACCESSORY_URL, Constants.BAG_URL, Constants.DRESS_UP_URL, Constants.HOME_PRODUCTS_URL, Constants.STATIONERY_URL,
            Constants.DIGIT_URL, Constants.GAME_URL};

    private boolean isFirst = true;
    private List<TypeBean.ResultBean> result;
    private TypeLeftAdapter leftAdapter;

    @Override
    public int getViewLayoutId() {
        return R.layout.fragment_list;
    }

    @Override
    public void initData() {
        super.initData();
        getDataFromNet(urls[0]);
    }

    public void getDataFromNet(String url) {
        OkHttpUtils
                .get()
                .url(url)
                .id(100)
                .build()
                .execute(new MyStringCallback());
    }

    public class MyStringCallback extends StringCallback {

        @Override
        public void onAfter(int id) {
            super.onAfter(id);
        }

        @Override
        public void onBefore(Request request, int id) {
            super.onBefore(request, id);
        }

        @Override
        public void onError(Call call, Exception e, int id) {
            LogUtil.e("联网失败" + e.getMessage());
        }

        @Override
        public void onResponse(String response, int id) {
            switch (id) {
                case 100:
//                    Toast.makeText(mContext, "http", Toast.LENGTH_SHORT).show();
                    if (response != null) {
                        processData(response);//得到右边json数据result
                        if (isFirst) {
                            leftAdapter = new TypeLeftAdapter(mContext);
                            lvLeft.setAdapter(leftAdapter);
                        }
                        initListener(leftAdapter);

                        //解析右边数据
                        TypeRightAdapter rightAdapter = new TypeRightAdapter(mContext, result);
                        rvRight.setAdapter(rightAdapter);

                        GridLayoutManager manager = new GridLayoutManager(getActivity(), 3);//设置GridView为三列

                        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {//？
                            @Override
                            public int getSpanSize(int position) {
                                if (position == 0) {
                                    return 3;
                                } else {
                                    return 1;
                                }
                            }
                        });
                        rvRight.setLayoutManager(manager);
                    }

                    break;
                case 101:
                    Toast.makeText(mContext, "https", Toast.LENGTH_SHORT).show();
                    break;
            }

        }
    }

    private void initListener(final TypeLeftAdapter leftAdapter) {
        //点击监听
        lvLeft.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                leftAdapter.changeSelected(position);//刷新
                if (position != 0) {
                    isFirst = false;
                }
                getDataFromNet(urls[position]);
                leftAdapter.notifyDataSetChanged();
            }
        });

        //选中监听
        lvLeft.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                leftAdapter.changeSelected(position);//刷新
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void processData(String json) {
        Gson gson = new Gson();
        TypeBean typeBean = gson.fromJson(json, TypeBean.class);
        result = typeBean.getResult();
    }
}
