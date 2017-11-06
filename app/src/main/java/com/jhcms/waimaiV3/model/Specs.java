package com.jhcms.waimaiV3.model;

/**
 * Created by wangyujie
 * Date 2017/6/20.
 * TODO 规格商品
 */

public class Specs {
    public int specNum;
    public String specName;
    public String specPrice;

    public Specs(String specName, String specPrice) {
        this.specName = specName;
        this.specPrice = specPrice;
    }

    public void setSpecNum(int specNum) {
        this.specNum = specNum;
    }
}
