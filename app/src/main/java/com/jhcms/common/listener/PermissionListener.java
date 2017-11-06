package com.jhcms.common.listener;

import java.util.List;

/**
 * Created by Administrator on 2017/3/5.
 */

public interface PermissionListener {
    void onGranted();


    void onDenied(List<String> permissions);
}
