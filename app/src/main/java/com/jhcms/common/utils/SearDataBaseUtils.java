package com.jhcms.common.utils;

import com.jhcms.waimaiV3.litepal.SearhContent;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by admin
 * on 2017/8/28.
 * TODO
 */

public class SearDataBaseUtils {
    public static void insert(String str) {
        List<String> data = search();
        for (String ss : data) {
            if (ss.equals(str)) {
                return;
            }
        }
        SearhContent content = new SearhContent();
        content.setContent(str);
        content.save();

    }

    public static void deleteAll() {
        DataSupport.deleteAll(SearhContent.class);
    }

    public static List<String> search() {
        List<SearhContent> allContent = DataSupport.findAll(SearhContent.class);
        List<String> data = new ArrayList<>();
        for (SearhContent content : allContent) {
            data.add(content.getContent());
        }
        return data;
    }
}
