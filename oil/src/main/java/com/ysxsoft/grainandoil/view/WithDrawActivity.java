package com.ysxsoft.grainandoil.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ysxsoft.grainandoil.R;
import com.ysxsoft.grainandoil.utils.BaseActivity;

/**
 * create by Sincerly on 2019/4/28 0028
 **/
public class WithDrawActivity extends BaseActivity implements View.OnClickListener {
    private EditText et_money;
    private Button tv_withdraw_cash;
    private LinearLayout ll_alipay;
    private LinearLayout ll_wx;
    private ImageView img_wechat;
    private ImageView img_alipay;

    public static void start(Context context) {
        Intent intent=new Intent(context,WithDrawActivity.class);
        context.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_with_draw);
        initView();
    }

    private void initView() {
        TextView title = getViewById(R.id.tv_title);
        RelativeLayout back = getViewById(R.id.img_back);
        et_money = getViewById(R.id.et_money);
        ll_alipay = getViewById(R.id.ll_alipay);
        ll_wx = getViewById(R.id.ll_wx);
        img_wechat = getViewById(R.id.img_wechatpay);
        img_alipay = getViewById(R.id.img_alipay);
        tv_withdraw_cash = getViewById(R.id.tv_withdraw_cash);
        title.setText("提现");
        back.setOnClickListener(this);
        ll_wx.setOnClickListener(this);
        ll_alipay.setOnClickListener(this);
        tv_withdraw_cash.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_back:
                finish();
                break;
            case R.id.tv_withdraw_cash:
                //提现
                if("".equals(et_money)){
                    showToastMessage("请输入充值金额");
                    return;
                }
                break;
            case R.id.ll_wx:
                //微信
                img_alipay.setVisibility(View.GONE);
                img_wechat.setVisibility(View.VISIBLE);
                break;
            case R.id.ll_alipay:
                //支付宝
                img_alipay.setVisibility(View.VISIBLE);
                img_wechat.setVisibility(View.GONE);
                break;
        }
    }
}
