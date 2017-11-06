package com.jhcms.waimaiV3.dialog;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialog;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.jhcms.common.model.ShopDetail;
import com.jhcms.common.utils.Api;
import com.jhcms.common.utils.ToastUtil;
import com.jhcms.common.utils.Utils;
import com.jhcms.waimaiV3.R;
import com.jhcms.waimaiV3.activity.WaiMaiInStoreSearchActivity;
import com.jhcms.waimaiV3.activity.WaiMaiShopActivity;
import com.jhcms.waimaiV3.model.Goods;
import com.jhcms.waimaiV3.model.MessageEvent;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Wyj on 2017/5/16
 * TODO: 规格Dialog
 */
public class GuiGeDialog extends BottomSheetDialog {
    private Goods item;
    private final LayoutInflater mInflater;
    @Bind(R.id.tv_format_price)
    TextView tvFormatPrice;
    @Bind(R.id.iv_format_close)
    ImageView ivFormatClose;
    @Bind(R.id.tv_format_sold)
    TextView tvFormatSold;
    @Bind(R.id.tv_format_good)
    TextView tvFormatGood;
    @Bind(R.id.tv_format_selected)
    TextView tvFormatSelected;
    @Bind(R.id.iv_format_pic)
    ImageView ivFormatPic;
    @Bind(R.id.flowlayout)
    TagFlowLayout flowlayout;
    @Bind(R.id.tvMinus)
    TextView tvMinus;
    @Bind(R.id.count)
    TextView count;
    @Bind(R.id.tvAdd)
    TextView tvAdd;
    private Context context;

    /**
     * 规格商品布局
     *
     * @return
     */
    private TagAdapter<String> mAdapter;
    /**
     * 规格描述数组
     */
    private String[] mVals;
    /**
     * 规格描述集合
     */
    private List<String> mValsList;
    /**
     * 商品规格集合
     */
    private List<ShopDetail.ItemsEntity.ProductsEntity.SpecsEntity> entityList;
    /**
     * 规格信息
     */
    private ShopDetail.ItemsEntity.ProductsEntity.SpecsEntity specsEntity;
//    /**
//     * 购物车总数
//     */
//    private int comCount;

    /**
     * 购物车数据
     */
    private SparseArray<Goods> selectedList;
    /*规格商品*/
    private SparseArray<Goods> specList;
    private SparseIntArray groupSelect;
    private String specsPrice;
    /**
     * 商品集合
     */
    private ArrayList<Goods> goodList;
    private int pos;
    private Goods selectGood;
    /**
     * 商品数量
     */

    private int countById;
    private ShopDetail shopDetail;
    private int type_Id;

    public GuiGeDialog(Context context) {
        super(context, R.style.transparent);
        this.context = context;
        mInflater = LayoutInflater.from(context);
    }

