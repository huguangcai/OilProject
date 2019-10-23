package com.ysxsoft.grainandoil.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ysxsoft.grainandoil.R;
import com.ysxsoft.grainandoil.impservice.ImpService;
import com.ysxsoft.grainandoil.modle.BalanceMoneyBean;
import com.ysxsoft.grainandoil.modle.MyBillNumBean;
import com.ysxsoft.grainandoil.modle.MyMsgBean;
import com.ysxsoft.grainandoil.modle.QQNumberBean;
import com.ysxsoft.grainandoil.utils.BaseFragment;
import com.ysxsoft.grainandoil.utils.ImageLoadUtil;
import com.ysxsoft.grainandoil.utils.NetWork;
import com.ysxsoft.grainandoil.view.CollectActivity;
import com.ysxsoft.grainandoil.view.InComeActivity;
import com.ysxsoft.grainandoil.view.GetGoodsAddressActivity;
import com.ysxsoft.grainandoil.view.MyCardBagActivity;
import com.ysxsoft.grainandoil.view.MyInfoActivity;
import com.ysxsoft.grainandoil.view.MyWalletActivity;
import com.ysxsoft.grainandoil.view.SettingActivity;
import com.ysxsoft.grainandoil.view.SettingEditorActivity;
import com.ysxsoft.grainandoil.view.UserPersonActivity;
import com.ysxsoft.grainandoil.view.WebViewActivity;
import com.ysxsoft.grainandoil.widget.CircleImageView;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 我的的Fragment
 */
public class MyFragment extends BaseFragment implements View.OnClickListener {

    private ImageView img_info, img_setting, img_dls;
    private CircleImageView img_head;
    private TextView tv_nikeName, tv_editor, tv_collect, tv_wait_pay, tv_wait_share,tv_complete_number,
            tv_wait_fahuo, tv_return_goods, tv_wait_get_goods, tv_wait_evaluate, tv_tip, tv_is_realName,tv_pay_message_number,tv_fahuo_message_number,tv_return_message_number,tv_gethou_message_number;
    private FrameLayout fl_img_arrow;
    private LinearLayout ll_my_wallet, ll_my_team, ll_get_goods_address, ll_my_card_bag, ll_kefu, ll_dailikefu, ll_dailishouyi,ll_person;
    private int stateBar;
    private String uid,headPath,sex,dengji,dengjis,zds,zong;
    private View view_line,view_line1;
    private String type;

    @Override
    public void onStart() {
        super.onStart();
        SharedPreferences sp = getActivity().getSharedPreferences("UID", Context.MODE_PRIVATE);
        uid = sp.getString("uid", "");
        stateBar = getStateBar();
        initView();
        initLisetent();
        requesrInfoData();
    }

