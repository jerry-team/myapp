package com.jerry.myapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.jerry.myapp.R;
import com.jerry.myapp.entity.CategoryEntity;

import java.io.Serializable;
import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder>{
    private List<CategoryEntity> categoryEntityList;
    private Context mContext;
    private static CategoryAdapter.OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(CategoryAdapter.OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView categoryIcon;
        TextView categoryText;
        private CategoryEntity categoryEntity;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryIcon = (ImageView) itemView.findViewById(R.id.category_icon);
            categoryText = (TextView) itemView.findViewById(R.id.category_text);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(categoryEntity);
                }
            });

        }
    }

    public void setDatas(List<CategoryEntity> categoryEntityList){
        this.categoryEntityList = categoryEntityList;
    }

    public CategoryAdapter(Context context) {
        this.mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.category_item_one,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CategoryEntity categoryEntity = categoryEntityList.get(position);
        holder.categoryText.setText(categoryEntity.getName());

        holder.categoryEntity = categoryEntity;
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.circleCrop()
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE);
//        Glide.diskCacheStrategy(DiskCacheStrategy.ALL);
//        Glide.get(mContext).clearDiskCache();
        Glide.with(mContext).load("http://10.0.2.2:8001/categoryIcon/"+categoryEntity.getIconUrl()).apply(requestOptions).into(holder.categoryIcon);




    }

    @Override
    public int getItemCount() {
        return categoryEntityList.size();
    }

    public interface OnItemClickListener {
        void onItemClick(Serializable obj);
    }
}
