package com.jerry.myapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.jerry.myapp.R;
import com.jerry.myapp.activity.GoodsDetailActivity;
import com.jerry.myapp.entity.GoodsEntity;

import org.raphets.roundimageview.RoundImageView;

import java.io.Serializable;
import java.util.List;

public class SearchGoodsAdapter extends BaseAdapter {
    Context context;
    List<GoodsEntity>mDatas;
    LayoutInflater inflater;

    public SearchGoodsAdapter(Context context, List<GoodsEntity> mDatas) {
        this.context = context;
        this.mDatas = mDatas;
        inflater = LayoutInflater.from(context);
    }

    public void setDatas(List<GoodsEntity> mDatas) {
        this.mDatas = mDatas;

    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.goods_search_one,parent,false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        GoodsEntity bean = mDatas.get(position);
//        holder.goodsImage.setImageResource(bean.getImgurl());
          holder.goodsName.setText(bean.getName());
          holder.goodsPrice.setText((int)bean.getPrice()+"");
          holder.goodsShop.setText(bean.getShopName());
        RequestOptions requestOptions = new RequestOptions();
        requestOptions
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE);
        Glide.with(context).load("http://10.0.2.2:8001/commodityImages/"+bean.getImgurl()).apply(requestOptions).into(holder.goodsImage);
          switch (bean.getCategory()) {
              case 1:
                  holder.goodsType.setText("猫咪");
                  break;
              case 2:
                  holder.goodsType.setText("狗狗");
                  break;
              case 3:
                  holder.goodsType.setText("金鱼");
                  break;
              case 4:
                  holder.goodsType.setText("仓鼠");
                  break;
              case 5:
                  holder.goodsType.setText("宠物周边");
                  break;
              case 6:
                  holder.goodsType.setText("宠物食物");
                  break;
              case 7:
                  holder.goodsType.setText("宠物洗澡");
                  break;
              case 8:
                  holder.goodsType.setText("宠物医疗");
                  break;
                  default:
                      break;
          }
        return convertView;
    }

    class ViewHolder{
        RoundImageView goodsImage;
        TextView goodsName;
        TextView goodsShop;
        CardView goodsCard;
        Button goodsType;
        TextView goodsPrice;
        public ViewHolder(View view){
            goodsImage = (RoundImageView) view.findViewById(R.id.good_pic);
            goodsName = (TextView) view.findViewById(R.id.search_good_name);
            goodsShop = (TextView) view.findViewById(R.id.search_good_shop);
            goodsPrice = (TextView) view.findViewById(R.id.search_good_price);
            goodsType = (Button)view.findViewById(R.id.search_good_type);
            goodsCard = (CardView) view.findViewById(R.id.search_good_card);

        }
    }


}
