package com.ysxsoft.grainandoil.view;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.ysxsoft.grainandoil.R;
import com.ysxsoft.grainandoil.impservice.ImpService;
import com.ysxsoft.grainandoil.modle.AliPayRechargeBean;
import com.ysxsoft.grainandoil.modle.MyMsgBean;
import com.ysxsoft.grainandoil.modle.WxPayBean;
import com.ysxsoft.grainandoil.utils.BaseActivity;
import com.ysxsoft.grainandoil.utils.CustomDialog;
import com.ysxsoft.grainandoil.utils.ImageLoadUtil;
import com.ysxsoft.grainandoil.utils.MoneyTextWatcher;
import com.ysxsoft.grainandoil.utils.NetWork;
import com.ysxsoft.grainandoil.utils.alipay.AlipayUtils;

import java.math.BigDecimal;
import java.util.Map;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class BillRepaymentActivity extends BaseActivity implements View.OnClickListener {

    private View img_back;
    private TextView tv_title, tv_balance_money,  tv_current_bill, tv_btn;
    private LinearLayout ll_alipay, ll_wechatpay;
    private ImageView img_alipay, img_wechatpay;
    private EditText ed_bill_repayment;
    private int type = 2;
    private String zong = "";
    private int stateBar;
    private String istype,uid;
    private IWXAPI api;
    private String zong1;

    public static void start(Context context, String zong) {
        Intent intent = new Intent(context, BillRepaymentActivity.class);
        intent.putExtra("zong", zong);//账单金额
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bill_repayment_layout);
        api = WXAPIFactory.createWXAPI(this, "wx4c6287736c2fc760");
        zong = getIntent().getStringExtra("zong");//账单金额
        istype = getIntent().getStringExtra("type");//是否逾期
        uid = getIntent().getStringExtra("uid");//是否逾期
        setHalfTransparent();
        setFitSystemWindow(false);
        stateBar = getStateBar();
        initView();
        initListener();

        IntentFilter filter = new IntentFilter("WXPAY");
        registerReceiver(receiver, filter);
    }

    private void requestData() {
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
                            zong1 = response.getData().getZong();
//                            if ("0".equals(response.getData().getZong())||"1".equals(response.getData().getType())){
//                                tv_btn.setBackgroundResource(R.color.hint_text_color);
//                                tv_btn.setEnabled(false);
//                            }else {
//                                tv_btn.setBackgroundResource(R.color.btn_color);
//                                tv_btn.setEnabled(true);
//                            }
                            tv_balance_money.setText(response.getData().getZong() == null ? "¥0" : "-¥" + response.getData().getZong());
                            ed_bill_repayment.setText("¥" + (response.getData().getZong() == null ? "0" : response.getData().getZong()));
                            ed_bill_repayment.setSelection(ed_bill_repayment.getText().toString().trim().length());
                            tv_current_bill.setText("当前账单共" + (response.getData().getZong() == null ? "0" : response.getData().getZong()) + "元");
//                            switch (response.getData().getType()) {
//                                case "1":
//                                    tv_btn.setBackgroundResource(R.color.hint_text_color);
//                                    tv_btn.setEnabled(false);
//                                    break;
//                                case "2":
//                                    tv_btn.setBackgroundResource(R.color.btn_color);
//                                    tv_btn.setEnabled(true);
//                                    break;
//                            }
                        }
                    }
                });

    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if ("WXPAY".equals(intent.getAction())) {
                String pay = intent.getStringExtra("pay");
                switch (pay) {
                    case "ok":
                        finish();
                        break;
                    case "no":
                        showToastMessage("支付失败");
                        break;

                }
            }
        }
    };
    private void initView() {
        RelativeLayout ll_title = getViewById(R.id.ll_title);
        ll_title.setPadding(0, stateBar, 0, 0);
        img_back = getViewById(R.id.img_back);
        tv_title = getViewById(R.id.tv_title);
        tv_title.setText("账单还款");
        tv_balance_money = getViewById(R.id.tv_balance_money);
        ed_bill_repayment = getViewById(R.id.ed_bill_repayment);
        ed_bill_repayment.addTextChangedListener(new MoneyTextWatcher(ed_bill_repayment));

        tv_current_bill = getViewById(R.id.tv_current_bill);
        ll_alipay = getViewById(R.id.ll_alipay);
        ll_wechatpay = getViewById(R.id.ll_wechatpay);
        img_alipay = getViewById(R.id.img_alipay);
        img_wechatpay = getViewById(R.id.img_wechatpay);
        tv_btn = getViewById(R.id.tv_btn);
//        tv_bill_repayment.setText("¥2593.2");
//        tv_current_bill.setText("当前账单共2593.2元");
    }

    private void initListener() {
        img_back.setOnClickListener(this);
        tv_btn.setOnClickListener(this);
        ll_alipay.setOnClickListener(this);
        ll_wechatpay.setOnClickListener(this);
        img_alipay.setOnClickListener(this);
        img_wechatpay.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.ll_alipay:
                type = 1;
                img_alipay.setVisibility(View.VISIBLE);
                img_wechatpay.setVisibility(View.GONE);
                break;
            case R.id.ll_wechatpay:
                type = 2;
                img_alipay.setVisibility(View.GONE);
                img_wechatpay.setVisibility(View.VISIBLE);
                break;
            case R.id.tv_btn:
                String s = ed_bill_repayment.getText().toString().trim().replaceAll("¥", "");
                if ("0".equals(s)||"0.00".equals(s)){
                    showToastMessage("还款金额不能为零");
                    return;
                }
                if (Double.valueOf(s)>Double.valueOf(zong1)){
                    showToastMessage("输入金额不能大于还款金额");
                    return;
                }
                if (type == 1) {
                    //支付宝支付
                    alipay();
                } else {
                    //微信支付
//                    showToastMessage("微信支付暂未开通！");
                    WeChatRecharge();
                }
                break;
        }
    }

    private void WeChatRecharge() {
        final CustomDialog wxPay = new CustomDialog(mContext, "获取订单中...");
        wxPay.show();
        String str = ed_bill_repayment.getText().toString().trim().replaceAll("¥", "");
        NetWork.getService(ImpService.class)
                .WxPaysData(uid, str,"2")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<WxPayBean>() {
                    private WxPayBean wxPayBean;

                    @Override
                    public void onCompleted() {
                        if ("0".equals(wxPayBean.getCode())) {
                            PayReq req = new PayReq();
                            req.appId = wxPayBean.getData().getAppid();
                            req.partnerId = wxPayBean.getData().getPartnerid();
                            req.prepayId = wxPayBean.getData().getPrepayid();
                            req.nonceStr = wxPayBean.getData().getNoncestr();
                            req.timeStamp = String.valueOf(wxPayBean.getData().getTimestamp());
                            req.packageValue = wxPayBean.getData().getPackageX();
                            req.sign = wxPayBean.getData().getSign();
                            req.extData = "app data"; // optional
//                            showToastMessage("正常调起支付");
                            // 在支付之前，如果应用没有注册到微信，应该先调用IWXMsg.registerApp将应用注册到微信
                            api.sendReq(req);
                            wxPay.dismiss();
                            tv_btn.setEnabled(true);
//                            finish();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(WxPayBean wxPayBean) {
                        this.wxPayBean = wxPayBean;
                    }
                });
    }

    /**
     * 支付宝还款
     */
    private void alipay() {
        SharedPreferences spUid = getSharedPreferences("UID", Context.MODE_PRIVATE);
        String uid = spUid.getString("uid", "");

        String str = ed_bill_repayment.getText().toString().trim().replaceAll("¥", "");
        NetWork.getService(ImpService.class)
                .AliPayRechargeData(uid, str, "2")//1是充值2是还款
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<AliPayRechargeBean>() {
                    private AliPayRechargeBean aliPayRechargeBean;

                    @Override
                    public void onCompleted() {
                        showToastMessage(aliPayRechargeBean.getMsg());
                        if ("0".equals(aliPayRechargeBean.getCode())) {
                            AlipayUtils.startAlipay(BillRepaymentActivity.this, handler, 0x10, aliPayRechargeBean.getData());//支付宝支付
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        showToastMessage(e.getMessage());
                    }

                    @Override
                    public void onNext(AliPayRechargeBean aliPayRechargeBean) {
                        this.aliPayRechargeBean = aliPayRechargeBean;
                    }
                });
    }

    //支付宝支付结果
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0x10:
                    Map<String, String> map = (Map<String, String>) msg.obj;
                    //9000支付成功  8000 正在处理中  4000 订单支付失败  5000重复请求  6001中途取消  6002网络连接出错 6004 支付结果未知  其他其他支付错误
                    if ("9000".equals(map.get("resultStatus"))) {//订单支付成功
                        Toast.makeText(BillRepaymentActivity.this, "支付宝支付成功！", Toast.LENGTH_SHORT).show();
                        finish();
                    } else if ("4000".equals(map.get("resultStatus"))) {//订单支付失败
                        Toast.makeText(BillRepaymentActivity.this, "支付宝支付失败！", Toast.LENGTH_SHORT).show();
                    } else if ("6001".equals(map.get("resultStatus"))) {//订单支付中途取消
                        Toast.makeText(BillRepaymentActivity.this, "支付宝支付取消！", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        requestData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }
}
