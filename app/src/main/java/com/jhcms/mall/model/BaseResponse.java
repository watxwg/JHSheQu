package com.jhcms.mall.model;

/**
 * 作者：WangWei
 * 日期：2017/8/21 10:34
 * 描述：基础响应类实体
 */

public class BaseResponse<T>{
    public int error;
    public String message;
    public T data;

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
