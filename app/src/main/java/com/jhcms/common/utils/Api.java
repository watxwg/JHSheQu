package com.jhcms.common.utils;

import com.jhcms.waimaiV3.model.Goods;

import java.util.ArrayList;

/**
 * Created by wangyujie
 * Date 2017/5/31.
 * TODO 接口
 */

public class Api {

    /*社区域名*/
    public static String URL = "o2o.jhcms.cn";
     public static String APP_URL = "http://waimai." + URL;

    /*外卖域名*/
//    public static String URL = "waimai.jhcms.cn";
//    public static String APP_URL = "http://" + URL + "/waimai";

    /*商城域名*/
//    public static String URL = "mall.jhcms.cn";
//    public static String APP_URL = "http://" + URL + "/mall";

//        public static final String IMAGE_URL = "http://" + URL + "/attachs/";
    public static final String IMAGE_URL = "";
    public static final String BASE_URL = "http://" + URL + "/api.php";

    public static final String CLIENT_API = "CUSTOM";
    public static final String CLIENT_OS = "ANDROID";
    public static final String CLIENT_VER = "1.0.0";
    public static final String CITY_ID = "0551";
    public static String TOKEN = "";
    /**
     * GAODE 使用高德地图
     */
    public static String GAODE = "GAODE";
    /**
     * GOOGLE 使用谷歌地图
     */
    public static String GOOGLE = "GOOGLE";
    /*app升级问题*/
    public static final String MAGIC_APPVER = "magic/appver";
    /*外卖token*/
    public static String WAIMAI_TOKEN = "";
    /*会员登录*/
    public static final String LOGIN = "client/passport/login";
    /*退出登录*/
    public static final String LOGIN_OUT = "client/passport/loginout";
    /*微信配置*/
    public static final String GET_WECHAT = "client/data/get_wechat";
    /*城市数据*/
    public static final String GET_CITY = "client/data/city";
    /*微信登陆*/
    public static final String WXLOGIN = "client/passport/wxlogin";
    /*微信绑定*/
    public static final String WXBIND = "client/passport/wxbind";
    /*首页*/
    public static final String CLIENT_HOME = "client/v3/index";
    /*首页广告*/
    public static final String CLIENT_ADV_START = "client/adv/start";
    /*外卖首页*/
    public static final String WAIMAI_HOME = "client/v3/waimai/index";
    /*会员消息*/
    public static final String WAIMAI_MSG = "client/member/msg";
    /*阅读消息*/
    public static final String WAIMAI_READMSG = "client/member/readmsg";
    /*外卖店铺详情*/
    public static final String WAIMAI_SHOP_DETAIL = "client/v3/waimai/shop/detail";
    /*会员修改地址*/
    public static final String WAIMAI_ADDRESS_UPDATE = "client/member/addr/update";
    /*会员添加地址 client/wmclient/addr/create*/
    public static final String WAIMAI_ADDRESS_CREATE = "client/member/addr/create";
    /*会员地址删除  client/member/addr/delete */
    public static final String WAIMAI_ADDRESS_DELETE = "client/member/addr/delete";
    /* 会员收藏商家 client/wmclient/collect/collect*/
    public static final String WAIMAI_COLLECTION_MERCHANT = "client/wmclient/collect/collect";

    /*会员收索 hotsearch*/
    public static final String WAIMAI_HOTSEARCH = "client/v3/waimai/shop/hotsearch";
    /*会员搜索  client/v3/waimai/shop/search*/
    public static final String WAIMAI_SEARCH = "client/v3/waimai/shop/search";


    /*订单模块*/
    /*订单列表 client/v3/waimai/order/index*/
    public static final String WAIMAI_ORDER = "client/v3/waimai/order/index";
    /*  client/v3/waimai/order/chargeback*/
    public static final String WAIMAI_ORDER_CHARGEBACK = "client/v3/waimai/order/chargeback";
    /*推单client/v3/waimai/order/remind*/
    public static final String WAIMAI_ORDER_REMIND = "client/v3/waimai/order/remind";
    /*确认送达 client/v3/waimai/order/confrim*/
    public static final String WAIMAI_ORDER_CONFRIM = "client/v3/waimai/order/confirm";

    /*退款理由client/v3/waimai/order/payback */
    public static final String WAIMAI_ORDER_PAYBACK = "client/v3/waimai/order/payback";

