package com.jhcms.common.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by admin on 2017/7/6.
 */
public class LookMerchantEvaluationData implements Serializable {
   private  LookMerchantEvaluationdetail detail;

    public LookMerchantEvaluationdetail getDetail() {
        return detail;
    }

    public void setDetail(LookMerchantEvaluationdetail detail) {
        this.detail = detail;
    }

  public    class LookMerchantEvaluationdetail implements  Serializable{
        /**
         * comment_id : 85
         * shop_id : 1
         * uid : 56
         * order_id : 1549
         * score : 3
         * score_peisong : 4
         * score_avg : 3.5
         * content : 哈哈
         * pei_time : 10
         * have_photo : 1
         * reply :
         * reply_ip :
         * reply_time : 0
         * closed : 0
         * clientip : 112.26.23.195
         * dateline : 1499323715
         * order_intro :
         * songda : 1499320185
         * shop_title : 卡旺卡
         * shop_logo : photo/201705/20170512_694BBFE915ADA3E020A3172107AB4713.jpg
         */

        private String comment_id;
        private String shop_id;
        private String uid;
        private String order_id;
        private String score;
        private String score_peisong;
        private String score_avg;
        private String content;
        private String pei_time;
        private String have_photo;
        private String reply;
        private String reply_ip;
        private String reply_time;
        private String closed;
        private String clientip;
        private String dateline;
        private String order_intro;
        private String songda;
        private String shop_title;
        private String shop_logo;
        ArrayList<photoModel> photos;

        public ArrayList<photoModel> getPhotos() {
            return photos;
        }

        public void setPhotos(ArrayList<photoModel> photos) {
            this.photos = photos;
        }

        private ArrayList<ProductModle> product_list;

        public ArrayList<ProductModle> getProduct_list() {
            return product_list;
        }

        public void setProduct_list(ArrayList<ProductModle> product_list) {
            this.product_list = product_list;
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

        public String getScore_peisong() {
            return score_peisong;
        }

        public void setScore_peisong(String score_peisong) {
            this.score_peisong = score_peisong;
        }

        public String getScore_avg() {
            return score_avg;
        }

        public void setScore_avg(String score_avg) {
            this.score_avg = score_avg;
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

        public String getOrder_intro() {
            return order_intro;
        }

        public void setOrder_intro(String order_intro) {
            this.order_intro = order_intro;
        }

        public String getSongda() {
            return songda;
        }

        public void setSongda(String songda) {
            this.songda = songda;
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
