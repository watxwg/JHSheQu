package com.jhcms.mall.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.util.Util;
import com.jhcms.common.utils.Utils;
import com.jhcms.mall.adapter.viewholder.CommonViewHolder;
import com.jhcms.waimaiV3.R;

import java.util.HashMap;
import java.util.List;

/**
 * 作者：WangWei
 * 日期：2017/9/20 11:33
 * 描述：图片广告适配器
 */

public class AdAdapter<T extends AdAdapter.ImageUrlProvider> extends CommonAdapter<T> {
    private static final String TAG = AdAdapter.class.getSimpleName();
    public static final int TYPE_3_1 = 0x31;
    public static final int TYPE_3_2 = 0x32;
    public static final int TYPE_2_2 = 0x33;
    public static final int TYPE_1_2_2 = 0x34;
    private final HashMap<Integer, Integer> mLineHeight;
    private int mDisplayType;
    private OnItemClickListener<T> mListener;

    /**
     * @param context
     * @param data        数据
     * @param lineHight   每行的高度，行数从0开始
     * @param displayType
     */
    public AdAdapter(Context context, List<T> data, @NonNull HashMap<Integer, Integer> lineHight, @NonNull Integer displayType) {
        super(context, data, R.layout.mall_list_item_ad);
        mDisplayType = displayType;
        mLineHeight = lineHight;
    }

    /**
     * 根据显示的类型创建布局管理器
     *
     * @return
     */
    public GridLayoutManager getLayoutManager() {
        GridLayoutManager gridLayoutManager = null;
        if (mDisplayType == TYPE_3_1) {
            gridLayoutManager = new GridLayoutManager(mContext, 3);
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    if (position < 3) {
                        return 1;
                    } else {

                        return 3;
                    }
                }
            });
        } else if (mDisplayType == TYPE_3_2) {
            gridLayoutManager = new GridLayoutManager(mContext, 6);
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    if (position < 3) {
                        return 2;
                    } else {
                        return 3;
                    }
                }
            });
        } else if (mDisplayType == TYPE_2_2) {
            gridLayoutManager = new GridLayoutManager(mContext, 2);
        } else if (mDisplayType == TYPE_1_2_2) {
            gridLayoutManager = new GridLayoutManager(mContext, 2);
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    if (position == 0) {
                        return 2;
                    } else {

                        return 1;
                    }
                }
            });
        }
        return gridLayoutManager;
    }

    @Override
    public void convertViewData(CommonViewHolder holder, T bean) {
        ImageView view = holder.<ImageView>getView(R.id.iv_ad);
        Utils.LoadStrPicture(mContext, bean.getImageUrl(), view);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        int adapterPosition = holder.getAdapterPosition();
        int lineNum = getLineNum(adapterPosition);
        Integer integer = mLineHeight.get(Integer.valueOf(lineNum));
        if (integer != null) {
            layoutParams.height = integer;
            Log.e(TAG, "convertViewData: " + layoutParams.height);
            view.setLayoutParams(layoutParams);
        }
        if (mLineHeight != null) {
            holder.itemView.setOnClickListener(v -> mListener.OnItemClickListener(bean, adapterPosition));
        }
    }

    public void setOnItemClickListener(OnItemClickListener<T> listener) {
        mListener = listener;
    }

    /**
     * 获取当前item所在行数
     *
     * @param postion
     * @return
     */
    public int getLineNum(int postion) {
        if (postion == -1) {
            return -1;
        }
        if (mDisplayType == TYPE_3_1) {
            return (postion / 3);
        } else if (mDisplayType == TYPE_3_2) {
            return (postion / 3);
        } else if (mDisplayType == TYPE_2_2) {
            return (postion / 2);
        } else if (mDisplayType == TYPE_1_2_2) {
            return (postion+1)/2;
        }
        return -1;
    }

    public interface ImageUrlProvider {
        /**
         * 需要返回完整的图片链接
         *
         * @return
         */
        String getImageUrl();
    }
}