    /*申请客服介入  client/v3/waimai/order/kefu*/
    public static final String WAIMAI_ORDER_KEFU = "client/v3/waimai/order/kefu";
    /*订单评价  client/v3/waimai/order/comment*/
    public static final String WAIMAI_ORDER_COMMMENT = "client/v3/waimai/order/comment";
    /*订单详情  client/v3/waimai/order/detail*/
    public static final String WAIMAI_ORDER_DETAIL = "client/v3/waimai/order/detail";
    /*商品详情*/
    public static final String WAIMAI_SHOP_PRODUCT = "client/v3/waimai/shop/product";
    /*外卖评价*/
    public static final String WAIMAI_SHOP_COMMENTS = "client/v3/waimai/shop/comments";
    /*商家信息*/
    public static final String WAIMAI_SHOP_INFO = "client/v3/waimai/shop/info";
    /*外卖搜索*/
    public static final String WAIMAI_SHOP_SEARCH = "client/v3/waimai/shop/search";
    /*外卖搜索--热搜*/
    public static final String WAIMAI_SHOP_HOTSEARCH = "client/v3/waimai/shop/hotsearch";
    /*下单接口*/
    public static final String WAIMAI_SHOP_PLACE_AN_ORDER = "client/v3/waimai/order/order";
    /*订单预处理*/
    public static final String WAIMAI_SHOP_ORDER_PREINFO = "client/v3/waimai/order/preinfo";
    /*创建订单*/
    public static final String WAIMAI_SHOP_ORDER_CREATE = "client/v3/waimai/order/create";
    /*余额微信支付宝支付*/
    public static final String WAIMAI_SHOP_PAY_ORDER = "client/payment/order";
    /*client/member/log/money*/
    public static final String WAIMAI_MONEY = "client/member/log";

    /*订单详情*/
    public static final String WAIMAI_SHOP_ORDER_DETAIL = "client/v3/waimai/order/detail";


    /*外卖商家列表*/
    public static final String WAIMAI_SHOP_LIST = "client/v3/waimai/shop/shoplist";
    /*外卖分类   client/v3/waimai/cate/index*/
    public static final String WAIMAI_SORT = "client/v3/waimai/cate/index";
    /*我的收藏  client/wmclient/collect/index*/
    public static final String WAIMAI_COLLECT = "client/wmclient/collect/index";

    /*会员部分*/
    /*会员注册*/
    public static final String SHEQU_REGISTER = "client/passport/signup";
    /*获取会员信息*/
    public static final String SHEQU_USERINFO = "client/member/info";
    /* 会员忘记密码*/
    public static final String SHEQU_USSERFOGOT = "client/passport/forgot";
    /*会员上传图片*/
    public static final String SHEQU_UPLOADFACE = "client/member/updateface";
    /*上传图片*/
    public static final String SHEQU_UPDATENAME = "client/member/updatename";

    public static final String complaint = "client/v3/waimai/order/complaint";
    /*更换手机号  client/member/updatemobile*/
    public static final String SHEQU_UPDATEMOBILE = "client/member/updatemobile";

    /*我的地址 client/member/addr/index*/
    public static final String WAIMAI_ADDRESS_LIST = "client/member/addr/index";
    /*我的地址(下单用)*/
    public static final String WAIMAI_ORDER_ADDRESS_LIST = "client/wmclient/addr/index";

    /*基础*/
    /*短信验证*/
    public static final String SHEQU_SMS_VERIFICATION = "magic/sendsms";

    public static final String WAIMAI_UPSATESEX = "client/member/updatesex";
    /*client/payment/package*/
    public static final String WAIMAI_PACKAGE = "client/payment/package";
    /*client/payment/money*/
    public static final String WAIMAI_PAYMENT_MONEY = "client/payment/money";

    /*client/member/passwd*/
    public static final String WAIMAI_PASSWAD = "client/member/passwd";
    /*当前定位的经纬度*/
    public static double LAT;
    public static double LON;
    /*优惠券*/
    public static String COUPON_URL = APP_URL + "/shop/getcoupon-";
    /*client/member/bindweixin*/
    public static final String WAIMAI_BINDWEIXIN = "client/member/bindweixin";

    /*client/member/nobindweixin*/
    public static final String WAIMAI_NOBINDWEIXIN = "client/member/nobindweixin";
    /*magic/verify 图形验证*/
    public static final String WAIMAI_VERIFY = "magic/verify";
    /*  client/v3/waimai/order/comment*/
    public static final String WAIMAI_COMMENT_DETAIL = "client/v3/waimai/order/comment_detail";
    /*client/v3/waimai/shop/product*/
    public static final String WAIMAI_SHOP_COMMENT = "client/v3/waimai/shop/product";
    /*是否更新商品信息*/
    public static boolean isUpDate = false;
    public static int Good_Num;
    public static Goods Good_Item;


