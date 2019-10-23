package com.ysxsoft.grainandoil.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.ysxsoft.grainandoil.R;
import com.ysxsoft.grainandoil.com.ListBaseAdapter;
import com.ysxsoft.grainandoil.com.SuperViewHolder;
import com.ysxsoft.grainandoil.modle.AgencyListBean;

public class InComeAdapter extends ListBaseAdapter<AgencyListBean.DataBean> {
    public InComeAdapter(Context context) {
        super(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.income_item_layout;
    }

    @Override
    public void onBindItemHolder(SuperViewHolder holder, int position) {
        AgencyListBean.DataBean dataBean = mDataList.get(position);
        TextView tv_time = holder.getView(R.id.tv_time);
        TextView tv_income = holder.getView(R.id.tv_income);
        tv_time.setText(dataBean.getAddtime());
        tv_income.setText(dataBean.getEarnings());
    }
}
