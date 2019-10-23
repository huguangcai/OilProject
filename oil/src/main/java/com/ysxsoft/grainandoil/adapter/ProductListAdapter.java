package com.ysxsoft.grainandoil.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.ysxsoft.grainandoil.R;
import com.ysxsoft.grainandoil.com.ListBaseAdapter;
import com.ysxsoft.grainandoil.com.SuperViewHolder;
import com.ysxsoft.grainandoil.modle.ProductListResponse;

public class ProductListAdapter extends ListBaseAdapter<ProductListResponse.DataBean> {
    public ProductListAdapter(Context context) {
        super(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_product_type;
    }

    @Override
    public void onBindItemHolder(SuperViewHolder holder, int position) {
        ProductListResponse.DataBean dataBean = mDataList.get(position);

        final TextView tv_goods_desc = holder.getView(R.id.tv_goods_desc);
        final TextView tv_price = holder.getView(R.id.tv_price);
        final TextView tv_pintuan_num = holder.getView(R.id.tv_pintuan_num);
        final TextView level = holder.getView(R.id.level);
        final ImageView goodPic = holder.getView(R.id.img_goods_tupian);
//                ImageLoadUtil.GlideGoodsImageLoad(mContext, item.getImgurl(), goodPic);
        RequestOptions options = new RequestOptions().error(R.mipmap.ic_launcher).bitmapTransform(new RoundedCorners(60));//图片圆角为60
        Glide.with(mContext).load(dataBean.getImgurl()) //图片地址
                .apply(options)
                .into(goodPic);
        //名称
        tv_goods_desc.setText(dataBean.getGoods_name());
        //价格
        tv_price.setText( dataBean.getPrice());
        //销量
        tv_pintuan_num.setText( String.valueOf(dataBean.getGroup()));
        //等级
        level.setText(String.valueOf(dataBean.getCategory_name()));

    }
}
