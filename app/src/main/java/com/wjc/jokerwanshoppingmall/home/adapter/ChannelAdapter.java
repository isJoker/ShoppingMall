package com.wjc.jokerwanshoppingmall.home.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wjc.jokerwanshoppingmall.R;
import com.wjc.jokerwanshoppingmall.home.bean.ResultBean;
import com.wjc.jokerwanshoppingmall.utils.Constants;

import java.util.List;

/**
 * Created by ${万嘉诚} on 2016/11/20.
 * WeChat：wjc398556712
 * Function：频道的适配器
 */
public class ChannelAdapter extends BaseAdapter {
    private final Context mContext;
    private final List<ResultBean.ChannelInfoBean> datas;

    public ChannelAdapter(Context mContext, List<ResultBean.ChannelInfoBean> channel_info) {
        this.mContext = mContext;
        this.datas = channel_info;
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null){
            convertView = View.inflate(mContext, R.layout.item_channel,null);
            viewHolder = new ViewHolder();
            viewHolder.iv_icon = (ImageView) convertView.findViewById(R.id.iv_channel);
            viewHolder.tv_title = (TextView) convertView.findViewById(R.id.tv_channel);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        //根据位置得到对应的数据
        ResultBean.ChannelInfoBean channelInfoBean = datas.get(position);
        Glide.with(mContext).load(Constants.BASE_URL_IMAGE+channelInfoBean.getImage()).into(viewHolder.iv_icon );
        viewHolder.tv_title.setText(channelInfoBean.getChannel_name());
        return convertView;
    }

    static class ViewHolder {
        ImageView iv_icon;
        TextView tv_title;
    }
}
