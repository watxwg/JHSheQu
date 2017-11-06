package com.jhcms.run.mode;

import com.google.gson.Gson;

import java.io.Serializable;

/**
 * Created by wangyujie
 * on 2017/10/9.11:47
 * TODO
 */

public class Data_Run_Mine implements Serializable {


    /**
     * phone : 0551-64278115
     * member : {"nickname":"戎马一生","money":"5123.50","face":"http://img01.jhcms.com/wmdemo/photo/201709/20170929_57BCA5E764CA431EDD341590A0FAE2C3.png"}
     * is_read : 0
     */

    private String phone;
    private MemberBean member;
    private int is_read;

    public static Data_Run_Mine objectFromData(String str) {

        return new Gson().fromJson(str, Data_Run_Mine.class);
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public MemberBean getMember() {
        return member;
    }

    public void setMember(MemberBean member) {
        this.member = member;
    }

    public int getIs_read() {
        return is_read;
    }

    public void setIs_read(int is_read) {
        this.is_read = is_read;
    }

    public static class MemberBean {
        /**
         * nickname : 戎马一生
         * money : 5123.50
         * face : http://img01.jhcms.com/wmdemo/photo/201709/20170929_57BCA5E764CA431EDD341590A0FAE2C3.png
         */

        private String nickname;
        private String money;
        private String face;

        public static MemberBean objectFromData(String str) {

            return new Gson().fromJson(str, MemberBean.class);
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public String getFace() {
            return face;
        }

        public void setFace(String face) {
            this.face = face;
        }
    }
}
