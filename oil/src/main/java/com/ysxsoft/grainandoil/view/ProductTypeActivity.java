package com.ysxsoft.grainandoil.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.github.jdsjlzx.ItemDecoration.LuDividerDecoration;
import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.recyclerview.LuRecyclerView;
import com.github.jdsjlzx.recyclerview.LuRecyclerViewAdapter;
import com.ysxsoft.grainandoil.R;
import com.ysxsoft.grainandoil.adapter.MyInfoAdapter;
import com.ysxsoft.grainandoil.adapter.ProductListAdapter;
import com.ysxsoft.grainandoil.base.RBaseAdapter;
import com.ysxsoft.grainandoil.base.RViewHolder;
import com.ysxsoft.grainandoil.impservice.ImpService;
import com.ysxsoft.grainandoil.modle.GetGoodsAddressBean;
import com.ysxsoft.grainandoil.modle.GradeIntroduceBean;
import com.ysxsoft.grainandoil.modle.MessageListBean;
import com.ysxsoft.grainandoil.modle.ProductListResponse;
import com.ysxsoft.grainandoil.modle.SesarchBean;
import com.ysxsoft.grainandoil.utils.ActivityPageManager;
import com.ysxsoft.grainandoil.utils.AppUtil;
import com.ysxsoft.grainandoil.utils.BaseActivity;
import com.ysxsoft.grainandoil.utils.ImageLoadUtil;
import com.ysxsoft.grainandoil.utils.IsLoginUtils;
import com.ysxsoft.grainandoil.utils.NetWork;
import com.ysxsoft.grainandoil.widget.pop.SelectLevelsPopupWindow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * create by Sincerly on 2019/4/27 0027
 **/
public class ProductTypeActivity extends BaseActivity implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {
    private int bid;
    private RelativeLayout backLayout, rl_search;
    private TextView menu1, menu2, menu3Text, menu4Text, tv_title_right;
    private LinearLayout menu3, menu4, emptyView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private LuRecyclerView recyclerView;
    private List<ProductListResponse.DataBean> data = new ArrayList<>();
    private List<SelectLevelsPopupWindow.Levels> levels = new ArrayList<>();
    //1是综合2是销量3是价格升序4是降序5是等级排序
    private int type = 1;
    private int page = 1;
    private int current = 0;
    private String cid = "";
    private SelectLevelsPopupWindow selectLevelsPopupWindow;
    private boolean needShowPopup = false;
    private ProductListResponse response;
    private String flag;

    public static void start(Context context, int bid) {
        Intent intent = new Intent(context, ProductTypeActivity.class);
        intent.putExtra("gid", bid);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bid = getIntent().getIntExtra("gid", 0);
        flag = getIntent().getStringExtra("flag");
        setContentView(R.layout.activity_product_type);
        initView();
        setListener();
    }

    private void initView() {
        backLayout = findViewById(R.id.img_back);
        menu1 = findViewById(R.id.menu1);
        menu2 = findViewById(R.id.menu2);
        menu3 = findViewById(R.id.menu3);
        menu3Text = findViewById(R.id.menu3Text);
        menu4 = findViewById(R.id.menu4);
        menu4Text = findViewById(R.id.menu4Text);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        if (swipeRefreshLayout != null) {
            swipeRefreshLayout.setProgressViewOffset(false, 0, AppUtil.dip2px(this, 48));
            swipeRefreshLayout.setColorSchemeResources(R.color.btn_color);
            swipeRefreshLayout.setOnRefreshListener(this);
        }
        emptyView = findViewById(R.id.ll_no_hava_data);
        recyclerView = findViewById(R.id.recyclerView);
        rl_search = findViewById(R.id.rl_search);
        tv_title_right = findViewById(R.id.tv_title_right);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                LinearLayoutManager lm = (LinearLayoutManager) recyclerView.getLayoutManager();
                int totalItemCount = recyclerView.getAdapter().getItemCount();
                int lastVisibleItemPosition = lm.findLastVisibleItemPosition();
                int visibleItemCount = recyclerView.getChildCount();

                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItemPosition == totalItemCount - 1 && visibleItemCount > 0) {
                    if (response != null) {
                        if (page != response.getLast_page()) {
                            page++;
                            refershMenuUI(current);
                        }
                    }
                }
            }
        });

        recyclerView.setAdapter(new LuRecyclerViewAdapter(new RBaseAdapter<ProductListResponse.DataBean>(ProductTypeActivity.this, R.layout.item_product_type, data) {
            @Override
            protected void fillItem(RViewHolder holder, ProductListResponse.DataBean item, int position) {
                final ImageView goodPic = holder.getView(R.id.img_goods_tupian);
//                ImageLoadUtil.GlideGoodsImageLoad(mContext, item.getImgurl(), goodPic);
                RequestOptions options = new RequestOptions().error(R.mipmap.ic_launcher).bitmapTransform(new RoundedCorners(60));//图片圆角为60
                Glide.with(mContext).load(item.getImgurl()) //图片地址
                        .apply(options)
                        .into(goodPic);
                //名称
                holder.setText(R.id.tv_goods_desc, item.getGoods_name());
                //价格
                holder.setText(R.id.tv_price, item.getPrice());
                //销量
                holder.setText(R.id.tv_pintuan_num, String.valueOf(item.getGroup()));
                //等级
                holder.setText(R.id.level, String.valueOf(item.getCategory_name()));
            }

            @Override
            protected int getViewType(ProductListResponse.DataBean item, int position) {
                return NORMAL;
            }
        }));
        recyclerView.setLoadMoreEnabled(true);
        recyclerView.setHasFixedSize(true);
        LuRecyclerViewAdapter adapter = (LuRecyclerViewAdapter) recyclerView.getAdapter();
        recyclerView.setFooterViewColor(R.color.btn_color, R.color.black, android.R.color.transparent);
        recyclerView.setFooterViewHint("拼命加载中", "没有更多数据了", "网络不给力啊，点击再试一次吧");
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (IsLoginUtils.isloginFragment(ProductTypeActivity.this)) {
                    ActivityPageManager instance = ActivityPageManager.getInstance();
                    instance.finishAllActivity();
                    startActivity(new Intent(ProductTypeActivity.this, LoginActivity.class));
                } else {
                    //跳转商品详情
                    SharedPreferences sp = ProductTypeActivity.this.getSharedPreferences("UID", Context.MODE_PRIVATE);
                    String uid = sp.getString("uid", "");
                    if (data.size() > position) {
                        Intent intent = new Intent(ProductTypeActivity.this, WebViewActivity.class);
                        String gid = String.valueOf(data.get(position).getGid());
                        intent.putExtra("gid", String.valueOf(gid));
                        if (!TextUtils.isEmpty(uid) && uid != null) {
                            intent.putExtra("uid", uid);
                        }
                        String url = NetWork.H5BaseUrl + "commodityDetails?gid=" + gid + "&uid=" + uid + "&flag=1";
                        intent.putExtra("url", url);
                        startActivity(intent);
                    }
                }
            }
        });
