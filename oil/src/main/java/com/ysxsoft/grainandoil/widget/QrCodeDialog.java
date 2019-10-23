package com.ysxsoft.grainandoil.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;

import com.ysxsoft.grainandoil.R;

/**
 * Create By 胡
 * on 2019/6/12 0012
 */
public class QrCodeDialog extends ABSDialog {
    public QrCodeDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void initView() {
        ImageView img_save_qrcode = getViewById(R.id.img_save_qrcode);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.qrcode_dialog_layout;
    }
}
