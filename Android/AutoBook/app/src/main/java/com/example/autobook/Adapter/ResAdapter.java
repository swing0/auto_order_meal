package com.example.autobook.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.autobook.Bean.Restaurant;
import com.example.autobook.R;

import java.util.List;


public class ResAdapter extends BaseAdapter {
    private List<Restaurant> data;
    private LayoutInflater layoutInflater;
    private Context context;
    public ResAdapter(Context context,List<Restaurant> data){
        this.context=context;
        this.data=data;
        this.layoutInflater=LayoutInflater.from(context);
    }
    final class zujian{
        public TextView tv_re_name;
        public TextView tv_re_address;
        public TextView tv_re_score;
        public TextView tv_re_class;
    }


    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        zujian z=null;
        if(convertView==null){
            z=new zujian();
            convertView=layoutInflater.inflate(R.layout.mainlist_item,null);
            z.tv_re_name=(TextView) convertView.findViewById(R.id.re_name);
            z.tv_re_address=(TextView) convertView.findViewById(R.id.re_address);
            z.tv_re_score=(TextView) convertView.findViewById(R.id.re_score);
            z.tv_re_class=(TextView) convertView.findViewById(R.id.re_class);
            convertView.setTag(z);
        }else{
            z=(zujian)convertView.getTag();
        }
        //绑定数据
        z.tv_re_name.setText(data.get(position).getName());
        z.tv_re_address.setText(data.get(position).getAddress());
        z.tv_re_score.setText(String.format("%.1f",data.get(position).getAva()));
        z.tv_re_class.setText(data.get(position).getClassification());
        return convertView;
    }
}
