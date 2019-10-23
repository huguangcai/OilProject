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

public class LoginOutDilaog extends ABSDialog {
    private Context context;

    public LoginOutDilaog(@NonNull Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void initView() {
        TextView tv_cancle = getViewById(R.id.tv_cancle);
        TextView tv_login_out = getViewById(R.id.tv_login_out);
        tv_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        tv_login_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences.Editor is_first = context.getSharedPreferences("IS_FIRST", Context.MODE_PRIVATE).edit();
                is_first.clear();
                is_first.commit();
                SharedPreferences.Editor save_pwd = context.getSharedPreferences("SAVE_PWD", Context.MODE_PRIVATE).edit();
                save_pwd.clear();
                save_pwd.commit();
                SharedPreferences.Editor spUid = context.getSharedPreferences("UID", Context.MODE_PRIVATE).edit();
                spUid.clear();
                spUid.commit();

                ActivityPageManager instance = ActivityPageManager.getInstance();
                instance.finishAllActivity();
                context.startActivity(new Intent(context,LoginActivity.class));
                dismiss();
            }
        });

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.login_out_dialog_layout;
    }
}
