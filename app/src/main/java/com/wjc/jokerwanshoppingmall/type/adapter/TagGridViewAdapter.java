package com.wjc.jokerwanshoppingmall.type.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wjc.jokerwanshoppingmall.R;
import com.wjc.jokerwanshoppingmall.type.bean.TagBean;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ${万嘉诚} on 2016/11/22.
 * WeChat：wjc398556712
 * Function：标签页适配器
 */

public class TagGridViewAdapter extends BaseAdapter {

    private Context mContext;
    private List<TagBean.ResultBean> result;
    private int[] colors = {Color.parseColor("#f0a420"), Color.parseColor("#4ba5e2"), Color.parseColor("#f0839a"),
            Color.parseColor("#4ba5e2"), Color.parseColor("#f0839a"), Color.parseColor("#f0a420"),
            Color.parseColor("#f0839a"), Color.parseColor("#f0a420"), Color.parseColor("#4ba5e2")
    };

    public TagGridViewAdapter(Context mContext, List<TagBean.ResultBean> result) {
        this.mContext = mContext;
        this.result = result;
    }

    @Override
    public int getCount() {
        return result != null ?result.size() : 0;
    }

    @Override
    public Object getItem(int i) {
        return result != null ? result.get(i) : null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            view = View.inflate(mContext, R.layout.item_tab_gridview, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        TagBean.ResultBean resultBean = result.get(i);
        holder.tvTag.setText(resultBean.getName());
        holder.tvTag.setTextColor(colors[i % colors.length]);

        return view;
    }

    class ViewHolder {
        @Bind(R.id.tv_tag)
        TextView tvTag;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
