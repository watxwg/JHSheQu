package com.jhcms.mall.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.classic.common.MultipleStatusView;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.recyclerview.ProgressStyle;
import com.jhcms.common.utils.Api;
import com.jhcms.common.utils.HttpUtils;
import com.jhcms.common.utils.OnRequestSuccess;
import com.jhcms.common.utils.ToastUtil;
import com.jhcms.common.utils.Utils;
import com.jhcms.mall.activity.MallConfirmOrderActivity;
import com.jhcms.mall.activity.MallShopProduceListActivity;
import com.jhcms.mall.adapter.MallShopCartAdapter;
import com.jhcms.mall.model.BaseResponse;
import com.jhcms.mall.model.GoodsInfo;
import com.jhcms.mall.model.MallOrderGetCart;
import com.jhcms.mall.model.StoreInfo;
import com.jhcms.waimaiV3.R;
import com.jhcms.waimaiV3.fragment.CustomerBaseFragment;

import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by Administrator on 2017/4/11.
 */
public class ShopCarFragment extends CustomerBaseFragment {

    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.lsitview)
    LRecyclerView lRecyclerView;
    @Bind(R.id.tv_jiesua)
    TextView tvJiesua;
    @Bind(R.id.tv_prices)
    TextView tvPrices;
    @Bind(R.id.tv_description)
    TextView tvDescription;
    @Bind(R.id.cb_allComm)
    CheckBox cbAllComm;
    @Bind(R.id.statusview)
    MultipleStatusView statusview;
    private MallShopCartAdapter shopCarAdapter;
    private List<StoreInfo> groups;
    private List<StoreInfo> groupsCheck;
    private Map<String, List<GoodsInfo>> children;
    private Map<String, List<GoodsInfo>> childrenCheck;
    private LinearLayoutManager linearLayoutManager;
    private LRecyclerViewAdapter listViewLAdapter;
    private NumberFormat nf;
    private boolean isPrepared = false;

    @Override
    protected View initView() {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_mall_shopcar, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    protected void initData() {
        super.initData();
        setToolBar(toolbar);
        toolbar.setNavigationIcon(null);
        toolbar.setBackgroundColor(Color.WHITE);
        tvTitle.setText("购物车");
        tvTitle.setTextColor(getResources().getColor(R.color.title_color));
        toolbar.setNavigationOnClickListener(v -> getActivity().finish());
        nf = NumberFormat.getCurrencyInstance();
        nf.setMaximumFractionDigits(2);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        shopCarAdapter = new MallShopCartAdapter(getActivity());
        listViewLAdapter = new LRecyclerViewAdapter(shopCarAdapter);
        lRecyclerView.setLayoutManager(linearLayoutManager);
        lRecyclerView.setAdapter(listViewLAdapter);
        lRecyclerView.addItemDecoration(Utils.setDivider(getActivity(), R.dimen.dp_5, R.color.background));
        /*处理刷新*/
        initRefresh();


        shopCarAdapter.setOnItemClickListener(new MallShopCartAdapter.OnItemClickListener() {
            @Override
            public void checkGroup(int groupPosition, boolean isChecked) {
                StoreInfo group = groups.get(groupPosition);
                List<GoodsInfo> childs = children.get(group.getShopId());
                for (int i = 0; i < childs.size(); i++) {
                    childs.get(i).setChoosed(isChecked);
                }
                if (isAllCheck())
                    cbAllComm.setChecked(true);
                else
                    cbAllComm.setChecked(false);
                listViewLAdapter.notifyDataSetChanged();
                calculate();
            }

            @Override
            public void isCheckAll() {
                if (isAllCheck())
                    cbAllComm.setChecked(true);
                else
                    cbAllComm.setChecked(false);
                listViewLAdapter.notifyDataSetChanged();
                calculate();
            }

            @Override
            public void detele(int groupPosition) {
                AlertDialog alert = new AlertDialog.Builder(getActivity()).create();
                alert.setTitle("操作提示");
                alert.setMessage("您确定要将这些商品从购物车中移除吗？");
                alert.setButton(DialogInterface.BUTTON_NEGATIVE, "取消",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                return;
                            }
                        });
                alert.setButton(DialogInterface.BUTTON_POSITIVE, "确定",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                deteleCart(Api.MALL_DELETE_BY_SHOP, groupPosition);
                            }
                        });
                alert.show();
            }

            /**
             * 删除商品
             * @param groupPosition
             * @param childPosition
             */
            @Override
            public void childDelete(int groupPosition, int childPosition) {
                childDeleteCart(Api.MALL_DELCART, groupPosition, childPosition, null, true);
            }

            /**
             * 添加商品数量
             * @param groupPosition 组元素位置
             * @param childPosition 子元素位置
             * @param showCountView 用于展示变化后数量的View
             * @param isChecked     子元素选中与否
             */
            @Override
            public void doIncrease(int groupPosition, int childPosition, View showCountView, boolean isChecked) {
                childAddCart(Api.MALL_ADDCART, groupPosition, childPosition, showCountView);
            }

            /**
             * 减少商品数量
             * @param groupPosition 组元素位置
             * @param childPosition 子元素位置
             * @param showCountView 用于展示变化后数量的View
             * @param isChecked     子元素选中与否
             */
            @Override
            public void doDecrease(int groupPosition, int childPosition, View showCountView, boolean isChecked) {
                childDeleteCart(Api.MALL_DELCART, groupPosition, childPosition, showCountView, false);
            }
        });
        statusview.setOnRetryClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * 去逛逛跳到全部上商家分类
                 * */
                startActivity(new Intent(getActivity(), MallShopProduceListActivity.class));

            }
        });

    }

    private void childAddCart(String api, int groupPosition, int childPosition, View showCountView) {
        String shopId = groups.get(groupPosition).getShopId();
        GoodsInfo product = (GoodsInfo) shopCarAdapter.getChild(groupPosition,
                childPosition);
        String cart = product.getGoodId() +
                "-" + product.getDescId() + ":1";
        JSONObject object = new JSONObject();
        try {
            object.put("shop_id", shopId);
            /*格式 5-96_98:2 数量为0则是删除该商品*/
            object.put("cart", cart);
        } catch (Exception e) {
            e.printStackTrace();
        }
        HttpUtils.postUrlWithBaseResponse(getActivity(), api, object.toString(), true, new OnRequestSuccess<BaseResponse<MallOrderGetCart>>() {
            @Override
            public void onSuccess(String url, BaseResponse<MallOrderGetCart> data) {
                if (data.error == 0) {
                    int currentCount = product.getCount();
                    currentCount++;
                    product.setCount(currentCount);
                    ((TextView) showCountView).setText(currentCount + "");
                    shopCarAdapter.notifyDataSetChanged();
                    calculate();
                } else {
                    ToastUtil.show(data.message);
                }
            }

            @Override
            public void onBeforeAnimate() {

            }

            @Override
            public void onErrorAnimate() {

            }
        });
    }

    /**
     * @param api
     * @param groupPosition
     * @param childPosition
     * @param showCountView
     * @param isDetele      是否删除商品
     */
    private void childDeleteCart(String api, int groupPosition, int childPosition, View showCountView, boolean isDetele) {
        GoodsInfo product = (GoodsInfo) shopCarAdapter.getChild(groupPosition,
                childPosition);
        if (product.getCount() == 1) {
            return;
        }
        String shopId = groups.get(groupPosition).getShopId();
        String cart = product.getGoodId() +
                "-" + product.getDescId() + ":" + (isDetele ? "0" : "1");
        JSONObject object = new JSONObject();
        try {
            object.put("shop_id", shopId);
            /*格式 5-96_98:2 数量为0则是删除该商品*/
            object.put("cart", cart);
        } catch (Exception e) {
            e.printStackTrace();
        }
        HttpUtils.postUrlWithBaseResponse(getActivity(), api, object.toString(), true, new OnRequestSuccess<BaseResponse<MallOrderGetCart>>() {
            @Override
            public void onSuccess(String url, BaseResponse<MallOrderGetCart> data) {
                if (data.error == 0) {
                    if (isDetele) {
                        children.get(groups.get(groupPosition).getShopId()).remove(childPosition);
                        StoreInfo group = groups.get(groupPosition);
                        List<GoodsInfo> childs = children.get(group.getShopId());
                        if (childs.size() == 0) {
                            groups.remove(groupPosition);
                        }
                    } else {
                        int currentCount = product.getCount();
                        if (currentCount == 1)
                            return;
                        currentCount--;
                        product.setCount(currentCount);
                        ((TextView) showCountView).setText(currentCount + "");
                    }
                    shopCarAdapter.notifyDataSetChanged();
                    calculate();
                } else {
                    ToastUtil.show(data.message);
                }
            }

            @Override
            public void onBeforeAnimate() {

            }

            @Override
            public void onErrorAnimate() {

            }
        });
    }

    /**
     * 删除商家购物车
     *
     * @param api
     * @param groupPosition
     */
    private void deteleCart(String api, int groupPosition) {
        String shopId = groups.get(groupPosition).getShopId();
        JSONObject object = new JSONObject();
        try {
            object.put("shop_id", shopId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        HttpUtils.postUrlWithBaseResponse(getActivity(), api, object.toString(), true, new OnRequestSuccess<BaseResponse<MallOrderGetCart>>() {
            @Override
            public void onSuccess(String url, BaseResponse<MallOrderGetCart> data) {
                if (data.error == 0) {
                    groups.remove(groupPosition);
                    listViewLAdapter.notifyDataSetChanged();
                    calculate();
                } else {
                    ToastUtil.show(data.message);
                }
            }

            @Override
            public void onBeforeAnimate() {

            }

            @Override
            public void onErrorAnimate() {

            }
        });
    }

    private boolean isAllCheck() {
        for (StoreInfo group : groups) {
            if (!group.isChoosed())
                return false;
        }
        return true;
    }

    private void initRefresh() {
        //设置头部加载颜色
        lRecyclerView.setHeaderViewColor(R.color.themColor, R.color.themColor, R.color.background);
        //设置底部加载颜色
        lRecyclerView.setFooterViewColor(R.color.themColor, R.color.themColor, R.color.background);
        //设置底部加载文字提示
        lRecyclerView.setFooterViewHint("拼命加载中", "已经全部为你呈现了", "网络不给力啊，点击再试一次吧");
        lRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader); //设置下拉刷新Progress的样式
        lRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallSpinFadeLoader);//设置加载更多Progress的样式
        lRecyclerView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                requestShopCart();
            }
        });
        /**
         * 加载更多
         */
        lRecyclerView.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        lRecyclerView.setNoMore(true);
                    }
                }, 100);
            }
        });
    }


    @Override
    protected void lazyLoad() {
        if (isVisible && isPrepared) {
            requestShopCart();
        }

    }

    private void requestShopCart() {
        HttpUtils.postUrlWithBaseResponse(getActivity(), Api.MALL_ORDER_GET_CART
                , null, true, new OnRequestSuccess<BaseResponse<MallOrderGetCart>>() {
                    @Override
                    public void onSuccess(String url, BaseResponse<MallOrderGetCart> data) {
                        dealWith(data);
                    }

                    @Override
                    public void onBeforeAnimate() {

                    }

                    @Override
                    public void onErrorAnimate() {

                    }
                });
    }


    private void dealWith(BaseResponse<MallOrderGetCart> data) {
        groups = new ArrayList<StoreInfo>();// 组元素数据列表
        children = new HashMap<String, List<GoodsInfo>>();// 子元素数据列表
        List<MallOrderGetCart.CartGoodsBean> cart_goods = data.getData().getCart_goods();
        if (null != cart_goods && cart_goods.size() > 0) {
            for (int i = 0; i < cart_goods.size(); i++) {
                if (TextUtils.isEmpty(cart_goods.get(i).getShop_id())) {
                    statusview.showEmpty();
                    return;
                } else {
                    StoreInfo storeInfo = new StoreInfo(cart_goods.get(i).getShop_id(), cart_goods.get(i).getTitle());
                    groups.add(storeInfo);
                    List<GoodsInfo> products = new ArrayList<GoodsInfo>();
                    for (int j = 0; j < cart_goods.get(i).getCarts().size(); j++) {
                        GoodsInfo goodsInfo = new GoodsInfo();
                        goodsInfo.setGoodId(cart_goods.get(i).getCarts().get(j).getProduct_id());
                        goodsInfo.setName(cart_goods.get(i).getCarts().get(j).getTitle());
                        goodsInfo.setDesc(cart_goods.get(i).getCarts().get(j).getStock_real_name());
                        goodsInfo.setDescId(cart_goods.get(i).getCarts().get(j).getStock_name());
                        goodsInfo.setPrice(cart_goods.get(i).getCarts().get(j).getWei_price());
                        goodsInfo.setCount(cart_goods.get(i).getCarts().get(j).getNumber());
                        goodsInfo.setGoodsImg(cart_goods.get(i).getCarts().get(j).getPhoto());
                        products.add(goodsInfo);
                    }
                    children.put(groups.get(i).getShopId(), products);// 将组元素的一个唯一值，这里取Id，作为子元素List的Key
                }

            }

            shopCarAdapter.setData(groups, children);
            lRecyclerView.refreshComplete(cart_goods.size());
            listViewLAdapter.notifyDataSetChanged();
            /*全选状态恢复默认*/
            calculate();
            cbAllComm.setChecked(false);
        } else {
            statusview.showEmpty();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


    @OnClick({R.id.tv_jiesua, R.id.cb_allComm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_jiesua:
                AlertDialog alert;
                if (totalCount == 0) {
                    ToastUtil.show("请选择要支付的商品");
                    return;
                }
                alert = new AlertDialog.Builder(getActivity()).create();
                alert.setTitle("操作提示");
                alert.setMessage("总计:\n" + totalCount + "种商品\n" + totalPrice + "元");
                alert.setButton(DialogInterface.BUTTON_NEGATIVE, "取消",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                return;
                            }
                        });
                alert.setButton(DialogInterface.BUTTON_POSITIVE, "确定",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                getActivity().startActivity(MallConfirmOrderActivity.generateIntent(getActivity(), buildProductInfo()));
                            }
                        });
                alert.show();
                break;
            case R.id.cb_allComm:
                dealWithCheckAll();
                break;
        }
    }

    /**
     * 拼接商品信息，用于订单预处理
     */
    private String buildProductInfo() {
        groupsCheck = new ArrayList<>();
        childrenCheck = new HashMap<String, List<GoodsInfo>>();
        StringBuilder sb = new StringBuilder();

        /*处理被选中的商品*/
        for (int i = 0; i < groups.size(); i++) {
            StoreInfo group = groups.get(i);
            List<GoodsInfo> childs = children.get(group.getShopId());
            List<GoodsInfo> products = new ArrayList<GoodsInfo>();
            for (int j = 0; j < childs.size(); j++) {
                GoodsInfo product = childs.get(j);
                if (product.isChoosed()) {
                    groupsCheck.add(group);
                    products.add(product);
                }
            }
            childrenCheck.put(group.getShopId(), products);
        }

        for (int i = 0; i < groupsCheck.size(); i++) {
            sb.append(groupsCheck.get(i).getShopId() + "@");
            for (int j = 0; j < childrenCheck.get(groupsCheck.get(i).getShopId()).size(); j++) {
                if (j == childrenCheck.get(groupsCheck.get(i).getShopId()).size() - 1) {
                    sb.append(childrenCheck.get(groupsCheck.get(i).getShopId()).get(j).getGoodId() + "-" + childrenCheck.get(groupsCheck.get(i).getShopId()).get(j).getDescId() + ":" + childrenCheck.get(groupsCheck.get(i).getShopId()).get(j).getCount());
                } else {
                    sb.append(childrenCheck.get(groupsCheck.get(i).getShopId()).get(j).getGoodId() + "-" + childrenCheck.get(groupsCheck.get(i).getShopId()).get(j).getDescId() + ":" + childrenCheck.get(groupsCheck.get(i).getShopId()).get(j).getCount() + ",");
                }
            }
            if (i != groupsCheck.size() - 1) {
                sb.append("#");
            }
        }
        String productInfo = sb.toString();
        return productInfo;
    }

    @Override
    public void onResume() {
        super.onResume();
        isPrepared = true;
        lazyLoad();
    }


    /**
     * 全选与反选
     */
    private void dealWithCheckAll() {
        for (int i = 0; i < groups.size(); i++) {
            groups.get(i).setChoosed(cbAllComm.isChecked());
            StoreInfo group = groups.get(i);
            List<GoodsInfo> childs = children.get(group.getShopId());
            for (int j = 0; j < childs.size(); j++) {
                childs.get(j).setChoosed(cbAllComm.isChecked());
            }
        }
        if (null != listViewLAdapter) {
            listViewLAdapter.notifyDataSetChanged();
        }
        calculate();
    }

    /**
     * 统计操作<br>
     * 1.先清空全局计数器<br>
     * 2.遍历所有子元素，只要是被选中状态的，就进行相关的计算操作<br>
     * 3.给底部的textView进行数据填充
     */
    private double totalPrice = 0.00;// 购买的商品总价
    private int totalCount = 0;// 购买的商品总数量

    private void calculate() {
        totalCount = 0;
        totalPrice = 0.00;
        for (int i = 0; i < groups.size(); i++) {
            StoreInfo group = groups.get(i);
            List<GoodsInfo> childs = children.get(group.getShopId());
            for (int j = 0; j < childs.size(); j++) {
                GoodsInfo product = childs.get(j);
                if (product.isChoosed()) {
                    totalCount++;
                    totalPrice += product.getPrice() * product.getCount();
                }
            }
        }

        tvPrices.setText(nf.format(totalPrice));
        tvJiesua.setText("结算(" + totalCount + ")");
        //计算购物车的金额为0时候清空购物车的视图
    }
}
