package com.jhcms.mall.model;

import android.content.Context;

import com.jhcms.common.utils.Utils;

/**
 * Created by admin
 * on 2017/8/23.
 * TODO
 */

public class OrderStatus {
    public int title;
    public String pics;
    public int count;

    public OrderStatus(int title, String pics, int count) {
        this.title = title;
        this.pics = pics;
        this.count = count;
    }
    public int getImageResourceId(Context context) {
        try {
            return Utils.getMipmapId(context, pics);

        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

}
