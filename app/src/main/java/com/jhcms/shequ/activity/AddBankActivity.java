package com.jhcms.shequ.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.pickerview.TimePickerView;
import com.google.gson.Gson;
import com.jhcms.common.model.BankModel;
import com.jhcms.common.model.SharedResponse;
import com.jhcms.common.utils.Api;
import com.jhcms.common.utils.HttpUtils;
import com.jhcms.common.utils.OnRequestSuccessCallback;
import com.jhcms.common.utils.ToastUtil;
import com.jhcms.waimaiV3.R;
import com.jhcms.waimaiV3.activity.SwipeBaseActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddBankActivity extends SwipeBaseActivity  {


    @Bind(R.id.iv_close)
    ImageView ivClose;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.seach_iv)
    ImageView seachIv;
    @Bind(R.id.tv_right_title)
    TextView tvRightTitle;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.Tv_BankName)
    EditText TvBankName;
    @Bind(R.id.Tv_BankNumber)
    EditText TvBankNumber;
    @Bind(R.id.Tv_BankTime)
    TextView TvBankTime;
    @Bind(R.id.Tv_BankSecurityCode)
    EditText TvBankSecurityCode;
    @Bind(R.id.Tv_AddBankSubmit)
    TextView TvAddBankSubmit;
    @Bind(R.id.Iv_BankOne)
    ImageView IvBankOne;
    @Bind(R.id.Iv_bankTwo)
    ImageView IvBankTwo;
    @Bind(R.id.Iv_BankThree)
    ImageView IvBankThree;
    @Bind(R.id.Tv_unbindCard)
    TextView TvUnbindCard;
    String year = "";
    String month="";
    private  int SelectCardIndex=1;
    private String CardModel="CardModel";
    private  String Type="Type";
    private  String typeWhere;
    private BankModel mCardModelWhere;
    @Override
    protected void initData() {
        InintIntent();
        InintAction();
        InintAllAddTextChangedListener();
        checkBtnBagDrawable();
        SelectCardIndex=1;
    }

    private void InintIntent() {
        Intent intent=getIntent();
        if(intent!=null){
          typeWhere=  intent.getStringExtra(Type);
            mCardModelWhere= (BankModel) intent.getSerializableExtra(CardModel);
        }
        if(typeWhere.equals("Update")){//Todo 解绑信用卡
            TvUnbindCard.setVisibility(View.VISIBLE);
            TvAddBankSubmit.setVisibility(View.GONE);
            SelectImage(Integer.parseInt(mCardModelWhere.getCard_type()));
            TvBankName.setText(mCardModelWhere.getCard_name());
            TvBankNumber.setText(mCardModelWhere.getCard_number());
            TvBankTime.setText(mCardModelWhere.getExp_month()+"/"+mCardModelWhere.getExp_year());
            TvBankSecurityCode.setText(mCardModelWhere.getCvc());
            TvBankName.setFocusable(false);
            TvBankNumber.setFocusable(false);
            TvBankTime.setFocusable(false);
            TvBankSecurityCode.setFocusable(false);
        }else {//Todo 添加信用卡
            TvUnbindCard.setVisibility(View.GONE);
            TvAddBankSubmit.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 编辑框监听
     */
    private void InintAllAddTextChangedListener() {
        TvBankName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                checkBtnBagDrawable();
            }
        });
        TvBankNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                checkBtnBagDrawable();
            }
        });
        TvBankSecurityCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                checkBtnBagDrawable();
            }
        });
    }

    private void InintAction() {
        tvTitle.setText("绑定信用卡");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_add_bank);
        ButterKnife.bind(this);
    }


    @OnClick ({R.id.Iv_BankOne, R.id.Iv_bankTwo,R.id.Iv_BankThree,R.id.Tv_AddBankSubmit,R.id.Tv_BankTime,R.id.Tv_unbindCard})
    public void  OnClick(View view) {
        switch (view.getId()){
            case R.id.Iv_BankOne:
                SelectImage(1);
                break;
            case R.id.Iv_bankTwo:
                SelectImage(2);
                break;
            case R.id.Iv_BankThree:
                SelectImage(3);
                break;
            case R.id.Tv_AddBankSubmit:
                RequestBindcard();
                break;
            case R.id.Tv_BankTime:
                showTimeDialg();
                break;
            case R.id.Tv_unbindCard:
                RequestUnbindCard();
                break;

        }
    }

    /**
     * 解绑信用卡
     */
    private void RequestUnbindCard() {
        JSONObject js=new JSONObject();
        try {
            js.put("card_id",mCardModelWhere.getCard_id());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HttpUtils.postUrl(AddBankActivity.this, Api.CardUnbind, js.toString(), false, new OnRequestSuccessCallback() {
            @Override
            public void onSuccess(String url, String Json) {
                dismiss(AddBankActivity.this);
                Gson gson=new Gson();
                SharedResponse shareResponse=gson.fromJson(Json,SharedResponse.class);
                if(shareResponse.error.equals("0")){
                    ToastUtil.show(getString(R.string.解绑成功));
                    finish();
                }else {
                    ToastUtil.show(shareResponse.message);
                }
            }

            @Override
            public void onBeforeAnimate() {
                showProgressDialog(AddBankActivity.this);
            }

            @Override
            public void onErrorAnimate() {
                dismiss(AddBankActivity.this);
            }
        });
    }

    private void RequestBindcard() {
        String bankName=TvBankName.getText().toString();
        String Bnaknumber=TvBankNumber.getText().toString();
        String BankSecurityCode=TvBankSecurityCode.getText().toString();
        JSONObject js=new JSONObject();
            try{

                js.put("card_type",SelectCardIndex+"");
                js.put("card_number",Bnaknumber);
                js.put("card_name",bankName);
                js.put("cvc",BankSecurityCode);
                js.put("mouth",month);
                js.put("year",year);
            }catch (Exception e){
                e.printStackTrace();
            }
        HttpUtils.postUrl(AddBankActivity.this, Api.CardBind, js.toString(), false, new OnRequestSuccessCallback() {
            @Override
            public void onSuccess(String url, String Json) {
                dismiss(AddBankActivity.this);
                switch (url){
                    case Api.CardBind:
                        try {
                            Gson gson=new Gson();
                            SharedResponse sharedResponse=gson.fromJson(Json,SharedResponse.class);
                            if(sharedResponse.error.equals("0")) {
                                ToastUtil.show(getString(R.string.添加成功));
                                finish();
                            }else {
                                ToastUtil.show(sharedResponse.message);
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                            ToastUtil.show(getString(R.string.网络出现了小问题));
                        }
                        break;
                }
            }

            @Override
            public void onBeforeAnimate() {
                showProgressDialog(AddBankActivity.this);
            }

            @Override
            public void onErrorAnimate() {
                dismiss(AddBankActivity.this);
            }

        });
    }


    private void showTimeDialg() {
        TimePickerView pvTime = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                String  time=getTime(date);
                year=time.substring(0,time.indexOf("-"));
                month=time.substring(time.indexOf("-")+1,time.lastIndexOf("-"));
                Log.i("---------------",year+"-----------"+month);
                TvBankTime.setText(month+"/"+year);
            }
        })
                .setType(new boolean[]{true, true, false, false, false, false})
                .isCyclic(true)
                .setLabel("", "", "", "", "", "")
                .isCenterLabel(false)
                .setDividerColor(Color.DKGRAY)
                .build();
        pvTime.show();



        /*TimePickerView  timepickerView=new TimePickerView(AddBankActivity.this, TimePickerView.Type.YEAR_MONTH);
        timepickerView.setCyclic(true);
        timepickerView.setOnTimeSelectListener(new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date) {

              *//*  SimpleDateFormat format = new SimpleDateFormat("MM/yyyy");
                 format.format(date);*//*


              String time=  getTime(date);
                year=time.substring(0,time.indexOf("-"));
                month=time.substring(time.indexOf("-"),time.lastIndexOf("-"));
                Log.i("--------------",year+"-----"+time+"--------"+month);
                TvBankTime.setText(month+"/"+year);
            }
        });
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        timepickerView.setRange(Calendar.YEAR-30,Calendar.YEAR+1);
        timepickerView.show();*/
    }


    /**
     *信用卡选择器
     * @param Postion
     */
    public void SelectImage(int Postion){
        SelectCardIndex=Postion;
       if(Postion==1){
           IvBankOne.setImageResource(R.mipmap.card_visa_once_selected);
           IvBankTwo.setImageResource(R.mipmap.card_master_two);
           IvBankThree.setImageResource(R.mipmap.card_ae_thre);
       }else if(Postion==2){
           IvBankOne.setImageResource(R.mipmap.card_visa_once);
           IvBankTwo.setImageResource(R.mipmap.card_master_two_select);
           IvBankThree.setImageResource(R.mipmap.card_ae_thre);
       }else if(Postion==3) {
           IvBankOne.setImageResource(R.mipmap.card_visa_once);
           IvBankTwo.setImageResource(R.mipmap.card_master_two);
           IvBankThree.setImageResource(R.mipmap.card_ae_thre_select);
       }
    }

    /**
     * 判断内容是否为空
     * @return
     */
    public boolean IsAllSetText(){
        if(TextUtils.isEmpty(TvBankName.getText())){
            return  false;
        }else if(TextUtils.isEmpty(TvBankNumber.getText())){
            return  false;
        }else if(TextUtils.isEmpty(TvBankSecurityCode.getText())) {
            return false;
        }else  if(TextUtils.isEmpty(year)){
            return false;
        }
        return  true;
    }

    public void  checkBtnBagDrawable(){
        if(IsAllSetText()){
            TvAddBankSubmit.setBackground(AddBankActivity.this.getResources().getDrawable(R.drawable.bg_theme));
            TvAddBankSubmit.setFocusable(true);
        }else {
            TvAddBankSubmit.setBackground(AddBankActivity.this.getResources().getDrawable(R.drawable.bg_theme_gray));
            TvAddBankSubmit.setFocusable(false);
        }
    }
    private String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(date);
    }


}
