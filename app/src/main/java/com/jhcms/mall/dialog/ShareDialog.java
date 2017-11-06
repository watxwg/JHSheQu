package com.jhcms.mall.dialog;

import android.view.View;

import com.jhcms.common.utils.ToastUtil;
import com.jhcms.mall.model.ShareInfoModel;
import com.jhcms.waimaiV3.R;
import com.jhcms.waimaiV3.adapter.ShareAdapter;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

/**
 * 作者：WangWei
 * 日期：2017/8/25 17:19
 * 描述：分享对话框
 */

public class ShareDialog extends BaseDialog implements View.OnClickListener {
    public static final int WEI_XIN = 0;
    public static final int QQ = 1;
    public static final int PENG_YOU_QUAN = 2;
    public static final int KONG_JIAN = 3;
    SHARE_MEDIA[] share_media = {SHARE_MEDIA.WEIXIN, SHARE_MEDIA.QQ, SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.QZONE};
    private ShareInfoModel mShareModel;
    private UMImage umImageUrl;
    private UMShareListener shareListener;
    private UMWeb umWeb;

    @Override
    public void onStart() {
        setShowBoottom(true);
        super.onStart();
    }

    @Override
    public int provideLauoutId() {
        return R.layout.mall_dialog_layout_share;
    }

    public void setShareModel(ShareInfoModel shareModel) {
        mShareModel = shareModel;
    }

    @Override
    public void initDialog(ViewHolder viewHolder, BaseDialog dialog) {
        viewHolder.setOnClickListener(R.id.ll_wx, this);
        viewHolder.setOnClickListener(R.id.ll_qq, this);
        viewHolder.setOnClickListener(R.id.ll_pyq, this);
        viewHolder.setOnClickListener(R.id.ll_kj, this);
        viewHolder.setOnClickListener(R.id.tv_cancle,v -> dismiss());
        umWeb = new UMWeb(mShareModel.getShare_url());

        umWeb.setTitle(mShareModel.getShare_title());/*标题*/
        umWeb.setThumb(new UMImage(getContext(), mShareModel.getShare_photo()));
        umWeb.setDescription(mShareModel.getShare_content());/*描述*/
        shareListener = new UMShareListener() {
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
    }

    private void share(int position) {
        new ShareAction(getActivity())
                .withMedia(umWeb)
                .setPlatform(share_media[position])
                .setCallback(shareListener)
                .share();
        dismiss();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.ll_wx:
                share(0);
                break;
            case R.id.ll_qq:
                share(1);
                break;
            case R.id.ll_pyq:
                share(2);
                break;
            case R.id.ll_kj:
                share(3);
                break;
        }
    }


}
