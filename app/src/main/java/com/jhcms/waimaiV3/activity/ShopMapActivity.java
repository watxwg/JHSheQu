package com.jhcms.waimaiV3.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.jhcms.common.utils.StatusBarUtil;
import com.jhcms.waimaiV3.R;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.jhcms.waimaiV3.R.id.view;

public class ShopMapActivity extends AppCompatActivity {

    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.map)
    MapView map;
    private AMap aMap;
    private MarkerOptions markerOptions;
    private Marker marker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_map);
        ButterKnife.bind(this);
        map.onCreate(savedInstanceState);
        initData();

    }

    private void initData() {
        StatusBarUtil.immersive(this, R.color.theme_color, 0.6f);
        StatusBarUtil.setPaddingSmart(this, toolbar);
        double lat = getIntent().getDoubleExtra("lat", 0);
        double lng = getIntent().getDoubleExtra("lng", 0);
        tvTitle.setText("商家位置");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        if (aMap == null) {
            aMap = map.getMap();
            LatLng latLng = null;
            latLng = new LatLng(lat, lng);
            addMarkersToMap(latLng);// 往地图上添加marker
        }
    }

    /**
     * 在地图上添加marker
     */
    private void addMarkersToMap(LatLng latLng) {
        changeCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition(latLng, 18, 30, 30)), null);
        markerOptions = new MarkerOptions()
                .position(latLng)
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.waimai_my_btn_location))
//                .title("商家位置")
                .draggable(true);
        marker = aMap.addMarker(markerOptions);
        marker.showInfoWindow();
    }

    /**
     * 根据动画按钮状态，调用函数animateCamera或moveCamera来改变可视区域
     */
    private void changeCamera(CameraUpdate update, AMap.CancelableCallback callback) {
        /*不带动画*/
//        aMap.moveCamera(update);
        /*带动画移动*/
        aMap.animateCamera(update, 1000, callback);
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onResume() {
        super.onResume();
        map.onResume();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onPause() {
        super.onPause();
        map.onPause();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        map.onSaveInstanceState(outState);
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        map.onDestroy();
    }

}
