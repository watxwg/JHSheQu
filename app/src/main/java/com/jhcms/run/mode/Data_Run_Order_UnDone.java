package com.jhcms.run.mode;

import com.google.gson.Gson;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wangyujie
 * on 2017/10/9.11:47
 * TODO
 */

public class Data_Run_Order_UnDone implements Serializable {


    /**
     * items : [{"order_id":"1723","msg":"送货中","btn":{"pay":"0","cancel":"0","cui":"1","confirm":"0","comment":"0","again":"0"},"no_pay_canel_time":"1506568997","from":"mai","product":["来包烟","金皖"],"s_addr":"望江西路与合作化南路交汇处F1层L1-07号110","m_addr":"就近购买","pei_amount":"3.00"},{"order_id":"1647","msg":"送货中","btn":{"pay":"0","cancel":"0","cui":"1","confirm":"0","comment":"0","again":"0"},"no_pay_canel_time":"1506502405","from":"mai","product":["来包烟"],"s_addr":"望江西路与合作化南路交汇处F1层L1-07号110","m_addr":"就近购买","pei_amount":"3.00"},{"order_id":"1646","msg":"送货中","btn":{"pay":"0","cancel":"0","cui":"1","confirm":"0","comment":"0","again":"0"},"no_pay_canel_time":"1506501771","from":"mai","product":["来包烟"],"s_addr":"望江西路与合作化南路交汇处F1层L1-07号110","m_addr":"就近购买","pei_amount":"3.00"}]
     * count : 3
     */

    private String count;
    private List<ItemsBean> items;

    public static Data_Run_Order_UnDone objectFromData(String str) {

        return new Gson().fromJson(str, Data_Run_Order_UnDone.class);
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public List<ItemsBean> getItems() {
        return items;
    }

    public void setItems(List<ItemsBean> items) {
        this.items = items;
    }

    public static class ItemsBean {
        /**
         * order_id : 1723
         * msg : 送货中
         * btn : {"pay":"0","cancel":"0","cui":"1","confirm":"0","comment":"0","again":"0"}
         * no_pay_canel_time : 1506568997
         * from : mai
         * product : ["来包烟","金皖"]
         * s_addr : 望江西路与合作化南路交汇处F1层L1-07号110
         * m_addr : 就近购买
         * pei_amount : 3.00
         */

        private String order_id;
        private String msg;
        private BtnBean btn;
        private String no_pay_canel_time;
        private String from;
        private String s_addr;
        private String m_addr;
        private String pei_amount;
        private List<String> product;

        public static ItemsBean objectFromData(String str) {

            return new Gson().fromJson(str, ItemsBean.class);
        }

        public String getOrder_id() {
            return order_id;
        }

        public void setOrder_id(String order_id) {
            this.order_id = order_id;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public BtnBean getBtn() {
            return btn;
        }

        public void setBtn(BtnBean btn) {
            this.btn = btn;
        }

        public String getNo_pay_canel_time() {
            return no_pay_canel_time;
        }

        public void setNo_pay_canel_time(String no_pay_canel_time) {
            this.no_pay_canel_time = no_pay_canel_time;
        }

        public String getFrom() {
            return from;
        }

        public void setFrom(String from) {
            this.from = from;
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

        public List<String> getProduct() {
            return product;
        }

        public void setProduct(List<String> product) {
            this.product = product;
        }

        public static class BtnBean {
            /**
             * pay : 0
             * cancel : 0
             * cui : 1
             * confirm : 0
             * comment : 0
             * again : 0
             */

            private String pay;
            private String cancel;
            private String cui;
            private String confirm;
            private String comment;
            private String again;

            public static BtnBean objectFromData(String str) {

                return new Gson().fromJson(str, BtnBean.class);
            }

            public String getPay() {
                return pay;
            }

            public void setPay(String pay) {
                this.pay = pay;
            }

            public String getCancel() {
                return cancel;
            }

            public void setCancel(String cancel) {
                this.cancel = cancel;
            }

            public String getCui() {
                return cui;
            }

            public void setCui(String cui) {
                this.cui = cui;
            }

            public String getConfirm() {
                return confirm;
            }

            public void setConfirm(String confirm) {
                this.confirm = confirm;
            }

            public String getComment() {
                return comment;
            }

            public void setComment(String comment) {
                this.comment = comment;
            }

            public String getAgain() {
                return again;
            }

            public void setAgain(String again) {
                this.again = again;
            }
        }
    }
}
