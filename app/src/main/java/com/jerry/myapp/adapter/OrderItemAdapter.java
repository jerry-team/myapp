package com.jerry.myapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.jerry.myapp.R;
import com.jerry.myapp.activity.GoodsDetailActivity;
import com.jerry.myapp.activity.OrderActivity;
import com.jerry.myapp.entity.GoodsEntity;
import com.jerry.myapp.entity.OrderEntity;
import com.jerry.myapp.entity.OrderItemEntity;

import org.w3c.dom.Text;

import java.io.Serializable;
import java.util.List;

public class OrderItemAdapter extends RecyclerView.Adapter<OrderItemAdapter.ViewHolder>{
    private List<OrderItemEntity> orderItemEntityList;
    private Context mContext;
    private String price = "￥";
    private static OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView image;
        TextView name;
        TextView description;
        TextView money;
        TextView num;
        private OrderItemEntity orderItemEntity;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.iv_goods);
            name = itemView.findViewById(R.id.tv_order_name);
            description = itemView.findViewById(R.id.tv_orderItem_des);
            num = itemView.findViewById(R.id.tv_order_num);
            money = itemView.findViewById(R.id.tv_orderItem_money);


//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    mOnItemClickListener.onItemClick((Serializable) orderItemEntity);
//                }
//            });

        }
    }

    public void setDatas(List<OrderItemEntity> orderItemEntityList){
        this.orderItemEntityList = orderItemEntityList;
    }

    public OrderItemAdapter(Context context,List<OrderItemEntity> orderItemEntityList) {
        this.mContext = context;
        this.orderItemEntityList = orderItemEntityList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_order_item,parent,false);
        ViewHolder holder = new ViewHolder(view);

//        holder.itemView.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v){
//                int position = holder.getAdapterPosition();
//                OrderEntity orderEntity = orderEntityList.get(position);
//                Intent in = new Intent(mContext, GoodsDetailActivity.class);
//                Bundle bd = new Bundle();
//                bd.putInt("commodityId",goodsEntity.getId());
//                in.putExtras(bd);
//                mContext.startActivity(in);
////                Toast.makeText(v.getContext(),"you clicked",Toast.LENGTH_SHORT).show();
//            }
//        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        OrderItemEntity orderItemEntity = orderItemEntityList.get(position);
        holder.name.setText(orderItemEntity.getName());
        holder.money.setText(price + orderItemEntity.getPrice());
        holder.description.setText(orderItemEntity.getDescription());
        holder.num.setText("x" + orderItemEntity.getNum());

        RequestOptions requestOptions = new RequestOptions();
        requestOptions
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE);
//        Glide.with(mContext).load("android.resource://com.jerry.myapp/mipmap/"+R.mipmap.cat2).into(holder.goodsImage);
        Glide.with(mContext).load("http://10.0.2.2:8001/commodityImages/"+"cat3.jpg").apply(requestOptions).into(holder.image);
    }

    @Override
    public int getItemCount() {
        if (orderItemEntityList != null && orderItemEntityList.size() > 0) {
            return orderItemEntityList.size();
        } else {
            return 0;
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Serializable obj);
    }

}