package com.jhcms.run.mode;

import java.util.List;

/**
 * 作者：WangWei
 * 日期：2017/10/11 14:53
 * 描述：订单金额信息
 */

public class OrderPriceInfoModel {
    private String pei_amount;
    private List<HongbaoInfoModel> hongbao;

    public String getPei_amount() {
        return pei_amount;
    }

    public void setPei_amount(String pei_amount) {
        this.pei_amount = pei_amount;
    }

    public List<HongbaoInfoModel> getHongbao() {
        return hongbao;
    }

    public void setHongbao(List<HongbaoInfoModel> hongbao) {
        this.hongbao = hongbao;
    }
}
