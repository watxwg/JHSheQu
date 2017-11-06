package com.jhcms.common.model;

import java.io.Serializable;

/**
 * Created by admin on 2017/6/15.
 */
public class YouHuiModel implements Serializable{

    /**
     * title : 满减活动减
     * word : 减
     * color : FF4D5B
     * amount : 2.00
     */

    private String title;
    private String word;
    private String color;
    private float amount;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }
}
