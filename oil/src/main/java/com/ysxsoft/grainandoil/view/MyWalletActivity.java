package com.ysxsoft.grainandoil.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.umeng.commonsdk.debug.E;
import com.ysxsoft.grainandoil.MainActivity;
import com.ysxsoft.grainandoil.R;
import com.ysxsoft.grainandoil.impservice.ImpService;
import com.ysxsoft.grainandoil.modle.BalanceMoneyBean;
import com.ysxsoft.grainandoil.modle.LiftingAmountBean;
import com.ysxsoft.grainandoil.modle.MyMsgBean;
import com.ysxsoft.grainandoil.utils.BaseActivity;
import com.ysxsoft.grainandoil.utils.NetWork;
import com.ysxsoft.grainandoil.widget.LiftingAmountDilaog;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MyWalletActivity extends BaseActivity implements View.OnClickListener {

    private View img_back;
    private TextView tv_balance_money, tv_bill_detail, tv_xingji;
    private LinearLayout ll_wallet_detail, ll_recharge, ll_withdraw_cash, ll_my_bill, ll_introduce, lv_xingji;
    private int stateBar;
    private String uid, dengji, dengjis, zds, zong, type;
    private MyMsgBean.DataBean dataBean;
    private FrameLayout fl_lifting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_wallet_layout);
        Intent intent = getIntent();
        uid = intent.getStringExtra("uid");
        dengji = intent.getStringExtra("dengji");
        dengjis = intent.getStringExtra("dengjis");
        zds = intent.getStringExtra("zds");
        zong = intent.getStringExtra("zong");
        type = intent.getStringExtra("type");
        setHalfTransparent();
        setFitSystemWindow(false);
        stateBar = getStateBar();
        initView();
        initListener();
    }

    /**
     * 获取余额
     */
    private void requestBalanceData() {
        NetWork.getService(ImpService.class)
                .BalanceMoneyData(uid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BalanceMoneyBean>() {
                    private BalanceMoneyBean balanceMoneyBean;

                    @Override
                    public void onCompleted() {
                        if ("0".equals(balanceMoneyBean.getCode())) {
//                            data = balanceMoneyBean.getData();
//                            tv_balance_money.setText(data.getMoney());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        showToastMessage(e.getMessage());
                    }

                    @Override
                    public void onNext(BalanceMoneyBean balanceMoneyBean) {
                        this.balanceMoneyBean = balanceMoneyBean;
                    }
                });
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void initView() {
        RelativeLayout ll_title = getViewById(R.id.ll_title);
        ll_title.setPadding(0, stateBar, 0, 0);
        img_back = getViewById(R.id.img_back);
        TextView tv_title = getViewById(R.id.tv_title);
        tv_title.setText("我的钱包");
        tv_balance_money = getViewById(R.id.tv_balance_money);
        tv_bill_detail = getViewById(R.id.tv_bill_detail);
        lv_xingji = getViewById(R.id.lv_xingji);
        ll_wallet_detail = getViewById(R.id.ll_wallet_detail);
        ll_recharge = getViewById(R.id.ll_recharge);
        ll_withdraw_cash = getViewById(R.id.ll_withdraw_cash);

        fl_lifting = getViewById(R.id.fl_lifting);
        ll_my_bill = getViewById(R.id.ll_my_bill);
        ll_introduce = getViewById(R.id.ll_introduce);
        tv_xingji = getViewById(R.id.tv_xingji);
        tv_xingji.setText(dengjis);
        switch (dengji) {
            case "1"://白金级
                tv_xingji.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(getApplicationContext(), R.drawable.img_bj), null, null, null);
                break;
            case "2"://钻石级"
                tv_xingji.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(getApplicationContext(), R.drawable.img_zs), null, null, null);

                break;
            case "3"://星耀级",
                tv_xingji.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(getApplicationContext(), R.drawable.img_xy), null, null, null);

                break;
            case "4":// "荣耀级",
                tv_xingji.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(getApplicationContext(), R.drawable.img_ry), null, null, null);
                break;
            case "5"://"至尊级",
                tv_xingji.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(getApplicationContext(), R.drawable.img_zz), null, null, null);
                break;
        }
    }

    private void initListener() {
        img_back.setOnClickListener(this);
        ll_wallet_detail.setOnClickListener(this);
        ll_recharge.setOnClickListener(this);
        ll_withdraw_cash.setOnClickListener(this);
        ll_my_bill.setOnClickListener(this);
        ll_introduce.setOnClickListener(this);
        tv_bill_detail.setOnClickListener(this);
        lv_xingji.setOnClickListener(this);
        fl_lifting.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.ll_wallet_detail:
                Intent detailcash = new Intent(mContext, WalletDetailActivity.class);
                detailcash.putExtra("uid", uid);
                startActivity(detailcash);
                break;
            case R.id.ll_recharge:
                Intent recharge = new Intent(mContext, RechargeBalanceActivity.class);
                recharge.putExtra("uid", uid);
                startActivity(recharge);
                break;
            case R.id.ll_withdraw_cash:
                if ("1".equals(dataBean.getZds())) {
                    Intent intent = new Intent(mContext, BillRepaymentActivity.class);
                    intent.putExtra("zong", zong);
                    intent.putExtra("type", type);
                    intent.putExtra("uid", uid);
                    startActivity(intent);
                } else {
                    Intent intent1 = new Intent(mContext, YQBillRepaymentActivity.class);
                    intent1.putExtra("zong", zong);
                    intent1.putExtra("uid", uid);
                    startActivity(intent1);
                }
//                Intent intentcash = new Intent(mContext, WithdrawCashActivity.class);
//                intentcash.putExtra("uid", uid);
//                intentcash.putExtra("money", data.getMoney());
//                intentcash.putExtra("mobile", data.getMobile());
//                startActivity(intentcash);
                break;
            case R.id.ll_my_bill:
//                showToastMessage("我的账单");
                Intent intentbill = new Intent(mContext, MyBillActivity.class);
                intentbill.putExtra("uid", uid);
                startActivity(intentbill);
//                Intent intentdailikefu = new Intent(mContext, WebViewActivity.class);
//                String s1 = NetWork.H5BaseUrl + "mybill";
//                intentdailikefu.putExtra("url", s1);
//                intentdailikefu.putExtra("uid", uid);
//                startActivity(intentdailikefu);
                break;
            case R.id.ll_introduce:
                startActivity(GradeIntroduceActivity.class);
                break;
            case R.id.tv_bill_detail:
                //账单额度
//                startActivity(BillRepaymentActivity.class);
                if ("1".equals(dataBean.getZds())) {
                    Intent intent = new Intent(mContext, BillRepaymentActivity.class);
                    intent.putExtra("zong", zong);
                    intent.putExtra("type", type);
                    intent.putExtra("uid", uid);
                    startActivity(intent);
                } else {
                    Intent intent1 = new Intent(mContext, YQBillRepaymentActivity.class);
                    intent1.putExtra("zong", zong);
                    intent1.putExtra("uid", uid);
                    startActivity(intent1);
                }
                break;
            case R.id.lv_xingji:
//                showToastMessage("星级会员");
//                LevelsActivity.start(this);
                Intent recharge1 = new Intent(MyWalletActivity.this, LevelsActivity.class);
                recharge1.putExtra("dengji", dengji);
                startActivity(recharge1);
                break;
            case R.id.fl_lifting:
                final LiftingAmountDilaog dilaog = new LiftingAmountDilaog(mContext);
                TextView tv_lifting_amount = dilaog.findViewById(R.id.tv_login_out);
                tv_lifting_amount.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        NetWork.getService(ImpService.class)
                                .LiftingAmount(uid, tv_xingji.getText().toString())
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Observer<LiftingAmountBean>() {
                                    @Override
                                    public void onCompleted() {

                                    }

                                    @Override
                                    public void onError(Throwable e) {

                                    }

                                    @Override
                                    public void onNext(LiftingAmountBean liftingAmountBean) {
                                        showToastMessage(liftingAmountBean.getMsg());
                                    }
                                });
                        dilaog.dismiss();
                    }
                });
                dilaog.show();
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
//        requestBalanceData();
        getMyInfo();
    }

    /**
     * 获取数据
     */
    private void getMyInfo() {
        SharedPreferences sp = getSharedPreferences("UID", Context.MODE_PRIVATE);
        uid = sp.getString("uid", "");
        NetWork.getRetrofit()
                .create(ImpService.class)
                .MyMsg(uid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MyMsgBean>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(MyMsgBean response) {
                        if ("0".equals(response.getCode())) {
//                            myMsgBean.getPrices();//可用额度
//                            myMsgBean.getDengjis();//等级名称
//                            myMsgBean.getDengji();//当前用户等级
//                            myMsgBean.getZong();//还款金额
//                            myMsgBean.getZds();//未逾期
//                            myMsgBean.getRole();//2是普通用户 3是代理商
//                            myMsgBean.getMoney();//充值的金额
                            dataBean = response.getData();
                            if (dataBean != null) {
                                tv_balance_money.setText(dataBean.getPrices());//可用额度
                            }
                        }
                    }
                });
    }
}
