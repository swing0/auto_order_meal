package com.example.autobook.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.autobook.Bean.Dish;
import com.example.autobook.R;

import org.xutils.x;

import java.util.List;

public class DishAdapter extends BaseAdapter  {
    private List<Dish> data;
    private LayoutInflater layoutInflater;
    private Context context;
    private OnCarClick onCarClick;
    //构造初始化
    public DishAdapter(Context context,List<Dish> data){
        super();
        this.context=context;
        this.data=data;
        this.layoutInflater=LayoutInflater.from(context);
    }

    public void setOnCarClick(OnCarClick onCarClick) {
        this.onCarClick = onCarClick;
    }

    //控件类
    final class zujian{
        public TextView tv_dish_name;
        public TextView tv_dish_price;
        public TextView tv_dish_volume;
        public ImageView tv_dish_img;
        public ImageView add;
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
        DishAdapter.zujian z=null;
        if(convertView==null){
            z=new DishAdapter.zujian();
            convertView=layoutInflater.inflate(R.layout.gridview_item,null);
            z.tv_dish_name=(TextView) convertView.findViewById(R.id.dish_name);
            z.tv_dish_price=(TextView) convertView.findViewById(R.id.dish_price);
            z.tv_dish_volume=(TextView) convertView.findViewById(R.id.dish_volume);
            z.tv_dish_img=(ImageView) convertView.findViewById(R.id.dish_img);
            z.add=(ImageView)convertView.findViewById(R.id.dish_add);
            convertView.setTag(z);
        }else{
            z=(DishAdapter.zujian)convertView.getTag();
        }
        //绑定数据
        z.tv_dish_name.setText(data.get(position).getName());
        z.tv_dish_price.setText(String.valueOf(data.get(position).getPrice()));
        z.tv_dish_volume.setText(String.valueOf(data.get(position).getSales_volume()));
        x.image().bind(z.tv_dish_img,data.get(position).getImage());
        //z.tv_dish_img.setImageBitmap(data.get(position).getImage());
        //x.image().bind(z.tv_dish_img,data.get(position).getImage());
        z.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCarClick.ClickAdd(position);
            }
        });
        return convertView;
    }

    public static interface OnCarClick{
        public void ClickAdd(int position);
    }
}
