package com.jhcms.mall.fragment;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jhcms.common.utils.Api;
import com.jhcms.common.utils.HttpUtils;
import com.jhcms.common.utils.OnRequestSuccess;

import com.jhcms.mall.activity.MallProductDetailActivity;

import com.jhcms.mall.activity.MallShopDetailsActivity;
import com.jhcms.mall.activity.MallShopProduceListActivity;
import com.jhcms.mall.activity.MallTopProductShopListActivity;
import com.jhcms.mall.adapter.CategoryRecommendAdapter;
import com.jhcms.mall.adapter.LeftFenLeiAdapter;

import com.jhcms.mall.adapter.SubCategoryAdapter;

import com.jhcms.mall.adapter.TypeLeftAdapter;
import com.jhcms.mall.adapter.TypeRightParentAdpter;
import com.jhcms.mall.model.BaseResponse;
import com.jhcms.mall.model.CategoryModel;
import com.jhcms.mall.model.CategoryRecommendModel;
import com.jhcms.mall.model.ShopItemModel;
import com.jhcms.mall.model.interfaces.CategoryInterface;
import com.jhcms.shequ.weight.Category;
import com.jhcms.waimaiV3.R;
import com.jhcms.waimaiV3.fragment.WaiMai_BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/4/11.
 */
public class TypeFragment extends WaiMai_BaseFragment {
    private static final String TAG = TypeFragment.class.getSimpleName();
    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.tv_search)
    TextView tvSearch;
    @Bind(R.id.rv_left)
    RecyclerView rvLeft;
    @Bind(R.id.rv_right)
    RecyclerView rvRight;
    @Bind(R.id.ll_root)
    LinearLayout llRoot;

    private TypeLeftAdapter typeleFtApter;
    private TypeRightParentAdpter typeRightParentAdpter;
    private List<CategoryInterface> leftFenLeiData;
    private LeftFenLeiAdapter leftFenLeiAdapter;
    private ArrayList<CategoryModel> categoryData;
    private SubCategoryAdapter subCategoryAdapter;
    private CategoryRecommendAdapter recommendAdapter;


    @Override
    protected View initView() {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_mall_type, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    protected void initData() {
        super.initData();
        setToolBar(llRoot);
        inintListview();
        requestData();
    }

    private void requestData() {
        HttpUtils.postUrlWithBaseResponse(getActivity(), Api.MALL_CATEGORY_RECOMMEND, null, false, new OnRequestSuccess<BaseResponse<CategoryRecommendModel>>() {
            @Override
            public void onSuccess(String url, BaseResponse<CategoryRecommendModel> data) {
                super.onSuccess(url, data);
                Log.e(TAG, "onSuccess: 解析正确");
                try {
                    handleData(data.getData());
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(), getString(R.string.mall_程序出错), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onErrorAnimate() {
                super.onErrorAnimate();
                Log.e(TAG, "onErrorAnimate: 解析错误");
            }
        });

    }

    /**
     * 处理请求到到分类推荐数据
     *
     * @param data 分类推荐数据
     */
    private void handleData(CategoryRecommendModel data) {
        List<CategoryModel> items = data.getItems();
        leftFenLeiData.clear();
        leftFenLeiData.addAll(items);

        CategoryModel categoryInterface = new CategoryModel();
        categoryInterface.setTitle(getString(R.string.mall_推荐));
        categoryInterface.setCate_id("-1");
        leftFenLeiData.add(0, categoryInterface);
        leftFenLeiAdapter.notifyDataSetChanged();

        recommendAdapter = new CategoryRecommendAdapter(getActivity(), data.getHot_products(), data.getHot_shops());
        recommendAdapter.setOnEventListener(new CategoryRecommendAdapter.OnEventListener() {
            @Override
            public void moreProduct(View view) {
                Intent intent = MallTopProductShopListActivity.generateIntent(getActivity()
                        , MallTopProductShopListActivity.PRODUCT_CONTENT_TYPE, true
                        , getString(R.string.mall_热销商品排行));
                getActivity().startActivity(intent);
            }

            @Override
            public void moreShop(View view) {
                Intent intent = MallTopProductShopListActivity.generateIntent(getActivity()
                        , MallTopProductShopListActivity.SHOP_CONTENT_TYPE, true
                        , getString(R.string.mall_TOP店铺排行));
                getActivity().startActivity(intent);
            }

            @Override
            public void jinDian(ShopItemModel shopItemModel) {
                try {

                    int shopId = Integer.parseInt(shopItemModel.getShop_id());
                    Intent intent = MallShopDetailsActivity.generateIntent(getActivity(), shopId);
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(), getString(R.string.mall_程序出错), Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            @Override
            public void onProductClick(String productId) {
                goProductDetail(productId);
            }
        });
        rvRight.setAdapter(recommendAdapter);

    }


    private void inintListview() {
        leftFenLeiData = new ArrayList<>();
        leftFenLeiAdapter = new LeftFenLeiAdapter(getActivity(), leftFenLeiData);
        rvLeft.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvLeft.setAdapter(leftFenLeiAdapter);
        rvRight.setLayoutManager(new LinearLayoutManager(getActivity()));
        categoryData = new ArrayList<CategoryModel>();
        subCategoryAdapter = new SubCategoryAdapter(getActivity(), categoryData);
        subCategoryAdapter.setOnItemClickListener((categoryModel,position)->{
            try {
                int cateId = Integer.parseInt(categoryModel.getCate_id());
                Intent intent = MallShopProduceListActivity.generateIntent(TypeFragment.this.getContext(), cateId, categoryModel.getTitle(),MallShopProduceListActivity.CATE_TYPE);
                startActivity(intent);
            }catch (Exception e){
                e.printStackTrace();
                Toast.makeText(mContext, getString(R.string.mall_程序出错), Toast.LENGTH_SHORT).show();
            }
        });
        leftFenLeiAdapter.setOnItemClickListener((cateInterface, position) -> {
            if (position == 0) {
                rvRight.setAdapter(recommendAdapter);
            } else {

                categoryData.clear();
                List<CategoryModel> childensImp2 = cateInterface.getChildensImp2();
                if (childensImp2 != null && childensImp2.size() > 0) {
                    categoryData.addAll(childensImp2);

                }
                rvRight.setAdapter(subCategoryAdapter);
            }

        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.iv_back, R.id.tv_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                break;
            case R.id.tv_search:
                startActivity(new Intent(getActivity(), MallShopProduceListActivity.class));
                break;
        }
    }

    /**
     * 跳转到商品详情界面
     *
     * @param productId
     */
    private void goProductDetail(String productId) {
        try {
            Intent intent = MallProductDetailActivity.generateIntent(getContext(), Integer.parseInt(productId));
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getContext(), getString(R.string.mall_程序出错), Toast.LENGTH_SHORT).show();
        }
    }
}
