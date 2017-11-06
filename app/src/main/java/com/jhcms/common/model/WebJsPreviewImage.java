package com.jhcms.common.model;

import java.util.List;

/**
 * Created by admin on 2017/7/19.
 */
public class WebJsPreviewImage {


    /**
     * index : 0
     * items : [{"title":"图片标题","img":"图片连接"},{"title":"图片标题","img":"图片连接"},{"title":"图片标题","img":"图片连接"},{"title":"图片标题","img":"图片连接"}]
     */

    private int index;
    private List<ItemsBean> items;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public List<ItemsBean> getItems() {
        return items;
    }

    public void setItems(List<ItemsBean> items) {
        this.items = items;
    }

    public static class ItemsBean {
        /**
         * title : 图片标题
         * img : 图片连接
         */

        private String title;
        private String img;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }
    }
}
