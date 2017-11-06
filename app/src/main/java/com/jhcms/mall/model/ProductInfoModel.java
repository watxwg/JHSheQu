package com.jhcms.mall.model;

import java.util.HashMap;
import java.util.List;

/**
 * 作者：WangWei
 * 日期：2017/9/14 17:52
 * 描述：商品信息
 */

public class ProductInfoModel {
    /**
     * product_id : 1
     * title : 测试商品
     * photo : photo/201705/20170510_CE597D46006463C2A376044C47478317.jpg
     * price : 0.01
     * wei_price : 0.01
     * intro : 近日在自己的节目中，德雷蒙德-格林曾这样评价奥利尼克。我不会去尊重这样的球员，你不能总是这样，我不会尊重这种人，奥利尼克是个肮脏的球员。他用肘子打了乌布雷的脸还有脖子，我不明白的是，这种人怎么能让他逃脱处罚。看看他都做了什么，每个人都看到了他做的那些事情。
     * sales : 26
     * stock : 26
     * collect : 0
     */

    private String product_id;
    private String title;
    private String photo;
    private String price;
    private String wei_price;
    private String intro;
    private String sales;
    private String stock;
    private String collect;
    private ShopInfo shop;
    private CommentCount comment_count;
    private String coupon_url;//优惠券链接
    private String link;//详情和评论链接
    private List<SpecificationInfoModel> attrgroups;
    private List<CommentInfoModel> comment_items;
    private ShareInfoModel share_info;
    private HashMap<String,SpecificaitonDetailModel> attrstocks;

    public String getCoupon_url() {
        return coupon_url;
    }

    public void setCoupon_url(String coupon_url) {
        this.coupon_url = coupon_url;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public ShareInfoModel getShare_info() {
        return share_info;
    }

    public void setShare_info(ShareInfoModel share_info) {
        this.share_info = share_info;
    }

    public HashMap<String, SpecificaitonDetailModel> getAttrstocks() {
        return attrstocks;
    }

    public void setAttrstocks(HashMap<String, SpecificaitonDetailModel> attrstocks) {
        this.attrstocks = attrstocks;
    }

    public ShopInfo getShop() {
        return shop;
    }

    public void setShop(ShopInfo shop) {
        this.shop = shop;
    }

    public CommentCount getComment_count() {
        return comment_count;
    }

    public void setComment_count(CommentCount comment_count) {
        this.comment_count = comment_count;
    }

    public List<SpecificationInfoModel> getAttrgroups() {
        return attrgroups;
    }

    public void setAttrgroups(List<SpecificationInfoModel> attrgroups) {
        this.attrgroups = attrgroups;
    }

    public List<CommentInfoModel> getComment_items() {
        return comment_items;
    }

    public void setComment_items(List<CommentInfoModel> comment_items) {
        this.comment_items = comment_items;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getWei_price() {
        return wei_price;
    }

    public void setWei_price(String wei_price) {
        this.wei_price = wei_price;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getSales() {
        return sales;
    }

    public void setSales(String sales) {
        this.sales = sales;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public String getCollect() {
        return collect;
    }

    public void setCollect(String collect) {
        this.collect = collect;
    }

    /**
     * 店铺信息
     */
    public static class ShopInfo{

        /**
         * shop_id : 1
         * title : 卡旺卡(商城)
         * phone : 13909090909
         * logo : photo/201705/20170509_EC326F962147ADAE552155FE04DD57CC.jpg
         * collect : 0
         */

        private String shop_id;
        private String title;
        private String phone;
        private String logo;
        private String collect;

        public String getShop_id() {
            return shop_id;
        }

        public void setShop_id(String shop_id) {
            this.shop_id = shop_id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public String getCollect() {
            return collect;
        }

        public void setCollect(String collect) {
            this.collect = collect;
        }
    }

    /**
     * 评论数量
     */
    public static class CommentCount{


        /**
         * all : 3
         * my : 3
         * bmy : 0
         * yt : 1
         */

        private String all;
        private String my;
        private String bmy;
        private String yt;

        public String getAll() {
            return all;
        }

        public void setAll(String all) {
            this.all = all;
        }

        public String getMy() {
            return my;
        }

        public void setMy(String my) {
            this.my = my;
        }

        public String getBmy() {
            return bmy;
        }

        public void setBmy(String bmy) {
            this.bmy = bmy;
        }

        public String getYt() {
            return yt;
        }

        public void setYt(String yt) {
            this.yt = yt;
        }
    }


}
