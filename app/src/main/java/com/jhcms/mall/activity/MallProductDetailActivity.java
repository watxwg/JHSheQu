package com.jhcms.mall.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jhcms.common.utils.Api;
import com.jhcms.common.utils.HttpUtils;
import com.jhcms.common.utils.OnRequestSuccess;
import com.jhcms.common.utils.Utils;
import com.jhcms.common.widget.RoundImageView;
import com.jhcms.mall.dialog.GuiGeDialog;
import com.jhcms.mall.dialog.ShareDialog;
import com.jhcms.mall.model.BaseResponse;
import com.jhcms.mall.model.CommentInfoModel;
import com.jhcms.mall.model.ImageInfoModel;
import com.jhcms.mall.model.ProductDetailModel;
import com.jhcms.mall.model.ProductInfoModel;
import com.jhcms.mall.model.ShareInfoModel;
import com.jhcms.mall.model.SpecificaitonDetailModel;
import com.jhcms.mall.model.SpecificationInfoModel;
import com.jhcms.waimaiV3.R;
import com.jhcms.waimaiV3.activity.WebViewActivity;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 作者：WangWei
 * 描述：商品详情界面
 */
public class MallProductDetailActivity extends BaseActivity {
    private static final String PRODUCT_ID = "productId";
    private static final int ALL_TYPE = 0;
    private static final int MANYI_TYPE = 1;
    private static final int BUMANYI_TYPE = 2;
    private static final int PICTURE_TYPE = 3;
    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.iv_share)
    ImageView ivShare;


    @Bind(R.id.iv_home)
    ImageView ivHome;
    @Bind(R.id.iv_cart)
    ImageView ivCart;
    @Bind(R.id.tv_call)
    TextView tvCall;
    @Bind(R.id.tv_shop)
    TextView tvShop;
    @Bind(R.id.tv_collect)
    TextView tvCollect;
    @Bind(R.id.tv_add_car)
    TextView tvAddCar;
    @Bind(R.id.tv_buy)
    TextView tvBuy;
    @Bind(R.id.activity_product_detail)
    LinearLayout activityProductDetail;

    @Bind(R.id.tv_product_name)
    TextView tvProductName;
    @Bind(R.id.tv_youhui_tag)
    TextView tvYouhuiTag;
    @Bind(R.id.tv_youhui_condition)
    TextView tvYouhuiCondition;
    @Bind(R.id.tv_get_coupon)
    TextView tvGetCoupon;
    @Bind(R.id.tv_coupon)
    TextView tvCoupon;
    @Bind(R.id.ll_coupon)
    LinearLayout llCoupon;
    @Bind(R.id.tv_guige)
    TextView tvGuige;
    @Bind(R.id.ll_guige)
    LinearLayout llGuige;
    @Bind(R.id.iv_shop_logo)
    RoundImageView ivShopLogo;
    @Bind(R.id.tv_shop_name)
    TextView tvShopName;
    @Bind(R.id.fm_come_shop)
    FrameLayout fmComeShop;
    @Bind(R.id.iv_product)
    ImageView ivProduct;
    @Bind(R.id.tv_price)
    TextView tvPrice;
    @Bind(R.id.tv_yunfei)
    TextView tvYunfei;
    @Bind(R.id.tv_shenyu)
    TextView tvShenyu;
    @Bind(R.id.tv_sale_num)
    TextView tvSaleNum;
    @Bind(R.id.tv_focus_num)
    TextView tvFocusNum;
    @Bind(R.id.wv_detail)
    WebView wvDetail;
    private ArrayList<ImageInfoModel> bannerData;
    private int mProductId;
    private int currentPage;
    private int currentType;
    private ProductInfoModel.ShopInfo shopInfo;
    private List<CommentInfoModel> commentData;
    private boolean isInit = false;
    private HashMap<String, SpecificaitonDetailModel> attrstocks;
    private ProductInfoModel detail;
    private GuiGeDialog guiGeDialog;
    //选择的商品数
    private String selectNum;
    //选择的规格信息，可能为null
    private SpecificaitonDetailModel specificaitonDetailModel;
    //是否有规格信息,记录当前是否收藏,标记是否选择过规格
    private boolean hasGuiGeInfo, collcetStatus, isSelectedGuige;

    public static Intent generateIntent(Context context, int productId) {
        Intent intent = new Intent(context, MallProductDetailActivity.class);
        intent.putExtra(PRODUCT_ID, productId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initData() {
        commentData = new ArrayList<>();
        mProductId = getIntent().getIntExtra(PRODUCT_ID, -1);
        currentPage = 1;
        currentType = ALL_TYPE;
        requestData(mProductId, currentPage, currentType);

    }

    /**
     * @param productId 商品id
     * @param page      页数
     * @param type      评论类型
     */
    private void requestData(int productId, int page, int type) {
        String paramData = null;
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("product_id", productId);
            jsonObject.put("page", page);
            jsonObject.put("type", type);
            paramData = jsonObject.toString();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, getString(R.string.mall_程序出错), Toast.LENGTH_SHORT).show();
            return;
        }
        HttpUtils.postUrlWithBaseResponse(this, Api.MALL_PRODUCT_DETAIL, paramData, true, new OnRequestSuccess<BaseResponse<ProductDetailModel>>() {
            @Override
            public void onSuccess(String url, BaseResponse<ProductDetailModel> data) {
                super.onSuccess(url, data);
                try {
                    initBaseData(data.getData());

                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(MallProductDetailActivity.this, getString(R.string.mall_程序出错), Toast.LENGTH_SHORT).show();
                    return;
                }
            }

        });
    }

    /**
     * 初始化基础数据
     *
     * @param data 商品详情
     */
    private void initBaseData(ProductDetailModel data) throws Exception {
        detail = data.getDetail();
        tvTitle.setText(detail.getTitle());
        Utils.LoadStrPicture(this, Api.IMAGE_URL + detail.getPhoto(), ivProduct);
        tvProductName.setText(detail.getTitle());
        if (!TextUtils.isEmpty(detail.getWei_price())) {
            tvPrice.setText(getString(R.string.mall_¥f, detail.getWei_price()));
        }
        tvYunfei.setText(getString(R.string.mall_运费, "0.00"));
        String stock = null;
        if (!TextUtils.isEmpty(detail.getStock())) {
            stock = detail.getStock();
        } else {
            stock = "0";
        }
        tvShenyu.setText(getString(R.string.mall_剩余, stock));
        String sales = null;
        if (!TextUtils.isEmpty(detail.getSales())) {
            sales = detail.getSales();
        } else {
            sales = "0";
        }
        if ("1".equals(detail.getCollect())) {
            switchCollectType(true);
        } else {
            switchCollectType(false);
        }
        tvSaleNum.setText(getString(R.string.mall_销量format, sales));
        shopInfo = detail.getShop();
        Utils.LoadStrPicture(this, Api.IMAGE_URL + shopInfo.getLogo(), ivShopLogo);
        tvShopName.setText(shopInfo.getTitle());
        tvFocusNum.setText(shopInfo.getCollect());
        attrstocks = detail.getAttrstocks();
        wvDetail.loadUrl(detail.getLink());
    }


