package com.jhcms.common.model;

/**
 * Created by admin on 2017/7/19.
 */
public class WebviewShareModel {

    /**
     * title : 分享标题
     * desc : 分享描述
     * img : http://www.jhcms.com/themes/default/product/waimai/statics/images/logo.jpg
     * link : http://www.jhcms.com
     */

    private String title;
    private String desc;
    private String img;
    private String link;

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

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
