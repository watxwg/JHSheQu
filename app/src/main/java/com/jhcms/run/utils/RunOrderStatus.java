package com.jhcms.run.utils;

import com.jhcms.run.mode.Data_Run_Order_UnDone;

/**
 * Created by wangyujie
 * on 2017/9/15.9:51
 * TODO
 */

public class RunOrderStatus {
    public final static int RUN_STATUS_CUI = 103;//催单
    public final static int RUN_STATUS_PAY = 106;//去支付
    public final static int RUN_STATUS_CANCEL = 107;//取消订单
    public final static int RUN_STATUS_PAY_CANCEL = 105;//去支付&取消订单
    public final static int RUN_STATUS_AGAIN_COMMENT = 108;//再来一单&评价
    public final static int RUN_STATUS_AGAIN = 109;//再来一单
    public final static int RUN_STATUS_CONFIRM = 110;//确认订单
    public final static int STATUS_NULL = 104;

    public static int dealWith(Data_Run_Order_UnDone.ItemsBean.BtnBean msg) {
        if ("1".equals(msg.getPay()) && "1".equals(msg.getCancel())) {
            return RUN_STATUS_PAY_CANCEL;
        } else if ("1".equals(msg.getPay()) && "0".equals(msg.getCancel())) {
            return RUN_STATUS_PAY;
        } else if ("0".equals(msg.getPay()) && "1".equals(msg.getCancel())) {
            return RUN_STATUS_CANCEL;
        } else if ("0".equals(msg.getAgain()) && "1".equals(msg.getCui())) {
            return RUN_STATUS_CUI;
        } else if ("1".equals(msg.getAgain()) && "1".equals(msg.getComment())) {
            return RUN_STATUS_AGAIN_COMMENT;
        } else if ("1".equals(msg.getAgain()) && "0".equals(msg.getComment())) {
            return RUN_STATUS_AGAIN;
        } else if ("1".equals(msg.getConfirm())) {
            return RUN_STATUS_CONFIRM;
        }
        return STATUS_NULL;
    }

}
