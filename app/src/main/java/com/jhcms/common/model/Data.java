package com.jhcms.common.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class Data implements Serializable {

    public String wxtype;
    public String error;

    /*短信验证*/
    public String sms_code;
    public String code;
    /*会员注册结果*/
    public String uid;
    public String nickname;
    public String face;
    public String mobile;
    public String token;
    public ArrayList<String> tags;
    public String tuan_ticket_count;
    public String cancle_pay_count;
    public String msg_new_count;
    public String lastlogin;
    public String all_orders;
    public String no_comment_count;
    public String hongbao_count;
    public String wx_unionid;
    public String order_comment_count;
    public String go_pay_count;
    public String money;
    public String wx_openid;
    public String jifen;
    public String loginip;
    public String kefu_phone;
    /*外卖分类*/
//    public List<ShopItems> items;
    /*collect*/
    public List<CollectModel> collect;
    /*外卖首页广告*/
    public List<Adv> adv;
    /*社区首页广告*/
    public List<Adv> advs;
    /*外卖首页广告位*/
    public List<Banner> banner;
    /*社区首页广告位*/
    public List<Banner> banners;
    /*社区、外卖首页分类*/
    public List<IndexCate> index_cate;
    /*外卖首页 商铺列表*/
    public List<ShopItems> shop_items;
    /*社区首页 商铺列表*/
    public List<ShopItems> items;
    public List<ShopLikes> likes;
    public ShopDetail detail;
    /*性别*/
    public String sex;
    public String message;
    public String order_id;
    public String apk_client_packname;
    public String apk_client_version;
    public String apk_client_force_update;
    public String apk_client_download;
    public String apk_client_intro;
}
