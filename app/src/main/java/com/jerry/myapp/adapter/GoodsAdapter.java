package com.jerry.myapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jerry.myapp.R;
import com.jerry.myapp.entity.GoodsEntity;

import java.util.List;

public class GoodsAdapter extends RecyclerView.Adapter<GoodsAdapter.ViewHolder>{
    private List<GoodsEntity> goodsEntityList;

    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView goodsImage;
        TextView goodsTitle;
        TextView goodsPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            goodsImage = (ImageView) itemView.findViewById(R.id.goods_item_image);
            goodsTitle = (TextView) itemView.findViewById(R.id.goods_item_title);
            goodsPrice= (TextView) itemView.findViewById(R.id.goods_item_price);
        }
    }

    public GoodsAdapter(List<GoodsEntity> goodsEntityList) {
        this.goodsEntityList = goodsEntityList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.goods_item_one,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GoodsEntity goodsEntity = goodsEntityList.get(position);
        holder.goodsTitle.setText(goodsEntity.getTitle());
        holder.goodsPrice.setText(goodsEntity.getPrice());
        holder.goodsImage.setImageResource(goodsEntity.getImageUrl());
    }

    @Override
    public int getItemCount() {
        return goodsEntityList.size();
    }
}
