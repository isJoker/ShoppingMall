package com.wjc.jokerwanshoppingmall.type.fragment;

import android.widget.GridView;

import com.wjc.jokerwanshoppingmall.R;
import com.wjc.jokerwanshoppingmall.base.BaseFragment;
import com.wjc.jokerwanshoppingmall.type.adapter.TagGridViewAdapter;
import com.wjc.jokerwanshoppingmall.type.bean.TagBean;

import java.util.List;

import butterknife.Bind;

/**
 * Created by ${万嘉诚} on 2016/11/22.
 * WeChat：wjc398556712
 * Function：标签页
 */

public class TagFragment extends BaseFragment {

    @Bind(R.id.gv_tag)
    GridView gvTag;
    private TagGridViewAdapter adapter;
    private List<TagBean.ResultBean> result;

    @Override
    public int getViewLayoutId() {
        return R.layout.fragment_tag;
    }

}
