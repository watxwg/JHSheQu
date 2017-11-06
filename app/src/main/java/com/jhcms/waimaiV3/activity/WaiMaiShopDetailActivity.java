package com.jhcms.waimaiV3.activity;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.classic.common.MultipleStatusView;
import com.coorchice.library.SuperTextView;
import com.flipboard.bottomsheet.BottomSheetLayout;
import com.google.gson.Gson;
import com.jhcms.common.listener.PermissionListener;
import com.jhcms.common.model.Data_WaiMai_ShopDetail;
import com.jhcms.common.model.Response_WaiMai_ShopDetail;
import com.jhcms.common.model.ShopDetail;
import com.jhcms.common.utils.Api;
import com.jhcms.common.utils.DividerListItemDecoration;
import com.jhcms.common.utils.HttpUtils;
import com.jhcms.common.utils.OnRequestSuccessCallback;
import com.jhcms.common.utils.StatusBarUtil;
import com.jhcms.common.utils.ToastUtil;
import com.jhcms.common.utils.Utils;
import com.jhcms.common.widget.ListenerScrollView;
import com.jhcms.waimaiV3.R;
import com.jhcms.waimaiV3.adapter.MinatoAdapter;
import com.jhcms.waimaiV3.adapter.SelectAdapter;
import com.jhcms.waimaiV3.dialog.CallDialog;
import com.jhcms.waimaiV3.model.Goods;
import com.jhcms.waimaiV3.model.MessageEvent;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.jhcms.common.utils.Utils.saveCrashInfo2File;

/**
 * Created by Wyj on 2017/5/9
 * TODO:商品详情
 */
public class WaiMaiShopDetailActivity extends SwipeBaseActivity implements OnRequestSuccessCallback {

    /**
     * 商品详情标记
     */
    public static String SHOP_DETAIL = "SHOP_DETAIL";
    /**
     * 该商品的goods字段标记
     */
    public static String GOODS_ITEM = "GOODS_ITEM";
    /**
     * 凑一凑标记
     */
    public static String SHOP_MINATO = "SHOP_MINATO";

    @Bind(R.id.iv_comm_pic)
    ImageView ivCommPic;
    @Bind(R.id.tv_comm_title)
    TextView tvCommTitle;
    @Bind(R.id.tv_comment_price)
    TextView tvCommentPrice;
    @Bind(R.id.tv_comment_sales)
    TextView tvCommentSales;
    @Bind(R.id.tvMinus)
    TextView tvMinus;
    @Bind(R.id.count)
    TextView count;
    @Bind(R.id.tvAdd)
    TextView tvAdd;
    @Bind(R.id.tv_shop_name)
    TextView tvShopName;
    @Bind(R.id.iv_shop_call)
    ImageView ivShopCall;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.tvCost)
    TextView tvCost;
    @Bind(R.id.tvTips)
    TextView tvTips;
    @Bind(R.id.tvSubmit)
    TextView tvSubmit;
    @Bind(R.id.ll_bottom)
    LinearLayout llBottom;
    @Bind(R.id.imgCart)
    ImageView imgCart;
    @Bind(R.id.tvCount)
    TextView tvCount;
    @Bind(R.id.rl_shopCart)
    RelativeLayout rlShopCart;
    @Bind(R.id.bottomFormatSheetLayout)
    BottomSheetLayout formatLayout;
    @Bind(R.id.scroll_view)
    ListenerScrollView scrollView;
    @Bind(R.id.progressBar)
    ProgressBar progressBar;
    @Bind(R.id.content_view)
    RelativeLayout containerLayout;
    @Bind(R.id.tv_good)
    TextView tvGood;
    @Bind(R.id.tv_bad)
    TextView tvBad;
    @Bind(R.id.bottomShopCartSheetLayout)
    BottomSheetLayout shopCartLayout;
    @Bind(R.id.multiplestatusview)
    MultipleStatusView multiplestatusview;
    @Bind(R.id.tv_good_rate)
    TextView tvGoodRate;
    @Bind(R.id.ll_normal)
    LinearLayout llNormal;
    @Bind(R.id.tv_guige)
    SuperTextView tvGuige;
    @Bind(R.id.tv_guige_num)
    SuperTextView tvGuigeNum;
    @Bind(R.id.ll_guige)
    LinearLayout llGuige;
    @Bind(R.id.iv_good)
    ImageView ivGood;
    @Bind(R.id.iv_bad)
    ImageView ivBad;
    @Bind(R.id.ll_sold_out)
    LinearLayout llSoldOut;
    @Bind(R.id.iv_coucou)
    ImageView ivCoucou;
    @Bind(R.id.minatoSheetLayout)
    BottomSheetLayout minatoSheetLayout;
    private Handler mHanlder;
    /**
     * 购物车数据
     */
    private SparseArray<Goods> selectedList;
    private SparseArray<Goods> specList;
    private SparseIntArray groupSelect;
    private ImageView ivFormatPic;
    private TextView tvFormatPrice;
    private ImageView ivFormatClose;
    private TextView tvFormatSold;
    private TextView tvFormatGood;
    private TextView tvFormatSelected;

    private TextView tvFormatMinus;
    private TextView tvFormatCount;
    private TextView tvFormatAdd;


    private String SHOPCART = "shopcart";
    private String FORMAT = "format";
    private View formatBottomSheet;
    private RecyclerView rvSelected;
    private ImageView coucou;
    private SelectAdapter selectAdapter;

    private String[] mVals;
    /**
     * 商品Id
     */
    private String product_id;
    private Response_WaiMai_ShopDetail productDetail;
    private Data_WaiMai_ShopDetail.DetailEntity detail;
    // 是否正在放大
    private Boolean mScaling = false;
    // 记录首次按下位置
    private float mFirstPosition = 0;
    private DisplayMetrics metric;
    private String phone;
    private List<String> mValsList;
    private List<Data_WaiMai_ShopDetail.DetailEntity.SpecsEntity> specsEntityList;
    /**
     * 好评数量
     */
    private String good;
    /**
     * 购物车中该商品数量
     */
