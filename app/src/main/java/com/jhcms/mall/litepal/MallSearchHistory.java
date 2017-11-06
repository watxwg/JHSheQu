package com.jhcms.mall.litepal;

import org.litepal.crud.DataSupport;

/**
 * 作者：WangWei
 * 日期：2017/8/23 15:43
 * 描述：数据库表模型
 */

public class MallSearchHistory extends DataSupport{

    private String searchContent;

    public String getSearchContent() {
        return searchContent;
    }

    public void setSearchContent(String searchContent) {
        this.searchContent = searchContent;
    }
}
