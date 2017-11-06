package com.jhcms.waimaiV3.activity;

import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.animation.BounceInterpolator;
import android.view.animation.Interpolator;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.jhcms.waimaiV3.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ShopMapActivityGMS extends SwipeBaseActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    private GoogleMap mMap;
    public static CameraPosition BONDI = null;
    private double lat, lng;
    private String address;
    private String title;

    @Override
    protected void initData() {
        setToolBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> finish());
        lat = getIntent().getDoubleExtra("lat", 0);
        lng = getIntent().getDoubleExtra("lng", 0);
        address = getIntent().getExtras().getString("address");
        title = getIntent().getExtras().getString("title");
        if (TextUtils.isEmpty(title)) {
            tvTitle.setText("商家位置");
        } else {
            tvTitle.setText(title);
        }
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_shop_map_gms);
        ButterKnife.bind(this);
        /**GooleMap
         * */
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.google_map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng sydney = new LatLng(lat, lng);
        mMap.setOnMarkerClickListener(this);
        mMap.getUiSettings().setZoomControlsEnabled(false);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        mMap.addMarker(new MarkerOptions().position(sydney).title(address));
//        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 10));
        BONDI = new CameraPosition.Builder().target(sydney)
                .zoom(15.5f)
                .bearing(0)/*地图旋转角度*/
                .tilt(50)
                .build();
        changeCamera(CameraUpdateFactory.newCameraPosition(BONDI));
    }

    private void changeCamera(CameraUpdate update) {
        changeCamera(update, null);
    }

    /**
     * Change the camera position by moving or animating the camera depending on the state of the
     * animate toggle button.
     */
    private void changeCamera(CameraUpdate update, GoogleMap.CancelableCallback callback) {
        mMap.animateCamera(update, callback);
    }

    @Override
    public boolean onMarkerClick(final Marker marker) {
        // This causes the marker at Perth to bounce into position when it is clicked.
        final Handler handler = new Handler();
        final long start = SystemClock.uptimeMillis();
        final long duration = 1500;

        final Interpolator interpolator = new BounceInterpolator();

        handler.post(new Runnable() {
            @Override
            public void run() {
                long elapsed = SystemClock.uptimeMillis() - start;
                float t = Math.max(
                        1 - interpolator.getInterpolation((float) elapsed / duration), 0);
                marker.setAnchor(0.5f, 1.0f + 2 * t);

                if (t > 0.0) {
                    // Post again 16ms later.
                    handler.postDelayed(this, 16);
                }
            }
        });

        // Markers have a z-index that is settable and gettable.
        float zIndex = marker.getZIndex() + 1.0f;
        marker.setZIndex(zIndex);

        // We return false to indicate that we have not consumed the event and that we wish
        // for the default behavior to occur (which is for the camera to move such that the
        // marker is centered and for the marker's info window to open, if it has one).
        return false;
    }
}
