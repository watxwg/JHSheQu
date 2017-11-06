package com.jhcms.mall.model;

/**
 * 作者：WangWei
 * 日期：2017/9/15 16:50
 * 描述：分享信息
 */

public class ShareInfoModel{

    /**
     * share_url : http://mall.o2o.jhcms.cn/product/detail-23.html
     * share_title : 测试商品
     * share_photo : http://o2o.jhcms.cn/./attachs/photo/201705/20170527_FE770C4077A29C8D83649D7120990C25.jpg
     * share_content : 测试商品很棒，快来看呀
     */

    private String share_url;
    private String share_title;
    private String share_photo;
    private String share_content;

    public String getShare_url() {
        return share_url;
    }

    public void setShare_url(String share_url) {
        this.share_url = share_url;
    }

    public String getShare_title() {
        return share_title;
    }

    public void setShare_title(String share_title) {
        this.share_title = share_title;
    }

    public String getShare_photo() {
        return share_photo;
    }

    public void setShare_photo(String share_photo) {
        this.share_photo = share_photo;
    }

    public String getShare_content() {
        return share_content;
    }

    public void setShare_content(String share_content) {
        this.share_content = share_content;
    }
}
