package com.jhcms.waimaiV3.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.jhcms.common.model.RefreshEvent;
import com.jhcms.common.utils.LogUtil;
import com.jhcms.common.utils.ToastUtil;
import com.jhcms.waimaiV3.MyApplication;
import com.jhcms.waimaiV3.R;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;

import java.util.Timer;
import java.util.TimerTask;


/**
 * Created by Administrator on 2015/12/15.
 */
public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {
    private static final String TAG = "WXPayEntryActivity";
    private IWXAPI api;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay_result);
        api = WXAPIFactory.createWXAPI(this, MyApplication.WX_APP_ID);
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {
    }
    @Override
    public void onResp(BaseResp resp) {
        LogUtil.d("Luke = " + resp.toString());

        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            int code = resp.errCode;
            switch (code) {
                case 0:
                    new Timer().schedule(new TimerTask() {
                        @Override
                        public void run() {
                            EventBus.getDefault().post(new RefreshEvent("weixin_pay_success"));
                        }
                    }, 2000);
                    ToastUtil.show("支付成功");
                    finish();
                    break;
                case -1:
                    ToastUtil.show("支付失败");
                    finish();
                    break;
                case -2:
                    ToastUtil.show("支付取消");
                    finish();
                    break;
                default:
                    ToastUtil.show("支付失败");
                    setResult(RESULT_OK);
                    finish();
                    break;
            }
        }
    }
}
