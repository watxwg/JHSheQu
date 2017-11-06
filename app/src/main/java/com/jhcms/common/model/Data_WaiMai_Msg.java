package com.jhcms.common.model;

import java.util.List;

/**
 * Created by wangyujie
 * Date 2017/6/30.
 * TODO 用户信息
 */

public class Data_WaiMai_Msg {

    /**
     * items : [{"message_id":"1411","uid":"59","title":"优惠券卡旺卡优惠券已过期","content":"您的外卖优惠券-卡旺卡优惠券(ID:176)已过期","type":"6","order_id":"0","is_read":"0","dateline":"1498617557","clientip":"112.26.23.195","can_id":"176"},{"message_id":"1410","uid":"59","title":"优惠券卡旺卡优惠券已过期","content":"您的外卖优惠券-卡旺卡优惠券(ID:177)已过期","type":"6","order_id":"0","is_read":"0","dateline":"1498617557","clientip":"112.26.23.195","can_id":"177"},{"message_id":"1408","uid":"59","title":"优惠券卡旺卡优惠券已过期","content":"您的外卖优惠券-卡旺卡优惠券(ID:165)已过期","type":"6","order_id":"0","is_read":"0","dateline":"1498541939","clientip":"112.26.23.195","can_id":"165"},{"message_id":"1407","uid":"59","title":"优惠券卡旺卡优惠券已过期","content":"您的外卖优惠券-卡旺卡优惠券(ID:167)已过期","type":"6","order_id":"0","is_read":"0","dateline":"1498541939","clientip":"112.26.23.195","can_id":"167"},{"message_id":"1406","uid":"59","title":"优惠券卡旺卡优惠券已过期","content":"您的外卖优惠券-卡旺卡优惠券(ID:168)已过期","type":"6","order_id":"0","is_read":"0","dateline":"1498541939","clientip":"112.26.23.195","can_id":"168"},{"message_id":"1405","uid":"59","title":"优惠券卡旺卡优惠券已过期","content":"您的外卖优惠券-卡旺卡优惠券(ID:169)已过期","type":"6","order_id":"0","is_read":"0","dateline":"1498541939","clientip":"112.26.23.195","can_id":"169"},{"message_id":"1404","uid":"59","title":"优惠券卡旺卡优惠券已过期","content":"您的外卖优惠券-卡旺卡优惠券(ID:170)已过期","type":"6","order_id":"0","is_read":"0","dateline":"1498541939","clientip":"112.26.23.195","can_id":"170"},{"message_id":"1403","uid":"59","title":"优惠券小辉外卖优惠券已过期","content":"您的外卖优惠券-小辉外卖优惠券(ID:171)已过期","type":"6","order_id":"0","is_read":"0","dateline":"1498541939","clientip":"112.26.23.195","can_id":"171"},{"message_id":"1402","uid":"59","title":"优惠券小辉外卖优惠券已过期","content":"您的外卖优惠券-小辉外卖优惠券(ID:172)已过期","type":"6","order_id":"0","is_read":"0","dateline":"1498541939","clientip":"112.26.23.195","can_id":"172"},{"message_id":"1401","uid":"59","title":"优惠券小辉外卖优惠券已过期","content":"您的外卖优惠券-小辉外卖优惠券(ID:173)已过期","type":"6","order_id":"0","is_read":"0","dateline":"1498541939","clientip":"112.26.23.195","can_id":"173"}]
     * total_count : 99
     */

    public String total_count;
    public List<ItemsEntity> items;

    public static class ItemsEntity {
        /**
         * message_id : 1411
         * uid : 59
         * title : 优惠券卡旺卡优惠券已过期
         * content : 您的外卖优惠券-卡旺卡优惠券(ID:176)已过期
         * type : 6
         * order_id : 0
         * is_read : 0
         * dateline : 1498617557
         * clientip : 112.26.23.195
         * can_id : 176
         */

        public String message_id;
        public String uid;
        public String title;
        public String content;
        public String type;
        public String order_id;
        public String is_read;
        public String dateline;
        public String clientip;
        public String can_id;
    }
}
