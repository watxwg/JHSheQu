package com.jhcms.waimaiV3.activity;

import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
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
import com.jhcms.common.model.ProductModle;
import com.jhcms.common.model.Response_LookMerchantEvaluation;
import com.jhcms.common.model.photoModel;
import com.jhcms.common.utils.Api;
import com.jhcms.common.utils.HttpUtils;
import com.jhcms.common.utils.OnRequestSuccessCallback;
import com.jhcms.common.utils.ToastUtil;
import com.jhcms.common.utils.Utils;
import com.jhcms.common.widget.GridViewForScrollView;
import com.jhcms.common.widget.RoundImageView;
import com.jhcms.waimaiV3.R;
import com.jhcms.waimaiV3.adapter.EvaluationCommAdapter;
import com.jhcms.waimaiV3.adapter.PhontoGrildviewAdapter;

import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * TODO 评论 和查看评价xml是一个界面
 */
public class LookMerchantEvaluationActivity extends SwipeBaseActivity implements View.OnTouchListener, OnRequestSuccessCallback {

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
    private String deliveryTime;
    private String comment_id;
    @Bind(R.id.content_text)
    TextView mContentText;
    @Bind(R.id.PhonoGrild)
    GridViewForScrollView mPhontoGrildview;
    @Bind(R.id.songdaImage)
    ImageView songdaImage;
    @Bind(R.id.shop_reply)
    TextView shop_reply;
    private ArrayList<photoModel> mPhoneDataList = new ArrayList<>();
    private PhontoGrildviewAdapter mPhotoAdapter;
    private ArrayList<ProductModle> mComDatalist = new ArrayList<>();
    private ArrayList<String> imageList;
    private String peitype="";

    @Override
    protected void initView() {
        setContentView(R.layout.activity_merchant_evaluation);
        ButterKnife.bind(this);
        photoLayout.setVisibility(View.GONE);
        etEvaShop.setVisibility(View.GONE);
        bottom.setVisibility(View.GONE);
        mContentText.setVisibility(View.VISIBLE);
    }

    @Override
    protected void initData() {
        imageList = new ArrayList<String>();
        inintIntent();
        mPhotoAdapter = new PhontoGrildviewAdapter(mPhoneDataList, LookMerchantEvaluationActivity.this);
        mPhontoGrildview.setAdapter(mPhotoAdapter);

        mPhotoAdapter.setOnPicItemClickListener(new PhontoGrildviewAdapter.OnPicItemClickListener() {
            @Override
            public void picItemClick(View view, int position) {
                Intent intent = new Intent(LookMerchantEvaluationActivity.this, PicturePreviewActivity.class);
                intent.putExtra(PicturePreviewActivity.POSITION, position);
                intent.putStringArrayListExtra(PicturePreviewActivity.IMAGELIST, imageList);
                ActivityOptionsCompat compat = ActivityOptionsCompat.makeScaleUpAnimation(view,
                        view.getWidth() / 2, view.getHeight() / 2, 0, 0);
                ActivityCompat.startActivity(LookMerchantEvaluationActivity.this, intent,
                        compat.toBundle());
            }
        });


        tvTitle.setText("查看评价");
//        initShop();
//        initComm();
//        initStaff();
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void inintIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            comment_id = intent.getStringExtra("comment_id");
            peitype=intent.getStringExtra("peitype");
        }
        requestcomment();
    }

    private void requestcomment() {
        try {
            JSONObject js = new JSONObject();
            js.put("comment_id", comment_id);
            String str = js.toString();
            HttpUtils.postUrl(LookMerchantEvaluationActivity.this, Api.WAIMAI_COMMENT_DETAIL, str, true, this);
        } catch (Exception e) {
            e.printStackTrace();
        }
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

                break;
        }
    }

    @Override
    public void onSuccess(String url, String Json) {
        Gson gson = new Gson();
        switch (url) {
            case Api.WAIMAI_COMMENT_DETAIL:
                try {
                    Response_LookMerchantEvaluation mModel = gson.fromJson(Json, Response_LookMerchantEvaluation.class);
                    if (mModel.error.equals("0")) {
                        inintevaluation_shop(mModel);
                        initComm(mModel);
                        initStaff(mModel);
                    } else {
                        ToastUtil.show(mModel.message);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    private void initStaff(Response_LookMerchantEvaluation mModel) {
        if (!peitype.equals("3")) {
            tvEvaStaffTime.setText(mModel.getData().getDetail().getPei_time() + "分钟（" + Utils.convertDate(Long.parseLong(mModel.getData().getDetail().getSongda()), "mm:ss") + ")送达");
            songdaImage.setVisibility(View.GONE);
            rbEvaStaff.setIsIndicator(true);
            rbEvaStaff.setRating(Integer.parseInt(mModel.getData().getDetail().getScore_peisong()));
        } else {
            Staff.setVisibility(View.GONE);
            tvEvaStaffTime.setVisibility(View.GONE);
        }
    }

    private void initComm(Response_LookMerchantEvaluation mModel) {
        commAdapter = new EvaluationCommAdapter(this);
        mComDatalist.addAll(mModel.getData().getDetail().getProduct_list());
        commAdapter.setmDatalist(mComDatalist);
        commAdapter.setIsevaluate(false);
        rvEvaComm.setAdapter(commAdapter);
        rbEvaShop.setRating(Integer.parseInt(mModel.getData().getDetail().getScore()));
        rbEvaShop.setIsIndicator(true);
        rvEvaComm.setLayoutManager(new LinearLayoutManager(this));
    }

    private void inintevaluation_shop(Response_LookMerchantEvaluation mModel) {
        /*店铺logo*/
       // Utils.LoadStrPicture(this, Api.IMAGE_URL + mModel.getData().getDetail().getShop_logo(), rivEvaShopLogo);
        Glide.with(LookMerchantEvaluationActivity.this).load(Api.IMAGE_URL+mModel.getData().getDetail().getShop_logo()).into(rivEvaShopLogo);
        /*店铺名称*/
        tvEvaShopName.setText(mModel.getData().getDetail().getShop_title());
        if (mModel.getData().getDetail().getContent() != null && mModel.getData().getDetail().getContent().length() > 0) {
            mContentText.setText(mModel.getData().getDetail().getContent());
        } else {
            mContentText.setVisibility(View.GONE);
        }
        etEvaShop.setOnTouchListener(this);
        mPhoneDataList.addAll(mModel.getData().getDetail().getPhotos());
        for (int i = 0; i < mPhoneDataList.size(); i++) {
            imageList.add(mPhoneDataList.get(i).getPhoto());
        }
        mPhotoAdapter.notifyDataSetChanged();
        if (mModel.getData().getDetail().getReply() != null && mModel.getData().getDetail().getReply().length() > 0) {
            shop_reply.setVisibility(View.VISIBLE);
            shop_reply.setText("商家回复：" + mModel.getData().getDetail().getReply());
        }


    }

    @Override
    public void onBeforeAnimate() {

    }

    @Override
    public void onErrorAnimate() {

    }
}
