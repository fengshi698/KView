package com.koala.bupt.kview;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by machenike on 2017/3/13.
 */

public class ViewAdapter extends RecyclerView.Adapter<ViewAdapter.MyHolder> {

    private List<ViewBean> mList = new ArrayList<>();
    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view,parent,false));
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        ViewBean bean = mList.get(position);
        holder.tv_name.setText(bean.getName());
        holder.tv_date.setText(bean.getDate());
        holder.tv_des.setText(bean.getDes());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void add(List<ViewBean> list){
        mList.addAll(list);
        notifyDataSetChanged();
    }

    public void add(ViewBean bean){
        mList.add(bean);
        notifyDataSetChanged();
    }

    class MyHolder extends RecyclerView.ViewHolder{

        TextView tv_name;
        TextView tv_des;
        TextView tv_date;
        public MyHolder(View itemView) {
            super(itemView);
            tv_name = (TextView) itemView.findViewById(R.id.tv);
            tv_des = (TextView) itemView.findViewById(R.id.tv_des);
            tv_date = (TextView) itemView.findViewById(R.id.tv_date);
        }
    }
}
