package com.jerry.myapp.activity;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.viewpager.widget.ViewPager;

import com.flyco.tablayout.SlidingTabLayout;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.gson.Gson;
import com.hb.dialog.myDialog.MyAlertDialog;
import com.jerry.myapp.R;
import com.jerry.myapp.adapter.HomeAdapter;
import com.jerry.myapp.adapter.SearchAdapter;
import com.jerry.myapp.adapter.SearchGoodsAdapter;
import com.jerry.myapp.adapter.SearchTagAdapter;
import com.jerry.myapp.api.Api;
import com.jerry.myapp.api.ApiConfig;
import com.jerry.myapp.api.TtitCallback;
import com.jerry.myapp.entity.CategoryEntity;
import com.jerry.myapp.entity.CategoryResponse;
import com.jerry.myapp.entity.GoodsEntity;
import com.jerry.myapp.entity.GoodsResponse;
import com.jerry.myapp.entity.SearchEntity;
import com.jerry.myapp.entity.SearchResponse;
import com.jerry.myapp.entity.Tag;
import com.jerry.myapp.fragment.BaseFragment;
import com.jerry.myapp.fragment.GoodsFragment;
import com.jerry.myapp.util.ParseTokenUtils;
import com.jerry.myapp.util.StringUtils;

import java.awt.font.TextAttribute;
import java.io.SyncFailedException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SearchHomeActivity extends BaseActivity {
//    private ViewPager viewPager;
//    private SlidingTabLayout slidingTabLayout;
//    private ArrayList<Fragment> mFragments = new ArrayList<>();
//    private String[] mTitles ={"全部","推荐","店铺","上新"};
    private List<GoodsEntity> goodsEntityList;
    private SearchGoodsAdapter searchAdapter;
    private EditText searchVal;
    private ImageView btn_return;
    private TextView goSearch;
    private Integer pageNum = 1;
    private ListView search_good_list;
    private RelativeLayout search_note;
    private RecyclerView search_history,search_often;
    private SearchTagAdapter searchTagAdapter; //历史搜索的适配器
    private SearchTagAdapter searchTagAdapter_often;
    private List<String> stringList; //接受历史搜索的list
    private List<String> oftensearchList; //接受常用搜索的list
    private ImageView history_search_delete;
    private ToggleButton often_search_visibility;
    private Drawable on,off;
    @Override
    protected int initLayout() {

        return R.layout.activity_search;
    }

    @Override
    protected void initView() {
        btn_return = findViewById(R.id.return_home);
        searchVal = findViewById(R.id.search_good_edit);
        goSearch = findViewById(R.id.search_button_go);
        search_good_list = findViewById(R.id.search_good_list);
        search_note = findViewById(R.id.search_note);
        search_history = findViewById(R.id.history_search_tab);
        search_often = findViewById(R.id.often_search_tab);
        on = getResources().getDrawable((R.mipmap.on_pwd));
        off = getResources().getDrawable((R.mipmap.off_pwd));
        history_search_delete = findViewById(R.id.history_search_delete);
        often_search_visibility = findViewById(R.id.often_search_visibility);

//        search_history.setLayoutManager(new FlexboxLayoutManager(this));
//        search_history.setOr(LinearLayoutManager.HORIZONTAL);
//        StaggeredGridLayoutManager gridLayoutManager1 = new StaggeredGridLayoutManager(5, StaggeredGridLayoutManager.VERTICAL);
//        FlexboxLayoutManager flexboxLayoutManager = new FlexboxLayoutManager(this, FlexDirection.)
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
//        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        //为recyclerview 设置布局
        search_history.setLayoutManager(new FlexboxLayoutManager(this));
        search_often.setLayoutManager(new FlexboxLayoutManager((this)));

    }

    @Override
    protected void initData() {
        Context _this = this;
        //为历史搜索设置适配器
        stringList = new ArrayList<>();
        searchTagAdapter = new SearchTagAdapter(this);
        search_history.setAdapter(searchTagAdapter);
        searchTagAdapter.setDatas(stringList);

        //为常用搜索设置适配器
        oftensearchList = new ArrayList<>();
        searchTagAdapter_often = new SearchTagAdapter(this);
        search_often.setAdapter(searchTagAdapter_often);
        searchTagAdapter_often.setDatas(oftensearchList);

        //为商品列表设置适配器
        goodsEntityList = new ArrayList<>();
        searchAdapter = new SearchGoodsAdapter(this,goodsEntityList);
        search_good_list.setAdapter(searchAdapter);

        getSearchTag();
        getOftenSearch();

        history_search_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyAlertDialog myAlertDialog = new MyAlertDialog(_this).builder()
                        .setTitle("提示" )
                        .setMsg("您确定要删除所有记录吗？")
                        .setPositiveButton("确认", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                deleteHistorySearch();
                            }
                        }).setNegativeButton("取消", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                            }
                        });
                myAlertDialog.show();
            }
        });

        often_search_visibility.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                if(isChecked){
                    //如果选中，显示
                    search_often.setVisibility(View.VISIBLE);
                    often_search_visibility.setBackgroundDrawable(on);

                }else{
                    //否则隐藏
                    search_often.setVisibility(View.GONE);
                    often_search_visibility.setBackgroundDrawable(off);
                }
            }
        });

        btn_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
