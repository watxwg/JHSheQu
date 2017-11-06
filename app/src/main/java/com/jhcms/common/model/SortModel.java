package com.jhcms.common.model;

import java.util.List;

/**
 * Created by admin on 2017/6/8.
 * 分类Model
 */
public class SortModel {

    /**
     * cate_id : 5
     * parent_id : 1
     * title : 快餐便当
     * icon :
     * photo :
     * orderby : 50
     * dateline : 1494314835
     * childrens : []
     */

    public String cate_id;
    public String parent_id;
    public String title;
    public String icon;
    public String photo;
    public String orderby;
    public String dateline;
    public List<SortModel> childrens;


}
