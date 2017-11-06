package com.jhcms.mall.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IntRange;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.jhcms.common.utils.Api;
import com.jhcms.common.utils.HttpUtils;
import com.jhcms.common.utils.OnRequestSuccess;
import com.jhcms.common.utils.Utils;
import com.jhcms.mall.activity.MallProductDetailActivity;
import com.jhcms.mall.activity.MallShopProduceListActivity;
import com.jhcms.mall.adapter.ProductDoubleAdapter;
import com.jhcms.mall.decoration.ProductDoubleListDecoration;
import com.jhcms.mall.model.BaseResponse;
import com.jhcms.mall.model.ProductItemModel;
import com.jhcms.mall.model.ShopDetailModel;
import com.jhcms.waimaiV3.R;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ShopProductFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ShopProductFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_SHOP_ID = "param1";
    private static final String ARG_TYPE = "param2";
    public static final int TYPE_ALL = 0;
    public static final int TYPE_HOT = 1;
    public static final int TYPE_NEW = 2;
    @Bind(R.id.rv_list)
    RecyclerView rvList;
    @Bind(R.id.spring)
    SpringView spring;

    private int mShopId;
    private int mType;
    private int currentPage;
    private List<ProductItemModel> productData;
    private ProductDoubleAdapter adapter;


    public ShopProductFragment() {
        // Required empty public constructor
    }

    /**
     * @param shopID      店铺id
     * @param contentType 显示内容的类型，使用静态常量
     * @return A new instance of fragment ShopProductFragment.
     */
    public static ShopProductFragment newInstance(int shopID, @IntRange(from = 0, to = 2) int contentType) {
        ShopProductFragment fragment = new ShopProductFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SHOP_ID, shopID);
        args.putInt(ARG_TYPE, contentType);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mShopId = getArguments().getInt(ARG_SHOP_ID, -1);
            mType = getArguments().getInt(ARG_TYPE, 0);
        }
        currentPage = 1;
        productData = new ArrayList();
        requestData(mShopId, mType, currentPage);
    }

    private void requestData(int shopId, int type, int page) {
        String paramsData = null;
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("shop_id", shopId);
            jsonObject.put("type", type);
            jsonObject.put("page", page);
            paramsData = jsonObject.toString();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getActivity(), getString(R.string.mall_程序出错), Toast.LENGTH_SHORT).show();
            return;
        }
        HttpUtils.postUrlWithBaseResponse(getActivity(), Api.MALL_SHOP_DETAIL, paramsData, true, new OnRequestSuccess<BaseResponse<ShopDetailModel>>() {
            @Override
            public void onSuccess(String url, BaseResponse<ShopDetailModel> data) {
                super.onSuccess(url, data);
                try {
                    if(page==1){
                        productData.clear();
                    }
                    List<ProductItemModel> tuijian = data.getData().getTuijian();
                    productData.addAll(tuijian);
                    adapter.notifyDataSetChanged();
                } catch (Exception e) {
                    e.printStackTrace();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_shop_product, container, false);
        ButterKnife.bind(this, view);

        rvList.setLayoutManager(new GridLayoutManager(getActivity(),2));
        adapter = new ProductDoubleAdapter(getActivity(),productData);
        adapter.setOnItemClickListener(this::goProductDetail);
        ProductDoubleListDecoration itemDecoration = new ProductDoubleListDecoration(Utils.dip2px(getActivity(), 5), 2);
        rvList.addItemDecoration(itemDecoration);
        rvList.setAdapter(adapter);
        initRefereshView();
        return view;
    }

    /**
     * 跳转到商品详情界面
     *
     * @param productItemModel 商品信息
     * @param position         商品在列表中的排序
     */
    private void goProductDetail(ProductItemModel productItemModel, int position) {
        try {
            Intent intent = MallProductDetailActivity.generateIntent(getActivity(), Integer.parseInt(productItemModel.getProduct_id()));
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getContext(), getString(R.string.mall_程序出错), Toast.LENGTH_SHORT).show();
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
                currentPage=1;
                requestData(mShopId,mType,currentPage);
            }

            @Override
            public void onLoadmore() {
                currentPage++;
                requestData(mShopId,mType,currentPage);

            }
        });
        spring.setHeader(new DefaultHeader(getActivity()));
        spring.setFooter(new DefaultFooter(getActivity()));
        spring.setType(SpringView.Type.FOLLOW);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
