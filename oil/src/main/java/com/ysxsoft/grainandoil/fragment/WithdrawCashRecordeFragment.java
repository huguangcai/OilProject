package com.ysxsoft.grainandoil.fragment;

import com.ysxsoft.grainandoil.R;
import com.ysxsoft.grainandoil.utils.BaseFragment;

/**
 * 提现记录的fragment
 */
public class WithdrawCashRecordeFragment extends BaseFragment {
    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    protected int getLayoutResId() {
//        return R.layout.recharge_recorde_layout;
        return R.layout.withdraw_cash_recorde_item_layout;
    }
}
