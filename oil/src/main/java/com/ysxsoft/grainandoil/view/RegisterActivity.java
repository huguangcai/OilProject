package com.ysxsoft.grainandoil.view;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ysxsoft.grainandoil.R;
import com.ysxsoft.grainandoil.impservice.ImpService;
import com.ysxsoft.grainandoil.modle.RegisterBean;
import com.ysxsoft.grainandoil.utils.AppUtil;
import com.ysxsoft.grainandoil.utils.BaseActivity;
import com.ysxsoft.grainandoil.utils.CountDownTimeHelper;
import com.ysxsoft.grainandoil.utils.NetWork;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class RegisterActivity extends BaseActivity implements View.OnClickListener {

    private EditText ed_phone, ed_pwd, ed_idenfy_code, ed_invatation_code,ed_second_pwd;
    private TextView tv_get_idenfy_code, tv_register_agreement, tv_login;
    private CheckBox cb_box;
    private Button btn_login;
    private LinearLayout ll_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_layout);
        setHalfTransparent();
        setFitSystemWindow(false);
        initView();
        initListener();
    }

    private void initView() {
        ed_phone = getViewById(R.id.ed_phone);
        ed_idenfy_code = getViewById(R.id.ed_idenfy_code);
        tv_get_idenfy_code = getViewById(R.id.tv_get_idenfy_code);
        ed_invatation_code = getViewById(R.id.ed_invatation_code);
        ed_pwd = getViewById(R.id.ed_pwd);
        cb_box = getViewById(R.id.cb_box);
        tv_register_agreement = getViewById(R.id.tv_register_agreement);
        tv_login = getViewById(R.id.tv_login);
        btn_login = getViewById(R.id.btn_login);
        ed_second_pwd = getViewById(R.id.ed_second_pwd);
        ll_login = getViewById(R.id.ll_login);
    }

    private void initListener() {
        tv_get_idenfy_code.setOnClickListener(this);
        cb_box.setOnClickListener(this);
        tv_register_agreement.setOnClickListener(this);
        tv_login.setOnClickListener(this);
        btn_login.setOnClickListener(this);
        ll_login.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_get_idenfy_code:
                if (checkPhoneNum()) return;
                CountDownTimeHelper timeHelper = new CountDownTimeHelper(60, tv_get_idenfy_code);
                //TODO 发送短信
                String code = sendMessage(ed_phone.getText().toString().trim());
                break;
            case R.id.cb_box:

                break;
            case R.id.tv_register_agreement:
                if (!cb_box.isChecked()){
                    showToastMessage("请同意注册协议");
                    return;
                }
                startActivity(RegisterAgreementActivity.class);
                break;
            case R.id.ll_login:
                finish();
                break;
            case R.id.btn_login:
                if (checkPhoneNum()) return;
                if (TextUtils.isEmpty(ed_idenfy_code.getText().toString().trim())) {
                    showToastMessage("验证码不能为空");
                    return;
                }
                if (TextUtils.isEmpty(ed_pwd.getText().toString().trim())) {
                    showToastMessage("密码不能为空");
                    return;
                }
                if (ed_pwd.getText().toString().trim().length()<6) {
                    showToastMessage("密码不能少于六位");
                    return;
                }
                if (TextUtils.isEmpty(ed_second_pwd.getText().toString().trim())) {
                    showToastMessage("确认密码不能为空");
                    return;
                }
                if (!TextUtils.equals(ed_pwd.getText().toString().trim(),ed_second_pwd.getText().toString().trim())){
                    showToastMessage("两次输入密码不一致");
                    return;
                }
//                if (!cb_box.isChecked()) {
//                    showToastMessage("请同意注册协议");
//                    return;
//                }
                submitData();
                break;

        }
    }

    private void submitData() {
        NetWork.getRetrofit()
                .create(ImpService.class)
                .Register(ed_phone.getText().toString().trim(),
                        ed_pwd.getText().toString().trim(),
//                        ed_invatation_code.getText().toString().trim(),
                        ed_idenfy_code.getText().toString().trim())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<RegisterBean>() {
                    private RegisterBean registerBean;

                    @Override
                    public void onCompleted() {
                        showToastMessage(registerBean.getMsg());
                        if ("0".equals(registerBean.getCode())) {
                            finish();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        showToastMessage(e.getMessage());
                    }

                    @Override
                    public void onNext(RegisterBean registerBean) {
                        this.registerBean = registerBean;
                    }
                });

    }

    /**
     * 核对手机号
     *
     * @return
     */
    private boolean checkPhoneNum() {
        if (TextUtils.isEmpty(ed_phone.getText().toString().trim())) {
            showToastMessage("手机号不能为空");
            return true;
        }
        if (!AppUtil.checkPhoneNum(ed_phone.getText().toString().trim())) {
            showToastMessage("手机号输入不正确");
            return true;
        }
        return false;
    }


}
