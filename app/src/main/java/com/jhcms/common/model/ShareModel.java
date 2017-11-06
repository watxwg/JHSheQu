package com.jhcms.common.model;

/**
 * Created by admin on 2017/7/13.
 */
public class ShareModel {
    private String title;
    private  String  desc;
    private  String imgUrl;
    private  String link;

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

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @Override
    public String toString() {
        return "ShareModel{" +
                "title='" + title + '\'' +
                ", desc='" + desc + '\'' +
                ", img='" + imgUrl + '\'' +
                ", link='" + link + '\'' +
                '}';
    }
}
