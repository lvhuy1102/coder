package com.hcpt.marketplace.activity;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.hcpt.marketplace.BaseActivity;
import com.hcpt.marketplace.R;
import com.hcpt.marketplace.adapter.ShopOrderAdapter;
import com.hcpt.marketplace.config.GlobalValue;
import com.hcpt.marketplace.modelmanager.ErrorNetworkHandler;
import com.hcpt.marketplace.modelmanager.ModelManager;
import com.hcpt.marketplace.modelmanager.ModelManagerListener;
import com.hcpt.marketplace.network.ParserUtility;
import com.hcpt.marketplace.object.OrderGroup;
import com.hcpt.marketplace.object.ShopOrder;
import com.hcpt.marketplace.util.NetworkUtil;

@SuppressLint("NewApi")
public class DetailShopOrderActivity extends BaseActivity implements
        OnClickListener {

    private ImageView btnBack;
    private ListView lsvShops;
    private ShopOrderAdapter shopAdapter;
    private ArrayList<ShopOrder> arrShopOrders;
    private OrderGroup orderGroup;
    private TextView lblSum, lblSTT, lblOrderTime;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_shop_order);
        initUI();
        initUIControls();
        // initData();

    }

    private void initUI() {
        btnBack = (ImageView) findViewById(R.id.btnBack);
        lsvShops = (ListView) findViewById(R.id.lsvShop);
        lblSum = (TextView) findViewById(R.id.lblTotalH);
        lblSTT = (TextView) findViewById(R.id.lblSTT);
        lblOrderTime = (TextView) findViewById(R.id.lblOrderTime);
    }

    private void initUIControls() {
        arrShopOrders = new ArrayList<ShopOrder>();
        shopAdapter = new ShopOrderAdapter(self, arrShopOrders);
        lsvShops.setAdapter(shopAdapter);
        lsvShops.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // TODO Auto-generated method stub
                GlobalValue.currentShopOrder = arrShopOrders.get(position);
                gotoActivity(DetailFoodOfShopOrderActivity.class);
            }
        });
        btnBack.setOnClickListener(this);
    }

    private void initData() {
        Bundle b = getIntent().getExtras();
        orderGroup = (OrderGroup) b.getSerializable(GlobalValue.KEY_ORDER_ID);
        //display data
        lblSTT.setText("#" + orderGroup.getId());
        lblOrderTime.setText(orderGroup.getDatetime());
        lblSum.setText(getResources().getString(R.string.currency) + orderGroup.getPrice());

        ModelManager.getListDetailOrder(self, orderGroup.getId(), true,
                new ModelManagerListener() {

                    @Override
                    public void onError(VolleyError error) {
                        arrShopOrders.clear();
                        shopAdapter.notifyDataSetChanged();
                        Toast.makeText(self, ErrorNetworkHandler.processError(error), Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onSuccess(Object object) {
                        // TODO Auto-generated method stub
                        String json = object.toString();
                        ArrayList<ShopOrder> list = ParserUtility
                                .parseListShopOrder(json);
                        if (list.size() > 0) {
                            arrShopOrders.clear();
                            arrShopOrders.addAll(list);
                            shopAdapter.notifyDataSetChanged();
                        }

                    }
                });
    }


    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
    }

    @Override
    public void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        if (!NetworkUtil.checkNetworkAvailable(this)) {
            Toast.makeText(this, R.string.message_network_is_unavailable, Toast.LENGTH_LONG).show();
        } else {
            initData();
        }
    }

    @Override
    protected void onStop() {
        // TODO Auto-generated method stub
        super.onStop();
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        if (v == btnBack) {
            onBackPressed();
        }
    }
}
