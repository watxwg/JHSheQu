package com.jhcms.waimaiV3.activity;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.jhcms.common.galleryfinal.GlideImageLoader;
import com.jhcms.common.model.ProductModle;
import com.jhcms.common.model.SharedResponse;
import com.jhcms.common.model.ShopOrderModel;
import com.jhcms.common.model.TimeModel;
import com.jhcms.common.utils.Api;
import com.jhcms.common.utils.ToastUtil;
import com.jhcms.common.utils.Utils;
import com.jhcms.common.widget.FullyLinearLayoutManager;
import com.jhcms.common.widget.RoundImageView;
import com.jhcms.waimaiV3.MyApplication;
import com.jhcms.waimaiV3.R;
import com.jhcms.waimaiV3.adapter.EvaluationCommAdapter;
import com.jhcms.waimaiV3.adapter.PhotoAdapter;
import com.jhcms.waimaiV3.dialog.PickerViewDialog;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.request.BaseRequest;
import com.lzy.okgo.request.PostRequest;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.finalteam.galleryfinal.CoreConfig;
import cn.finalteam.galleryfinal.FunctionConfig;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.ThemeConfig;
import cn.finalteam.galleryfinal.model.PhotoInfo;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Wyj on 2017/5/24
 * TODO:
 */
public class MerchantEvaluationActivity extends SwipeBaseActivity implements View.OnTouchListener {

    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.riv_eva_shop_logo)
    RoundImageView rivEvaShopLogo;
    @Bind(R.id.tv_eva_shop_name)
    TextView tvEvaShopName;
    @Bind(R.id.rb_eva_shop)
    RatingBar rbEvaShop;
    @Bind(R.id.et_eva_shop)
    EditText etEvaShop;
    @Bind(R.id.rv_eva_comm)
    RecyclerView rvEvaComm;
    @Bind(R.id.tv_eva_staff_time)
    TextView tvEvaStaffTime;
    @Bind(R.id.ll_eva_staff_time)
    LinearLayout llEvaStaffTime;
    @Bind(R.id.rb_eva_staff)
    RatingBar rbEvaStaff;
    @Bind(R.id.tv_eva_integral)
    TextView tvEvaIntegral;
    @Bind(R.id.tv_eva_publish)
    TextView tvEvaPublish;
    @Bind(R.id.bottom)
    RelativeLayout bottom;
    @Bind(R.id.iv_take_photo)
    ImageView ivTakephoto;
    @Bind(R.id.rv_photo)
    RecyclerView rvPhoto;
    @Bind(R.id.ll_take_photo)
    LinearLayout llTakePhoto;
    @Bind(R.id.Staff)
    LinearLayout Staff;
    @Bind(R.id.photoLayout)
    LinearLayout photoLayout;
    private EvaluationCommAdapter commAdapter;
    private PickerViewDialog dialog;
    private int shopCountNum, staffCountNum;
    private String shopContent;
    private String deliveryTime;
    private PhotoAdapter photoAdapter;
    public static final String DELETE = "DELETE";
    public static final String PREVIEW = "PREVIEW";
    public static final String NODATA = "NODATA";
    private ShopOrderModel mDataModel;
    private ArrayList<ProductModle> mComDatalist = new ArrayList<>();
    private ArrayList<String> mphotoDatalist = new ArrayList<>();
    private ArrayList<TimeModel> mdataList;
    private void inintGallerFinal() {
        ThemeConfig theme = new ThemeConfig.Builder()
                .build();
        CoreConfig coreConfig = new CoreConfig.Builder(MerchantEvaluationActivity.this, new GlideImageLoader(), theme).build();
        GalleryFinal.init(coreConfig);
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_merchant_evaluation);
        ButterKnife.bind(this);

    }

    @Override
    protected void initData() {
             inintIntent();
            mphotoDatalist.add("photo");
            tvTitle.setText("评价");
            initShop();
            initComm();
            initStaff();
            final LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.width = Utils.getScreenW(this) / 5;
            params.height = Utils.getScreenW(this) / 5;
            ivTakephoto.setLayoutParams(params);
            photoAdapter = new PhotoAdapter(this);
            rvPhoto.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

            photoAdapter.setData(mphotoDatalist);
            rvPhoto.setAdapter(photoAdapter);
            photoAdapter.setOnItemClickListener(new PhotoAdapter.OnItemClickListener() {
                @Override
                public void ItemClickListener(String type, int position) {

                    switch (type) {
                        case DELETE:
                            mphotoDatalist.remove(position);
                            photoAdapter.setData(mphotoDatalist);
                            break;
                        case PREVIEW:
                            ToastUtil.show("预览第" + (position + 1) + "张图片");
                            break;
                        case NODATA:
                            openGallerySingle();
                            break;
                    }
                }
            });
            inintGallerFinal();

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    private ArrayList<String> trueData(ArrayList<String> mdata) {
        for (int i = 0; i < mdata.size(); i++) {
            if (mdata.get(i).equals("photo")) {
                mdata.remove(i);
            }
        }
        return mdata;
    }

    /**
     * 单选打开相册
     */
    private void openGallerySingle() {
        FunctionConfig functionConfig = new FunctionConfig.Builder()
                .setMutiSelectMaxSize(5)
                .setEnableCamera(true)
                .setCropHeight(100)
                .setCropWidth(100)
                .setSelected(trueData(mphotoDatalist))
                .build();
        GalleryFinal.openGalleryMuti(10001, functionConfig, new GalleryFinal.OnHanlderResultCallback() {
            @Override
            public void onHanlderSuccess(int reqeustCode, List<PhotoInfo> resultList) {
                if (reqeustCode == 10001) {
                    mphotoDatalist.clear();
                    for (int i = 0; i < resultList.size(); i++) {
                        mphotoDatalist.add(resultList.get(i).getPhotoPath());
                    }
                    photoAdapter.setData(mphotoDatalist);
                }
            }

            @Override
            public void onHanlderFailure(int requestCode, String errorMsg) {
            }
        });
    }

    private void inintIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            mDataModel = (ShopOrderModel) intent.getSerializableExtra("model");
        }
    }


    private void initShop() {
        /*评价后获得积分*/
        tvEvaIntegral.setText(mDataModel.jifen_total);
        /*店铺logo*/
      //  Utils.LoadStrPicture(this, Api.IMAGE_URL + mDataModel.shop_logo, rivEvaShopLogo);
        Glide.with(MerchantEvaluationActivity.this).load(Api.IMAGE_URL+mDataModel.getShop_logo()).into(rivEvaShopLogo);
        /*店铺名称*/
        tvEvaShopName.setText(mDataModel.shop_title);

        etEvaShop.setOnTouchListener(this);
    }

    private void initComm() {
        commAdapter = new EvaluationCommAdapter(this);
        mComDatalist.addAll(mDataModel.products);
        commAdapter.setmDatalist(mComDatalist);
        rvEvaComm.setAdapter(commAdapter);
        FullyLinearLayoutManager fullyLinearLayoutManager=new FullyLinearLayoutManager(MerchantEvaluationActivity.this);
        rvEvaComm.setNestedScrollingEnabled(false);
        rvEvaComm.setLayoutManager(fullyLinearLayoutManager);

    }


    private void initStaff() {
        if(mDataModel.spend_number!=null&&mDataModel.spend_number.length()>0){
            Staff.setVisibility(View.GONE);
        }
        tvEvaStaffTime.setText(mDataModel.time.get(0).getMinute()+"分钟（"+mDataModel.time.get(0).getDate()+"分钟)");
        deliveryTime=mDataModel.time.get(0).getMinute();
        tvEvaStaffTime.setTag(0);
        mdataList  = mDataModel.time;
        dialog = new PickerViewDialog(this);
        dialog.setmDatas(mdataList);
        dialog.setCanceledOnTouchOutside(true);

    }


    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        //触摸的是EditText并且当前EditText可以滚动则将事件交给EditText处理；否则将事件交由其父类处理
        if ((view.getId() == R.id.et_eva_shop && canVerticalScroll(etEvaShop))) {
            view.getParent().requestDisallowInterceptTouchEvent(true);
            if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                view.getParent().requestDisallowInterceptTouchEvent(false);
            }
        }
        return false;
    }

    /**
     * EditText竖直方向是否可以滚动
     *
     * @param editText 需要判断的EditText
     * @return true：可以滚动   false：不可以滚动
     */
    private boolean canVerticalScroll(EditText editText) {
        //滚动的距离 computeVerticalScrollOffset()方法
        int scrollY = editText.getScrollY();
        //控件内容的总高度 computeVerticalScrollRange()
        int scrollRange = editText.getLayout().getHeight();
        //控件实际显示的高度 computeVerticalScrollExtent()
        int scrollExtent = editText.getHeight() - editText.getCompoundPaddingTop() - editText.getCompoundPaddingBottom();
        //控件内容总高度与实际显示高度的差值
        int scrollDifference = scrollRange - scrollExtent;
        if (scrollDifference == 0) {
            return false;
        }
        return (scrollY > 0) || (scrollY < scrollDifference - 1);
    }


    @OnClick({R.id.ll_eva_staff_time, R.id.tv_eva_publish, R.id.iv_take_photo})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_eva_staff_time:
                if (dialog != null) {
                    dialog.show();
                    dialog.setOnSelectTimeListener(new PickerViewDialog.OnSelectTimeListener() {
                        @Override
                        public void selectTimeListener(String time, int selectId) {
                            deliveryTime = time;
                            tvEvaStaffTime.setText(mdataList.get(selectId).getMinute()+"分钟"+"("+mdataList.get(selectId).getDate()+"分钟)");
                            tvEvaStaffTime.setTag(selectId);
                        }
                    });
                }
                break;
            case R.id.iv_take_photo:
                break;
            case R.id.tv_eva_publish:
                /*商铺打分*/
                shopCountNum = (int) rbEvaShop.getRating();
