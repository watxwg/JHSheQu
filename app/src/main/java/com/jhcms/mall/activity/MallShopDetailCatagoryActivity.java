package com.jhcms.mall.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.text.TextUtilsCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jhcms.common.utils.Api;
import com.jhcms.common.utils.HttpUtils;
import com.jhcms.common.utils.OnRequestSuccess;
import com.jhcms.common.utils.Utils;
import com.jhcms.mall.adapter.ProductDoubleAdapter;
import com.jhcms.mall.decoration.ProductDoubleListDecoration;
import com.jhcms.mall.model.BaseResponse;
import com.jhcms.mall.model.ItemsModel;
import com.jhcms.mall.model.ProductItemModel;
import com.jhcms.waimaiV3.R;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 作者：WangWei
 * 描述：商铺详情——分类详情
 */
public class MallShopDetailCatagoryActivity extends BaseActivity {
    private static final int NEW_TYPE = 2;
    private static final int SALE_TYPE = 1;

    private static final String SHOP_ID = "shopId";
    private static final String KEY_WORD = "keyWord";
    private static final String CATE_ID = "cateId";
    private static final String CATE_NAME = "cateName";

    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.tv_sale_num)
    TextView tvSaleNum;
    @Bind(R.id.tv_new)
    TextView tvNew;
    @Bind(R.id.tv_price)
    TextView tvPrice;
    @Bind(R.id.rv_list)
    RecyclerView rvList;
    @Bind(R.id.spring)
    SpringView spring;
    @Bind(R.id.activity_shop_detail_catagory)
    LinearLayout activityShopDetailCatagory;
    private List<ProductItemModel> productData;
    private ProductDoubleAdapter productDoubleAdapter;
    //当前页，当前的价格排序类型，1：高到低 2：低到高 当前类型：销量和上新
    private int currentPageIndex, currentPriceRank, currentType;
    private int mShopId, mCateId;
    private String mKeyWord, mCateName;

    /**
     * @param context
     * @param shopId   店铺id
     * @param keyWord  搜索关键字
     * @param cateId   分类id
     * @param cateName 分类名
     * @return
     */
    public static Intent generateIntent(Context context, @NonNull Integer shopId
            , @Nullable String keyWord, @Nullable Integer cateId, @Nullable String cateName) {
        Intent intent = new Intent(context, MallShopDetailCatagoryActivity.class);
        intent.putExtra(SHOP_ID, shopId);
        intent.putExtra(KEY_WORD, keyWord);
        intent.putExtra(CATE_ID, cateId);
        intent.putExtra(CATE_NAME, cateName);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        mShopId = intent.getIntExtra(SHOP_ID, -1);

        mCateId = intent.getIntExtra(CATE_ID, -1);
        mKeyWord = intent.getStringExtra(KEY_WORD);
        mCateName = intent.getStringExtra(CATE_NAME);
        if (TextUtils.isEmpty(mCateName)) {
            tvTitle.setText(R.string.mall_全部);
        } else {
            tvTitle.setText(mCateName);
        }
        currentType = SALE_TYPE;
        tvSaleNum.setTextColor(Color.parseColor(getString(R.string.mall_color_theme)));
        currentPageIndex = 1;
        currentPriceRank = 0;
        switchPriceType();
        productData = new ArrayList<>();
        productDoubleAdapter = new ProductDoubleAdapter(this, productData);
        productDoubleAdapter.setOnItemClickListener(this::goProductDetail);
        rvList.setAdapter(productDoubleAdapter);

        rvList.setLayoutManager(new GridLayoutManager(this, 2));
        ProductDoubleListDecoration itemDecoration = new ProductDoubleListDecoration(Utils.dip2px(this, 5), 2);
        rvList.addItemDecoration(itemDecoration);

        requestData(mShopId,mCateId,mKeyWord,currentType,currentPriceRank,currentPageIndex);
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

    /**
     * 请求数据
     *
     * @param shopId    店铺id
     * @param cateId    分类id
     * @param keyWord   关键字
     * @param type      类型
     * @param priceType 价格排序类型
     * @param page      页数
     */
    public void requestData(int shopId, int cateId, String keyWord, int type, int priceType, int page) {
        String paramData = null;
        try {
            JSONObject jsonObject = new JSONObject();
            if (shopId == -1) {
                Toast.makeText(this, getString(R.string.mall_店铺不存在), Toast.LENGTH_SHORT).show();
                return;
            }
            jsonObject.put("shop_id", shopId);
            if (cateId != -1) {
                jsonObject.put("cate_id", cateId);
            }
            if (!TextUtils.isEmpty(keyWord)) {
                jsonObject.put("title", keyWord);
            }
            jsonObject.put("type", type);
            jsonObject.put("price", priceType%2 + 1);
            jsonObject.put("page", page);
            paramData = jsonObject.toString();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, getString(R.string.mall_程序出错), Toast.LENGTH_SHORT).show();
            return;
        }
        Log.e(TAG, "requestData: "+paramData );
        HttpUtils.postUrlWithBaseResponse(this, Api.MALL_SHOP_PRODUCT, paramData, true, new OnRequestSuccess<BaseResponse<ItemsModel<ProductItemModel>>>() {
            @Override
            public void onSuccess(String url, BaseResponse<ItemsModel<ProductItemModel>> data) {
                super.onSuccess(url, data);
                List<ProductItemModel> items = data.getData().getItems();
                if (items == null || items.size() == 0) {
                    return;
                }
                if (page == 1) {
                    productData.clear();
                }
                productData.addAll(items);
                productDoubleAdapter.notifyDataSetChanged();
            }

            @Override
            public void onAfter() {
                super.onAfter();
                spring.onFinishFreshAndLoad();
            }
        });
    }

    /**
     * 切换价格按钮的显示
     */
    private void switchPriceType() {
        Rect rect = new Rect(0, 0, Utils.dip2px(this, 10), Utils.dip2px(this, 15));
        Drawable drawable = null;
        switch (currentPriceRank % 2) {
            case 0://降序
                drawable = getResources().getDrawable(R.mipmap.mall_btn_price_up);
                break;
            case 1://升序
                drawable = getResources().getDrawable(R.mipmap.mall_btn_price_down);
                break;
        }
        drawable.setBounds(rect);
        tvPrice.setCompoundDrawables(null, null, drawable, null);
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_shop_detail_catagory);
        ButterKnife.bind(this);
        initRefereshView();
    }

    @OnClick({R.id.iv_back, R.id.tv_title, R.id.tv_sale_num, R.id.tv_new, R.id.tv_price})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.tv_title:

                break;
            case R.id.tv_sale_num:
                ((TextView)view).setTextColor(Color.parseColor(getString(R.string.mall_color_theme)));
                tvNew.setTextColor(Color.parseColor(getString(R.string.mall_color_333333)));
                currentType=SALE_TYPE;
                requestData(mShopId,mCateId,mKeyWord,currentType,currentPriceRank,currentPageIndex);
                break;
            case R.id.tv_new:
                ((TextView)view).setTextColor(Color.parseColor(getString(R.string.mall_color_theme)));
                tvSaleNum.setTextColor(Color.parseColor(getString(R.string.mall_color_333333)));
                currentType=NEW_TYPE;
                requestData(mShopId,mCateId,mKeyWord,currentType,currentPriceRank,currentPageIndex);
                break;
            case R.id.tv_price:
                currentPriceRank++;
                switchPriceType();
                requestData(mShopId,mCateId,mKeyWord,currentType,currentPriceRank,currentPageIndex);
                break;
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
                requestData(mShopId,mCateId,mKeyWord,currentType,currentPriceRank,currentPageIndex);
            }

            @Override
            public void onLoadmore() {
                currentPageIndex++;
                requestData(mShopId,mCateId,mKeyWord,currentType,currentPriceRank,currentPageIndex);
            }
        });
        spring.setHeader(new DefaultHeader(MallShopDetailCatagoryActivity.this));
        spring.setFooter(new DefaultFooter(MallShopDetailCatagoryActivity.this));
        spring.setType(SpringView.Type.FOLLOW);
    }
}
