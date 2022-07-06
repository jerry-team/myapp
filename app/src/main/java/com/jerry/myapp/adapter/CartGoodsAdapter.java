package com.jerry.myapp.adapter;

import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jerry.myapp.R;
import com.jerry.myapp.entity.CarResponse;
import com.jerry.myapp.entity.GoodsEntity;

import java.util.List;


public class CartGoodsAdapter extends BaseQuickAdapter<GoodsEntity, BaseViewHolder> {

   public CartGoodsAdapter(int layoutResId, @Nullable List<GoodsEntity> data) {
      super(layoutResId, data);
   }

   @Override
   protected void convert(BaseViewHolder helper, GoodsEntity item) {
      helper.setText(R.id.tv_good_name, item.getName())
              .setText(R.id.tv_good_color, item.getBreed())
//              .setText(R.id.tv_good_size, item.getName())
              .setText(R.id.tv_goods_price, "￥" + item.getPrice())
              .setText(R.id.tv_goods_num, item.getNumber() + "");
      ImageView goodImg = helper.getView(R.id.iv_goods);
//      RequestOptions requestOptions = new RequestOptions();
//      requestOptions
//              .skipMemoryCache(true)
//              .diskCacheStrategy(DiskCacheStrategy.NONE);
      Glide.with(mContext).load("http://10.0.2.2:8001/commodityImages/"+item.getImgurl()).into(goodImg);
//      Glide.with(mContext).load(item.getDefaultPic()).into(goodImg);

      ImageView checkedGoods = helper.getView(R.id.iv_checked_goods);
      //判断商品是否选中
      if (item.isChecked()) {
         checkedGoods.setImageDrawable(mContext.getDrawable(R.drawable.ic_checked));
      } else {
         checkedGoods.setImageDrawable(mContext.getDrawable(R.drawable.ic_check));
      }
      //添加点击事件
      helper.addOnClickListener(R.id.iv_checked_goods)//选中商品
              .addOnClickListener(R.id.tv_increase_goods_num)//增加商品
              .addOnClickListener(R.id.tv_reduce_goods_num);//减少商品
   }
}
