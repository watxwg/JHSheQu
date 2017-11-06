package com.jhcms.run.mode;

import com.jhcms.run.adapter.LeftTimeAdapter;

/**
 * 作者：WangWei
 * 日期：2017/10/10 17:01
 * 描述：
 */

public class DayConfigInfoModel implements LeftTimeAdapter.ContentProvider{

    /**
     * date : 2017-08-16
     * day : 今天(周三)
     */

    private String date;
    private String day;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    @Override
    public String getContent() {
        return day;
    }
}
