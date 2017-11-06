package com.jhcms.common.model;

import java.util.List;

/**
 * Created by wangyujie
 * Date 2017/6/12.
 * TODO
 */

public class Data_WaiMai_Info {

    /**
     * detail : {"phone":"18226618664","lat":"31.812123","lng":"117.345694","addr":"财富广场","yy_peitime":[{"stime":"13:00","ltime":"20:00"},{"stime":"09:00","ltime":"12:00"}],"huodong":[{"title":"商家支持在线支付","word":"付","color":"14AAE4"}],"verify":["photo/201705/20170523_B589562031C27E8F4CD4D17D1982D080.jpg","photo/201705/20170523_39516F864D7295CA849AB5B3862A4D98.jpg","photo/201705/20170523_19E11169F45D80C0B6E3C4D23FC0B59E.jpg"],"album":["photo/201705/20170523_DADB77E7BE45B2B17946D7F39E5F618A.jpg"]}
     */

    public DetailEntity detail;


    public static class DetailEntity {
        /**
         * phone : 18226618664
         * lat : 31.812123
         * lng : 117.345694
         * addr : 财富广场
         * yy_peitime : [{"stime":"13:00","ltime":"20:00"},{"stime":"09:00","ltime":"12:00"}]
         * huodong : [{"title":"商家支持在线支付","word":"付","color":"14AAE4"}]
         * verify : ["photo/201705/20170523_B589562031C27E8F4CD4D17D1982D080.jpg","photo/201705/20170523_39516F864D7295CA849AB5B3862A4D98.jpg","photo/201705/20170523_19E11169F45D80C0B6E3C4D23FC0B59E.jpg"]
         * album : ["photo/201705/20170523_DADB77E7BE45B2B17946D7F39E5F618A.jpg"]
         */

        public String phone;
        public String lat;
        public String lng;
        public String addr;
        public String pei_type;
        public List<YyPeitimeEntity> yy_peitime;
        public List<HuodongEntity> huodong;
        public List<String> verify;
        public List<String> album;



        public static class YyPeitimeEntity {
            /**
             * stime : 13:00
             * ltime : 20:00
             */

            public String stime;
            public String ltime;

        }

        public static class HuodongEntity {
            /**
             * title : 商家支持在线支付
             * word : 付
             * color : 14AAE4
             */

            public String title;
            public String word;
            public String color;


        }
    }
}
/*
shop_id	int	商家ID
title	string	商家名称
logo	string	商家logo
orders	int	订单数
min_amount	float	起送价
freight	float	运费
pei_type	int	配送类型 0:商家配送 1：平台配送
pei_time	string	配送时间（平均）
yy_status	int	商家手动营业状态
yysj_status	string	商家配送区间，都为1时 ，显示营业中，有一个不为1 则为打烊中
huodong	array	活动
lng	number	商家经度
lat	number	商家纬度
delcare	string	公告
collect	int	是否收藏
phone	string	客服电话
addr	string	地址
online_pay	int	是否支持在线支付
yy_peitime	array	营业时间
verify	array	资质图片
album	array	环境图片
* */
