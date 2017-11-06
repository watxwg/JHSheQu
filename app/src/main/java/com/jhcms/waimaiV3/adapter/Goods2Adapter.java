package com.jhcms.waimaiV3.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.coorchice.library.SuperTextView;
import com.jhcms.common.utils.Api;
import com.jhcms.common.utils.ToastUtil;
import com.jhcms.common.utils.Utils;
import com.jhcms.common.widget.SectionedBaseAdapter;
import com.jhcms.waimaiV3.R;
import com.jhcms.waimaiV3.activity.WaiMaiShopActivity;
import com.jhcms.waimaiV3.model.Goods;
import com.jhcms.waimaiV3.model.Type;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.jhcms.waimaiV3.R.id.count;


/**
 * Created by Wyj on 2017/5/8
 * TODO:商家详情-->商品-->右侧商品 ItemAdapter
 */
public class Goods2Adapter extends SectionedBaseAdapter {

    private WaiMaiShopActivity mContext;
    private NumberFormat nf;
    private LayoutInflater mInflater;
    private static final int GUIGE = 1;
    private static final int NORMAL = 0;
    private List<Type> data;
    private List<Goods> goodsList;
    private OnGuiGeClickListener guiGeClickListener;

    private OnItemClickListener itemClickListener;
    private HashMap<Integer, ArrayList<Goods>> goodsMap;
    private int hotProductId = -1;

