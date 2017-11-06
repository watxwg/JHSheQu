package com.jhcms.mall.model;

import com.jhcms.common.utils.Api;
import com.jhcms.mall.adapter.AdAdapter;

/**
 * 作者：WangWei
 * 日期：2017/9/20 11:31
 * 描述：广告
 */

public class AdModel implements AdAdapter.ImageUrlProvider{

    /**
     * adv_id : 1
     * shop_id : 1
     * link : http://mall.o2o.jhcms.cn
     * photo : photo/201709/20170908_9A43DE8DDFD4A32C137448AFABDF5B23.png
     * orderby : 1
     */

    private String adv_id;
    private String shop_id;
    private String link;
    private String photo;
    private String orderby;

    public String getAdv_id() {
        return adv_id;
    }

    public void setAdv_id(String adv_id) {
        this.adv_id = adv_id;
    }

    public String getShop_id() {
        return shop_id;
    }

    public void setShop_id(String shop_id) {
        this.shop_id = shop_id;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getOrderby() {
        return orderby;
    }

    public void setOrderby(String orderby) {
        this.orderby = orderby;
    }

    @Override
    public String getImageUrl() {
        return Api.IMAGE_URL+getPhoto();
    }
}
