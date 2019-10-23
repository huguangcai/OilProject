package com.ysxsoft.grainandoil.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.ysxsoft.grainandoil.R;
import com.ysxsoft.grainandoil.impservice.ImpService;
import com.ysxsoft.grainandoil.modle.SystemDetialBean;
import com.ysxsoft.grainandoil.utils.BaseActivity;
import com.ysxsoft.grainandoil.utils.NetWork;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class InfoDetailActivity extends BaseActivity {
    private WebView web_content;
    private String sid;
    private TextView web_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_detail_layout);
        Intent intent = getIntent();
        sid = intent.getStringExtra("sid");
        View img_back = getViewById(R.id.img_back);
        TextView tv_title = getViewById(R.id.tv_title);
        web_title = getViewById(R.id.web_title);
        tv_title.setText("消息详情");
        requestData();
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        web_content = getViewById(R.id.web_content);
        WebSettings webSettings = web_content.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setTextSize(WebSettings.TextSize.LARGEST);
        web_content.setWebViewClient(new MyWebViewClient());

    }

    private void requestData() {
        NetWork.getService(ImpService.class)
                .SystemDetialData(sid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SystemDetialBean>() {
                    private SystemDetialBean systemDetialBean;

                    @Override
                    public void onCompleted() {
                        if ("0".equals(systemDetialBean.getCode())) {
                            web_title.setText(systemDetialBean.getData().getTexts());
                            web_content.loadDataWithBaseURL(null, systemDetialBean.getData().getText(), "text/html", "utf-8", null);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        showToastMessage(e.getMessage());
                    }

                    @Override
                    public void onNext(SystemDetialBean systemDetialBean) {

                        this.systemDetialBean = systemDetialBean;
                    }
                });
    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        // 在WebView中而不在默认浏览器中显示页面
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
}