    /**
     * 已读和未读消息获取
     */
    private void requesrInfoData() {
        NetWork.getService(ImpService.class)
                .BalanceMoneyData(uid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BalanceMoneyBean>() {
                    private BalanceMoneyBean balanceMoneyBean;

                    @Override
                    public void onCompleted() {
                        if ("0".equals(balanceMoneyBean.getCode())) {
                            if (balanceMoneyBean.getData().getNews() == 0) {//0是未读1是已读
                                img_info.setBackgroundResource(R.mipmap.img_have_info);
                            } else {
                                img_info.setBackgroundResource(R.mipmap.img_no_info);
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(BalanceMoneyBean balanceMoneyBean) {
                        this.balanceMoneyBean = balanceMoneyBean;
                    }
                });

    }

    private void initLisetent() {
        img_info.setOnClickListener(this);
        img_setting.setOnClickListener(this);
        tv_editor.setOnClickListener(this);
        tv_collect.setOnClickListener(this);
        tv_wait_pay.setOnClickListener(this);
        tv_wait_share.setOnClickListener(this);
        tv_wait_fahuo.setOnClickListener(this);
        tv_return_goods.setOnClickListener(this);
        tv_wait_get_goods.setOnClickListener(this);
        tv_wait_evaluate.setOnClickListener(this);
        fl_img_arrow.setOnClickListener(this);
        ll_my_wallet.setOnClickListener(this);
        ll_my_team.setOnClickListener(this);
        ll_get_goods_address.setOnClickListener(this);
        ll_my_card_bag.setOnClickListener(this);
        img_info.setOnClickListener(this);
        ll_kefu.setOnClickListener(this);
        ll_dailikefu.setOnClickListener(this);
        ll_dailishouyi.setOnClickListener(this);
        tv_is_realName.setOnClickListener(this);
        ll_person.setOnClickListener(this);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.my_layout;
    }

    private void initView() {
        LinearLayout ll_title = getViewById(R.id.ll_title);
        ll_title.setPadding(0, stateBar, 0, 0);
        TextView tv_title = getViewById(R.id.tv_title);
        tv_title.setText("我的");
        img_head = getViewById(R.id.img_head);
        view_line = getViewById(R.id.view_line);
        view_line1 = getViewById(R.id.view_line1);
        img_info = getViewById(R.id.img_info);
        img_setting = getViewById(R.id.img_setting);
        tv_nikeName = getViewById(R.id.tv_nikeName);
        fl_img_arrow = getViewById(R.id.fl_img_arrow);
        tv_editor = getViewById(R.id.tv_editor);
        tv_collect = getViewById(R.id.tv_collect);
        tv_wait_pay = getViewById(R.id.tv_wait_pay);
        tv_wait_share = getViewById(R.id.tv_wait_share);
        tv_wait_fahuo = getViewById(R.id.tv_wait_fahuo);
        tv_return_goods = getViewById(R.id.tv_return_goods);
        tv_wait_get_goods = getViewById(R.id.tv_wait_get_goods);
        tv_wait_evaluate = getViewById(R.id.tv_wait_evaluate);
        ll_my_wallet = getViewById(R.id.ll_my_wallet);
        ll_my_team = getViewById(R.id.ll_my_team);
        ll_get_goods_address = getViewById(R.id.ll_get_goods_address);
        ll_my_card_bag = getViewById(R.id.ll_my_card_bag);
        ll_dailishouyi = getViewById(R.id.ll_dailishouyi);
        ll_kefu = getViewById(R.id.ll_kefu);
        ll_dailikefu = getViewById(R.id.ll_dailikefu);
        ll_person = getViewById(R.id.ll_person);
        tv_tip = getViewById(R.id.tv_tip);
        img_dls = getViewById(R.id.img_dls);
        tv_is_realName = getViewById(R.id.tv_is_realName);
        tv_pay_message_number = getViewById(R.id.tv_pay_message_number);
        tv_fahuo_message_number = getViewById(R.id.tv_fahuo_message_number);
        tv_return_message_number = getViewById(R.id.tv_return_message_number);
        tv_gethou_message_number = getViewById(R.id.tv_gethou_message_number);
        tv_complete_number = getViewById(R.id.tv_complete_number);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_info://消息
                Intent infointent = new Intent(getActivity(), MyInfoActivity.class);
                infointent.putExtra("uid", uid);
                startActivity(infointent);
                break;
            case R.id.img_setting://设置
                Intent intent0 = new Intent(getActivity(), SettingActivity.class);
                intent0.putExtra("uid", uid);
                startActivity(intent0);
                break;
            case R.id.tv_editor://编辑
                Intent intent = new Intent(getActivity(), SettingEditorActivity.class);
                intent.putExtra("uid", uid);
                intent.putExtra("nikeName", tv_nikeName.getText().toString().trim());
                intent.putExtra("headPath", headPath);
                intent.putExtra("sex", sex);
                startActivity(intent);
                break;
            case R.id.tv_collect://收藏
                Intent collectintent = new Intent(getActivity(), CollectActivity.class);
                collectintent.putExtra("uid", uid);
                startActivity(collectintent);
                break;
            case R.id.tv_wait_pay://待支付
                Intent payintent = new Intent(getActivity(), WebViewActivity.class);
                String payurl = NetWork.H5BaseUrl + "order?sc=2&status=" + "1" + "&uid=" + uid;
                payintent.putExtra("uid", uid);
                payintent.putExtra("url", payurl);
                payintent.putExtra("flag", "myfragment");
                startActivity(payintent);
                break;
            case R.id.tv_wait_fahuo://待发货
                Intent fahuointent = new Intent(getActivity(), WebViewActivity.class);
                String fahuourl = NetWork.H5BaseUrl + "order?sc=2&status=" + "2" + "&uid=" + uid;
                fahuointent.putExtra("uid", uid);
                fahuointent.putExtra("url", fahuourl);
                fahuointent.putExtra("flag", "myfragment");
                startActivity(fahuointent);
                break;
            case R.id.tv_wait_get_goods://待收货
                Intent goodsintent = new Intent(getActivity(), WebViewActivity.class);
                String goodsurl = NetWork.H5BaseUrl + "order?sc=2&status=" + "3" + "&uid=" + uid;
                goodsintent.putExtra("uid", uid);
                goodsintent.putExtra("url", goodsurl);
                goodsintent.putExtra("flag", "myfragment");
                startActivity(goodsintent);
                break;
            case R.id.tv_wait_evaluate://已完成
                Intent evaluateintent = new Intent(getActivity(), WebViewActivity.class);
                String evaluateurl = NetWork.H5BaseUrl + "order?sc=2&status=" + "5" + "&uid=" + uid;
                evaluateintent.putExtra("uid", uid);
                evaluateintent.putExtra("url", evaluateurl);
                evaluateintent.putExtra("flag", "myfragment");
                startActivity(evaluateintent);

                break;
            case R.id.tv_return_goods://退货/售后
                Intent returnintent = new Intent(getActivity(), WebViewActivity.class);
                String returnurl = NetWork.H5BaseUrl + "order?sc=2&status=" + "4" + "&uid=" + uid;
                returnintent.putExtra("uid", uid);
                returnintent.putExtra("url", returnurl);
                returnintent.putExtra("flag", "myfragment");
                startActivity(returnintent);

                break;
            case R.id.ll_my_wallet://我的钱包
                Intent intentwallet = new Intent(getActivity(), MyWalletActivity.class);
                intentwallet.putExtra("uid", uid);
                intentwallet.putExtra("dengji", dengji);
                intentwallet.putExtra("dengjis", dengjis);
                intentwallet.putExtra("zds", zds);
                intentwallet.putExtra("zong", zong);
                intentwallet.putExtra("type", type);
                startActivity(intentwallet);
                break;
            case R.id.ll_dailishouyi://代理收益
                Intent intentincome = new Intent(getActivity(), InComeActivity.class);
                intentincome.putExtra("uid", uid);
                startActivity(intentincome);
                break;
            case R.id.ll_person://代理商所属用户
                Intent intentperson = new Intent(getActivity(), UserPersonActivity.class);
                intentperson.putExtra("uid", uid);
                startActivity(intentperson);
                break;
            case R.id.ll_get_goods_address://收货地址
                Intent intentaddress = new Intent(getActivity(), GetGoodsAddressActivity.class);
                intentaddress.putExtra("uid", uid);
                startActivity(intentaddress);
                break;
            case R.id.ll_my_card_bag://我的卡包
                Intent intentMyCard = new Intent(getActivity(), MyCardBagActivity.class);
                intentMyCard.putExtra("uid", uid);
                startActivity(intentMyCard);
                break;
            case R.id.fl_img_arrow://全部订单
                Intent allintent = new Intent(getActivity(), WebViewActivity.class);
                String allurl = NetWork.H5BaseUrl + "order?sc=2&status=" + "0" + "&uid=" + uid;
//                String allurl = "http://192.168.1.173:8081/#/order";
                allintent.putExtra("uid", uid);
                allintent.putExtra("url", allurl);
                allintent.putExtra("flag", "myfragment");
                startActivity(allintent);
                break;
            case R.id.ll_kefu:
                Intent intentkefu = new Intent(getActivity(), WebViewActivity.class);
                String s = NetWork.H5BaseUrl + "companyservice?sc=2";
                intentkefu.putExtra("url", s);
                intentkefu.putExtra("uid", uid);
                startActivity(intentkefu);
                break;
            case R.id.ll_dailikefu:
                Intent intentdailikefu = new Intent(getActivity(), WebViewActivity.class);
                String s1 = NetWork.H5BaseUrl + "viceservice?sc=2";
                intentdailikefu.putExtra("url", s1);
                intentdailikefu.putExtra("uid", uid);
                startActivity(intentdailikefu);
                break;
            case R.id.tv_is_realName:
                Intent intentrealName = new Intent(getActivity(), WebViewActivity.class);
                String sUrl = NetWork.H5BaseUrl + "realname?sc=2";
                intentrealName.putExtra("url", sUrl);
                intentrealName.putExtra("uid", uid);
                startActivity(intentrealName);
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        requestData();
        requestQQ();
        requestMsgData();
    }

    private void requestMsgData() {
        NetWork.getService(ImpService.class)
                .MyBillNum(uid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MyBillNumBean>() {
                    private MyBillNumBean myBillNumBean;

                    @Override
                    public void onCompleted() {
                        if ("0".equals(myBillNumBean.getCode())){
                            if (!"0".equals(myBillNumBean.getData().getOr())&&!TextUtils.isEmpty(myBillNumBean.getData().getOr())) {
                                tv_pay_message_number.setVisibility(View.VISIBLE);
                                tv_pay_message_number.setText(myBillNumBean.getData().getOr());
                            }else {
                                tv_pay_message_number.setVisibility(View.GONE);
                            }

                            if (!"0".equals(myBillNumBean.getData().getOs())&&!TextUtils.isEmpty(myBillNumBean.getData().getOs())) {
                                tv_fahuo_message_number.setVisibility(View.VISIBLE);
                                tv_fahuo_message_number.setText(myBillNumBean.getData().getOs());
                            }else {
                                tv_fahuo_message_number.setVisibility(View.GONE);
                            }
                            if (!"0".equals(myBillNumBean.getData().getOd())&&!TextUtils.isEmpty(myBillNumBean.getData().getOd())) {
                                tv_gethou_message_number.setVisibility(View.VISIBLE);
                                tv_gethou_message_number.setText(myBillNumBean.getData().getOd());
                            }else {
                                tv_gethou_message_number.setVisibility(View.GONE);
                            }
                            if (!"0".equals(myBillNumBean.getData().getOf())&&!TextUtils.isEmpty(myBillNumBean.getData().getOf())) {
                                tv_return_message_number.setVisibility(View.VISIBLE);
                                tv_return_message_number.setText(myBillNumBean.getData().getOf());
                            }else {
                                tv_return_message_number.setVisibility(View.GONE);
                            }

                             if (!"0".equals(myBillNumBean.getData().getOe())&&!TextUtils.isEmpty(myBillNumBean.getData().getOe())) {
                                 tv_complete_number.setVisibility(View.VISIBLE);
                                 tv_complete_number.setText(myBillNumBean.getData().getOe());
                            }else {
                                 tv_complete_number.setVisibility(View.GONE);
                            }

                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(MyBillNumBean myBillNumBean) {

                        this.myBillNumBean = myBillNumBean;
                    }
                });
    }

    private void requestQQ() {
        NetWork.getService(ImpService.class)
                .QQNumberData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<QQNumberBean>() {
                    private QQNumberBean qqNumberBean;

                    @Override
                    public void onCompleted() {
                        if ("0".equals(qqNumberBean.getCode())) {
//                            tv_qq.setText("官方qq："+qqNumberBean.getData().getQq());
//                            tv_wx.setText("官方微信："+qqNumberBean.getData().getWx());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(QQNumberBean qqNumberBean) {

                        this.qqNumberBean = qqNumberBean;
                    }
                });
    }

    /**
     * 获取数据
     */
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
//                        showToastMessage(e.getMessage());
                    }

                    @Override
                    public void onNext(MyMsgBean response) {
                        if ("0".equals(response.getCode())) {
                            headPath = response.getData().getImgurl();
                            sex = String.valueOf(response.getData().getSex());
                            tv_nikeName.setText(response.getData().getNickname());
                            dengji = response.getData().getDengji();
                            dengjis = response.getData().getDengjis();
                            zds = response.getData().getZds();
                            zong = response.getData().getZong();
                            type = response.getData().getType();
                            ImageLoadUtil.GlideHeadImageLoad(getActivity(), response.getData().getImgurl(), img_head);
                            switch (response.getData().getRole()) {
                                case "2":
                                    tv_tip.setVisibility(View.GONE);
                                    img_dls.setVisibility(View.GONE);
                                    ll_person.setVisibility(View.GONE);
                                    view_line1.setVisibility(View.GONE);
                                    view_line.setVisibility(View.GONE);
                                    ll_dailishouyi.setVisibility(View.GONE);
//                                    tv_is_realName.setVisibility(View.GONE);
                                    break;
                                case "3":
                                    tv_tip.setVisibility(View.VISIBLE);
                                    img_dls.setVisibility(View.VISIBLE);
                                    ll_person.setVisibility(View.VISIBLE);
                                    view_line1.setVisibility(View.VISIBLE);
                                    view_line.setVisibility(View.VISIBLE);
                                    ll_dailishouyi.setVisibility(View.VISIBLE);
//                                    tv_is_realName.setVisibility(View.VISIBLE);
                                    break;
                            }
                            switch (response.getData().getZds()) {
                                case "1":
                                    tv_tip.setVisibility(View.GONE);
                                    break;
                                case "2":
                                    tv_tip.setVisibility(View.VISIBLE);
                                    break;
                            }
                            switch (response.getData().getIdcare()) {
                                case "0"://未实名
                                    tv_is_realName.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(getContext(),R.drawable.img_no_real_name), null, null);
                                    tv_is_realName.setText("未实名");
                                    break;
                                case "1"://审核中
                                    tv_is_realName.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(getContext(),R.drawable.img_checking), null, null);
                                    tv_is_realName.setText("审核中");
                                    break;
                                case "2"://已认证
                                    tv_is_realName.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(getContext(),R.drawable.img_real_name), null, null);
                                    tv_is_realName.setText("已认证");
                                    break;
                                case "3"://审核失败
                                    tv_is_realName.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(getContext(),R.drawable.img_no_real_name), null, null);
                                    tv_is_realName.setText("审核失败");
                                    break;
                            }

                        }
                    }
                });
    }
}
