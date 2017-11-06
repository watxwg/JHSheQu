package com.jhcms.mall.model;

import java.util.List;

/**
 * 作者：WangWei
 * 日期：2017/8/22 16:50
 * 描述：热搜数据
 */

public class HotSearchModel {


    private List<String> hots;

    public List<String> getHots() {
        return hots;
    }

    public void setHots(List<String> hots) {
        this.hots = hots;
    }

    @Override
    public String toString() {
        return "HotSearchModel{" +
                "hots=" + hots +
                '}';
    }
}
