package com.jhcms.mall.utils;

import com.jhcms.mall.model.MallOrderItemMode;

/**
 * Created by wangyujie
 * on 2017/9/15.9:51
 * TODO
 */

public class MallOrderStatus {
    public final static int STATUS_WULIU_CONFIRM = 101;//查看物流&确认订单
    public final static int STATUS_COMMENT_WULIU = 102;//去评价&查看物流
    public final static int STATUS_PAY_CANCEL = 105;//去支付&取消订单
    public final static int STATUS_WULIU = 103;//查看物流
    public final static int STATUS_NULL = 104;//查看物流

    public static int dealWith(MallOrderItemMode.ItemsBean.MsgBean msg) {
        if ("1".equals(msg.show_btn.wuliu) && "1".equals(msg.show_btn.confirm)) {
            return STATUS_WULIU_CONFIRM;
        } else if ("1".equals(msg.show_btn.wuliu) && "1".equals(msg.show_btn.comment)) {
            return STATUS_COMMENT_WULIU;
        } else if ("1".equals(msg.show_btn.cancel) && "1".equals(msg.show_btn.pay)) {
            return STATUS_PAY_CANCEL;
        } else if ("1".equals(msg.show_btn.wuliu)) {
            return STATUS_WULIU;
        }
        return STATUS_NULL;
    }

}
