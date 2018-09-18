package com.hcpt.marketplace.activity.tabs.user;

import java.util.ArrayList;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.hcpt.marketplace.BaseFragment;
import com.hcpt.marketplace.R;
import com.hcpt.marketplace.activity.DetailShopOrderActivity;
import com.hcpt.marketplace.activity.tabs.MainTabActivity;
import com.hcpt.marketplace.activity.tabs.MainUserActivity;
import com.hcpt.marketplace.adapter.ListOrderAdapter;
import com.hcpt.marketplace.config.GlobalValue;
import com.hcpt.marketplace.modelmanager.ErrorNetworkHandler;
import com.hcpt.marketplace.modelmanager.ModelManager;
import com.hcpt.marketplace.modelmanager.ModelManagerListener;
import com.hcpt.marketplace.network.ParserUtility;
import com.hcpt.marketplace.object.OrderGroup;
import com.hcpt.marketplace.pulltorefresh.PullToRefreshBase;
import com.hcpt.marketplace.pulltorefresh.PullToRefreshListView;
import com.hcpt.marketplace.pulltorefresh.PullToRefreshBase.OnRefreshListener2;

public class HistoryFragment extends BaseFragment {
	private View view;
	private ArrayList<OrderGroup> arrOrder = new ArrayList<OrderGroup>();
	private PullToRefreshListView lvOrder;
	private ListView lsvActually;
	private ListOrderAdapter adapter;
	private ImageView btnBack;
	private WebView wbChef;
	private LinearLayout layoutUser;
	private int page = 0;
	private boolean isLoadMore = true;
	private MainUserActivity self;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.fragment_history, container, false);
		self = (MainUserActivity) getActivity();
		initUI(view);
		initControlUI();
		initData();
		return view;
	}

	@Override
	public void onHiddenChanged(boolean hidden) {
		// TODO Auto-generated method stub
		super.onHiddenChanged(hidden);
		if (!hidden) {
			if (GlobalValue.myAccount.isUser()) {
				wbChef.setVisibility(View.GONE);
				layoutUser.setVisibility(View.VISIBLE);
				if (self.isLoadNew()) {
					getListOrder(true, false);
					self.setLoadNew(false);
				}
			} else {
				wbChef.setVisibility(View.VISIBLE);
				layoutUser.setVisibility(View.GONE);
				wbChef.loadUrl(GlobalValue.myAccount.getRedirectLink());
			}
		}
	}

	private void loadWebView() {
		wbChef.setHorizontalScrollBarEnabled(true);
		wbChef.getSettings().setAllowFileAccess(true);
		wbChef.getSettings().setJavaScriptEnabled(true);
		wbChef.getSettings().setBuiltInZoomControls(true);
		wbChef.getSettings().setUseWideViewPort(true);
		wbChef.setInitialScale(1);
		wbChef.setWebChromeClient(new WebChromeClient() {

			public void onProgressChanged(WebView webview, int progress) {

			}

		});

		wbChef.setWebViewClient(new WebViewClient() {
			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {

				super.onPageStarted(view, url, favicon);

			}

			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {

				return super.shouldOverrideUrlLoading(view, url);

			}

			@Override
			public void onPageFinished(WebView view, String url) {

				super.onPageFinished(view, url);

			}

			public void onReceivedError(WebView view, int errorCode,
					String description, String failingUrl) {
			}
		});
	}

	private void initData() {
		// TODO Auto-generated method stub

	}

	private void getListOrder(boolean isRefresh, boolean isPull) {

		if (isRefresh) {
			page = 0;
			arrOrder.clear();
			isLoadMore = true;
		}

		if (!isLoadMore) {
			Toast.makeText(self, "No more data!", Toast.LENGTH_SHORT).show();
			new Handler().postDelayed(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					lvOrder.onRefreshComplete();
				}
			}, 1000);

		} else {
			// add more page
			page++;
			// TODO Auto-generated method stub
			ModelManager.getListOrder(self, GlobalValue.myAccount.getId() + "",
					page, !isPull, new ModelManagerListener() {

						@Override
						public void onSuccess(Object object) {
							// TODO Auto-generated method stub
							String json = (String) object;
							ArrayList<OrderGroup> list = ParserUtility
									.parseListOrderGroup(json);

							if (list.size() > 0) {
								arrOrder.addAll(list);
								isLoadMore = true;
							} else {
								isLoadMore = false;
								Toast.makeText(self, "No more data!",
										Toast.LENGTH_SHORT).show();
							}
							adapter.notifyDataSetChanged();
							lvOrder.onRefreshComplete();
						}

						@Override
						public void onError(VolleyError error) {
							// TODO Auto-generated method stub
							adapter.notifyDataSetChanged();
							lvOrder.onRefreshComplete();
							Toast.makeText(self, ErrorNetworkHandler.processError(error), Toast.LENGTH_LONG).show();
						}
					});
		}
	}

	private void initControlUI() {
		// set up listview

		adapter = new ListOrderAdapter(self, R.layout.row_list_history,
				arrOrder);
		lvOrder.setAdapter(adapter);

		// TODO Auto-generated method stub
		lvOrder.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				OrderGroup o = arrOrder.get(position - 1);
				Bundle b = new Bundle();
				b.putSerializable(GlobalValue.KEY_ORDER_ID, o);
				((MainTabActivity) self.getParent()).gotoActivity(
						DetailShopOrderActivity.class, b);
			}
		});

		lvOrder.setOnRefreshListener(new OnRefreshListener2<ListView>() {
			@Override
			public void onPullDownToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				String label = DateUtils.formatDateTime(getActivity(),
						System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME
								| DateUtils.FORMAT_SHOW_DATE
								| DateUtils.FORMAT_ABBREV_ALL);
				// Update the LastUpdatedLabel
				refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
				getListOrder(true, true);
			}

			@Override
			public void onPullUpToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				getListOrder(false, true);
			}
		});

		btnBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				MainUserActivity activity = (MainUserActivity) self;
				activity.backFragment(MainUserActivity.MYACC_PAGE);
			}
		});

		loadWebView();

	}

	private void initUI(View view) {
		// TODO Auto-generated method stub
		lvOrder = (PullToRefreshListView) view.findViewById(R.id.lvOrder);
		lsvActually = lvOrder.getRefreshableView();
		btnBack = (ImageView) view.findViewById(R.id.btnBack);
		wbChef = (WebView) view.findViewById(R.id.wbChef);
		layoutUser = (LinearLayout) view.findViewById(R.id.layoutUser);

	}
}
