package com.jerry.myapp.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.SurfaceTexture;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.dueeeke.videocontroller.StandardVideoController;
import com.dueeeke.videocontroller.component.CompleteView;
import com.dueeeke.videocontroller.component.ErrorView;
import com.dueeeke.videocontroller.component.GestureView;
import com.dueeeke.videocontroller.component.PrepareView;
import com.dueeeke.videocontroller.component.TitleView;
import com.dueeeke.videocontroller.component.VodControlView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.dueeeke.videoplayer.player.VideoView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.huawei.hms.common.util.Logger;
import com.huawei.hms.videokit.player.InitFactoryCallback;
import com.huawei.hms.videokit.player.LogConfigInfo;
import com.huawei.hms.videokit.player.WisePlayer;
import com.huawei.hms.videokit.player.WisePlayerFactory;
import com.huawei.hms.videokit.player.WisePlayerFactoryOptionsExt;
import com.huawei.hms.videokit.player.common.PlayerConstants;
import com.jerry.myapp.R;
import com.jerry.myapp.VideoKitApplication;
import com.jerry.myapp.adapter.CommentAdapter;
import com.jerry.myapp.adapter.ReplyAdapter;
import com.jerry.myapp.api.Api;
import com.jerry.myapp.api.ApiConfig;
import com.jerry.myapp.api.TtitCallback;
import com.jerry.myapp.entity.CommentEntity;
import com.jerry.myapp.entity.CommentResponse;
import com.jerry.myapp.entity.DefaultAddressResponse;
import com.jerry.myapp.entity.GoodsDetailResponse;
import com.jerry.myapp.entity.GoodsEntity;
import com.jerry.myapp.entity.GoodsResponse;
import com.jerry.myapp.entity.ShopCartCommodityResponse;
import com.jerry.myapp.entity.ShopCartResponse;
import com.jerry.myapp.entity.Tag;
import com.jerry.myapp.util.Tags;
import com.jerry.myapp.util.Utils;

import com.wuhenzhizao.titlebar.widget.CommonTitleBar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.view.SurfaceHolder.Callback;
import android.view.TextureView.SurfaceTextureListener;

public class GoodsDetailActivity extends BaseActivity implements Callback, SurfaceTextureListener, WisePlayer.ErrorListener, WisePlayer.ReadyListener, WisePlayer.EventListener, WisePlayer.PlayEndListener,WisePlayer.ResolutionUpdatedListener, WisePlayer.SeekEndListener, WisePlayer.LoadingListener{


    public static int commodityId;
    private CommonTitleBar titleBar;
    private ImageView rlShop;
    private TextView tv_shopcart;

    private ImageView imageView;
    private TextView price;
    private TextView description;
    private TextView vaccin;
    private TextView isPedigree;
    private TextView isPest;
    private TextView breed;
    private TextView address;
    private String default_Address;

    private SurfaceView surfaceView;
    private WisePlayer player;

    protected VideoView mVideoView;
    protected StandardVideoController mController;
    protected ErrorView mErrorView;
    protected CompleteView mCompleteView;
    protected TitleView mTitleView;
    private LinearLayoutManager linearLayoutManager;
    private PrepareView mPrepareView;
    public FrameLayout mPlayerContainer;

    /**
     * 评论
     */
    private RecyclerView commentRecyclerView;
    private List<CommentEntity> commentEntityList = new ArrayList<>();
    private CommentAdapter commentAdapter;
    public static LinearLayout line_comment;
    public static EditText et_comment_1;
    public static EditText et_comment_2;
    public static Button bt_comment;
    public static Integer parentId;

    /**
     * 当前播放的位置
     */
    protected int mCurPos = -1;
    /**
     * 上次播放的位置，用于页面切回来之后恢复播放
     */
    protected int mLastPos = mCurPos;

    @Override
    protected int initLayout() {
        return R.layout.activity_goods_detail;
    }

    @Override
    protected void initView() {
        titleBar = findViewById(R.id.titlebar);
        rlShop = findViewById(R.id.rl_shop);
        tv_shopcart = findViewById(R.id.tv_shopcart);

        imageView = findViewById(R.id.imageView);
        price = findViewById(R.id.detail_price);
        description = findViewById(R.id.detail_description);
        vaccin = findViewById(R.id.vaccin);
        isPedigree = findViewById(R.id.isPedigree);
        isPest = findViewById(R.id.isPest);
        breed = findViewById(R.id.breed);
        address = findViewById(R.id.address);

        mPrepareView = findViewById(R.id.prepare_view);
        mPlayerContainer = findViewById(R.id.player_container);
        initVideoView();
//        surfaceView = findViewById(R.id.surface_view);
        commentRecyclerView = findViewById(R.id.rcl_3);
        line_comment = findViewById(R.id.line_comment);
        et_comment_1 = findViewById(R.id.et_comment_1);
        et_comment_2 = findViewById(R.id.et_comment_2);
        bt_comment = findViewById(R.id.bt_comment);
    }

