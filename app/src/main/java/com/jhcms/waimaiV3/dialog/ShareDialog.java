package com.jhcms.waimaiV3.dialog;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.jhcms.common.utils.ToastUtil;
import com.jhcms.common.utils.Utils;
import com.jhcms.waimaiV3.R;
import com.jhcms.waimaiV3.adapter.ShareAdapter;
import com.jhcms.waimaiV3.model.ShareItem;
import com.jhcms.waimaiV3.model.ShareType;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Wyj on 2017/5/16
 * TODO: 分享Dialog
 */
public class ShareDialog extends BottomSheetDialog {
    private Activity context;
    @Bind(R.id.rl_share)
    RecyclerView rlShare;
    @Bind(R.id.tv_cancle)
    TextView tvCancle;
    private UMImage umImageUrl;
    String[] shareTitle = {"微信", "QQ", "朋友圈", "QQ空间"};
    SHARE_MEDIA[] share_media = {SHARE_MEDIA.WEIXIN, SHARE_MEDIA.QQ, SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.QZONE};
    int[] sharePic = {R.mipmap.icon_share_wx, R.mipmap.icon_share_qq, R.mipmap.icon_share_wx_friend, R.mipmap.icon_share_qqzone};
    private List<ShareType> shareTypeList;
    private ShareAdapter shareAdapter;
    private ShareItem shareItems;

    public ShareDialog(Activity context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bottomsheet_share_dialog);
        ButterKnife.bind(this);
        Window win = getWindow();
        WindowManager.LayoutParams lp = win.getAttributes();
        lp.width = Utils.getScreenW(context);
        win.setAttributes(lp);
    }

    @Override
    public void show() {
        super.show();
        initData();
    }

    private void initData() {
        shareTypeList = new ArrayList<>();
        for (int i = 0; i < sharePic.length; i++) {
            ShareType shareType = new ShareType();
            shareType.setTitle(shareTitle[i]);
            shareType.setPic(sharePic[i]);
            shareTypeList.add(shareType);
        }
        shareAdapter = new ShareAdapter(context, shareTypeList);
        rlShare.setAdapter(shareAdapter);
        rlShare.setItemAnimator(new DefaultItemAnimator());
        rlShare.setLayoutManager(new GridLayoutManager(context, 4));
        if (shareItems.getLogo()!=null) {
            umImageUrl = new UMImage(context, shareItems.getLogo());
        }else {
            umImageUrl=new UMImage(context,shareItems.getREImageRocs());
        }


        final UMWeb umWeb = new UMWeb(shareItems.getUrl());
        umWeb.setTitle(shareItems.getTitle());/*标题*/
        umWeb.setThumb(umImageUrl);
        umWeb.setDescription(shareItems.getDescription());/*描述*/
        shareAdapter.setOnItemClickListener(new ShareAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(int position) {
                new ShareAction(context)
                        .withMedia(umWeb)
                        .setPlatform(share_media[position])
                        .setCallback(shareListener)
                        .share();
                dismiss();
            }
        });
    }

    @OnClick(R.id.tv_cancle)
    public void onClick() {
        dismiss();
    }

    private UMShareListener shareListener = new UMShareListener() {
        @Override
        public void onStart(SHARE_MEDIA share_media) {

        }

        @Override
        public void onResult(SHARE_MEDIA platform) {
            ToastUtil.show("分享成功");
        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            ToastUtil.show("失败" + t.getMessage());
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            ToastUtil.show("取消了");
        }
    };

    public void setItem(ShareItem item) {
        this.shareItems = item;
    }
}
