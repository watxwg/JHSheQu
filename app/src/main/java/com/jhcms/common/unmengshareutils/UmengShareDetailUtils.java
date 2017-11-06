package com.jhcms.common.unmengshareutils;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.jhcms.waimaiV3.R;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

import java.util.ArrayList;

/**
 * Created by admin on 2017/5/9.
 * 第一步拷贝 onActivityResult
 * 设置flag 是否自定义消息
 * 在回调 RemakeShare
 */
public class UmengShareDetailUtils {
    private Boolean flag=false;
    private Context context;
    private PopupWindow mSharePopuwinndow;
    private View mShareView;
    private GridView mGrideview;
    private TextView mTvCancel;
    private  RemakeShare remakeShare;
    private ShareModel mShareModel;
    public void setRemakeShare(RemakeShare remakeShare) {
        this.remakeShare = remakeShare;
    }

    public UmengShareDetailUtils(Context context,ShareModel shareModel) {
        this.context = context;
        this.mShareModel=shareModel;
        inintmSharePopuwinndow();
    }

    private  void  inintmSharePopuwinndow(){
        mShareView= LayoutInflater.from(context).inflate(R.layout.item_share_popuwindow_layout,null);
        LinearLayout layout= (LinearLayout) mShareView.findViewById(R.id.topLayout);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mSharePopuwinndow!=null&&mSharePopuwinndow.isShowing()) {
                    mSharePopuwinndow.dismiss();
                }
            }
        });
        mGrideview= (GridView) mShareView.findViewById(R.id.msharegridview);
        ArrayList<GridviewModel> list=new ArrayList<>();
        GridviewModel gridviewModel=new GridviewModel();
        gridviewModel.setImageRes(R.drawable.umeng_socialize_qq);
        gridviewModel.setShareName("QQ");
        list.add(gridviewModel);
        gridviewModel=new GridviewModel();
        gridviewModel.setImageRes(R.drawable.umeng_socialize_qzone);
        gridviewModel.setShareName("QQ空间");
        list.add(gridviewModel);
        gridviewModel=new GridviewModel();
        gridviewModel.setImageRes(R.drawable.umeng_socialize_wechat);
        gridviewModel.setShareName("微信");
        list.add(gridviewModel);
        gridviewModel=new GridviewModel();
        gridviewModel.setImageRes(R.drawable.umeng_socialize_wxcircle);
        gridviewModel.setShareName("朋友圈");
        list.add(gridviewModel);
        MShareUnmengGridviewAdapter mAdapter=new MShareUnmengGridviewAdapter(context,list);
        mGrideview.setAdapter(mAdapter);
        mTvCancel= (TextView) mShareView.findViewById(R.id.dismis);
        layout.getBackground().setAlpha(255/2);
        mSharePopuwinndow=new PopupWindow(mShareView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT,true);
        mSharePopuwinndow.setContentView(mShareView);
        mSharePopuwinndow.setOutsideTouchable(true);
        mSharePopuwinndow.setFocusable(true);// 获取焦点
        mSharePopuwinndow.setClippingEnabled(false);
        mSharePopuwinndow.setAnimationStyle(R.style.Sharepopwindow_anim_style);
        mTvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DismissSharePopuwinndow();
            }
        });
        mGrideview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(flag) {
                    remakeShare.RemakeSharedata(position);
                }else {
                UMImage thumb =  new UMImage(context, mShareModel.getShareImageRes());
                UMWeb web = new UMWeb(mShareModel.getUrl());
                web.setTitle(mShareModel.getTitle());//标题
                web.setThumb(thumb);  //缩略图
                web.setDescription(mShareModel.getDescription());//描述
                switch (position){
                    case 0:
                        if(mShareModel!=null) {
                            new ShareAction((Activity) context).withMedia(web).setPlatform(SHARE_MEDIA.QQ).setCallback(umShareListener).share();
                        }

                        break;
                    case 1:
                        if(mShareModel!=null) {
                            new ShareAction((Activity) context).withMedia(web).setPlatform(SHARE_MEDIA.QZONE).setCallback(umShareListener).share();
                        }
                        break;
                    case 2:
                        if(mShareModel!=null) {
                            new ShareAction((Activity) context).withMedia(web).setPlatform(SHARE_MEDIA.WEIXIN).setCallback(umShareListener).share();
                        }
                        break;
                    case 3:
                        if(mShareModel!=null) {
                            new ShareAction((Activity) context).withMedia(web).setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE).setCallback(umShareListener).share();
                        }
                        break;
                }
               }
            }
        });
    };

    public void ShowSharePopuwinndow(View view){
        if(mSharePopuwinndow!=null&&!mSharePopuwinndow.isShowing()) {
            Animation animation= AnimationUtils.loadAnimation(context,
                    R.anim.list_anim);
            LayoutAnimationController layoutAnimationController=new LayoutAnimationController(animation);
            mGrideview.setLayoutAnimation(layoutAnimationController);
            mSharePopuwinndow.showAtLocation(view, Gravity.BOTTOM, 0,com.jhcms.common.utils. Utils.getNavigationBarHeight(context));
        }
    }

    private  void   DismissSharePopuwinndow(){
        if(mSharePopuwinndow!=null&&mSharePopuwinndow.isShowing()){
            mSharePopuwinndow.dismiss();
        }
    }

    private UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onStart(SHARE_MEDIA platform) {
            //分享开始的回调
        }
        @Override
        public void onResult(SHARE_MEDIA platform) {


            Toast.makeText(context, platform + " 分享成功啦", Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText(context,platform + " 分享失败啦", Toast.LENGTH_SHORT).show();
            if(t!=null){
            }
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(context,platform + " 分享取消了", Toast.LENGTH_SHORT).show();
        }
    };
    public  interface  RemakeShare{
        void RemakeSharedata(int position);
    }

/**
 * TODO 特别注意
 * 最后在分享所在的Activity里复写onActivityResult方法,
 * 注意不可在fragment中实现，
 * 如果在fragment中调用分享，
 * 在fragment依赖的Activity中实现，
 * 如果不实现onActivityResult方法，
 * 会导致分享或回调无法正常进行
 */
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
//
//    }
}