//    /**
//     * 将评论数据逆序加入集合中
//     *
//     * @param comments
//     */
//    public void addCommentData(List<CommentInfoModel> comments) {
//        for (int i = comments.size() - 1; i >= 0; i--) {
//            commentData.add(comments.get(i));
//        }
//    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_product_detail);
        ButterKnife.bind(this);
        wvDetail.setWebViewClient(new WebViewClient());
        wvDetail.setWebChromeClient(new WebChromeClient());
        wvDetail.getSettings().setJavaScriptEnabled(true);
    }


    @OnClick({R.id.iv_back, R.id.iv_share, R.id.tv_call, R.id.tv_shop
            , R.id.tv_collect, R.id.tv_add_car, R.id.tv_buy
            , R.id.fm_come_shop, R.id.ll_guige, R.id.ll_coupon})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.iv_share:
                showShareDialog();
                break;

            case R.id.ll_coupon:
                if (detail == null) {
                    return;
                }
                Intent intent = new Intent(this, WebViewActivity.class);
                intent.putExtra(WebViewActivity.URL, detail.getCoupon_url());
                startActivity(intent);
                break;
            case R.id.tv_call:
                if (shopInfo != null)
                    callPhone(shopInfo.getPhone());
                else
                    Toast.makeText(this, getString(R.string.mall_没有商家联系方式), Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_shop:
                jinDian();
                break;
            case R.id.tv_collect:
                if (TextUtils.isEmpty(Api.TOKEN)) {
                    Utils.GoLogin(this);
                    return;
                }
                requestCollect(mProductId, !collcetStatus);
                break;
            case R.id.tv_add_car:
                if (!isSelectedGuige) {
                    Toast.makeText(this, getString(R.string.mall_请选择需要购买的规格), Toast.LENGTH_SHORT).show();
                    return;
                }
                if ("0".equals(selectNum)) {
                    Toast.makeText(this, getString(R.string.mall_库存不够), Toast.LENGTH_SHORT).show();
                    return;
                }
                if (shopInfo == null) {
                    return;
                }
                String productInfo2 = buildProductInfoForCar();
                if (TextUtils.isEmpty(productInfo2)) {
                    return;
                }
                requestAddCar(shopInfo.getShop_id(), productInfo2);

                break;
            case R.id.tv_buy:
                if (!isSelectedGuige) {
                    Toast.makeText(this, getString(R.string.mall_请选择需要购买的规格), Toast.LENGTH_SHORT).show();
                    return;
                }

                if ("0".equals(selectNum)) {
                    Toast.makeText(this, getString(R.string.mall_库存不够), Toast.LENGTH_SHORT).show();
                    return;
                }
                String productInfo = buildProductInfo();
                if (TextUtils.isEmpty(productInfo)) {
                    return;
                }
                startActivity(MallConfirmOrderActivity.generateIntent(this, productInfo));
                break;
            case R.id.ll_guige:
                showGuiGeDialog();
                break;

            case R.id.fm_come_shop:
                jinDian();
                break;
        }
    }

    /**
     * 加入购物车
     *
     * @param shop_id
     * @param productInfo
     */
    private void requestAddCar(String shop_id, String productInfo) {
        String paramData = null;
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("shop_id", Integer.parseInt(shop_id));
            jsonObject.put("cart", productInfo);
            paramData = jsonObject.toString();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, getString(R.string.mall_程序出错), Toast.LENGTH_SHORT).show();
            return;
        }
        Log.e(TAG, "requestAddCar: " + paramData);
        HttpUtils.postUrlWithBaseResponse(this, Api.MALL_ADDCART, paramData, true, new OnRequestSuccess<BaseResponse<String>>() {
            @Override
            public void onSuccess(String url, BaseResponse<String> data) {
                super.onSuccess(url, data);
                Toast.makeText(MallProductDetailActivity.this, getString(R.string.mall_添加成功), Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 拼接商品信息，用于订单预处理
     */
    private String buildProductInfo() {
        if (shopInfo == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(shopInfo.getShop_id() + "@");
        if (mProductId == -1) {
            return null;
        }
        sb.append(mProductId);
        if (hasGuiGeInfo) {
            sb.append("-" + specificaitonDetailModel.getStock_name());
        } else {
            sb.append("-0");
        }
        sb.append(":" + selectNum);
        String productInfo = sb.toString();
        Log.e(TAG, "buildProductInfo: " + productInfo);
        return productInfo;
    }

    //拼接购物车信息
    private String buildProductInfoForCar() {
        if (shopInfo == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        if (mProductId == -1) {
            return null;
        }
        sb.append(mProductId);
        if (hasGuiGeInfo) {
            sb.append("-" + specificaitonDetailModel.getStock_name());
        } else {
            sb.append("-0");
        }
        sb.append(":" + selectNum);
        String productInfo = sb.toString();
        Log.e(TAG, "buildProductInfo: " + productInfo);
        return productInfo;
    }

    /**
     * 规格dialog
     */
    private void showGuiGeDialog() {
        if (guiGeDialog == null) {

            guiGeDialog = new GuiGeDialog();
            List<SpecificationInfoModel> attrgroups = detail.getAttrgroups();
            HashMap<String, SpecificaitonDetailModel> attrstocks = detail.getAttrstocks();
            if (attrgroups != null && attrgroups.size() > 0) {
                guiGeDialog.setData(attrgroups, attrstocks);
            }
            guiGeDialog.setOutCanclable(true);
            guiGeDialog.setBasicInfo(detail.getWei_price(), detail.getStock(), detail.getPhoto());
            guiGeDialog.setOnSelectListener(((hasGuiGe, num, values) -> {
                this.hasGuiGeInfo = hasGuiGe;
                isSelectedGuige = true;
                selectNum = num;
                specificaitonDetailModel = values;
                if (hasGuiGe) {
                    tvGuige.setText(specificaitonDetailModel.getStock_real_name());
                }
                //标记是否选择过规格
                tvGuige.setTag(true);
            }));
        }
        guiGeDialog.show(getSupportFragmentManager(), "guige");
    }

    /**
     * 切换收藏按钮显示
     *
     * @param isCollect
     */
    private void switchCollectType(boolean isCollect) {
        int dimension = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 22, getResources().getDisplayMetrics());
        Rect bound = new Rect(0, 0, dimension, dimension);
        Drawable drawable = null;
        if (isCollect) {
            drawable = getResources().getDrawable(R.mipmap.mall_btn_collect_yes);
        } else {
            drawable = getResources().getDrawable(R.mipmap.mall_btn_collect);
        }
        drawable.setBounds(bound);
        tvCollect.setCompoundDrawables(null, drawable, null, null);
        collcetStatus = isCollect;
    }

    private void showShareDialog() {
        if (detail == null || detail.getShare_info() == null) {
            return;
        }
        ShareInfoModel share_info = detail.getShare_info();
        ShareDialog shareDialog = new ShareDialog();
        shareDialog.setShareModel(share_info);
        shareDialog.setOutCanclable(true);
        shareDialog.show(getSupportFragmentManager(), "dialog");
    }

    /**
     * 进店
     */
    private void jinDian() {
        try {
            int shopId = Integer.parseInt(shopInfo.getShop_id());
            Intent intent = MallShopDetailsActivity.generateIntent(this, shopId);
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, getString(R.string.mall_程序出错), Toast.LENGTH_SHORT).show();
            return;
        }
    }

    private void callPhone(String phoneNum) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNum));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    /**
     * 收藏商品
     *
     * @param productID
     * @param isCollect
     */
    private void requestCollect(int productID, boolean isCollect) {
        String paramData = null;
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("type", "weidian_product");
            jsonObject.put("status", isCollect ? "1" : "0");
            jsonObject.put("can_id", productID);
            paramData = jsonObject.toString();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, getString(R.string.mall_程序出错), Toast.LENGTH_SHORT).show();
            return;
        }
        HttpUtils.postUrlWithBaseResponse(this, Api.MALL_COLLECT, paramData, true, new OnRequestSuccess<BaseResponse<String>>() {
            @Override
            public void onSuccess(String url, BaseResponse<String> data) {
                super.onSuccess(url, data);
                Toast.makeText(MallProductDetailActivity.this, data.getMessage(), Toast.LENGTH_SHORT).show();
                switchCollectType(isCollect);
            }
        });

    }


}
