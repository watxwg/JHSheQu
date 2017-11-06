package com.jhcms.run.mode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 作者：WangWei
 * 日期：2017/10/10 10:43
 * 描述：首页数据
 */

public class HomeDataModel {
    private List<ImageInfoModel> banner;
    private List<CateInfoModel> mai;
    private List<CateInfoModel> song;
    private List<String> weight;
    private ArrayList<String> price;
    private ArrayList<String> xiaofei;

    public List<ImageInfoModel> getBanner() {
        return banner;
    }

    public void setBanner(List<ImageInfoModel> banner) {
        this.banner = banner;
    }

    public List<CateInfoModel> getMai() {
        return mai;
    }

    public void setMai(List<CateInfoModel> mai) {
        this.mai = mai;
    }

    public List<CateInfoModel> getSong() {
        return song;
    }

    public void setSong(List<CateInfoModel> song) {
        this.song = song;
    }

    public List<String> getWeight() {
        return weight;
    }

    public void setWeight(List<String> weight) {
        this.weight = weight;
    }

    public ArrayList<String> getPrice() {
        return price;
    }

    public void setPrice(ArrayList<String> price) {
        this.price = price;
    }

    public ArrayList<String> getXiaofei() {
        return xiaofei;
    }

    public void setXiaofei(ArrayList<String> xiaofei) {
        this.xiaofei = xiaofei;
    }
}
