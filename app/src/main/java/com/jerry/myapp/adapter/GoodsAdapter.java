package com.jerry.myapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.huawei.hms.hwid.I;
import com.jerry.myapp.R;
import com.jerry.myapp.activity.GoodsDetailActivity;
import com.jerry.myapp.entity.GoodsEntity;

import java.io.Serializable;
import java.util.List;

public class GoodsAdapter extends RecyclerView.Adapter<GoodsAdapter.ViewHolder>{
    private List<GoodsEntity> goodsEntityList;
    private Context mContext;
    private String price = "￥";
    private static OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView goodsImage;
        TextView goodsTitle;
        TextView goodsPrice;
        CardView goodsCard;
        private GoodsEntity goodsEntity;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            goodsImage = (ImageView) itemView.findViewById(R.id.goods_item_image);
            goodsTitle = (TextView) itemView.findViewById(R.id.goods_item_title);
            goodsPrice = (TextView) itemView.findViewById(R.id.goods_item_price);
            goodsCard = (CardView) itemView.findViewById(R.id.goods_card);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(goodsEntity);
                }
            });

        }
    }

    public void setDatas(List<GoodsEntity> goodsEntityList){
        this.goodsEntityList = goodsEntityList;
    }

    public GoodsAdapter(Context context) {
        this.mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.goods_item_one,parent,false);
        ViewHolder holder = new ViewHolder(view);

        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                int position = holder.getAdapterPosition();
                GoodsEntity goodsEntity = goodsEntityList.get(position);
                Intent in = new Intent(mContext, GoodsDetailActivity.class);
                Bundle bd = new Bundle();
                bd.putInt("commodityId",goodsEntity.getId());
                in.putExtras(bd);
                mContext.startActivity(in);
//                Toast.makeText(v.getContext(),"you clicked",Toast.LENGTH_SHORT).show();
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GoodsEntity goodsEntity = goodsEntityList.get(position);
        holder.goodsTitle.setText(goodsEntity.getName());
        holder.goodsPrice.setText(price + goodsEntity.getPrice());
//        holder.goodsImage.setImageResource(R.mipmap.cat1);
        holder.goodsEntity = goodsEntity;
        RequestOptions requestOptions = new RequestOptions();
        requestOptions
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE);
//        Glide.with(mContext).load("android.resource://com.jerry.myapp/mipmap/"+R.mipmap.cat2).into(holder.goodsImage);
        Glide.with(mContext).load("http://10.0.2.2:8001/commodityImages/"+goodsEntity.getImgurl()).apply(requestOptions).into(holder.goodsImage);
    }

    @Override
    public int getItemCount() {
        if (goodsEntityList != null && goodsEntityList.size() > 0) {
            return goodsEntityList.size();
        } else {
            return 0;
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Serializable obj);
    }

}
