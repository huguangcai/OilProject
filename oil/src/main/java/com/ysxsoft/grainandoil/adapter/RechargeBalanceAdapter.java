package com.ysxsoft.grainandoil.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ysxsoft.grainandoil.R;
import com.ysxsoft.grainandoil.com.ListBaseAdapter;
import com.ysxsoft.grainandoil.com.SuperViewHolder;
import com.ysxsoft.grainandoil.modle.GradeIntroduceBean;

public class RechargeBalanceAdapter extends ListBaseAdapter<GradeIntroduceBean.DataBean> {
    public RechargeBalanceAdapter(Context context) {
        super(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.recharge_balance_item_layout;
    }

    @Override
    public void onBindItemHolder(SuperViewHolder holder, int position) {
        GradeIntroduceBean.DataBean dataBean = mDataList.get(position);
        ImageView img_tupian = holder.getView(R.id.img_tupian);
        TextView tv_jibie = holder.getView(R.id.tv_jibie);
        TextView tv_desc = holder.getView(R.id.tv_desc);
        tv_jibie.setText(dataBean.getCategory_name());
        tv_desc.setText(dataBean.getTexts());
        switch (dataBean.getGrade()) {
            case 1://白金级
                img_tupian.setBackgroundResource(R.mipmap.img_bj);
                break;
            case 2://钻石级"
                img_tupian.setBackgroundResource(R.mipmap.img_zs);
                break;
            case 3://星耀级",
                img_tupian.setBackgroundResource(R.mipmap.img_xy);
                break;
            case 4:// "荣耀级",
                img_tupian.setBackgroundResource(R.mipmap.img_ry);
                break;
            case 5://"至尊级",
                img_tupian.setBackgroundResource(R.mipmap.img_zz);
                break;
        }
    }
}
