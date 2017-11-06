package com.jhcms.waimaiV3.fragment;

import android.content.Intent;
import android.os.Build;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.classic.common.MultipleStatusView;
import com.coorchice.library.SuperTextView;
import com.flipboard.bottomsheet.BottomSheetLayout;
import com.jhcms.common.model.ShopDetail;
import com.jhcms.common.utils.Utils;
import com.jhcms.common.widget.PinnedHeaderListView;
import com.jhcms.waimaiV3.R;
import com.jhcms.waimaiV3.activity.WaiMaiShopActivity;
import com.jhcms.waimaiV3.activity.WaiMaiShopDetailActivity;
import com.jhcms.waimaiV3.activity.WebViewActivity;
import com.jhcms.waimaiV3.adapter.Goods2Adapter;
import com.jhcms.waimaiV3.adapter.GoodsTypeAdapter;
import com.jhcms.waimaiV3.dialog.GuiGeDialog;
import com.jhcms.waimaiV3.model.Goods;
import com.jhcms.waimaiV3.model.Type;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by Wyj on 2017/5/6
 * TODO: 店铺菜单  购物车
 */
public class WaiMaiShopGoodsFragment extends CustomerBaseFragment {


    @Bind(R.id.left_listView)
    RecyclerView leftListView;
    @Bind(R.id.right_listview)
    PinnedHeaderListView rightListview;
    @Bind(R.id.content_view)
    LinearLayout contentView;
    @Bind(R.id.main_multiplestatusview)
    MultipleStatusView statusview;
    @Bind(R.id.bottomFormatSheetLayout)
    BottomSheetLayout bottomFormatSheetLayout;
    /**
     * 商户id
     */
    private String shop_id;
    /*商户详情字段*/
    private ShopDetail shopDetail;
    /*菜品字段*/
    private List<ShopDetail.ItemsEntity> data;
    private List<Type> types;
    private GoodsTypeAdapter typeAdapter;
    private Goods2Adapter myAdapter;
    private ArrayList<Goods> goodsList;
    private boolean isScroll = false;
    private HashMap<Integer, ArrayList<Goods>> goodsMap;
    /**
     * 规格dialog
     */
    private GuiGeDialog guiGeDialog;
    private ArrayList<Goods> couGoodsList;



