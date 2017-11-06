package com.jhcms.shequ.weight;

import android.view.View;
import android.widget.TextView;

import com.jhcms.common.utils.ActivityCollector;
import com.jhcms.common.utils.Utils;
import com.jhcms.waimaiV3.R;


/**
 * Created by wangyujie
 * Date 2017/6/1.
 * TODO
 */

class CategoryViewHolder extends BetterViewHolder {
    private TextView titleText;
    private TextView tvMore;
    public CategoryViewHolder(View itemView) {
        super(itemView);
        titleText = (TextView) itemView.findViewById(R.id.tv_home_title);
        tvMore = (TextView) itemView.findViewById(R.id.tv_more);
    }

    @Override
    public void bindItem(Visitable visitable) {
        final Category category = (Category) visitable;
        titleText.setText(category.title);
        tvMore.setOnClickListener(v -> {
            if (!Utils.isFastDoubleClick()) {
                Utils.dealWithHomeLink(ActivityCollector.getTopActivity(), category.more_url, null);
            }
        });
    }
}
