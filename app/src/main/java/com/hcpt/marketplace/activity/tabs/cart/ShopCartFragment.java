package com.hcpt.marketplace.activity.tabs.cart;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.hcpt.marketplace.BaseFragment;
import com.hcpt.marketplace.R;
import com.hcpt.marketplace.activity.tabs.MainCartActivity;
import com.hcpt.marketplace.activity.tabs.MainTabActivity;
import com.hcpt.marketplace.adapter.ShopCartAdapter;
import com.hcpt.marketplace.adapter.ShopCartAdapter.ShopCartListener;
import com.hcpt.marketplace.config.GlobalValue;
import com.hcpt.marketplace.object.Shop;
import com.hcpt.marketplace.util.CustomToast;

@SuppressLint("NewApi")
public class ShopCartFragment extends BaseFragment implements OnClickListener {

    private TextView btnOrder, lblNoData;
    private TextView lblSum;
    private ListView lsvShops;
    private ShopCartAdapter shopAdapter;
    private double total;
    private MainCartActivity self;

    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_list_shop_cart, container,
                false);
        self = (MainCartActivity) getActivity();
        initUI(view);
        initData();
        return view;
    }

    private void initUI(View view) {
        btnOrder = (TextView) view.findViewById(R.id.btnOrder);
        lblSum = (TextView) view.findViewById(R.id.lblSum);
        lsvShops = (ListView) view.findViewById(R.id.lsvShop);
        lblNoData = (TextView) view.findViewById(R.id.lblNoData);
        btnOrder.setOnClickListener(this);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        // TODO Auto-generated method stub
        super.onHiddenChanged(hidden);
        if (!hidden) {
            refreshContent();
        }
    }

    private void initData() {
        shopAdapter = new ShopCartAdapter(self, GlobalValue.arrMyMenuShop,
                new ShopCartListener() {

                    @Override
                    public void showDetailOrder(int position) {
                        // TODO Auto-generated method stub
                        Bundle b = new Bundle();
                        b.putInt("position", position);
                        ((MainTabActivity) self.getParent()).gotoActivity(
                                ShopCartDetailActivity.class, b);

                    }

                    @Override
                    public void deleteItem(int position) {
                        // TODO Auto-generated method stub
                        GlobalValue.arrMyMenuShop.remove(position);
                        shopAdapter.notifyDataSetChanged();
                    }
                });
        lsvShops.setAdapter(shopAdapter);

    }

    @Override
    public void refreshContent() {
        total = (double) 0;

        if (GlobalValue.arrMyMenuShop.size() > 0) {
            shopAdapter.notifyDataSetChanged();
            double totalOfShop = 0;
            double VATOfShop = 0;
            double ShipPriceOfShop = 0;

            for (int i = 0; i < GlobalValue.arrMyMenuShop.size(); i++) {
                Shop shop = GlobalValue.arrMyMenuShop.get(i);
                if (shop.getArrOrderFoods().size() == 0) {
                    GlobalValue.arrMyMenuShop.remove(i);
                } else {
                    totalOfShop += shop.getTotalPrice();
                    VATOfShop += shop.getCurrentTotalVAT();
                    ShipPriceOfShop += shop.getcurrentShipping();
                }
            }
            total = totalOfShop + VATOfShop + ShipPriceOfShop;
            lblNoData.setVisibility(View.GONE);
            lsvShops.setVisibility(View.VISIBLE);
        } else {
            lblNoData.setVisibility(View.VISIBLE);
            lsvShops.setVisibility(View.GONE);
        }

        lblSum.setText(String.format("%.1f", total));
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        if (v == btnOrder) {
            onBtnOrderClick();
        }
    }

    private void onBtnOrderClick() {
        if (GlobalValue.myAccount == null) {
            CustomToast.showCustomAlert(self,
                    self.getString(R.string.message_no_login),
                    Toast.LENGTH_SHORT);
        } else {

            if (GlobalValue.arrMyMenuShop.size() > 0) {
                ((MainCartActivity) self)
                        .gotoFragment(MainCartActivity.PAGE_CONFIRM);
            } else {
                CustomToast.showCustomAlert(self,
                        self.getString(R.string.message_no_item_menu),
                        Toast.LENGTH_SHORT);
            }
        }
    }

}
