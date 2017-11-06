package com.jhcms.waimaiV3.model;

/**
 * Created by WangLu on 16/7/16.
 */
public class ShareItem {
    private String logo;
    private String title;
    private String url;
    private String description;
    private Integer REImageRocs;

    public Integer getREImageRocs() {
        return REImageRocs;
    }

    public void setREImageRocs(Integer REImageRocs) {
        this.REImageRocs = REImageRocs;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}


