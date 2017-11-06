package com.jhcms.mall.activity;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.jhcms.common.widget.BaseActivity;
import com.jhcms.common.widget.GridViewForScrollView;
import com.jhcms.mall.adapter.MallRefundGridViewAdapter;
import com.jhcms.waimaiV3.R;

import java.util.ArrayList;

/**
 * 申请退款页面
 */
public class MallRefundActivity extends BaseActivity {
    private View mHeadview;
    private ImageView mIvBack;
    private TextView mTitleContent;
    private  TextView mApplyService;
    private  TextView mSelectGoodStatu;
    private  TextView mGoodsmoneny;
    private EditText mEdReFoudContent;
    private  EditText mEdPhoneNumber;
    private  TextView mBtnSubmit;
    private PopupWindow mPopuwindowApplyService;
    private GridViewForScrollView mGridview;
    private ArrayList<String> mDatalist=new ArrayList<>();
    private MallRefundGridViewAdapter  mAdapter;
    private  int mImageNumber=3;
    /**
     *仅退款 false 代表 没被选中
     * true  代表被选中
     */
    private  Boolean mTvOnlyRefundFlag=false;
    /**
     *退货退款 false 代表 没被选中
     * true  代表被选中
     */
    private Boolean mRefundMoneyAndGoodFlag=false;
    @Override
    protected void initView() {
        setContentView(R.layout.activity_mall_refund);
    }

    @Override
    protected void initData() {
        inintmPopuwindowApplyService();
        inintGridview();
    }

    private void inintGridview() {
        mAdapter=new MallRefundGridViewAdapter(MallRefundActivity.this,mDatalist);
        mGridview.setAdapter(mAdapter);
        if(mDatalist.size()==0){

        }
        mAdapter.setClearImage(new MallRefundGridViewAdapter.ClearImage() {
            @Override
            public void cleaImage(String string) {
                mDatalist.remove(string);
                mAdapter.notifyDataSetChanged();
            }
        });
    }

    /**
     * 选择申请服务
     */
    private void inintmPopuwindowApplyService() {
        View view= LayoutInflater.from(MallRefundActivity.this).inflate(R.layout.mall_popuwindow_apply_service_layout,null);
        mPopuwindowApplyService=new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT,true);
        mPopuwindowApplyService.setContentView(view);
        mPopuwindowApplyService.setOutsideTouchable(false);
        mPopuwindowApplyService.setFocusable(true);// 获取焦点
        mPopuwindowApplyService.setClippingEnabled(false);
        final TextView mTvOnlyRefund= (TextView) view.findViewById(R.id.OnlyRefund);
        final TextView mRefundMoneyAndGood= (TextView) view.findViewById(R.id.RefundMoneyAndGoods);
        updatemPopuwindowApplyServiceStatu(mTvOnlyRefund,mRefundMoneyAndGood);;
        mTvOnlyRefund.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTvOnlyRefundFlag=true;
                mRefundMoneyAndGoodFlag=false;
                updatemPopuwindowApplyServiceStatu(mTvOnlyRefund,mRefundMoneyAndGood);
                mApplyService.setText("仅退款");
                if(mPopuwindowApplyService!=null&&mPopuwindowApplyService.isShowing()){
                    mPopuwindowApplyService.dismiss();
                }
            }
        });
        mRefundMoneyAndGood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTvOnlyRefundFlag=false;
                mRefundMoneyAndGoodFlag=true;
                updatemPopuwindowApplyServiceStatu(mTvOnlyRefund,mRefundMoneyAndGood);
                mApplyService.setText("退货退款");
                if(mPopuwindowApplyService!=null&&mPopuwindowApplyService.isShowing()){
                    mPopuwindowApplyService.dismiss();
                }
            }
        });

    }

    /**
     * 改变选中状态
     * @param mTvOnlyRefund
     * @param mRefundMoneyAndGood
     */
    public void updatemPopuwindowApplyServiceStatu(TextView mTvOnlyRefund,TextView mRefundMoneyAndGood){
        if(!mTvOnlyRefundFlag){
            mTvOnlyRefund.setCompoundDrawablesWithIntrinsicBounds(null,null,getResources().getDrawable(R.mipmap.index_selector_disable),null);
        }else {
            mTvOnlyRefund.setCompoundDrawablesWithIntrinsicBounds(null,null,getResources().getDrawable(R.mipmap.index_selector_enable),null);
        }

        if(!mRefundMoneyAndGoodFlag){
            mRefundMoneyAndGood.setCompoundDrawablesWithIntrinsicBounds(null,null,getResources().getDrawable(R.mipmap.index_selector_disable),null);
        }else {
            mRefundMoneyAndGood.setCompoundDrawablesWithIntrinsicBounds(null,null,getResources().getDrawable(R.mipmap.index_selector_enable),null);
        }

    }

    @Override
    protected void initActionBar() {
        mIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MallRefundActivity.this.finish();
            }
        });
        mTitleContent.setText("申请退款");
    }

    @Override
    protected void initFindViewById() {
        mHeadview=this.findViewById(R.id.title);
        mIvBack= (ImageView) mHeadview.findViewById(R.id.back_iv);
        mTitleContent= (TextView) mHeadview.findViewById(R.id.title_tv);
        mApplyService= (TextView) this.findViewById(R.id.applyService_tv);
        mSelectGoodStatu= (TextView) this.findViewById(R.id.SelectGoodStatu_tv);
        mGoodsmoneny= (TextView) this.findViewById(R.id.GoodsMoneny_tv);
        mEdReFoudContent= (EditText) this.findViewById(R.id.reFoudContent);
        mEdPhoneNumber= (EditText) this.findViewById(R.id.phonenamber_ed);
        mBtnSubmit= (TextView) this.findViewById(R.id.BtnCommoit);
        mGridview= (GridViewForScrollView) this.findViewById(R.id.mGridview);
    }

    @Override
    protected void initEvent() {
        inintmApplyServiceEvent();
    }

    private void inintmApplyServiceEvent() {
        mApplyService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mPopuwindowApplyService!=null&&!mPopuwindowApplyService.isShowing()){
                    mPopuwindowApplyService.showAtLocation(MallRefundActivity.this.findViewById(R.id.Top_lay), Gravity.CENTER,0,0);
                }
            }
        });

    }
}
