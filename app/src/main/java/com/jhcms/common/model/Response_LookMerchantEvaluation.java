package com.jhcms.common.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by admin on 2017/7/6.
 */
public class Response_LookMerchantEvaluation extends SharedResponse implements Serializable {

  public     LookMerchantEvaluationData data;

    public LookMerchantEvaluationData getData() {
        return data;
    }

    public void setData(LookMerchantEvaluationData data) {
        this.data = data;
    }
}
