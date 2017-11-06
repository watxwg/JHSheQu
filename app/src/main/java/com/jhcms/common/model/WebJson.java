package com.jhcms.common.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2017/7/17.
 * js model
 */
public class WebJson {

    /**
     * type : more
     * params : {"items":[{"type":"search","params":{"title":"搜索","link":"http://www.jhcms.com"}},{"type":"phone","params":{"phone":[{"title":"商户","phone":"13888888888"},{"title":"客服","phone":"0551-64278115"}]}},{"type":"share","params":{"title":"分享标题","desc":"分享描述","img":"http://www.jhcms.com/themes/default/product/waimai/statics/images/logo.jpg","link":"http://www.jhcms.com"}},{"type":"text","params":{"title":"官网","link":"http://www.jhcms.com"}},{"type":"text","params":{"title":"外卖","link":"http://waimai.o2o.jhcms.cn"}}]}
     */

    private String type;
    private ParamsBeanX params;
    private String title;
    private  String desc;
    private  String img;
    private  String link;
    private  text text;

    public WebJson.text getText() {
        return text;
    }

    public void setText(WebJson.text text) {
        this.text = text;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ParamsBeanX getParams() {
        return params;
    }

    public void setParams(ParamsBeanX params) {
        this.params = params;
    }

    public static class ParamsBeanX {
        private ParamsBeanX params;

        public ParamsBeanX getParams() {
            return params;
        }

        public void setParams(ParamsBeanX params) {
            this.params = params;
        }

        private List<ParamsBeanX> items;
        private ArrayList<phonemodel>phone;
        private  String title;
        private  String link;
        private  String desc;
        private  String img;
        private String type;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
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

        public List<ParamsBeanX> getItems() {
            return items;
        }

        public void setItems(List<ParamsBeanX> items) {
            this.items = items;
        }


        public ArrayList<phonemodel> getPhone() {
            return phone;
        }

        public void setPhone(ArrayList<phonemodel> phone) {
            this.phone = phone;
        }

    }

  public   class text{

        /**
         * title : 江湖CMS
         * link : http://www.jhcms.com
         */

        private String title;
        private String link;

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
    }
}
