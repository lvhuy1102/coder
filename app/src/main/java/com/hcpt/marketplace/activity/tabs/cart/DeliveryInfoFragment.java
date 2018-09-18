package com.hcpt.marketplace.activity.tabs.cart;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.hcpt.marketplace.BaseFragment;
import com.hcpt.marketplace.R;
import com.hcpt.marketplace.activity.tabs.MainCartActivity;
import com.hcpt.marketplace.adapter.PaymentMethodAdapter;
import com.hcpt.marketplace.adapter.SpinnerSimpleAdapter;
import com.hcpt.marketplace.config.Constant;
import com.hcpt.marketplace.config.GlobalValue;
import com.hcpt.marketplace.config.WebServiceConfig;
import com.hcpt.marketplace.modelmanager.ErrorNetworkHandler;
import com.hcpt.marketplace.modelmanager.ModelManager;
import com.hcpt.marketplace.modelmanager.ModelManagerListener;
import com.hcpt.marketplace.object.Menu;
import com.hcpt.marketplace.object.Shop;
import com.hcpt.marketplace.util.CustomToast;
import com.hcpt.marketplace.util.DialogUtility;
import com.hcpt.marketplace.util.NetworkUtil;
import com.hcpt.marketplace.util.StringUtility;
import com.hcpt.marketplace.widget.AutoBgButton;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PaymentActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;

