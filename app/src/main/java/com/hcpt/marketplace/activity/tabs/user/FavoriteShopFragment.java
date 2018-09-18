package com.hcpt.marketplace.activity.tabs.user;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.hcpt.marketplace.BaseActivity;
import com.hcpt.marketplace.R;
import com.hcpt.marketplace.activity.ShopDetailActivity;
import com.hcpt.marketplace.adapter.ShopAdapter;
import com.hcpt.marketplace.config.GlobalValue;
import com.hcpt.marketplace.object.Shop;

import java.util.ArrayList;

@SuppressLint("NewApi")
public final class FavoriteShopFragment extends Fragment implements OnClickListener {

	public Activity act;
	private View view;
	private ListView listView;
	public ArrayList<Shop> arrayList;
	private ShopAdapter shopAdapter;

	public static FavoriteShopFragment setInstance(Activity act,ArrayList<Shop> arrShop) {
		FavoriteShopFragment fragment = new FavoriteShopFragment();
		fragment.act=act;
		fragment.arrayList = arrShop;
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		view = inflater.inflate(R.layout.fragment_favorite_list, null);
		listView=(ListView)view.findViewById(R.id.listView);
		shopAdapter=new ShopAdapter(act,arrayList);
		listView.setAdapter(shopAdapter);
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
				Shop shop=arrayList.get(i);
				Bundle bundle = new Bundle();
				bundle.putInt(GlobalValue.KEY_SHOP_ID, shop.getShopId());
				((BaseActivity)act).gotoActivity(act, ShopDetailActivity.class, bundle);
			}
		});
		return view;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
	}
}
