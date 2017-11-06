package com.jhcms.run.mode;

import com.google.gson.Gson;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wangyujie
 * on 2017/10/9.11:47
 * TODO
 */

public class Data_Run_Order_Details implements Serializable {


    /**
     * juli : 距您还有131m
     * kefu_mobile : 0551-64278115
     * staff_mobile : 17755555555
     * staff : {"staff_id":"105","name":"蛇皮怪","mobile":"17755555555","face":"http://img01.jhcms.com/wmdemo/photo/201709/20170920_53236F8F840EBD52C53A9E6D2FC5F9F2.png","lat":"31.836543","lng":"117.256087"}
     * from : mai
     * comment : {"comment_id":"31","score":"5","content":"不错"}
     * show_label : []
     * msg : 已评价
     * btn : {"pay":"0","cancel":"0","cui":"0","confirm":"0","comment":"0","again":"1"}
     * product_info : ["来包烟"]
     * s_addr : 望江西路与合作化南路交汇处F1层L1-07号110
     * m_addr : 就近购买
     * pei_amount : 3.00
     * tip : 0.00
     * hongbao : 0.00
     * payed_money : 3.00
     * pei_time : 立刻送达
     * pei_distance : 就近购买
     * order_id : 1773
     * dateline : 2017-09-28 16:28:51
     * order_status : 8
     * pay_status : 1
     * no_pay_canel_time : 0
     * pay_type : 余额支付
     */

    private String juli;
    private String kefu_mobile;
    private String staff_mobile;
    private StaffBean staff;
    private String from;
    private CommentBean comment;
    private String msg;
    private Data_Run_Order_UnDone.ItemsBean.BtnBean btn;
    private String s_addr;
    private String m_addr;
    private String pei_amount;
    private String tip;
    private String hongbao;
    private String payed_money;
    private String pei_time;
    private String pei_distance;
    private String order_id;
    private String dateline;
    private String order_status;
    private String pay_status;
    private String no_pay_canel_time;
    private String pay_type;
    private List<String> show_label;
    private List<String> product_info;

    public static Data_Run_Order_Details objectFromData(String str) {

        return new Gson().fromJson(str, Data_Run_Order_Details.class);
    }

    public String getJuli() {
        return juli;
    }

    public void setJuli(String juli) {
        this.juli = juli;
    }

    public String getKefu_mobile() {
        return kefu_mobile;
    }

    public void setKefu_mobile(String kefu_mobile) {
        this.kefu_mobile = kefu_mobile;
    }

    public String getStaff_mobile() {
        return staff_mobile;
    }

    public void setStaff_mobile(String staff_mobile) {
        this.staff_mobile = staff_mobile;
    }

    public StaffBean getStaff() {
        return staff;
    }

    public void setStaff(StaffBean staff) {
        this.staff = staff;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public CommentBean getComment() {
        return comment;
    }

    public void setComment(CommentBean comment) {
        this.comment = comment;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Data_Run_Order_UnDone.ItemsBean.BtnBean getBtn() {
        return btn;
    }

    public void setBtn(Data_Run_Order_UnDone.ItemsBean.BtnBean btn) {
        this.btn = btn;
    }

    public String getS_addr() {
        return s_addr;
    }

    public void setS_addr(String s_addr) {
        this.s_addr = s_addr;
    }

    public String getM_addr() {
        return m_addr;
    }

    public void setM_addr(String m_addr) {
        this.m_addr = m_addr;
    }

    public String getPei_amount() {
        return pei_amount;
    }

    public void setPei_amount(String pei_amount) {
        this.pei_amount = pei_amount;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public String getHongbao() {
        return hongbao;
    }

    public void setHongbao(String hongbao) {
        this.hongbao = hongbao;
    }

    public String getPayed_money() {
        return payed_money;
    }

    public void setPayed_money(String payed_money) {
        this.payed_money = payed_money;
    }

    public String getPei_time() {
        return pei_time;
    }

    public void setPei_time(String pei_time) {
        this.pei_time = pei_time;
    }

    public String getPei_distance() {
        return pei_distance;
    }

    public void setPei_distance(String pei_distance) {
        this.pei_distance = pei_distance;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getDateline() {
        return dateline;
    }

    public void setDateline(String dateline) {
        this.dateline = dateline;
    }

    public String getOrder_status() {
        return order_status;
    }

    public void setOrder_status(String order_status) {
        this.order_status = order_status;
    }

    public String getPay_status() {
        return pay_status;
    }

    public void setPay_status(String pay_status) {
        this.pay_status = pay_status;
    }

    public String getNo_pay_canel_time() {
        return no_pay_canel_time;
    }

    public void setNo_pay_canel_time(String no_pay_canel_time) {
        this.no_pay_canel_time = no_pay_canel_time;
    }

    public String getPay_type() {
        return pay_type;
    }

    public void setPay_type(String pay_type) {
        this.pay_type = pay_type;
    }

    public List<String> getShow_label() {
        return show_label;
    }

    public void setShow_label(List<String> show_label) {
        this.show_label = show_label;
    }

    public List<String> getProduct_info() {
        return product_info;
    }

    public void setProduct_info(List<String> product_info) {
        this.product_info = product_info;
    }

    public static class StaffBean {
        /**
         * staff_id : 105
         * name : 蛇皮怪
         * mobile : 17755555555
         * face : http://img01.jhcms.com/wmdemo/photo/201709/20170920_53236F8F840EBD52C53A9E6D2FC5F9F2.png
         * lat : 31.836543
         * lng : 117.256087
         */

        private String staff_id;
        private String name;
        private String mobile;
        private String face;
        private String lat;
        private String lng;

        public static StaffBean objectFromData(String str) {

            return new Gson().fromJson(str, StaffBean.class);
        }

        public String getStaff_id() {
            return staff_id;
        }

        public void setStaff_id(String staff_id) {
            this.staff_id = staff_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getFace() {
            return face;
        }

        public void setFace(String face) {
            this.face = face;
        }

        public String getLat() {
            return lat;
        }

        public void setLat(String lat) {
            this.lat = lat;
        }

        public String getLng() {
            return lng;
        }

        public void setLng(String lng) {
            this.lng = lng;
        }
    }

    public static class CommentBean {
        /**
         * comment_id : 31
         * score : 5
         * content : 不错
         */

        private String comment_id;
        private String score;
        private String content;

        public static CommentBean objectFromData(String str) {

            return new Gson().fromJson(str, CommentBean.class);
        }

        public String getComment_id() {
            return comment_id;
        }

        public void setComment_id(String comment_id) {
            this.comment_id = comment_id;
        }

        public String getScore() {
            return score;
        }

        public void setScore(String score) {
            this.score = score;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }


}
