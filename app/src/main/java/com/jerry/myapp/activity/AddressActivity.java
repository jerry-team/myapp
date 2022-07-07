package com.jerry.myapp.activity;

import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.huawei.hms.hwid.A;
import com.jerry.myapp.R;
import com.jerry.myapp.adapter.AddressAdapter;
import com.jerry.myapp.api.Api;
import com.jerry.myapp.api.ApiConfig;
import com.jerry.myapp.api.TtitCallback;
import com.jerry.myapp.entity.AddressResponse;
import com.jerry.myapp.fragment.ProfileFragment;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AddressActivity extends BaseActivity{

    private ImageButton leftReturn2;
    private TextView adrEdit;
    private LinearLayout adrEmpty;
    private SmartRefreshLayout refresh2;
    private TextView adrAdd;
    private RecyclerView adrRecycle;

    private boolean isAdrEdit = false;//是否编辑（右上）
    private boolean isHaveAdr = false;//是否有地址
    private boolean isEdit = false;//是否点击编辑小图标

    private AddressAdapter addressAdapter;
    private List<AddressResponse.AddressInfo> adrList = new ArrayList<>();

    private ImageView adrDefault;
    private TextView adrDefaultText;
    private TextView adrName;
    private TextView adrTelephone;
    private TextView adrAddress;
    private ImageView adrDelete;
    private ImageView adrEdit2;
    @Override
    protected int initLayout() {
        return R.layout.activity_address;
    }

    @Override
    protected void initView() {
        leftReturn2 = findViewById(R.id.left_return2);
        adrEdit = findViewById(R.id.adr_edit);
        adrEmpty = findViewById(R.id.adr_empty);
        refresh2 = findViewById(R.id.refresh2);
        adrAdd = findViewById(R.id.adr_add);
        adrRecycle = findViewById(R.id.adr_recycle);

        adrDelete = findViewById(R.id.adr_delete);
        adrEdit2 = findViewById(R.id.adr_edit2);
        //返回个人中心页面
        leftReturn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        //右上角地址编辑 按下后所有卡片都可编辑
        /*adrEdit.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (isAdrEdit) {
                            adrEdit.setText("编辑");
                            //删除图标
                            adrDelete.setVisibility(View.GONE);
                            //编辑图标
                            adrEdit2
                            isAdrEdit = false;
                        } else {
                            adrEdit.setText("完成");
                            //adrDelete.setVisibility(View.VISIBLE);
                            isAdrEdit = true;
                        }
                    }
                });*/
        //跳转至添加地址页面
        adrAdd.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View view){
                        navigateTo(AddressAddActivity.class);
                    }
                }
        );
        //new一个适配器
        addressAdapter = new AddressAdapter(R.layout.address_card,adrList);
        //设置刷新页面管理器
        adrRecycle.setLayoutManager(new LinearLayoutManager(this));
        //设置适配器
        adrRecycle.setAdapter(addressAdapter);
        getAddress();
        //子监听器
        addressAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {

            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                AddressResponse.AddressInfo cardBean = adrList.get(position);
                switch (view.getId()){
                    case R.id.adr_edit2://点击编辑图标
                        if (isEdit){
                            adrDelete.setVisibility(View.VISIBLE);
                            isEdit = true;
                        }else{
                            adrDelete.setVisibility(View.GONE);
                            isEdit =false;
                        }
                        break;
                    /*case R.id.adr_delete:
                        //删除该地址卡片
                        break;*/
                }
            }
        }

        );

        //判断地址是否有数据
        if(adrList.size()<=0 || adrList==null){
            isHaveAdr = false;
        }else{
            isHaveAdr = true;
        }
        //当地址为空时
        if (isHaveAdr){
            adrEmpty.setVisibility(View.GONE);
        }
        else{
            adrEmpty.setVisibility(View.VISIBLE);
        }
        //下拉刷新监听 ;
        refresh2.setOnRefreshListener(
                refreshLayout -> initView());
        //下拉加载数据完成后，关闭下拉刷新动画
        refresh2.finishRefresh();

        //adrEmpty.setVisibility(View.GONE);
    }

    @Override
    protected void initData() {

    }

    private void getAddress(){
        String token = getStringFromSp("token");
        //api获取后台数据
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("token", token);
        params.put("userId",1);
        //查询List
        Api.config(ApiConfig.ListAddress,params).postRequest(this,new TtitCallback() {
            @Override
            public void onSuccess(final String res) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.e("address", res);
                        Gson gson = new Gson();
                        AddressResponse addressResponse = gson.fromJson(res, AddressResponse.class);
                        //AddressResponse addressResponse = gson.fromJson(res, new TypeToken<List<AddressResponse.AddressInfo>>() {}.getType());
                        //List<AddressResponse.AddressInfo> test= addressResponse.getData();
                        List<AddressResponse.AddressInfo> list = addressResponse.getData();
                        adrList.clear();
                        adrList.addAll(list);
                        addressAdapter.notifyDataSetChanged();
                    }
                });

            }
            @Override
            public void onFailure(Exception e) {
            }
        });
    }

}
