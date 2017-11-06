package com.jhcms.run.mode;

import java.util.List;

/**
 * 作者：WangWei
 * 日期：2017/10/10 10:41
 * 描述：首页分类
 */

public class CateInfoModel {

    /**
     * cate_id : 33
     * title : 帮我买
     * desc : 帮我买
     * config : ["13","13","13","13"]
     * photo : http://img01.jhcms.com/wmdemo/photo/201708/20170810_34687428E9E19D99F36DC5AF2078F911.jpg
     */

    private String cate_id;
    private String title;
    private String desc;
    private String photo;
    private List<String> config;

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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public List<String> getConfig() {
        return config;
    }

    public void setConfig(List<String> config) {
        this.config = config;
    }
}
