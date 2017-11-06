package com.jhcms.mall.model;

import java.util.List;

/**
 * 作者：WangWei
 * 日期：2017/9/14 18:05
 * 描述：规格信息
 */

public class SpecificationInfoModel {


    /**
     * attr_group_id : 7
     * product_id : 1
     * title : 大小
     * orderby : 50
     */

    private String attr_group_id;
    private String product_id;
    private String title;
    private String orderby;
    private List<SpecificationValues> values;

    public List<SpecificationValues> getValues() {
        return values;
    }

    public void setValues(List<SpecificationValues> values) {
        this.values = values;
    }

    public String getAttr_group_id() {
        return attr_group_id;
    }

    public void setAttr_group_id(String attr_group_id) {
        this.attr_group_id = attr_group_id;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOrderby() {
        return orderby;
    }

    public void setOrderby(String orderby) {
        this.orderby = orderby;
    }

    /**
     * 子规格
     */
    public static class SpecificationValues{


        /**
         * attr_value_id : 14
         * attr_group_id : 7
         * title : 小份
         * orderby : 50
         */

        private String attr_value_id;
        private String attr_group_id;
        private String title;
        private String orderby;

        public String getAttr_value_id() {
            return attr_value_id;
        }

        public void setAttr_value_id(String attr_value_id) {
            this.attr_value_id = attr_value_id;
        }

        public String getAttr_group_id() {
            return attr_group_id;
        }

        public void setAttr_group_id(String attr_group_id) {
            this.attr_group_id = attr_group_id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getOrderby() {
            return orderby;
        }

        public void setOrderby(String orderby) {
            this.orderby = orderby;
        }
    }
}
