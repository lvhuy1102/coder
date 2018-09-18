package com.hcpt.marketplace.activity;

import org.json.JSONObject;

import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.hcpt.marketplace.BaseActivity;
import com.hcpt.marketplace.R;
import com.hcpt.marketplace.config.GlobalValue;
import com.hcpt.marketplace.config.WebServiceConfig;
import com.hcpt.marketplace.modelmanager.ErrorNetworkHandler;
import com.hcpt.marketplace.modelmanager.ModelManager;
import com.hcpt.marketplace.modelmanager.ModelManagerListener;
import com.hcpt.marketplace.util.NetworkUtil;

public class AddReviewActivity extends BaseActivity implements OnClickListener {

    private ImageView mBtnBack;
    private RatingBar mRtb;
    private EditText mTxtReview;
    private TextView mBtnAdd;
    private TextView lblCurrentTextNumber, lblTotalTextNumber;

    private String mUser = "";
    private String mRate = "";
    private String mContent = "";
    private String mShopId = "";
    private String mFoodId = "";
    private int textTotal = 120;
    private int currentTextTotal = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_review);
        initUI();
        getExtraValues();
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        if (v == mBtnBack) {
            onBackPressed();
        } else if (v == mBtnAdd) {
            if (!NetworkUtil.checkNetworkAvailable(this)) {
                Toast.makeText(this, R.string.message_network_is_unavailable, Toast.LENGTH_LONG).show();
            } else {
                addReview();
            }
        }
    }

    private void getExtraValues() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            if (bundle.containsKey(GlobalValue.KEY_SHOP_ID)) {
                mShopId = bundle.getString(GlobalValue.KEY_SHOP_ID);
            }

            if (bundle.containsKey(GlobalValue.KEY_FOOD_ID)) {
                mFoodId = bundle.getString(GlobalValue.KEY_FOOD_ID);
            } else {
                mFoodId = "-1";
            }
        }
    }

    private void addReview() {
        // Get values.
        if (GlobalValue.myAccount != null) {
            mUser = GlobalValue.myAccount.getUserName();
        }
        mRate = (mRtb.getProgress() * 2) + "";
        mContent = mTxtReview.getText().toString().trim();

        // Call add api.
        if (mContent.isEmpty()) {
            Toast.makeText(
                    self,
                    getResources().getString(
                            R.string.please_leave_your_messages),
                    Toast.LENGTH_SHORT).show();
            mTxtReview.requestFocus();
        } else {
            ModelManager.addFoodReview(self, mShopId, mFoodId, mRate, mUser,
                    mContent, false, new ModelManagerListener() {

                        @Override
                        public void onError(VolleyError error) {
                            Toast.makeText(self, ErrorNetworkHandler.processError(error), Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onSuccess(Object object) {
                            try {
                                String json = (String) object;
                                if (!json.isEmpty()) {
                                    JSONObject obj = new JSONObject(json);
                                    if (obj.getString(WebServiceConfig.KEY_STATUS).equals(WebServiceConfig.KEY_STATUS_SUCCESS)) {
                                        Toast.makeText(
                                                self,
                                                R.string.message_adding_new_comment_successfully,
                                                Toast.LENGTH_SHORT).show();
                                        AddReviewActivity.this.finish();
                                    } else {
                                        Toast.makeText(
                                                self,
                                                R.string.error_adding_new_comment,
                                                Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(
                                            self,
                                            R.string.error_adding_new_comment,
                                            Toast.LENGTH_SHORT).show();
                                }
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }
                    });
        }
    }

    private void initUIControls() {
        // TODO Auto-generated method stub
        mBtnBack.setOnClickListener(this);
        mBtnAdd.setOnClickListener(this);
        mTxtReview.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                lblCurrentTextNumber.setText(s.length() + "");
            }
        });
    }

    private void initUI() {
        // TODO Auto-generated method stub
        mBtnBack = (ImageView) findViewById(R.id.btnBack);
        mRtb = (RatingBar) findViewById(R.id.rtbRating);
        mTxtReview = (EditText) findViewById(R.id.txtReview);
        mTxtReview.setFilters(new InputFilter[]{new InputFilter.LengthFilter(textTotal)});
        mBtnAdd = (TextView) findViewById(R.id.btnAdd);
        lblTotalTextNumber = (TextView) findViewById(R.id.lblTextTotalNumber);
        lblCurrentTextNumber = (TextView) findViewById(R.id.lblCurrentNumber);
        lblTotalTextNumber.setText("/" + textTotal);
        lblCurrentTextNumber.setText(currentTextTotal + "");

        // Should call this method at the end of declaring UI.
        initUIControls();
    }
}
