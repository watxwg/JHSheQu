package com.jhcms.common.unmengshareutils;

/**
 * Created by admin on 2017/5/9.
 */
public class GridviewModel {
    private  Integer ImageRes;
    private  String ShareName;

    public GridviewModel() {
    }

    public GridviewModel(Integer imageRes, String shareName) {
        ImageRes = imageRes;
        ShareName = shareName;
    }

    public Integer getImageRes() {
        return ImageRes;
    }

    public void setImageRes(Integer imageRes) {
        ImageRes = imageRes;
    }

    public String getShareName() {
        return ShareName;
    }

    public void setShareName(String shareName) {
        ShareName = shareName;
    }

    @Override
    public String toString() {
        return "GridviewModel{" +
                "ImageRes=" + ImageRes +
                ", ShareName='" + ShareName + '\'' +
                '}';
    }
}
