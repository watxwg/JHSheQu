package com.jhcms.mall.model;

/**
 * Created by wangyujie
 * on 2017/9/15.17:42
 * TODO
 */

/**
 * 商品信息
 */
public class GoodsInfo {
    private String goodId;//商品id
    private String name;//商品名字
    private boolean isChoosed;
    private String desc;//规格
    private String descId;//规格id
    private double price;//价钱
    private int count;//数量
    private String goodsImg;//商品图片


    public void setGoodId(String goodId) {
        this.goodId = goodId;
    }

    public String getDescId() {
        return descId;
    }

    public void setDescId(String descId) {
        this.descId = descId;
    }

    public String getGoodsImg() {
        return goodsImg;
    }

    public void setGoodsImg(String goodsImg) {
        this.goodsImg = goodsImg;
    }

    public GoodsInfo() {

    }


    public String getGoodId() {
        return goodId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isChoosed() {
        return isChoosed;
    }

    public void setChoosed(boolean isChoosed) {
        this.isChoosed = isChoosed;
    }


    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

}
