package com.ysxsoft.grainandoil.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ysxsoft.grainandoil.R;
import com.ysxsoft.grainandoil.impservice.ImpService;
import com.ysxsoft.grainandoil.modle.BalanceMoneyBean;
import com.ysxsoft.grainandoil.modle.MyMsgBean;
import com.ysxsoft.grainandoil.utils.BaseActivity;
import com.ysxsoft.grainandoil.utils.NetWork;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * create by Sincerly on 2019/4/28 0028
 **/
public class LevelsActivity extends BaseActivity implements View.OnClickListener {
    private ImageView img_vip;
    private TextView money;
    private String uid;
    private MyMsgBean.DataBean dataBean;
    private String dengji;
    private BalanceMoneyBean.DataBean data;

    public static void start(Context context) {
        Intent intent = new Intent(context, LevelsActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_levels);
        Intent intent = getIntent();
        dengji = intent.getStringExtra("dengji");
        requestBalanceData();
        initView();
    }

    private void initView() {
        TextView title = getViewById(R.id.tv_title);
        RelativeLayout back = getViewById(R.id.img_back);
        TextView recharge = getViewById(R.id.recharge);
        TextView withdraw = getViewById(R.id.withdraw);
        img_vip = getViewById(R.id.img_vip);
        money = getViewById(R.id.money);
        title.setText("我的等级");
        back.setOnClickListener(this);
        recharge.setOnClickListener(this);
        withdraw.setOnClickListener(this);
    }
    private void requestBalanceData() {
        NetWork.getService(ImpService.class)
                .BalanceMoneyData(getSharedPreferences("UID", Context.MODE_PRIVATE).getString("uid", ""))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BalanceMoneyBean>() {
                    private BalanceMoneyBean balanceMoneyBean;

                    @Override
                    public void onCompleted() {
                        if ("0".equals(balanceMoneyBean.getCode())) {
                            data = balanceMoneyBean.getData();
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
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.recharge:
                //充值
                if(uid!=null){
//                    Intent recharge = new Intent(LevelsActivity.this, RechargeBalanceActivity.class);
                    Intent recharge = new Intent(LevelsActivity.this, RechargeActivity.class);
                    recharge.putExtra("uid", uid);
                    recharge.putExtra("grade", dengji);
                    recharge.putExtra("flag", "1");
                    startActivity(recharge);
                }
                break;
            case R.id.withdraw:
                //提现
//                WithDrawActivity.start(this);
                Intent intent=new Intent(mContext,WithdrawCashActivity.class);
                intent.putExtra("money",dataBean.getMoney());
                intent.putExtra("uid", uid);
                intent.putExtra("type", "1");
                intent.putExtra("mobile", data.getMobile());
                startActivity(intent);
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getUserInfo();
    }

    private void getUserInfo() {
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
                                String level = dataBean.getDengji();
                                //设置图标
                                setLevelImage(level);
                                money.setText(dataBean.getMoney()+"元");
                            }
                        }
                    }
                });
    }

    private void setLevelImage(String grade) {
        switch (grade) {
            case "1":
                //白金级
                img_vip.setBackgroundResource(R.mipmap.img_vip_bj);
                break;
            case "2":
                //钻石级
                img_vip.setBackgroundResource(R.mipmap.img_vip_zs);
                break;
            case "3":
                //星耀级
                img_vip.setBackgroundResource(R.mipmap.img_vip_xy);
                break;
            case "4":
                //荣耀级
                img_vip.setBackgroundResource(R.mipmap.img_vip_ry);
                break;
            case "5":
                //至尊级
                img_vip.setBackgroundResource(R.mipmap.img_vip_zz);
                break;
        }
    }
}
