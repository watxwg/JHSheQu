package com.jhcms.mall.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jhcms.common.utils.Api;
import com.jhcms.common.utils.HttpUtils;
import com.jhcms.common.utils.OnRequestSuccess;
import com.jhcms.common.utils.Utils;
import com.jhcms.mall.adapter.ProductDoubleAdapter;
import com.jhcms.mall.adapter.ProductSingleAdapter;
import com.jhcms.mall.adapter.ShopListAdapter;
import com.jhcms.mall.decoration.ProductDoubleListDecoration;
import com.jhcms.mall.model.BaseResponse;
import com.jhcms.mall.model.ItemsModel;
import com.jhcms.mall.model.ProductItemModel;
import com.jhcms.mall.model.ShopItemModel;
import com.jhcms.waimaiV3.R;
import com.jhcms.waimaiV3.litepal.Shop;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MallTopProductShopListActivity extends BaseActivity {
    public static final int SHOP_CONTENT_TYPE = 10;
    public static final int PRODUCT_CONTENT_TYPE = 20;
    private static final String TITLE = "title";
    private static final String CONTENT_TYPE = "contentType";
    private static final String IS_SINGLE_COLUM = "isSingleColum";
    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.rv_list)
    RecyclerView rvList;
    @Bind(R.id.spring)
    SpringView spring;
    @Bind(R.id.iv_btn_style)
    ImageView ivBtnStyle;
    @Bind(R.id.activity_top_product_shop_list)
    LinearLayout activityTopProductShopList;
    private int contentType;
    private int currentPageIndex;
    private List<ShopItemModel> shopData;
    private List<ProductItemModel> productData;
    private ProductSingleAdapter productSingleAdapter;
    private ProductDoubleAdapter productDoubleAdapter;
    private ShopListAdapter shopListAdapter;
    private ProductDoubleListDecoration itemDecoration;
    private boolean isSinleColum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * 创建Intent
     *
     * @param context
     * @param contentType   显示类容，商品或商家，只接收这两个常量：SHOP_CONTENT_TYPE和PRODUCT_CONTENT_TYPE
     * @param isSingleColum 显示列数 true：单列 false：双列，当显示类容是商品时此参数有效
     * @param title         标题
     * @return
     */
    public static Intent generateIntent(Context context, int contentType, boolean isSingleColum, String title) {
        Intent intent = new Intent(context, MallTopProductShopListActivity.class);
        intent.putExtra(TITLE, title);
        intent.putExtra(CONTENT_TYPE, contentType);
        intent.putExtra(IS_SINGLE_COLUM, isSingleColum);
        return intent;
    }

    @Override
    protected void initData() {
        currentPageIndex = 1;
        Intent intent = getIntent();
        String title = intent.getStringExtra(TITLE);
        tvTitle.setText(title);
        isSinleColum = intent.getBooleanExtra(IS_SINGLE_COLUM, true);
        contentType = intent.getIntExtra(CONTENT_TYPE, 0);
        if (contentType == PRODUCT_CONTENT_TYPE) {
            ivBtnStyle.setVisibility(View.VISIBLE);
            productData = new ArrayList<>();
            shopData = new ArrayList<>();
            productSingleAdapter = new ProductSingleAdapter(this, productData);
            productDoubleAdapter = new ProductDoubleAdapter(this, productData);
            productSingleAdapter.setShowRankIndex(true);
            productSingleAdapter.setOnItemClickListener(this::goProductDetail);
            productDoubleAdapter.setShowRankIndex(true);
            productDoubleAdapter.setOnItemClickListener(this::goProductDetail);

            switchShowType(isSinleColum);
            requestProductData(currentPageIndex, true);
        } else if (contentType == SHOP_CONTENT_TYPE) {
            ivBtnStyle.setVisibility(View.GONE);
            shopData = new ArrayList<>();
            shopListAdapter = new ShopListAdapter(this, shopData);
            shopListAdapter.setOnItemClickListener(new ShopListAdapter.OnItemClickListener() {
                @Override
                public void comeInShop(String shopId) {
                    try {

                        Intent intent1 = MallShopDetailsActivity.generateIntent(MallTopProductShopListActivity.this, Integer.parseInt(shopId));
                        startActivity(intent1);
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(MallTopProductShopListActivity.this, getString(R.string.mall_程序出错), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void OnProductClick(String productId) {
                    try {

                        Intent intent1 =MallProductDetailActivity.generateIntent(MallTopProductShopListActivity.this, Integer.parseInt(productId));
                        startActivity(intent1);
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(MallTopProductShopListActivity.this, getString(R.string.mall_程序出错), Toast.LENGTH_SHORT).show();
                    }
                }
            });
            shopListAdapter.setProductProvider(shopItemModel -> shopItemModel.getRe_products());
            shopListAdapter.setShowRankIndex(true);
            rvList.setLayoutManager(new LinearLayoutManager(this));
            rvList.setAdapter(shopListAdapter);
            requestShopData(currentPageIndex, true);
        } else {
            throw new IllegalArgumentException("传入的参数有误");
        }
    }

    /**
     * 初始化刷新加载控件
     */
    private void initRefereshView() {
        spring.setGive(SpringView.Give.BOTH);
        spring.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                currentPageIndex = 1;
                if (contentType == PRODUCT_CONTENT_TYPE) {
                    requestProductData(currentPageIndex, true);
                } else if (contentType == SHOP_CONTENT_TYPE) {
                    requestShopData(currentPageIndex, true);
                }
            }

            @Override
            public void onLoadmore() {
                currentPageIndex++;
                if (contentType == PRODUCT_CONTENT_TYPE) {
                    requestProductData(currentPageIndex, true);
                } else if (contentType == SHOP_CONTENT_TYPE) {
                    requestShopData(currentPageIndex, true);
                }
            }
        });
        spring.setHeader(new DefaultHeader(MallTopProductShopListActivity.this));
        spring.setFooter(new DefaultFooter(MallTopProductShopListActivity.this));
        spring.setType(SpringView.Type.FOLLOW);
    }

    /**
     * 切换显示的列数
     *
     * @param isSinleColum
     */
    private void switchShowType(boolean isSinleColum) {
        if (isSinleColum) {
            ivBtnStyle.setImageResource(R.mipmap.mall_btn_two_fr);
            rvList.setLayoutManager(new LinearLayoutManager(this));
            rvList.removeItemDecoration(itemDecoration);
            rvList.setAdapter(productSingleAdapter);
        } else {
            ivBtnStyle.setImageResource(R.mipmap.mall_btn_one_fr);
            rvList.setLayoutManager(new GridLayoutManager(this, 2));
            rvList.setAdapter(productDoubleAdapter);
            itemDecoration = new ProductDoubleListDecoration(Utils.dip2px(this, 5), 2);
            rvList.addItemDecoration(itemDecoration);
        }
    }

    /**
     * 店铺数据
     *
     * @param page
     * @param isShowProgress 是否显示加载对话框
     */
    private void requestShopData(int page, boolean isShowProgress) {
        JSONObject jsonObject = new JSONObject();
        String paramsData = null;
        try {
            jsonObject.put("page", page);
            paramsData = jsonObject.toString();
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this, getString(R.string.mall_程序出错), Toast.LENGTH_SHORT).show();
            return;
        }
        HttpUtils.postUrlWithBaseResponse(this, Api.MALL_SHOP_RANK, paramsData, isShowProgress, new OnRequestSuccess<BaseResponse<ItemsModel<ShopItemModel>>>() {
            @Override
            public void onSuccess(String url, BaseResponse<ItemsModel<ShopItemModel>> data) {
                super.onSuccess(url, data);
                List<ShopItemModel> items = data.getData().getItems();
                if (page == 1) {
                    shopData.clear();
                }
                shopData.addAll(items);
                shopListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onAfter() {
                super.onAfter();
                spring.onFinishFreshAndLoad();
            }
        });
    }

    /**
     * 商品数据
     *
     * @param page
     * @param isShowProgress
     */
    private void requestProductData(int page, boolean isShowProgress) {
        JSONObject jsonObject = new JSONObject();
        String paramsData = null;
        try {
            jsonObject.put("page", page);
            paramsData = jsonObject.toString();
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this, getString(R.string.mall_程序出错), Toast.LENGTH_SHORT).show();
            return;
        }
        HttpUtils.postUrlWithBaseResponse(this, Api.MALL_PRODUCT_RANK, paramsData, isShowProgress, new OnRequestSuccess<BaseResponse<ItemsModel<ProductItemModel>>>() {
            @Override
            public void onSuccess(String url, BaseResponse<ItemsModel<ProductItemModel>> data) {
                super.onSuccess(url, data);
                List<ProductItemModel> items = data.getData().getItems();
                if (page == 1) {
                    productData.clear();
                }
                productData.addAll(items);
                if (isSinleColum) {
                    productSingleAdapter.notifyDataSetChanged();
                } else {
                    productDoubleAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onAfter() {
                super.onAfter();
                spring.onFinishFreshAndLoad();
            }
        });
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_top_product_shop_list);
        ButterKnife.bind(this);
        initRefereshView();
    }

    @OnClick({R.id.iv_back, R.id.iv_btn_style})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.iv_btn_style:
                isSinleColum = !isSinleColum;
                switchShowType(isSinleColum);
                break;
        }
    }

    /**
     * 跳转到商品详情界面
     *
     * @param productItemModel 商品信息
     * @param position         商品在列表中的排序
     */
    private void goProductDetail(ProductItemModel productItemModel, int position) {
        try {
            Intent intent = MallProductDetailActivity.generateIntent(this, Integer.parseInt(productItemModel.getProduct_id()));
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, getString(R.string.mall_程序出错), Toast.LENGTH_SHORT).show();
        }
    }
}
