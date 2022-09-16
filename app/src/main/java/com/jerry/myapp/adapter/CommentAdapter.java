package com.jerry.myapp.adapter;


import static android.content.Context.MODE_PRIVATE;
import static com.jerry.myapp.activity.GoodsDetailActivity.bt_comment;
import static com.jerry.myapp.activity.GoodsDetailActivity.commodityId;
import static com.jerry.myapp.activity.GoodsDetailActivity.et_comment_1;
import static com.jerry.myapp.activity.GoodsDetailActivity.et_comment_2;
import static com.jerry.myapp.activity.GoodsDetailActivity.line_comment;
import static com.jerry.myapp.activity.GoodsDetailActivity.parentId;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.huawei.hms.hwid.A;
import com.huawei.hms.hwid.I;
import com.jerry.myapp.R;
import com.jerry.myapp.activity.GoodsDetailActivity;
import com.jerry.myapp.api.Api;
import com.jerry.myapp.api.ApiConfig;
import com.jerry.myapp.api.TtitCallback;
import com.jerry.myapp.entity.CommentEntity;
import com.jerry.myapp.entity.GoodsEntity;
import com.jerry.myapp.entity.OrderEntity;
import com.jerry.myapp.entity.OrderItemDTO;
import com.jerry.myapp.entity.OrderItemEntity;
import com.jerry.myapp.entity.OrdersDTO;
import com.jerry.myapp.util.SampleCircleImageView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder>{
    private List<CommentEntity> commentEntityList;
    private ReplyAdapter replyAdapter;
    private Context mContext;
    private static OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        RecyclerView replyRecyclerView;
        SampleCircleImageView user_icon;
        TextView user_name;
        TextView time;
        TextView content;
        RelativeLayout rl_comment;
        Button bt_test;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            user_icon = (SampleCircleImageView) itemView.findViewById(R.id.ico);
            user_name = (TextView) itemView.findViewById(R.id.user);
            content = (TextView) itemView.findViewById(R.id.data);
            replyRecyclerView = itemView.findViewById(R.id.replyRecyclerView);
            rl_comment = itemView.findViewById(R.id.rl_comment);
//            bt_test = itemView.findViewById(R.id.bt_test);
        }
    }

    public void setDatas(List<CommentEntity> commentEntityList){
        this.commentEntityList = commentEntityList;
    }

    public CommentAdapter(Context context,List<CommentEntity> commentEntityList) {
        this.commentEntityList = commentEntityList;
        this.mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_item_comment,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CommentEntity commentEntity = commentEntityList.get(position);
        holder.user_name.setText(commentEntity.getUser_name());
        holder.content.setText(commentEntity.getContent());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext,RecyclerView.VERTICAL,false);
        holder.replyRecyclerView.setLayoutManager(linearLayoutManager);
        replyAdapter = new ReplyAdapter(mContext,getReply(commentEntity.getReplyList()));
        holder.replyRecyclerView.setAdapter(replyAdapter);
        holder.rl_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(line_comment.getVisibility() == View.VISIBLE){
                    line_comment.setVisibility(View.GONE);
                }
                else{
                    line_comment.setVisibility(View.VISIBLE);
                    et_comment_1.setText(commentEntity.getUser_name() + ":");
                    parentId = commentEntity.getId();
                }

            }
        });


        bt_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommentEntity comment = new CommentEntity();
                comment.setCommodityId(commodityId);
                comment.setContent(et_comment_2.getText().toString());
                comment.setParentId(parentId);
                addComment(comment);
            }
        });

    }
    public void addComment(CommentEntity comment){
        HashMap<String, Object> params = new HashMap<String, Object>();
        List<OrdersDTO> ordersDTOList = new ArrayList<>();
        for(int i = 0;i < 2;i++){
            OrdersDTO ordersDTO = new OrdersDTO();
            ordersDTO.setShopId(i);
            List<OrderItemDTO> orderItemDTOList = new ArrayList<>();
            for(int j = 0;j < 2;j++){
                OrderItemDTO orderItemDTO = new OrderItemDTO();
                orderItemDTO.setCommodityId(j);
                orderItemDTOList.add(orderItemDTO);
            }
            ordersDTO.setOrderItemDTOList(orderItemDTOList);
            ordersDTOList.add(ordersDTO);
        }


        OrdersDTO ordersDTO = new OrdersDTO();
        ordersDTO.setShopId(23);
        Gson gson = new Gson();
        System.out.println(gson.toJson(ordersDTO));
        params.put("id",comment.getId());
        params.put("commodityId", comment.getCommodityId());
        params.put("content", gson.toJson(ordersDTO));
        params.put("parentId",comment.getParentId());
//        params.put("test1",222);
//        params.put("OrdersDTOList",ordersDTOList);
//        params.put("test",gson.toJson(ordersDTO));

        Api.config(ApiConfig.ADDCOMMENT, params).postRequest(mContext,new TtitCallback() {
            @Override
            public void onSuccess(final String res) {
                ((Activity)mContext).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.e("onSuccess", res);
                        line_comment.setVisibility(View.GONE);
                        et_comment_2.setText("");
                        Toast.makeText(mContext, "评论成功", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onFailure(Exception e) {}
        });
    }

    public void test() {
    }

    public List<CommentEntity> getReply(List<CommentEntity> list){
        List<CommentEntity> replyList = new ArrayList<>();
        for(CommentEntity reply:list){
            replyList.add(reply);
            if(reply.getReplyList().size() > 0){
                replyList.addAll(getReply(reply.getReplyList()));
            }
        }
        return replyList;
    }

    @Override
    public int getItemCount() {
        if (commentEntityList != null && commentEntityList.size() > 0) {
            return commentEntityList.size();
        } else {
            return 0;
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Serializable obj);
    }

}