    @Override
    protected void initData() {
        Intent in = getIntent();
        Bundle bd = in.getExtras();
        this.commodityId = bd.getInt("commodityId");
//        Toast.makeText(GoodsDetailActivity.this, "commodityId:" +this.commodityId , Toast.LENGTH_SHORT).show();
        titleBar.setListener(new CommonTitleBar.OnTitleBarListener() {
            @Override
            public void onClicked(View v, int action, String extra) {
                if (action == CommonTitleBar.ACTION_LEFT_TEXT) {
                    finish();
//                    Toast.makeText(GoodsDetailActivity.this, "Titlebar double clicked!", Toast.LENGTH_SHORT).show();
                }
                // CommonTitleBar.ACTION_LEFT_TEXT;        // 左边TextView被点击
                // CommonTitleBar.ACTION_LEFT_BUTTON;      // 左边ImageBtn被点击
                // CommonTitleBar.ACTION_RIGHT_TEXT;       // 右边TextView被点击
                // CommonTitleBar.ACTION_RIGHT_BUTTON;     // 右边ImageBtn被点击
                // CommonTitleBar.ACTION_SEARCH;           // 搜索框被点击,搜索框不可输入的状态下会被触发
                // CommonTitleBar.ACTION_SEARCH_SUBMIT;    // 搜索框输入状态下,键盘提交触发，此时，extra为输入内容
                // CommonTitleBar.ACTION_SEARCH_VOICE;     // 语音按钮被点击
                // CommonTitleBar.ACTION_SEARCH_DELETE;    // 搜索删除按钮被点击
                // CommonTitleBar.ACTION_CENTER_TEXT;      // 中间文字点击
            }
        });

        rlShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bd = new Bundle();
                bd.putInt("commodityId",commodityId);
                navigateToWithBundle(ShopDetailActivity.class,bd);
            }
        });
        tv_shopcart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertShopCart();
            }
        });

        getDefaultAddress();
        getCommodity();

        FrameLayout playerContainer = findViewById(R.id.player_container);
        View v = playerContainer.getChildAt(0);
        if (v != null && v == mVideoView && !mVideoView.isFullScreen()) {
            releaseVideoView();
        }

        mPrepareView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPlay();
            }
        });

        getComment();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext,RecyclerView.VERTICAL,false);
        commentRecyclerView.setLayoutManager(linearLayoutManager);
        commentAdapter = new CommentAdapter(mContext,commentEntityList);
        commentRecyclerView.setAdapter(commentAdapter);




