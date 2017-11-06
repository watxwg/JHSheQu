package com.jhcms.mall.model;

import com.jhcms.mall.model.interfaces.CategoryInterface;

import java.io.Serializable;
import java.util.List;

/**
 * 作者：WangWei
 * 日期：2017/9/9 09:58
 * 描述：商品商家列表 分类popuwindow数据
 */

public class CategoryModel implements CategoryInterface,Serializable{

    /**
     * cate_id : 1
     * parent_id : 0
     * title : 食品
     * icon : photo/201705/20170509_229E8452E772A40415FBD11BAB011759.png
     * photo : photo/201705/20170509_6BCF08424EB2E1DD5D3271D076FFE941.png
     * orderby : 1
     * dateline : 1494313819
     * childrens : [{"cate_id":"1","title":"全部"},{"cate_id":"8","parent_id":"1","title":"盖浇饭","icon":"","photo":"","orderby":"50","dateline":"1494315249"}]
     */

    private String cate_id;
    private String parent_id;
    private String title;
    private String icon;
    private String photo;
    private String orderby;
    private String dateline;
    private List<CategoryModel> childrens;

    public String getCate_id() {
        return cate_id;
    }

    public void setCate_id(String cate_id) {
        this.cate_id = cate_id;
    }

    public String getParent_id() {
        return parent_id;
    }

    public void setParent_id(String parent_id) {
        this.parent_id = parent_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getOrderby() {
        return orderby;
    }

    public void setOrderby(String orderby) {
        this.orderby = orderby;
    }

    public String getDateline() {
        return dateline;
    }

    public void setDateline(String dateline) {
        this.dateline = dateline;
    }

    public List<CategoryModel> getChildrens() {
        return childrens;
    }

    public void setChildrens(List<CategoryModel> childrens) {
        this.childrens = childrens;
    }

    @Override
    public String toString() {
        return "CategoryModel{" +
                "cate_id='" + cate_id + '\'' +
                ", parent_id='" + parent_id + '\'' +
                ", title='" + title + '\'' +
                ", icon='" + icon + '\'' +
                ", photo='" + photo + '\'' +
                ", orderby='" + orderby + '\'' +
                ", dateline='" + dateline + '\'' +
                ", childrens=" + childrens +
                '}';
    }

    /*-----------------接口方法----------------------*/
    @Override
    public String getTitleImp() {
        return getTitle();
    }

    @Override
    public String getCateIdImp() {
        return getCate_id();
    }

    @Override
    public String getParentIdImp() {
        return getParent_id();
    }

    @Override
    public List<? extends CategoryInterface> getChildrensImp() {
        return getChildrens();
    }

    @Override
    public List<CategoryModel> getChildensImp2() {
        return getChildrens();
    }

    @Override
    public boolean hasSubDataImp() {
        return (getChildrens()!=null&&getChildrens().size()>0);
    }

    @Override
    public String toStringImp() {
        return toString();
    }
}