    /*极光registrationID*/
    public static String REGISTRATION_ID;
    /*除去热销中的所有商品*/
    public static ArrayList<Goods> GOOD_LIST;

    public static String CITY_CODE = "0551";

    //TODO  团购
    /*团购client/v3/tuan/order/items*/
    public static final String WAIMAI_TUAN_ORDER = "client/v3/tuan/order/items";

    /*团购首页*/
    public static String GROUP_HOME = "client/v3/tuan/index";

    /*团购商家详情*/
    public static String WAIMAI_TUAN_SHOP_DETAIL = "client/v3/tuan/shop/detail";
    /*商品详情*/
    public static String WAIMAI_TUAN_SHOP_TUAN = "client/v3/tuan/shop/tuan";
    /*商家全部商品*/
    public static String WAIMAI_TUAN_ALL_GOODS = "client/v3/tuan/shop/products";
    /*团购分类地区*/
    public static final String WAIMAI_TUAN_CATES_AREAS = "client/v3/tuan/shop/cates_areas";
    /*团购商家*/
    public static final String WAIMAI_TUAN_SHOP_ITEMS = "client/v3/tuan/shop/items";
    /*团购商品*/
    public static final String WAIMAI_TUAN_SHOP_GOODS = "client/v3/tuan/shop/goods";
    /*团购收藏*/
    public static final String WAIMAI_TUAN_SHOP_COLLECT = "client/v3/tuan/shop/collect";
    /*团购创建订单*/
    public static final String WAIMAI_TUAN_SHOP_CREATE = "client/v3/tuan/order/create";
    /*订单详情*/
    public static final String WAIMAI_TUAN_ORDER_DETAIL = "client/v3/tuan/order/detail";
    /*商家相册*/
    public static final String WAIMAI_TUAN_SHOP_ALBUM = "client/v3/tuan/shop/album";
    /*团购取消 client/v3/tuan/order/cancel*/
    public static final String WAIMAI_TUAN_ORDER_CANCEL = "client/v3/tuan/order/cancel";
    /*团购评价client/v3/tuan/order/comment*/
    public static final String TUAN_ORDER_COMMENT = "client/v3/tuan/order/comment";
    /*团购评价详情  client/v3/tuan/order/comment_detail*/
    public static final String TUAN_ORDER_COMMENT_DETAIL = "client/v3/tuan/order/comment_detail";
    /*优惠买单列表 client/v3/tuan/maidan/items*/
    public static final String TUAN_ORDER_MAIDAN_ITEMS = "client/v3/tuan/maidan/items";
    /*订单详情 client/v3/tuan/order/detail*/
    public static final String WAIMAI_TUAN_ORDER_DEATAIL = "client/v3/tuan/order/detail";
    public static final String TUAN_ORDER_MAIDAN_DETAIL = "client/v3/tuan/maidan/detail";
    /*优惠买单订单创建*/
    public static final String TUAN_ORDER_MAIDAN_CREATE = "client/v3/tuan/maidan/create";
    /*团购热搜*/
    public static final String TUAN_SHOP_HOTSEARCH = "client/v3/tuan/shop/hotsearch";

    /*---------------------------商城API----------------------------------*/
    public static final String MALL_HOME = "client/v3/mall/index";
    public static final String MALL_ADDR_INDEX = "client/v3/mall/addr/index";
    public static final String MALL_SHOP_SEARCH_HOT = "client/v3/mall/shop/hotsearch";
    public static final String MALL_PRODUCT_SEARCH_HOT = "client/v3/mall/product/hotsearch";
    public static final String MALL_PRODUCT_SEARCH = "client/v3/mall/product/items";
    public static final String MALL_SHOP_SEARCH = "client/v3/mall/shop/items";

