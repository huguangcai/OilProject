package com.ysxsoft.grainandoil.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.ysxsoft.grainandoil.R;
import com.ysxsoft.grainandoil.com.ListBaseAdapter;
import com.ysxsoft.grainandoil.com.SuperViewHolder;
import com.ysxsoft.grainandoil.modle.FactorListBean;
import com.ysxsoft.grainandoil.utils.AppUtil;

/**
 * Create By 胡
 * on 2019/7/12 0012
 */
public class UserPersonAdapter extends ListBaseAdapter<FactorListBean.DataBean> {

    public UserPersonAdapter(Context context) {
        super(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.user_person_item_layout;
    }

    @Override
    public void onBindItemHolder(SuperViewHolder holder, int position) {
        FactorListBean.DataBean bean = mDataList.get(position);
        TextView tv_nikeName = holder.getView(R.id.tv_nikeName);
        TextView tv_mobile = holder.getView(R.id.tv_mobile);
        TextView tv_acount = holder.getView(R.id.tv_acount);
        tv_nikeName.setText("昵称："+bean.getNickname());
        tv_mobile.setText("手机号："+ AppUtil.subBefore3Num(bean.getMobile(),7)+"****");
        tv_acount.setText("账号："+AppUtil.subBefore3Num(bean.getUsername(),5)+"****");

    }
}
