package com.jhcms.mall.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jhcms.common.model.Data_Mall_Addr;
import com.jhcms.waimaiV3.R;

import java.util.List;

import static com.jhcms.waimaiV3.R.id.AdressSelected_iv;

/**
 * Created by admin on 2017/5/18.
 */
public class MallShippingAddressAdapter extends BaseAdapter {

    private Context context;
    private List<Data_Mall_Addr.ItemsBean> data;
    private OnDeteleListener deteleListener;
    private OnModifyListener modifyListener;
    private OnDefaultListener defaultListener;
    private String addrId;
    private OnChangeListener onChangeListener;

    public MallShippingAddressAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return data == null ? 0 : data.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        viewholder viewholder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.mall_shipping_address_adapter_layout, null);
            viewholder = new viewholder(convertView);
            convertView.setTag(viewholder);
        } else {
            viewholder = (MallShippingAddressAdapter.viewholder) convertView.getTag();
        }

        viewholder.bindData(position);
        return convertView;
    }

    public void setData(List<Data_Mall_Addr.ItemsBean> data, String addr_id) {
        this.data = data;
        this.addrId = addr_id;
        notifyDataSetChanged();
    }

    class viewholder {
        private TextView mNameAndmobel;
        private TextView mAddress;
        private LinearLayout mLyAdressUpadter;
        private ImageView mAdressSelected;
        private TextView mAdressTitle;
        private ImageView mAdressUpdate, mAdressDelete;
        private CheckBox mCbAddr;

        public viewholder(View view) {
            mNameAndmobel = (TextView) view.findViewById(R.id.nameandmobel_tv);
            mAddress = (TextView) view.findViewById(R.id.address_tv);
            mLyAdressUpadter = (LinearLayout) view.findViewById(R.id.addressupdater_li);
            mAdressSelected = (ImageView) view.findViewById(AdressSelected_iv);
            mAdressTitle = (TextView) view.findViewById(R.id.addressTitle_iv);
            mAdressUpdate = (ImageView) view.findViewById(R.id.addressupdate_iv);
            mAdressDelete = (ImageView) view.findViewById(R.id.Adressdelete);
            mCbAddr = (CheckBox) view.findViewById(R.id.cb_addr);
        }

        public void bindData(int position) {
            Data_Mall_Addr.ItemsBean itemsBean = data.get(position);
            mNameAndmobel.setText(itemsBean.contact + "-" + itemsBean.mobile);
            mAddress.setText("收货地址：" + itemsBean.province_name + itemsBean.city_name + itemsBean.area_name + itemsBean.addr);
            if ("1".equals(itemsBean.is_default)) {
                mAdressSelected.setSelected(true);
                mAdressTitle.setText("默认地址");
            } else {
                mAdressSelected.setSelected(false);
                mAdressTitle.setText("设置为默认地址");
            }
            if (TextUtils.isEmpty(addrId)) {
                mCbAddr.setVisibility(View.GONE);
            } else {
                mCbAddr.setVisibility(View.VISIBLE);
                if (addrId.equals(itemsBean.addr_id)) {
                    mCbAddr.setChecked(true);
                } else {
                    mCbAddr.setChecked(false);
                }
            }
            mAdressUpdate.setOnClickListener(v -> {
                if (null != modifyListener) {
                    modifyListener.modifyAddr(position);
                }
            });
            mAdressDelete.setOnClickListener(v -> {
                if (null != deteleListener) {
                    deteleListener.deteleAdd(position);
                }
            });
            mLyAdressUpadter.setOnClickListener(v -> {
                if (null != defaultListener) {
                    defaultListener.defaultAddr(position);
                }
            });
            mCbAddr.setOnClickListener(v -> {
                if (onChangeListener != null) {
                    onChangeListener.changeAddr(position, mCbAddr.isChecked());
                }
            });

        }

    }

    /**
     * 删除地址接口
     */
    public interface OnDeteleListener {
        void deteleAdd(int position);
    }

    public void setOnDeteleListener(OnDeteleListener deteleListener) {
        this.deteleListener = deteleListener;
    }

    /**
     * 修改地址接口
     */
    public interface OnModifyListener {
        void modifyAddr(int position);
    }

    public void setOnModifyListener(OnModifyListener modifyListener) {
        this.modifyListener = modifyListener;
    }

    /**
     * 设置默认地址接口
     */
    public interface OnDefaultListener {
        void defaultAddr(int position);
    }

    public void setOnDefaultListener(OnDefaultListener defaultListener) {
        this.defaultListener = defaultListener;
    }

    /**
     * 设置默认地址接口
     */
    public interface OnChangeListener {
        void changeAddr(int position, boolean isChecked);
    }

    public void setOnChangeListener(OnChangeListener onChangeListener) {
        this.onChangeListener = onChangeListener;
    }

}
