package com.jhcms.waimaiV3.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.classic.common.MultipleStatusView;
import com.flipboard.bottomsheet.BottomSheetLayout;
import com.google.gson.Gson;
import com.jhcms.common.model.ShopDetail;
import com.jhcms.common.utils.Api;
import com.jhcms.common.utils.ToastUtil;
import com.jhcms.common.utils.Utils;
import com.jhcms.common.widget.ClearEditText;
import com.jhcms.waimaiV3.R;
import com.jhcms.waimaiV3.adapter.InStoreSearchAdapter;
import com.jhcms.waimaiV3.adapter.MinatoAdapter;
import com.jhcms.waimaiV3.adapter.SelectAdapter;
import com.jhcms.waimaiV3.dialog.CallDialog;
import com.jhcms.waimaiV3.dialog.GuiGeDialog;
import com.jhcms.waimaiV3.litepal.Product;
import com.jhcms.waimaiV3.model.Goods;
import com.jhcms.waimaiV3.model.MessageEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.litepal.crud.DataSupport;

import java.io.Serializable;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class WaiMaiInStoreSearchActivity extends SwipeBaseActivity {

    public static String SHOP_DETAIL = "SHOP_DETAIL";
    public static String SHOP_MINATO = "SHOP_MINATO";
    public static String REFRESH_GOODS = "REFRESH_GOODS";


    @Bind(R.id.et_content)
    ClearEditText etContent;
    @Bind(R.id.ll_search)
    LinearLayout llSearch;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.content_view)
    RecyclerView rvSearch;
    @Bind(R.id.iv_coucou)
    ImageView ivCoucou;
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
    @Bind(R.id.rl_root)
    RelativeLayout rlRoot;
    @Bind(R.id.minatoSheetLayout)
    BottomSheetLayout minatoSheetLayout;
    @Bind(R.id.bottomShopCartSheetLayout)
    BottomSheetLayout shopCartLayout;
    @Bind(R.id.bottomFormatSheetLayout)
    BottomSheetLayout formatLayout;
    @Bind(R.id.statusview)
    MultipleStatusView statusView;
    private InStoreSearchAdapter adapter;
    private Handler mHanlder;
    private ShopDetail shopDetail;
    private int type_Id;
    private SparseIntArray groupSelect;
    private SparseArray<Goods> selectedList;
    private SparseArray<Goods> specList;
    private double totalAmount;
    private ArrayList<Goods> couGoodsList;
    private GuiGeDialog dialog;

    /**
     * 规格dialog
     */

    @Override
    protected void initView() {
        setContentView(R.layout.activity_waimai_in_store_search);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void initData() {
        statusView.showEmpty();
        mHanlder = new Handler(getMainLooper());
        shopDetail = (ShopDetail) getIntent().getSerializableExtra(SHOP_DETAIL);
        couGoodsList = (ArrayList<Goods>) getIntent().getSerializableExtra(SHOP_MINATO);
        groupSelect = WaiMaiShopActivity.getGroupSelect();
        selectedList = WaiMaiShopActivity.getSelectedList();
        specList = WaiMaiShopActivity.getSpecList();
        /*getCurrencyInstance 返回当前缺省语言环境的通用格式*/
        nf = NumberFormat.getCurrencyInstance();
        /*设置数值的小数部分允许的最大位数为2*/
        nf.setMaximumFractionDigits(2);


        dialog = new GuiGeDialog(this);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeActivity();
            }
        });

        adapter = new InStoreSearchAdapter(WaiMaiShopActivity.instance, this);
        rvSearch.setLayoutManager(new LinearLayoutManager(this));
        rvSearch.setAdapter(adapter);
        rvSearch.addItemDecoration(Utils.setDivider(this, R.dimen.dp_050, R.color.view_color));
        adapter.setOnItemClickListener(new InStoreSearchAdapter.OnItemClickListener() {
            @Override
            public void itemClick(Goods item) {
                if (!Utils.isFastDoubleClick()) {
                    Intent intent = new Intent(WaiMaiInStoreSearchActivity.this, WaiMaiShopDetailActivity.class);
                    intent.putExtra(WaiMaiShopDetailActivity.SHOP_DETAIL, shopDetail);
                    intent.putExtra(WaiMaiShopDetailActivity.SHOP_MINATO, couGoodsList);
                    intent.putExtra(WaiMaiShopDetailActivity.GOODS_ITEM, item);
                    startActivity(intent);
                }
            }
        });
        adapter.setOnGuiGeClickListener(new InStoreSearchAdapter.OnGuiGeClickListener() {
            @Override
            public void guiGeClick(Goods item) {
//                showFormatBottomSheet();
                dialog.setData(item, shopDetail);
                dialog.show();
            }
        });
        update(true);
    }

    public void add(Goods item, boolean refreshGoodList) {
        /*处理左侧热销不显示数量*/

        if (shopDetail.items != null && shopDetail.items.size() > 0 && shopDetail.items.get(0).cate_id.equals("hot")) {
            if (item.typeId == 0) {
                for (int i = 1; i < shopDetail.items.size(); i++) {
                    for (int j = 0; j < shopDetail.items.get(i).products.size(); j++) {
                        if (item.productsEntity.product_id.equals(shopDetail.items.get(i).products.get(j).product_id)) {
                            type_Id = i;
                        }
                    }
                }
            } else {
                type_Id = item.typeId;
            }
        }else {
            type_Id = item.typeId;
        }

        /*处理左侧商品分类的数量--start*/
        int groupCount = groupSelect.get(type_Id);
        if (groupCount == 0) {
            groupSelect.append(type_Id, 1);
        } else {
            groupSelect.append(type_Id, ++groupCount);
        }
        /*处理左侧商品分类的数量--end*/

        /*处理进入店铺后规格商品的添加--start*/
        if (item.is_spec.equals("1")) {
            Goods specTemp = specList.get(Integer.parseInt(item.productId));
            if (specTemp == null) {
                for (int i = 0; i < Api.GOOD_LIST.size(); i++) {
                    if (Api.GOOD_LIST.get(i).product_id.equals(item.productId)) {
                        Api.GOOD_LIST.get(i).count = 1;
                        specList.append(Integer.parseInt(item.productId), Api.GOOD_LIST.get(i));
                    }
                }
            } else {
                specTemp.count++;
            }
        }
        /*处理进入店铺后规格商品的添加--end*/
        /*处理添加商品的操作--start*/
        Goods temp = selectedList.get(Integer.parseInt(item.product_id));
        if (temp == null) {
            item.count = 1;
            selectedList.append(Integer.parseInt(item.product_id), item);
        } else {
            temp.count++;
        }
        /*处理添加商品的操作--end*/
        update(refreshGoodList);
    }


    public void remove(Goods item, boolean refreshGoodList) {
        if (shopDetail.items != null && shopDetail.items.size() > 0 && shopDetail.items.get(0).cate_id.equals("hot")) {
            if (item.typeId == 0) {
                for (int i = 1; i < shopDetail.items.size(); i++) {
                    for (int j = 0; j < shopDetail.items.get(i).products.size(); j++) {
                        if (item.productsEntity.product_id.equals(shopDetail.items.get(i).products.get(j).product_id)) {
                            type_Id = i;
                        }
                    }
                }
            } else {
                type_Id = item.typeId;
            }
        }else {
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
                temp.count--;
                selectedList.remove(Integer.parseInt(item.product_id));
            } else {
                temp.count--;
            }
        }

        /*处理进入店铺后规格商品的添加--start*/
        if (item.is_spec.equals("1")) {
            Goods specTemp = specList.get(Integer.parseInt(item.productId));
            if (specTemp != null) {
                specTemp.count--;
            }
        }
        /*处理进入店铺后规格商品的添加--end*/


        update(refreshGoodList);
    }

    @Subscribe
    public void onMessageEvent(MessageEvent event) {
        if (event.message.equals(REFRESH_GOODS)) {
            update(true);
        }
    }

    private void update(boolean refreshGoodList) {
        int size = selectedList.size();
        int count = 0;
        totalAmount = 0;
        for (int i = 0; i < size; i++) {
            Goods item = selectedList.valueAt(i);
            count += item.count;
            totalAmount += item.count * Double.parseDouble(item.price);
        }
        if (count < 1) {
            tvCount.setVisibility(View.GONE);
        } else {
            tvCount.setVisibility(View.VISIBLE);
        }

        tvCount.setText(String.valueOf(count));
        dealWithPagePrice();

        /**
         * 判断是否让凑凑按钮 显示
         *
         * 当购物车显示的时候 让 ivCoucou 隐藏 并判断 购物车上的coucou 凑一凑按钮是否显示
         * 当凑一凑显示的时候 让 ivCoucou 隐藏
         * 当购物车 凑一凑 都不显示的时候 在判断是否让 ivCoucou 显示
         *
         * */
        if (isShowShopCart) {
            ivCoucou.setVisibility(View.GONE);
            if (coucou != null) {
                if (isShowCouCou()) {
                    coucou.setVisibility(View.GONE);
                } else {
                    coucou.setVisibility(View.VISIBLE);
                }
            }
        } else if (isShowCou) {
            ivCoucou.setVisibility(View.GONE);
        } else {
            if (isShowCouCou()) {
                ivCoucou.setVisibility(View.GONE);
            } else {
                ivCoucou.setVisibility(View.VISIBLE);
            }
        }

        if (selectAdapter != null) {
            selectAdapter.notifyDataSetChanged();
        }
        if (shopCartLayout.isSheetShowing() && selectedList.size() < 1) {
            shopCartLayout.dismissSheet();
        }
        if (minatoAdapter != null) {
            minatoAdapter.notifyDataSetChanged();
        }
        if (null != adapter) {
            adapter.notifyDataSetChanged();
        }
        EventBus.getDefault().post(new MessageEvent(WaiMaiShopActivity.REFRESH_GOODS));
    }

    private TextView tvPagePrice;
    /**
     * 打包费
     */
    private double pagePrice;
    private static NumberFormat nf;

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
        tvCost.setText(nf.format(totalAmount + v));

        /**
         * 商家未打烊状态
         * */
        if ("1".equals(shopDetail.yysj_status) && "1".equals(shopDetail.yy_status)) {
            if (totalAmount > 0 && totalAmount + v >= Double.parseDouble(shopDetail.min_amount)) {
                tvTips.setVisibility(View.GONE);
                tvSubmit.setVisibility(View.VISIBLE);
            } else {
                tvSubmit.setVisibility(View.GONE);
                tvTips.setVisibility(View.VISIBLE);
                /**
                 * 处理还差多少钱起送
                 * */
                if (totalAmount != 0 && Double.parseDouble(shopDetail.min_amount) > totalAmount + v) {
                    tvTips.setText("差" + nf.format(Double.parseDouble(shopDetail.min_amount) - totalAmount - v) + "起送");
                } else {
                    tvTips.setText("￥" + shopDetail.min_amount + "元起送");
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

    //根据商品id获取当前规格商品的采购数量
    public int getSpecItemCountById(int id) {
        int specCount = WaiMaiShopActivity.getSpecItemCountById(id);
        return specCount;
    }


    @OnClick({R.id.ll_search, R.id.iv_coucou, R.id.tvSubmit, R.id.ll_bottom, R.id.rl_shopCart})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_search:
                String name = etContent.getText().toString();
                if (TextUtils.isEmpty(name)) {
                    ToastUtil.show("请输入商品名称");
                    return;
                }
                searchGood(name);
                break;
            case R.id.iv_coucou:
                showMinato();
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
                    intent.putExtra(ConfirmOrderActivity.SHOP_ID, shopDetail.shop_id);
                    intent.putExtra(ConfirmOrderActivity.PEI_TYPE, shopDetail.pei_type);
                    startActivity(intent);
                }
                break;
            case R.id.ll_bottom:
            case R.id.rl_shopCart:
                if (minatoSheetLayout != null && minatoSheetLayout.isSheetShowing()) {
                    minatoSheetLayout.dismissSheet();
                    isHindeCou = true;
                }
                showShopCart();
                break;
        }
    }

    private boolean isHindeCou = false;
    private View shopCartView;
    /**
     * 判断购物车是否处于显示状态
     */
    private boolean isShowShopCart = false;

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

    private RecyclerView rvSelected;
    private ImageView coucou;
    private SelectAdapter selectAdapter;

    private View showShopCartBottomSheet() {

        View view = LayoutInflater.from(this).inflate(R.layout.layout_shopcart_sheet, (ViewGroup) getWindow().getDecorView(), false);
        rvSelected = (RecyclerView) view.findViewById(R.id.selectRecyclerView);
        rvSelected.setLayoutManager(new LinearLayoutManager(this));
        rvSelected.addItemDecoration(Utils.setDivider(this, R.dimen.dp_1, R.color.background));
        TextView clear = (TextView) view.findViewById(R.id.clear);
        tvPagePrice = (TextView) view.findViewById(R.id.tv_pageprice);
        coucou = (ImageView) view.findViewById(R.id.iv_coucou);
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new CallDialog(WaiMaiInStoreSearchActivity.this)
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
        selectAdapter = new SelectAdapter(WaiMaiInStoreSearchActivity.this, selectedList);
        rvSelected.setAdapter(selectAdapter);
        return view;
    }


    /**
     * 凑一凑 的显示状态 isShowCou
     * <p>
     * 消失的时候 判断是否显示 ivCoucou凑一凑
     * 显示的时候 让ivCoucou凑一凑 隐藏
     */
    /**
     * 判断凑一凑 是否正在显示
     */
    private boolean isShowCou = false;
    private View minatoSheet;

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
        rvMinato.addItemDecoration(Utils.setDivider(this, R.dimen.dp_1, R.color.background));
        minatoAdapter = new MinatoAdapter(WaiMaiShopActivity.instance, WaiMaiInStoreSearchActivity.this, couGoodsList);
        rvMinato.setAdapter(minatoAdapter);
        return view;
    }

    //清空购物车
    public void clearCart() {
        selectedList.clear();
        groupSelect.clear();
        specList.clear();
        update(true);
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
        if (shopDetail.min_amount.equals("0")) {
            return true;
        } else if ((totalAmount + pagePrice) < Double.parseDouble(shopDetail.min_amount) && (totalAmount + pagePrice) >= Double.parseDouble(shopDetail.min_amount) / 2) {
            return false;
        } else {
            return true;
        }
    }


    private List<Goods> searchGood;

    public void searchGood(String search) {
        List<Product> products = DataSupport.where("title like ?", "%" + search + "%").find(Product.class);
        if (null != products && products.size() > 0) {
            statusView.showContent();
            Gson gson = new Gson();
            searchGood = new ArrayList<>();
            for (int i = 0; i < products.size(); i++) {
                Goods goods = gson.fromJson(products.get(i).getGoodInfo(), Goods.class);
                searchGood.add(goods);
            }
            adapter.setData(searchGood, search);
        } else {
            ToastUtil.show("没有搜索到相关商品");
            statusView.showEmpty();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!TextUtils.isEmpty(etContent.getText().toString())) {
            searchGood(etContent.getText().toString());
        }
    }

    public void playAnimation(int[] start_location) {
        ImageView img = new ImageView(this);
        img.setImageResource(R.mipmap.icon_shop_add);
        WindowManager.LayoutParams params = new WindowManager.LayoutParams(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        params.width = Utils.dip2px(this, 20);
        params.height = Utils.dip2px(this, 20);
        img.setLayoutParams(params);
        setAnim(img, start_location);
    }

    private void setAnim(final View v, int[] start_location) {

        addViewToAnimLayout(rlRoot, v, start_location);
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
                        rlRoot.removeView(v);
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
        set.setDuration(250);
        return set;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
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
