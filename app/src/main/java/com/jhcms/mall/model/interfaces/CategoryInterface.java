package com.jhcms.mall.model.interfaces;

import com.jhcms.mall.model.CategoryModel;

import java.util.List;

/**
 * 作者：WangWei
 * 日期：2017/8/24 11:44
 * 描述：分类列表数据接口
 */

public interface CategoryInterface{
    /**
     * 分类标题
     * @return
     */
    String getTitleImp();

    /**
     * 分类Id
     * @return
     */
    String getCateIdImp();

    /**
     * 父分类Id
     * @return
     */
    String getParentIdImp();

    /**
     * 子分类
     * @return
     */
    List<? extends CategoryInterface> getChildrensImp();
    List<CategoryModel> getChildensImp2();
    /**
     * 是否有子分类
     * @return
     */
    boolean hasSubDataImp();

    String toStringImp();
}
