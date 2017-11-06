package com.jhcms.waimaiV3.litepal;

import org.litepal.crud.DataSupport;

/**
 * Created by wangyujie
 * Date 2017/7/8.
 * TODO
 */

public class SearhContent extends DataSupport {
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String content;



}
