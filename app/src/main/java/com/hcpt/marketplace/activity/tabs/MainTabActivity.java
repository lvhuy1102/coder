package com.hcpt.marketplace.activity.tabs;

import android.app.Activity;
import android.app.TabActivity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;

import com.hcpt.marketplace.R;
import com.hcpt.marketplace.config.GlobalValue;
import com.hcpt.marketplace.object.Shop;
import com.hcpt.marketplace.util.DialogUtility;
import com.hcpt.marketplace.util.MySharedPreferences;

import java.util.ArrayList;


@SuppressWarnings("deprecation")
public class MainTabActivity extends TabActivity {
    public static final int TAB_HOME = 0;
    public static final int TAB_SEARCH = 1;
    public static final int TAB_MY_CART = 2;
    public static final int TAB_ACCOUNT = 3;
    TabHost tabHost = null;
    private Activity self;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_tabs);
        self = this;
        // restart app when catching crash issues.
        // Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));
        // init tabhost
        initTabPages();
        // Set value for shop cart
        if (GlobalValue.arrMyMenuShop == null) {
            GlobalValue.arrMyMenuShop = new ArrayList<Shop>();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshContent();
    }

    private void refreshContent() {
        updateIconSelected();
        if (tabHost.getCurrentTab() == TAB_ACCOUNT) {
            MainUserActivity activity = (MainUserActivity) getLocalActivityManager()
                    .getActivity(GlobalValue.KEY_TAB_ACCOUNT);
            activity.refreshContent();
        }
    }

    private void initTabPages() {

        tabHost = getTabHost();

        tabHost.addTab(tabHost.newTabSpec(GlobalValue.KEY_TAB_HOME)
                .setIndicator(createTabIndicator(R.drawable.ic_home_variant_white_64, this.getString(R.string.home_upper_case)))
                .setContent(new Intent(this, HomeActivity.class)));

        tabHost.addTab(tabHost
                .newTabSpec(GlobalValue.KEY_TAB_SEARCH)
                .setIndicator(
                        createTabIndicator(R.drawable.ic_search_gray_64, this.getString(R.string.search_upper_case)))
                .setContent(new Intent(this, SearchActivity.class)));

        tabHost.addTab(tabHost
                .newTabSpec(GlobalValue.KEY_TAB_MY_MENU)
                .setIndicator(
                        createTabIndicator(R.drawable.ic_shopping_cart_gray_64, this.getString(R.string.my_cart_upper_case)))
                .setContent(new Intent(this, MainCartActivity.class)));

        tabHost.addTab(tabHost
                .newTabSpec(GlobalValue.KEY_TAB_ACCOUNT)
                .setIndicator(createTabIndicator(R.drawable.ic_account_gray_64, this.getString(R.string.login_upper_case)))
                .setContent(new Intent(this, MainUserActivity.class)));


        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                // TODO Auto-generated method stub
                updateIconSelected();
                if (tabId.equals(GlobalValue.KEY_TAB_MY_MENU)) {
                    new MySharedPreferences(MainTabActivity.this).setFirstOpenSearchScreen(true);
                    MainCartActivity activity = (MainCartActivity) getLocalActivityManager()
                            .getActivity(GlobalValue.KEY_TAB_MY_MENU);
                    activity.refreshContent();

                } else if (tabId.equals(GlobalValue.KEY_TAB_HOME)) {
                    new MySharedPreferences(MainTabActivity.this).setFirstOpenSearchScreen(true);
                } else if (tabId.equals(GlobalValue.KEY_TAB_ACCOUNT)) {
                    new MySharedPreferences(MainTabActivity.this).setFirstOpenSearchScreen(true);
                    MainUserActivity activity = (MainUserActivity) getLocalActivityManager()
                            .getActivity(GlobalValue.KEY_TAB_ACCOUNT);
                    activity.refreshContent();
                } else if (tabId.equals(GlobalValue.KEY_TAB_SEARCH)) {

                }
            }
        });

        tabHost.setCurrentTab(TAB_HOME);
        updateIconSelected();
    }

    public void updateIconSelected() {
        TabWidget tabwidget = tabHost.getTabWidget();
        //check login or not
        if (GlobalValue.myAccount == null) {
            ((TextView) tabwidget.getChildTabViewAt(TAB_ACCOUNT).findViewById(R.id.text))
                    .setText(getString(R.string.login_upper_case));
        } else {
            ((TextView) tabwidget.getChildTabViewAt(TAB_ACCOUNT).findViewById(R.id.text))
                    .setText(getString(R.string.account_upper_case));
        }

        if (tabHost.getCurrentTab() == TAB_HOME) {
            tabwidget.getChildTabViewAt(TAB_HOME).findViewById(R.id.imgIcon)
                    .setBackgroundResource(R.drawable.ic_home_variant_white_64);
            ((TextView) tabwidget.getChildTabViewAt(TAB_HOME).findViewById(R.id.text))
                    .setTextColor(Color.WHITE);
            tabwidget.getChildTabViewAt(TAB_SEARCH).findViewById(R.id.imgIcon)
                    .setBackgroundResource(R.drawable.ic_search_gray_64);
            ((TextView) tabwidget.getChildTabViewAt(TAB_SEARCH).findViewById(R.id.text))
                    .setTextColor(getResources().getColor(R.color.gray_home_bottom));
            tabwidget.getChildTabViewAt(TAB_MY_CART).findViewById(R.id.imgIcon)
                    .setBackgroundResource(R.drawable.ic_shopping_cart_gray_64);
            ((TextView) tabwidget.getChildTabViewAt(TAB_MY_CART).findViewById(R.id.text))
                    .setTextColor(getResources().getColor(R.color.gray_home_bottom));
            tabwidget.getChildTabViewAt(TAB_ACCOUNT).findViewById(R.id.imgIcon)
                    .setBackgroundResource(R.drawable.ic_account_gray_64);
            ((TextView) tabwidget.getChildTabViewAt(TAB_ACCOUNT).findViewById(R.id.text))
                    .setTextColor(getResources().getColor(R.color.gray_home_bottom));

        } else if (tabHost.getCurrentTab() == TAB_SEARCH) {
            tabwidget.getChildTabViewAt(TAB_HOME).findViewById(R.id.imgIcon)
                    .setBackgroundResource(R.drawable.ic_home_variant_gray_64);
            ((TextView) tabwidget.getChildTabViewAt(TAB_HOME).findViewById(R.id.text))
                    .setTextColor(getResources().getColor(R.color.gray_home_bottom));
            tabwidget.getChildTabViewAt(TAB_SEARCH).findViewById(R.id.imgIcon)
                    .setBackgroundResource(R.drawable.ic_search_white_64);
            ((TextView) tabwidget.getChildTabViewAt(TAB_SEARCH).findViewById(R.id.text))
                    .setTextColor(Color.WHITE);
            tabwidget.getChildTabViewAt(TAB_MY_CART).findViewById(R.id.imgIcon)
                    .setBackgroundResource(R.drawable.ic_shopping_cart_gray_64);
            ((TextView) tabwidget.getChildTabViewAt(TAB_MY_CART).findViewById(R.id.text))
                    .setTextColor(getResources().getColor(R.color.gray_home_bottom));
            tabwidget.getChildTabViewAt(TAB_ACCOUNT).findViewById(R.id.imgIcon)
                    .setBackgroundResource(R.drawable.ic_account_gray_64);
            ((TextView) tabwidget.getChildTabViewAt(TAB_ACCOUNT).findViewById(R.id.text))
                    .setTextColor(getResources().getColor(R.color.gray_home_bottom));
        } else if (tabHost.getCurrentTab() == TAB_MY_CART) {
            tabwidget.getChildTabViewAt(TAB_HOME).findViewById(R.id.imgIcon)
                    .setBackgroundResource(R.drawable.ic_home_variant_gray_64);
            ((TextView) tabwidget.getChildTabViewAt(TAB_HOME).findViewById(R.id.text))
                    .setTextColor(getResources().getColor(R.color.gray_home_bottom));
            tabwidget.getChildTabViewAt(TAB_SEARCH).findViewById(R.id.imgIcon)
                    .setBackgroundResource(R.drawable.ic_search_gray_64);
            ((TextView) tabwidget.getChildTabViewAt(TAB_SEARCH).findViewById(R.id.text))
                    .setTextColor(getResources().getColor(R.color.gray_home_bottom));
            tabwidget.getChildTabViewAt(TAB_MY_CART).findViewById(R.id.imgIcon)
                    .setBackgroundResource(R.drawable.ic_shopping_cart_white_64);
            ((TextView) tabwidget.getChildTabViewAt(TAB_MY_CART).findViewById(R.id.text))
                    .setTextColor(Color.WHITE);
            tabwidget.getChildTabViewAt(TAB_ACCOUNT).findViewById(R.id.imgIcon)
                    .setBackgroundResource(R.drawable.ic_account_gray_64);
            ((TextView) tabwidget.getChildTabViewAt(TAB_ACCOUNT).findViewById(R.id.text))
                    .setTextColor(getResources().getColor(R.color.gray_home_bottom));
        } else if (tabHost.getCurrentTab() == TAB_ACCOUNT) {

            tabwidget.getChildTabViewAt(TAB_HOME).findViewById(R.id.imgIcon)
                    .setBackgroundResource(R.drawable.ic_home_variant_gray_64);
            ((TextView) tabwidget.getChildTabViewAt(TAB_HOME).findViewById(R.id.text))
                    .setTextColor(getResources().getColor(R.color.gray_home_bottom));
            tabwidget.getChildTabViewAt(TAB_SEARCH).findViewById(R.id.imgIcon)
                    .setBackgroundResource(R.drawable.ic_search_gray_64);
            ((TextView) tabwidget.getChildTabViewAt(TAB_SEARCH).findViewById(R.id.text))
                    .setTextColor(getResources().getColor(R.color.gray_home_bottom));
            tabwidget.getChildTabViewAt(TAB_MY_CART).findViewById(R.id.imgIcon)
                    .setBackgroundResource(R.drawable.ic_shopping_cart_gray_64);
            ((TextView) tabwidget.getChildTabViewAt(TAB_MY_CART).findViewById(R.id.text))
                    .setTextColor(getResources().getColor(R.color.gray_home_bottom));
            tabwidget.getChildTabViewAt(TAB_ACCOUNT).findViewById(R.id.imgIcon)
                    .setBackgroundResource(R.drawable.ic_account_white_64);
            ((TextView) tabwidget.getChildTabViewAt(TAB_ACCOUNT).findViewById(R.id.text))
                    .setTextColor(Color.WHITE);
        }
    }


    private View createTabIndicator(int resource, String text) {
        View tabIndicator = getLayoutInflater()
                .inflate(R.layout.view_tab, null);
        ImageView image = (ImageView) tabIndicator.findViewById(R.id.imgIcon);
        image.setBackgroundResource(resource);
        TextView content = (TextView) tabIndicator.findViewById(R.id.text);
        content.setText(text);
        return tabIndicator;
    }

    public void gotoActivity(Class<?> cla) {
        Intent intent = new Intent(this, cla);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left2);
    }

    public void gotoActivity(Class<?> cla, Bundle bundle) {
        Intent intent = new Intent(this, cla);
        intent.putExtras(bundle);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_left, R.anim.push_left_out);
    }

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        showQuitDialog();
    }

    private void showQuitDialog() {

        DialogUtility.showYesNoDialog(self, R.string.message_quit_app, R.string.yes, R.string.no, new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (GlobalValue.myAccount != null) {
                    GlobalValue.myAccount = null;
                }
                new MySharedPreferences(self).setCacheUserInfo("");
                finish();
            }
        });
    }
}