package com.ysxsoft.grainandoil.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.ysxsoft.grainandoil.R;
import com.ysxsoft.grainandoil.com.ListBaseAdapter;
import com.ysxsoft.grainandoil.com.SuperViewHolder;
import com.ysxsoft.grainandoil.modle.WalletDetailBean;

public class WalletDetailAdapter extends ListBaseAdapter<WalletDetailBean.DataBean> {

    public WalletDetailAdapter(Context context) {
        super(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.withdraw_cash_recorde_item_layout;
    }

    @Override
    public void onBindItemHolder(SuperViewHolder holder, int position) {
        WalletDetailBean.DataBean dataBean = mDataList.get(position);
        ImageView img_withdraw = holder.getView(R.id.img_withdraw);
        TextView tv_withdraw_cash_type = holder.getView(R.id.tv_withdraw_cash_type);
        TextView tv_time = holder.getView(R.id.tv_time);
        TextView tv_withdraw_cash_money = holder.getView(R.id.tv_withdraw_cash_money);
        tv_withdraw_cash_type.setText(dataBean.getValues());
        tv_time.setText(dataBean.getAddtime());

        switch (dataBean.getGenre()) {
            case "0":
                tv_withdraw_cash_money.setText("-"+dataBean.getFalgs());
                break;
            case "1":
                tv_withdraw_cash_money.setText("+"+dataBean.getFalgs());
                break;
        }
        switch (dataBean.getType()) {
            case 1:
                img_withdraw.setBackgroundResource(R.mipmap.img_withdraw_cash);
                break;
            case 2:
                img_withdraw.setBackgroundResource(R.mipmap.img_withdraw_cash);
                break;
            case 3:
                img_withdraw.setBackgroundResource(R.mipmap.img_withdraw_cash);
                break;
        }
    }
}