    public static final String MALL_MEMBER_INFO = "client/v3/mall/member/index";
    public static final String MALL_ADDR_SETDEFAULT = "client/v3/mall/addr/setdefault";
    public static final String MALL_ADDR_DELETE = "client/v3/mall/addr/delete";
    /*订单列表*/
    public static final String MALL_ORDER_ITEMS = "client/v3/mall/order/items";
    /*订单取消*/
    public static final String MALL_ORDER_CANCEL = "client/v3/mall/order/cancel";
    /*确认收货*/
    public static final String MALL_ORDER_COMPLETE = "client/v3/mall/order/complete";
    /*所有地区*/
    public static final String MALL_ADDR_ALL_AREAS = "client/v3/mall/addr/all_areas";
    /*地址详情*/
    public static final String MALL_ADDR_DETAIL = "client/v3/mall/addr/detail";
    /*创建地址*/
    public static final String MALL_ADDR_CREATE = "client/v3/mall/addr/create";
    /*地址编辑*/
    public static final String MALL_ADDR_EDIT = "client/v3/mall/addr/edit";
    /*信用卡部分*/
    public static final String Card_List = "client/member/card/index";

    public static final String CardBind = "client/member/card/bind";
    //client/member/card/unbind
    public static final String CardUnbind = "client/member/card/unbind";


    public static final String MALL_CATEGORY = "client/v3/mall/shop/cates";
    public static final String MALL_CATEGORY_RECOMMEND = "client/v3/mall/shop/recommend";
    public static final String MALL_PRODUCT_RANK = "client/v3/mall/product/rank";
    public static final String MALL_SHOP_RANK = "client/v3/mall/shop/rank";
    public static final String MALL_SHOP_DETAIL = "client/v3/mall/shop/detail";


    /*获取购物车*/
    public static final String MALL_ORDER_GET_CART = "client/v3/mall/order/get_cart";


    public static final String MALL_SHOP_PRODUCT = "client/v3/mall/shop/products";
    public static final String MALL_PRODUCT_DETAIL = "client/v3/mall/product/detail";
    public static final String MALL_COLLECT = "client/v3/mall/member/collect";
    public static final String MALL_SHOP_CATE = "client/v3/mall/shop/shopcate";
    //订单预处理
    public static final String MALL_ORDER_PREPROCESS = "client/v3/mall/order/order";
    public static final String MALL_ORDER_CREATE = "client/v3/mall/order/create";
    public static final String MALL_PAYMENT_ORDERS = "client/payment/orders";
    /*删除商家购物车*/
    public static final String MALL_DELETE_BY_SHOP = "client/v3/mall/order/delete_by_shop";
    /*减少购物车*/
    public static final String MALL_DELCART = "client/v3/mall/order/delcart";
    /*添加购物车*/
    public static final String MALL_ADDCART = "client/v3/mall/order/addcart";


    /*-----------------------------跑腿-------------------------------------------------*/
    /*个人中心首页*/
    public static final String PAOTUI_MEMBER_INDEX = "client/v3/paotui/member/index";
    /*订单列表*/
    public static final String PAOTUI_ORDER_ITEMS = "client/v3/paotui/order/items";
    //首页详情
    public static final String PAOTUI_HOME_DATA = "client/v3/paotui/index/index";
    //选择下单类型
    public static final String PAOTUI_SELECT_ORDER_TYPE = "client/v3/paotui/order/order";
    //获取地址列表
    public static final String PAOTUI_GET_ADDRESS_LIST = "client/v3/paotui/addr/items";
    //获取订单金额
    public static final String PAOTUI_GET_ORDER_PRICE = "client/v3/paotui/order/preinfo";
    //创建订单
    public static final String PAOTUI_CREATE_ORDER = "client/v3/paotui/order/ordercreate";
    //费用说明
    public static final String PAOTUI_CHARGE_EXPLAIN="client/v3/paotui/config/index";
    /*订单详情*/
    public static final String PAOTUI_ORDER_DETAIL = "client/v3/paotui/order/detail";
    /*取消订单*/
    public static final String PAOTUI_ORDER_CANEL = "client/v3/paotui/order/canel";
    /*催单*/
    public static final String PAOTUI_ORDER_CUI = "client/v3/paotui/order/cui";
    /*确认订单*/
    public static final String PAOTUI_ORDER_CONFIRM = "client/v3/paotui/order/confirm";
    /*评论订单*/
    public static final String PAOTUI_ORDER_COMMENT = "client/v3/paotui/order/comment";
    /*评论详情*/
    public static final String PAOTUI_ORDER_COMMON_DETAIL = "client/v3/paotui/order/common_detail";
    /*保存评论*/
    public static final String PAOTUI_ORDER_COMMON_HANDLE = "client/v3/paotui/order/common_handle";
    //再来一单
    public static final String PAOTUI_ORDER_AGAIN = "client/v3/paotui/order/again";
}
