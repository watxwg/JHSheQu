package com.jhcms.mall.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.jhcms.common.utils.Api;
import com.jhcms.common.utils.HttpUtils;
import com.jhcms.common.utils.OnRequestSuccess;
import com.jhcms.common.utils.StatusBarUtil;
import com.jhcms.common.utils.Utils;
import com.jhcms.mall.adapter.ListSelectAdapter;
import com.jhcms.mall.adapter.MallSearchListAdapter;
import com.jhcms.mall.adapter.PopLeftFenLeiAdapter;
import com.jhcms.mall.adapter.PopRightFenLeiAdapter;
import com.jhcms.mall.adapter.ProductDoubleAdapter;
import com.jhcms.mall.adapter.ProductSingleAdapter;
import com.jhcms.mall.adapter.ShopListAdapter;
import com.jhcms.mall.decoration.ProductDoubleListDecoration;
import com.jhcms.mall.fragment.TypeFragment;
import com.jhcms.mall.litepal.MallSearchHistory;
import com.jhcms.mall.model.BaseResponse;
import com.jhcms.mall.model.CategoryModel;
import com.jhcms.mall.model.HotSearchModel;
import com.jhcms.mall.model.ItemsModel;
import com.jhcms.mall.model.ProductItemModel;
import com.jhcms.mall.model.ShopItemModel;
import com.jhcms.mall.model.interfaces.CategoryInterface;
import com.jhcms.run.adapter.RightTimeAdapter;
import com.jhcms.waimaiV3.R;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import org.json.JSONObject;
import org.litepal.crud.DataSupport;
import org.litepal.crud.callback.FindMultiCallback;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author Wangwei
 *         商品和商家列表界面
 */
