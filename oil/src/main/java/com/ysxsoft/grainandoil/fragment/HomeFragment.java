package com.ysxsoft.grainandoil.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.ysxsoft.grainandoil.R;
import com.ysxsoft.grainandoil.adapter.HomeAdapter;
import com.ysxsoft.grainandoil.adapter.HomeRecommendAdapter;
import com.ysxsoft.grainandoil.impservice.ImpService;
import com.ysxsoft.grainandoil.modle.BalanceMoneyBean;
import com.ysxsoft.grainandoil.modle.HomeClassifyBean;
import com.ysxsoft.grainandoil.modle.HomeDialogBean;
import com.ysxsoft.grainandoil.modle.HomeLunBoBean;
import com.ysxsoft.grainandoil.modle.HotGoodsBean;
import com.ysxsoft.grainandoil.modle.RecommendBean;
import com.ysxsoft.grainandoil.utils.ActivityPageManager;
import com.ysxsoft.grainandoil.utils.AppUtil;
import com.ysxsoft.grainandoil.utils.CustomDialog;
import com.ysxsoft.grainandoil.utils.IsLoginUtils;
import com.ysxsoft.grainandoil.utils.NetWork;
import com.ysxsoft.grainandoil.view.LoginActivity;
import com.ysxsoft.grainandoil.view.MyInfoActivity;
import com.ysxsoft.grainandoil.view.SearchDataActivity;
import com.ysxsoft.grainandoil.widget.HomeDialog;

import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 首页的Fragment
 */
public class HomeFragment extends Fragment implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {

