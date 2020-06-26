package com.example.autobook.Adapter;

import android.content.Context;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.autobook.Bean.OrderDetails;
import com.example.autobook.Bean.SimpleDish;
import com.example.autobook.R;

import org.xutils.x;
import android.os.Handler;
import java.util.List;

import java.util.logging.LogRecord;

public class OrderAdapter extends BaseExpandableListAdapter {
    private List<OrderDetails> data;
    //private LayoutInflater layoutInflater;
    private Context context;
    //checkbox监听回调接口
    private onCheckChangeListener onclick_checkbox;
    private Handler handler;


    public void setOnclick_checkbox(onCheckChangeListener onclick_checkbox) {
        this.onclick_checkbox = onclick_checkbox;
    }

    public OrderAdapter(List<OrderDetails> data, Context context) {
        this.data = data;
        this.context = context;
        handler = new Handler(){

            @Override
            public void handleMessage(Message msg) {
                notifyDataSetChanged();
                super.handleMessage(msg);
            }
        };
    }
    public void refresh() {
        handler.sendMessage(new Message());
    }

    @Override
    public int getGroupCount() {
        if(data!=null&&data.size()>0){
            return data.size();
        }else {
            return 0;
        }

    }

    @Override
    public int getChildrenCount(int i) {
        if (data.get(i).getSimpleDishes()!= null && data.get(i).getSimpleDishes().size() > 0) {
            return data.get(i).getSimpleDishes().size();
        } else {
            return 0;
        }
    }

    @Override
    public Object getGroup(int i) {
        return data.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return data.get(i).getSimpleDishes().get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(final int i, boolean b, View view, ViewGroup viewGroup) {
        GroupView holder ;
        if (view == null){
            view = LayoutInflater.from(context).inflate(R.layout.group_item,viewGroup,false);
            holder  = new GroupView();
            holder.checkBox = (CheckBox) view.findViewById(R.id.group_check);
            holder.order_price=(TextView)view.findViewById(R.id.order_price);
            holder.order_time=(TextView)view.findViewById(R.id.order_time);
            view.setTag(holder);
        }else{
            holder = (GroupView) view.getTag();
        }
        OrderDetails a=data.get(i);
        holder.checkBox.setChecked(a.isPaid());
        holder.checkBox.setText(a.getRestaurant_name());
        holder.order_price.setText("总计：¥"+String.valueOf(a.getPrice()));
        holder.order_time.setText("下单时间："+a.getDate());
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(onclick_checkbox!=null){
                    onclick_checkbox.onGroupClick(i);
                }
            }
        });
        return view;
    }
    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        ChildView holder = null;
        if (view == null){
            view = LayoutInflater.from(context).inflate(R.layout.referdish_item,viewGroup,false);
            holder = new ChildView();
            holder.checkBoxchild = (CheckBox) view.findViewById(R.id.child_check);
            holder.imageView = (ImageView) view.findViewById(R.id.child_img);
            holder.textView_price = (TextView) view.findViewById(R.id.child_price);
            holder.textView_name = (TextView) view.findViewById(R.id.child_name);
            view.setTag(holder);
        }else{
            holder = (ChildView) view.getTag();
        }
        SimpleDish a= data.get(i).getSimpleDishes().get(i1);
        holder.checkBoxchild.setChecked(true);
        holder.checkBoxchild.setClickable(false);
        //holder.imageView.setImageResource(R.mipmap.ic_launcher);
        //使用xutil3加载网络图片
        x.image().bind(holder.imageView,a.getImage());
        holder.textView_price.setText(String.valueOf(a.getPrice()));
        holder.textView_name.setText(a.getName());
        return view;
    }
    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }

    class GroupView{
        CheckBox checkBox;
        TextView order_price,order_time;
    }
    class ChildView{
        CheckBox checkBoxchild;
        ImageView imageView;
        TextView textView_price;
        TextView textView_name;
    }
    //监听多选框点击事件回调接口。
    public interface onCheckChangeListener{
        void onGroupClick(int groupID);
    }
}