//                Logger.d("商铺打分" + shopCountNum);
                /*商铺评论*/
                shopContent = etEvaShop.getText().toString();
//                Logger.d(shopContent);
                /*送达时间*/
//                Logger.d(deliveryTime);
                /*配送打分*/
                staffCountNum = (int) rbEvaStaff.getRating();
//                Logger.d("配送打分" + shopCountNum);
                if(shopCountNum<=0){
                    ToastUtil.show("请给商家打分！");
                    return;
                }
                if(!mDataModel.getPei_type().equals("3")) {
                    if (staffCountNum <= 0) {
                        ToastUtil.show("请给配送打分！");
                        return;
                    }
                }
                if(!mDataModel.getPei_type().equals("3")) {
                    if (TextUtils.isEmpty(deliveryTime) || deliveryTime.equals("")) {
                        ToastUtil.show("请选择送达时间");
                        return;
                    }
                }else {
                    deliveryTime="";
                }
                Map<Integer, Boolean> commScore = commAdapter.getCommScore();
                try {
                    final Gson gson = new Gson();
                    final JSONObject js = new JSONObject();
                    js.put("order_id", mDataModel.order_id);
                    if(shopContent!=null) {
                        js.put("content", shopContent);
                    }
                    if(shopCountNum!=0) {
                        js.put("score", shopCountNum);
                    }
                    if(staffCountNum!=0) {
                        js.put("score_peisong", staffCountNum);
                    }
                    if(!mDataModel.getPei_type().equals("3")) {
                        js.put("pei_time", mDataModel.time.get((Integer) tvEvaStaffTime.getTag()).getMinute());

                    }
                    JSONObject jsonObject=new JSONObject();
                      /*商品评分*/
                    for (int i = 0; i < commScore.size(); i++) {
                        Boolean aBoolean = commScore.get(i);
                        if (aBoolean == true) {
                            jsonObject.put(mComDatalist.get(i).getProduct_id(),1);
                        } else {
                            jsonObject.put(mComDatalist.get(i).getProduct_id(),0);
                        }
                    }
                    js.put("info",jsonObject);
                    ArrayList<String> data = trueData(mphotoDatalist);
                    String str = js.toString();
                    HttpHeaders headers = new HttpHeaders();
                    headers.put("User-Agent", MyApplication.useAgent);
                    final OkGo okGo = OkGo.getInstance();
                    PostRequest postRequest = okGo.addCommonHeaders(headers)
                            .post(Api.BASE_URL)
                            .tag(MerchantEvaluationActivity.this);
                    postRequest.params("API", Api.WAIMAI_ORDER_COMMMENT)
                            .params("CLIENT_API", Api.CLIENT_API)
                            .params("CLIENT_OS", Api.CLIENT_OS)
                            .params("CLIENT_VER", Api.CLIENT_VER)
                            .params("CITY_ID", Api.CITY_ID)
                            .params("TOKEN", Api.TOKEN)
                            .params("data", str);
                    for (int i = 0; i < data.size(); i++) {
                        if(data.size()>0) {
                            postRequest.params("photo" + i, new File(data.get(i)));
                        }
                    }
                    postRequest.execute(new StringCallback() {
                        @Override
                        public void onSuccess(String s, Call call, Response response) {
                            SharedResponse mshare = gson.fromJson(s, SharedResponse.class);
                           if(mshare.error.equals("0")){
                               ToastUtil.show("评价成功");
                               finish();
                           }else {
                               ToastUtil.show(mshare.message);
                           }
                        }

                        @Override
                        public void onBefore(BaseRequest request) {
                            super.onBefore(request);
                            showProgressDialog(MerchantEvaluationActivity.this);
                        }

                        @Override
                        public void onAfter(@Nullable String s, @Nullable Exception e) {
                            super.onAfter(s, e);
                            dismiss(MerchantEvaluationActivity.this);
                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }


                break;
        }
    }

}