    public void setHotProductId(int hotProductId) {
        this.hotProductId = hotProductId;
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

    public Goods2Adapter(WaiMaiShopActivity mContext) {
        this.mContext = mContext;
        nf = NumberFormat.getCurrencyInstance();
        nf.setMaximumFractionDigits(2);
        mInflater = LayoutInflater.from(mContext);
    }

    public void setData(List<Type> data, List<Goods> goodsList, HashMap<Integer, ArrayList<Goods>> goodsMap) {
        this.data = data;
        this.goodsList = goodsList;
        this.goodsMap = goodsMap;
        notifyDataSetChanged();
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
    public Object getItem(int section, int position) {
        return null;
    }

    @Override
    public long getItemId(int section, int position) {
        return 0;
    }

    /**
     * 标题的个数
     *
     * @return
     */
    @Override
    public int getSectionCount() {
        return data == null ? 0 : data.size();
    }

    /**
     * @param section
     * @return
     */
    @Override
    public int getCountForSection(int section) {
        return goodsMap.get(section) == null ? 0 : goodsMap.get(section).size();
    }


    @Override
    public int getItemViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int section, int position) {
        if (goodsMap.get(section).get(position).productsEntity.is_spec.equals("0")) {
            return NORMAL;
        } else {
            return GUIGE;
        }
    }

    @Override
    public View getItemView(int section, int position, View convertView, ViewGroup parent) {
        NormalViewHolder normalViewHolder = null;
        GuiGeViewHolder guiGeViewHolder = null;
        Goods item = goodsMap.get(section).get(position);
        switch (getItemViewType(section, position)) {
            case NORMAL:
                if (convertView == null) {
                    convertView = mInflater.inflate(R.layout.adapter_goods_normal_item, null);
                    normalViewHolder = new NormalViewHolder(convertView);
                    convertView.setTag(normalViewHolder);
                } else {
                    normalViewHolder = (NormalViewHolder) convertView.getTag();
                }
                normalViewHolder.bindData(item, section, position);

                break;
            case GUIGE:
                if (convertView == null) {
                    convertView = mInflater.inflate(R.layout.adapter_goods_guige_item, null);
                    guiGeViewHolder = new GuiGeViewHolder(convertView);
                    convertView.setTag(guiGeViewHolder);
                } else {
                    guiGeViewHolder = (GuiGeViewHolder) convertView.getTag();
                }
                guiGeViewHolder.bindData(item, section, position);
                break;
        }
        return convertView;
    }

    @Override
    public View getSectionHeaderView(int section, View convertView, ViewGroup parent) {
        GoodsHeaderHolder headerHolder = null;
        if (null == convertView) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.adapter_goods_header_view, parent, false);
            headerHolder = new GoodsHeaderHolder();
            headerHolder.tv_header = (TextView) convertView.findViewById(R.id.tv_header);
            convertView.setTag(headerHolder);
        } else {
            headerHolder = (GoodsHeaderHolder) convertView.getTag();
        }
        headerHolder.tv_header.setText(data.get(section).itemsEntity.title);
        return convertView;
    }

    static class GoodsHeaderHolder {
        TextView tv_header;
    }

    class NormalViewHolder implements View.OnClickListener {
        private TextView name, price, tvAdd, tvMinus, tvCount, sold, praise;
        private RelativeLayout rlCommNormal;
        private Goods item;
        private ImageView ivImg;
        private SuperTextView sutSoldOut;

        public NormalViewHolder(View itemView) {
            name = (TextView) itemView.findViewById(R.id.tvName);
            rlCommNormal = (RelativeLayout) itemView.findViewById(R.id.rl_comm_normal);
            price = (TextView) itemView.findViewById(R.id.tvPrice);
            tvCount = (TextView) itemView.findViewById(count);
            tvMinus = (TextView) itemView.findViewById(R.id.tvMinus);
            tvAdd = (TextView) itemView.findViewById(R.id.tvAdd);
            ivImg = (ImageView) itemView.findViewById(R.id.img);
            sold = (TextView) itemView.findViewById(R.id.tv_sold);
            praise = (TextView) itemView.findViewById(R.id.tv_praise);
            sutSoldOut = (SuperTextView) itemView.findViewById(R.id.tv_sold_out);
        }

        public void bindData(final Goods item, int section, int position) {
            this.item = item;
            if (section == 0 && position == hotProductId) {
                name.setTextColor(mContext.getResources().getColor(R.color.themColor));
            } else {
                name.setTextColor(mContext.getResources().getColor(R.color.title_color));
            }
            if (item.productsEntity.sale_sku <= 0) {
                sutSoldOut.setVisibility(View.VISIBLE);
                tvMinus.setVisibility(View.GONE);
                tvAdd.setVisibility(View.GONE);
                tvCount.setVisibility(View.GONE);
            } else {
                sutSoldOut.setVisibility(View.GONE);
                tvAdd.setVisibility(View.VISIBLE);
                item.count = mContext.getSelectedItemCountById(Integer.parseInt(item.productsEntity.product_id));
                /*购物车中商品的数量*/
                tvCount.setText(String.valueOf(item.count));
                if (item.count < 1) {
                    tvCount.setVisibility(View.GONE);
                    tvMinus.setVisibility(View.GONE);
                } else {
                    tvCount.setVisibility(View.VISIBLE);
                    tvMinus.setVisibility(View.VISIBLE);
                }
            }
            /*商品名称*/
            name.setText(item.productsEntity.title);
            /*商品价格*/
            price.setText(nf.format(Double.parseDouble(item.productsEntity.price)));
            /*商品图*/
            Utils.LoadStrPicture(mContext, Api.IMAGE_URL + item.productsEntity.photo, ivImg);
            /*售出数量*/
            sold.setText(String.format(mContext.getString(R.string.已售), item.productsEntity.sales));
            /*赞*/
            praise.setText(String.format(mContext.getString(R.string.赞), item.productsEntity.good));

            tvMinus.setOnClickListener(this);
            tvAdd.setOnClickListener(this);
            ivImg.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.img:
                    if (null != itemClickListener) {
                        itemClickListener.itemClick(item);
                    }
                    break;
                case R.id.tvAdd:
                    int countAdd = mContext.getSelectedItemCountById(Integer.parseInt(item.productsEntity.product_id));
                    countAdd++;
                    if (countAdd > item.sale_sku) {
                        ToastUtil.show("库存不足");
                        return;
                    }
                    if (countAdd < 1) {
                        tvMinus.setAnimation(getShowAnimation());
                        tvMinus.setVisibility(View.VISIBLE);
                        tvCount.setVisibility(View.VISIBLE);
                    }
                    mContext.add(item, true);
                    tvCount.setText(String.valueOf(countAdd));
                    int[] loc = new int[2];
                    v.getLocationInWindow(loc);
                    mContext.playAnimation(loc);

                    break;
                case R.id.tvMinus:
                    int countMinus = mContext.getSelectedItemCountById(Integer.parseInt(item.productsEntity.product_id));
                    if (countMinus < 2) {
                        tvMinus.setAnimation(getHiddenAnimation());
                        tvMinus.setVisibility(View.GONE);
                        tvCount.setVisibility(View.GONE);
                    }
                    countMinus--;
                    mContext.remove(item, true);//activity.getSelectedItemCountById(item.id)
                    tvCount.setText(String.valueOf(countMinus));
                    break;
                default:
                    break;
            }
        }

    }

    class GuiGeViewHolder {
        private SuperTextView tvGuige, tvGuigeNum;
        private RelativeLayout rlCommGuige;
        private TextView name, price, sold, praise;
        private Goods item;
        private ImageView ivImg;

        public GuiGeViewHolder(View itemView) {
            rlCommGuige = (RelativeLayout) itemView.findViewById(R.id.rl_comm_guige);
            name = (TextView) itemView.findViewById(R.id.tvName);
            price = (TextView) itemView.findViewById(R.id.tvPrice);
            tvGuige = (SuperTextView) itemView.findViewById(R.id.tv_guige);
            tvGuigeNum = (SuperTextView) itemView.findViewById(R.id.tv_guige_num);
            ivImg = (ImageView) itemView.findViewById(R.id.img);
            sold = (TextView) itemView.findViewById(R.id.tv_sold);
            praise = (TextView) itemView.findViewById(R.id.tv_praise);
        }

        public void bindData(final Goods item, int section, int position) {
            this.item = item;
            if (section == 0 && position == hotProductId) {
                name.setTextColor(mContext.getResources().getColor(R.color.themColor));
            } else {
                name.setTextColor(mContext.getResources().getColor(R.color.title_color));
            }
            name.setText(item.productsEntity.title);
            price.setText(nf.format(Double.parseDouble(item.productsEntity.price)));
            Utils.LoadStrPicture(mContext, Api.IMAGE_URL + item.productsEntity.photo, ivImg);
            sold.setText(String.format(mContext.getString(R.string.已售), item.productsEntity.sales));
            praise.setText(String.format(mContext.getString(R.string.赞), item.productsEntity.good));

            int count = mContext.getSpecItemCountById(Integer.parseInt(item.productsEntity.product_id));
            if (count == 0) {
                tvGuigeNum.setVisibility(View.GONE);
            } else {
                tvGuigeNum.setVisibility(View.VISIBLE);
                tvGuigeNum.setText(String.valueOf(count));
            }

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
        set.setDuration(300);
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
        set.setDuration(300);
        return set;
    }


}