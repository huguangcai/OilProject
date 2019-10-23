package com.ysxsoft.grainandoil.adapter;

import android.content.Context;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.umeng.commonsdk.debug.E;
import com.ysxsoft.grainandoil.R;
import com.ysxsoft.grainandoil.com.ListBaseAdapter;
import com.ysxsoft.grainandoil.com.SuperViewHolder;
import com.ysxsoft.grainandoil.modle.GradeIntroduceBean;

public class GradeIntroduceAdapter extends ListBaseAdapter<GradeIntroduceBean.DataBean> {
    public GradeIntroduceAdapter(Context context) {
        super(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.grade_introduce_item_layout;
    }

    @Override
    public void onBindItemHolder(SuperViewHolder holder, int position) {
        GradeIntroduceBean.DataBean dataBean = mDataList.get(position);
        ImageView img_tupian = holder.getView(R.id.img_tupian);
        TextView tv_desc = holder.getView(R.id.tv_desc);
        TextView tv_jibie = holder.getView(R.id.tv_jibie);
        TextView tv_vip_introduce = holder.getView(R.id.tv_vip_introduce);
        WebView web_content = holder.getView(R.id.web_content);
        tv_jibie.setText(dataBean.getCategory_name());
        tv_desc.setText(dataBean.getTexts());

        WebSettings webSettings = web_content.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setTextSize(WebSettings.TextSize.LARGEST);
        web_content.setWebViewClient(new MyWebViewClient());
        web_content.loadDataWithBaseURL(null, dataBean.getCounts(), "text/html", "utf-8", null);
        switch (dataBean.getGrade()) {
            case 1://白金级
                img_tupian.setBackgroundResource(R.mipmap.img_bj);
                tv_vip_introduce.setText("白金会员介绍");
                tv_jibie.setTextColor(mContext.getResources().getColor(R.color.hint_text_color));
                break;
            case 2://钻石级"
                img_tupian.setBackgroundResource(R.mipmap.img_zs);
                tv_vip_introduce.setText("钻石会员介绍");
                tv_jibie.setTextColor(mContext.getResources().getColor(R.color.zs_color));
                break;
            case 3://星耀级",
                img_tupian.setBackgroundResource(R.mipmap.img_xy);
                tv_vip_introduce.setText("星耀会员介绍");
                tv_jibie.setTextColor(mContext.getResources().getColor(R.color.xy_color));
                break;
            case 4:// "荣耀级",
                img_tupian.setBackgroundResource(R.mipmap.img_ry);
                tv_vip_introduce.setText("荣耀会员介绍");
                tv_jibie.setTextColor(mContext.getResources().getColor(R.color.ry_color));
                break;
            case 5://"至尊级",
                img_tupian.setBackgroundResource(R.mipmap.img_zz);
                tv_vip_introduce.setText("至尊会员介绍");
                tv_jibie.setTextColor(mContext.getResources().getColor(R.color.zz_color));
                break;
        }
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
