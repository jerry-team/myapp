package com.jerry.myapp.adapter;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jerry.myapp.R;
import com.jerry.myapp.entity.CarResponse;
import com.jerry.myapp.entity.GoodsEntity;
import com.jerry.myapp.entity.ShopCartCommodityResponse;
import com.jerry.myapp.util.GoodsCallback;

import java.util.List;

/**
 * 店铺适配器
 *
 * @author llw
 */
public class CartShopAdapter extends BaseQuickAdapter<ShopCartCommodityResponse.ShopBean, BaseViewHolder> {

    private RecyclerView rvGood;
    //商品回调
    private GoodsCallback goodsCallback;
    //店铺对象
    private List<ShopCartCommodityResponse.ShopBean> storeBean;

    public CartShopAdapter(int layoutResId, @Nullable List<ShopCartCommodityResponse.ShopBean> data, GoodsCallback goodsCallback) {
        super(layoutResId, data);
        this.goodsCallback = goodsCallback;
        storeBean = data;//赋值
    }
//    public CartShopAdapter(int layoutResId,GoodsCallback goodsCallback) {
//        super(layoutResId);
//        this.goodsCallback = goodsCallback;
//    }

    public void setData(List<ShopCartCommodityResponse.ShopBean> data){
        this.storeBean = data;
    }

    @Override
    protected void convert(BaseViewHolder helper, final ShopCartCommodityResponse.ShopBean item) {

        rvGood = helper.getView(R.id.rv_goods);
        helper.setText(R.id.tv_store_name, item.getName());

        ImageView checkedStore = helper.getView(R.id.iv_checked_store);
        if (item.isChecked()) {
            checkedStore.setImageDrawable(mContext.getDrawable(R.drawable.ic_checked));
        } else {
            checkedStore.setImageDrawable(mContext.getDrawable(R.drawable.ic_check));
        }
        //点击事件
        helper.addOnClickListener(R.id.iv_checked_store);//选中店铺


        final CartGoodsAdapter goodsAdapter = new CartGoodsAdapter(R.layout.item_good, item.getCommodityList());
        rvGood.setLayoutManager(new LinearLayoutManager(mContext));
        rvGood.setAdapter(goodsAdapter);

        //商品item中的点击事件
        goodsAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                GoodsEntity goodsBean = item.getCommodityList().get(position);

                switch (view.getId()) {
                    case R.id.iv_checked_goods://选中商品
                        //如果已选中则取消选中，未选中则选中
                        goodsBean.setChecked(!goodsBean.isChecked());
                        //刷新适配器
                        goodsAdapter.notifyDataSetChanged();
                        //控制店铺是否选中
                        controlStore(item);
                        //商品控制价格
                        goodsCallback.calculationPrice();
                        break;
                    case R.id.tv_increase_goods_num://增加商品数量
                        updateGoodsNum(goodsBean, goodsAdapter,true);
                        break;
                    case R.id.tv_reduce_goods_num://减少商品数量
                        updateGoodsNum(goodsBean, goodsAdapter,false);
                        break;
                    default:
                        break;
                }
            }
        });

    }

    /**
     * 修改商品数量  增加或者减少
     * @param goodsBean
     * @param goodsAdapter
     * @param state  true增加 false减少
     */
    private void updateGoodsNum(GoodsEntity goodsBean, CartGoodsAdapter goodsAdapter,boolean state) {
        //其实商品应该还有一个库存值或者其他的限定值，我这里写一个假的库存值为10
        int inventory = 3;
        int count = goodsBean.getNumber();

        if(state){
//            if(goodsBean.getCategory() != 5)
//            {
//                Toast.makeText(mContext,"宠物为唯一商品！",Toast.LENGTH_SHORT).show();
//                return;
//            }
            if (count >= inventory){
                Toast.makeText(mContext,"商品数量不可超过库存值~",Toast.LENGTH_SHORT).show();
                return;
            }
            count++;
        }else {
            if (count <= 1){
                Toast.makeText(mContext,"已是最低商品数量~",Toast.LENGTH_SHORT).show();
                return;
            }
            count--;
        }
        goodsBean.setNumber(count);//设置商品数量
        //刷新适配器
        goodsAdapter.notifyDataSetChanged();
        //计算商品价格
        goodsCallback.calculationPrice();
    }

    /**
     * 控制店铺是否选中
     */
    private void controlStore(ShopCartCommodityResponse.ShopBean item) {
        int num = 0;
        for (GoodsEntity bean : item.getCommodityList()) {
            if (bean.isChecked()) {
                ++num;
            }
        }
        if (num == item.getCommodityList().size()) {
            //全选中  传递需要选中的店铺的id过去
            goodsCallback.checkedStore(item.getId(), true);
        } else {
            goodsCallback.checkedStore(item.getId(), false);
        }
    }

    /**
     * 控制商品是否选中
     */
    public void controlGoods(int shopId, boolean state) {
        //根据店铺id选中该店铺下所有商品
        for (ShopCartCommodityResponse.ShopBean shopBean : storeBean) {
            //店铺id等于传递过来的店铺id  则选中该店铺下所有商品
            if (shopBean.getId() == shopId) {
                for (GoodsEntity cartlistBean : shopBean.getCommodityList()) {
                    cartlistBean.setChecked(state);
                    //刷新
                    notifyDataSetChanged();
                }
            }
        }
    }
    //public interface GoodsCallback {
    // /**
    //         * 选中店铺
    //         * @param shopId 店铺id
    //         * @param state 是否选中
    //         */
    //        void checkedStore(int shopId,boolean state);
    //
    //        /**
    //         * 计算价格
    //         */
    //        void calculationPrice();
    //    }


}
