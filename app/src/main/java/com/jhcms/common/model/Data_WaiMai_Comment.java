
package com.jhcms.common.model;

import java.io.Serializable;
import java.util.List;


public class Data_WaiMai_Comment implements Serializable {
    public List<ShopComment> items;
    public ShopCommentDetail detail;
    public List<ShopCommentSwitch> switchnav;
}
