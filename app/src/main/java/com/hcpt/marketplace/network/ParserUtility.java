package com.hcpt.marketplace.network;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.hcpt.marketplace.config.WebServiceConfig;
import com.hcpt.marketplace.object.Account;
import com.hcpt.marketplace.object.Banner;
import com.hcpt.marketplace.object.Category;
import com.hcpt.marketplace.object.City;
import com.hcpt.marketplace.object.Comment;
import com.hcpt.marketplace.object.Menu;
import com.hcpt.marketplace.object.Offer;
import com.hcpt.marketplace.object.OpenHour;
import com.hcpt.marketplace.object.Order;
import com.hcpt.marketplace.object.OrderGroup;
import com.hcpt.marketplace.object.Setting;
import com.hcpt.marketplace.object.SettingPreferences;
import com.hcpt.marketplace.object.Shop;
import com.hcpt.marketplace.object.ShopOrder;

public class ParserUtility {

    // account
    public static Account parseAccount(String json) {
        Account account = null;
        try {
            JSONObject object = new JSONObject(json);
            if (object.getString(WebServiceConfig.KEY_STATUS).equalsIgnoreCase(
                    WebServiceConfig.KEY_STATUS_SUCCESS)) {
                JSONObject item = object
                        .getJSONObject(WebServiceConfig.KEY_DATA);
                account = new Account();
                account.setId(item.getString(WebServiceConfig.KEY_ACCOUNT_ID));
                account.setUserName(item
                        .getString(WebServiceConfig.KEY_ACCOUNT_USER_NAME));
                account.setEmail(item
                        .getString(WebServiceConfig.KEY_ACCOUNT_EMAIL));
                account.setFull_name(item
                        .getString(WebServiceConfig.KEY_ACCOUNT_FULL_NAME));
                account.setPhone(item
                        .getString(WebServiceConfig.KEY_ACCOUNT_PHONE));
                account.setAddress(item
                        .getString(WebServiceConfig.KEY_ACCOUNT_ADDRESS));
                account.setCity(item
                        .getString(WebServiceConfig.KEY_ACCOUNT_CITY));
                account.setZipCode(item
                        .getString(WebServiceConfig.KEY_ACCOUNT_ZIP_CODE));
                account.setRole(item
                        .getString(WebServiceConfig.KEY_ACCOUNT_ROLE));
                account.setRedirectLink(item.getString(WebServiceConfig.KEY_ACCOUNT_REDIRECT_LINK));
                account.setPreferences(new SettingPreferences(item
                        .getString(WebServiceConfig.KEY_ACCOUNT_PREFERENCES)));
                account.setNumberOfFavouriteProducts(item.getInt(WebServiceConfig.KEY_ACCOUNT_NUMBER_FAVOURITE_PRODUCTS));
                account.setNumberOfFavouriteShops(item.getInt(WebServiceConfig.KEY_ACCOUNT_NUMBER_FAVOURITE_SHOPS));
                account.setNumberOfOrders(item.getInt(WebServiceConfig.KEY_ACCOUNT_NUMBER_ORDERS));
            }

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return account;
    }

    // offer
    public static ArrayList<Offer> parseListOffer(String json) {
        ArrayList<Offer> arrOffers = new ArrayList<Offer>();
        try {
            JSONObject object = new JSONObject(json);
            JSONArray arrjson = object.getJSONArray(WebServiceConfig.KEY_DATA);
            JSONObject item;

            Offer offer;
            for (int i = 0; i < arrjson.length(); i++) {
                item = arrjson.getJSONObject(i);

                offer = new Offer();
                offer.setOfferId(item.getInt(WebServiceConfig.KEY_OFFER_ID));
                offer.setShopId(item.getInt(WebServiceConfig.KEY_SHOP_ID));
                offer.setDescription(item
                        .getString(WebServiceConfig.KEY_OFFER_DESCRIPTION));
                offer.setImage(item.getString(WebServiceConfig.KEY_OFFER_IMAGE));
                offer.setEndDate(item
                        .getString(WebServiceConfig.KEY_OFFER_END_DATE));
                offer.setEndTime(item
                        .getString(WebServiceConfig.KEY_OFFER_END_TIME));

                arrOffers.add(offer);
            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return arrOffers;
    }

    public static ArrayList<OrderGroup> parseListOrderGroup(String json) {
        ArrayList<OrderGroup> arrOffers = new ArrayList<OrderGroup>();
        try {
            JSONObject object = new JSONObject(json);

            JSONObject item;
            OrderGroup oder;

            if (!object.isNull(WebServiceConfig.KEY_DATA)) {
                JSONArray arrjson = object
                        .getJSONArray(WebServiceConfig.KEY_DATA);

                for (int i = 0; i < arrjson.length(); i++) {
                    item = arrjson.getJSONObject(i);
                    oder = new OrderGroup();
                    if (!item.isNull("code"))
                        oder.setId(item.getString("code"));
                    if (!item.isNull("total"))
                        oder.setPrice(item.getDouble("total"));
                    if (!item.isNull("time"))
                        oder.setDatetime(item.getString("time"));
                    if (!item.isNull("status"))
                        oder.setStatus(item.getString("status"));

                    arrOffers.add(oder);
                }
            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return arrOffers;
    }

    public static ArrayList<ShopOrder> parseListShopOrder(String json) {
        ArrayList<ShopOrder> arrOffers = new ArrayList<ShopOrder>();
        try {
            JSONObject object = new JSONObject(json);
            JSONObject item, jsonFood;
            JSONArray arrjson, arrFoodJson;
            ShopOrder shopOrder = null;
            ArrayList<Menu> arrFoods;
            Menu food;

            if (!object.isNull(WebServiceConfig.KEY_DATA)) {
                arrjson = object.getJSONArray(WebServiceConfig.KEY_DATA);

                for (int i = 0; i < arrjson.length(); i++) {
                    // parse shop order
                    item = arrjson.getJSONObject(i);
                    shopOrder = new ShopOrder();
                    if (!item.isNull("order_id"))
                        shopOrder.setOrderId(item.getString("order_id"));
                    if (!item.isNull("account_id"))
                        shopOrder.setAccountId(item.getString("account_id"));
                    if (!item.isNull("shop_name"))
                        shopOrder.setShopName(item.getString("shop_name"));
                    if (!item.isNull("name_category"))
                        shopOrder.setShopCategories(item.getString("name_category"));
                    if (!item.isNull("shop_address"))
                        shopOrder.setShopAddress(item.getString("shop_address"));
                    if (!item.isNull("shop_thumbnail"))
                        shopOrder
                                .setShopImage(item.getString("shop_thumbnail"));
                    if (!item.isNull("order_places"))
                        shopOrder.setOrderAddress(item
                                .getString("order_places"));
                    if (!item.isNull("total"))
                        shopOrder.setTotalPrice(item.getDouble("total"));
                    if (!item.isNull("tax"))
                        shopOrder.setVAT(item.getDouble("tax"));
                    if (!item.isNull("shipping"))
                        shopOrder.setShipping(item.getDouble("shipping"));
                    if (!item.isNull("grandTotal"))
                        shopOrder.setGrandTotal(item.getDouble("grandTotal"));
                    if (!item.isNull("order_time"))
                        shopOrder.setOrderTime(item.getString("order_time"));
                    if (!item.isNull("orderStatus"))
                        shopOrder.setOrderStatus(item.getInt("orderStatus"));
                    if (!item.isNull("paymentStatus"))
                        shopOrder
                                .setPaymentStatus(item.getInt("paymentStatus"));
                    if (!item.isNull("paymentMethod"))
                        shopOrder
                                .setPaymentMethod(item.getInt("paymentMethod"));

                    // parse list food
                    arrFoodJson = item.getJSONArray("foods");
                    arrFoods = new ArrayList<Menu>();
                    int size = arrFoodJson.length();
                    for (int j = 0; j < size; j++) {
                        jsonFood = arrFoodJson.getJSONObject(j);
                        food = new Menu();
                        food.setId(jsonFood.getInt("food_id"));
                        food.setOrderNumber(jsonFood.getInt("number"));
                        food.setPrice(jsonFood.getDouble("price"));
                        food.setCode(jsonFood.getString("food_code"));
                        food.setName(jsonFood.getString("food_name"));
                        food.setCategoryId(jsonFood.getInt("food_menu"));
                        food.setImage(jsonFood.getString("food_thumbnail"));
                        food.setDescription(jsonFood
                                .getString("food_description"));
                        arrFoods.add(food);
                    }
                    shopOrder.setArrFoods(arrFoods);
                    arrOffers.add(shopOrder);
                }

            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            Log.e("huy-log", "list-shop-order : " + e.getMessage());
            e.printStackTrace();

        }

        return arrOffers;
    }

    public static ArrayList<Order> parseListOrder(String json) {
        ArrayList<Order> arrOffers = new ArrayList<Order>();
        try {
            JSONObject object = new JSONObject(json);
            JSONArray arrjson = object.getJSONArray(WebServiceConfig.KEY_DATA);
            JSONObject item;

            Order oder;
            for (int i = 0; i < arrjson.length(); i++) {
                item = arrjson.getJSONObject(i);

                oder = new Order();
                oder.setPlacesO(item.getString("order_places"));
                oder.setStatusO(item.getString("orderStatus"));
                oder.setO_id(item.getString("order_id"));
                oder.setSttO(item.getString("count"));
                oder.setTimeO(item.getString("created"));
                oder.setTotalO(item.getString("total"));

                arrOffers.add(oder);
            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return arrOffers;
    }

    public static ArrayList<Order> parseListDetailOrder(String json) {
        ArrayList<Order> arrOffers = new ArrayList<Order>();
        try {
            JSONObject object = new JSONObject(json);
            JSONArray arrjson = object.getJSONArray(WebServiceConfig.KEY_DATA);
            JSONObject item;

            Order oder;
            for (int i = 0; i < arrjson.length(); i++) {
                item = arrjson.getJSONObject(i);

                oder = new Order();
                oder.setName(item.getString("food_name"));
                oder.setImage(item.getString("food_thumbnail"));
                oder.setNumber(item.getString("number"));
                oder.setPrice(item.getString("price"));
                oder.setPromotion(item.getString("promotion"));

                arrOffers.add(oder);
            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return arrOffers;
    }

    public static Offer parseOffer(String json) {
        Offer offer = new Offer();
        try {
            JSONObject object = new JSONObject(json);
            JSONObject item = object.getJSONObject(WebServiceConfig.KEY_DATA);

            offer.setOfferId(item.getInt(WebServiceConfig.KEY_OFFER_ID));
            offer.setShopId(item.getInt(WebServiceConfig.KEY_SHOP_ID));
            offer.setDescription(item
                    .getString(WebServiceConfig.KEY_OFFER_DESCRIPTION));
            offer.setImage(item.getString(WebServiceConfig.KEY_OFFER_IMAGE));
            offer.setEndDate(item
                    .getString(WebServiceConfig.KEY_OFFER_END_DATE));
            offer.setEndTime(item
                    .getString(WebServiceConfig.KEY_OFFER_END_TIME));

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return offer;
    }

    // shop
    public static ArrayList<Shop> getListShop(String json) {
        ArrayList<Shop> arrShop = new ArrayList<Shop>();
        try {
            JSONObject object = new JSONObject(json);
            Shop shop;
            JSONObject item;
            JSONArray arr = object.getJSONArray(WebServiceConfig.KEY_DATA);
            if (arr.length() > 0) {
                for (int i = 0; i < arr.length(); i++) {
                    item = arr.getJSONObject(i);
                    shop = new Shop();
                    shop.setShopId(item.getInt(WebServiceConfig.KEY_SHOP_ID));
                    shop.setShopName(item
                            .getString(WebServiceConfig.KEY_SHOP_NAME));
                    shop.setAddress(item
                            .getString(WebServiceConfig.KEY_SHOP_ADDRESS));
                    shop.setPhone(item
                            .getString(WebServiceConfig.KEY_SHOP_PHONE));
                    shop.setImage(item
                            .getString(WebServiceConfig.KEY_SHOP_IMAGE));
                    shop.setDescription(item
                            .getString(WebServiceConfig.KEY_SHOP_DESCRIPTION));
                    shop.setLatitude(item
                            .getDouble(WebServiceConfig.KEY_SHOP_LATITUDE));
                    shop.setLongitude(item
                            .getDouble(WebServiceConfig.KEY_SHOP_LONGTITUDE));
                    shop.setCityId(item.getInt(WebServiceConfig.KEY_SHOP_CITY));
                    shop.setIsVerified(item.getString("isVerified"));
                    shop.setIsFeatured(item.getString("isFeatured"));
                    shop.setFacebook(item.getString("facebook"));
                    shop.setTwitter(item.getString("twitter"));
                    shop.setEmail(item.getString("email"));
                    shop.setLive_chat(item.getString("live_chat"));
                    //shop categories
                    if (!item.isNull("name_category"))
                        shop.setCategory(item.getString("name_category"));

                    try {
                        shop.setRateNumber(Integer.parseInt(item
                                .getString(WebServiceConfig.KEY_SHOP_RATE_TIMES)));
                    } catch (Exception ex) {
                        shop.setRateNumber(0);
                    }
                    try {
                        shop.setRateValue(Float.parseFloat(item
                                .getString(WebServiceConfig.KEY_SHOP_RATE)));
                    } catch (Exception ex) {
                        shop.setRateValue(0);
                    }

                    JSONObject arrOpenhour = item
                            .getJSONObject(WebServiceConfig.KEY_SHOP_OPEN_HOUR);
                    OpenHour openhour = new OpenHour();
                    openhour.setOpenAM(arrOpenhour
                            .getString(WebServiceConfig.KEY_SHOP_OPEN_HOUR_OPEN_AM));
                    openhour.setOpenPM(arrOpenhour
                            .getString(WebServiceConfig.KEY_SHOP_OPEN_HOUR_OPEN_PM));
                    openhour.setCloseAM(arrOpenhour
                            .getString(WebServiceConfig.KEY_SHOP_OPEN_HOUR_CLOSE_AM));
                    openhour.setClosePM(arrOpenhour
                            .getString(WebServiceConfig.KEY_SHOP_OPEN_HOUR_CLOSE_PM));
                    openhour.setShopId(shop.getShopId());

                    shop.setOpenHour(openhour);

                    //is open or close
                    if (!item.isNull("is_open"))
                        shop.setIsOpen(item.getString("is_open").equals("1"));

                    // get VAT
                    if (!item.isNull("shop_vat"))
                        shop.setShopVAT(item.getDouble("shop_vat"));

                    // get delivery cost
                    if (!item.isNull("shop_transport_fee")) {

                        JSONObject jsonShip = item
                                .getJSONObject("shop_transport_fee");
                        shop.setMinPriceForDelivery(jsonShip
                                .getDouble("minimum"));
                        shop.setDeliveryPrice(jsonShip
                                .getDouble("shipping_fee"));
                    }

                    arrShop.add(shop);

                }
            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

        }
        return arrShop;
    }

    public static ArrayList<Shop> getListFavouriteShop(String json) {
        ArrayList<Shop> arrShop = new ArrayList<Shop>();
        try {
            JSONObject object = new JSONObject(json);
            Shop shop;
            JSONObject item;
            JSONArray arr = object.getJSONArray("data_shops");
            if (arr.length() > 0) {
                for (int i = 0; i < arr.length(); i++) {
                    item = arr.getJSONObject(i);
                    shop = new Shop();
                    shop.setShopId(item.getInt(WebServiceConfig.KEY_SHOP_ID));
                    shop.setShopName(item
                            .getString(WebServiceConfig.KEY_SHOP_NAME));
                    shop.setAddress(item
                            .getString(WebServiceConfig.KEY_SHOP_ADDRESS));
                    shop.setPhone(item
                            .getString(WebServiceConfig.KEY_SHOP_PHONE));
                    shop.setImage(item
                            .getString(WebServiceConfig.KEY_SHOP_IMAGE));
                    shop.setDescription(item
                            .getString(WebServiceConfig.KEY_SHOP_DESCRIPTION));
                    shop.setLatitude(item
                            .getDouble(WebServiceConfig.KEY_SHOP_LATITUDE));
                    shop.setLongitude(item
                            .getDouble(WebServiceConfig.KEY_SHOP_LONGTITUDE));
                    shop.setCityId(item.getInt(WebServiceConfig.KEY_SHOP_CITY));
                    shop.setIsVerified(item.getString("isVerified"));
                    shop.setIsFeatured(item.getString("isFeatured"));
                    shop.setFacebook(item.getString("facebook"));
                    shop.setTwitter(item.getString("twitter"));
                    shop.setEmail(item.getString("email"));
                    shop.setLive_chat(item.getString("live_chat"));
                    //shop categories
                    if (!item.isNull("name_category"))
                        shop.setCategory(item.getString("name_category"));

                    try {
                        shop.setRateNumber(Integer.parseInt(item
                                .getString(WebServiceConfig.KEY_SHOP_RATE_TIMES)));
                    } catch (Exception ex) {
                        shop.setRateNumber(0);
                    }
                    try {
                        shop.setRateValue(Float.parseFloat(item
                                .getString(WebServiceConfig.KEY_SHOP_RATE)));
                    } catch (Exception ex) {
                        shop.setRateValue(0);
                    }

                    JSONObject arrOpenhour = item
                            .getJSONObject(WebServiceConfig.KEY_SHOP_OPEN_HOUR);
                    OpenHour openhour = new OpenHour();
                    openhour.setOpenAM(arrOpenhour
                            .getString(WebServiceConfig.KEY_SHOP_OPEN_HOUR_OPEN_AM));
                    openhour.setOpenPM(arrOpenhour
                            .getString(WebServiceConfig.KEY_SHOP_OPEN_HOUR_OPEN_PM));
                    openhour.setCloseAM(arrOpenhour
                            .getString(WebServiceConfig.KEY_SHOP_OPEN_HOUR_CLOSE_AM));
                    openhour.setClosePM(arrOpenhour
                            .getString(WebServiceConfig.KEY_SHOP_OPEN_HOUR_CLOSE_PM));
                    openhour.setShopId(shop.getShopId());

                    shop.setOpenHour(openhour);

                    //is open or close
                    if (!item.isNull("is_open"))
                        shop.setIsOpen(item.getString("is_open").equals("1"));

                    // get VAT
                    if (!item.isNull("shop_vat"))
                        shop.setShopVAT(item.getDouble("shop_vat"));

                    // get delivery cost
                    if (!item.isNull("shop_transport_fee")) {

                        JSONObject jsonShip = item
                                .getJSONObject("shop_transport_fee");
                        shop.setMinPriceForDelivery(jsonShip
                                .getDouble("minimum"));
                        shop.setDeliveryPrice(jsonShip
                                .getDouble("shipping_fee"));
                    }

                    arrShop.add(shop);

                }
            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

        }
        return arrShop;
    }


    public static Shop parseShop(String json) {
        JSONObject object;
        Shop shop = null;
        try {
            object = new JSONObject(json);
            JSONObject item = object.getJSONObject(WebServiceConfig.KEY_DATA);
            shop = new Shop();
            shop.setShopId(item.getInt(WebServiceConfig.KEY_SHOP_ID));
            shop.setShopName(item.getString(WebServiceConfig.KEY_SHOP_NAME));
            shop.setAddress(item.getString(WebServiceConfig.KEY_SHOP_ADDRESS));
            shop.setPhone(item.getString(WebServiceConfig.KEY_SHOP_PHONE));
            shop.setImage(item.getString("shop_image"));
            shop.setThumbnail(item.getString(WebServiceConfig.KEY_SHOP_IMAGE));
            shop.setDescription(item
                    .getString(WebServiceConfig.KEY_SHOP_DESCRIPTION));
            shop.setLatitude(item.getDouble(WebServiceConfig.KEY_SHOP_LATITUDE));
            shop.setLongitude(item
                    .getDouble(WebServiceConfig.KEY_SHOP_LONGTITUDE));
            shop.setCityId(item.getInt(WebServiceConfig.KEY_SHOP_CITY));
            shop.setFacebook(item.getString("facebook"));
            shop.setTwitter(item.getString("twitter"));
            shop.setEmail(item.getString("email"));
            shop.setLive_chat(item.getString("live_chat"));
            shop.setIsVerified(item.getString("isVerified"));
            shop.setIsFeatured(item.getString("isFeatured"));
            //shop categories
            if (!item.isNull("name_category"))
                shop.setCategory(item.getString("name_category"));

            try {
                shop.setRateNumber(item
                        .getInt(WebServiceConfig.KEY_SHOP_RATE_TIMES));
            } catch (Exception ex) {
                shop.setRateNumber(0);
            }
            try {
                shop.setRateValue(item.getInt(WebServiceConfig.KEY_SHOP_RATE));
            } catch (Exception ex) {
                shop.setRateValue(0);
            }

            // get all banner
            JSONArray bannerJS = item
                    .getJSONArray(WebServiceConfig.KEY_SHOP_BANNERS);
            ArrayList<Banner> arrBanner = new ArrayList<Banner>();
            Banner banner;
            JSONObject itemBannerJs;
            for (int i = 0; i < bannerJS.length(); i++) {
                itemBannerJs = bannerJS.getJSONObject(i);
                banner = new Banner();
                banner.setId(itemBannerJs
                        .getInt(WebServiceConfig.KEY_BANNER_ID));
                banner.setName(itemBannerJs
                        .getString(WebServiceConfig.KEY_BANNER_NAME));
                banner.setImage(itemBannerJs
                        .getString(WebServiceConfig.KEY_BANNER_IMAGE));

                banner.setShopId(shop.getShopId());

                arrBanner.add(banner);
            }

            shop.setArrBanner(arrBanner);

            //open-close time
            ArrayList<OpenHour> arrOpenHour = new ArrayList<OpenHour>();
            JSONArray openhourJS = item.getJSONArray("openHour");
            OpenHour openhour;
            JSONObject itemOpenhourJs;
            for (int i = 0; i < openhourJS.length(); i++) {
                itemOpenhourJs = openhourJS.getJSONObject(i);
                openhour = new OpenHour();
                openhour.setDateId(itemOpenhourJs
                        .getInt(WebServiceConfig.KEY_SHOP_OPEN_HOUR_DATE_ID));
                openhour.setDateName(itemOpenhourJs
                        .getString(WebServiceConfig.KEY_SHOP_OPEN_HOUR_DATE_NAME));
                openhour.setOpenAM(itemOpenhourJs
                        .getString(WebServiceConfig.KEY_SHOP_OPEN_HOUR_OPEN_AM));
                openhour.setOpenPM(itemOpenhourJs
                        .getString(WebServiceConfig.KEY_SHOP_OPEN_HOUR_OPEN_PM));
                openhour.setCloseAM(itemOpenhourJs
                        .getString(WebServiceConfig.KEY_SHOP_OPEN_HOUR_CLOSE_AM));
                openhour.setClosePM(itemOpenhourJs
                        .getString(WebServiceConfig.KEY_SHOP_OPEN_HOUR_CLOSE_PM));

                arrOpenHour.add(openhour);
            }
            shop.setArrOpenHour(arrOpenHour);

            //is open or close
            if (!item.isNull("is_open"))
                shop.setIsOpen(item.getInt("is_open") == 1);

            // get VAT
            if (!item.isNull("shop_vat"))
                shop.setShopVAT(item.getDouble("shop_vat"));

            // get delivery cost
            if (!item.isNull("shop_transport_fee")) {

                JSONObject jsonShip = item.getJSONObject("shop_transport_fee");
                shop.setMinPriceForDelivery(jsonShip.getDouble("minimum"));
                shop.setDeliveryPrice(jsonShip.getDouble("shipping_fee"));
            }

            //get number of product
            if (!item.isNull("number_product"))
                shop.setNumberItems(item.getInt("number_product"));

            //is favorite or not
            if (!item.isNull("isFavourite"))
                shop.setFavourite(item.getString("isFavourite").equals("1"));

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

        }
        return shop;
    }

    // open hour

    public static ArrayList<OpenHour> parseListOpenHour(String json) {
        // get all openhour
        JSONObject item;
        ArrayList<OpenHour> arrOpenHour = new ArrayList<OpenHour>();
        try {
            item = new JSONObject(json);
            JSONArray openhourJS = item.getJSONArray(WebServiceConfig.KEY_DATA);
            OpenHour openhour;
            JSONObject itemOpenhourJs;
            for (int i = 0; i < openhourJS.length(); i++) {
                itemOpenhourJs = openhourJS.getJSONObject(i);
                openhour = new OpenHour();
                openhour.setDateId(itemOpenhourJs
                        .getInt(WebServiceConfig.KEY_SHOP_OPEN_HOUR_DATE_ID));
                openhour.setDateName(itemOpenhourJs
                        .getString(WebServiceConfig.KEY_SHOP_OPEN_HOUR_DATE_NAME));
                openhour.setOpenAM(itemOpenhourJs
                        .getString(WebServiceConfig.KEY_SHOP_OPEN_HOUR_OPEN_AM));
                openhour.setOpenPM(itemOpenhourJs
                        .getString(WebServiceConfig.KEY_SHOP_OPEN_HOUR_OPEN_PM));
                openhour.setCloseAM(itemOpenhourJs
                        .getString(WebServiceConfig.KEY_SHOP_OPEN_HOUR_CLOSE_AM));
                openhour.setClosePM(itemOpenhourJs
                        .getString(WebServiceConfig.KEY_SHOP_OPEN_HOUR_CLOSE_PM));

                arrOpenHour.add(openhour);
            }

        } catch (Exception e) {
            // TODO: handle exception
        }
        return arrOpenHour;
    }

    // city

    public static ArrayList<City> parseListCity(String json) {
        ArrayList<City> arrCities = new ArrayList<City>();
        try {
            JSONObject object = new JSONObject(json);
            JSONArray arrjson = object.getJSONArray(WebServiceConfig.KEY_DATA);
            JSONObject item;
            City city;
            for (int i = 0; i < arrjson.length(); i++) {
                item = arrjson.getJSONObject(i);
                city = new City();
                city.setId(item.getString(WebServiceConfig.KEY_CITY_ID));
                city.setPostCode(item
                        .getString(WebServiceConfig.KEY_CITY_POST_CODE));
                city.setName(item.getString(WebServiceConfig.KEY_CITY_NAME));

                arrCities.add(city);
            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return arrCities;
    }

    // categories

    public static ArrayList<Category> parseListCategories(String json) {
        ArrayList<Category> arrCategories = new ArrayList<Category>();
        try {
            JSONObject object = new JSONObject(json);
            JSONArray arrjson = object.getJSONArray(WebServiceConfig.KEY_DATA);
            JSONObject item;
            Category category;
            for (int i = 0; i < arrjson.length(); i++) {
                item = arrjson.getJSONObject(i);
                category = new Category();
                category.setId(item.getString(WebServiceConfig.KEY_CATEGORY_ID));
                category.setName(item
                        .getString(WebServiceConfig.KEY_CATEGORY_NAME));
                category.setDescription(item
                        .getString(WebServiceConfig.KEY_CATEGORY_DESCRIPTION));
                category.setImage(item
                        .getString(WebServiceConfig.KEY_CATEGORY_IMAGE));

                arrCategories.add(category);
            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return arrCategories;
    }

    public static Category parseCategory(String json) {
        Category category = new Category();
        try {
            JSONObject object = new JSONObject(json);
            JSONObject item = object.getJSONObject(WebServiceConfig.KEY_DATA);

            category.setId(item.getString(WebServiceConfig.KEY_CATEGORY_ID));
            category.setName(item.getString(WebServiceConfig.KEY_CATEGORY_NAME));
            category.setDescription(item
                    .getString(WebServiceConfig.KEY_CATEGORY_DESCRIPTION));
            category.setImage(item
                    .getString(WebServiceConfig.KEY_CATEGORY_IMAGE));

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return category;
    }

    public static ArrayList<Menu> parseListFood(String json) {
        ArrayList<Menu> arrFood = new ArrayList<Menu>();
        try {
            JSONObject object = new JSONObject(json);
            JSONArray arrjson = object.getJSONArray(WebServiceConfig.KEY_DATA);
            JSONObject item;
            Menu food;
            for (int i = 0; i < arrjson.length(); i++) {
                item = arrjson.getJSONObject(i);
                food = new Menu();
                food.setId(item.getInt(WebServiceConfig.KEY_FOOD_ID));
                food.setCode(item.getString(WebServiceConfig.KEY_FOOD_CODE));
                food.setName(item.getString(WebServiceConfig.KEY_FOOD_NAME));
                food.setDescription(item
                        .getString(WebServiceConfig.KEY_FOOD_DESCRIPTION));
                food.setImage(item.getString(WebServiceConfig.KEY_FOOD_IMAGE));
                food.setPrice(item.getDouble(WebServiceConfig.KEY_FOOD_PRICE));
                food.setPercentDiscount(item
                        .getDouble(WebServiceConfig.KEY_FOOD_PERCENT_DISCOUNT));
                food.setShopId(item.getInt(WebServiceConfig.KEY_FOOD_SHOP));
                food.setCategoryId(item.getInt(WebServiceConfig.KEY_FOOD_MENU));
                try {
                    food.setRateValue(Float.parseFloat(item
                            .getString(WebServiceConfig.KEY_FOOD_RATE)));
                } catch (Exception ex) {
                    food.setRateValue(0);
                }
                try {
                    food.setRateNumber(Integer.parseInt(item
                            .getString(WebServiceConfig.KEY_FOOD_RATE_COUNT)));
                } catch (Exception ex) {
                    food.setRateNumber(0);
                }

                arrFood.add(food);
            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            Log.e("error", "Parsed list food false");
        }

        return arrFood;
    }

    public static ArrayList<Menu> parseListFavoriteFood(String json) {
        ArrayList<Menu> arrFood = new ArrayList<Menu>();
        try {
            JSONObject object = new JSONObject(json);
            JSONArray arrjson = object.getJSONArray("data_products");
            JSONObject item;
            Menu food;
            Shop shop;
            for (int i = 0; i < arrjson.length(); i++) {
                item = arrjson.getJSONObject(i);
                food = new Menu();
                food.setId(item.getInt(WebServiceConfig.KEY_FOOD_ID));
                food.setCode(item.getString(WebServiceConfig.KEY_FOOD_CODE));
                food.setName(item.getString(WebServiceConfig.KEY_FOOD_NAME));
                food.setDescription(item
                        .getString(WebServiceConfig.KEY_FOOD_DESCRIPTION));
                food.setImage(item.getString(WebServiceConfig.KEY_FOOD_IMAGE));
                food.setPrice(item.getDouble(WebServiceConfig.KEY_FOOD_PRICE));
                food.setPercentDiscount(item
                        .getDouble(WebServiceConfig.KEY_FOOD_PERCENT_DISCOUNT));
                food.setShopId(item.getInt(WebServiceConfig.KEY_FOOD_SHOP));
                food.setCategoryId(item.getInt(WebServiceConfig.KEY_FOOD_MENU));
                food.setCategory(item.getString("category"));

                try {
                    food.setRateValue(Float.parseFloat(item
                            .getString(WebServiceConfig.KEY_FOOD_RATE)));
                } catch (Exception ex) {
                    food.setRateValue(0);
                }
                try {
                    food.setRateNumber(Integer.parseInt(item
                            .getString(WebServiceConfig.KEY_FOOD_RATE_COUNT)));
                } catch (Exception ex) {
                    food.setRateNumber(0);
                }

                //add shop
                shop = new Shop();
                shop.setShopId(item.getInt(WebServiceConfig.KEY_FOOD_SHOP));
                shop.setShopName(item.getString("shop_name"));
                shop.setAddress(item.getString("shop_address"));
                shop.setPhone(item.getString("shop_phone"));
                shop.setLatitude(item.getDouble("shop_lat"));
                shop.setLongitude(item.getDouble("shop_long"));
                shop.setImage(item.getString("shop_image_url"));
                shop.setThumbnail(item.getString("shop_image_thumbnail_url"));

                food.setShop(shop);
                arrFood.add(food);
            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            Log.e("error", "Parsed list food false");
        }

        return arrFood;
    }


    public static ArrayList<Menu> parseListFoodInSearch(String json) {
        ArrayList<Menu> arrFood = new ArrayList<Menu>();
        try {
            JSONObject object = new JSONObject(json);
            JSONArray arrjson = object.getJSONArray(WebServiceConfig.KEY_DATA);
            JSONObject item;
            Menu food;
            Shop shop;
            for (int i = 0; i < arrjson.length(); i++) {
                item = arrjson.getJSONObject(i);
                food = new Menu();
                food.setId(item.getInt(WebServiceConfig.KEY_FOOD_ID));
                food.setCode(item.getString(WebServiceConfig.KEY_FOOD_CODE));
                food.setName(item.getString(WebServiceConfig.KEY_FOOD_NAME));
                food.setDescription(item
                        .getString(WebServiceConfig.KEY_FOOD_DESCRIPTION));
                food.setImage(item.getString(WebServiceConfig.KEY_FOOD_IMAGE));
                food.setPrice(item.getDouble(WebServiceConfig.KEY_FOOD_PRICE));
                food.setPercentDiscount(item
                        .getDouble(WebServiceConfig.KEY_FOOD_PERCENT_DISCOUNT));
                food.setShopId(item.getInt(WebServiceConfig.KEY_FOOD_SHOP));
                food.setCategoryId(item.getInt(WebServiceConfig.KEY_FOOD_MENU));
                food.setCategory(item.getString("category"));

                try {
                    food.setRateValue(Float.parseFloat(item
                            .getString(WebServiceConfig.KEY_FOOD_RATE)));
                } catch (Exception ex) {
                    food.setRateValue(0);
                }
                try {
                    food.setRateNumber(Integer.parseInt(item
                            .getString(WebServiceConfig.KEY_FOOD_RATE_COUNT)));
                } catch (Exception ex) {
                    food.setRateNumber(0);
                }

                //add shop
                shop = new Shop();
                shop.setShopId(item.getInt(WebServiceConfig.KEY_FOOD_SHOP));
                shop.setShopName(item.getString("shop_name"));
                shop.setAddress(item.getString("shop_address"));
                shop.setPhone(item.getString("shop_phone"));
                shop.setLatitude(item.getDouble("shop_lat"));
                shop.setLongitude(item.getDouble("shop_long"));
                shop.setImage(item.getString("shop_image_url"));
                shop.setThumbnail(item.getString("shop_image_thumbnail_url"));

                food.setShop(shop);
                arrFood.add(food);
            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            Log.e("error", "Parsed list food false");
        }

        return arrFood;
    }

    public static Menu parseFood(String json) {
        Menu food = null;
        try {
            JSONObject object = new JSONObject(json);
            JSONObject item = object.getJSONObject(WebServiceConfig.KEY_DATA);
            food = new Menu();
            food.setId(item.getInt(WebServiceConfig.KEY_FOOD_ID));
            food.setCode(item.getString(WebServiceConfig.KEY_FOOD_CODE));
            food.setName(item.getString(WebServiceConfig.KEY_FOOD_NAME));
            food.setDescription(item
                    .getString(WebServiceConfig.KEY_FOOD_DESCRIPTION));
            food.setImage(item.getString(WebServiceConfig.KEY_FOOD_IMAGE));
            food.setPercentDiscount(item
                    .getDouble(WebServiceConfig.KEY_FOOD_PERCENT_DISCOUNT));
            food.setPrice(item.getDouble(WebServiceConfig.KEY_FOOD_PRICE));
            food.setShopId(item.getInt(WebServiceConfig.KEY_FOOD_SHOP));
            food.setCategoryId(item.getInt(WebServiceConfig.KEY_FOOD_MENU));
            food.setCategory(item.getString("name_category"));
            try {
                food.setRateValue(Float.parseFloat(item
                        .getString(WebServiceConfig.KEY_FOOD_RATE)));
            } catch (Exception ex) {
                food.setRateValue(0);
            }
            try {
                food.setRateNumber(Integer.parseInt(item
                        .getString(WebServiceConfig.KEY_FOOD_RATE_COUNT)));
            } catch (Exception ex) {
                food.setRateNumber(0);
            }

            // get all gallery
            JSONArray bannerJS = item
                    .getJSONArray("gallery");
            ArrayList<Banner> arrBanner = new ArrayList<Banner>();
            Banner banner;
            JSONObject itemBannerJs;
            for (int i = 0; i < bannerJS.length(); i++) {
                itemBannerJs = bannerJS.getJSONObject(i);
                banner = new Banner();
                banner.setId(itemBannerJs
                        .getInt("id"));
                banner.setImage(itemBannerJs
                        .getString("image"));
                arrBanner.add(banner);
            }
            food.setArrGalleries(arrBanner);

            // add shop
            Shop shop = new Shop();
            shop.setShopId(item.getInt(WebServiceConfig.KEY_SHOP_ID));
            shop.setShopName(item.getString(WebServiceConfig.KEY_SHOP_NAME));
            shop.setAddress(item.getString(WebServiceConfig.KEY_SHOP_ADDRESS));
            shop.setPhone(item.getString(WebServiceConfig.KEY_SHOP_PHONE));
            shop.setImage(item.getString(WebServiceConfig.KEY_SHOP_IMAGE));
            shop.setThumbnail(item.getString("shop_thumbnail"));
            shop.setDescription(item
                    .getString(WebServiceConfig.KEY_SHOP_DESCRIPTION));
            shop.setLatitude(item.getDouble(WebServiceConfig.KEY_SHOP_LATITUDE));
            shop.setLongitude(item
                    .getDouble(WebServiceConfig.KEY_SHOP_LONGTITUDE));
            shop.setCityId(item.getInt(WebServiceConfig.KEY_SHOP_CITY));
            shop.setFacebook(item.getString("shop_facebook"));
            shop.setTwitter(item.getString("shop_twitter"));
            shop.setEmail(item.getString("shop_email"));
            shop.setLive_chat(item.getString("shop_live_chat"));
            shop.setIsVerified(item.getString("shop_isVerified"));
            shop.setIsFeatured(item.getString("shop_isFeatured"));
            try {
                shop.setRateNumber(item
                        .getInt("shop_rate_times"));
            } catch (Exception ex) {
                shop.setRateNumber(0);
            }
            try {
                shop.setRateValue(item.getInt("shop_rate"));
            } catch (Exception ex) {
                shop.setRateValue(0);
            }
            //add open hours :
            ArrayList<OpenHour> arrOpenHour = new ArrayList<OpenHour>();
            JSONArray openhourJS = item.getJSONArray("openHour");
            OpenHour openhour;
            JSONObject itemOpenhourJs;
            for (int i = 0; i < openhourJS.length(); i++) {
                itemOpenhourJs = openhourJS.getJSONObject(i);
                openhour = new OpenHour();
                openhour.setDateId(itemOpenhourJs
                        .getInt(WebServiceConfig.KEY_SHOP_OPEN_HOUR_DATE_ID));
                openhour.setDateName(itemOpenhourJs
                        .getString(WebServiceConfig.KEY_SHOP_OPEN_HOUR_DATE_NAME));
                openhour.setOpenAM(itemOpenhourJs
                        .getString(WebServiceConfig.KEY_SHOP_OPEN_HOUR_OPEN_AM));
                openhour.setOpenPM(itemOpenhourJs
                        .getString(WebServiceConfig.KEY_SHOP_OPEN_HOUR_OPEN_PM));
                openhour.setCloseAM(itemOpenhourJs
                        .getString(WebServiceConfig.KEY_SHOP_OPEN_HOUR_CLOSE_AM));
                openhour.setClosePM(itemOpenhourJs
                        .getString(WebServiceConfig.KEY_SHOP_OPEN_HOUR_CLOSE_PM));

                arrOpenHour.add(openhour);
            }

            shop.setArrOpenHour(arrOpenHour);

            //is favorite or not
            if (!item.isNull("isFavourite"))
                food.setIsFavourite(item.getInt("isFavourite") == 1);

            food.setShop(shop);

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return food;
    }

    public static Setting parseSetting(String json) {
        Setting setting = new Setting();

        try {

            JSONObject item;
            JSONObject jsonObject = new JSONObject(json);
            JSONArray itemService = jsonObject
                    .getJSONArray(WebServiceConfig.KEY_DATA);
            for (int i = 0; i < itemService.length(); i++) {
                item = itemService.getJSONObject(i);
                setting.setVat(item.getString(WebServiceConfig.KEY_VAT));
                setting.setTransportFee(item
                        .getString(WebServiceConfig.KEY_SHIP));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return setting;

    }

    public static ArrayList<Comment> parseComments(String json) {
        ArrayList<Comment> arrComment = new ArrayList<Comment>();

        if (json != null) {
            try {
                JSONObject jsonObject = new JSONObject(json);
                if (jsonObject.getString(WebServiceConfig.KEY_STATUS)
                        .equalsIgnoreCase(WebServiceConfig.KEY_STATUS_SUCCESS)) {
                    JSONArray jsonArr = jsonObject
                            .getJSONArray(WebServiceConfig.KEY_DATA);
                    if (jsonArr != null && jsonArr.length() > 0) {
                        for (int i = 0; i < jsonArr.length(); i++) {
                            JSONObject obj = jsonArr.getJSONObject(i);

                            Comment cmt = new Comment();
                            cmt.setContent(obj.getString("content"));
                            cmt.setCreatedDate(obj.getString("created"));
                            cmt.setRateValue(Float.parseFloat(obj
                                    .getString("rate")));
                            cmt.setUserID(obj.getString("account_id"));

                            arrComment.add(cmt);
                        }
                    }
                }
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return arrComment;
    }

    public static String convertAccountToJsonString(Account acc) {
        String str = "";
        if (acc.getId().isEmpty()) {
            JSONObject json = new JSONObject();
            try {
                json.put("id", acc.getId());
                json.put("userName", acc.getUserName());
                json.put("fullName", acc.getFull_name());
                json.put("email", acc.getEmail());
                json.put("address", acc.getAddress());
                json.put("city", acc.getCity());
                json.put("zipcode", acc.getZipCode());
                json.put("phone", acc.getPhone());
                json.put("pass", acc.getPassword());
                json.put("role", acc.getRole());
                json.put("redirectLink", acc.getRedirectLink());
                json.put("loginType", acc.getType());
                json.put("preferences", acc.getPreferences().getJsonString());

                str = json.toString();

            } catch (JSONException e) {
                str = "";
            }
        }
        return str;
    }

    public static Account convertJsonStringtoAccount(String json) {
        Account account = null;
        if (json.isEmpty()) {
            try {
                account = new Account();
                JSONObject jsonObj = new JSONObject(json);
                account.setId(jsonObj.getString("id"));
                account.setUserName(jsonObj.getString("userName"));
                account.setFull_name(jsonObj.getString("fullName"));
                account.setEmail(jsonObj.getString("email"));
                account.setAddress(jsonObj.getString("address"));
                account.setCity(jsonObj.getString("city"));
                account.setZipCode(jsonObj.getString("zipcode"));
                account.setPhone(jsonObj.getString("phone"));
                account.setPassword(jsonObj.getString("pass"));
                account.setRole(jsonObj.getString("role"));
                account.setRedirectLink(jsonObj.getString("redirectLink"));
                account.setType(jsonObj.getString("loginType"));
                //account preferences
                SettingPreferences preferences = new SettingPreferences(jsonObj.getString("preferences"));
                preferences.setUserId(account.getId());
                account.setPreferences(preferences);

            } catch (JSONException e) {
                account = null;
            }
        }
        return account;
    }

    public static LatLng parseDefaultLocation(String json) {
        try {
            JSONObject object = new JSONObject(json);
            return new LatLng(object.getJSONObject("data").getDouble("lat"), object.getJSONObject("data").getDouble("lon"));
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return new LatLng(0.0000000, 0.0000000);
        }
    }

    public static boolean isSuccess(String json) {
        try {
            JSONObject object = new JSONObject(json);
            if (object.getString(WebServiceConfig.KEY_STATUS).equals(WebServiceConfig.KEY_STATUS_SUCCESS)) {
                return true;
            } else {
                return false;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }
    }
}
