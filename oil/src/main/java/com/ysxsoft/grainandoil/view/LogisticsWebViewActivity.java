package com.ysxsoft.grainandoil.view;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.ysxsoft.grainandoil.R;
import com.ysxsoft.grainandoil.utils.BaseActivity;

public class LogisticsWebViewActivity extends BaseActivity implements View.OnClickListener {

    private View img_back;
    private WebView web_content;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.logistics_layout);
        url = getIntent().getStringExtra("url");
        initView();
        initListener();
    }

    private void initView() {
        img_back = getViewById(R.id.img_back);
        TextView tv_title = getViewById(R.id.tv_title);
        tv_title.setText("物流详情");
        web_content = getViewById(R.id.web_content);
        WebSettings webSettings = web_content.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);

        //支持javascript
        web_content.getSettings().setJavaScriptEnabled(true);
        // 设置可以支持缩放
        web_content.getSettings().setSupportZoom(true);
        // 设置出现缩放工具
        web_content.getSettings().setBuiltInZoomControls(true);
        //扩大比例的缩放
        web_content.getSettings().setUseWideViewPort(true);
        //自适应屏幕
        web_content.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        web_content.getSettings().setLoadWithOverviewMode(true);

//        webSettings.setTextSize(WebSettings.TextSize.LARGEST);
        web_content.setWebViewClient(new MyWebViewClient());
        web_content.loadUrl(url);
    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        // 在WebView中而不在默认浏览器中显示页面
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

    private void initListener() {
        img_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                finish();
                break;
        }
    }
}
