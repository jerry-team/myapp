package com.jerry.myapp.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.jerry.myapp.R;
import com.jerry.myapp.entity.GoodsEntity;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class GoodsAdapter extends RecyclerView.Adapter<GoodsAdapter.ViewHolder>{
    private List<GoodsEntity> goodsEntityList;
    private Context mContext;

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
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GoodsEntity goodsEntity = goodsEntityList.get(position);
        holder.goodsTitle.setText(goodsEntity.getTitle());
        holder.goodsPrice.setText(goodsEntity.getPrice());
//        Bitmap bitmap = BitmapFactory.decodeFile("/petsImage/cat_muppet.png");
        holder.goodsImage.setImageResource(goodsEntity.getImageUrl());
//        holder.goodsImage.setImageBitmap(bitmap);

//        Glide.with(this.mContext).load("https://s1.xoimg.com/i/2022/06/27/i7ja5g.png").into(holder.goodsImage);



    }

    @Override
    public int getItemCount() {
        return goodsEntityList.size();
    }

}
