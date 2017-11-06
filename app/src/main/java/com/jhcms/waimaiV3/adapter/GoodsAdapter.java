package com.jhcms.waimaiV3.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.coorchice.library.SuperTextView;
import com.jhcms.common.stickylistheaders.StickyListHeadersAdapter;
import com.jhcms.common.utils.Api;
import com.jhcms.common.utils.ToastUtil;
import com.jhcms.common.utils.Utils;
import com.jhcms.waimaiV3.R;
import com.jhcms.waimaiV3.activity.WaiMaiShopActivity;
import com.jhcms.waimaiV3.model.Goods;
import com.jhcms.waimaiV3.model.Type;

import java.text.NumberFormat;
import java.util.List;


/**
 * Created by Wyj on 2017/5/8
 * TODO:商家详情-->商品-->右侧商品 ItemAdapter
 */
public class GoodsAdapter extends BaseAdapter implements StickyListHeadersAdapter {

    private WaiMaiShopActivity mContext;
    private NumberFormat nf;
    private LayoutInflater mInflater;
    private OnGuiGeClickListener guiGeClickListener;
    private static final int GUIGE = 1;
    private static final int NORMAL = 0;
    private List<Type> data;
    private List<Goods> goodsList;
    /**
     * 优惠券
     */
    private OnItemClickListener itemClickListener;

    public void setData(List<Type> data, List<Goods> goodsList) {
        this.data = data;
        this.goodsList = goodsList;
        notifyDataSetChanged();
    }

    /**
     * 点击规格接口
     */
    public interface OnGuiGeClickListener {
        void guiGeClick(Goods item);
    }

    /**
     * 点击商品接口
     */
    public interface OnItemClickListener {
        void itemClick(Goods item);
    }

    public interface OnCouponClickListener {
        void CouponClickListener();
    }

    public GoodsAdapter(WaiMaiShopActivity mContext) {
        this.mContext = mContext;
        nf = NumberFormat.getCurrencyInstance();
        nf.setMaximumFractionDigits(2);
        mInflater = LayoutInflater.from(mContext);
    }

    /**
     * 设置规格点击监听
     *
     * @param guiGeClickListener
     */
    public void setOnGuiGeClickListener(OnGuiGeClickListener guiGeClickListener) {
        this.guiGeClickListener = guiGeClickListener;
    }