    private ImageView img_title_right;
    private RelativeLayout rl_search;
    private int stateBar;
    private EditText ed_title_search;
    private String uid;
    private HomeRecommendAdapter mDataAdapter;
    private RecommendBean recommendBean;
    private RecyclerView recyclerView;
    private List<RecommendBean.DataBean> recommendDatas;
    private List<HotGoodsBean.DataBean> hotDatas;
    private List<HomeClassifyBean.DataBean> baDatas;
    private List<HomeLunBoBean.DataBean> lunBoDatas;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_layout, null);
        stateBar = getStateBar();
        SharedPreferences sp = getActivity().getSharedPreferences("UID", Context.MODE_PRIVATE);
        uid = sp.getString("uid", "");
        initView(view);
        requestLunBoData();//轮播
        requestClassifyData();//获取八宫格数据
        requestHorizontalBannerData();//RecyclerView 水平滑动的数据
        requestNormalData();//正常数据
        initListener();

        return view;
    }

    @Override
    public void onRefresh() {
        requestLunBoData();//轮播
        requestClassifyData();//获取八宫格数据
        requestHorizontalBannerData();//RecyclerView 水平滑动的数据
        requestNormalData();//正常数据
        mSwipeRefreshLayout.setRefreshing(false);
    }

    /**
     * 已读和未读消息获取
     */
    private void requesrInfoData() {
        NetWork.getService(ImpService.class)
                .BalanceMoneyData(uid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BalanceMoneyBean>() {
                    private BalanceMoneyBean balanceMoneyBean;

                    @Override
                    public void onCompleted() {
                        if ("0".equals(balanceMoneyBean.getCode())) {
                            if (balanceMoneyBean.getData().getNews() == 0) {//0是未读1是已读
                                img_title_right.setBackgroundResource(R.mipmap.img_home_info);
                            } else {
                                img_title_right.setBackgroundResource(R.mipmap.img_no_info);
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(BalanceMoneyBean balanceMoneyBean) {
                        this.balanceMoneyBean = balanceMoneyBean;
                    }
                });

    }

    private void requestLunBoData() {
        NetWork.getRetrofit()
                .create(ImpService.class)
                .HomeLunBo()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<HomeLunBoBean>() {
                    private HomeLunBoBean homeLunBoBean;

                    @Override
                    public void onCompleted() {
                        if ("0".equals(homeLunBoBean.getCode())) {
                            lunBoDatas = homeLunBoBean.getData();
                            if (lunBoDatas != null && baDatas != null && hotDatas != null && recommendDatas != null) {
                                HomeAdapter adapter = new HomeAdapter(getActivity(), lunBoDatas, baDatas, hotDatas, recommendDatas);
                                recyclerView.setAdapter(adapter);
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println("轮播====="+e.getMessage());
                    }

                    @Override
                    public void onNext(HomeLunBoBean homeLunBoBean) {
                        this.homeLunBoBean = homeLunBoBean;
                    }
                });
    }

    /**
     * 获取八宫格数据
     */
    private void requestClassifyData() {
        NetWork.getRetrofit()
                .create(ImpService.class)
                .HomeClassify()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<HomeClassifyBean>() {
                    private HomeClassifyBean homeClassifyBean;

                    @Override
                    public void onCompleted() {
                        if ("0".equals(homeClassifyBean.getCode())) {
                            baDatas = homeClassifyBean.getData();
                            if (lunBoDatas != null && baDatas != null && hotDatas != null && recommendDatas != null) {
                                HomeAdapter adapter = new HomeAdapter(getActivity(), lunBoDatas, baDatas, hotDatas, recommendDatas);
                                recyclerView.setAdapter(adapter);
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println("八宫格数据====="+e.getMessage());
                    }

                    @Override
                    public void onNext(HomeClassifyBean homeClassifyBean) {
                        this.homeClassifyBean = homeClassifyBean;
                    }
                });
    }

    /**
     * RecyclerView 水平滑动的数据  热门数据
     */
    private void requestHorizontalBannerData() {
        NetWork.getRetrofit()
                .create(ImpService.class)
                .HotGoodsData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<HotGoodsBean>() {
                    private HotGoodsBean hotGoodsBean;

                    @Override
                    public void onCompleted() {
                        if ("0".equals(hotGoodsBean.getCode())) {
                            hotDatas = hotGoodsBean.getData();
                            if (lunBoDatas != null && baDatas != null && hotDatas != null && recommendDatas != null) {
                                HomeAdapter adapter = new HomeAdapter(getActivity(), lunBoDatas, baDatas, hotDatas, recommendDatas);
                                recyclerView.setAdapter(adapter);
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println("水平滑动====="+e.getMessage());
                    }

                    @Override
                    public void onNext(HotGoodsBean hotGoodsBean) {
                        this.hotGoodsBean = hotGoodsBean;
                    }
                });
    }

    /**
     * 推荐数据
     */
    private void requestNormalData() {
        NetWork.getRetrofit()
                .create(ImpService.class)
                .RecommendData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<RecommendBean>() {
                    private RecommendBean recommendBean;

                    @Override
                    public void onCompleted() {
                        if ("0".equals(recommendBean.getCode())) {
                            recommendDatas = recommendBean.getData();
                            if (lunBoDatas != null && baDatas != null && hotDatas != null && recommendDatas != null) {
                                HomeAdapter adapter = new HomeAdapter(getActivity(), lunBoDatas, baDatas, hotDatas, recommendDatas);
                                recyclerView.setAdapter(adapter);
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
//                        Toast.makeText(getActivity(),e.getMessage(),Toast.LENGTH_SHORT).show();
                        System.out.println("推荐数据====="+e.getMessage());
                    }

                    @Override
                    public void onNext(RecommendBean recommendBean) {
                        this.recommendBean = recommendBean;
                    }
                });
    }

    private void initListener() {
        rl_search.setOnClickListener(this);
        img_title_right.setOnClickListener(this);
        ed_title_search.setOnClickListener(this);
    }

    private void initView(View view) {
        RelativeLayout ll_title = view.findViewById(R.id.ll_title);
        ll_title.setPadding(0, stateBar, 0, 0);
        rl_search = view.findViewById(R.id.rl_search);
        img_title_right = view.findViewById(R.id.img_title_right);
        ed_title_search = view.findViewById(R.id.ed_title_search);
        ed_title_search.setFocusable(false);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
        recyclerView = view.findViewById(R.id.recyclerView);
        GridLayoutManager manager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(manager);

        //设置刷新时动画的颜色，可以设置4个
        if (mSwipeRefreshLayout != null) {
            mSwipeRefreshLayout.setProgressViewOffset(false, 0, AppUtil.dip2px(getActivity(), 48));
            mSwipeRefreshLayout.setColorSchemeResources(R.color.btn_color);
            mSwipeRefreshLayout.setOnRefreshListener(this);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_title_right:
                if (IsLoginUtils.isloginFragment(getActivity())) {
                    getActivity().startActivity(new Intent(getActivity(), LoginActivity.class));
                } else {
                    Intent intent = new Intent(getActivity(), MyInfoActivity.class);
                    intent.putExtra("uid", uid);
                    startActivity(intent);
                }
                break;
            case R.id.ed_title_search:
            case R.id.rl_search:
                AppUtil.colsePhoneKeyboard(getActivity());
                Intent intent2 = new Intent(getActivity(), SearchDataActivity.class);
                intent2.putExtra("uid", uid);
                startActivity(intent2);
                break;
        }
    }

    /**
     * 跳转界面
     *
     * @param clazz
     */
    protected void startActivity(Class clazz) {
        startActivity(new Intent(getActivity(), clazz));
    }

    /**
     * 获取状态栏高度
     *
     * @return
     */
    protected int getStateBar() {
        int result = 0;
        int resourceId = this.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = this.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    @Override
    public void onResume() {
        super.onResume();
        requesrInfoData();
    }
}