//        VideoKitApplication videoKitApplication = new VideoKitApplication();
//        WisePlayerFactory factory = videoKitApplication.getWisePlayerFactory();
//        player = VideoKitApplication.getWisePlayerFactory().createWisePlayer();
//        // 方式1：SurfaceView显示界面
//        SurfaceHolder surfaceHolder = surfaceView.getHolder();
//        surfaceHolder.addCallback(this);
//        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
//
//        // 设置播放器错误信息上报监听器
//        player.setErrorListener(this);
//        // 设置播放器媒体以及播放信息上报监听器
//        player.setEventListener(this);
//        // 设置播放视频分辨率发生变化监听器
//        player.setResolutionUpdatedListener(this);
//        // 设置媒体内容已经完成准备监听器
//        player.setReadyListener(this);
//        // 设置播放器缓冲相关事件的监听器
//        player.setLoadingListener(this);
//        // 设置播放完成监听器
//        player.setPlayEndListener(this);
//        // 设置seek操作完成的监听器
//        player.setSeekEndListener(this);
//
//        player.setVideoType(PlayerConstants.PlayMode.PLAY_MODE_NORMAL);
//        player.setBookmark(10000);
//        player.setCycleMode(PlayerConstants.CycleMode.MODE_CYCLE);
//
//
//        // 方式1：设置单个播放地址
//        player.setPlayUrl("https://videoplay-mos-dra.dbankcdn.com/P_VT/video_injection/92/v3/C072F990370950198572111872/MP4Mix_H.264_1920x1080_6000_HEAAC1_PVC_NoCut.mp4");
//        player.ready();

    }
    protected void getComment(){
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("commodityId", this.commodityId);
        Api.config(ApiConfig.GETCOMMENT,params).postRequest(this,new TtitCallback() {
            @Override
            public void onSuccess(final String res) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
//                        Log.e("insertShopCart", res);
                        Gson gson = new Gson();
                        CommentResponse rs = gson.fromJson(res, CommentResponse.class);
                        if(rs.getCode() == 200) {
                            List<CommentEntity> list = rs.getData();
                            commentEntityList.clear();
                            commentEntityList.addAll(list);
                            commentAdapter.notifyDataSetChanged();
//                            shopBeanList = rs.getData();
                        } else {

                        }
                    }
                });

            }
            @Override
            public void onFailure(Exception e) {

            }
        });
    }

    protected void initVideoView() {
        mVideoView = new VideoView(this);
        mVideoView.setOnStateChangeListener(new com.dueeeke.videoplayer.player.VideoView.SimpleOnStateChangeListener() {
            @Override
            public void onPlayStateChanged(int playState) {
                //监听VideoViewManager释放，重置状态
                if (playState == com.dueeeke.videoplayer.player.VideoView.STATE_IDLE) {
                    Utils.removeViewFormParent(mVideoView);
                    mLastPos = mCurPos;
                    mCurPos = -1;
                }
            }
        });
        mController = new StandardVideoController(this);
        mErrorView = new ErrorView(this);
        mController.addControlComponent(mErrorView);
        mCompleteView = new CompleteView(this);
        mController.addControlComponent(mCompleteView);
        mTitleView = new TitleView(this);
        mController.addControlComponent(mTitleView);
        mController.addControlComponent(new VodControlView(this));
        mController.addControlComponent(new GestureView(this));
        mController.setEnableOrientation(true);
        mVideoView.setVideoController(mController);
    }

    public void insertShopCart(){
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("commodityId", this.commodityId);
        Api.config(ApiConfig.INSERT_SHOP_CART,params).postRequest(this,new TtitCallback() {
            @Override
            public void onSuccess(final String res) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.e("insertShopCart", res);
                        Gson gson = new Gson();
                        ShopCartResponse rs = gson.fromJson(res, ShopCartResponse.class);
                        if(rs.getCode() == 200) {
                            Toast.makeText(GoodsDetailActivity.this, "已加入购物车！", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(GoodsDetailActivity.this, "购物车已有该商品！", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
            @Override
            public void onFailure(Exception e) {

            }
        });
    }

    public void getDefaultAddress(){
        String token = getStringFromSp("token");
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("userId", 1);
        Api.config("/address/getByUserId",params).postRequest(this,new TtitCallback() {
            @Override
            public void onSuccess(final String res) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.e("DefaultAddress", res);
                        Gson gson = new Gson();
                        DefaultAddressResponse defaultAddressResponse = gson.fromJson(res, DefaultAddressResponse.class);
                        DefaultAddressResponse.DefaultAddress de = defaultAddressResponse.getData();
                        Log.e("DefaultAddress", gson.toJson(de));
                        default_Address = de.getAddress();
                        address.setText(default_Address);
                    }
                });

            }
            @Override
            public void onFailure(Exception e) {

            }
        });
    }

    public void getCommodity(){
        String token = getStringFromSp("token");
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("commodityId", this.commodityId);
        Api.config(ApiConfig.GETGOODS_BYID,params).postRequest(this,new TtitCallback() {
            @Override
            public void onSuccess(final String res) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.e("commodityone", res);
                        Gson gson = new Gson();
                        GoodsDetailResponse goodsResponse = gson.fromJson(res, GoodsDetailResponse.class);
                        if(goodsResponse.getCode() == 200) {
                            GoodsEntity goodsEntity = goodsResponse.getData();
                            Glide.with(mContext).load("http://10.0.2.2:8001/commodityImages/"+goodsEntity.getImgurl()).into(imageView);
                            price.setText("￥"+goodsEntity.getPrice());
                            description.setText(goodsEntity.getDescription());
                            vaccin.setText(goodsEntity.getVaccin()+"针");
                            if(goodsEntity.getIsPedigree() == 1)
                                isPedigree.setText("有血统证书");
                            if(goodsEntity.getIsPedigree() == 0)
                                isPedigree.setText("无血统证书");

                            if(goodsEntity.getIsPest() == 1)
                                isPedigree.setText("已驱虫");
                            if(goodsEntity.getIsPest() == 0)
                                isPedigree.setText("未驱虫");

                            breed.setText(goodsEntity.getBreed());
                        } else {
                            showToastSync(goodsResponse.getMsg());
                        }
                    }
                });

            }
            @Override
            public void onFailure(Exception e) {

            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        pause();
    }

    /**
     * 由于onPause必须调用super。故增加此方法，
     * 子类将会重写此方法，改变onPause的逻辑
     */
    protected void pause() {
        releaseVideoView();
    }

    @Override
    public void onResume() {
        super.onResume();
        resume();
    }

    /**
     * 由于onResume必须调用super。故增加此方法，
     * 子类将会重写此方法，改变onResume的逻辑
     */
    protected void resume() {
//        if (mLastPos == -1)
//            return;
        //恢复上次播放的位置
        startPlay();
    }

    /**
     * PrepareView被点击
     */
