package com.jhcms.waimaiV3.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.coorchice.library.SuperTextView;
import com.jhcms.common.utils.Api;
import com.jhcms.common.utils.ToastUtil;
import com.jhcms.common.utils.Utils;
import com.jhcms.waimaiV3.R;
import com.jhcms.waimaiV3.activity.WaiMaiInStoreSearchActivity;
import com.jhcms.waimaiV3.activity.WaiMaiShopActivity;
import com.jhcms.waimaiV3.model.Goods;

import java.text.NumberFormat;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by wangyujie
 * Date 2017/7/6.
 * TODO 店内搜索 Adapter
 */

public class InStoreSearchAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private LayoutInflater layoutInflater;
    private ForegroundColorSpan span;
    private WaiMaiShopActivity activity;
    private WaiMaiInStoreSearchActivity context;
    private List<Goods> data;
    private static final int GUIGE = 1;
    private static final int NORMAL = 0;
    private NumberFormat nf;
    private int index;
    private String search;
    private SpannableStringBuilder builder;

    public InStoreSearchAdapter(WaiMaiShopActivity activity, WaiMaiInStoreSearchActivity context) {
        this.activity = activity;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        nf = NumberFormat.getCurrencyInstance();
        nf.setMaximumFractionDigits(2);

        span = new ForegroundColorSpan(context.getResources().getColor(R.color.themColor));//要显示的颜色

    }

    @Override
    public int getItemViewType(int position) {
        if (data.get(position).is_spec.equals("0")) {
            return NORMAL;
        } else {
            return GUIGE;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        RecyclerView.ViewHolder holder = null;
        switch (viewType) {
            case NORMAL:
                view = layoutInflater.inflate(R.layout.adapter_goods_normal_item, parent, false);
                holder = new NormalViewHolder(view);
                break;

            case GUIGE:
                view = layoutInflater.inflate(R.layout.adapter_goods_guige_item, parent, false);
                holder = new GuiGeViewHolder(view);
                break;
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Goods item = data.get(position);
        builder = new SpannableStringBuilder(item.productsEntity.title);
        index = item.productsEntity.title.indexOf(search);//从第几个匹配上
        switch (getItemViewType(position)) {
            case NORMAL:
                NormalViewHolder normalHolder = (NormalViewHolder) holder;
                normalHolder.bindData(item);
                break;
            case GUIGE:
                GuiGeViewHolder guiGeHolder = (GuiGeViewHolder) holder;
                guiGeHolder.bindData(item);

                break;
        }
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    public void setData(List<Goods> data, String search) {
        this.data = data;
        this.search = search;

        notifyDataSetChanged();
    }

    /**
     * 点击商品接口
     */
    public interface OnItemClickListener {
        void itemClick(Goods item);
    }

    /**
     * 商品点击事件监听
     *
     * @param itemClickListener
     */
    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    private OnItemClickListener itemClickListener;

    class NormalViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @Bind(R.id.img)
        ImageView img;
        @Bind(R.id.tvName)
        TextView tvName;
        @Bind(R.id.tv_sold)
        TextView tvSold;
        @Bind(R.id.tv_praise)
        TextView tvPraise;
        @Bind(R.id.tvPrice)
        TextView tvPrice;
        @Bind(R.id.tvMinus)
        TextView tvMinus;
        @Bind(R.id.count)
        TextView tvCount;
        @Bind(R.id.tvAdd)
        TextView tvAdd;
        @Bind(R.id.tv_sold_out)
        SuperTextView tvSoldOut;
        @Bind(R.id.rl_comm_normal)
        RelativeLayout rlCommNormal;
        private Goods item;

        public NormalViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);

        }

        public void bindData(Goods item) {
            this.item = item;
            if (item.productsEntity.sale_sku == 0) {
                tvSoldOut.setVisibility(View.VISIBLE);
                tvMinus.setVisibility(View.GONE);
                tvAdd.setVisibility(View.GONE);
                tvCount.setVisibility(View.GONE);
            } else {
                tvSoldOut.setVisibility(View.GONE);
                tvAdd.setVisibility(View.VISIBLE);
                item.count = context.getSelectedItemCountById(Integer.parseInt(item.productsEntity.product_id));
                tvCount.setText(String.valueOf(item.count));
                if (item.count < 1) {
                    tvCount.setVisibility(View.GONE);
                    tvMinus.setVisibility(View.GONE);
                } else {
                    tvCount.setVisibility(View.VISIBLE);
                    tvMinus.setVisibility(View.VISIBLE);
                }
            }


            if (index != -1) {//有这个关键字用builder显示
                builder.setSpan(span, index, index + search.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                tvName.setText(builder);
            } else {//没有则直接显示
                tvName.setText(item.productsEntity.title);
            }


            tvPrice.setText(nf.format(Double.parseDouble(item.productsEntity.price)));
            Utils.LoadStrPicture(context, Api.IMAGE_URL + item.productsEntity.photo, img);
            tvSold.setText(String.format(context.getString(R.string.已售), item.productsEntity.sales));
            tvPraise.setText(String.format(context.getString(R.string.赞), item.productsEntity.good));
            tvMinus.setOnClickListener(this);
            tvAdd.setOnClickListener(this);
            img.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.img:
                    if (null != itemClickListener) {
                        itemClickListener.itemClick(item);
                    }
                    break;
                case R.id.tvAdd: {
                    int count = context.getSelectedItemCountById(Integer.parseInt(item.productsEntity.product_id));
                    count++;
                    if (count > item.sale_sku) {
                        ToastUtil.show("库存不足");
                        return;
                    }
                    if (count < 1) {
                        tvMinus.setAnimation(getShowAnimation());
                        tvMinus.setEnabled(true);
                        tvMinus.setVisibility(View.VISIBLE);
                        tvCount.setVisibility(View.VISIBLE);
                    }
                    context.add(item, true);
                    tvCount.setText(String.valueOf(count));
                    int[] loc = new int[2];
                    v.getLocationInWindow(loc);
                    context.playAnimation(loc);
                }
                break;
                case R.id.tvMinus: {
                    int count = context.getSelectedItemCountById(Integer.parseInt(item.productsEntity.product_id));
                    if (count < 2) {
                        tvMinus.setAnimation(getHiddenAnimation());
                        tvMinus.setVisibility(View.GONE);
                        tvMinus.setEnabled(false);
                        tvCount.setVisibility(View.GONE);
                    }
                    count--;
                    context.remove(item, true);
                    tvCount.setText(String.valueOf(count));
                }
                break;
                default:
                    break;
            }
        }
    }

    private OnGuiGeClickListener guiGeClickListener;

    /**
     * 点击规格接口
     */
    public interface OnGuiGeClickListener {
        void guiGeClick(Goods item);
    }

    /**
     * 设置规格点击监听
     *
     * @param guiGeClickListener
     */
    public void setOnGuiGeClickListener(OnGuiGeClickListener guiGeClickListener) {
        this.guiGeClickListener = guiGeClickListener;
    }

    class GuiGeViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.img)
        ImageView img;
        @Bind(R.id.tvName)
        TextView tvName;
        @Bind(R.id.tv_sold)
        TextView tvSold;
        @Bind(R.id.tv_praise)
        TextView tvPraise;
        @Bind(R.id.tvPrice)
        TextView tvPrice;
        @Bind(R.id.ll_root)
        LinearLayout llRoot;
        @Bind(R.id.tv_guige)
        SuperTextView tvGuige;
        @Bind(R.id.tv_guige_num)
        SuperTextView tvGuigeNum;
        @Bind(R.id.rl_comm_guige)
        RelativeLayout rlCommGuige;

        public GuiGeViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void bindData(final Goods item) {
            if (index != -1) {//有这个关键字用builder显示
                builder.setSpan(span, index, index + search.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                tvName.setText(builder);
            } else {//没有则直接显示
                tvName.setText(item.productsEntity.title);
            }

            tvPrice.setText(nf.format(Double.parseDouble(item.productsEntity.price)));
            Utils.LoadStrPicture(context, Api.IMAGE_URL + item.productsEntity.photo, img);
            tvSold.setText(String.format(context.getString(R.string.已售), item.productsEntity.sales));
            tvPraise.setText(String.format(context.getString(R.string.赞), item.productsEntity.good));
            int count = context.getSpecItemCountById(Integer.parseInt(item.productsEntity.product_id));
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
            img.setOnClickListener(new View.OnClickListener() {
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
        set.setDuration(1000);
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