@SuppressLint("NewApi")
public class DeliveryInfoFragment extends BaseFragment implements
        OnClickListener {

    private static final int DELIVERY_METHOD = 1;
    private static final int PAYPAL_METHOD = 2;
    private static final int BANKING_METHOD = 3;

    private View view;
    private ImageView btnBack;
    private EditText txtBuyerName, txtBuyerEmail, txtBuyerPhone, txtBuyerAddress, txtBuyerCity, txtBuyerZipCode;
    private EditText txtReceiverName, txtReceiverEmail, txtReceiverPhone, txtReceiverAddress, txtReceiverCity, txtReceiverZipCode;
    private CheckBox ckbSameAsBuyerInfo;
    private AutoBgButton btnContinue;
    private TextView lblTotal;
    private Spinner spnPaymentMethods;
    private PaymentMethodAdapter paymentMethodAdapter;
    private int selectedPaymentMethod = 0;
    private String data;
    private double total=0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_details_order, container, false);
        initUI(view);
        initControls();
        return view;
    }

    private void initUI(View view) {
        btnContinue = (AutoBgButton) view.findViewById(R.id.btnContinue);
        btnBack = (ImageView) view.findViewById(R.id.btnBack);
        lblTotal = (TextView) view.findViewById(R.id.lblTotal);
        spnPaymentMethods = (Spinner) view.findViewById(R.id.spnPaymentMethods);

        txtBuyerName = (EditText) view.findViewById(R.id.txtBuyerName);
        txtBuyerEmail = (EditText) view.findViewById(R.id.txtBuyerEmail);
        txtBuyerPhone = (EditText) view.findViewById(R.id.txtBuyerPhone);
        txtBuyerAddress = (EditText) view.findViewById(R.id.txtBuyerAddress);
        txtBuyerCity = (EditText) view.findViewById(R.id.txtBuyerCity);
        txtBuyerZipCode = (EditText) view.findViewById(R.id.txtBuyerZipcode);
        //Receiver info
        txtReceiverName = (EditText) view.findViewById(R.id.txtReceiverName);
        txtReceiverEmail = (EditText) view.findViewById(R.id.txtReceiverEmail);
        txtReceiverPhone = (EditText) view.findViewById(R.id.txtReceiverPhone);
        txtReceiverAddress = (EditText) view.findViewById(R.id.txtReceiverAddress);
        txtReceiverCity = (EditText) view.findViewById(R.id.txtReceiverCity);
        txtReceiverZipCode = (EditText) view.findViewById(R.id.txtReceiverZipcode);

        ckbSameAsBuyerInfo = (CheckBox) view.findViewById(R.id.ckbSameAsBuyerInfo);

    }

    private void initControls() {
        btnBack.setOnClickListener(this);
        btnContinue.setOnClickListener(this);
        ckbSameAsBuyerInfo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                updateReceiverInformation(b);
            }
        });
        spnPaymentMethods.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                selectedPaymentMethod = position + 1;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }
        });
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        // TODO Auto-generated method stub
        super.onHiddenChanged(hidden);
        if (!hidden) {
            refreshContent();
        }
    }

    private void setPaymentMethodData() {
        paymentMethodAdapter = new PaymentMethodAdapter(getCurrentActivity(),
                android.R.layout.simple_spinner_item, getResources()
                .getStringArray(R.array.arr_payment_methods));
        spnPaymentMethods.setAdapter(paymentMethodAdapter);
        spnPaymentMethods.setSelection(0);
    }

    private void showTotalPrice() {
        total = (double) 0;
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
        lblTotal.setText(getString(R.string.currency) + String.format("%.1f", total));
    }

    @Override
    public void refreshContent() {
        txtBuyerName.setText(GlobalValue.myAccount.getFull_name());
        txtBuyerEmail.setText(GlobalValue.myAccount.getEmail());
        txtBuyerPhone.setText(GlobalValue.myAccount.getPhone());
        txtBuyerAddress.setText(GlobalValue.myAccount.getAddress());
        txtBuyerCity.setText(GlobalValue.myAccount.getCity());
        txtBuyerZipCode.setText(GlobalValue.myAccount.getZipCode());
        //show total Price
        showTotalPrice();
        //set payment method data
        setPaymentMethodData();
        ckbSameAsBuyerInfo.setSelected(false);
        //updateReceiverInformation(ckbSameAsBuyerInfo.isSelected());
    }

    private void updateReceiverInformation(boolean selectedSameAsBuyer) {
        if (selectedSameAsBuyer) {
            txtReceiverName.setText(GlobalValue.myAccount.getFull_name());
            txtReceiverEmail.setText(GlobalValue.myAccount.getEmail());
            txtReceiverPhone.setText(GlobalValue.myAccount.getPhone());
            txtReceiverAddress.setText(GlobalValue.myAccount.getAddress());
            txtReceiverCity.setText(GlobalValue.myAccount.getCity());
            txtReceiverZipCode.setText(GlobalValue.myAccount.getZipCode());
        } else {
            txtReceiverName.setText("");
            txtReceiverEmail.setText("");
            txtReceiverPhone.setText("");
            txtReceiverAddress.setText("");
            txtReceiverCity.setText("");
            txtReceiverZipCode.setText("");
        }
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        if (v == btnContinue) {
            onClickOrder();

        } else if (v == btnBack) {
            // hidden keyboard :
            hiddenKeyboard();
            MainCartActivity activity = (MainCartActivity) getCurrentActivity();
            activity.onBackPressed();
        }
    }

    private void onClickOrder() {
        // TODO Auto-generated method stub
        //check receiverName
        String receiverName = txtReceiverName.getText().toString();
        if (StringUtility.isEmpty(receiverName)) {
            Toast.makeText(getCurrentActivity(),
                    getString(R.string.message_empty_delivery_information), Toast.LENGTH_SHORT)
                    .show();
            return;
        }
        //check receiver email
        String receiverEmail = txtReceiverEmail.getText().toString();
        if (StringUtility.isEmpty(receiverEmail)) {
            Toast.makeText(getCurrentActivity(),
                    getString(R.string.message_empty_delivery_information), Toast.LENGTH_SHORT)
                    .show();
            return;
        }
        //check receiver phone
        String receiverPhone = txtReceiverPhone.getText().toString();
        if (StringUtility.isEmpty(receiverPhone)) {
            Toast.makeText(getCurrentActivity(),
                    getString(R.string.message_empty_delivery_information), Toast.LENGTH_SHORT)
                    .show();
            return;
        }
        //check receiver address
        String receiverAddress = txtReceiverAddress.getText().toString();
        if (StringUtility.isEmpty(receiverAddress)) {
            Toast.makeText(getCurrentActivity(),
                    getString(R.string.message_empty_delivery_information), Toast.LENGTH_SHORT)
                    .show();
            return;
        }
        //check receiver city
        String receiverCity = txtReceiverCity.getText().toString();
        if (StringUtility.isEmpty(receiverCity)) {
            Toast.makeText(getCurrentActivity(),
                    getString(R.string.message_empty_delivery_information), Toast.LENGTH_SHORT)
                    .show();
            return;
        }
        //check receiver zip code
        String receiverZipCode = txtReceiverZipCode.getText().toString();
        if (StringUtility.isEmpty(receiverZipCode)) {
            Toast.makeText(getCurrentActivity(),
                    getString(R.string.message_empty_delivery_information), Toast.LENGTH_SHORT)
                    .show();
            return;
        }

        data = createOfferJson(GlobalValue.arrMyMenuShop, receiverName, receiverEmail, receiverPhone, receiverAddress, receiverCity, receiverZipCode);
        Log.e("Huy -test ", "Log json data =" + data);
        if (NetworkUtil.checkNetworkAvailable(getCurrentActivity())) {
            if (selectedPaymentMethod != PAYPAL_METHOD) {
                sendListOrder(data, selectedPaymentMethod);
            } else {
                requestPaypalPayment(total,"paypal payment","USD");
            }
        } else {
            Toast.makeText(getCurrentActivity(),
                    R.string.message_network_is_unavailable,
                    Toast.LENGTH_LONG).show();
        }

    }

    public void sendOrderInfoWithPaypalMethod()
    {
        sendListOrder(data,PAYPAL_METHOD);
    }

    private void sendListOrder(String data, final int paymentMethod) {
        ModelManager.sendListOrder(getCurrentActivity(), data, paymentMethod,
                true, new ModelManagerListener() {

                    @Override
                    public void onSuccess(Object object) {
                        // TODO Auto-generated method stub
                        String strJson = (String) object;
                        try {
                            JSONObject json = new JSONObject(strJson);
                            if (json.getString(WebServiceConfig.KEY_STATUS)
                                    .equals(WebServiceConfig.KEY_STATUS_SUCCESS)) {
                                Log.e("DeliveryInfoFragment", "sendListOrder success");
                                if (paymentMethod != BANKING_METHOD) {
                                    CustomToast.showCustomAlert(
                                            getCurrentActivity(),
                                            getCurrentActivity().getString(
                                                    R.string.message_success),
                                            Toast.LENGTH_SHORT);
                                } else {
                                    showBankInfo(getCurrentActivity().getString(
                                            R.string.message_success));
                                }
                                // clear list
                                GlobalValue.arrMyMenuShop.clear();
                                // go back list shop cart
                                (getCurrentActivity())
                                        .onBackPressed();

                            } else {
                                CustomToast.showCustomAlert(
                                        getCurrentActivity(),
                                        getCurrentActivity().getString(
                                                R.string.message_order_false),
                                        Toast.LENGTH_SHORT);
                            }
                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(VolleyError error) {
                        // TODO Auto-generated method stub
                        Toast.makeText(self, ErrorNetworkHandler.processError(error), Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void showBankInfo(String message) {
        DialogUtility.alert(getCurrentActivity(), "Banking Information :", Html.fromHtml(message).toString());
    }

    // paypal
    private void requestPaypalPayment(double value, String content,
                                      String currency) {

        PayPalPayment thingToBuy = new PayPalPayment(new BigDecimal(value),
                currency, content);

        Intent intent = new Intent(getCurrentActivity(), PaymentActivity.class);

        intent.putExtra(PaymentActivity.EXTRA_PAYPAL_ENVIRONMENT,
                PaymentActivity.ENVIRONMENT_NO_NETWORK);
        intent.putExtra(PaymentActivity.EXTRA_CLIENT_ID,
                Constant.PAYPAL_CLIENT_APP_ID);
        intent.putExtra(PaymentActivity.EXTRA_RECEIVER_EMAIL,
                Constant.PAYPAL_RECEIVE_EMAIL_ID);
        intent.putExtra(PaymentActivity.EXTRA_PAYER_ID,
                GlobalValue.myAccount.getId() + "");
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, thingToBuy);

        startActivityForResult(intent, 0);

    }

    private String createOfferJson(ArrayList<Shop> arrShops, String receiverName, String receiverEmail, String receiverPhone, String receiverAddress, String receiverCity, String receiverZipcode) {
        JSONObject jsonOrder = new JSONObject();
        JSONArray jsonFoods = new JSONArray();
        JSONObject jsonFood = null;
        Double price = null;

        try {
            for (Shop shop : arrShops) {

                for (Menu menu : shop.getArrOrderFoods()) {

                    price = menu.getPrice()
                            - (menu.getPrice() * menu.getPercentDiscount() / 100);

                    jsonFood = new JSONObject();
                    jsonFood.put(WebServiceConfig.KEY_ORDER_SHOP,
                            menu.getShopId());
                    jsonFood.put(WebServiceConfig.KEY_ORDER_FOOD, menu.getId());
                    jsonFood.put(WebServiceConfig.KEY_ORDER_NUMBER_FOOD,
                            menu.getOrderNumber());
                    jsonFood.put(WebServiceConfig.KEY_ORDER_PRICE_FOOD,
                            round(price, 2));
                    jsonFoods.put(jsonFood);

                }
            }

            jsonOrder.put(WebServiceConfig.KEY_ORDER_ACCOUT_ID,
                    GlobalValue.myAccount.getId());
            jsonOrder.put("billing_name", receiverName);
            jsonOrder.put("billing_email", receiverEmail);
            jsonOrder.put("billing_phone", receiverPhone);
            jsonOrder.put("billing_address", receiverAddress);
            jsonOrder.put("billing_city", receiverCity);
            jsonOrder.put("billing_zip_code", receiverZipcode);
            jsonOrder.put(WebServiceConfig.KEY_ORDER_ITEM, jsonFoods);

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return jsonOrder.toString();
    }

    public static double round(double number, int digit) {
        if (digit > 0) {
            int temp = 1, i;
            for (i = 0; i < digit; i++)
                temp = temp * 10;
            number = number * temp;
            number = Math.round(number);
            number = number / temp;
            return number;
        } else
            return 0.0;
    }

}
