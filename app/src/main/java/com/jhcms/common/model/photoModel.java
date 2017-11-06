package com.jhcms.common.model;

import java.io.Serializable;

public class  photoModel implements Serializable {

        /**
         * photo_id : 138
         * comment_id : 85
         * photo : photo/201707/20170706_AED59DBDD51CE14E92CA76B7B174CD44.png
         * dateline : 1499323715
         */

        private String photo_id;
        private String comment_id;
        private String photo;
        private String dateline;

        public String getPhoto_id() {
            return photo_id;
        }

        public void setPhoto_id(String photo_id) {
            this.photo_id = photo_id;
        }

        public String getComment_id() {
            return comment_id;
        }

        public void setComment_id(String comment_id) {
            this.comment_id = comment_id;
        }

        public String getPhoto() {
            return photo;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }

        public String getDateline() {
            return dateline;
        }

        public void setDateline(String dateline) {
            this.dateline = dateline;
        }
    }