public class MallShopProduceListActivity extends BaseActivity {
    private static final String TAG = MallShopProduceListActivity.class.getSimpleName();
    private static final String CATE_ID = "cateID";
    private static final String CATE_NAME = "cateName";
    private static final String DISPLAY_TYPE = "displayType";
    public static final int PRODUCT_TYPE = 0x22;//显示商品
    public static final int SHOP_TYPE = 0x23;//显示店铺
    public static final int CATE_TYPE = 0x24;//类别搜索
    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.tv_sort)
    TextView tvSort;
    @Bind(R.id.et_search)
    EditText etSearch;
    @Bind(R.id.iv_delete)
    ImageView ivDelete;
    @Bind(R.id.fm_search)
    FrameLayout fmSearch;
    @Bind(R.id.tv_fenlei)
    TextView tvFenlei;
    @Bind(R.id.tv_zonghe)
    TextView tvZonghe;
    @Bind(R.id.tv_price)
    TextView tvPrice;
    @Bind(R.id.iv_list_style)
    ImageView ivListStyle;
    @Bind(R.id.rv_product_shop_list)
    RecyclerView rvProductShopList;
    @Bind(R.id.spring)
    SpringView spring;
    @Bind(R.id.activity_mall_shop_produce_list)
    LinearLayout activityMallShopProduceList;
    @Bind(R.id.ll_select_container)
    LinearLayout llSelectContainer;
    @Bind(R.id.v_background)
    View vBackground;
    @Bind(R.id.rv_search_list)
    RecyclerView rvSearchList;
    @Bind(R.id.tv_cancle)
    TextView tvCancle;
    @Bind(R.id.ll_search)
    LinearLayout llSearch;
    /*----------------------分类popuwindow相关----------------------*/
    private PopupWindow searchpopwin;
    private PopupWindow zongHePopwi;
    private ArrayList<CategoryInterface> leftFenLeiData;
    private PopLeftFenLeiAdapter leftFenLeiAdapter;
    private PopupWindow fenLeiPopwi;
    private int priceBtnStatus = 0;
    private ArrayList<CategoryInterface> rightFenLeiData;
    private PopRightFenLeiAdapter rightFenLeiAdapter;
    //排序popupwindow的List
    private ListSelectAdapter orderListadapter;
    /**
     * 记录排序类型
     */
    private String selectOrderType;
    /*------------------------列表相关----------------------------*/
    private List<ProductItemModel> productData;
    private List<ShopItemModel> shopData;
    private ProductDoubleAdapter productDoubleAdapter;
    private ProductSingleAdapter productSingleAdapter;
    private ShopListAdapter shopListAdapter;
    /**
     * 标记商品列表是否单列显示
     */
    private boolean productSingleColum = true;
    /**
     * 标记当前显示的内容，true：显示商品 flase：显示商家
     */
    private boolean isShowProduct = true;
    /**
     * 记录商品和商家的加载页数
     */
    private int productPageIndex;
    private int shopPageIndex;
    private ProductDoubleListDecoration itemDecoration;
    /**
     * 记录选择的分类数据
     */
    private CategoryInterface selectedCategory;
    /*----------------------搜索历史列表相关------------------------*/
    private View headerView;
    private List<String> listSearchData;
    private MallSearchListAdapter searchListAdapter;
    private TagFlowLayout tagFlowLayout;
    private TextView tvDelete;
    private List<String> hotData;
    private TagAdapter<String> tagAdapter;
    private List<String> searchHistory;
    private List<String> hotProduct;
    private List<String> hotShop;

    /**
     * @param context
     * @param cateId      分类id
     * @param cateName    分类名称
     * @param displayType 显示内容的类别
     * @return
     */
    public static Intent generateIntent(Context context, @Nullable Integer cateId, @Nullable String cateName, int displayType) {
        Intent intent = new Intent(context, MallShopProduceListActivity.class);
        if (cateId != null) {

            intent.putExtra(CATE_ID, cateId);
        }
        if (cateName != null) {

            intent.putExtra(CATE_NAME, cateName);
        }
        intent.putExtra(DISPLAY_TYPE, displayType);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        int cateId = intent.getIntExtra(CATE_ID, -1);
        String cateName = intent.getStringExtra(CATE_NAME);
        int displayType = intent.getIntExtra(DISPLAY_TYPE, -1);
        Log.e(TAG, "initData: " + cateId + ":" + cateName);
        if (displayType != -1) {
            switch (displayType) {
                case PRODUCT_TYPE:
                    switchShowingContent(true);
                    break;
                case SHOP_TYPE:
                    switchShowingContent(false);
                    break;
                case CATE_TYPE:

                    if (!TextUtils.isEmpty(cateName)) {
                        if (cateId != -1) {

                            CategoryModel categoryModel = new CategoryModel();
                            categoryModel.setCate_id(cateId + "");
                            categoryModel.setTitle(cateName);
                            selectedCategory = categoryModel;
                        }
                    }
                    break;


            }
            rvSearchList.setVisibility(View.GONE);
        } else {//显示搜索历史列表时，需要设置焦点
            etSearch.requestFocus();
        }

        productPageIndex = 1;
        shopPageIndex = 1;
        requestCategoryData();
        requestProductData(null, cateId == -1 ? null : cateId, null, productPageIndex, true);
        requestShopData(null, null, null, shopPageIndex, false);
        //热搜数据初始化
        requestHotData(Api.MALL_PRODUCT_SEARCH_HOT, true);
        requestHotData(Api.MALL_SHOP_SEARCH_HOT, false);
        DataSupport.order("id").findAsync(MallSearchHistory.class).listen(new FindMultiCallback() {
            @Override
            public <T> void onFinish(List<T> t) {
                List<MallSearchHistory> data = (List<MallSearchHistory>) t;
                searchHistory = new ArrayList<String>();
                for (MallSearchHistory mallSearchHistory : data) {
                    searchHistory.add(mallSearchHistory.getSearchContent());
                }
                listSearchData.addAll(searchHistory);
                searchListAdapter.notifyDataSetChanged();
            }
        });
    }


    @Override
    protected void initView() {
        setContentView(R.layout.activity_mall_shop_produce_list);
        ButterKnife.bind(this);
        StatusBarUtil.immersive(this, Color.WHITE);
        initRefereshView();
        inintsearchpopwi();
        initZongHePopwi();
        initFenLeiPopWi();
        initList();
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    ivDelete.setVisibility(View.VISIBLE);
                    fmSearch.setVisibility(View.VISIBLE);
                } else {
                    ivDelete.setVisibility(View.GONE);
                    fmSearch.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        etSearch.setOnFocusChangeListener((view, hasFocus) -> {
            Log.e(TAG, "initView: " + hasFocus);
            if (hasFocus) {
                tvCancle.setVisibility(View.VISIBLE);
                rvSearchList.setVisibility(View.VISIBLE);
            } else {
                tvCancle.setVisibility(View.INVISIBLE);
                rvSearchList.setVisibility(View.GONE);
            }
        });

    }

    /**
     * 初始化列表
     */
    private void initList() {
        productData = new ArrayList<>();
        shopData = new ArrayList<>();
        productSingleAdapter = new ProductSingleAdapter(this, productData);
        productSingleAdapter.setOnItemClickListener(this::goProductDetail);
        productDoubleAdapter = new ProductDoubleAdapter(this, productData);
        productDoubleAdapter.setOnItemClickListener(this::goProductDetail);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        shopListAdapter = new ShopListAdapter(this, shopData);
        shopListAdapter.setOnItemClickListener(new ShopListAdapter.OnItemClickListener() {
            @Override
            public void comeInShop(String shopId) {
                try {

                    Intent intent1 = MallShopDetailsActivity.generateIntent(MallShopProduceListActivity.this, Integer.parseInt(shopId));
                    startActivity(intent1);
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(MallShopProduceListActivity.this, getString(R.string.mall_程序出错), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void OnProductClick(String productId) {
                try {

                    Intent intent1 = MallProductDetailActivity.generateIntent(MallShopProduceListActivity.this, Integer.parseInt(productId));
                    startActivity(intent1);
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(MallShopProduceListActivity.this, getString(R.string.mall_程序出错), Toast.LENGTH_SHORT).show();
                }
            }
        });
        rvProductShopList.setAdapter(productSingleAdapter);
        rvProductShopList.setLayoutManager(linearLayoutManager);
        //搜索历史列表
        rvSearchList.setLayoutManager(new LinearLayoutManager(this));
        headerView = LayoutInflater.from(this).inflate(R.layout.mall_search_list_item_header, rvSearchList, false);
        initHeaderView(headerView);
        listSearchData = new ArrayList<>();
        searchListAdapter = new MallSearchListAdapter(this, listSearchData, true, headerView);
        rvSearchList.setAdapter(searchListAdapter);
        searchListAdapter.setOnItemClickListener((s, position) -> {
            etSearch.setText(s);
            etSearch.setSelection(s.length());
            cleanSearchFocus();
            searchData(true, true);
        });
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
     * 初始化搜索历史列表头部view
     *
     * @param headerView
     */
    private void initHeaderView(View headerView) {
        tagFlowLayout = (TagFlowLayout) headerView.findViewById(R.id.tag_flow);
        tvDelete = (TextView) headerView.findViewById(R.id.tv_delete);
        tvDelete.setOnClickListener(v -> {
            searchHistory.clear();
            listSearchData.clear();
            searchListAdapter.notifyDataSetChanged();
            DataSupport.deleteAll(MallSearchHistory.class);
        });
        hotData = new ArrayList<>();
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        tagAdapter = new TagAdapter<String>(hotData) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {
                TextView tv = (TextView) layoutInflater.inflate(R.layout.mall_tag_layout, parent, false);
                tv.setText(s);
                return tv;
            }
        };
        tagFlowLayout.setAdapter(tagAdapter);
        tagFlowLayout.setOnTagClickListener((view, position, parent) -> {
            etSearch.setText(((TextView) view).getText().toString());
            etSearch.setSelection(((TextView) view).getText().toString().length());
            cleanSearchFocus();
            searchData(true, true);
            return true;
        });
    }

    /**
     * 请求热门搜索数据
     *
     * @param api
     * @param isShowDialog 是否显示加载对话框
     */
    private void requestHotData(String api, boolean isShowDialog) {
        HttpUtils.postUrlWithBaseResponse(this, api, null, isShowDialog
                , new OnRequestSuccess<BaseResponse<HotSearchModel>>() {

                    @Override
                    public void onSuccess(String url, BaseResponse<HotSearchModel> data) {
                        if (Api.MALL_PRODUCT_SEARCH_HOT.equals(url)) {
                            hotProduct = data.getData().getHots();
                            initHotData(hotProduct);
                        } else if (Api.MALL_SHOP_SEARCH_HOT.equals(url)) {
                            hotShop = data.getData().getHots();
                        }
                        Log.e("++++++++++", "onSuccess: " + data.getData().getHots().size());
                    }

                });

    }

    /**
     * 初始化热搜数据
     *
     * @param hotData
     */
    private void initHotData(List<String> hotData) {
        this.hotData.clear();
        this.hotData.addAll(hotData);
        tagAdapter.notifyDataChanged();
    }

    /**
     * 请求分类数据
     */
    private void requestCategoryData() {
        String paramsData = new JSONObject().toString();
        HttpUtils.postUrlWithBaseResponse(this, Api.MALL_CATEGORY, paramsData, false, new OnRequestSuccess<BaseResponse<ItemsModel<CategoryModel>>>() {
            @Override
            public void onSuccess(String url, BaseResponse<ItemsModel<CategoryModel>> data) {
                super.onSuccess(url, data);
                try {

                    List<CategoryModel> items = data.getData().getItems();
                    leftFenLeiData.clear();
                    leftFenLeiData.addAll(items);
                    leftFenLeiAdapter.notifyDataSetChanged();
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(MallShopProduceListActivity.this, getString(R.string.mall_程序出错), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * 请求商家数据
     *
     * @param searchContent  搜索内容
     * @param cataId         分类id
     * @param order          排序 default:默认，new:新店，s_up 销量从低到高 s_down 销量从高到低 collects 关注数(粉丝数)
     * @param page           分页页数
     * @param isShowProgress 是否显示进度对话框
     */
    private void requestShopData(@Nullable String searchContent
            , @Nullable Integer cataId, @Nullable String order
            , @NonNull final int page
            , boolean isShowProgress) {
        JSONObject jsonObject = new JSONObject();
        try {
            if (!TextUtils.isEmpty(searchContent)) {
                jsonObject.put("title", searchContent);
            }
            if (cataId != null) {
                jsonObject.put("cate_id", cataId);
            }
            if (!TextUtils.isEmpty(order)) {
                jsonObject.put("order", order);
            }
            jsonObject.put("page", page);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, getString(R.string.mall_程序出错), Toast.LENGTH_SHORT).show();
            return;
        }
        String paramsData = jsonObject.toString();
        Log.e(TAG, "requestShopData: params=" + paramsData);
        HttpUtils.postUrlWithBaseResponse(this, Api.MALL_SHOP_SEARCH, paramsData, isShowProgress
                , new OnRequestSuccess<BaseResponse<ItemsModel<ShopItemModel>>>() {

                    @Override
                    public void onSuccess(String url, BaseResponse<ItemsModel<ShopItemModel>> data) {
                        try {

                            loadShopData(data.getData().getItems(), page);
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(MallShopProduceListActivity.this, getString(R.string.mall_程序出错), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onAfter() {
                        spring.onFinishFreshAndLoad();
                    }
                });
    }

    /**
     * 加载商家数据
     *
     * @param items 商家列表数据
     * @param page  当前加载页数
     */
    private void loadShopData(List<ShopItemModel> items, int page) {
        if (page == 1) {
            shopData.clear();
        } else {
            if (items == null || items.size() == 0) {
                Toast.makeText(this, R.string.mall_没有更多数据了, Toast.LENGTH_SHORT).show();
                return;
            }
        }
        shopData.addAll(items);
        if (isShowProduct) {
            return;
        }
        shopListAdapter.notifyDataSetChanged();

    }

    /**
     * 请求商品数据
     *
     * @param seachContent 关键词
     * @param cataId       分类Id
     * @param order        排序 default:智能，sales:热销，new:上新, p_up:价格从低到高，p_down:价格从高到低
     * @param page         分页
     */
    private void requestProductData(@Nullable String seachContent
            , @Nullable Integer cataId, String order
            , @NonNull final int page
            , boolean isShowProgress) {
        JSONObject jsonObject = new JSONObject();
        try {

            if (!TextUtils.isEmpty(seachContent)) {
                jsonObject.put("title", seachContent);
            }
            if (cataId != null) {
                jsonObject.put("cate_id", cataId);
            }
            if (!TextUtils.isEmpty(order)) {
                jsonObject.put("order", order);
            }
            jsonObject.put("page", page);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, getString(R.string.mall_程序出错), Toast.LENGTH_SHORT).show();
            return;
        }
        String data = jsonObject.toString();
        Log.e(TAG, "requestProductData: params=" + data);
        HttpUtils.postUrlWithBaseResponse(this, Api.MALL_PRODUCT_SEARCH, data
                , isShowProgress, new OnRequestSuccess<BaseResponse<ItemsModel<ProductItemModel>>>() {

                    @Override
                    public void onSuccess(String url, BaseResponse<ItemsModel<ProductItemModel>> data) {
                        loadProductData(data.getData().getItems(), page);
                    }

                    @Override
                    public void onBeforeAnimate() {

                    }

                    @Override
                    public void onErrorAnimate() {

                    }

                    @Override
                    public void onAfter() {
                        spring.onFinishFreshAndLoad();
                    }
                });
    }

    /**
     * 加载商品数据
     *
     * @param items 商品数据
     * @param page  当前加载的页数
     */
    private void loadProductData(List<ProductItemModel> items, int page) {
        if (page == 1) {
            productData.clear();
            productData.addAll(items);
        } else {
            if (items == null || items.size() == 0) {
                Toast.makeText(this, R.string.mall_没有更多数据了, Toast.LENGTH_SHORT).show();
                return;
            }
            productData.addAll(items);
        }
        //若当前显示的是商家，则不需要更新
        if (!isShowProduct) {
            return;
        }
        if (productSingleColum) {
            productSingleAdapter.notifyDataSetChanged();
        } else {
            productDoubleAdapter.notifyDataSetChanged();
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
                if (isShowProduct) {
                    productPageIndex = 1;
//                    requestProductData(null, null, null, productPageIndex, false);
                } else {
                    shopPageIndex = 1;
//                    requestShopData(null, null, null, shopPageIndex, false);
                }
                searchData(true, true);
            }

            @Override
            public void onLoadmore() {
                if (isShowProduct) {
                    productPageIndex++;
//                    requestProductData(null, null, null, productPageIndex, false);
                } else {
                    shopPageIndex++;
//                    requestShopData(null, null, null, shopPageIndex, false);
                }
                searchData(true, false);

            }
        });
        spring.setHeader(new DefaultHeader(MallShopProduceListActivity.this));
        spring.setFooter(new DefaultFooter(MallShopProduceListActivity.this));
        spring.setType(SpringView.Type.FOLLOW);
    }

    /**
     * 根据选择的排序类型，返回相印的请求接口参数
     *
     * @return
     */
    @Nullable
    private String getOrderParams() {
        if (selectOrderType == null) {
            return null;
        }
        if (getString(R.string.mall_智能).equals(selectOrderType)) {
            return "default";
        } else if (getString(R.string.mall_热销).equals(selectOrderType)) {
            return "sales";
        } else if (getString(R.string.mall_上新).equals(selectOrderType)) {
            return "new";
        } else if (getString(R.string.mall_智能排序).equals(selectOrderType)) {
            return "default";
        } else if (getString(R.string.mall_新店开业).equals(selectOrderType)) {
            return "new";
        } else if (getString(R.string.mall_关注最多).equals(selectOrderType)) {
            return "collects";
        } else {
            return null;
        }
    }

    /**
     * 根据选择的价格或销量的排序类型返回相印的请求接口的参数
     *
     * @return
     */
    @Nullable
    private String getSalesOrPriceOrderParams() {
        String item = null;
        if (isShowProduct) {
            switch (priceBtnStatus % 3) {
                case 0:
                    break;
                case 1:
                    item = "p_up";
                    break;
                case 2:
                    item = "p_down";
                    break;
            }
        } else {
            switch (priceBtnStatus % 3) {
                case 0:
                    break;
                case 1:
                    item = "s_up";
                    break;
                case 2:
                    item = "s_down";
                    break;
            }
        }
        return item;
    }

    @OnClick({R.id.iv_back, R.id.tv_sort, R.id.iv_delete
            , R.id.fm_search, R.id.tv_fenlei, R.id.tv_zonghe
            , R.id.tv_price, R.id.iv_list_style, R.id.tv_cancle})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                if (searchpopwin.isShowing() || fenLeiPopwi.isShowing() || zongHePopwi.isShowing()) {
                    return;
                }
                onBackPressed();
                break;
            case R.id.tv_sort:
                if (!searchpopwin.isShowing()) {
                    searchpopwin.showAsDropDown(view);
                }
                break;
            case R.id.iv_delete:
                etSearch.setText("");
                break;
            case R.id.fm_search:
                String content = etSearch.getText().toString();
                if (TextUtils.isEmpty(content)) {
                    Toast.makeText(this, getString(R.string.mall_请输入), Toast.LENGTH_SHORT).show();
                    return;
                }
                //搜索
                cleanSearchFocus();
                searchData(true, true);
                for (String s : searchHistory) {
                    if (content.equals(s)) {
                        return;
                    }
                }
                //历史记录处理
                listSearchData.add(content);
                searchListAdapter.notifyDataSetChanged();
                MallSearchHistory history = new MallSearchHistory();
                history.setSearchContent(content);
                history.save();
                searchHistory.add(content);
                break;
            case R.id.tv_fenlei:
                if (fenLeiPopwi.isShowing()) {
                    fenLeiPopwi.dismiss();
                } else {
                    fenLeiPopwi.showAsDropDown(llSelectContainer);
                    switchFenLeistatus(true);
                    vBackground.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.tv_zonghe:
                if (zongHePopwi.isShowing()) {
                    zongHePopwi.dismiss();
                } else {
                    zongHePopwi.showAsDropDown(llSelectContainer);
                    switchZongheStatus(true);
                    vBackground.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.tv_price:
                priceBtnStatus++;
                switchPriceStatus();
                //擦除原来的排序类型
                selectOrderType = null;
                orderListadapter.resetSelect();
                searchData(true, true);
                break;
            case R.id.iv_list_style:
                switchProducctListStyle();
                break;
            case R.id.tv_cancle:
                Log.e(TAG, "onViewClicked: " + "cancle");
                cleanSearchFocus();
                break;
        }
    }

    /**
     * 清除搜索框的焦点，隐藏软键盘
     */
    private void cleanSearchFocus() {
        etSearch.clearFocus();
        ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
                .hideSoftInputFromWindow(this
                                .getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * 切换商品列表的显示样式
     */
    private void switchProducctListStyle() {
        if (!isShowProduct) {
            return;
        }
        if (productSingleColum) {
            ivListStyle.setImageResource(R.mipmap.mall_list_style_single);
        } else {
            ivListStyle.setImageResource(R.mipmap.mall_list_style_double);
        }
        productSingleColum = !productSingleColum;
        if (productSingleColum) {
            rvProductShopList.setLayoutManager(new LinearLayoutManager(this));
            rvProductShopList.setAdapter(productSingleAdapter);
            rvProductShopList.removeItemDecoration(itemDecoration);
        } else {
            rvProductShopList.setLayoutManager(new GridLayoutManager(this, 2));
            rvProductShopList.setAdapter(productDoubleAdapter);
            itemDecoration = new ProductDoubleListDecoration(Utils.dip2px(this, 5), 2);
            rvProductShopList.addItemDecoration(itemDecoration);
        }
    }


 /*===============================头部交互相关=======================================*/

    /**
     * 切换价格按钮的显示状态
     */
    private void switchPriceStatus() {
        Rect rect = new Rect(0, 0, Utils.dip2px(this, 10), Utils.dip2px(this, 15));
        Drawable drawable = null;
        switch (priceBtnStatus % 3) {
            case 0://默认状态
                drawable = getResources().getDrawable(R.mipmap.mall_btn_price);
                break;
            case 1://降序
                drawable = getResources().getDrawable(R.mipmap.mall_btn_price_up);
                break;
            case 2://升序
                drawable = getResources().getDrawable(R.mipmap.mall_btn_price_down);
                break;
        }
        drawable.setBounds(rect);
        tvPrice.setCompoundDrawables(null, null, drawable, null);
    }

    /**
     * 初始化搜索框中商家商品选择的popupWindow
     */
    private void inintsearchpopwi() {
        View view = LayoutInflater.from(this).inflate(R.layout.item_mallsearchactivity_mall_select, null);
        searchpopwin = new PopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        searchpopwin.setContentView(view);
        searchpopwin.setOutsideTouchable(true);
        searchpopwin.setBackgroundDrawable(new BitmapDrawable());
        searchpopwin.setFocusable(false);// 获取焦点
        final TextView mTvGood = (TextView) view.findViewById(R.id.Good_tv);
        final TextView mTvshop = (TextView) view.findViewById(R.id.shop_tv);

        mTvGood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (searchpopwin.isShowing()) {
                    searchpopwin.dismiss();
                }
                switchShowingContent(true);
            }
        });
        mTvshop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (searchpopwin.isShowing()) {
                    searchpopwin.dismiss();
                }
                switchShowingContent(false);


            }
        });
    }

    /**
     * 切换显示内容
     *
     * @param isShowProduct true：显示商品 false：显示商家
     */
    private void switchShowingContent(boolean isShowProduct) {
        if (this.isShowProduct != isShowProduct) {//内容与之前内容有变化,擦除选择的排序类型
            selectOrderType = null;
            priceBtnStatus = 0;
            orderListadapter.resetSelect();
            switchPriceStatus();
        }
        this.isShowProduct = isShowProduct;
        if (isShowProduct) {
            tvSort.setText(getString(R.string.mall_商品));
            tvZonghe.setText(getString(R.string.mall_综合));
            tvPrice.setText(getString(R.string.mall_价格));
            //先做取反，会在switchProducctListStyle重新取反
            productSingleColum = !productSingleColum;
            String[] stringArray = getResources().getStringArray(R.array.zonghe_select);
            orderListadapter.setData(Arrays.asList(stringArray));
            switchProducctListStyle();

            hotData.clear();
            if (hotProduct != null) {

                hotData.addAll(hotProduct);
                tagAdapter.notifyDataChanged();
            }
        } else {
            tvSort.setText(getString(R.string.mall_商家));
            tvZonghe.setText(getString(R.string.mall_综合排序));
            tvPrice.setText(getString(R.string.mall_销量));
            if (!productSingleColum) {
                rvProductShopList.removeItemDecoration(itemDecoration);
                rvProductShopList.setLayoutManager(new LinearLayoutManager(this));
            }
            String[] stringArray = getResources().getStringArray(R.array.zonghe_select2);
            orderListadapter.setData(Arrays.asList(stringArray));
            rvProductShopList.setAdapter(shopListAdapter);

            hotData.clear();
            if (hotShop != null) {

                hotData.addAll(hotShop);
                tagAdapter.notifyDataChanged();
            }
        }
    }

    /**
     * 初始化综合popupwindow
     */
    private void initZongHePopwi() {
        RecyclerView view = (RecyclerView) LayoutInflater.from(this).inflate(R.layout.mall_popwi_list_select_layout, null);
        zongHePopwi = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        zongHePopwi.setOutsideTouchable(true);
        zongHePopwi.setBackgroundDrawable(new BitmapDrawable());
        zongHePopwi.setFocusable(true);
        String[] stringArray = getResources().getStringArray(R.array.zonghe_select);
        List<String> selectData = Arrays.asList(stringArray);
        orderListadapter = new ListSelectAdapter(this, selectData);
        view.setAdapter(orderListadapter);
        view.setLayoutManager(new LinearLayoutManager(this));
        orderListadapter.setOnItemSelectListener((selectName, position) -> {
            Toast.makeText(this, selectName, Toast.LENGTH_SHORT).show();
            selectOrderType = selectName;
            priceBtnStatus = 0;
            switchPriceStatus();
            zongHePopwi.dismiss();
            searchData(true, true);
        });
        zongHePopwi.setOnDismissListener(() -> {

                    switchZongheStatus(false);
                    vBackground.setVisibility(View.GONE);
                }
        );
    }

    /**
     * 切换综合按钮的显示状态
     *
     * @param b 表示popupWindos是否显示
     */
    private void switchZongheStatus(boolean b) {
        Rect rect = new Rect(0, 0, Utils.dip2px(this, 10), Utils.dip2px(this, 7));
        Drawable drawable = null;
        if (b) {
            tvZonghe.setTextColor(Color.parseColor(getString(R.string.mall_color_theme)));
            drawable = getResources().getDrawable(R.mipmap.mall_btn_arrow_up_select);
        } else {
            tvZonghe.setTextColor(Color.parseColor(getString(R.string.mall_color_333333)));
            drawable = getResources().getDrawable(R.mipmap.mall_arrow_down);
        }
        drawable.setBounds(rect);
        tvZonghe.setCompoundDrawables(null, null, drawable, null);
    }

    /**
     * 初始化分类popupwindow
     */
    private void initFenLeiPopWi() {
        View view = LayoutInflater.from(this).inflate(R.layout.mall_popwi_fenlei, null);
        //获取屏幕显示区域的高度
        Rect fram = new Rect();
        getWindow().getDecorView().getWindowVisibleDisplayFrame(fram);
        fenLeiPopwi = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, (int) (fram.height() * 0.6f));
        fenLeiPopwi.setOutsideTouchable(true);
        fenLeiPopwi.setBackgroundDrawable(new BitmapDrawable());
        fenLeiPopwi.setFocusable(true);
        fenLeiPopwi.setOnDismissListener(() -> {
            switchFenLeistatus(false);
            vBackground.setVisibility(View.GONE);
        });

        RecyclerView rvLeft = (RecyclerView) view.findViewById(R.id.rv_left);
        RecyclerView rvRight = (RecyclerView) view.findViewById(R.id.rv_right);
        leftFenLeiData = new ArrayList<>();
        leftFenLeiAdapter = new PopLeftFenLeiAdapter(this, leftFenLeiData);
        rvLeft.setLayoutManager(new LinearLayoutManager(this));
        rvLeft.setAdapter(leftFenLeiAdapter);
        rightFenLeiData = new ArrayList<>();
        rightFenLeiAdapter = new PopRightFenLeiAdapter(this, rightFenLeiData);
        rvRight.setLayoutManager(new LinearLayoutManager(this));
        rvRight.setAdapter(rightFenLeiAdapter);


        leftFenLeiAdapter.setOnItemClickListener(((catagoryInterface, position) -> {
            if (!catagoryInterface.hasSubDataImp()) {
                selectedCategory = catagoryInterface;
                tvFenlei.setText(selectedCategory.getTitleImp());
                rightFenLeiData.clear();
                rightFenLeiAdapter.notifyDataSetChanged();
                fenLeiPopwi.dismiss();
                searchData(true, true);
            } else {
                List<? extends CategoryInterface> childrensImp = catagoryInterface.getChildrensImp();
                rightFenLeiData.clear();
                rightFenLeiData.addAll(childrensImp);
                rightFenLeiAdapter.resetSelectIndex();
                rightFenLeiAdapter.notifyDataSetChanged();
            }
            Log.e(TAG, "initFenLeiPopWi: " + "leftClick=" + catagoryInterface.toStringImp());
        }));
        rightFenLeiAdapter.setOnItemClickListener(((categoryInterface, position) -> {
            selectedCategory = categoryInterface;
            fenLeiPopwi.dismiss();
            tvFenlei.setText(selectedCategory.getTitleImp());
            searchData(true, true);
            Log.e(TAG, "initFenLeiPopWi: " + "rightClick=" + categoryInterface.toStringImp());
        }));
    }

    /**
     * 根据选择的排序条件搜索数据
     *
     * @param isShowProgress 是否显示进度对话框
     * @param isResetPage    是否重置页数
     */
    private void searchData(boolean isShowProgress, boolean isResetPage) {
        String searchConent = etSearch.getText().toString();
        Integer cataId = null;
        if (selectedCategory != null) {

            cataId = Integer.parseInt(selectedCategory.getCateIdImp());
        }
        String order = getOrderParams();
        if (order == null) {
            order = getSalesOrPriceOrderParams();
        }
        if (isShowProduct) {
            if (isResetPage) {
                productPageIndex = 1;
            }
            requestProductData(searchConent, cataId, order, productPageIndex, isShowProgress);
        } else {
            if (isResetPage) {
                shopPageIndex = 1;
            }
            requestShopData(searchConent, cataId, order, shopPageIndex, isShowProgress);
        }
    }

    /**
     * 切换分类按钮的显示状态
     *
     * @param b 分类popupwindow是否显示
     */
    private void switchFenLeistatus(boolean b) {
        Rect rect = new Rect(0, 0, Utils.dip2px(this, 10), Utils.dip2px(this, 7));
        Drawable drawable = null;
        if (b) {
            tvFenlei.setTextColor(Color.parseColor(getString(R.string.mall_color_theme)));
            drawable = getResources().getDrawable(R.mipmap.mall_btn_arrow_up_select);
        } else {
            tvFenlei.setTextColor(Color.parseColor(getString(R.string.mall_color_333333)));
            drawable = getResources().getDrawable(R.mipmap.mall_arrow_down);
        }
        drawable.setBounds(rect);
        tvFenlei.setCompoundDrawables(null, null, drawable, null);
    }
}
