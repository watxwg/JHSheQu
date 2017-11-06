package com.jhcms.run.mode;

import com.google.gson.Gson;

import java.io.Serializable;

/**
 * Created by wangyujie
 * on 2017/10/9.11:47
 * TODO
 */

public class Data_Run_Order_Comment implements Serializable {


    /**
     * "score": "4",
     * "pei_time": "60",
     * "content": "",
     * staff : {"staff_id":"105","name":"蛇皮怪","mobile":"17755555555","face":"http://img01.jhcms.com/wmdemo/photo/201709/20170920_53236F8F840EBD52C53A9E6D2FC5F9F2.png","lat":"31.836543","lng":"117.256087"}
     * minute : 0
     * pei_complete_time : 09:58
     * dateline : 2017-09-28 09:57:58
     */

    private StaffBean staff;
    /*评价*/
    private String minute;
    private String pei_complete_time;
    private String dateline;
    private String score;
    /*查看评价*/
    private String pei_time;
    private String content;

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getPei_time() {
        return pei_time;
    }

    public void setPei_time(String pei_time) {
        this.pei_time = pei_time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


    public static Data_Run_Order_Comment objectFromData(String str) {

        return new Gson().fromJson(str, Data_Run_Order_Comment.class);
    }

    public StaffBean getStaff() {
        return staff;
    }

    public void setStaff(StaffBean staff) {
        this.staff = staff;
    }

    public String getMinute() {
        return minute;
    }

    public void setMinute(String minute) {
        this.minute = minute;
    }

    public String getPei_complete_time() {
        return pei_complete_time;
    }

    public void setPei_complete_time(String pei_complete_time) {
        this.pei_complete_time = pei_complete_time;
    }

    public String getDateline() {
        return dateline;
    }

    public void setDateline(String dateline) {
        this.dateline = dateline;
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
}
