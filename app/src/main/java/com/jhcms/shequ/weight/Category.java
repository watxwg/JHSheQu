package com.jhcms.shequ.weight;

/**
 * Created by wangyujie
 * Date 2017/6/1.
 * TODO
 */

public class Category implements Visitable{
    public String title;
    public String more_url;

    public Category(String title,String more_url) {
        this.title = title;
        this.more_url = more_url;
    }

    @Override
    public int type(TypeFactory typeFactory) {
        return typeFactory.type(this);
    }
}