//    private int goods_number;
    /**
     * 商品的总价格
     */
    private double totalAmount;

    private NumberFormat nf;
    /**
     * 购物车商品总数量
     */
    private int comCount;
    private Goods item;
    private ShopDetail shopDetails;
    private int typeNum;
    /**
     * 间隔线
     */
    private DividerListItemDecoration divider;
    /**
     * 商品规格集合
     */
    private List<ShopDetail.ItemsEntity.ProductsEntity.SpecsEntity> entityList;
    /**
     * 商品集合
     */
    private ArrayList<Goods> goodList;
    /**
     * 商品数量
     */
    private int countById;

    private int type_Id;
    private ArrayList<Goods> couGoodsList;
    /**
     * 判断购物车是否处于显示状态
     */
    private boolean isShowShopCart = false;
    /**
     * 添加规格商品时的mode
     */
    private Goods addTemp;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_waimai_shop_detail);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        /**
         * 设置商品图片大小
         * */
        // 获取屏幕宽高
        metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        // 设置图片初始大小 这里我设为满屏的375:130
        ViewGroup.LayoutParams lp = ivCommPic.getLayoutParams();
        lp.width = metric.widthPixels;
        lp.height = metric.widthPixels * 10 / 13;
        ivCommPic.setLayoutParams(lp);
        StatusBarUtil.immersive(this);
        StatusBarUtil.setPaddingSmart(this, toolbar);
        /*getCurrencyInstance 返回当前缺省语言环境的通用格式*/
        nf = NumberFormat.getCurrencyInstance();
        /*设置数值的小数部分允许的最大位数为2*/
        nf.setMaximumFractionDigits(2);
        /**
         * 获取数据
         * */
        shopDetails = (ShopDetail) getIntent().getSerializableExtra(SHOP_DETAIL);
        couGoodsList = (ArrayList<Goods>) getIntent().getSerializableExtra(SHOP_MINATO);
        item = (Goods) getIntent().getSerializableExtra(GOODS_ITEM);


        product_id = item.product_id;


        selectedList = WaiMaiShopActivity.getSelectedList();
        specList = WaiMaiShopActivity.getSpecList();
        groupSelect = WaiMaiShopActivity.getGroupSelect();


        entityList = item.productsEntity.specs;
        goodList = new ArrayList<>();

        if (item.is_spec.equals("1")) {
            for (int i = 0; i < entityList.size(); i++) {
                Goods goods = new Goods();
                goods.setBad(item.bad);
                goods.setGood(item.good);
                goods.setIs_spec(item.is_spec);
                goods.setPrice(entityList.get(i).price);
                goods.setProduct_id(item.product_id + entityList.get(i).spec_id);
                goods.setProductId(item.product_id);
                goods.setProductsEntity(item.productsEntity);
                goods.setPagePrice(entityList.get(i).package_price);
                goods.setSale_sku(entityList.get(i).sale_sku);
                goods.setShop_id(item.shop_id);
                goods.setLogo(entityList.get(i).spec_photo);
                goods.setSpec_id(entityList.get(i).spec_id);
                goods.setName(item.productsEntity.title + "(" + entityList.get(i).spec_name + ")");
                goods.setTypeId(item.typeId);
                goodList.add(goods);
            }
        }

        update();


        mHanlder = new Handler(getMainLooper());

        divider = new DividerListItemDecoration.Builder(this)
                .setHeight(R.dimen.dp_1)
                .setColorResource(R.color.background)
                .build();


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeActivity();
            }
        });
        toolbar.getBackground().mutate().setAlpha(0);
        scrollView.setOnChangeListener(new ListenerScrollView.onScrollChanged() {
            @Override
            public void onChanged(int l, int t, int oldl, int oldt) {
                int height = ivCommPic.getHeight() / 2;
                if (t <= height && t >= 0) {
                    float scale = (float) t / height;
                    float alpha = (255 * scale);
                    toolbar.getBackground().mutate().setAlpha((int) alpha);
                } else if (t > height) {
                    toolbar.getBackground().mutate().setAlpha(255);
                }
            }
        });
        scrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ViewGroup.LayoutParams lp = ivCommPic
                        .getLayoutParams();
                switch (event.getAction()) {
                    case MotionEvent.ACTION_UP:
                        // 手指离开后恢复图片
                        mScaling = false;
                        replyImage();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        if (!mScaling) {
                            if (scrollView.getScrollY() == 0) {
                                mFirstPosition = event.getY();// 滚动到顶部时记录位置，否则正常返回
                            } else {
                                break;
                            }
                        }
                        int distance = (int) ((event.getY() - mFirstPosition) * 0.6); // 滚动距离乘以一个系数
                        if (distance < 0) { // 当前位置比记录位置要小，正常返回
                            break;
                        }
                        // 处理放大
                        mScaling = true;
                        lp.width = metric.widthPixels + distance;
                        lp.height = (metric.widthPixels + distance) * 10 / 13;
                        ivCommPic.setLayoutParams(lp);
                        return true; // 返回true表示已经完成触摸事件，不再处理
                }
                return false;
            }
        });


        requestProduct(Api.WAIMAI_SHOP_PRODUCT, product_id);

    }

    private void requestProduct(String api, String product_id) {
        JSONObject object = new JSONObject();
        try {
            object.put("product_id", product_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String str = object.toString();
        HttpUtils.postUrl(this, api, str, false, this);

    }

    public void add(Goods item) {
        if (shopDetails.items != null && shopDetails.items.size() > 0 && shopDetails.items.get(0).cate_id.equals("hot")) {
            if (item.typeId == 0) {
                for (int i = 1; i < shopDetails.items.size(); i++) {
                    for (int j = 0; j < shopDetails.items.get(i).products.size(); j++) {
                        if (item.productsEntity.product_id.equals(shopDetails.items.get(i).products.get(j).product_id)) {
                            type_Id = i;
                        }
                    }
                }
            } else {
                type_Id = item.typeId;
            }
        } else {
            type_Id = item.typeId;
        }

        int groupCount = groupSelect.get(type_Id);
        if (groupCount == 0) {
            groupSelect.append(type_Id, 1);
        } else {
            groupSelect.append(type_Id, ++groupCount);
        }

        if (item.is_spec.equals("1")) {
            Goods specTemp = specList.get(Integer.parseInt(item.productId));
            if (specTemp == null) {
                if (Api.GOOD_LIST != null && Api.GOOD_LIST.size() > 0) {
                    for (int i = 1; i < Api.GOOD_LIST.size(); i++) {
                        if (Api.GOOD_LIST.get(i).productId.equals(item.productId)) {
                            Api.GOOD_LIST.get(i).count = 1;
                            specList.append(Integer.parseInt(item.productId), Api.GOOD_LIST.get(i));
                        }
                    }
                }

            } else {
                specTemp.count++;
            }
        }


        Goods temp = selectedList.get(Integer.parseInt(item.product_id));
        if (temp == null) {
            item.count = 1;
            selectedList.append(Integer.parseInt(item.product_id), item);
            totalAmount += Double.parseDouble(item.price);
        } else {
            temp.count++;
            totalAmount += Double.parseDouble(temp.price);
        }
        update();
    }

    //移除商品
    public void remove(Goods item) {

        if (shopDetails.items != null && shopDetails.items.size() > 0 && shopDetails.items.get(0).cate_id.equals("hot")) {
            if (item.typeId == 0) {
                for (int i = 1; i < shopDetails.items.size(); i++) {
                    for (int j = 0; j < shopDetails.items.get(i).products.size(); j++) {
                        if (item.productsEntity.product_id.equals(shopDetails.items.get(i).products.get(j).product_id)) {
                            type_Id = i;
                        }
                    }
                }
            } else {
                type_Id = item.typeId;
            }
        } else {
            type_Id = item.typeId;
        }

        int groupCount = groupSelect.get(type_Id);
        if (groupCount == 1) {
            groupSelect.delete(type_Id);
        } else if (groupCount > 1) {
            groupSelect.append(type_Id, --groupCount);
        }

        Goods temp = selectedList.get(Integer.parseInt(item.product_id));
        if (temp != null) {
            if (temp.count < 2) {
                selectedList.remove(Integer.parseInt(item.product_id));
                totalAmount -= Double.parseDouble(temp.price);
            } else {
                temp.count--;
                totalAmount -= Double.parseDouble(temp.price);
            }
        }

        if (item.is_spec.equals("1")) {
            Goods specTemp = specList.get(Integer.parseInt(item.productId));
            if (specTemp != null) {
                specTemp.count--;
            }
        }

        update();
    }

    private void update() {
        /*购物车商品数量*/
        int size = selectedList.size();

        comCount = 0;

        totalAmount = 0;

        for (int i = 0; i < size; i++) {
            Goods items = selectedList.valueAt(i);
            comCount += items.count;
            totalAmount += items.count * Double.parseDouble(items.price);
        }


        /*商品的总数量*/
        if (comCount < 1) {
            /*购物车数量*/
            tvCount.setVisibility(View.GONE);
        } else {
            tvCount.setVisibility(View.VISIBLE);
            tvCount.setText(String.valueOf(comCount));
        }

        /*处理规格角标数量*/
        int specCount = WaiMaiShopActivity.getSpecItemCountById(Integer.parseInt(item.productsEntity.product_id));
        Goods specGoods = specList.get(Integer.parseInt(item.productsEntity.product_id));
        if (specGoods != null) {
        }
        if (specCount == 0) {
            tvGuigeNum.setVisibility(View.GONE);
        } else {
            tvGuigeNum.setVisibility(View.VISIBLE);
            tvGuigeNum.setText(String.valueOf(specCount));
        }



        /*购物车数量*/
        tvCount.setText(String.valueOf(comCount));
        /*该商品数量*/

        /*处理带规格的*/
        if (item.is_spec.equals("1") && specsEntity != null) {
            countById = getSelectedItemCountById(Integer.parseInt(item.product_id + specsEntity.spec_id));
            tvFormatCount.setText(String.valueOf(countById));
        } else {
            /*不带规格的*/
            countById = getSelectedItemCountById(Integer.parseInt(item.product_id));
            count.setText(String.valueOf(countById));
        }
        if (countById < 1) {
            tvMinus.setVisibility(View.GONE);
            tvMinus.setEnabled(false);
            count.setVisibility(View.GONE);
            if (formatLayout.isSheetShowing()) {
                tvFormatMinus.setEnabled(false);
            }
        } else {
            if (formatLayout.isSheetShowing()) {
                tvFormatMinus.setEnabled(true);
            }
            tvMinus.setVisibility(View.VISIBLE);
            tvMinus.setEnabled(true);
            count.setVisibility(View.VISIBLE);
        }
        dealWithPagePrice();

        if (selectAdapter != null) {
            selectAdapter.notifyDataSetChanged();
        }
        if (minatoAdapter != null) {
            minatoAdapter.notifyDataSetChanged();
        }

        if (shopCartLayout.isSheetShowing() && selectedList.size() < 1) {
            shopCartLayout.dismissSheet();
        }
        /*更新ShopActivity中的商品数据标志*/
        EventBus.getDefault().post(new MessageEvent(WaiMaiShopActivity.REFRESH_GOODS));
        EventBus.getDefault().post(new MessageEvent(WaiMaiInStoreSearchActivity.REFRESH_GOODS));
    }

    /**
     * 打包费
     */
    private double pagePrice;

    private void dealWithPagePrice() {
        double v = 0;
        for (int i = 0; i < selectedList.size(); i++) {
            Goods item = selectedList.valueAt(i);
            v += item.count * Double.parseDouble(item.pagePrice);
        }
        if (v != 0) {
            if (tvPagePrice != null) {
                tvPagePrice.setText("￥" + v);
            }
        }
        pagePrice = v;
         /*总金额*/
        tvCost.setText(nf.format(totalAmount + v));


        if (shopCartLayout != null && shopCartLayout.isSheetShowing()) {
            ivCoucou.setVisibility(View.GONE);
            if (isShowCouCou()) {
                coucou.setVisibility(View.GONE);
            } else {
                coucou.setVisibility(View.VISIBLE);
            }
        } else if (minatoSheetLayout != null && minatoSheetLayout.isSheetShowing()) {
            ivCoucou.setVisibility(View.GONE);
        } else {
            if (isShowCouCou()) {
                ivCoucou.setVisibility(View.GONE);
            } else {
                ivCoucou.setVisibility(View.VISIBLE);
            }
        }


        /**
         * 商家未打烊状态
         * */
        if ("1".equals(shopDetails.yysj_status) && "1".equals(shopDetails.yy_status)) {
            if (totalAmount > 0 && (totalAmount + v) >= Double.parseDouble(shopDetails.min_amount)) {
                tvTips.setVisibility(View.GONE);
                tvSubmit.setVisibility(View.VISIBLE);
            } else {
                tvSubmit.setVisibility(View.GONE);
                tvTips.setVisibility(View.VISIBLE);
                /**
                 * 处理还差多少钱起送
                 * */
                if (totalAmount != 0 && Double.parseDouble(shopDetails.min_amount) > (totalAmount + v)) {
                    tvTips.setText("差" + nf.format(Double.parseDouble(shopDetails.min_amount) - totalAmount - v) + "起送");
                } else {
                    tvTips.setText("￥" + shopDetails.min_amount + "元起送");
                }
            }
        } else {
            tvTips.setText("已打烊");
        }
    }


    //根据商品id获取当前商品的采购数量
    public int getSelectedItemCountById(int id) {
        Goods temp = selectedList.get(id);
        if (temp == null) {
            return 0;
        }
        return temp.count;
    }

    @OnClick({R.id.tvMinus, R.id.tvAdd, R.id.tv_guige, R.id.iv_shop_call, R.id.iv_coucou,/*R.id.iv_good, R.id.iv_bad, */R.id.tv_shop_name, R.id.tvSubmit, R.id.ll_bottom, R.id.rl_shopCart})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvMinus:
                if (countById < 2) {
                    tvMinus.setAnimation(getHiddenAnimation());
                    tvMinus.setVisibility(View.GONE);
                    tvMinus.setEnabled(false);
                    count.setVisibility(View.GONE);
                }
                comCount--;
                if (comCount < 1) {
                    tvCount.setVisibility(View.GONE);
                }
                remove(item);
                break;
            case R.id.tvAdd:
                countById++;
                if (countById > detail.sale_sku) {
                    ToastUtil.show("库存不足");
                    countById--;
                    return;
                }
                if (countById < 2) {
                    tvMinus.setAnimation(getShowAnimation());
                    tvMinus.setVisibility(View.VISIBLE);
                    tvMinus.setEnabled(true);
                    tvCount.setVisibility(View.VISIBLE);
                    count.setVisibility(View.VISIBLE);
                }
                comCount++;
                add(item);
                int[] loc = new int[2];
                view.getLocationInWindow(loc);
                playAnimation(loc);
                break;
            case R.id.iv_coucou:
                showMinato();
                break;
            /*购物车*/
            case R.id.ll_bottom:
            /*购物车图标*/
            case R.id.rl_shopCart:
                if (minatoSheetLayout != null && minatoSheetLayout.isSheetShowing()) {
                    minatoSheetLayout.dismissSheet();
                    isHindeCou = true;
                }
                showShopCart();
                break;
            case R.id.iv_shop_call:
                SwipeBaseActivity.requestRuntimePermission(new String[]{Manifest.permission.CALL_PHONE}, new PermissionListener() {
                    @Override
                    public void onGranted() {
                        new CallDialog(WaiMaiShopDetailActivity.this)
                                .setMessage(phone)
                                .setTipTitle("拨打商家电话")
                                .setPositiveButton("确定", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent intent = new Intent(Intent.ACTION_CALL, Uri
                                                .parse("tel:" + phone));
                                        if (ActivityCompat.checkSelfPermission(WaiMaiShopDetailActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                            return;
                                        }
                                        startActivity(intent);
                                    }
                                })
                                .setNegativeButton("取消", null)
                                .show();
                    }

                    @Override
                    public void onDenied(List<String> permissions) {
                        Utils.showMissingPermissionDialog(WaiMaiShopDetailActivity.this);
                    }
                });
                break;
            /*规格*/
            case R.id.tv_guige:
                showFormatBottomSheet();
                break;
            case R.id.tvSubmit:
                if (TextUtils.isEmpty(Api.TOKEN)) {
                    Utils.GoLogin(this);
                } else {
                    Intent intent = new Intent();
                    List<Goods> arrayList = new ArrayList<>();
                    for (int i = 0; i < selectedList.size(); i++) {
                        arrayList.add(selectedList.valueAt(i));
                    }
                    intent.setClass(this, ConfirmOrderActivity.class);
                    intent.putExtra(ConfirmOrderActivity.SHOP_CART, (Serializable) arrayList);
                    intent.putExtra(ConfirmOrderActivity.SHOP_ID, detail.shop_id);
                    intent.putExtra(ConfirmOrderActivity.PEI_TYPE, shopDetails.pei_type);
                    startActivity(intent);
                }
                break;

        }
    }

    /**
     * 凑一凑 的显示状态 isShowCou
     * <p>
     * 消失的时候 判断是否显示 ivCoucou凑一凑
     * 显示的时候 让ivCoucou凑一凑 隐藏
     */
    private View minatoSheet;
    /**
     * 判断凑一凑 是否正在显示
     */
    private boolean isShowCou = false;

    private void showMinato() {
        if (minatoSheet == null) {
            minatoSheet = creatMinatoView();
        }
        if (minatoSheetLayout.isSheetShowing()) {
            minatoSheetLayout.dismissSheet();
            isShowCou = false;
        } else {
            minatoSheetLayout.showWithSheetView(minatoSheet);
            isShowCou = true;
            minatoSheetLayout.addOnSheetStateChangeListener(new BottomSheetLayout.OnSheetStateChangeListener() {
                @Override
                public void onSheetStateChanged(BottomSheetLayout.State state) {
                        /*formatLayout 消失*/
                    if (state.toString().equals("HIDDEN")) {
                        isShowCou = false;
                        if (isHindeCou) {
                            ivCoucou.setVisibility(View.GONE);
                            isHindeCou = false;
                        } else {
                            if (!isShowCouCou()) {
                                ivCoucou.setVisibility(View.VISIBLE);
                            } else {
                                ivCoucou.setVisibility(View.GONE);
                            }
                        }
                    } else if (state.toString().equals("PEEKED")) {
                        /*formatLayout 展开*/
                        ivCoucou.setVisibility(View.GONE);
                    }
                }
            });
        }
    }

    /**
     * 购物车金额是否大于起送金额
     * 大于？凑凑按钮隐藏
     * 小于？凑凑按钮显示
     * true 隐藏
     *
     * @return
     */
    public boolean isShowCouCou() {
        if (shopDetails.min_amount.equals("0")) {
            return true;
        } else if ((totalAmount + pagePrice) < Double.parseDouble(shopDetails.min_amount) && (totalAmount + pagePrice) >= Double.parseDouble(shopDetails.min_amount) / 2) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 凑一凑视图
     *
     * @return
     */
    private RecyclerView rvMinato;
    private MinatoAdapter minatoAdapter;

    private View creatMinatoView() {
        View view = LayoutInflater.from(this).inflate(R.layout.layout_minato_sheet, (ViewGroup) getWindow().getDecorView(), false);
        rvMinato = (RecyclerView) view.findViewById(R.id.rv_minato);
        rvMinato.setLayoutManager(new LinearLayoutManager(this));
        rvMinato.addItemDecoration(divider);
        minatoAdapter = new MinatoAdapter(WaiMaiShopActivity.instance, WaiMaiShopDetailActivity.this, couGoodsList);
        rvMinato.setAdapter(minatoAdapter);
        return view;
    }

    private View shopCartView;
    private boolean isHindeCou = false;

    private void showShopCart() {
        if (shopCartView == null) {
            shopCartView = showShopCartBottomSheet();
        }
        if (shopCartLayout.isSheetShowing()) {
            shopCartLayout.dismissSheet();
        } else {
            if (selectedList.size() != 0) {
                shopCartLayout.showWithSheetView(shopCartView);

                shopCartLayout.addOnSheetStateChangeListener(new BottomSheetLayout.OnSheetStateChangeListener() {
                    @Override
                    public void onSheetStateChanged(BottomSheetLayout.State state) {
                        if (state.toString().equals("HIDDEN")) {
                        /*shopCartLayout 消失*/
                            isShowShopCart = false;
                            if (!isShowCouCou()) {
                                ivCoucou.setVisibility(View.VISIBLE);
                            } else {
                                ivCoucou.setVisibility(View.GONE);
                            }
                            if (isHindeCou) {
                                ivCoucou.setVisibility(View.GONE);
                                isHindeCou = false;
                            }
                        } else if (state.toString().equals("PEEKED")) {
                            isShowShopCart = true;
                            dealWithPagePrice();
                            if (isShowCouCou()) {
                                coucou.setVisibility(View.GONE);
                            } else {
                                coucou.setVisibility(View.VISIBLE);
                            }
                            ivCoucou.setVisibility(View.GONE);
                        }
                    }
                });
            }
        }
    }

    //清空购物车
    public void clearCart() {
        selectedList.clear();
        groupSelect.clear();
        specList.clear();
        item.count = 0;
        update();
    }

    private TextView tvPagePrice;

    private View showShopCartBottomSheet() {
        View view = LayoutInflater.from(this).inflate(R.layout.layout_shopcart_sheet, (ViewGroup) getWindow().getDecorView(), false);
        rvSelected = (RecyclerView) view.findViewById(R.id.selectRecyclerView);
        rvSelected.setLayoutManager(new LinearLayoutManager(this));
        rvSelected.addItemDecoration(divider);
        TextView clear = (TextView) view.findViewById(R.id.clear);
        tvPagePrice = (TextView) view.findViewById(R.id.tv_pageprice);
        coucou = (ImageView) view.findViewById(R.id.iv_coucou);
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new CallDialog(WaiMaiShopDetailActivity.this)
                        .setMessage("是否清空已选商品")
                        .setNegativeButton("取消", null)
                        .setPositiveButton("清空", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                clearCart();
                            }
                        })
                        .show();
            }
        });
        coucou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shopCartLayout.dismissSheet();
                isHindeCou = true;
                showMinato();
            }
        });
        selectAdapter = new SelectAdapter(WaiMaiShopDetailActivity.this, selectedList);
        rvSelected.setAdapter(selectAdapter);
        return view;
    }


    private void showFormatBottomSheet() {
        if (formatBottomSheet == null) {
            formatBottomSheet = createGguiGeBottomSheetView();
        }
        if (formatLayout.isSheetShowing()) {
            formatLayout.dismissSheet();
        } else {
            formatLayout.showWithSheetView(formatBottomSheet);
        }
    }

    /**
     * 规格商品布局
     *
     * @return
     */
    private TagAdapter<String> mAdapter;
    private TagFlowLayout mFlowLayout;


    /**
     * 规格View
     *
     * @return
     */
    private View createGguiGeBottomSheetView() {
        View view = LayoutInflater.from(this).inflate(R.layout.layout_bottom_guige_sheet, (ViewGroup) getWindow().getDecorView(), false);
        initView(view);
        final LayoutInflater mInflater = LayoutInflater.from(this);
        mFlowLayout.setAdapter(mAdapter = new TagAdapter<String>(mVals) {

            @Override
            public View getView(FlowLayout parent, int position, String s) {
                TextView tv = (TextView) mInflater.inflate(R.layout.adapter_guige_item,
                        mFlowLayout, false);
                tv.setText(s);
                return tv;
            }
        });
        /**
         * 默认选中第一个
         * */
        if (null != mVals && mVals.length > 0) {
            mAdapter.setSelectedList(0);
            selectTag(0);
        }
        /**
         * 点击标签时的回调
         * */
        mFlowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                selectTag(position);
                return true;
            }
        });


        /*关闭bottomsheet*/
        ivFormatClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (formatLayout != null) {
                    formatLayout.dismissSheet();
                }
            }
        });
        return view;
    }

    /**
     * 选择规格
     *
     * @param position
     */
    private int pos;
    private String specsPrice;
    Data_WaiMai_ShopDetail.DetailEntity.SpecsEntity specsEntity;

    private void selectTag(int position) {
        pos = position;
        specsEntity = specsEntityList.get(position);

        /*名称*/
        tvFormatSelected.setText(mVals[position]);
        /*图片*/
        Utils.LoadStrPicture(WaiMaiShopDetailActivity.this, Api.IMAGE_URL + specsEntity.spec_photo, ivFormatPic);
        /*价格*/
        specsPrice = specsEntity.price;
        tvFormatPrice.setText("￥" + specsPrice);
        /*销量*/
        tvFormatSold.setText(String.format(getString(R.string.已售), specsEntity.sale_count));
        /*赞*/
        tvFormatGood.setText(String.format(getString(R.string.赞), good));
        update();
    }

    private void initView(View view) {
        ivFormatPic = (ImageView) view.findViewById(R.id.iv_format_pic);
        tvFormatPrice = (TextView) view.findViewById(R.id.tv_format_price);
        ivFormatClose = (ImageView) view.findViewById(R.id.iv_format_close);
        tvFormatSold = (TextView) view.findViewById(R.id.tv_format_sold);
        tvFormatGood = (TextView) view.findViewById(R.id.tv_format_good);
        tvFormatSelected = (TextView) view.findViewById(R.id.tv_format_selected);
        mFlowLayout = (TagFlowLayout) view.findViewById(R.id.flowlayout);

        tvFormatMinus = (TextView) view.findViewById(R.id.tvMinus);
        tvFormatCount = (TextView) view.findViewById(R.id.count);
        tvFormatAdd = (TextView) view.findViewById(R.id.tvAdd);
        tvFormatMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                formatRemove(pos);
            }
        });
        tvFormatAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                formatAdd(pos);
            }
        });


    }

    private Goods selectGood;

    private void formatAdd(int position) {
        selectGood = goodList.get(position);
        selectGood.specSelect = position;
        if (shopDetails.items != null && shopDetails.items.size() > 0 && shopDetails.items.get(0).cate_id.equals("hot")) {
            if (selectGood.typeId == 0) {
                for (int i = 1; i < shopDetails.items.size(); i++) {
                    for (int j = 0; j < shopDetails.items.get(i).products.size(); j++) {
                        if (item.productsEntity.product_id.equals(shopDetails.items.get(i).products.get(j).product_id)) {
                            type_Id = i;
                        }
                    }
                }
            } else {
                type_Id = selectGood.typeId;
            }
        } else {
            type_Id = selectGood.typeId;
        }


        addTemp = selectedList.get(Integer.parseInt(item.product_id + specsEntity.spec_id));
        if (addTemp == null) {
            selectGood.count = 1;
            if (selectGood.count > selectGood.sale_sku) {
                ToastUtil.show("库存不足");
                return;
            }
            selectedList.append(Integer.parseInt(item.product_id + specsEntity.spec_id), selectGood);
            totalAmount += Double.parseDouble(specsPrice);
        } else {
            addTemp.count++;
            if (addTemp.count > addTemp.sale_sku) {
                ToastUtil.show("库存不足");
                addTemp.count--;
                return;
            }
            totalAmount += Double.parseDouble(specsPrice);
        }
        /*规格角标*/
        Goods specTemp = specList.get(Integer.parseInt(item.product_id));
        if (specTemp == null) {
            item.count = 1;
            specList.append(Integer.parseInt(item.product_id), item);
        } else {
            specTemp.count++;
        }
        /*商品分类数量*/
        int groupCount = groupSelect.get(type_Id);
        if (groupCount == 0) {
            groupSelect.append(type_Id, 1);
        } else {
            groupSelect.append(type_Id, ++groupCount);
        }
        update();
    }

    private void formatRemove(int position) {
        selectGood = goodList.get(position);
        selectGood.specSelect = position;

        if (shopDetails.items != null && shopDetails.items.size() > 0 && shopDetails.items.get(0).cate_id.equals("hot")) {
            if (selectGood.typeId == 0) {
                for (int i = 1; i < shopDetails.items.size(); i++) {
                    for (int j = 0; j < shopDetails.items.get(i).products.size(); j++) {
                        if (item.productsEntity.product_id.equals(shopDetails.items.get(i).products.get(j).product_id)) {
                            type_Id = i;
                        }
                    }
                }
            } else {
                type_Id = selectGood.typeId;
            }
        } else {
            type_Id = selectGood.typeId;
        }


        int groupCount = groupSelect.get(type_Id);
        if (groupCount == 1) {
            groupSelect.delete(type_Id);
        } else if (groupCount > 1) {
            groupSelect.append(type_Id, --groupCount);
        }

        Goods temp = selectedList.get(Integer.parseInt(item.product_id + specsEntity.spec_id));
        if (temp != null) {
            if (temp.count < 2) {
                selectedList.remove(Integer.parseInt(item.product_id + specsEntity.spec_id));
                totalAmount -= Double.parseDouble(temp.price);
            } else {
                temp.count--;
                totalAmount -= Double.parseDouble(temp.price);
            }
        }


        Goods specTemp = specList.get(Integer.parseInt(item.product_id));
        if (specTemp != null) {
            specTemp.count--;
        }

        update();


    }


    public Animation getShowAnimation() {
        AnimationSet set = new AnimationSet(true);
        RotateAnimation rotate = new RotateAnimation(0, 720, RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        set.addAnimation(rotate);
        TranslateAnimation translate = new TranslateAnimation(
                TranslateAnimation.RELATIVE_TO_SELF, 2f
                , TranslateAnimation.RELATIVE_TO_SELF, 0
                , TranslateAnimation.RELATIVE_TO_SELF, 0
                , TranslateAnimation.RELATIVE_TO_SELF, 0);
        set.addAnimation(translate);
        AlphaAnimation alpha = new AlphaAnimation(0, 1);
        set.addAnimation(alpha);
        set.setDuration(250);
        return set;
    }

    private Animation getHiddenAnimation() {
        AnimationSet set = new AnimationSet(true);
        RotateAnimation rotate = new RotateAnimation(0, 720, RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        set.addAnimation(rotate);
        TranslateAnimation translate = new TranslateAnimation(
                TranslateAnimation.RELATIVE_TO_SELF, 0
                , TranslateAnimation.RELATIVE_TO_SELF, 2f
                , TranslateAnimation.RELATIVE_TO_SELF, 0
                , TranslateAnimation.RELATIVE_TO_SELF, 0);
        set.addAnimation(translate);
        AlphaAnimation alpha = new AlphaAnimation(1, 0);
        set.addAnimation(alpha);
        set.setDuration(250);
        return set;
    }

    public void playAnimation(int[] start_location) {
        ImageView img = new ImageView(this);
        img.setImageResource(R.mipmap.icon_shop_add);
        WindowManager.LayoutParams params = new WindowManager.LayoutParams(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        params.width = Utils.dip2px(this, 24);
        params.height = Utils.dip2px(this, 24);
        img.setLayoutParams(params);
        setAnim(img, start_location);
    }

    private void setAnim(final View v, int[] start_location) {

        addViewToAnimLayout(containerLayout, v, start_location);
        Animation set = createAnim(start_location[0], start_location[1]);
        set.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(final Animation animation) {
                mHanlder.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        containerLayout.removeView(v);
                    }
                }, 100);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        v.startAnimation(set);
    }

    private void addViewToAnimLayout(final ViewGroup vg, final View view,
                                     int[] location) {
        int x = location[0];
        int y = location[1];
        int[] loc = new int[2];
        vg.getLocationInWindow(loc);
        view.setX(x);
        view.setY(y - loc[1]);
        vg.addView(view);
    }

    private Animation createAnim(int startX, int startY) {
        int[] des = new int[2];
        imgCart.getLocationInWindow(des);

        AnimationSet set = new AnimationSet(false);

        Animation translationX = new TranslateAnimation(0, des[0] - startX, 0, 0);
        translationX.setInterpolator(new LinearInterpolator());
        Animation translationY = new TranslateAnimation(0, 0, 0, des[1] - startY);
        translationY.setInterpolator(new AccelerateInterpolator());
        Animation alpha = new AlphaAnimation(1, 0.5f);
        set.addAnimation(translationX);
        set.addAnimation(translationY);
        set.addAnimation(alpha);
        set.setDuration(500);
        return set;
    }


    @Override
    public void onSuccess(String url, String Json) {
        try {
            Gson gson = new Gson();
            productDetail = gson.fromJson(Json, Response_WaiMai_ShopDetail.class);
            if (productDetail.error.equals("0")) {
                detail = productDetail.data.detail;
                /*商品名*/
                tvTitle.setText(detail.title);
                phone = detail.phone;
                tvCommTitle.setText(detail.title);
                tvShopName.setText(detail.shop_title);
                /*商品图片*/
                Utils.LoadStrPicture(this, Api.IMAGE_URL + detail.photo, ivCommPic);
                /*商品价格*/
                tvCommentPrice.setText("￥" + detail.price);
                /*商品销量*/
                tvCommentSales.setText(String.format(getString(R.string.已售), detail.sales));
                /*好评率*/
                tvGoodRate.setText(detail.good_rate + "%");
                progressBar.setProgress((int) Double.parseDouble(detail.good_rate));
                /*好评数*/
                good = detail.good;
                tvGood.setText(good);
                /*差评数*/
                tvBad.setText(detail.bad);
                /*规格数据*/
                specsEntityList = detail.specs;
                if (null != specsEntityList && specsEntityList.size() > 0) {
                    /*显示规格 隐藏正常的加减数据*/
                    llNormal.setVisibility(View.GONE);
                    llSoldOut.setVisibility(View.GONE);
                    llGuige.setVisibility(View.VISIBLE);
                    mValsList = new ArrayList<>();
                    /*获取规格名称数据*/
                    for (int i = 0; i < specsEntityList.size(); i++) {
                        mValsList.add(specsEntityList.get(i).spec_name);
                    }
                    mVals = mValsList.toArray(new String[mValsList.size()]);
                } else if (detail.sale_sku == 0) {
                    llNormal.setVisibility(View.GONE);
                    llSoldOut.setVisibility(View.VISIBLE);
                    llGuige.setVisibility(View.GONE);
                } else {
                    llNormal.setVisibility(View.VISIBLE);
                    llSoldOut.setVisibility(View.GONE);
                    llGuige.setVisibility(View.GONE);
                }

                multiplestatusview.showContent();
            } else {
                ToastUtil.show(productDetail.message);
                multiplestatusview.showError();
            }

        } catch (Exception e) {
            ToastUtil.show(R.string.接口异常);
            multiplestatusview.showError();
            saveCrashInfo2File(e);
        }

    }

    @Override
    public void onBeforeAnimate() {
        multiplestatusview.showLoading();
    }

    @Override
    public void onErrorAnimate() {
        multiplestatusview.showError();
    }


    // 回弹动画 (使用了属性动画)
    public void replyImage() {
        final ViewGroup.LayoutParams lp = ivCommPic
                .getLayoutParams();
        final float w = ivCommPic.getLayoutParams().width;// 图片当前宽度
        final float h = ivCommPic.getLayoutParams().height;// 图片当前高度
        final float newW = metric.widthPixels;// 图片原宽度
        final float newH = metric.widthPixels * 10 / 13;// 图片原高度

        // 设置动画
        ValueAnimator anim = ObjectAnimator.ofFloat(0.0F, 1.0F)
                .setDuration(200);

        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float cVal = (Float) animation.getAnimatedValue();
                lp.width = (int) (w - (w - newW) * cVal);
                lp.height = (int) (h - (h - newH) * cVal);
                ivCommPic.setLayoutParams(lp);
            }
        });
        anim.start();
    }

    @Override
    public void onBackPressed() {
        closeActivity();
    }

    private void closeActivity() {
        if (null != shopCartLayout && shopCartLayout.isSheetShowing()) {
            shopCartLayout.dismissSheet();
        } else if (null != minatoSheetLayout && minatoSheetLayout.isSheetShowing()) {
            minatoSheetLayout.dismissSheet();
        } else {
            finish();
        }
    }
}
