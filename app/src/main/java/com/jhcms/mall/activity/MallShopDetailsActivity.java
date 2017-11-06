package com.jhcms.mall.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.text.TextUtilsCompat;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.maps.model.Text;
import com.jhcms.common.utils.Api;
import com.jhcms.common.utils.HttpUtils;
import com.jhcms.common.utils.OnRequestSuccess;
import com.jhcms.common.utils.Utils;
import com.jhcms.common.widget.RoundImageView;
import com.jhcms.mall.adapter.MallShopDetailPagerAdapter;
import com.jhcms.mall.dialog.ShareDialog;
import com.jhcms.mall.fragment.ShopHomeFragment;
import com.jhcms.mall.fragment.ShopProductFragment;
import com.jhcms.mall.model.BaseResponse;
import com.jhcms.mall.model.CategoryModel;
import com.jhcms.mall.model.ShareInfoModel;
import com.jhcms.mall.model.ShopInfoModel;
import com.jhcms.waimaiV3.R;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MallShopDetailsActivity extends BaseActivity {
    private static final String SHOP_ID = "shopID";
    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.et_search)
    EditText etSearch;
    @Bind(R.id.tv_catagory)
    TextView tvCatagory;
    @Bind(R.id.tv_share)
    TextView tvShare;
    @Bind(R.id.title_layout)
    LinearLayout titleLayout;
    @Bind(R.id.iv_bg)
    ImageView ivBg;
    @Bind(R.id.bt_focus)
    Button btFocus;
    @Bind(R.id.tv_focus_person_num)
    TextView tvFocusPersonNum;
    @Bind(R.id.iv_shop_logo)
    RoundImageView ivShopLogo;
    @Bind(R.id.vp_shop_detail)
    ViewPager vpShopDetail;
    @Bind(R.id.tv_shop_name)
    TextView tvShopName;
    @Bind(R.id.tl_title)
    TabLayout tlTitle;
    private int mShopId;
    private boolean isFocus = false;
    private ArrayList<CategoryModel> categoryData;
    private ShareInfoModel mShareInfoModel;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_mall_shop_details);
        ButterKnife.bind(this);
        int dimen = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 15, getResources().getDisplayMetrics());
        Rect bounds = new Rect(0, 0, dimen, dimen);
        Drawable cate = getResources().getDrawable(R.mipmap.mall_btn_cate2);
        Drawable share = getResources().getDrawable(R.mipmap.mall_navbar_btn_share);
        cate.setBounds(bounds);
        share.setBounds(bounds);
        tvCatagory.setCompoundDrawables(null, cate, null, null);
        tvShare.setCompoundDrawables(null, share, null, null);
        categoryData=new ArrayList<>();
        etSearch.setOnKeyListener((v,keyCode,event)->{
            if(keyCode== KeyEvent.KEYCODE_ENTER&&event.getAction()== MotionEvent.ACTION_UP){
                if(TextUtils.isEmpty(etSearch.getText().toString())){
                    return true;
                }else{
                    ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(MallShopDetailsActivity.this.getCurrentFocus()
                                    .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    Intent intent = MallShopDetailCatagoryActivity
                            .generateIntent(MallShopDetailsActivity.this
                            , mShopId, null, null, null);
                    startActivity(intent);
                }
            }
            return false;
        });
    }

    public static Intent generateIntent(Context context, int shopId) {
        Intent intent = new Intent(context, MallShopDetailsActivity.class);
        intent.putExtra(SHOP_ID, shopId);
        return intent;
    }

    @Override
    protected void initData() {
        inintIntent();
    }


    private void inintIntent() {


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
        mShopId = getIntent().getIntExtra(SHOP_ID, -1);
        List<Fragment> fragments = new ArrayList<>();
        ShopHomeFragment homeFragment = ShopHomeFragment.newInstance(mShopId);
        ShopProductFragment allProductFragment = ShopProductFragment.newInstance(mShopId, ShopProductFragment.TYPE_ALL);
        ShopProductFragment hotProductFragment = ShopProductFragment.newInstance(mShopId, ShopProductFragment.TYPE_HOT);
        ShopProductFragment newProductFragment = ShopProductFragment.newInstance(mShopId, ShopProductFragment.TYPE_NEW);
        fragments.add(homeFragment);
        fragments.add(allProductFragment);
        fragments.add(hotProductFragment);
        fragments.add(newProductFragment);
        String[] stringArray = getResources().getStringArray(R.array.shop_detail_titles);
        MallShopDetailPagerAdapter pagerAdapter = new MallShopDetailPagerAdapter(getSupportFragmentManager(), fragments, Arrays.asList(stringArray));
        vpShopDetail.setAdapter(pagerAdapter);
        tlTitle.setupWithViewPager(vpShopDetail);

    }

    @OnClick({R.id.iv_back, R.id.tv_catagory, R.id.tv_share, R.id.bt_focus})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;

            case R.id.tv_catagory:
                Intent intent = MallShopDetailCatagoryListActivity.generateIntent(this, mShopId);
                startActivity(intent);

                break;
            case R.id.tv_share:
                share();
                break;
            case R.id.bt_focus:
                if(TextUtils.isEmpty(Api.TOKEN)){
                    Utils.GoLogin(this);
                    return;
                }
                requestCollect(mShopId,!isFocus);
                break;
        }
    }
    //分享
    private void share(){
        if (mShareInfoModel == null) {
            return;
        }

        ShareDialog shareDialog = new ShareDialog();
        shareDialog.setShareModel(mShareInfoModel);
        shareDialog.setOutCanclable(true);
        shareDialog.show(getSupportFragmentManager(), "dialog");
    }

    /**
     * 由Fragment调用
     * @param shopInfoModel
     * @param shareInfoModel
     */
    public void setShopDetailInfo(ShopInfoModel shopInfoModel,ShareInfoModel shareInfoModel) {
        Utils.LoadStrPicture(this, Api.IMAGE_URL + shopInfoModel.getLogo(), ivShopLogo);
        tvShopName.setText(shopInfoModel.getTitle());
        tvFocusPersonNum.setText(getString(R.string.mall_人, shopInfoModel.getCollects()));
        mShareInfoModel = shareInfoModel;

        switchBtnStyle("1".equals(shopInfoModel.getCollect()));


    }
    public void setCategoryData(List<CategoryModel> categoryData){
        this.categoryData.clear();
        this.categoryData.addAll(categoryData);
    }

    /**
     * 切换收藏按钮显示
     * @param isFocus
     */
    private void switchBtnStyle(boolean isFocus){
        int size = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 12, getResources().getDisplayMetrics());
        Rect bound = new Rect(0, 0, size, size);
        Drawable d = null;
        if(isFocus){
            d = getResources().getDrawable(R.mipmap.mall_icon_focus);
            btFocus.setBackgroundResource(R.drawable.mall_bg_btn_5);
            btFocus.setTextColor(Color.parseColor(getString(R.string.mall_color_f96720)));
            btFocus.setText(R.string.mall_已关注);
        }else {
            d = getResources().getDrawable(R.mipmap.mall_icon_not_focus);
            btFocus.setBackgroundResource(R.drawable.mall_bg_btn_2);
            btFocus.setTextColor(Color.WHITE);
            btFocus.setText(R.string.mall_关注);
        }
        d.setBounds(bound);
        btFocus.setCompoundDrawables(d, null, null, null);
        this.isFocus = isFocus;
    }
    /**
     * 收藏店铺
     * @param shopId
     * @param isCollect
     */
    private void requestCollect(int shopId,boolean isCollect){
        String paramData = null;
        try{
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("type","weidian");
            jsonObject.put("status",isCollect?"1":"0");
            jsonObject.put("can_id",shopId);
            paramData=jsonObject.toString();
        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(this, getString(R.string.mall_程序出错), Toast.LENGTH_SHORT).show();
            return;
        }
        HttpUtils.postUrlWithBaseResponse(this,Api.MALL_COLLECT,paramData,true,new OnRequestSuccess<BaseResponse<String>>(){
            @Override
            public void onSuccess(String url, BaseResponse<String> data) {
                super.onSuccess(url, data);
                Toast.makeText(MallShopDetailsActivity.this, data.getMessage(), Toast.LENGTH_SHORT).show();
                switchBtnStyle(isCollect);
            }
        });

    }

}
