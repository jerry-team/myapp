package com.jerry.myapp.activity;

import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jerry.myapp.R;
import com.jerry.myapp.adapter.AddressAdapter;
import com.jerry.myapp.entity.AddressResponse;
import com.jerry.myapp.fragment.ProfileFragment;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

public class AddressActivity extends BaseActivity{

    private ImageButton leftReturn2;
    private TextView adrEdit;
    private LinearLayout adrEmpty;
    private SmartRefreshLayout refresh2;
    private TextView adrAdd;
    private RecyclerView adrRecycle;

    private boolean isAdrEdit = false;//是否编辑
    private boolean isHaveAdr = false;

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
        //下拉刷新监听
        refresh2.setOnRefreshListener(refreshLayout -> initView());
        //返回个人中心页面
        leftReturn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateTo(ProfileFragment.class);
            }
        });
        //地址编辑
        adrEdit.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (isAdrEdit) {
                            adrEdit.setText("编辑");
                            //layEdit.setVisibility(View.GONE);
                            isAdrEdit = false;
                        } else {
                            adrEdit.setText("完成");
                            //layEdit.setVisibility(View.VISIBLE);
                            isAdrEdit = true;
                        }
                    }
                });
        //跳转至添加地址页面
        adrAdd.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View view){

                    }
                }
        );
        //api获取后台数据

        //判断地址是否有数据
        if(adrList.size()<=0 || adrList==null){
            isHaveAdr = false;
        }else{
            isHaveAdr = true;

        }
        //当地址为空时
        if (!isHaveAdr){
            adrEmpty.setVisibility(View.VISIBLE);
        }
        else{
            //new一个适配器
            addressAdapter = new AddressAdapter(R.layout.address_card,adrList);
            //设置刷新页面管理器
            adrRecycle.setLayoutManager(new LinearLayoutManager(this));
            //设置适配器
            adrRecycle.setAdapter(addressAdapter);
        }


        //下拉加载数据完成后，关闭下拉刷新动画
        refresh2.finishRefresh();
    }

    @Override
    protected void initData() {

    }
}