    public void setData(Goods item, ShopDetail shopDetail) {
        this.item = item;
        this.shopDetail = shopDetail;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_bottom_guige_sheet);
        ButterKnife.bind(this);
        Window win = getWindow();
        WindowManager.LayoutParams lp = win.getAttributes();
        lp.width = Utils.getScreenW(context);
        win.setAttributes(lp);
        setCanceledOnTouchOutside(true);


    }

    @Override
    public void show() {
        super.show();
        initData();
    }

    private void initData() {
        selectedList = WaiMaiShopActivity.getSelectedList();
        specList = WaiMaiShopActivity.getSpecList();
        groupSelect = WaiMaiShopActivity.getGroupSelect();
        entityList = item.productsEntity.specs;
        mValsList = new ArrayList<>();
        goodList = new ArrayList<>();

        for (int i = 0; i < entityList.size(); i++) {
            mValsList.add(entityList.get(i).spec_name);
            Goods goods = new Goods();
            goods.setBad(item.bad);
            goods.setGood(item.good);
            goods.setIs_spec(item.is_spec);
            goods.setPrice(entityList.get(i).price);
            goods.setProduct_id(item.product_id + entityList.get(i).spec_id);
            goods.setProductId(item.product_id);
            goods.setSale_sku(entityList.get(i).sale_sku);
            goods.setShop_id(item.shop_id);
            goods.setLogo(entityList.get(i).spec_photo);
            goods.setProductsEntity(item.productsEntity);
            goods.setPagePrice(entityList.get(i).package_price);
            goods.setSpec_id(entityList.get(i).spec_id);
            goods.setName(item.productsEntity.title + "(" + entityList.get(i).spec_name + ")");
            goods.setTypeId(item.typeId);
            goodList.add(goods);
        }
        mVals = mValsList.toArray(new String[mValsList.size()]);

        flowlayout.setAdapter(mAdapter = new TagAdapter<String>(mVals) {

            @Override
            public View getView(FlowLayout parent, int position, String s) {
                TextView tv = (TextView) mInflater.inflate(R.layout.adapter_guige_item,
                        flowlayout, false);
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
        flowlayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                selectTag(position);
                return true;
            }
        });
    }

    /**
     * 选择规格
     *
     * @param position
     */
    private void selectTag(int position) {
        pos = position;
        specsEntity = entityList.get(position);
        update(position);

        /*名称*/
        tvFormatSelected.setText(mVals[position]);
        /*图片*/
        Utils.LoadStrPicture(context, Api.IMAGE_URL + specsEntity.spec_photo, ivFormatPic);
        /*价格*/
        specsPrice = specsEntity.price;
        tvFormatPrice.setText("￥" + specsPrice);
        /*销量*/
        tvFormatSold.setText(String.format(context.getString(R.string.已售), specsEntity.sale_count));
        /*赞*/
        tvFormatGood.setText(String.format(context.getString(R.string.赞), item.good));
    }


    @OnClick({R.id.iv_format_close, R.id.tvMinus, R.id.tvAdd})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_format_close:
                dismiss();
                break;
            case R.id.tvMinus:
                remove(pos);
                break;
            case R.id.tvAdd:
                countById++;
                if (countById > specsEntity.sale_sku) {
                    ToastUtil.show("库存不足");
                    return;
                }
                add(pos);
                break;
        }
    }

    /**
     * 商品的总价格
     */
    private double totalAmount;


    //移除商品
    public void remove(int position) {
        selectGood = goodList.get(position);
        selectGood.specSelect = position;
        if (shopDetail.items != null && shopDetail.items.size() > 0 && shopDetail.items.get(0).cate_id.equals("hot")) {
            if (selectGood.typeId == 0) {
                for (int i = 1; i < shopDetail.items.size(); i++) {
                    for (int j = 0; j < shopDetail.items.get(i).products.size(); j++) {
                        if (item.productsEntity.product_id.equals(shopDetail.items.get(i).products.get(j).product_id)) {
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
        Goods specTemp = specList.get(Integer.parseInt(item.product_id));
        if (specTemp != null) {
            specTemp.count--;
        }
        if (temp != null) {
            if (temp.count < 2) {
                selectedList.remove(Integer.parseInt(item.product_id + specsEntity.spec_id));
                totalAmount -= Double.parseDouble(temp.price);
            } else {
                temp.count--;
                totalAmount -= Double.parseDouble(temp.price);
            }
        }
        update(position);
    }


    private void add(int position) {
        selectGood = goodList.get(position);
        selectGood.specSelect = position;

        if (shopDetail.items != null && shopDetail.items.size() > 0 && shopDetail.items.get(0).cate_id.equals("hot")) {
            if (selectGood.typeId == 0) {
                for (int i = 1; i < shopDetail.items.size(); i++) {
                    for (int j = 0; j < shopDetail.items.get(i).products.size(); j++) {
                        if (item.productsEntity.product_id.equals(shopDetail.items.get(i).products.get(j).product_id)) {
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
        if (groupCount == 0) {
            groupSelect.append(type_Id, 1);
        } else {
            groupSelect.append(type_Id, ++groupCount);
        }


        Goods temp = selectedList.get(Integer.parseInt(item.product_id + specsEntity.spec_id));
        Goods specTemp = specList.get(Integer.parseInt(item.product_id));
        if (specTemp == null) {
            item.count = 1;
            specList.append(Integer.parseInt(item.product_id), item);
        } else {
            specTemp.count++;
        }
        if (temp == null) {
            selectGood.count = 1;
            selectedList.append(Integer.parseInt(item.product_id + specsEntity.spec_id), selectGood);
            totalAmount += Double.parseDouble(specsPrice);
        } else {
            temp.count++;
            totalAmount += Double.parseDouble(specsPrice);
        }

        update(position);
    }

    Map<Integer, Integer> numMap = new HashMap<>();

    private void update(int position) {

        for (int i = 0; i < selectedList.size(); i++) {
            Goods items = selectedList.valueAt(i);
            totalAmount += items.count * Double.parseDouble(items.price);
        }
        countById = getSelectedItemCountById(Integer.parseInt(item.product_id + specsEntity.spec_id));
        count.setText(String.valueOf(countById));
        numMap.put(position, countById);
        if (countById < 1) {
            tvMinus.setEnabled(false);
        } else {
            tvMinus.setEnabled(true);
        }
        EventBus.getDefault().post(new MessageEvent(WaiMaiShopActivity.REFRESH_GOODS));
        EventBus.getDefault().post(new MessageEvent(WaiMaiInStoreSearchActivity.REFRESH_GOODS));
    }


    public int getSelectedItemCountById(int id) {
        Goods temp = selectedList.get(id);
        if (temp == null) {
            return 0;
        }
        return temp.count;
    }

}
