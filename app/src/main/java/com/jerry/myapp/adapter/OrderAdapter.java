package com.jerry.myapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.huawei.hms.hwid.B;
import com.jerry.myapp.R;
import com.jerry.myapp.activity.GoodsDetailActivity;
import com.jerry.myapp.activity.OrderActivity;
import com.jerry.myapp.activity.OrderReturnActivity;
import com.jerry.myapp.activity.ScoreActivity;
import com.jerry.myapp.api.Api;
import com.jerry.myapp.api.ApiConfig;
import com.jerry.myapp.api.TtitCallback;
import com.jerry.myapp.entity.GoodsEntity;
import com.jerry.myapp.entity.OrderEntity;
import com.jerry.myapp.entity.Result;

import org.w3c.dom.Text;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder>{
    private List<OrderEntity> orderEntityList;
    private Context mContext;
    private String price = "￥";
    private static OnItemClickListener mOnItemClickListener;
    private OrderItemAdapter orderItemAdapter;
    private AlertDialog dialog;//弹窗

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView shopName;
        TextView cancelTitle;
        RecyclerView itemRecyclerView;
        TextView totalMoney;
        Button bt1,bt2,bt3,bt4,bt5,bt6;
        TextView finish;
        TextView cancelOrder;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            shopName= (TextView) itemView.findViewById(R.id.tv_shop);
            cancelTitle = (TextView) itemView.findViewById(R.id.tv_cancel);
            itemRecyclerView = (RecyclerView) itemView.findViewById(R.id.rcl_2);
            totalMoney = (TextView) itemView.findViewById(R.id.tv_money);
            cancelOrder = (TextView) itemView.findViewById(R.id.tv_cancel_order);
            bt1 = itemView.findViewById(R.id.bt_1);
            bt2 = itemView.findViewById(R.id.bt_2);
            bt3 = itemView.findViewById(R.id.bt_3);
            bt4 = itemView.findViewById(R.id.bt_4);
            bt5 = itemView.findViewById(R.id.bt_5);
            bt6 = itemView.findViewById(R.id.bt_6);
            finish = itemView.findViewById(R.id.tv_order_finish);

//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    mOnItemClickListener.onItemClick(OrderEntity);
//                }
//            });

        }
    }

    public void setDatas(List<OrderEntity> orderEntityList){
        this.orderEntityList = orderEntityList;
    }

    public OrderAdapter(Context context,List<OrderEntity> orderEntityList) {
        this.orderEntityList = orderEntityList;
        this.mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_order,parent,false);
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
    public void onBindViewHolder(@NonNull ViewHolder holder,int position) {
        OrderEntity orderEntity = orderEntityList.get(position);
        holder.shopName.setText(orderEntity.getShopName());
        holder.totalMoney.setText("实付款 " + "￥" + orderEntity.getTotalAmount());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext,RecyclerView.VERTICAL,false);
        holder.itemRecyclerView.setLayoutManager(linearLayoutManager);
        orderItemAdapter = new OrderItemAdapter(mContext,orderEntity.getOrderItemList());
        holder.itemRecyclerView.setAdapter(orderItemAdapter);
        switch(orderEntity.getStatus()){
            case 0:
                holder.cancelTitle.setText("12:00:00后取消");
                holder.cancelTitle.setVisibility(View.VISIBLE);
                holder.bt1.setVisibility(View.VISIBLE);
                holder.bt5.setVisibility(View.VISIBLE);
                break;
            case 1:
                holder.bt4.setVisibility(View.VISIBLE);
                holder.bt5.setVisibility(View.VISIBLE);
                break;
            case 2:
                holder.bt2.setVisibility(View.VISIBLE);
                holder.bt6.setVisibility(View.VISIBLE);
                break;
            case 3:
                holder.bt3.setVisibility(View.VISIBLE);
                holder.bt6.setVisibility(View.VISIBLE);
                break;
            case 4:
                holder.finish.setText("已完成");
                holder.finish.setVisibility(View.VISIBLE);
                break;
            case -1:
                holder.cancelOrder.setText("订单已取消");
                holder.cancelOrder.setVisibility(View.VISIBLE);
                break;
            case -2:
                holder.cancelOrder.setText("退单审核中........");
                holder.cancelOrder.setVisibility(View.VISIBLE);
                break;
            case -3:
                holder.cancelOrder.setText("商家已退单");
                holder.cancelOrder.setVisibility(View.VISIBLE);
                break;
            case -4:
                holder.cancelOrder.setText("管理员已退单");
                holder.cancelOrder.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }
        holder.totalMoney.setText(price + orderEntity.getTotalAmount());

        holder.bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new AlertDialog.Builder(mContext)
                        .setMessage("确定支付吗?")
                        .setPositiveButton("确定", (dialog, which) -> payOrder(position))
                        .setNegativeButton("取消", (dialog, which) -> dialog.dismiss())
                        .create();
                dialog.show();
            }
        });

        holder.bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new AlertDialog.Builder(mContext)
                        .setMessage("确认收货吗?")
                        .setPositiveButton("确定", (dialog, which) -> receiveOrder(position))
                        .setNegativeButton("取消", (dialog, which) -> dialog.dismiss())
                        .create();
                dialog.show();
            }
        });

        holder.bt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orderEntityList.get(position).setStatus(-2);
                notifyDataSetChanged();
                Intent in = new Intent(mContext, ScoreActivity.class);
                Bundle bd = new Bundle();
                bd.putInt("orderId",orderEntity.getId().intValue());
                in.putExtras(bd);
                mContext.startActivity(in);
            }
        });

        holder.bt4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext,"正在发货中.....",Toast.LENGTH_SHORT).show();
            }
        });

        holder.bt5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //弹窗
                dialog = new AlertDialog.Builder(mContext)
                        .setMessage("确定要取消订单吗?")
                        .setPositiveButton("确定", (dialog, which) -> cancelOrder(position))
                        .setNegativeButton("取消", (dialog, which) -> dialog.dismiss())
                        .create();
                dialog.show();
            }
        });

        holder.bt6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(mContext, OrderReturnActivity.class);
                Bundle bd = new Bundle();
                bd.putInt("orderId",orderEntity.getId().intValue());
                in.putExtras(bd);
                mContext.startActivity(in);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (orderEntityList != null && orderEntityList.size() > 0) {
            return orderEntityList.size();
        } else {
            return 0;
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Serializable obj);
    }

    //取消订单
    public void cancelOrder(Integer position){
        Gson gson = new Gson();
        OrderEntity item = orderEntityList.get(position);
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("orderId",item.getId());
        Api.config(ApiConfig.CANCELORDER, params).postRequest(mContext,new TtitCallback() {
            @Override
            public void onSuccess(final String res) {
                ((Activity)mContext).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Result rs = gson.fromJson(res, Result.class);
                        if(rs.getCode() == 200){
                            item.setStatus(-1);
                            notifyDataSetChanged();
                        }
                        Toast.makeText(mContext, rs.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onFailure(Exception e) {}
        });
    }

    //支付订单
    public void payOrder(Integer position){
        OrderEntity item = orderEntityList.get(position);
        Gson gson = new Gson();
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("orderId",item.getId());
        Api.config(ApiConfig.PAYORDER, params).postRequest(mContext,new TtitCallback() {
            @Override
            public void onSuccess(final String res) {
                ((Activity)mContext).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Result rs = gson.fromJson(res, Result.class);
                        if(rs.getCode() == 200){
                            item.setStatus(1);
                            notifyDataSetChanged();
                        }
                        Toast.makeText(mContext, rs.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onFailure(Exception e) {}
        });
    }

    //确认收货
    public void receiveOrder(Integer position){
        Gson gson = new Gson();
        OrderEntity item = orderEntityList.get(position);
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("orderId",item.getId());
        Api.config(ApiConfig.RORDER, params).postRequest(mContext,new TtitCallback() {
            @Override
            public void onSuccess(final String res) {
                ((Activity)mContext).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Result rs = gson.fromJson(res, Result.class);
                        if(rs.getCode() == 200){
                            item.setStatus(3);
                            notifyDataSetChanged();
                        }
                        Toast.makeText(mContext, rs.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onFailure(Exception e) {}
        });
    }


}