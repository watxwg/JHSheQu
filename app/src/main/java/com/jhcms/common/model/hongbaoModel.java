package com.jhcms.common.model;

import java.io.Serializable;

/**
 * Created by admin on 2017/6/15.
 */
public class hongbaoModel implements Serializable {

    /**
     * link : http://waimai.o2o.jhcms.cn/hongbao/index-593.html
     * title : 端午节江湖外卖送你大红包啦！！！
     * desc : 大额满减，低值1折，等你来吃。赶快领取吧！5
     * imgUrl : /attachs/
     */

    private String link;
    private String title;
    private String desc;
    private String imgUrl;

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
