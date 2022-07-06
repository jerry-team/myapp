package com.jerry.myapp.adapter;

import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jerry.myapp.R;
import com.jerry.myapp.entity.AddressResponse;


import java.util.ArrayList;
import java.util.List;


public class AddressAdapter extends BaseQuickAdapter<AddressResponse.AddressInfo, BaseViewHolder> {
    //地址回调
    private RecyclerView adrRecycle;
    //地址信息数组
    private List<AddressResponse.AddressInfo> addressList;
    private boolean isDefault;
    public AddressAdapter(int layoutResId, @Nullable List<AddressResponse.AddressInfo> data) {
        super(layoutResId, data);
        addressList = data;//赋值
    }
    //private ImageView adrDefault;
    //private TextView adrDefaultText;
    //private TextView adrName;
    //private TextView adrTelephone;
    //private TextView adrAddress;
    //private ImageView adrDelete;
    //private ImageView adrEdit2;
    protected void convert(BaseViewHolder helper, final AddressResponse.AddressInfo item) {

        adrRecycle = helper.getView(R.id.adr_recycle);

        helper.setText(R.id.adr_name,item.getName())
                .setText(R.id.adr_telephone,item.getTelephone())
                .setText(R.id.adr_address,item.getAddress());

        ImageView adrDefault = helper.getView(R.id.adr_default);
        //判断是否默认地址
        if (item.getDefaultAddress() == 1){
            isDefault =true;
        }else{
            isDefault = false;
        }
        if (isDefault){
            adrDefault.setImageDrawable(mContext.getDrawable(R.drawable.ic_checked));
        }else{
            adrDefault.setImageDrawable(mContext.getDrawable(R.drawable.ic_check));
        }

    }
}
