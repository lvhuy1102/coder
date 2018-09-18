package com.hcpt.marketplace.activity.tabs.user;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.hcpt.marketplace.BaseActivity;
import com.hcpt.marketplace.R;
import com.hcpt.marketplace.activity.ProductDetailActivity;
import com.hcpt.marketplace.activity.ShopDetailActivity;
import com.hcpt.marketplace.adapter.ListFoodAdapter;
import com.hcpt.marketplace.adapter.ShopAdapter;
import com.hcpt.marketplace.config.GlobalValue;
import com.hcpt.marketplace.object.Menu;
import com.hcpt.marketplace.object.Shop;

import java.util.ArrayList;

@SuppressLint("NewApi")
public final class FavoriteProductFragment extends Fragment implements OnClickListener {

    public Activity act;
    private View view;
    private ListView listView;
    public ArrayList<Menu> arrayList;
    private ListFoodAdapter productAdapter;

    public static FavoriteProductFragment setInstance(Activity act, ArrayList<Menu> arrProduct) {
        FavoriteProductFragment fragment = new FavoriteProductFragment();
        fragment.act = act;
        fragment.arrayList = arrProduct;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.fragment_favorite_list, null);
        listView = (ListView) view.findViewById(R.id.listView);
        productAdapter = new ListFoodAdapter(act, arrayList);
        listView.setAdapter(productAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Menu menu = arrayList.get(i);
                Bundle bundle = new Bundle();
                bundle.putString(GlobalValue.KEY_FOOD_ID, menu.getId() + "");
                ((BaseActivity) act).gotoActivity(act, ProductDetailActivity.class, bundle);
            }
        });
        return view;
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
    }
}