//    @Override
//    public void onItemChildClick(int position) {
//        startPlay(position);
//    }

//    /**
//     * 开始播放
//     *
//     * @param position 列表位置
//     */
    protected void startPlay() {
//        if (mCurPos == position) return;
        if (mCurPos != -1) {
            releaseVideoView();
        }
//        VideoEntity videoEntity = datas.get(position);
        //边播边存
//        String proxyUrl = ProxyVideoCacheManager.getProxy(getActivity()).getProxyUrl(videoBean.getUrl());
//        mVideoView.setUrl(proxyUrl);

        mVideoView.setUrl("http://vfx.mtime.cn/Video/2019/02/04/mp4/190204084208765161.mp4");
        mTitleView.setTitle("猫猫大战狗狗");
//        View itemView = linearLayoutManager.findViewByPosition(position);
//        if (itemView == null) return;
//        VideoAdapter.ViewHolder viewHolder = (VideoAdapter.ViewHolder) itemView.getTag();
        //把列表中预置的PrepareView添加到控制器中，注意isPrivate此处只能为true。
        mController.addControlComponent(mPrepareView, true);
        Utils.removeViewFormParent(mVideoView);
        mPlayerContainer.addView(mVideoView, 0);
        //播放之前将VideoView添加到VideoViewManager以便在别的页面也能操作它
        getVideoViewManager().add(mVideoView, Tags.LIST);
        mVideoView.start();
//        mCurPos = position;

    }

    private void releaseVideoView() {
        mVideoView.release();
        if (mVideoView.isFullScreen()) {
            mVideoView.stopFullScreen();
        }
        if (this.getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
            this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        mCurPos = -1;
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        player.setView(surfaceView);
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {

    }

    @Override
    public void onSurfaceTextureAvailable(@NonNull SurfaceTexture surface, int width, int height) {

    }

    @Override
    public void onSurfaceTextureSizeChanged(@NonNull SurfaceTexture surface, int width, int height) {

    }

    @Override
    public boolean onSurfaceTextureDestroyed(@NonNull SurfaceTexture surface) {
        return false;
    }

    @Override
    public void onSurfaceTextureUpdated(@NonNull SurfaceTexture surface) {

    }

    @Override
    public boolean onError(WisePlayer wisePlayer, int i, int i1) {
        return false;
    }

    @Override
    public boolean onEvent(WisePlayer wisePlayer, int i, int i1, Object o) {
        return false;
    }

    @Override
    public void onLoadingUpdate(WisePlayer wisePlayer, int i) {

    }

    @Override
    public void onStartPlaying(WisePlayer wisePlayer) {
//        System.out.println(player.getDuration());
//        System.out.println();player.getCurrentTime();
//        player.seek(3600000);
    }

    @Override
    public void onPlayEnd(WisePlayer wisePlayer) {
//        player.stop();
        // 释放播放器，并将播放器对象置为null
        player.release();
        player = null;
    }

    @Override
    public void onReady(WisePlayer wisePlayer) {
//        player.getDuration();
//        player.getCurrentTime();
//        player.seek(3600000);

        player.start();
        player.setPlaySpeed((float)2.0);
        player.getCurrentTime();
        player.getDuration();
    }

    @Override
    public void onResolutionUpdated(WisePlayer wisePlayer, int i, int i1) {

    }

    @Override
    public void onSeekEnd(WisePlayer wisePlayer) {

    }
}
