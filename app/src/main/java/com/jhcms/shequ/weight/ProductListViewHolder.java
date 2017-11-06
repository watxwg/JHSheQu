package com.jhcms.shequ.weight;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.jhcms.common.utils.ActivityCollector;
import com.jhcms.common.utils.Utils;
import com.jhcms.waimaiV3.R;

import java.util.List;

/**
 * Created by wangyujie
 * Date 2017/6/1.
 * TODO
 */
public class ProductListViewHolder extends BetterViewHolder {

    private RecyclerView recyclerView;
    private ProductsAdapter adapter;

    public ProductListViewHolder(View itemView) {
        super(itemView);
        recyclerView = (RecyclerView) itemView.findViewById(R.id.recycler_view);
        GridLayoutManager layoutManager = new GridLayoutManager(itemView.getContext(), 2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        adapter = new ProductsAdapter();
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void bindItem(Visitable visitable) {

        ProductList productList = (ProductList) visitable;
        final List<Product> products = productList.products;
        adapter.setData(products);
        adapter.notifyDataSetChanged();
        adapter.setOnMoreListener(position -> {
            if (!Utils.isFastDoubleClick()) {
                Utils.dealWithHomeLink(ActivityCollector.getTopActivity(), products.get(position).link, null);
            }
        });
    }

}
