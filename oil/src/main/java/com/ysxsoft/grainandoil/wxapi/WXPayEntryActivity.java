package com.ysxsoft.grainandoil.wxapi;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.ysxsoft.grainandoil.utils.BaseActivity;
import com.ysxsoft.grainandoil.utils.NetWork;
import com.ysxsoft.grainandoil.view.WebViewActivity;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {
    private IWXAPI api;
    private String uid;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        api = WXAPIFactory.createWXAPI(this, "wx4c6287736c2fc760");
        api.handleIntent(getIntent(), this);
        SharedPreferences sp = getSharedPreferences("UID", Context.MODE_PRIVATE);
        uid = sp.getString("uid", "");
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(BaseResp resp) {
        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            Intent intent=new Intent("WXPAY");
            if (resp.errCode == 0) {
//                Toast.makeText(this,"支付成功",Toast.LENGTH_SHORT).show();
                intent.putExtra("pay","ok");
            } else {
//                Toast.makeText(this,resp.errStr,Toast.LENGTH_SHORT).show();
                intent.putExtra("pay","no");
            }
            sendBroadcast(intent);
            finish();
        }
    }
}
