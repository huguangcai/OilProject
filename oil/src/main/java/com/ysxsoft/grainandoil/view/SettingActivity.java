package com.ysxsoft.grainandoil.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ysxsoft.grainandoil.R;
import com.ysxsoft.grainandoil.impservice.ImpService;
import com.ysxsoft.grainandoil.modle.AcountSafeBean;
import com.ysxsoft.grainandoil.modle.CustomerPhoneNumBean;
import com.ysxsoft.grainandoil.utils.AppUtil;
import com.ysxsoft.grainandoil.utils.BaseActivity;
import com.ysxsoft.grainandoil.utils.NetWork;
import com.ysxsoft.grainandoil.widget.CustomerPhoneDialog;
import com.ysxsoft.grainandoil.widget.LoginOutDilaog;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class SettingActivity extends BaseActivity implements View.OnClickListener {

    private View img_back;
    private LinearLayout ll_account_security, ll_about_platform, ll_customer_phone, ll_version_updata, ll_help_center, ll_login_out, ll_shiming;
    private String uid;
    private String tradepassword;
    private String mobile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_setting_layout);
        Intent intent = getIntent();
        uid = intent.getStringExtra("uid");
        initView();
        initListener();
        requestData();
    }

    private void requestData() {
        NetWork.getRetrofit()
                .create(ImpService.class)
                .AcountSafe(uid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<AcountSafeBean>() {
                    private AcountSafeBean acountSafeBean;

                    @Override
                    public void onCompleted() {
                        if ("0".equals(acountSafeBean.getCode())) {
                            mobile = acountSafeBean.getData().getMobile();
                            tradepassword = acountSafeBean.getData().getTradepassword();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        showToastMessage(e.getMessage());
                    }

                    @Override
                    public void onNext(AcountSafeBean acountSafeBean) {
                        this.acountSafeBean = acountSafeBean;
                    }
                });
    }


    private void initView() {
        img_back = getViewById(R.id.img_back);
        TextView tv_title = getViewById(R.id.tv_title);
        tv_title.setText("设置");
        ll_account_security = getViewById(R.id.ll_account_security);
        ll_about_platform = getViewById(R.id.ll_about_platform);
        ll_customer_phone = getViewById(R.id.ll_customer_phone);
        ll_version_updata = getViewById(R.id.ll_version_updata);
        ll_help_center = getViewById(R.id.ll_help_center);
        ll_login_out = getViewById(R.id.ll_login_out);
        ll_shiming = getViewById(R.id.ll_shiming);

    }

    private void initListener() {
        img_back.setOnClickListener(this);
        ll_account_security.setOnClickListener(this);
        ll_about_platform.setOnClickListener(this);
        ll_customer_phone.setOnClickListener(this);
        ll_version_updata.setOnClickListener(this);
        ll_help_center.setOnClickListener(this);
        ll_login_out.setOnClickListener(this);
        ll_shiming.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.ll_account_security://账户安全
                Intent intent = new Intent(mContext, AccountSecurityActivity.class);
                intent.putExtra("uid", uid);
                startActivity(intent);
                break;

            case R.id.ll_shiming://实名认证
//                showToastMessage("实名认证");
//                startActivity(AboutPlatformActivity.class);
                Intent intentdailikefu = new Intent(mContext, WebViewActivity.class);
                String s1 = NetWork.H5BaseUrl + "realname?sc=2";
                intentdailikefu.putExtra("url", s1);
                intentdailikefu.putExtra("uid", uid);
                startActivity(intentdailikefu);
                break;
            case R.id.ll_about_platform://关于平台
                startActivity(AboutPlatformActivity.class);
                break;

            case R.id.ll_customer_phone://客户电话
                Intent modifyintent = new Intent(mContext, ModifyLoginPwdActivity.class);
                modifyintent.putExtra("mobile", mobile);
                startActivity(modifyintent);
//                final CustomerPhoneDialog dialog = new CustomerPhoneDialog(mContext);
//                final TextView tv_phone_num = dialog.findViewById(R.id.tv_phone_num);
//                tv_phone_num.setText(phone);
//                TextView tv_call_phone = dialog.findViewById(R.id.tv_call_phone);
//                tv_call_phone.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Intent intent = new Intent(Intent.ACTION_DIAL);
//                        Uri data = Uri.parse("tel:" + tv_phone_num.getText().toString());
//                        intent.setData(data);
//                        startActivity(intent);
//                        dialog.dismiss();
//                    }
//                });
//                dialog.show();
                break;

            case R.id.ll_version_updata://支付密码
//                startActivity(VersionUpdateActivity.class);
                if ("1".equals(tradepassword)) {//tradepassword	1是设置过支付密码2是没有设置过
                    Intent intent1 = new Intent(mContext, ModifyTradePwdActivity.class);
                    intent1.putExtra("uid", uid);
                    intent1.putExtra("mobile", mobile);
                    startActivity(intent1);
                } else {
                    Intent intent2 = new Intent(mContext, TradePwdActivity.class);
                    intent2.putExtra("uid", uid);
                    startActivity(intent2);
                }
                break;

            case R.id.ll_help_center://手机号
//                startActivity(HelpCenterActivity.class);
                Intent intent3 = new Intent(mContext, ModifyPhoneNumActivity.class);
                intent3.putExtra("uid", uid);
                startActivity(intent3);
                break;

            case R.id.ll_login_out://退出登陆
                LoginOutDilaog loginOutDilaog = new LoginOutDilaog(mContext);
                loginOutDilaog.show();
                break;
        }
    }
}
