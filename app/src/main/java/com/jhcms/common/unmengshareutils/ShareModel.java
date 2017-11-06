package com.jhcms.common.unmengshareutils;

/**
 * Created by admin on 2017/5/9.
 */
public class ShareModel {
    private  String  Title;
    private  String Description;
    private  Integer ShareImageRes;
    private  String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public Integer getShareImageRes() {
        return ShareImageRes;
    }

    public void setShareImageRes(Integer shareImageRes) {
        ShareImageRes = shareImageRes;
    }

    public ShareModel(String title, String description, Integer shareImageRes) {
        Title = title;
        Description = description;
        ShareImageRes = shareImageRes;
    }

    @Override
    public String toString() {
        return "ShareModel{" +
                "Title='" + Title + '\'' +
                ", Description='" + Description + '\'' +
                ", ShareImageRes=" + ShareImageRes +
                '}';
    }
}
