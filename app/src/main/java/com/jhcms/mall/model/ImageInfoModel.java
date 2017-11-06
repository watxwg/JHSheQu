package com.jhcms.mall.model;

import com.jhcms.common.utils.Api;
import com.jhcms.mall.adapter.AdAdapter;

/**
 * 作者：WangWei
 * 日期：2017/8/21 10:41
 * 描述：图片数据
 */

public class ImageInfoModel implements AdAdapter.ImageUrlProvider{

    /**
     * title : 1 标题
     * link : https://www.baidu.com/ 此图片对应点击事件的超链接
     * thumb : photo/201707/20170724_05DB54DB2CF9A0ADBD9B6954B1844E9E.png 图片链接
     */

    private String title;
    private String link;
    private String thumb;
    private String photo;

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
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

    @Override
    public String getImageUrl() {
        return Api.IMAGE_URL+getThumb();
    }
}
