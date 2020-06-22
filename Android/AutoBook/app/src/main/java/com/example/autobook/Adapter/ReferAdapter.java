package com.example.autobook.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.autobook.Bean.Refer_Dish;
import com.example.autobook.R;
import java.util.List;

public class ReferAdapter extends BaseAdapter {
    Context context;
    List<Refer_Dish> list;
    private oncheck check;

    public void setCheck(oncheck check) {
        this.check = check;
    }

    public ReferAdapter(Context context, List<Refer_Dish> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }
    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ChildHolder childHolder=null;
        if(view==null){
            view= LayoutInflater.from(context).inflate(R.layout.referdish_item,viewGroup,false);
            childHolder=new ChildHolder();
            childHolder.checkBox=(CheckBox)view.findViewById(R.id.child_check);
            childHolder.imageView=(ImageView)view.findViewById(R.id.child_img);
            childHolder.name=(TextView)view.findViewById(R.id.child_name);
            childHolder.price=(TextView)view.findViewById(R.id.child_price);
            view.setTag(childHolder);
        }else {
            childHolder=(ChildHolder)view.getTag();
        }
        childHolder.checkBox.setChecked(list.get(i).isCheck());
        childHolder.imageView.setImageBitmap(list.get(i).getImage());
        childHolder.name.setText(list.get(i).getName());
        childHolder.price.setText(String.valueOf(list.get(i).getPrice()));
        childHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(check!=null){
                    check.Checkedchanged(i,b);
                }
            }
        });
        return view;
    }
    public static interface oncheck{
        public void Checkedchanged(int position,boolean ischecked);
    }

    //子项控件件类
    class ChildHolder{
        CheckBox checkBox;
        ImageView imageView;
        TextView name,price;
    }


}
