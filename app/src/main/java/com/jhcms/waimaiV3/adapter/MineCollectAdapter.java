package com.jhcms.waimaiV3.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jhcms.common.model.CollectModel;
import com.jhcms.common.model.SharedResponse;
import com.jhcms.common.utils.Api;
import com.jhcms.common.utils.HttpUtils;
import com.jhcms.common.utils.OnRequestSuccessCallback;
import com.jhcms.common.utils.ToastUtil;
import com.jhcms.common.utils.Utils;
import com.jhcms.waimaiV3.R;

import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Wyj on 2017/4/17.
 */
public class MineCollectAdapter extends RecyclerView.Adapter<MineCollectAdapter.CollectViewHolder> {
    private Context context;
    private LayoutInflater inflater;
    int num = 0;
    private  Unfriended unfriended;

    public void setUnfriended(Unfriended unfriended) {
        this.unfriended = unfriended;
    }

    ArrayList<CollectModel> mDataList = new ArrayList<>();

    public void setmDataList(ArrayList<CollectModel> mDataList) {
        this.mDataList.clear();
        this.mDataList.addAll(mDataList);
        notifyDataSetChanged();
    }

    public MineCollectAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }


    @Override
    public CollectViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.adapter_collect_item, null);
        return new CollectViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final CollectViewHolder holder, final int position) {
        if(mDataList!=null&&mDataList.size()>0) {
            if (mDataList.get(position).title != null) {
                holder.tvShopName.setText(mDataList.get(position).title);
            }
            if (mDataList.get(position).logo != null) {
                Utils.LoadStrPicture(context, Api.IMAGE_URL + mDataList.get(position).logo, holder.ivShopLogo);
            }
            if (mDataList.get(position).orders != null) {
                holder.tvSaleNum.setText("月售" + mDataList.get(position).orders + "单数");
            }
            //  android:text="￥0起送 / 配送费0元"
            if (mDataList.get(position).freight != null && mDataList.get(position).min_amount != null) {
                holder.tvFreight.setText("￥" + mDataList.get(position).freight + "起送 / 配送费" + mDataList.get(position).min_amount + "元");

            }
            if (mDataList.get(position).pei_time != null) {
                holder.tvFreightTime.setText(mDataList.get(position).pei_time + "分钟");
            }
            if (mDataList.get(position).juli_label != null) {
                holder.tvDistance.setText(mDataList.get(position).juli_label);
            }

        /*满星是50*/
            if (mDataList.get(position).score != null)
                holder.shopStar.setProgress((int) (Float.parseFloat(mDataList.get(position).score) * 10));
            final int finalPosition = position;
            holder.llShopInfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    holder.tvDistance.setText("" + num++);
                    Toast.makeText(context, "" + finalPosition, Toast.LENGTH_SHORT).show();
                }
            });

            holder.btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try{
                        JSONObject jsonObject=new JSONObject();
                        jsonObject.put("status",0);
                        jsonObject.put("type",1);
                        jsonObject.put("can_id",mDataList.get(position).shop_id);
                        String str=jsonObject.toString();
                        HttpUtils.postUrl(context,Api.WAIMAI_COLLECTION_MERCHANT, str, true, new OnRequestSuccessCallback() {
                            @Override
                            public void onSuccess(String url, String Json) {
                                Gson gson=new Gson();
                                SharedResponse sharedResponse=gson.fromJson(Json,SharedResponse.class);
                                if(sharedResponse.error.equals("0")){
                                    ToastUtil.show(sharedResponse.message);
                                    unfriended.cancel();
                                }
                            }

                            @Override
                            public void onBeforeAnimate() {

                            }

                            @Override
                            public void onErrorAnimate() {

                            }
                        });
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    public void clear() {
        mDataList.clear();
        notifyDataSetChanged();
    }

    static class CollectViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.iv_shop_logo)
        ImageView ivShopLogo;
        @Bind(R.id.tv_shop_name)
        TextView tvShopName;
        @Bind(R.id.shop_star)
        RatingBar shopStar;
        @Bind(R.id.tv_sale_num)
        TextView tvSaleNum;
        @Bind(R.id.tv_freight_time)
        TextView tvFreightTime;
        @Bind(R.id.tv_freight)
        TextView tvFreight;
        @Bind(R.id.tv_distance)
        TextView tvDistance;
        @Bind(R.id.ll_shopInfo)
        RelativeLayout llShopInfo;
        @Bind(R.id.bt_guanzhu)
        Button btnCancel;
        CollectViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }


    }

    public  interface  Unfriended{
        void cancel();
    };

}
