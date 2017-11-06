package com.jhcms.common.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by admin on 2017/7/24.
 */
public class LookTuanOrderDetailsActivity_data implements Serializable {


    /**
     * detail : {"comment_id":"35","shop_id":"1","uid":"56","order_id":"2702","score":"3","score_fuwu":"0","score_kouwei":"0","content":"哈哈","pei_time":"30","have_photo":"1","reply":"","reply_ip":"","reply_time":"0","closed":"0","clientip":"112.26.23.195","dateline":"1500875663","u_mobile":"","shop_title":"卡旺卡","shop_logo":"photo/201705/20170512_694BBFE915ADA3E020A3172107AB4713.jpg"}
     */

    private DetailBean detail;

    public DetailBean getDetail() {
        return detail;
    }

    public void setDetail(DetailBean detail) {
        this.detail = detail;
    }

    public static class DetailBean  implements Serializable{
        /**
         * comment_id : 35
         * shop_id : 1
         * uid : 56
         * order_id : 2702
         * score : 3
         * score_fuwu : 0
         * score_kouwei : 0
         * content : 哈哈
         * pei_time : 30
         * have_photo : 1
         * reply :
         * reply_ip :
         * reply_time : 0
         * closed : 0
         * clientip : 112.26.23.195
         * dateline : 1500875663
         * u_mobile :
         * shop_title : 卡旺卡
         * shop_logo : photo/201705/20170512_694BBFE915ADA3E020A3172107AB4713.jpg
         */

        private String comment_id;
        private String shop_id;
        private String uid;
        private String order_id;
        private String score;
        private String score_fuwu;
        private String score_kouwei;
        private String content;
        private String pei_time;
        private String have_photo;
        private String reply;
        private String reply_ip;
        private String reply_time;
        private String closed;
        private String clientip;
        private String dateline;
        private String u_mobile;
        private String shop_title;
        private String shop_logo;
        private ArrayList<photoModel> photos;

        public ArrayList<photoModel> getPhotos() {
            return photos;
        }

        public void setPhotos(ArrayList<photoModel> photos) {
            this.photos = photos;
        }

        public String getComment_id() {
            return comment_id;
        }

        public void setComment_id(String comment_id) {
            this.comment_id = comment_id;
        }

        public String getShop_id() {
            return shop_id;
        }

        public void setShop_id(String shop_id) {
            this.shop_id = shop_id;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getOrder_id() {
            return order_id;
        }

        public void setOrder_id(String order_id) {
            this.order_id = order_id;
        }

        public String getScore() {
            return score;
        }

        public void setScore(String score) {
            this.score = score;
        }

        public String getScore_fuwu() {
            return score_fuwu;
        }

        public void setScore_fuwu(String score_fuwu) {
            this.score_fuwu = score_fuwu;
        }

        public String getScore_kouwei() {
            return score_kouwei;
        }

        public void setScore_kouwei(String score_kouwei) {
            this.score_kouwei = score_kouwei;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getPei_time() {
            return pei_time;
        }

        public void setPei_time(String pei_time) {
            this.pei_time = pei_time;
        }

        public String getHave_photo() {
            return have_photo;
        }

        public void setHave_photo(String have_photo) {
            this.have_photo = have_photo;
        }

        public String getReply() {
            return reply;
        }

        public void setReply(String reply) {
            this.reply = reply;
        }

        public String getReply_ip() {
            return reply_ip;
        }

        public void setReply_ip(String reply_ip) {
            this.reply_ip = reply_ip;
        }

        public String getReply_time() {
            return reply_time;
        }

        public void setReply_time(String reply_time) {
            this.reply_time = reply_time;
        }

        public String getClosed() {
            return closed;
        }

        public void setClosed(String closed) {
            this.closed = closed;
        }

        public String getClientip() {
            return clientip;
        }

        public void setClientip(String clientip) {
            this.clientip = clientip;
        }

        public String getDateline() {
            return dateline;
        }

        public void setDateline(String dateline) {
            this.dateline = dateline;
        }

        public String getU_mobile() {
            return u_mobile;
        }

        public void setU_mobile(String u_mobile) {
            this.u_mobile = u_mobile;
        }

        public String getShop_title() {
            return shop_title;
        }

        public void setShop_title(String shop_title) {
            this.shop_title = shop_title;
        }

        public String getShop_logo() {
            return shop_logo;
        }

        public void setShop_logo(String shop_logo) {
            this.shop_logo = shop_logo;
        }
    }
}
