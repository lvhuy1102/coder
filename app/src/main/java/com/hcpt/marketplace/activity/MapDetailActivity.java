package com.hcpt.marketplace.activity;

import java.util.HashMap;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.androidquery.AQuery;
import com.androidquery.callback.AjaxStatus;
import com.androidquery.callback.BitmapAjaxCallback;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.hcpt.marketplace.BaseActivity;
import com.hcpt.marketplace.R;
import com.hcpt.marketplace.config.GlobalValue;
import com.hcpt.marketplace.modelmanager.ErrorNetworkHandler;
import com.hcpt.marketplace.modelmanager.ModelManager;
import com.hcpt.marketplace.modelmanager.ModelManagerListener;
import com.hcpt.marketplace.network.ParserUtility;
import com.hcpt.marketplace.object.Shop;
import com.hcpt.marketplace.util.ImageUtil;
import com.hcpt.marketplace.util.Logger;
import com.hcpt.marketplace.util.NetworkUtil;

@SuppressLint("NewApi")
public class MapDetailActivity extends BaseActivity implements OnClickListener {

    private GoogleMap googleMap;
    // private ArrayList<Shop> arrShop;
    private Marker currentMaker;
    private HashMap<String, Shop> markerRestaurantMap;
    private int shopId = -1;
    private Shop shop;
    private ImageView btnBack;
    private AQuery aq;
    private TextView lblShopName, lblAddress;
    public static BaseActivity self;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        self = this;
        aq = new AQuery(this);
        setContentView(R.layout.activity_map_of_shop);
        initUI();
        initControl();
        initGoogleMap();
        setData();

    }

    private void initUI() {
        btnBack = (ImageView) findViewById(R.id.btnBack);
        lblShopName = (TextView) findViewById(R.id.lblShopName);
        lblAddress = (TextView) findViewById(R.id.lblAddress);
    }

    private void initControl() {

        btnBack.setOnClickListener(this);
        lblShopName.setOnClickListener(this);
    }

    private void initGoogleMap() {
        // initImageLoader();
        int status = GooglePlayServicesUtil
                .isGooglePlayServicesAvailable(getBaseContext());

        if (status != ConnectionResult.SUCCESS) {
            int requestCode = 10;
            Dialog dialog = GooglePlayServicesUtil.getErrorDialog(status, this,
                    requestCode);
            dialog.show();

        } else {
            SupportMapFragment fm = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);
            googleMap = fm.getMap();
            googleMap.setMyLocationEnabled(true);
            googleMap.setInfoWindowAdapter(new CustomInfoWindowAdapter());
            googleMap
                    .setOnInfoWindowClickListener(new OnInfoWindowClickListener() {

                        @Override
                        public void onInfoWindowClick(Marker marker) {
                            // TODO Auto-generated method stub
                            // String title = marker.getTitle();
                        }
                    });

        }
    }

    private void setData() {
        // arrShop = new ArrayList<Shop>();
//        showToastMessage(getString(R.string.message_demo_location));
        Bundle b = getIntent().getExtras();
        if (b != null) {
            shopId = b.getInt(GlobalValue.KEY_SHOP_ID);

        }

        if (shopId != -1) {
            if (!NetworkUtil.checkNetworkAvailable(this)) {
                Toast.makeText(this, R.string.message_network_is_unavailable, Toast.LENGTH_LONG).show();
            } else
                ModelManager.getShopById(self, shopId, true,
                        new ModelManagerListener() {

                            @Override
                            public void onSuccess(Object object) {
                                // TODO Auto-generated method stub
                                String json = (String) object;
                                shop = ParserUtility.parseShop(json);
                                if (shop != null) {
                                    lblShopName.setText(shop.getShopName());
                                    lblAddress.setText(shop.getAddress());
                                    setDataToMap();
                                }
                            }

                            @Override
                            public void onError(VolleyError error) {
                                // TODO Auto-generated method stub
                                Toast.makeText(self, ErrorNetworkHandler.processError(error), Toast.LENGTH_LONG).show();
                            }
                        });
        }

    }

    private void setDataToMap() {
        if (markerRestaurantMap == null) {
            markerRestaurantMap = new HashMap<String, Shop>();
        } else {
            if (!markerRestaurantMap.isEmpty()) {
                markerRestaurantMap.clear();
            }
            googleMap.clear();
        }

        Marker marker;
        if (shop != null) {
            LatLng item = new LatLng(shop.getLatitude(), shop.getLongitude());

            marker = googleMap.addMarker(new MarkerOptions().position(item)
                    .title(shop.getShopName()));
            try {
                marker.setIcon(BitmapDescriptorFactory
                        .fromResource(R.drawable.ic_address_map));
//                // set marker icon
//                Bitmap bitmap = ImageUtil.createBitmapFromUrl(shop.getImage());
//                // resize
//                int size = ImageUtil.getSizeBaseOnDensity(self, 30);
//                bitmap = ImageUtil.getResizedBitmap(bitmap, size, size);
//                // ser Bitmap to marker
//                marker.setIcon(BitmapDescriptorFactory.fromBitmap(bitmap));
            } catch (Exception e) {
                marker.setIcon(BitmapDescriptorFactory
                        .fromResource(R.drawable.ic_address_map));
            }
            markerRestaurantMap.put(marker.getId(), shop);
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(item));
            googleMap.animateCamera(CameraUpdateFactory.zoomTo(15));
        }

    }

    private class CustomInfoWindowAdapter implements InfoWindowAdapter {

        private View v;

        public CustomInfoWindowAdapter() {
            v = getLayoutInflater().inflate(R.layout.map_detail, null);
        }

        @Override
        public View getInfoContents(Marker marker) {
            if (currentMaker != null && !currentMaker.isInfoWindowShown()) {
                currentMaker.showInfoWindow();
            }
            return null;
        }

        @Override
        public View getInfoWindow(final Marker marker) {
            currentMaker = marker;
            Logger.d("MapMaker iD ", marker.getId());
            Logger.d("MapMaker", markerRestaurantMap.toString());

            Shop shop = markerRestaurantMap.get(marker.getId());
            TextView lblName = (TextView) v.findViewById(R.id.lblShopName);
            TextView lblAddress = (TextView) v.findViewById(R.id.lblAddress);
            TextView lblCategory = (TextView) v.findViewById(R.id.lblCategoryName);
            lblName.setSelected(true);
            if (shop != null) {
                lblName.setText(shop.getShopName());
                lblAddress.setText(shop.getAddress());
                lblCategory.setText(shop.getCategory());

            } else {
                Logger.d("AAA", "Restaurant is null");
            }
            return v;
        }
    }

    public void gotoActive(Shop shop, Class<?> cls) {
        // startActivity(intent);
        Intent returnIntent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putInt("id", shop.getShopId());
        bundle.putString("name", shop.getShopName());
        returnIntent.putExtra("result", bundle);
        setResult(RESULT_OK, returnIntent);
        finish();
    }

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
    }


    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        if (v == btnBack) {
            onBackPressed();
        } else if (v == lblShopName) {
            gotoShopDetail(shopId);
        }
    }

}
