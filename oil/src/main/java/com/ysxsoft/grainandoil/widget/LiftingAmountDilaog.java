package com.ysxsoft.grainandoil.widget;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.ysxsoft.grainandoil.R;
import com.ysxsoft.grainandoil.utils.ActivityPageManager;
import com.ysxsoft.grainandoil.view.LoginActivity;

public class LiftingAmountDilaog extends ABSDialog {
    private Context context;

    public LiftingAmountDilaog(@NonNull Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void initView() {
        TextView tv_cancle = getViewById(R.id.tv_cancle);
        TextView tv_content = getViewById(R.id.tv_content);
        tv_content.setText("是否要提额？");
        TextView tv_login_out = getViewById(R.id.tv_login_out);
        tv_login_out.setText("确定");
        tv_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });


    }

    @Override
    protected int getLayoutResId() {
        return R.layout.login_out_dialog_layout;
    }
}
