package com.jhcms.common.utils;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import com.alipay.sdk.app.PayTask;
import com.jhcms.common.model.Data_WaiMai_PayOrder;
import com.jhcms.waimaiV3.MyApplication;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

/**
 * Created by wangyujie
 * Date 2017/6/14.
 * TODO
 */

public class WaiMaiPay {
    // 支付宝支付
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    PayResult payResult = new PayResult((String) msg.obj);
                    // 支付宝返回此次支付结果及加签，建议对支付宝签名信息拿签约时支付宝提供的公钥做验签
                    String resultInfo = payResult.getResult();
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                    if (TextUtils.equals(resultStatus, "9000")) {
                        if (payListener != null) {
                            ToastUtil.show("支付成功");
                            payListener.onFinish(true);
                        }
                    } else {
                        // 判断resultStatus 为非“9000”则代表可能支付失败
                        // “8000”代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                        if (TextUtils.equals(resultStatus, "8000")) {
                            ToastUtil.show("支付结果确认中");
                        } else {
                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                            ToastUtil.show("支付失败");
                        }
                    }
                    break;
                }
                case SDK_CHECK_FLAG: {
                    ToastUtil.show("检查结果为：" + msg.obj);
                    break;
                }
                default:
                    break;
            }
        }

    };
    public static final int SDK_PAY_FLAG = 1;
    public static final int SDK_CHECK_FLAG = 2;
    private final Context context;
    private final OnPayListener payListener;
    // 微信支付
    private IWXAPI msgApi;

    public WaiMaiPay(Context context, OnPayListener listener) {
        this.context = context;
        this.payListener = listener;

        msgApi = WXAPIFactory.createWXAPI(context, MyApplication.WX_APP_ID);

    }

    public void pay(String code, Data_WaiMai_PayOrder data) {
        if (null == data) {
            //判断是否为空。
            ToastUtil.show("服务器异常");
            return;
        }
        switch (code) {
            case "wxpay":
                wxpay(data);
                break;
            case "alipay":
                alipay(data);
                break;
        }
    }

    private void wxpay(Data_WaiMai_PayOrder data) {
        //data  根据服务器返回的json数据创建的实体类对象
        PayReq req = new PayReq();
        req.appId = data.appid;
        req.partnerId = data.partnerid;
        req.prepayId = data.prepayid;
        req.nonceStr = data.noncestr;
        req.packageValue = data.wxpackage;
        req.timeStamp = data.timestamp.toString();
        req.sign = data.sign;

        msgApi.registerApp(data.appid);
        //发起请求
        msgApi.sendReq(req);

    }


    private void alipay(Data_WaiMai_PayOrder data) {
        // 订单
        String orderInfo = data.signstr;
//        String orderInfo = getOrderInfo(data);
        String sign = data.sign;
        // 完整的符合支付宝参数规范的订单信息
        final String payInfo = orderInfo + "&sign=\"" + sign + "\"&"
                + getSignType();
        Runnable payRunnable = new Runnable() {
            @Override
            public void run() {
                // 构造PayTask 对象
                PayTask alipay = new PayTask((Activity) context);
                // 调用支付接口，获取支付结果
                String result = alipay.pay(payInfo, false);
                System.out.println("获取支付结果==" + result);
                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };
        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    /**
     * get the sign type we use. 获取签名方式
     */
    public String getSignType() {
        return "sign_type=\"RSA\"";
    }

    /**
     * create the order info. 创建订单信息
     */
    public String getOrderInfo(Data_WaiMai_PayOrder alipay) {

        // 签约合作者身份ID
        String orderInfo = "partner=" + "\"" + alipay.partner + "\"";

        // 签约卖家支付宝账号
        orderInfo += "&seller_id=" + "\"" + alipay.seller_id + "\"";

        // 商户网站唯一订单号
        orderInfo += "&out_trade_no=" + "\"" + alipay.out_trade_no + "\"";

        // 商品名称
        orderInfo += "&subject=" + "\"" + alipay.subject + "\"";

        // 商品详情
        orderInfo += "&body=" + "\"" + alipay.body + "\"";

        // 商品金额
        orderInfo += "&total_fee=" + "\"" + alipay.total_fee + "\"";

        // 服务器异步通知页面路径
        orderInfo += "&notify_url=" + "\"" + alipay.notify_url + "\"";

        // 服务接口名称， 固定值
        //orderInfo += "&service=\"mobile.securitypay.pay\"";
        orderInfo += "&service=\"" + alipay.service + "\"";

        // 支付类型， 固定值
        //orderInfo += "&payment_type=\"1\"";
        orderInfo += "&payment_type=\"" + alipay.payment_type + "\"";

        // 参数编码， 固定值
        //orderInfo += "&_input_charset=\"utf-8\"";
        orderInfo += "&_input_charset=\"" + alipay._input_charset + "\"";

        // 设置未付款交易的超时时间
        // 默认30分钟，一旦超时，该笔交易就会自动被关闭。
        // 取值范围：1m～15d。
        // m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
        // 该参数数值不接受小数点，如1.5h，可转换为90m。
        //orderInfo += "&it_b_pay=\"30m\"";

        // extern_token为经过快登授权获取到的alipay_open_id,带上此参数用户将使用授权的账户进行支付
        // orderInfo += "&extern_token=" + "\"" + extern_token + "\"";

        // 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
        //orderInfo += "&return_url=\"m.alipay.com\"";

        // 调用银行卡支付，需配置此参数，参与签名， 固定值 （需要签约《无线银行卡快捷支付》才能使用）
        // orderInfo += "&paymethod=\"expressGateway\"";

        return orderInfo;
    }

    public interface OnPayListener {
        void onFinish(boolean success);
    }
}
