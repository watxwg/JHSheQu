package com.jhcms.waimaiV3.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.coorchice.library.SuperTextView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jhcms.common.model.ShopItems;
import com.jhcms.common.utils.Api;
import com.jhcms.common.utils.Utils;
import com.jhcms.waimaiV3.R;
import com.jhcms.waimaiV3.litepal.Shop;
import com.jhcms.waimaiV3.model.Goods;

import org.litepal.crud.DataSupport;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;


public class ShopItemAdapter extends RecyclerView.Adapter<ShopItemAdapter.MyViewHolder> {
    private Context context;
    private OnClickListener listener;
    private ArrayList<ShopItems> dataList = new ArrayList<>();
    private HashMap<String, Boolean> isOpen = new HashMap<>();
    private static NumberFormat nf;


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_shop_item, parent, false);
        return new MyViewHolder(view);
    }

    /**
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final ShopItems items = dataList.get(position);
        final ShopItems.WaimaiEntity waimaiEntity = items.waimai;
        List<ShopItems.ProductsEntity> products = items.products;
        isOpen.put(waimaiEntity.shop_id, false);


        List<Shop> mDestList = DataSupport.where("shop_id = ?", waimaiEntity.shop_id).find(Shop.class);
        if (null != mDestList && mDestList.size() > 0) {
            Gson gson = new Gson();
            final List<Goods> goodses = gson.fromJson(mDestList.get(0).getProduct_info(), new TypeToken<List<Goods>>() {
            }.getType());
            int count = 0;
            if (null != goodses && goodses.size() > 0) {
                for (int i = 0; i < goodses.size(); i++) {
                    count += goodses.get(i).count;
                }
            }
            if (count == 0) {
                holder.stvNum.setVisibility(View.GONE);
            } else {
                holder.stvNum.setVisibility(View.VISIBLE);
                holder.stvNum.setText(String.valueOf(count));

            }
        } else {
            holder.stvNum.setVisibility(View.GONE);
        }


        /**
         * 是否新店铺 1:表示新店
         * */
        if (waimaiEntity.is_new.equals("0")) {
            holder.ivShopLabel.setVisibility(View.GONE);
        } else {
            holder.ivShopLabel.setVisibility(View.VISIBLE);
        }


        /**
         * 0是商家配送
         * 1是平台专送 icon_label_son
         * */
        if (waimaiEntity.pei_type.equals("0")) {
            holder.ivLabelSon.setImageResource(R.mipmap.icon_shop_son);
        } else {
            holder.ivLabelSon.setImageResource(R.mipmap.icon_label_son);
        }
        if ("1".equals(waimaiEntity.yysj_status) && "1".equals(waimaiEntity.yy_status)) {
            holder.tvShopStatus.setVisibility(View.GONE);
        } else {
            holder.tvShopStatus.setVisibility(View.VISIBLE);
        }

        Utils.LoadRoundPicture(context, Api.IMAGE_URL + waimaiEntity.logo, holder.ivShopLogo);

        holder.tvShopName.setText(waimaiEntity.title);
        holder.tvShopScore.setText(waimaiEntity.avg_score + context.getString(R.string.分));
        holder.shopStar.setProgress((int) (10 * Double.parseDouble(waimaiEntity.avg_score)));
        holder.tvSaleNum.setText(context.getString(R.string.月售) + waimaiEntity.orders + context.getString(R.string.单));
        holder.tvFreightTime.setText(waimaiEntity.pei_time + context.getString(R.string.分钟));
        if (!TextUtils.isEmpty(waimaiEntity.freight) && !waimaiEntity.freight.equals("0")) {
            holder.tvFreight.setText("￥" + waimaiEntity.min_amount + "元起送/配送费" + waimaiEntity.freight + "元");
        } else {
            holder.tvFreight.setText("￥" + waimaiEntity.min_amount + "元起送/免配送费");
        }
        holder.tvDistance.setText(waimaiEntity.juli_label);
        final List<ShopItems.WaimaiEntity.HuodongEntity> huodong = waimaiEntity.huodong;
        if (null != huodong) {
            holder.tvHuodongNum.setText(huodong.size() + "个活动");
            if (huodong.size() > 0) {
                holder.llAllHuodong.setVisibility(View.VISIBLE);
                if (huodong.size() > 2) {
                    showActivity(holder, 2, huodong);
                    holder.llHuodong.setVisibility(View.VISIBLE);
                } else {
                    showActivity(holder, huodong.size(), huodong);
                    holder.llHuodong.setVisibility(View.GONE);
                }
            } else {
                holder.llAllHuodong.setVisibility(View.GONE);
            }
            holder.llAllHuodong.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isOpen.get(waimaiEntity.shop_id) != null && isOpen.get(waimaiEntity.shop_id)) {
                        if (huodong.size() > 2) {
                            showActivity(holder, 2, huodong);
                        } else {
                            if (listener != null) {
                                listener.onClick(waimaiEntity.shop_id);
                            }
                        }
                        holder.ivHuodongStatus.setSelected(false);
                        isOpen.put(waimaiEntity.shop_id, false);
                    } else {
                        showActivity(holder, huodong.size(), huodong);
                        holder.ivHuodongStatus.setSelected(true);
                        isOpen.put(waimaiEntity.shop_id, true);
                    }
                }
            });
        } else {
            holder.llAllHuodong.setVisibility(View.GONE);
        }
        /*判断 热销产品是否为空
        * 为空隐藏
        * 反之显示
        * */
        if (null != products && products.size() > 0) {
            holder.rvHotComm.setVisibility(View.VISIBLE);
            HotCommAdapter commAdapter = new HotCommAdapter(context, waimaiEntity.shop_id);
            holder.rvHotComm.setNestedScrollingEnabled(true);
            holder.rvHotComm.setFocusable(false);
            holder.rvHotComm.setAdapter(commAdapter);
            commAdapter.setProducts(products);
            holder.rvHotComm.setLayoutManager(new GridLayoutManager(context, 4));
        } else {
            holder.rvHotComm.setVisibility(View.GONE);
        }

        holder.rlItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onClick(waimaiEntity.shop_id);
                }
            }

        });

    }


    @Override
    public int getItemCount() {
        return dataList == null ? 0 : dataList.size();
    }

    public void setDataList(List<ShopItems> list) {
        dataList.clear();
        dataList.addAll(list);
        notifyDataSetChanged();
    }

    public void addAll(List<ShopItems> list) {
        int lastIndex = this.dataList.size();
        if (this.dataList.addAll(list)) {
            notifyItemRangeInserted(lastIndex, list.size());
        }
    }

    public void clear() {
        dataList.clear();
        notifyDataSetChanged();
    }


    public interface OnClickListener {
        void onClick(String shopId);
    }

    public ShopItemAdapter(Context context) {
        this.context = context;
        /*getCurrencyInstance 返回当前缺省语言环境的通用格式*/
        nf = NumberFormat.getNumberInstance(Locale.US);
        /*设置数值的小数部分允许的最大位数为2*/
        nf.setMaximumFractionDigits(1);
    }

    public void setOnClickListener(OnClickListener listener) {
        this.listener = listener;
    }

    private SuperTextView tvYouhuiWord;
    private TextView tvYouhuiTitle;


    private void showActivity(MyViewHolder holder, int num, List<ShopItems.WaimaiEntity.HuodongEntity> shopItemsHDs) {
        holder.activeLl.removeAllViews();
        for (int i = 0; i < num; i++) {
            LinearLayout firstLl = new LinearLayout(context);
            View view = LayoutInflater.from(context).inflate(R.layout.youhuiquan, firstLl, false);
            tvYouhuiWord = (SuperTextView) view.findViewById(R.id.tv_youhui_word);
            tvYouhuiTitle = (TextView) view.findViewById(R.id.tv_youhui_title);
            tvYouhuiWord.setText(shopItemsHDs.get(i).word);
            tvYouhuiWord.setSolid(Color.parseColor("#" + shopItemsHDs.get(i).color));
            tvYouhuiTitle.setText(shopItemsHDs.get(i).title);
            tvYouhuiTitle.setTextColor(context.getResources().getColor(R.color.title_color));
            firstLl.addView(view);
            holder.activeLl.addView(firstLl);
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.iv_shop_logo)
        ImageView ivShopLogo;
        @Bind(R.id.tv_shop_name)
        TextView tvShopName;
        @Bind(R.id.iv_label_son)
        ImageView ivLabelSon;
        @Bind(R.id.shop_star)
        RatingBar shopStar;
        @Bind(R.id.tv_shop_score)
        TextView tvShopScore;
        @Bind(R.id.tv_sale_num)
        TextView tvSaleNum;
        @Bind(R.id.tv_freight_time)
        TextView tvFreightTime;
        @Bind(R.id.tv_freight)
        TextView tvFreight;
        @Bind(R.id.tv_distance)
        TextView tvDistance;
        @Bind(R.id.ll_shopInfo)
        LinearLayout llShopInfo;
        @Bind(R.id.active_ll)
        LinearLayout activeLl;
        @Bind(R.id.tv_huodong_num)
        TextView tvHuodongNum;
        @Bind(R.id.tv_shop_status)
        TextView tvShopStatus;
        @Bind(R.id.iv_huodong_status)
        ImageView ivHuodongStatus;
        @Bind(R.id.ll_huodong)
        LinearLayout llHuodong;
        @Bind(R.id.ll_all_huodong)
        LinearLayout llAllHuodong;
        @Bind(R.id.rv_hot_comm)
        RecyclerView rvHotComm;
        @Bind(R.id.rl_item)
        LinearLayout rlItem;
        @Bind(R.id.ll_root)
        LinearLayout llRoot;
        @Bind(R.id.stv_num)
        SuperTextView stvNum;
        @Bind(R.id.iv_shop_label)
        ImageView ivShopLabel;

        MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
