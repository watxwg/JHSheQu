package com.jhcms.tuangou.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.classic.common.MultipleStatusView;
import com.google.gson.Gson;
import com.jhcms.common.model.Data_Group_Shop_Album;
import com.jhcms.common.model.Response_Group_Shop_Album;
import com.jhcms.common.utils.Api;
import com.jhcms.common.utils.HttpUtils;
import com.jhcms.common.utils.OnRequestSuccessCallback;
import com.jhcms.common.utils.ToastUtil;
import com.jhcms.tuangou.activity.MerchantAlbumActivity;
import com.jhcms.tuangou.adapter.MerchantAlbumAdapter;
import com.jhcms.waimaiV3.R;
import com.jhcms.waimaiV3.activity.PicturePreviewActivity;
import com.jhcms.waimaiV3.fragment.CustomerBaseFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by admin
 * on 2017/8/4.
 * TODO
 */

public class MerchantAlbumFragment extends CustomerBaseFragment {


    @Bind(R.id.content_view)
    RecyclerView contentView;
    @Bind(R.id.main_multiplestatusview)
    MultipleStatusView statusview;
    private boolean isPrepared = false;
    private int type;
    private String shopId;
    private MerchantAlbumAdapter albumAdapter;
    private ArrayList<String> images;

    @Override
    protected View initView() {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_waimai_available, null);
        ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();

    }

    @Override
    protected void initData() {
        type = getArguments().getInt("type", 0);
        shopId = getArguments().getString(MerchantAlbumActivity.SHOP_ID);
        albumAdapter = new MerchantAlbumAdapter(getActivity());

        contentView.setAdapter(albumAdapter);
        contentView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        requestAlbun();
        albumAdapter.setOnClickListener(new MerchantAlbumAdapter.OnClickListener() {
            @Override
            public void click(View view, int position) {
                Intent intent = new Intent(getActivity(), PicturePreviewActivity.class);
                intent.putExtra(PicturePreviewActivity.POSITION, position);
                intent.putStringArrayListExtra(PicturePreviewActivity.IMAGELIST, images);
                ActivityOptionsCompat compat = ActivityOptionsCompat.makeScaleUpAnimation(view,
                        view.getWidth() / 2, view.getHeight() / 2, 0, 0);
                ActivityCompat.startActivity(getActivity(), intent, compat.toBundle());
            }
        });
    }

    private void requestAlbun() {
        JSONObject object = new JSONObject();
        try {
            object.put("type", type);
            object.put("shop_id", shopId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HttpUtils.postUrl(getActivity(), Api.WAIMAI_TUAN_SHOP_ALBUM, object.toString(), false, new OnRequestSuccessCallback() {
            @Override
            public void onSuccess(String url, String Json) {
                Gson gson = new Gson();
                Response_Group_Shop_Album shopAlbum = gson.fromJson(Json, Response_Group_Shop_Album.class);
                if (shopAlbum.error.equals("0")) {
                    if (null != statusview) {
                        statusview.showContent();
                    }
                    List<Data_Group_Shop_Album.ItemsBean> items = shopAlbum.data.items;
                    if (null != items && items.size() > 0) {
                        albumAdapter.setData(items);
                        images = new ArrayList<String>();
                        for (int i = 0; i < items.size(); i++) {
                            images.add(items.get(i).photo);
                        }
                    } else {
                        statusview.showEmpty();
                    }
                } else {
                    ToastUtil.show(shopAlbum.message);
                    statusview.showError();
                }
            }

            @Override
            public void onBeforeAnimate() {
                statusview.showLoading();
            }

            @Override
            public void onErrorAnimate() {
                statusview.showError();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        isPrepared = true;
        lazyLoad();
    }

    @Override
    protected void lazyLoad() {
        if (isVisible && isPrepared) {
//            ToastUtil.show("看的见" + type);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