    /**
     * 商品点击事件监听
     *
     * @param itemClickListener
     */
    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public View getHeaderView(int position, View convertView, ViewGroup parent) {
        HeadViewHolder headViewHolder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.adapter_goods_header_view, parent, false);
            headViewHolder = new HeadViewHolder();
            headViewHolder.tvHeader = (TextView) convertView.findViewById(R.id.tv_header);
            convertView.setTag(headViewHolder);
        } else {
            headViewHolder = (HeadViewHolder) convertView.getTag();
        }

        headViewHolder.tvHeader.setText(goodsList.get(position).title);
        return convertView;
    }

    class HeadViewHolder {
        private TextView tvHeader;
    }

    @Override
    public long getHeaderId(int position) {
        return goodsList.get(position).typeId;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        if (goodsList.get(position).productsEntity.is_spec.equals("0")) {
            return NORMAL;
        } else {
            return GUIGE;
        }
    }

    @Override
    public int getCount() {
        if (goodsList == null) {
            return 0;
        }
        return goodsList.size();
    }

    @Override
    public Object getItem(int position) {
        return goodsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        NormalViewHolder normalViewHolder = null;
        GuiGeViewHolder guiGeViewHolder = null;
        Goods item = goodsList.get(position);
        switch (getItemViewType(position)) {
            case GUIGE:
                if (convertView == null) {
                    convertView = mInflater.inflate(R.layout.adapter_goods_guige_item, null);
                    guiGeViewHolder = new GuiGeViewHolder(convertView);
                    convertView.setTag(guiGeViewHolder);
                } else {
                    guiGeViewHolder = (GuiGeViewHolder) convertView.getTag();
                }
                guiGeViewHolder.bindData(item);
                break;
            case NORMAL:
                if (convertView == null) {
                    convertView = mInflater.inflate(R.layout.adapter_goods_normal_item, null);
                    normalViewHolder = new NormalViewHolder(convertView);
                    convertView.setTag(normalViewHolder);
                } else {
                    normalViewHolder = (NormalViewHolder) convertView.getTag();
                }
                normalViewHolder.bindData(item);
                break;
        }
        return convertView;
    }

    class GuiGeViewHolder {
        private SuperTextView tvGuige;
        private RelativeLayout rlCommGuige;
        private TextView name, price, sold, praise;
        private Goods item;
        private ImageView ivImg;

        public GuiGeViewHolder(View itemView) {
            rlCommGuige = (RelativeLayout) itemView.findViewById(R.id.rl_comm_guige);
            name = (TextView) itemView.findViewById(R.id.tvName);
            price = (TextView) itemView.findViewById(R.id.tvPrice);
            tvGuige = (SuperTextView) itemView.findViewById(R.id.tv_guige);
            ivImg = (ImageView) itemView.findViewById(R.id.img);
            sold = (TextView) itemView.findViewById(R.id.tv_sold);
            praise = (TextView) itemView.findViewById(R.id.tv_praise);
        }

        public void bindData(final Goods item) {
            this.item = item;
            name.setText(item.productsEntity.title);
            price.setText(nf.format(Double.parseDouble(item.productsEntity.price)));
            Utils.LoadStrPicture(mContext, Api.IMAGE_URL + item.productsEntity.photo, ivImg);
            sold.setText(String.format(mContext.getString(R.string.已售), item.productsEntity.sales));
            praise.setText(String.format(mContext.getString(R.string.赞), item.productsEntity.good));
            tvGuige.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (guiGeClickListener != null) {
                        guiGeClickListener.guiGeClick(item);
                    }
                }
            });
            ivImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != itemClickListener) {
                        itemClickListener.itemClick(item);
                    }
                }
            });
        }
    }

    class NormalViewHolder implements View.OnClickListener {
        private TextView name, price, tvAdd, tvMinus, tvCount, sold, praise;
        private RelativeLayout rlCommNormal;
        private Goods item;
        private ImageView ivImg;

        public NormalViewHolder(View itemView) {
            name = (TextView) itemView.findViewById(R.id.tvName);
            rlCommNormal = (RelativeLayout) itemView.findViewById(R.id.rl_comm_normal);
            price = (TextView) itemView.findViewById(R.id.tvPrice);
            tvCount = (TextView) itemView.findViewById(R.id.count);
            tvMinus = (TextView) itemView.findViewById(R.id.tvMinus);
            tvAdd = (TextView) itemView.findViewById(R.id.tvAdd);
            ivImg = (ImageView) itemView.findViewById(R.id.img);
            sold = (TextView) itemView.findViewById(R.id.tv_sold);
            praise = (TextView) itemView.findViewById(R.id.tv_praise);
        }

        public void bindData(final Goods item) {
            this.item = item;
            name.setText(item.productsEntity.title);
            item.count = mContext.getSelectedItemCountById(Integer.parseInt(item.productsEntity.product_id));
            tvCount.setText(String.valueOf(item.count));
            price.setText(nf.format(Double.parseDouble(item.productsEntity.price)));
            Utils.LoadStrPicture(mContext, Api.IMAGE_URL + item.productsEntity.photo, ivImg);
            sold.setText(String.format(mContext.getString(R.string.已售), item.productsEntity.sales));
            praise.setText(String.format(mContext.getString(R.string.赞), item.productsEntity.good));
            if (item.count < 1) {
                tvCount.setVisibility(View.GONE);
                tvMinus.setVisibility(View.GONE);
            } else {
                tvCount.setVisibility(View.VISIBLE);
                tvMinus.setVisibility(View.VISIBLE);
            }
            tvMinus.setOnClickListener(this);
            tvAdd.setOnClickListener(this);
            ivImg.setOnClickListener(this);
        }

        /**
         * @param v
         */
        @Override
        public void onClick(View v) {
            WaiMaiShopActivity activity = mContext;
            switch (v.getId()) {
                case R.id.img:
                    if (null != itemClickListener) {
                        itemClickListener.itemClick(item);
                    }
                    break;
                case R.id.tvAdd: {
                    int count = mContext.getSelectedItemCountById(Integer.parseInt(item.productsEntity.product_id));
                    if (count < 1) {
                        tvMinus.setAnimation(getShowAnimation());
                        tvMinus.setVisibility(View.VISIBLE);
                        tvCount.setVisibility(View.VISIBLE);
                    }
                    count++;
                    if (count > item.sale_sku) {
                        ToastUtil.show("库存不足");
                        return;
                    }
                    mContext.add(item, true);
                    tvCount.setText(String.valueOf(count));
                    int[] loc = new int[2];
                    v.getLocationInWindow(loc);
                    mContext.playAnimation(loc);
                }
                break;
                case R.id.tvMinus: {
                    int count = mContext.getSelectedItemCountById(Integer.parseInt(item.productsEntity.product_id));
                    if (count < 2) {
                        tvMinus.setAnimation(getHiddenAnimation());
                        tvMinus.setVisibility(View.GONE);
                        tvCount.setVisibility(View.GONE);
                    }
                    count--;
                    mContext.remove(item, true);//activity.getSelectedItemCountById(item.id)
                    tvCount.setText(String.valueOf(count));
                }
                break;
                default:
                    break;
            }
        }
    }

    private Animation getShowAnimation() {
        AnimationSet set = new AnimationSet(true);
        RotateAnimation rotate = new RotateAnimation(0, 720, RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        set.addAnimation(rotate);
        TranslateAnimation translate = new TranslateAnimation(
                TranslateAnimation.RELATIVE_TO_SELF, 2f
                , TranslateAnimation.RELATIVE_TO_SELF, 0
                , TranslateAnimation.RELATIVE_TO_SELF, 0
                , TranslateAnimation.RELATIVE_TO_SELF, 0);
        set.addAnimation(translate);
        AlphaAnimation alpha = new AlphaAnimation(0, 1);
        set.addAnimation(alpha);
        set.setDuration(500);
        return set;
    }

    private Animation getHiddenAnimation() {
        AnimationSet set = new AnimationSet(true);
        RotateAnimation rotate = new RotateAnimation(0, 720, RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        set.addAnimation(rotate);
        TranslateAnimation translate = new TranslateAnimation(
                TranslateAnimation.RELATIVE_TO_SELF, 0
                , TranslateAnimation.RELATIVE_TO_SELF, 2f
                , TranslateAnimation.RELATIVE_TO_SELF, 0
                , TranslateAnimation.RELATIVE_TO_SELF, 0);
        set.addAnimation(translate);
        AlphaAnimation alpha = new AlphaAnimation(1, 0);
        set.addAnimation(alpha);
        set.setDuration(500);
        return set;
    }
}