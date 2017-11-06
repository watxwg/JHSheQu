package com.jhcms.mall.model;

import java.util.List;

/**
 * 作者：WangWei
 * 日期：2017/9/14 17:57
 * 描述：评论信息
 */

public class CommentInfoModel {

    /**
     * comment_id : 7
     * title : 测试商品
     * content : 已发货vhghdtyii
     * is_good : 1
     * reply :
     * dateline : 1497318528
     * nickname : 北辰灬陨落
     * face : photo/201707/20170726_347C0B763B155A3A3051C117A02C57AE.png
     * photos : []
     */

    private String comment_id;
    private String title;
    private String content;
    private String is_good;
    private String reply;
    private String dateline;
    private String nickname;
    private String face;
    private List<String> photos;

    public String getComment_id() {
        return comment_id;
    }

    public void setComment_id(String comment_id) {
        this.comment_id = comment_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getIs_good() {
        return is_good;
    }

    public void setIs_good(String is_good) {
        this.is_good = is_good;
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    public String getDateline() {
        return dateline;
    }

    public void setDateline(String dateline) {
        this.dateline = dateline;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getFace() {
        return face;
    }

    public void setFace(String face) {
        this.face = face;
    }

    public List<String> getPhotos() {
        return photos;
    }

    public void setPhotos(List<String> photos) {
        this.photos = photos;
    }
}
