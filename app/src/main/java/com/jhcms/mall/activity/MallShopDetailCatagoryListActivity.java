package com.jhcms.mall.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jhcms.common.utils.Api;
import com.jhcms.common.utils.HttpUtils;
import com.jhcms.common.utils.OnRequestSuccess;
import com.jhcms.mall.adapter.CommonAdapter;
import com.jhcms.mall.adapter.viewholder.CommonViewHolder;
import com.jhcms.mall.model.BaseResponse;
import com.jhcms.mall.model.CategoryModel;
import com.jhcms.mall.model.ItemsModel;
import com.jhcms.mall.model.ShopCateModel;
import com.jhcms.waimaiV3.R;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 作者：WangWei
 * 描述：店铺详情——分类列表
 */
public class MallShopDetailCatagoryListActivity extends BaseActivity {
    private static final String SHOP_ID = "shopId";
    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.rv_list)
    RecyclerView rvList;
    @Bind(R.id.activity_shop_detail_catagory_list)
    LinearLayout activityShopDetailCatagoryList;
    private List<ShopCateModel> categoryData;
    private int mShopId;
    private CategoryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public static Intent generateIntent(Context context, int shopId) {
        Intent intent = new Intent(context, MallShopDetailCatagoryListActivity.class);
        intent.putExtra(SHOP_ID, shopId);
        return intent;
    }

    @Override
    protected void initData() {
        mShopId = getIntent().getIntExtra(SHOP_ID, -1);
        categoryData=new ArrayList<>();
        adapter = new CategoryAdapter(this, categoryData, R.layout.mall_list_item_catagory);
        rvList.setAdapter(adapter);
        rvList.setLayoutManager(new LinearLayoutManager(this));
        tvTitle.setText(R.string.mall_店铺商品分类);
        requestData();
    }

    private void requestData() {
        String paramData = null;
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("shop_id", mShopId);
            paramData = jsonObject.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        HttpUtils.postUrlWithBaseResponse(this, Api.MALL_SHOP_CATE, paramData, true, new OnRequestSuccess<BaseResponse<ItemsModel<ShopCateModel>>>() {
            @Override
            public void onSuccess(String url, BaseResponse<ItemsModel<ShopCateModel>> data) {
                super.onSuccess(url, data);
                List<ShopCateModel> items = data.getData().getItems();
                ShopCateModel shopCateModel = new ShopCateModel();
                shopCateModel.setCate_id("0");
                shopCateModel.setTitle(getString(R.string.mall_全部));
                items.add(0,shopCateModel);
                categoryData.clear();
                categoryData.addAll(items);
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_shop_detail_catagory_list);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        onBackPressed();
    }

    public class CategoryAdapter extends CommonAdapter<ShopCateModel> {

        public CategoryAdapter(Context context, List<ShopCateModel> data, @LayoutRes int layoutId) {
            super(context, data, layoutId);
        }

        @Override
        public void convertViewData(CommonViewHolder holder, ShopCateModel bean) {
            if (holder.getAdapterPosition() == 0) {
                RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) holder.itemView.getLayoutParams();
                int size = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, this.mContext.getResources().getDisplayMetrics());
                layoutParams.setMargins(0, size, 0, size);
                holder.itemView.requestLayout();
            }else{
                RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) holder.itemView.getLayoutParams();
                int size = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, this.mContext.getResources().getDisplayMetrics());
                layoutParams.setMargins(0, 0, 0, size);
                holder.itemView.requestLayout();
            }
            holder.<TextView>getView(R.id.tv_name).setText(bean.getTitle());
            holder.itemView.setOnClickListener(v -> {
                try {

                    Intent intent = MallShopDetailCatagoryActivity.generateIntent(this.mContext, mShopId, null, Integer.parseInt(bean.getCate_id()), bean.getTitle());
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
    }


}
