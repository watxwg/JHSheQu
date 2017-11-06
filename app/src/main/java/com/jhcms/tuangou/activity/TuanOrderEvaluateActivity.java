package com.jhcms.tuangou.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jhcms.common.galleryfinal.GlideImageLoader;
import com.jhcms.common.model.SharedResponse;
import com.jhcms.common.utils.Api;
import com.jhcms.common.utils.ToastUtil;
import com.jhcms.common.utils.Utils;
import com.jhcms.common.widget.BaseActivity;
import com.jhcms.common.widget.GridViewForScrollView;
import com.jhcms.common.widget.RoundImageView;
import com.jhcms.waimaiV3.MyApplication;
import com.jhcms.waimaiV3.R;
import com.jhcms.waimaiV3.adapter.PhotoAdapter;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.request.BaseRequest;
import com.lzy.okgo.request.PostRequest;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.finalteam.galleryfinal.CoreConfig;
import cn.finalteam.galleryfinal.FunctionConfig;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.ThemeConfig;
import cn.finalteam.galleryfinal.model.PhotoInfo;
import okhttp3.Call;
import okhttp3.Response;

public class TuanOrderEvaluateActivity extends BaseActivity {
    @Bind(R.id.ratingbarId)
    RatingBar ratingbarId;
    @Bind(R.id.iv_take_photo)
    ImageView ivTakePhoto;
    @Bind(R.id.ll_take_photo)
    LinearLayout llTakePhoto;
    @Bind(R.id.rv_photo)
    RecyclerView rvPhoto;
    @Bind(R.id.photoLayout)
    LinearLayout photoLayout;
    @Bind(R.id.ShopEvaluate)
    EditText ShopEvaluate;
    @Bind(R.id.ImmediateEvaluation_tv)
    TextView ImmediateEvaluationTv;
    @Bind(R.id.Replylayout)
    LinearLayout Replylayout;
    @Bind(R.id.shopImage)
    RoundImageView shopImage;
    @Bind(R.id.ShopName)
    TextView ShopName;
    @Bind(R.id.iv_close)
    ImageView ivClose;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.content_text)
    TextView contentText;
    @Bind(R.id.PhonoGrild)
    GridViewForScrollView PhonoGrild;
    @Bind(R.id.shop_reply)
    TextView shopReply;
    private int falg; //flag 1去评价
    private String OrderId;
    private String Type;//订单类型  团购 tuan 买单 maidan
    private String shop_logo;
    private String shop_title;
    private ArrayList<String> mphotoDatalist = new ArrayList<>();
    private PhotoAdapter photoAdapter;
    public static final String DELETE = "DELETE";
    public static final String PREVIEW = "PREVIEW";
    public static final String NODATA = "NODATA";

    @Override
    protected void initView() {
        setContentView(R.layout.activity_tuan_order_evaluate);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        inintitent();
        IninTPotho();
        InintViewData();
    }

    private void InintViewData() {
        Utils.LoadUrlImage(TuanOrderEvaluateActivity.this, Api.IMAGE_URL + shop_logo, shopImage);
        ShopName.setText(shop_title);
    }

    private void IninTPotho() {
        mphotoDatalist.add("photo");
//        final LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//        params.width = Utils.getScreenW(this);
//        params.height = (int) (Utils.getScreenW(this) * 0.23);
//        rvPhoto.setLayoutParams(params);
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
//                        ToastUtil.show("预览第" + (position + 1) + "张图片");
                        break;
                    case NODATA:
                        openGallerySingle();
                        break;
                }
            }
        });
        inintGallerFinal();
    }

    private void inintGallerFinal() {
        ThemeConfig theme = new ThemeConfig.Builder()
                .build();
        CoreConfig coreConfig = new CoreConfig.Builder(TuanOrderEvaluateActivity.this, new GlideImageLoader(), theme).build();
        GalleryFinal.init(coreConfig);
    }

    /**
     * 单选打开相册
     */
    private void openGallerySingle() {
        ArrayList<String> date=new ArrayList<>();
    for (int i=0;i<mphotoDatalist.size();i++){
        if(!mphotoDatalist.get(i).equals("photo")){
            date.add(mphotoDatalist.get(i));
        }
    }
        FunctionConfig functionConfig = new FunctionConfig.Builder()
                .setMutiSelectMaxSize(5)
                .setEnableCamera(true)
                .setCropHeight(100)
                .setCropWidth(100)
                .setSelected(date)
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


    private ArrayList<String> trueData(ArrayList<String> mdata) {
        for (int i = 0; i < mdata.size(); i++) {
            if (mdata.get(i).equals("photo")) {
                mdata.remove(i);
            }
        }
        return mdata;
    }

    private void inintitent() {
        Intent intent = getIntent();
        OrderId = intent.getStringExtra("order_id");
        Type = intent.getStringExtra("type");
        shop_title = intent.getStringExtra("shop_title");
        shop_logo = intent.getStringExtra("shop_logo");
    }

    @Override
    protected void initActionBar() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tvTitle.setText("订单评价");
    }

    @Override
    protected void initFindViewById() {

    }

    @Override
    protected void initEvent() {
        ImmediateEvaluationTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Commintcomment();
            }
        });

    }

    private void Commintcomment() {//提交评价数据
        int Ratnum = (int) ratingbarId.getRating();
        String Comment = ShopEvaluate.getText().toString();
        ArrayList<String> data = trueData(mphotoDatalist);
        if (Ratnum <= 0) {
            ToastUtil.show("请给商家打分！");
            return;
        }

        try {
            JSONObject js = new JSONObject();
            js.put("order_id", OrderId);
            js.put("score", Ratnum);
            js.put("type", Type);
            if (Comment != null) {
                js.put("content", Comment);
            }
            HttpHeaders headers = new HttpHeaders();
            headers.put("User-Agent", MyApplication.useAgent);
            final OkGo okGo = OkGo.getInstance();
            PostRequest postRequest = okGo.addCommonHeaders(headers)
                    .post(Api.BASE_URL)
                    .tag(TuanOrderEvaluateActivity.this);
            postRequest.params("API", Api.TUAN_ORDER_COMMENT)
                    .params("CLIENT_API", Api.CLIENT_API)
                    .params("CLIENT_OS", Api.CLIENT_OS)
                    .params("CLIENT_VER", Api.CLIENT_VER)
                    .params("CITY_ID", Api.CITY_ID)
                    .params("TOKEN", Api.TOKEN)
                    .params("data", js.toString());
            for (int i = 0; i < data.size(); i++) {
                if (data.size() > 0) {
                    postRequest.params("photo" + i, new File(data.get(i)));
                }
            }
            postRequest.execute(new StringCallback() {
                @Override
                public void onSuccess(String s, Call call, Response response) {
                    Gson gson = new Gson();
                    SharedResponse mShopModel = gson.fromJson(s, SharedResponse.class);
                    if (mShopModel.error.equals("0")) {
                        ToastUtil.show("评价成功");
                        finish();
                    } else {
                        ToastUtil.show(mShopModel.message);
                    }
                }

                @Override
                public void onBefore(BaseRequest request) {
                    super.onBefore(request);
                    showProgressDialog(TuanOrderEvaluateActivity.this);
                }

                @Override
                public void onAfter(@Nullable String s, @Nullable Exception e) {
                    super.onAfter(s, e);
                    dismiss(TuanOrderEvaluateActivity.this);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            ToastUtil.show("网络出现问题！");
        }


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
