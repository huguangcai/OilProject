package com.ysxsoft.grainandoil.fragment;

import com.ysxsoft.grainandoil.R;
import com.ysxsoft.grainandoil.utils.BaseFragment;

/**
 * 消费记录的fragment
 */
public class ConsumeRecordeFragment extends BaseFragment {
    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    protected int getLayoutResId() {
//        return R.layout.recharge_recorde_layout;
        return R.layout.consume_recorde_item_layout;
    }
}
