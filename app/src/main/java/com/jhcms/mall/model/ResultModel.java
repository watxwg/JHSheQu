package com.jhcms.mall.model;

/**
 * 作者：WangWei
 * 日期：2017/9/19 11:36
 * 描述：
 */

public class ResultModel<T> {
    private T result;

    public T getResult() {
        return result;
    }

    public void setResult(T t) {
        this.result = t;
    }

    @Override
    public String toString() {
        return "ResultModel{" +
                "result=" + result.toString() +
                '}';
    }
}
