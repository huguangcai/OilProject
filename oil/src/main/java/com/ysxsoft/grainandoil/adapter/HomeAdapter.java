package com.ysxsoft.grainandoil.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.stx.xhb.pagemenulibrary.PageMenuLayout;
import com.stx.xhb.pagemenulibrary.holder.AbstractHolder;
import com.stx.xhb.pagemenulibrary.holder.PageMenuViewHolderCreator;
import com.umeng.commonsdk.debug.I;
import com.ysxsoft.grainandoil.MainActivity;
import com.ysxsoft.grainandoil.R;
import com.ysxsoft.grainandoil.modle.HomeClassifyBean;
import com.ysxsoft.grainandoil.modle.HomeLunBoBean;
import com.ysxsoft.grainandoil.modle.HotGoodsBean;
import com.ysxsoft.grainandoil.modle.RecommendBean;
import com.ysxsoft.grainandoil.utils.ActivityPageManager;
import com.ysxsoft.grainandoil.utils.ImageLoadUtil;
import com.ysxsoft.grainandoil.utils.IsLoginUtils;
import com.ysxsoft.grainandoil.utils.NetWork;
import com.ysxsoft.grainandoil.utils.ScreenUtil;
import com.ysxsoft.grainandoil.view.LoginActivity;
import com.ysxsoft.grainandoil.com.GallerySnapHelper;
import com.ysxsoft.grainandoil.view.ProductTypeActivity;
import com.ysxsoft.grainandoil.view.SecondHomeActivity;
import com.ysxsoft.grainandoil.view.WebViewActivity;
import com.ysxsoft.grainandoil.widget.CircleImageView;
import com.ysxsoft.grainandoil.widget.MyRecyclerView;
import com.ysxsoft.grainandoil.widget.banner.Banner;
import com.ysxsoft.grainandoil.widget.banner.GlideImageLoader;
import com.ysxsoft.grainandoil.widget.banner.OnBannerListener;
import com.ysxsoft.grainandoil.widget.itemdecoration.GridSpacingItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements OnBannerListener {
    private FragmentActivity context;
    private List<HomeLunBoBean.DataBean> lunBoDatas;
    private List<HomeClassifyBean.DataBean> baDatas;
    private List<HotGoodsBean.DataBean> hotDatas;
    public List<RecommendBean.DataBean> recommendDatas;
    private final int BANNER_VIEW_TYPE = 0;//轮播图
    private final int GRIDE_VIEW_TYPE = 1;//八宫格
    private final int HOT_VIEW_TYPE = 2;//热门商品
    private final int NORMAL_VIEW_TYPE = 3;//正常布局
    private final String uid;
    private OnItemClickListener onItemClickListener;
    private ArrayList<String> urls = new ArrayList<>();
    private BannerHolder bannerHolder;

    public HomeAdapter(FragmentActivity context,
                       List<HomeLunBoBean.DataBean> lunBoDatas,
                       List<HomeClassifyBean.DataBean> baDatas,
                       List<HotGoodsBean.DataBean> hotDatas,
                       List<RecommendBean.DataBean> recommendDatas) {
        SharedPreferences sp = context.getSharedPreferences("UID", Context.MODE_PRIVATE);
        uid = sp.getString("uid", "");
        this.context = context;
        this.lunBoDatas = lunBoDatas;
        this.baDatas = baDatas;
        this.hotDatas = hotDatas;
        this.recommendDatas = recommendDatas;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {//轮播
            return BANNER_VIEW_TYPE;
        } else if (position == 1) {//八宫格
            return GRIDE_VIEW_TYPE;
        } else if (position == 2) {//热门数据
            return HOT_VIEW_TYPE;
        } else {//正常数据
            return NORMAL_VIEW_TYPE;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, final int viewType) {
        View view;
        if (viewType == BANNER_VIEW_TYPE) {
            view = getView(R.layout.home_banner_layout);
            return new BannerHolder(view);
        } else if (viewType == GRIDE_VIEW_TYPE) {
            view = getView(R.layout.gride_layout);
            return new GridHolder(view);
        } else if (viewType == HOT_VIEW_TYPE) {
            view = getView(R.layout.hot_recycleview_layout);
            return new HotHolder(view);
        } else {
            view = getView(R.layout.recommend_item_layout);
            return new NormalHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder viewHolder, int i) {
        if (viewHolder instanceof BannerHolder) {
            bannerHolder = (BannerHolder) viewHolder;
            if (lunBoDatas != null) {
                if (urls.size() != 0) {
                    urls.clear();
                }
                for (int j = 0; j < lunBoDatas.size(); j++) {
                    urls.add(lunBoDatas.get(j).getImgurl());
                }
                ((BannerHolder) viewHolder).vp_banner.setImages(urls)
                        .setImageLoader(new GlideImageLoader())
                        .setOnBannerListener(this)
                        .start();
            }
        } else if (viewHolder instanceof GridHolder) {
            GridLayoutManager manager = new GridLayoutManager(context, 4);
            ((GridHolder) viewHolder).gridView.setLayoutManager(manager);
            HomeClassifyAdapter adapter = new HomeClassifyAdapter(context, baDatas);
            ((GridHolder) viewHolder).gridView.setAdapter(adapter);
            adapter.setOnItemClickListener(new HomeClassifyAdapter.OnItemClickListener() {
                @Override
                public void onClick(int i) {
                        if (i == 7) {//跳转至分类
                            Intent intent = new Intent(context, MainActivity.class);
                            intent.putExtra("flag", "2");
                            context.startActivity(intent);
                            Intent sendintent=new Intent("GONE");
                            context.sendBroadcast(sendintent);
                            context.onBackPressed();
                        } else {//跳转至二级分类页面
//                           HomeClassifyBean.DataBean dataBean = baDatas.get(i);
//                           int id = dataBean.getId();
//                           Intent intent = new Intent(context, SecondHomeActivity.class);
//                           intent.putExtra("gid", String.valueOf(id));
//                           if (!TextUtils.isEmpty(uid) && uid != null) {
//                               intent.putExtra("uid", uid);
//                           }
//                           context.startActivity(intent);
                            ProductTypeActivity.start(context, baDatas.get(i).getId());
                        }
                }
            });

//            ((GridHolder) viewHolder).mPageMenuLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
//            ((GridHolder) viewHolder).mPageMenuLayout.setPageDatas(baDatas, new PageMenuViewHolderCreator() {
//                @Override
//                public AbstractHolder createHolder(View itemView) {
//                    return new AbstractHolder(itemView) {
//
//                        private TextView tv_img_desc;
//                        private CircleImageView img_goods_tupian;
//
//                        @Override
//                        protected void initView(View itemView) {
//                            img_goods_tupian = itemView.findViewById(R.id.img_goods_tupian);
//                            tv_img_desc = itemView.findViewById(R.id.tv_img_desc);
//                            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//                            itemView.setLayoutParams(layoutParams);
//                        }
//
//                        @Override
//                        public void bindView(RecyclerView.ViewHolder holder, Object data, final int pos) {
//                            tv_img_desc.setText(baDatas.get(pos).getCategory_name());
//                            ImageLoadUtil.GlideGoodsImageLoad(context, baDatas.get(pos).getImgurl(), img_goods_tupian);
//                            holder.itemView.setOnClickListener(new View.OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//                                    if (IsLoginUtils.isloginFragment(context)) {
//                                        ActivityPageManager instance = ActivityPageManager.getInstance();
//                                        instance.finishAllActivity();
//                                        context.startActivity(new Intent(context, LoginActivity.class));
//                                    } else {
//                                        HomeClassifyBean.DataBean dataBean = baDatas.get(pos);
//                                        int id = dataBean.getId();
//                                        Intent intent = new Intent(context, SecondHomeActivity.class);
//                                        intent.putExtra("gid", String.valueOf(id));
//                                        if (!TextUtils.isEmpty(uid) && uid != null) {
//                                            intent.putExtra("uid", uid);
//                                        }
//                                        context.startActivity(intent);
//                                    }
//                                }
//                            });
//
//                        }
//                    };
//                }
//
//                @Override
//                public int getLayoutId() {
//                    return R.layout.home_classify_item_layout;
//                }
//            });
        } else if (viewHolder instanceof HotHolder) {

            LinearLayoutManager layoutManager = new LinearLayoutManager(context);
            layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            ((HotHolder) viewHolder).hsv_list.setLayoutManager(layoutManager);
            GallerySnapHelper snapHelper = new GallerySnapHelper();
            ((HotHolder) viewHolder).hsv_list.setOnFlingListener(null);
            snapHelper.attachToRecyclerView(((HotHolder) viewHolder).hsv_list);
            if (hotDatas != null) {
                final HotGoddsAdapter adapter = new HotGoddsAdapter(context, hotDatas);
                ((HotHolder) viewHolder).hsv_list.setAdapter(adapter);
                adapter.setOnItemClickListener(new HotGoddsAdapter.OnItemClickListener() {
                    @Override
                    public void onClick(int i) {
                            Intent intent = new Intent(context, WebViewActivity.class);
                            String gid = String.valueOf(adapter.data.get(i).getGid());
                            intent.putExtra("gid", String.valueOf(gid));
                            if (!TextUtils.isEmpty(uid) && uid != null) {
                                intent.putExtra("uid", uid);
                            }
                            String url = NetWork.H5BaseUrl + "commodityDetails?gid=" + gid + "&uid=" + uid + "&flag=1";
                            intent.putExtra("url", url);
                            context.startActivity(intent);
                        }
                });
            }
        } else if (viewHolder instanceof NormalHolder) {
            RecommendBean.DataBean dataBean = recommendDatas.get(viewHolder.getAdapterPosition() - 3);
            Log.e("tag", "url:" + dataBean.getImgurl());
            NormalHolder holder = (NormalHolder) viewHolder;

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        RecommendBean.DataBean dataBean = recommendDatas.get(viewHolder.getAdapterPosition() - 3);
                        String gid = String.valueOf(dataBean.getGid());
                        Intent intent = new Intent(context, WebViewActivity.class);
                        intent.putExtra("gid", String.valueOf(gid));
                        if (!TextUtils.isEmpty(uid) && uid != null) {
                            intent.putExtra("uid", uid);
                        }
                        String url = NetWork.H5BaseUrl + "commodityDetails?gid=" + gid + "&uid=" + uid + "&flag=1";
                        intent.putExtra("url", url);
                        context.startActivity(intent);
                    }
            });
            ImageLoadUtil.GlideGoodsImageLoad(context, dataBean.getImgurl(), holder.img_goods_tupian);
            ((NormalHolder) viewHolder).tv_goods_desc.setText(dataBean.getGoods_name());
            ((NormalHolder) viewHolder).tv_price.setText(dataBean.getPrice());
            ((NormalHolder) viewHolder).tv_pintuan_num.setText(String.valueOf(dataBean.getGroup()));
        }
    }

    @Override
    public int getItemCount() {
        return recommendDatas.size() + 3;
    }

    /**
     * 用来引入布局的方法
     */
    private View getView(int view) {
        View view1 = View.inflate(context, view, null);
        return view1;
    }

    @Override
    public void OnBannerClick(int position) {
        String gid = String.valueOf(lunBoDatas.get(position).getGid());
        Intent intent = new Intent(context, WebViewActivity.class);
        intent.putExtra("gid", String.valueOf(gid));
        if (!TextUtils.isEmpty(uid) && uid != null) {
            intent.putExtra("uid", uid);
        }
        String url = NetWork.H5BaseUrl + "commodityDetails?gid=" + gid + "&uid=" + uid + "&flag=1";
        intent.putExtra("url", url);
        context.startActivity(intent);
    }


    /**
     * 轮播的holder
     */
    public static class BannerHolder extends RecyclerView.ViewHolder {
        ViewPager banner;
        private final LinearLayout ll_point;
        private final Banner vp_banner;

        public BannerHolder(View itemView) {
            super(itemView);
            vp_banner = itemView.findViewById(R.id.vp_banner);
            banner = (ViewPager) itemView.findViewById(R.id.vp_content);
            ll_point = (LinearLayout) itemView.findViewById(R.id.ll_point);
        }
    }

    /**
     * 八宫格ViewHolder
     */
    public static class GridHolder extends RecyclerView.ViewHolder {
        private final RecyclerView gridView;
//        private final PageMenuLayout<HomeClassifyBean.DataBean> mPageMenuLayout;

        public GridHolder(View itemView) {
            super(itemView);
            gridView = itemView.findViewById(R.id.rv_list);
//            mPageMenuLayout = itemView.findViewById(R.id.pagemenu);
        }
    }

    public static class HotHolder extends RecyclerView.ViewHolder {
        private final RecyclerView hsv_list;

        public HotHolder(@NonNull View itemView) {
            super(itemView);
            hsv_list = itemView.findViewById(R.id.hsv_list);
        }
    }

    /**
     * 正常布局的ViewHolder
     */
    public static class NormalHolder extends RecyclerView.ViewHolder {
        ImageView img_goods_tupian;
        TextView tv_goods_desc;
        TextView tv_price;
        TextView tv_pintuan_num;

        public NormalHolder(View itemView) {
            super(itemView);
            img_goods_tupian = (ImageView) itemView.findViewById(R.id.img_goods_tupian);
            tv_goods_desc = (TextView) itemView.findViewById(R.id.tv_goods_desc);
            tv_price = (TextView) itemView.findViewById(R.id.tv_price);
            tv_pintuan_num = (TextView) itemView.findViewById(R.id.tv_pintuan_num);
        }
    }

    public interface OnItemClickListener {
        void OnClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            final GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
            final GridLayoutManager.SpanSizeLookup spanSizeLookup = gridLayoutManager.getSpanSizeLookup();
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    if (position == 0 || position == 1 || position == 2) {
                        return gridLayoutManager.getSpanCount();
                    }
                    return 1;
                }
            });
            gridLayoutManager.setSpanCount(gridLayoutManager.getSpanCount());
        }
    }

}
