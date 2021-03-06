package com.jerry.myapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.jerry.myapp.R;
import com.jerry.myapp.entity.GoodsEntity;

import java.io.Serializable;
import java.util.List;

public class ShopGoodsAdapter extends RecyclerView.Adapter<ShopGoodsAdapter.ViewHolder>{
    private List<GoodsEntity> goodsEntityList;
    private Context mContext;
    private String price = "";
    private static ShopGoodsAdapter.OnItemClickListener mOnItemClickListener;

    public ShopGoodsAdapter( Context mContext,List<GoodsEntity> goodsEntityList) {
        this.goodsEntityList = goodsEntityList;
        this.mContext = mContext;
    }

    public void setOnItemClickListener(ShopGoodsAdapter.OnItemClickListener onItemClickListener) {
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

    public List<GoodsEntity> getDatas(){
        return this.goodsEntityList;
    }

    public ShopGoodsAdapter(Context context) {
        this.mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.goods_item_one,parent,false);
        ViewHolder holder = new ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GoodsEntity goodsEntity = goodsEntityList.get(position);
        holder.goodsTitle.setText(goodsEntity.getName());
        holder.goodsPrice.setText(price + goodsEntity.getPrice());
//        Bitmap bitmap = BitmapFactory.decodeFile("/petsImage/cat_muppet.png");
        holder.goodsImage.setImageResource(R.mipmap.cat1);
        holder.goodsEntity = goodsEntity;

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
