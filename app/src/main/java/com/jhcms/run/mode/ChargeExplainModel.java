package com.jhcms.run.mode;

import java.util.List;

/**
 * 作者：WangWei
 * 日期：2017/10/12 14:48
 * 描述：费用说明
 */

public class ChargeExplainModel {
    private List<String> distance;
    private List<String> time;

    public List<String> getDistance() {
        return distance;
    }

    public void setDistance(List<String> distance) {
        this.distance = distance;
    }

    public List<String> getTime() {
        return time;
    }

    public void setTime(List<String> time) {
        this.time = time;
    }
}
