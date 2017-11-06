package com.jhcms.waimaiV3.model;

import com.jhcms.common.model.ShopDetail;

/**
 * Created by wangyujie
 * Date 2017/6/9.
 * TODO
 */

public class Type {
    public final ShopDetail.ItemsEntity itemsEntity;
    public final int typeId;

    public Type(ShopDetail.ItemsEntity itemsEntity, int typeId) {
        this.itemsEntity = itemsEntity;
        this.typeId = typeId;
    }
}
