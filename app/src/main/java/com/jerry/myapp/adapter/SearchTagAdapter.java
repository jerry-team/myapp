package com.jerry.myapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jerry.myapp.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SearchTagAdapter extends RecyclerView.Adapter<SearchTagAdapter.ViewHolder>{
    private static ShopGoodsAdapter.OnItemClickListener mOnItemClickListener;
    List<String> tagList = new ArrayList<>();
    private Context mContext;

    public void setOnItemClickListener(ShopGoodsAdapter.OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        TextView search_tag;
        String tag_info;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            search_tag = (TextView)itemView.findViewById(R.id.search_tag_one);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(tag_info);
                }
            });
        }
    }

    public void setDatas(List<String> tagList){
        this.tagList = tagList;
    }

    public SearchTagAdapter(Context context) {
        this.mContext = context;
    }


    @NonNull
    @Override
    public SearchTagAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.tag_search_one,parent,false);
        ViewHolder holder = new ViewHolder(view);
        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                int position = holder.getAdapterPosition();
                String searchEntity = tagList.get(position);
//                Intent in = new Intent(mContext, GoodsDetailActivity.class);
//                Bundle bd = new Bundle();
//                bd.putInt("commodityId",goodsEntity.getId());
//                in.putExtras(bd);
//                mContext.startActivity(in);
//                Toast.makeText(v.getContext(),"you clicked",Toast.LENGTH_SHORT).show();
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull SearchTagAdapter.ViewHolder holder, int position) {
        String tag_info = tagList.get(position);
        System.out.println(tag_info+"绑定成功");
        holder.search_tag.setText(tag_info);
    }

    @Override
    public int getItemCount() {
        if (tagList != null && tagList.size() > 0) {
            return tagList.size();
        } else {
            return 0;
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Serializable obj);
    }
}
