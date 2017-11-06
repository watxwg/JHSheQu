package com.jhcms.mall.model;

/**
 * 作者：WangWei
 * 日期：2017/8/21 10:43
 * 描述：首页cate
 */

public class CateModel {


    /**
     * cate_id : 1
     * title : 美食 标题
     * link : http://o2o.jhcms.cn/shop/index.html?cate_id=1 此图片对应的点击事件超链接
     * thumb : photo/201705/20170509_D28597AA3E47C00D6179BE5D18C4AD00.png 图片链接
     */

    private String cate_id;
    private String title;
    private String link;
    private String thumb;

    public String getCate_id() {
        return cate_id;
    }

    public void setCate_id(String cate_id) {
        this.cate_id = cate_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }
}
