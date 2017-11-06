package com.jhcms.common.utils;

/**
 * 作者：WangWei
 * 日期：2017/8/22 14:47
 * 描述：转换json的接口
 */

public interface JsonConvert<T> {
    void convert(String json,String url);
}
