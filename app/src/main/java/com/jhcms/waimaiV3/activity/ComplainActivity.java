package com.jhcms.waimaiV3.activity;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jhcms.common.galleryfinal.GlideImageLoader;
import com.jhcms.common.model.SharedResponse;
import com.jhcms.common.utils.Api;
import com.jhcms.common.utils.ToastUtil;
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

public class ComplainActivity extends SwipeBaseActivity {
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.phone)
    TextView mTvPhone;
    @Bind(R.id.content)
    EditText mEdContent;
    @Bind(R.id.rv_photo)
    RecyclerView  mPhoneView;
    @Bind(R.id.submit_bt)
    TextView mTvSubmit;
    private  String orderId;
    private  String phoneNum;
    private PhotoAdapter photoAdapter;
    public static final String DELETE = "DELETE";
    public static final String PREVIEW = "PREVIEW";
    public static final String NODATA = "NODATA";
    private ArrayList<String> mphotoDatalist = new ArrayList<>();
    @Override
    protected void initData() {
        tvTitle.setText("投诉");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        inintIntent();
        BindViewData();
        inintPhoto();
    }

    private void inintPhoto() {
        photoAdapter = new PhotoAdapter(this);
        mPhoneView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        photoAdapter.setData(mphotoDatalist);
        mPhoneView.setAdapter(photoAdapter);
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
        inintEvent();
    }

    private void inintEvent() {
        mTvSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequertData();
            }


        });
    }
    private void RequertData() {
        if(TextUtils.isEmpty(mEdContent.getText())){
            ToastUtil.show("内容不能为空！");
            return;
        }

        if(trueData(mphotoDatalist).size()<0){
            ToastUtil.show("请选择图片！");
            return;
        }
        try {
            JSONObject js=new JSONObject();
            js.put("order_id",orderId);
            js.put("content",mEdContent.getText().toString());
            String str=js.toString();
            HttpHeaders headers = new HttpHeaders();
            headers.put("User-Agent", MyApplication.useAgent);
            final OkGo okGo = OkGo.getInstance();
            PostRequest postRequest = okGo.addCommonHeaders(headers)
                    .post(Api.BASE_URL)
                    .tag(ComplainActivity.this);

            postRequest.params("API", Api.complaint)
                    .params("CLIENT_API", Api.CLIENT_API)
                    .params("CLIENT_OS", Api.CLIENT_OS)
                    .params("CLIENT_VER", Api.CLIENT_VER)
                    .params("CITY_ID", Api.CITY_ID)
                    .params("TOKEN", Api.TOKEN)
                    .params("data", str);
            ArrayList<String>data=trueData(mphotoDatalist);
            for (int i = 0; i < data.size(); i++) {
                postRequest.params("photo" + i, new File(data.get(i)));
            }

            postRequest.execute(new StringCallback() {
                @Override
                public void onSuccess(String s, Call call, Response response) {
                    Gson gson=new Gson();
                    SharedResponse mshare = gson.fromJson(s, SharedResponse.class);
                    if(mshare.error.equals("0")&&mshare.message.equals("success")){
                        ToastUtil.show("投诉成功！");
                        finish();
                    }else {
                        ToastUtil.show(mshare.message);
                    }

                }

                @Override
                public void onAfter(@Nullable String s, @Nullable Exception e) {
                    super.onAfter(s, e);
                    dismiss(ComplainActivity.this);
                }

                @Override
                public void onBefore(BaseRequest request) {
                    super.onBefore(request);
                    showProgressDialog(ComplainActivity.this);
                }
            });



        }catch (Exception e){

        }
    }
    private void inintGallerFinal() {
        ThemeConfig theme = new ThemeConfig.Builder()
                .build();
        CoreConfig coreConfig = new CoreConfig.Builder(ComplainActivity.this, new GlideImageLoader(), theme).build();
        GalleryFinal.init(coreConfig);
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
    private ArrayList<String> trueData(ArrayList<String> mdata) {
        for (int i = 0; i < mdata.size(); i++) {
            if (mdata.get(i).equals("photo")) {
                mdata.remove(i);
            }
        }
        return mdata;
    }


    private void BindViewData() {
        mTvPhone.setText(phoneNum);
    }

    private void inintIntent() {
        Intent intent=getIntent();
        if(intent!=null){
            orderId=intent.getStringExtra("orderid");
            phoneNum=intent.getStringExtra("phone");

        }
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_complain);
        ButterKnife.bind(this);

    }
}
