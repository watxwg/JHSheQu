package com.jhcms.waimaiV3.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;
import com.jhcms.common.utils.Api;
import com.jhcms.common.utils.Utils;
import com.jhcms.waimaiV3.R;
import com.jhcms.waimaiV3.activity.PicturePreviewActivity;

import java.util.ArrayList;


/**
 * Created by wangyujie
 * Date 2017/6/12.
 * TODO
 */

public class SamplePagerAdapter extends PagerAdapter {
    private final PicturePreviewActivity activity;
    private final ArrayList<String> imageList;

    public SamplePagerAdapter(PicturePreviewActivity activity, ArrayList<String> imageList) {
        this.activity = activity;
        this.imageList = imageList;
    }

    @Override
    public int getCount() {
        return imageList == null ? 0 : imageList.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        PhotoView photoView = new PhotoView(container.getContext());
        if (imageList.get(position).contains("http:")) {
            Utils.LoadStrPicture(activity, imageList.get(position), photoView);
            Glide.with(activity).load(imageList.get(position))
                    .error(R.mipmap.home_banner_default).override(Utils.getScreenW(activity), (int) (Utils.getScreenH(activity) * 0.5f)).into(photoView);
        } else {
            Glide.with(activity).load(Api.IMAGE_URL + imageList.get(position))
                    .error(R.mipmap.home_banner_default).override(Utils.getScreenW(activity), (int) (Utils.getScreenH(activity) * 0.5f)).into(photoView);
        }
        container.addView(photoView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        photoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.finish();
            }
        });
        return photoView;

    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