//        refershMenuUI(current);
        onRefresh();
    }

    private void setListener() {
        swipeRefreshLayout.setOnRefreshListener(this);
        backLayout.setOnClickListener(this);
        menu1.setOnClickListener(this);
        menu2.setOnClickListener(this);
        menu3.setOnClickListener(this);
        menu4.setOnClickListener(this);
        tv_title_right.setOnClickListener(this);
        rl_search.setOnClickListener(this);
    }

    private boolean isManager = false;
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_search:
            case R.id.tv_title_right:
                if (IsLoginUtils.isloginFragment(ProductTypeActivity.this)) {
                    ActivityPageManager instance = ActivityPageManager.getInstance();
                    instance.finishAllActivity();
                    ProductTypeActivity.this.startActivity(new Intent(ProductTypeActivity.this, LoginActivity.class));
                } else {
                    SharedPreferences sp = getSharedPreferences("UID", Context.MODE_PRIVATE);
                    String uid = sp.getString("uid", "");
                    AppUtil.colsePhoneKeyboard(ProductTypeActivity.this);
                    Intent intent = new Intent(ProductTypeActivity.this, SearchDataActivity.class);
                    intent.putExtra("uid", uid);
                    startActivity(intent);
                }
                break;
            case R.id.img_back:
                finish();
                break;
            case R.id.menu1:
                //综合
                page=1;
                refershMenuUI(0);
                break;
            case R.id.menu2:
                //销量
                page=1;
                refershMenuUI(1);
                break;
            case R.id.menu3:
                //等级
                page=1;
                needShowPopup = true;
                refershMenuUI(2);
                break;
            case R.id.menu4:
                //价格
                if (isManager) {
                    type=2;
                    isManager = false;
                } else {
                    type=1;
                    isManager = true;
                }
                page=1;
                refershMenuUI(3);
                break;
        }
    }

    private void refershMenuUI(int position) {
        HashMap<String, String> map = new HashMap<>();
        map.put("bid", String.valueOf(bid));
        map.put("page", String.valueOf(page));

        current = position;
        menu1.setSelected(position == 0);
        menu2.setSelected(position == 1);
        menu3Text.setSelected(position == 2);
        menu4Text.setSelected(position == 3);
        if (position == 3) {
            cid = "";//清空等级
            if (type == 1) {
//                type = 2;
                setRightDrawable(menu4Text, R.drawable.icon_order_up);
                //价格上升
                map.put("type", String.valueOf("4"));
                if (!("classify").equals(flag)){
                    getProductList(map);
                }else {
                    getProductDatas(map);
                }
            } else {
//                type = 1;
                setRightDrawable(menu4Text, R.drawable.icon_order_down);
                //价格下降
                map.put("type", String.valueOf("3"));
                if (!("classify").equals(flag)){
                    getProductList(map);
                }else {
                    getProductDatas(map);
                }
            }
        } else {
            type = 0;
            setRightDrawable(menu4Text, R.drawable.icon_order_normal);
        }
        switch (position) {
            case 0:
                //综合
                map.put("type", String.valueOf("1"));
                if (!("classify").equals(flag)){
                    getProductList(map);
                }else {
                    getProductDatas(map);
                }
                cid = "";//清空等级
                break;
            case 1:
                //销量
                map.put("type", String.valueOf("2"));
                if (!("classify").equals(flag)){
                    getProductList(map);
                }else {
                    getProductDatas(map);
                }
                cid = "";//清空等级
                break;
            case 2:
                //等级
                if (levels.isEmpty()) {
                    getLevels();
                } else {
                    if (selectLevelsPopupWindow != null && !selectLevelsPopupWindow.isShowing() && !needShowPopup) {
                        requestLevelsProductList(cid);
                    } else {
                        showPop();
                    }
                }
                break;
        }

    }

    private void showPop() {
        selectLevelsPopupWindow = new SelectLevelsPopupWindow();
        selectLevelsPopupWindow.init(this, cid);
        selectLevelsPopupWindow.showPop(menu3Text, levels, new SelectLevelsPopupWindow.OnMenuSelectListener() {
            @Override
            public void select(String tempCid, String cname) {
                cid = tempCid;
                requestLevelsProductList(tempCid);
            }
        });
        needShowPopup = false;
    }

    private void requestLevelsProductList(String tempCid) {
        HashMap<String, String> map = new HashMap<>();
        map.put("bid", String.valueOf(bid));
        map.put("page", String.valueOf(page));
        map.put("type", "5");
        map.put("cid", tempCid);

        if (!("classify").equals(flag)){
            getProductList(map);
        }else {
            getProductDatas(map);
        }
    }

    private void getLevels() {
        NetWork.getService(ImpService.class)
                .GradeIntroduce()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<GradeIntroduceBean>() {

                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(GradeIntroduceBean gradeIntroduceBean) {
                        if ("0".equals(gradeIntroduceBean.getCode())) {
                            List<GradeIntroduceBean.DataBean> data = gradeIntroduceBean.getData();
                            if (data != null) {
                                int size = data.size();
                                levels.clear();
                                for (int i = 0; i < size; i++) {
                                    GradeIntroduceBean.DataBean item = data.get(i);
                                    SelectLevelsPopupWindow.Levels level = new SelectLevelsPopupWindow.Levels();
                                    level.setCid(item.getCid());
                                    level.setCname(item.getCategory_name());
                                    levels.add(level);
                                }
                                showPop();//显示等级窗口
                            }
                        }
                    }
                });
    }

    private void setRightDrawable(TextView textView, int resourceId) {
        Drawable drawable = getResources().getDrawable(resourceId);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        textView.setCompoundDrawables(null, null, drawable, null);
    }

    @Override
    public void onRefresh() {
        page = 1;
        refershMenuUI(current);
    }

    /**
     * 获取商品列表
     */
    private void getProductList(HashMap<String, String> map) {
        //入参  bid  type page cid    tips:type=5时 cid传
        NetWork.getRetrofit()
                .create(ImpService.class)
                .getProductList(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ProductListResponse>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        releaseRefreshAndLodeMore();
                    }

                    @Override
                    public void onNext(ProductListResponse response) {
                        releaseRefreshAndLodeMore();
                        if ("0".equals(response.getCode())) {
                            if (page == 1) {
                                data.clear();
                                if (response.getData().isEmpty()) {
                                    emptyView.setVisibility(View.VISIBLE);
                                } else {
                                    emptyView.setVisibility(View.GONE);
                                }
                            } else {
                                if (page == response.getLast_page()) {
                                    recyclerView.setNoMore(true);
                                }
                            }
                            data.addAll(response.getData());
                            showData(response);
                            if (recyclerView != null) {
                                recyclerView.getAdapter().notifyDataSetChanged();
                            }
                        } else {
                            showToastMessage(response.getMsg());
                        }
                    }
                });
    }

    private void getProductDatas(HashMap<String, String> map) {
        //入参  bid  type page cid    tips:type=5时 cid传
        NetWork.getRetrofit()
                .create(ImpService.class)
                .getProductDatas(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ProductListResponse>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        releaseRefreshAndLodeMore();
                    }

                    @Override
                    public void onNext(ProductListResponse response) {
                        releaseRefreshAndLodeMore();
                        if ("0".equals(response.getCode())) {
                            if (page == 1) {
                                data.clear();
                                if (response.getData().isEmpty()) {
                                    emptyView.setVisibility(View.VISIBLE);
                                } else {
                                    emptyView.setVisibility(View.GONE);
                                }
                            } else {
                                if (page == response.getLast_page()) {
                                    recyclerView.setNoMore(true);
                                }
                            }
                            data.addAll(response.getData());
                            showData(response);
                            if (recyclerView != null) {
                                recyclerView.getAdapter().notifyDataSetChanged();
                            }
                        } else {
                            showToastMessage(response.getMsg());
                        }
                    }
                });
    }

    private void showData(ProductListResponse response) {
        this.response = response;
    }


    private void releaseRefreshAndLodeMore() {
        if (swipeRefreshLayout != null) {
            swipeRefreshLayout.setRefreshing(false);
        }
    }
}
