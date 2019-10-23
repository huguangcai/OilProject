package com.ysxsoft.grainandoil.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.ysxsoft.grainandoil.R;

public class HomeDialog extends ABSDialog {

    private static String url;

    public static void setUrl(String url){
        HomeDialog.url = url;
    }

    public HomeDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void initView() {
        ImageView img_tupian = getViewById(R.id.img_tupian);

        ImageView img_close = getViewById(R.id.img_close);
        img_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.home_dialog_layout;
    }
}
