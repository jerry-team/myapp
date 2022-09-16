package com.jerry.myapp.adapter;



import static com.jerry.myapp.activity.GoodsDetailActivity.bt_comment;
import static com.jerry.myapp.activity.GoodsDetailActivity.commodityId;
import static com.jerry.myapp.activity.GoodsDetailActivity.et_comment_1;
import static com.jerry.myapp.activity.GoodsDetailActivity.et_comment_2;
import static com.jerry.myapp.activity.GoodsDetailActivity.line_comment;
import static com.jerry.myapp.activity.GoodsDetailActivity.parentId;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.huawei.hms.hwid.I;
import com.jerry.myapp.R;
import com.jerry.myapp.activity.GoodsDetailActivity;
import com.jerry.myapp.activity.HomeActivity;
import com.jerry.myapp.api.Api;
import com.jerry.myapp.api.ApiConfig;
import com.jerry.myapp.api.TtitCallback;
import com.jerry.myapp.entity.CommentEntity;
import com.jerry.myapp.entity.GoodsEntity;
import com.jerry.myapp.entity.LoginResponse;
import com.jerry.myapp.util.SampleCircleImageView;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

public class ReplyAdapter extends RecyclerView.Adapter<ReplyAdapter.ViewHolder>{
    private List<CommentEntity> commentEntityList;
    private ReplyAdapter replyAdapter;
    private Context mContext;
    private static OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
//        SampleCircleImageView user_icon;
        TextView user_name;
        TextView time;
        TextView content;
        LinearLayout line_comment;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
//            user_icon = (SampleCircleImageView) itemView.findViewById(R.id.ico);
            user_name = (TextView) itemView.findViewById(R.id.user);
            content = (TextView) itemView.findViewById(R.id.data);
            line_comment = itemView.findViewById(R.id.line_comment);
        }
    }

    public void setDatas(List<CommentEntity> commentEntityList){
        this.commentEntityList = commentEntityList;
    }

    public ReplyAdapter(Context context,List<CommentEntity> commentEntityList) {
        this.mContext = context;
        this.commentEntityList = commentEntityList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_item_reply,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CommentEntity commentEntity = commentEntityList.get(position);
        holder.user_name.setText(commentEntity.getUser_name());
        if(commentEntity.getParent_name() == null){
            holder.content.setText(commentEntity.getContent());
        }else{
            holder.content.setText("回复 @" + commentEntity.getParent_name() + ": " + commentEntity.getContent());
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(line_comment.getVisibility() == View.VISIBLE){
                    line_comment.setVisibility(View.GONE);
                }
                else{
                    line_comment.setVisibility(View.VISIBLE);
                    et_comment_1.setText("回复" + commentEntity.getUser_name() + ":");
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
                comment.setParentId(commentEntity.getId());
                addComment(comment);
            }
        });

    }

    public void addComment(CommentEntity comment){
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("commodityId", comment.getCommodityId());
        params.put("content", et_comment_2.getText().toString());
        params.put("parentId",parentId);
        Api.config(ApiConfig.ADDCOMMENT, params).postRequest(mContext,new TtitCallback() {
            @Override
            public void onSuccess(final String res) {
                ((Activity)mContext).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        line_comment.setVisibility(View.GONE);
                        et_comment_2.setText("");
                        Toast.makeText(mContext, "评论成功", Toast.LENGTH_SHORT).show();
                        Intent in = new Intent(mContext, GoodsDetailActivity.class);
                        Bundle bd = new Bundle();
                        bd.putInt("commodityId",commodityId);
                        in.putExtras(bd);
                        mContext.startActivity(in);
                    }
                });

            }

            @Override
            public void onFailure(Exception e) {}
        });
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