//        getSearchView();
        goSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search_note.setVisibility(View.GONE);
                search_good_list.setVisibility(View.VISIBLE);
                getSearchView(searchVal.getText().toString());
            }
        });
        searchVal.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //在编辑框改变前调用
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //在编辑框改变时调用
                if(searchVal.getText().toString() == "" || StringUtils.isEmpty(searchVal.getText().toString())){
                    search_note.setVisibility(View.VISIBLE);
                    search_good_list.setVisibility(View.GONE);
                    getSearchTag();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                //在编辑框改变后调用
            }
        });
    }
    private void getSearchView(String search_val){

//      判断输入内容是否为空，如果为空，就提示不能搜索
        if (TextUtils.isEmpty(search_val.trim())) {
            Toast.makeText(this,"输入内容不能为空！",Toast.LENGTH_SHORT).show();
            return;
        }
        String token = getStringFromSp("token");
        if(!StringUtils.isEmpty(token)){
            HashMap<String, Object> params = new HashMap<>();
            params.put("token", token);
            params.put("start", pageNum);
            params.put("pageSize", ApiConfig.PAGE_SIZE);
            params.put("val",search_val);
            ParseTokenUtils parseTokenUtils = new ParseTokenUtils();
            String username = parseTokenUtils.parseToken(token,"sub");
            params.put("username",username);
            Api.config(ApiConfig.SEARCHGOODS,params).postRequest(this,new TtitCallback() {
                @Override
                public void onSuccess(final String res) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            GoodsResponse response = new Gson().fromJson(res, GoodsResponse.class);
                            if(response != null && response.getCode() == 200) {
                                List<GoodsEntity> list = response.getData();
                                goodsEntityList.clear();
                                goodsEntityList.addAll(list);
                                searchAdapter.notifyDataSetChanged();
                            }
                        }
                    });
                }
                @Override
                public void onFailure(Exception e) {

                }
            });
        }
        else{
            navigateTo(LoginActivity.class);
        }
    }

    //从后端获取历史搜索
    private void getSearchTag(){
        String token = getStringFromSp("token");
        if(!StringUtils.isEmpty(token)){
            HashMap<String, Object> params = new HashMap<>();
            params.put("token", token);
            ParseTokenUtils parseTokenUtils = new ParseTokenUtils();
            String username = parseTokenUtils.parseToken(token,"sub");
            params.put("username",username);
            Api.config(ApiConfig.HISTORYSEARCH,params).postRequest(this,new TtitCallback() {
                @Override
                public void onSuccess(final String res) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            SearchResponse response = new Gson().fromJson(res, SearchResponse.class);
                            if(response != null && response.getCode() == 200) {
                                List<SearchEntity> list_raw = response.getData();
                                List<String> list = new ArrayList<>();
                                for(SearchEntity entity:list_raw){
                                    list.add(entity.getVal());
                                }
                                System.out.println(list.get(1));
                                stringList.clear();
                                stringList.addAll(list);
                                searchTagAdapter.notifyDataSetChanged();
                            }
                        }
                    });
                }
                @Override
                public void onFailure(Exception e) {

                }
            });
        }
        else{
            navigateTo(LoginActivity.class);
        }
    }

    //从后端获取常用搜索
    public void getOftenSearch(){
        String token = getStringFromSp("token");
        if(!StringUtils.isEmpty(token)){
            HashMap<String, Object> params = new HashMap<>();
            params.put("token", token);
            ParseTokenUtils parseTokenUtils = new ParseTokenUtils();
            String username = parseTokenUtils.parseToken(token,"sub");
            params.put("username",username);
            Api.config(ApiConfig.OFTENSEARCH,params).postRequest(this,new TtitCallback() {
                @Override
                public void onSuccess(final String res) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            SearchResponse response = new Gson().fromJson(res, SearchResponse.class);
                            if(response != null && response.getCode() == 200) {
                                List<SearchEntity> list_raw = response.getData();
                                List<String> list = new ArrayList<>();
                                for(SearchEntity entity:list_raw){
                                    list.add(entity.getVal());
                                }
                                oftensearchList.clear();
                                oftensearchList.addAll(list);
                                searchTagAdapter.notifyDataSetChanged();
                            }
                        }
                    });
                }
                @Override
                public void onFailure(Exception e) {

                }
            });
        }
        else{
            navigateTo(LoginActivity.class);
        }
    }

    public void deleteHistorySearch(){
        String token = getStringFromSp("token");
        if(!StringUtils.isEmpty(token)){
            HashMap<String, Object> params = new HashMap<>();
            params.put("token", token);
            ParseTokenUtils parseTokenUtils = new ParseTokenUtils();
            String username = parseTokenUtils.parseToken(token,"sub");
            params.put("username",username);
            Api.config(ApiConfig.OFTENSEARCH,params).postRequest(this,new TtitCallback() {
                @Override
                public void onSuccess(final String res) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            SearchResponse response = new Gson().fromJson(res, SearchResponse.class);
                            if(response != null && response.getCode() == 200) {
                                Toast.makeText(getApplicationContext(),"删除成功！",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                @Override
                public void onFailure(Exception e) {
                }
            });
        }
        else{
            navigateTo(LoginActivity.class);
        }
    }
}
