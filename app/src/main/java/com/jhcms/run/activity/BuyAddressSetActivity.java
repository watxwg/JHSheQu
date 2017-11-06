package com.jhcms.run.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.jhcms.common.utils.Api;
import com.jhcms.common.utils.ProgressDialogUtil;
import com.jhcms.common.utils.ToastUtil;
import com.jhcms.common.utils.Utils;
import com.jhcms.run.mode.AddressInfoModel;
import com.jhcms.shequ.activity.SearchMapActivity;
import com.jhcms.waimaiV3.MyApplication;
import com.jhcms.waimaiV3.R;
import com.jhcms.waimaiV3.activity.SwipeBaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 设置购买地址
 */
public class BuyAddressSetActivity extends SwipeBaseActivity {
    public static final String LAT = "lat";
    public static final String LNG = "lng";
    public static final String ADDRESS = "adress";
    private static final int REQUEST_GOU_ADDRESS_CODE_GAODE = 0x322;
    private static final int REQUEST_GOU_ADDRESS_CODE_GOOGLE = 0x323;
    @Bind(R.id.back_iv)
    ImageView backIv;
    @Bind(R.id.title_tv)
    TextView titleTv;
    @Bind(R.id.tv_address)
    TextView tvAddress;
    @Bind(R.id.ll_address)
    LinearLayout llAddress;
    @Bind(R.id.et_address)
    EditText etAddress;
    private String lat;
    private String lng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_buy_address_set);
        ButterKnife.bind(this);
        titleTv.setText(R.string.购买地址2);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_GOU_ADDRESS_CODE_GAODE && data != null) {
                Bundle extras = data.getExtras();
                PoiItem poiItem = extras.getParcelable(SearchMapActivity.POIITEM);
                if (poiItem != null) {
                    tvAddress.setText(poiItem.getTitle());
                    etAddress.setText(poiItem.getSnippet());
                    LatLonPoint latLonPoint = poiItem.getLatLonPoint();
                    if (latLonPoint != null) {
                        double latitude = latLonPoint.getLatitude();
                        double longitude = latLonPoint.getLongitude();
                        /*转百度位置*/
                        double[] doubles = Utils.gd_To_Bd(latitude, longitude);
                        lat=String.valueOf(doubles[0]);
                        lng=String.valueOf(doubles[1]);
                    }

                }
            } else if (requestCode == REQUEST_GOU_ADDRESS_CODE_GOOGLE && data != null) {
                ProgressDialogUtil.dismiss(this);
                Place place = PlacePicker.getPlace(this, data);
                tvAddress.setText(place.getName());
                etAddress.setText(place.getAddress());
                lat=String.valueOf(place.getLatLng().latitude);
                lng=String.valueOf(place.getLatLng().longitude);

            }
        }
    }

    @OnClick({R.id.back_iv, R.id.ll_address,R.id.bt_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_iv:
                onBackPressed();
                break;
            case R.id.ll_address:
                if (MyApplication.MAP.equals(Api.GAODE)) {
                    Intent intent = new Intent(this, SearchMapActivity.class);
                    startActivityForResult(intent, REQUEST_GOU_ADDRESS_CODE_GAODE);
                } else if (MyApplication.MAP.equals(Api.GOOGLE)) {
                    ProgressDialogUtil.showProgressDialog(this);
                    try {
                        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                        startActivityForResult(builder.build(this), REQUEST_GOU_ADDRESS_CODE_GOOGLE);
                    } catch (GooglePlayServicesRepairableException e) {
                        e.printStackTrace();
                    } catch (GooglePlayServicesNotAvailableException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case R.id.bt_confirm:
                if(TextUtils.isEmpty(tvAddress.getText().toString())
                        ||TextUtils.isEmpty(lat)
                        ||TextUtils.isEmpty(lng)
                        ||TextUtils.isEmpty(etAddress.getText().toString())){
                    Toast.makeText(this, R.string.请选择地址, Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent();
                intent.putExtra(LAT,lat);
                intent.putExtra(LNG,lng);
                intent.putExtra(ADDRESS,tvAddress.getText().toString()+etAddress.getText());
                setResult(RESULT_OK,intent);
                finish();
                break;
        }
    }
}
