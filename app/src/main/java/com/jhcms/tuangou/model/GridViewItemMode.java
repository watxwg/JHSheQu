package com.jhcms.tuangou.model;

/**
 * Created by admin on 2017/4/15.
 */
public class GridViewItemMode {
    private int resid;
    private  String msg;

    public GridViewItemMode() {
        super();
    }

    public int getResid() {
        return resid;
    }

    public void setResid(int resid) {
        this.resid = resid;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "GridViewItemMode{" +
                "resid=" + resid +
                ", msg='" + msg + '\'' +
                '}';
    }

    public GridViewItemMode(String msg, int resid) {
        this.msg = msg;
        this.resid = resid;
    }
}