    @Override
    protected View initView() {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_waimai_shopgoods, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    protected void initData() {

        guiGeDialog = new GuiGeDialog(getActivity());

        /*左侧分类*/
        leftListView.setLayoutManager(new LinearLayoutManager(getActivity()));
        typeAdapter = new GoodsTypeAdapter((WaiMaiShopActivity) getActivity());
        leftListView.setAdapter(typeAdapter);
        leftListView.addItemDecoration(Utils.setDivider(getActivity(), R.dimen.dp_050, R.color.qing));
        typeAdapter.setOnClickListener(new GoodsTypeAdapter.onClickListener() {
            @Override
            public void onClick(int typeId) {
                isScroll = false;
                typeAdapter.setSelectTypeId(typeId);

                rightListview.setSelection(getSelectedPosition(typeId));
            }
        });

        /*右侧商品*/
        myAdapter = new Goods2Adapter((WaiMaiShopActivity) getActivity());
        rightListview.setAdapter(myAdapter);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            rightListview.setNestedScrollingEnabled(true);
        }
        rightListview.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (isScroll) {
                    if (null != goodsList && goodsList.size() > 0) {
                        int leftSection = myAdapter.getSectionForPosition(firstVisibleItem);
                        if (typeAdapter.getSelectTypeId() != leftSection) {
                            leftListView.smoothScrollToPosition(leftSection);
                            typeAdapter.setSelectTypeId(leftSection);
                        }
                    }
                    isScroll = false;
                } else {
                    isScroll = true;
                }
            }
        });


        myAdapter.setOnItemClickListener(new Goods2Adapter.OnItemClickListener() {
            /**
             * 商品点击回调
             * @param item
             */
            @Override
            public void itemClick(Goods item) {
                if (!Utils.isFastDoubleClick()) {
                    Intent intent = new Intent(getActivity(), WaiMaiShopDetailActivity.class);
                    intent.putExtra(WaiMaiShopDetailActivity.SHOP_DETAIL, shopDetail);
                    intent.putExtra(WaiMaiShopDetailActivity.SHOP_MINATO, couGoodsList);
                    intent.putExtra(WaiMaiShopDetailActivity.GOODS_ITEM, item);
                    startActivity(intent);
                }
            }
        });
        myAdapter.setOnGuiGeClickListener(new Goods2Adapter.OnGuiGeClickListener() {
            /**
             * 点击规格回调
             * @param item
             */
            @Override
            public void guiGeClick(Goods item) {
                guiGeDialog.setData(item, shopDetail);
                guiGeDialog.show();
            }
        });
    }


    /**
     * 根据类别id获取分类的Position 用于滚动左侧的类别列表
     *
     * @param typeId
     * @return
     */
    public int getSelectedGroupPosition(int typeId) {
        for (int i = 0; i < types.size(); i++) {
            if (typeId == types.get(i).typeId) {
                return i;
            }
        }
        return 0;
    }


    /**
     * 根据类别id获取分类的Position 用于滚动右侧的类别列表
     *
     * @param typeId
     * @return
     */
    private int getSelectedPosition(int typeId) {
        /*右边选中的位置*/
        int rightSection = 0;
        for (int i = 0; i < typeId; i++) {
            rightSection += myAdapter.getCountForSection(i) + 1;
        }
        return rightSection;
    }

    @Override
    protected void lazyLoad() {
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


    /*优惠券view id*/
    private LinearLayout llYouhui;
    private TextView tvYouhuiPrices;
    private TextView tvYouhuiMan;
    private SuperTextView tvYouhui;

    /**
     * @param shopDetail   外卖商户详情 mode
     * @param couGoodsList 符合凑一凑的商品列表
     * @param product_id   热销 product_id
     */
    public void setData(ShopDetail shopDetail, final ArrayList<Goods> couGoodsList, String product_id) {
        this.shop_id = shopDetail.shop_id;
        this.shopDetail = shopDetail;
        this.data = shopDetail.items;
        this.couGoodsList = couGoodsList;

        if (null != shopDetail.items && shopDetail.items.size() == 0) {
            statusview.showEmpty();
        } else {
            statusview.showContent();
            types = new ArrayList<>();

            /**
             * 处理商品优惠券
             * */
            final ShopDetail.ShopCouponEntity shop_coupon = shopDetail.shop_coupon;
            LinearLayout llCoupon = (LinearLayout) LayoutInflater.from(getActivity()).inflate(R.layout.shop_coupon_view, null);
            if (null != shop_coupon && !TextUtils.isEmpty(shop_coupon.title)) {
                llYouhui = (LinearLayout) llCoupon.findViewById(R.id.ll_youhui);
                tvYouhuiPrices = (TextView) llCoupon.findViewById(R.id.tv_youhui_prices);
                tvYouhuiMan = (TextView) llCoupon.findViewById(R.id.tv_youhui_man);
                tvYouhui = (SuperTextView) llCoupon.findViewById(R.id.tv_youhui);
                rightListview.addHeaderView(llCoupon);
                tvYouhuiPrices.setText(shop_coupon.coupon_amount);
                tvYouhuiMan.setText(shop_coupon.title);
                tvYouhui.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!Utils.isFastDoubleClick()) {
                            Intent intent = new Intent(getActivity(), WebViewActivity.class);
                            intent.putExtra(WebViewActivity.URL, shop_coupon.link);
                            startActivity(intent);
                        }
                    }
                });
            }

            /**
             * 把店铺商圈转成Goods对象
             * */
            goodsMap = new HashMap<Integer, ArrayList<Goods>>();
            for (int i = 0; i < data.size(); i++) {
                goodsList = new ArrayList<>();
                Type type = new Type(data.get(i), i);
                types.add(type);
                for (int j = 0; j < data.get(i).products.size(); j++) {
                    Goods goods = new Goods(data.get(i).products.get(j), data.get(i).products.get(j).product_id, data.get(i).title, i);
                    goods.setBad(data.get(i).products.get(j).bad);
                    goods.setGood(data.get(i).products.get(j).good);
                    goods.setIs_spec(data.get(i).products.get(j).is_spec);
                    goods.setPrice(data.get(i).products.get(j).price);
                    goods.setProduct_id(data.get(i).products.get(j).product_id);
                    goods.setProductId(data.get(i).products.get(j).product_id);
                    goods.setSale_sku(data.get(i).products.get(j).sale_sku);
                    goods.setShop_id(data.get(i).products.get(j).shop_id);
                    goods.setSpec_id("0");
                    goods.setPagePrice(data.get(i).products.get(j).package_price);
                    goods.setName(data.get(i).products.get(j).title);
                    goods.setTitle(data.get(i).title);
                    goods.setLogo(data.get(i).products.get(j).photo);
                    goods.setTypeId(i);
                    goodsList.add(goods);
                }
                goodsMap.put(i, goodsList);
            }

            typeAdapter.setData(types);
            myAdapter.setData(types, goodsList, goodsMap);
            if (!TextUtils.isEmpty(product_id)) {
                for (int i = 0; i < goodsMap.get(0).size(); i++) {
                    if (goodsMap.get(0).get(i).product_id.equals(product_id)) {
//                        if (null != shop_coupon && !TextUtils.isEmpty(shop_coupon.title)) {
//                            rightListview.setSelection(i);
//                        } else {
//                        }
                        rightListview.setSelection(i + 1);
                        myAdapter.setHotProductId(i);
                        return;
                    }
                }
            }
        }
    }

    public void setMyAdapterDataChanged() {
        if (null != myAdapter)
            myAdapter.notifyDataSetChanged();
    }

    public void setTypeAdapterDataChanged() {
        if (null != typeAdapter)
            typeAdapter.notifyDataSetChanged();

    }
}