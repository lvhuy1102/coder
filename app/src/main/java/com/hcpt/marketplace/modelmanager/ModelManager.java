package com.hcpt.marketplace.modelmanager;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;

import com.android.volley.VolleyError;
import com.hcpt.marketplace.config.WebServiceConfig;
import com.hcpt.marketplace.network.HttpError;
import com.hcpt.marketplace.network.HttpGet;
import com.hcpt.marketplace.network.HttpListener;
import com.hcpt.marketplace.network.HttpPost;
import com.hcpt.marketplace.network.ParameterFactory;
import com.hcpt.marketplace.object.Account;
import com.hcpt.marketplace.object.SettingPreferences;
import com.hcpt.marketplace.util.Utils;

public class ModelManager {
    private static String TAG = "ModelManager";

    public static void getListShop(final Context context, double longitude,
                                   double latitude, boolean isProgress,
                                   final ModelManagerListener listener) {
        final String url = WebServiceConfig.FULL_PATH
                + WebServiceConfig.ACTION_GET_ALL_SHOP;

        String mlong = longitude + "";
        String mlat = latitude + "";
        Map<String, String> params = (Map<String, String>) ParameterFactory
                .createLongLatParams(context, mlong, mlat);

        new HttpGet(context, url, params, isProgress, new HttpListener() {
            @Override
            public void onHttpRespones(Object respones) {
                if (respones != null) {
                    listener.onSuccess(respones.toString());
                } else {
                    listener.onError(null);
                }
            }
        }, new HttpError() {
            @Override
            public void onHttpError(VolleyError volleyError) {
                listener.onError(volleyError);
            }
        });
    }

    public static void getListShopBySearch(final Context context,
                                           String keyword, String categoryId, String cityId, int page,
                                           String open, String distance, String sortBy, String sortType,
                                           String lat, String lon, boolean isProgress,
                                           final ModelManagerListener listener) {
        final String url = WebServiceConfig.FULL_PATH
                + WebServiceConfig.ACTION_SEARCH_SHOP;

        Map<String, String> params = new HashMap<>();
        params.put("keyword", keyword);
        params.put("c_id", cityId);
        params.put("m_id", categoryId);
        params.put("page", page + "");
        params.put("open", open);
        params.put("distance", distance);
        params.put("sort_name", sortBy);
        params.put("sort_type", sortType);
        params.put("lat", lat);
        params.put("long", lon);
        params.put("now", Utils.getCurrentTimestamp());

        new HttpGet(context, url, params, isProgress, new HttpListener() {
            @Override
            public void onHttpRespones(Object respones) {
                if (respones != null) {
                    listener.onSuccess(respones.toString());
                } else {
                    listener.onError(null);
                }
            }
        }, new HttpError() {
            @Override
            public void onHttpError(VolleyError volleyError) {
                listener.onError(volleyError);
            }
        });
    }

    public static void getListFoodBySearch(final Context context,
                                           String keyword, String categoryId, String cityId, int page,
                                           String open, String distance, String sortBy, String sortType,
                                           String lat, String lon, boolean isProgress,
                                           final ModelManagerListener listener) {
        final String url = WebServiceConfig.FULL_PATH
                + WebServiceConfig.ACTION_SEARCH_FOOD;

        Map<String, String> params = new HashMap<>();
        params.put("keyword", keyword);
        params.put("c_id", cityId);
        params.put("m_id", categoryId);
        params.put("page", page + "");
        params.put("open", open);
        params.put("distance", distance);
        params.put("sort_name", sortBy);
        params.put("sort_type", sortType);
        params.put("lat", lat);
        params.put("long", lon);
        params.put("now", Utils.getCurrentTimestamp());

        new HttpGet(context, url, params, isProgress, new HttpListener() {
            @Override
            public void onHttpRespones(Object respones) {
                if (respones != null) {
                    listener.onSuccess(respones.toString());
                } else {
                    listener.onError(null);
                }
            }
        }, new HttpError() {
            @Override
            public void onHttpError(VolleyError volleyError) {
                listener.onError(volleyError);
            }
        });
    }


    public static void getListOrder(final Context context, String id, int page,
                                    boolean isProgress, final ModelManagerListener listener) {
        final String url = WebServiceConfig.FULL_PATH
                + WebServiceConfig.ACTION_GET_HISTORY_ORDERS;
        Map<String, String> params = new HashMap<>();
        params.put("account", id);
        params.put("page", page + "");
        new HttpGet(context, url, params, isProgress, new HttpListener() {
            @Override
            public void onHttpRespones(Object respones) {
                if (respones != null) {
                    listener.onSuccess(respones.toString());
                } else {
                    listener.onError(null);
                }
            }
        }, new HttpError() {
            @Override
            public void onHttpError(VolleyError volleyError) {
                listener.onError(volleyError);
            }
        });
    }

    public static void getListDetailOrder(final Context context, String id,
                                          boolean isProgress, final ModelManagerListener listener) {
        final String url = WebServiceConfig.FULL_PATH
                + WebServiceConfig.ACTION_GET_ORDER_DETAILS;
        Map<String, String> params = new HashMap<>();
        params.put("group_code", id);
        new HttpGet(context, url, params, isProgress, new HttpListener() {
            @Override
            public void onHttpRespones(Object respones) {
                if (respones != null) {
                    listener.onSuccess(respones.toString());
                } else {
                    listener.onError(null);
                }
            }
        }, new HttpError() {
            @Override
            public void onHttpError(VolleyError volleyError) {
                listener.onError(volleyError);
            }
        });
    }

    public static void getListCity(final Context context, boolean isProgress,
                                   final ModelManagerListener listener) {
        final String url = WebServiceConfig.FULL_PATH
                + WebServiceConfig.ACTION_GET_ALL_CITY;
        Map<String, String> params = new HashMap<>();
        new HttpGet(context, url, params, isProgress, new HttpListener() {
            @Override
            public void onHttpRespones(Object respones) {
                if (respones != null) {
                    listener.onSuccess(respones.toString());
                } else {
                    listener.onError(null);
                }
            }
        }, new HttpError() {
            @Override
            public void onHttpError(VolleyError volleyError) {
                listener.onError(volleyError);
            }
        });
    }

    public static void getListCategory(final Context context,
                                       boolean isProgress, final ModelManagerListener listener) {
        final String url = WebServiceConfig.FULL_PATH
                + WebServiceConfig.ACTION_GET_ALL_CATEGORIES;
        Map<String, String> params = new HashMap<>();
        new HttpGet(context, url, params, isProgress, new HttpListener() {
            @Override
            public void onHttpRespones(Object respones) {
                if (respones != null) {
                    listener.onSuccess(respones.toString());
                } else {
                    listener.onError(null);
                }
            }
        }, new HttpError() {
            @Override
            public void onHttpError(VolleyError volleyError) {
                listener.onError(volleyError);
            }
        });
    }

    public static void getShopById(final Context context, int shopId,
                                   boolean isProgress, final ModelManagerListener listener) {
        final String url = WebServiceConfig.FULL_PATH
                + WebServiceConfig.ACTION_GET_SHOP_BY_ID;
        String shop = shopId + "";
        Map<String, String> params = (Map<String, String>) ParameterFactory
                .createShopIdParams(context, shop);
        new HttpGet(context, url, params, isProgress, new HttpListener() {
            @Override
            public void onHttpRespones(Object respones) {
                if (respones != null) {
                    listener.onSuccess(respones.toString());
                } else {
                    listener.onError(null);
                }
            }
        }, new HttpError() {
            @Override
            public void onHttpError(VolleyError volleyError) {
                listener.onError(volleyError);
            }
        });
    }

    public static void getOpenHourByShop(final Context context, int shopId,
                                         boolean isProgress, final ModelManagerListener listener) {
        final String url = WebServiceConfig.FULL_PATH
                + WebServiceConfig.ACTION_GET_OPEN_HOUR_BY_SHOP;
        String shop = shopId + "";
        Map<String, String> params = (Map<String, String>) ParameterFactory
                .createShopIdParams(context, shop);
        new HttpGet(context, url, params, isProgress, new HttpListener() {
            @Override
            public void onHttpRespones(Object respones) {
                if (respones != null) {
                    listener.onSuccess(respones.toString());
                } else {
                    listener.onError(null);
                }
            }
        }, new HttpError() {
            @Override
            public void onHttpError(VolleyError volleyError) {
                listener.onError(volleyError);
            }
        });
    }


    public static void getListCategoryByShop(final Context context, int shopId,
                                             boolean isProgress, final ModelManagerListener listener) {
        final String url = WebServiceConfig.FULL_PATH
                + WebServiceConfig.ACTION_GET_CATEGORY_BY_SHOP;
        String shop = shopId + "";
        Map<String, String> params = (Map<String, String>) ParameterFactory
                .createShopIdParams(context, shop);
        new HttpGet(context, url, params, isProgress, new HttpListener() {
            @Override
            public void onHttpRespones(Object respones) {
                if (respones != null) {
                    listener.onSuccess(respones.toString());
                } else {
                    listener.onError(null);
                }
            }
        }, new HttpError() {
            @Override
            public void onHttpError(VolleyError volleyError) {
                listener.onError(volleyError);
            }
        });
    }

    public static void getOfferById(final Context context, int offerId,
                                    boolean isProgress, final ModelManagerListener listener) {
        final String url = WebServiceConfig.FULL_PATH
                + WebServiceConfig.ACTION_GET_OFFER_BY_ID;
        String offer = offerId + "";
        Map<String, String> params = (Map<String, String>) ParameterFactory
                .createOfferIdParams(context, offer);
        new HttpGet(context, url, params, isProgress, new HttpListener() {
            @Override
            public void onHttpRespones(Object respones) {
                if (respones != null) {
                    listener.onSuccess(respones.toString());
                } else {
                    listener.onError(null);
                }
            }
        }, new HttpError() {
            @Override
            public void onHttpError(VolleyError volleyError) {
                listener.onError(volleyError);
            }
        });
    }

    public static void getListFoodByShopAndCategory(final Context context,
                                                    int shopId, String categoryId, boolean isProgress,
                                                    final ModelManagerListener listener) {
        final String url = WebServiceConfig.FULL_PATH
                + WebServiceConfig.ACTION_GET_FOOD_BY_SHOP_AND_CATEGORY;
        String shop = shopId + "";
        String category = categoryId + "";
        Map<String, String> params = (Map<String, String>) ParameterFactory
                .createShopIdAndCategoryIdParams(context, shop, category);
        new HttpGet(context, url, params, isProgress, new HttpListener() {
            @Override
            public void onHttpRespones(Object respones) {
                if (respones != null) {
                    listener.onSuccess(respones.toString());
                } else {
                    listener.onError(null);
                }
            }
        }, new HttpError() {
            @Override
            public void onHttpError(VolleyError volleyError) {
                listener.onError(volleyError);
            }
        });
    }

    public static void getListFoodOfDay(final Context context,
                                        double longitude, double latitude, boolean isProgress,
                                        final ModelManagerListener listener) {
        final String url = WebServiceConfig.FULL_PATH
                + WebServiceConfig.ACTION_GET_LIST_FOOD_OF_DAY;
        String mlong = longitude + "";
        String mlat = latitude + "";
        Map<String, String> params = (Map<String, String>) ParameterFactory
                .createLongLatParams(context, mlong, mlat);
        new HttpGet(context, url, params, isProgress, new HttpListener() {
            @Override
            public void onHttpRespones(Object respones) {
                if (respones != null) {
                    listener.onSuccess(respones.toString());
                } else {
                    listener.onError(null);
                }
            }
        }, new HttpError() {
            @Override
            public void onHttpError(VolleyError volleyError) {
                listener.onError(volleyError);
            }
        });
    }

    public static void getListFoodByPromotion(final Context context, int offer,
                                              boolean isProgress, final ModelManagerListener listener) {
        final String url = WebServiceConfig.FULL_PATH
                + WebServiceConfig.ACTION_GET_LIST_FOOD_BY_PROMOTION;
        String mpromotion = offer + "";
        Map<String, String> params = (Map<String, String>) ParameterFactory
                .createOfferIdParams(context, mpromotion);
        new HttpGet(context, url, params, isProgress, new HttpListener() {
            @Override
            public void onHttpRespones(Object respones) {
                if (respones != null) {
                    listener.onSuccess(respones.toString());
                } else {
                    listener.onError(null);
                }
            }
        }, new HttpError() {
            @Override
            public void onHttpError(VolleyError volleyError) {
                listener.onError(volleyError);
            }
        });
    }

    public static void getFoodById(final Context context, String foodId,
                                   boolean isProgress, final ModelManagerListener listener) {
        final String url = WebServiceConfig.FULL_PATH
                + WebServiceConfig.ACTION_GET_FOOD_BY_ID;
        Map<String, String> params = (Map<String, String>) ParameterFactory
                .createFoodIdParams(context, foodId);
        new HttpGet(context, url, params, isProgress, new HttpListener() {
            @Override
            public void onHttpRespones(Object respones) {
                if (respones != null) {
                    listener.onSuccess(respones.toString());
                } else {
                    listener.onError(null);
                }
            }
        }, new HttpError() {
            @Override
            public void onHttpError(VolleyError volleyError) {
                listener.onError(volleyError);
            }
        });
    }

    public static void getUserInforById(final Context context, String id,
                                        boolean isProgress, final ModelManagerListener listener) {
        final String url = WebServiceConfig.FULL_PATH
                + WebServiceConfig.ACTION_GET_USER_INFOR;
        Map<String, String> params = ParameterFactory
                .createUserIdParams(context, id);
        new HttpGet(context, url, params, isProgress, new HttpListener() {
            @Override
            public void onHttpRespones(Object respones) {
                if (respones != null) {
                    listener.onSuccess(respones.toString());
                } else {
                    listener.onError(null);
                }
            }
        }, new HttpError() {
            @Override
            public void onHttpError(VolleyError volleyError) {
                listener.onError(volleyError);
            }
        });
    }

    public static void login(final Context context, String username,
                             String pass, boolean isProgress, final ModelManagerListener listener) {
        final String url = WebServiceConfig.FULL_PATH
                + WebServiceConfig.ACTION_LOGIN;
        Map<String, String> params = (Map<String, String>) ParameterFactory
                .createLoginParams(context, username, pass);
        new HttpGet(context, url, params, isProgress, new HttpListener() {
            @Override
            public void onHttpRespones(Object respones) {
                if (respones != null) {
                    listener.onSuccess(respones.toString());
                } else {
                    listener.onError(null);
                }
            }
        }, new HttpError() {
            @Override
            public void onHttpError(VolleyError volleyError) {
                listener.onError(volleyError);
            }
        });
    }

    public static void register(final Context context, String data,
                                boolean isProgress, final ModelManagerListener listener) {
        final String url = WebServiceConfig.FULL_PATH
                + WebServiceConfig.ACTION_REGISTER;
        Map<String, String> params = (Map<String, String>) ParameterFactory
                .createDataRegisterParams(context, data);
        new HttpGet(context, url, params, isProgress, new HttpListener() {
            @Override
            public void onHttpRespones(Object respones) {
                if (respones != null) {
                    listener.onSuccess(respones.toString());
                } else {
                    listener.onError(null);
                }
            }
        }, new HttpError() {
            @Override
            public void onHttpError(VolleyError volleyError) {
                listener.onError(volleyError);
            }
        });
    }

    public static void putFeedBack(final Context context, String id,
                                   String title, String des, String type, boolean isProgress,
                                   final ModelManagerListener listener) {
        final String url = WebServiceConfig.FULL_PATH
                + WebServiceConfig.ACTION_FEEDBACK;
        Map<String, String> params = (Map<String, String>) ParameterFactory
                .putFeedBackParams(context, id, title, des, type);
        new HttpGet(context, url, params, isProgress, new HttpListener() {
            @Override
            public void onHttpRespones(Object respones) {
                if (respones != null) {
                    listener.onSuccess(respones.toString());
                } else {
                    listener.onError(null);
                }
            }
        }, new HttpError() {
            @Override
            public void onHttpError(VolleyError volleyError) {
                listener.onError(volleyError);
            }
        });
    }

    public static void updateProfile(final Context context, Account acc,
                                     boolean isProgress, final ModelManagerListener listener) {
        final String url = WebServiceConfig.FULL_PATH
                + WebServiceConfig.ACTION_UPDATEINFOR;
        Map<String, String> params = ParameterFactory
                .updateInforUserParams(acc.getId(), acc.getPassword(), acc.getEmail(), acc.getFull_name(), acc.getPhone(),
                        acc.getAddress(), acc.getCity(), acc.getZipCode());
        new HttpGet(context, url, params, isProgress, new HttpListener() {
            @Override
            public void onHttpRespones(Object respones) {
                if (respones != null) {
                    listener.onSuccess(respones.toString());
                } else {
                    listener.onError(null);
                }
            }
        }, new HttpError() {
            @Override
            public void onHttpError(VolleyError volleyError) {
                listener.onError(volleyError);
            }
        });
    }

    public static void registerShopOwner(final Context context, String id,
                                         boolean isProgress, final ModelManagerListener listener) {
        final String url = WebServiceConfig.FULL_PATH
                + WebServiceConfig.ACTION_REGISTER_SHOP_OWNER;
        Map<String, String> params = new HashMap<>();
        params.put("user_id", id);
        new HttpGet(context, url, params, isProgress, new HttpListener() {
            @Override
            public void onHttpRespones(Object respones) {
                if (respones != null) {
                    listener.onSuccess(respones.toString());
                } else {
                    listener.onError(null);
                }
            }
        }, new HttpError() {
            @Override
            public void onHttpError(VolleyError volleyError) {
                listener.onError(volleyError);
            }
        });
    }


    public static void sendListOrder(final Context context, String data,
                                     int paymentMethod, boolean isProgress,
                                     final ModelManagerListener listener) {
        final String url = WebServiceConfig.FULL_PATH
                + WebServiceConfig.ACTION_SEND_LIST_ORDER;
        Map<String, String> params = (Map<String, String>) ParameterFactory
                .createDataOrderParams(context, data, paymentMethod);
        new HttpGet(context, url, params, isProgress, new HttpListener() {
            @Override
            public void onHttpRespones(Object respones) {
                if (respones != null) {
                    listener.onSuccess(respones.toString());
                } else {
                    listener.onError(null);
                }
            }
        }, new HttpError() {
            @Override
            public void onHttpError(VolleyError volleyError) {
                listener.onError(volleyError);
            }
        });
    }

    public static void getOrderHistory(final Context context, String accountId,
                                       boolean isProgress, final ModelManagerListener listener) {
        final String url = WebServiceConfig.FULL_PATH
                + WebServiceConfig.ACTION_GET_HISTORY_ORDERS;
        Map<String, String> params = new HashMap<>();
        params.put("account", accountId);
        new HttpGet(context, url, params, isProgress, new HttpListener() {
            @Override
            public void onHttpRespones(Object respones) {
                if (respones != null) {
                    listener.onSuccess(respones.toString());
                } else {
                    listener.onError(null);
                }
            }
        }, new HttpError() {
            @Override
            public void onHttpError(VolleyError volleyError) {
                listener.onError(volleyError);
            }
        });
    }

    public static void getOrderDetails(final Context context,
                                       String orderGroupId, boolean isProgress,
                                       final ModelManagerListener listener) {
        final String url = WebServiceConfig.FULL_PATH
                + WebServiceConfig.ACTION_GET_ORDER_DETAILS;
        Map<String, String> params = new HashMap<>();
        params.put("group_code", orderGroupId);
        new HttpGet(context, url, params, isProgress, new HttpListener() {
            @Override
            public void onHttpRespones(Object respones) {
                if (respones != null) {
                    listener.onSuccess(respones.toString());
                } else {
                    listener.onError(null);
                }
            }
        }, new HttpError() {
            @Override
            public void onHttpError(VolleyError volleyError) {
                listener.onError(volleyError);
            }
        });
    }


    public static void getFoodsComments(final Context context, String id,
                                        int page, boolean isProgress, final ModelManagerListener listener) {
        final String url = WebServiceConfig.FULL_PATH
                + WebServiceConfig.ACTION_GET_COMMENTS;

        Map<String, String> params = new HashMap<>();
        params.put("objectType", "food");
        params.put("objectId", id);
        params.put("page", page + "");
        new HttpGet(context, url, params, isProgress, new HttpListener() {
            @Override
            public void onHttpRespones(Object respones) {
                if (respones != null) {
                    listener.onSuccess(respones.toString());
                } else {
                    listener.onError(null);
                }
            }
        }, new HttpError() {
            @Override
            public void onHttpError(VolleyError volleyError) {
                listener.onError(volleyError);
            }
        });
    }

    public static void getShopsComments(final Context context, String id,
                                        int page, boolean isProgress, final ModelManagerListener listener) {
        final String url = WebServiceConfig.FULL_PATH
                + WebServiceConfig.ACTION_GET_COMMENTS;

        Map<String, String> params = new HashMap<>();
        params.put("objectType", "shop");
        params.put("objectId", id);
        params.put("page", page + "");
        new HttpGet(context, url, params, isProgress, new HttpListener() {
            @Override
            public void onHttpRespones(Object respones) {
                if (respones != null) {
                    listener.onSuccess(respones.toString());
                } else {
                    listener.onError(null);
                }
            }
        }, new HttpError() {
            @Override
            public void onHttpError(VolleyError volleyError) {
                listener.onError(volleyError);
            }
        });
    }

    public static void addFoodReview(final Context context, String shopId,
                                     String foodId, String rate, String userId, String content, boolean isProgress,
                                     final ModelManagerListener listener) {
        final String url = WebServiceConfig.FULL_PATH
                + WebServiceConfig.ACTION_ADD_FOOD_REVIEW;

        Map<String, String> params = new HashMap<>();
        params.put("s_id", shopId);
        params.put("food_id", foodId);
        params.put("rate", rate);
        params.put("account_id", userId);
        params.put("title", "dummy");
        params.put("content", content);
        new HttpGet(context, url, params, isProgress, new HttpListener() {
            @Override
            public void onHttpRespones(Object respones) {
                if (respones != null) {
                    listener.onSuccess(respones.toString());
                } else {
                    listener.onError(null);
                }
            }
        }, new HttpError() {
            @Override
            public void onHttpError(VolleyError volleyError) {
                listener.onError(volleyError);
            }
        });
    }

    public static void getDefaultLocation(final Context context, boolean isProgress,
                                          final ModelManagerListener listener) {
        final String url = WebServiceConfig.FULL_PATH
                + WebServiceConfig.ACTION_GET_DEFAULT_LOCATION;

        Map<String, String> params = new HashMap<>();

        new HttpGet(context, url, params, isProgress, new HttpListener() {
            @Override
            public void onHttpRespones(Object respones) {
                if (respones != null) {
                    listener.onSuccess(respones.toString());
                } else {
                    listener.onError(null);
                }
            }
        }, new HttpError() {
            @Override
            public void onHttpError(VolleyError volleyError) {
                listener.onError(volleyError);
            }
        });
    }

    public static void getFavourite(final Context context, String userID, boolean isProgress,
                                    final ModelManagerListener listener) {
        final String url = WebServiceConfig.FULL_PATH
                + WebServiceConfig.ACTION_SHOW_FAVOURITE;

        Map<String, String> params = new HashMap<>();
        params.put("user_id", userID);
        params.put("now", Utils.getCurrentTimestamp());

        new HttpGet(context, url, params, isProgress, new HttpListener() {
            @Override
            public void onHttpRespones(Object respones) {
                if (respones != null) {
                    listener.onSuccess(respones.toString());
                } else {
                    listener.onError(null);
                }
            }
        }, new HttpError() {
            @Override
            public void onHttpError(VolleyError volleyError) {
                listener.onError(volleyError);
            }
        });
    }

    public static void updatePreferences(final Context context, SettingPreferences preferences, boolean isProgress,
                                         final ModelManagerListener listener) {
        final String url = WebServiceConfig.FULL_PATH
                + WebServiceConfig.ACTION_UPDATE_USER_PREFERENCES;

        Map<String, String> params = new HashMap<>();
        params.put("user_id", preferences.getUserId());
        params.put("preferences", preferences.getJsonString());

        new HttpGet(context, url, params, isProgress, new HttpListener() {
            @Override
            public void onHttpRespones(Object respones) {
                if (respones != null) {
                    listener.onSuccess(respones.toString());
                } else {
                    listener.onError(null);
                }
            }
        }, new HttpError() {
            @Override
            public void onHttpError(VolleyError volleyError) {
                listener.onError(volleyError);
            }
        });
    }

    public static void updateFavouriteShop(final Context context, String userID, String shopID, boolean isFavourite, boolean isProgress,
                                           final ModelManagerListener listener) {
        final String url = WebServiceConfig.FULL_PATH
                + WebServiceConfig.ACTION_GET_UPDATE_FAVOURITE;

        Map<String, String> params = new HashMap<>();
        params.put("user_id", userID);
        params.put("object_id", shopID);
        params.put("type", "shop");
        params.put("action", isFavourite ? "add" : "remove");

        new HttpGet(context, url, params, isProgress, new HttpListener() {
            @Override
            public void onHttpRespones(Object respones) {
                if (respones != null) {
                    listener.onSuccess(respones.toString());
                } else {
                    listener.onError(null);
                }
            }
        }, new HttpError() {
            @Override
            public void onHttpError(VolleyError volleyError) {
                listener.onError(volleyError);
            }
        });
    }

    public static void updateFavouriteProduct(final Context context, String userID, String productID, boolean isFavourite, boolean isProgress,
                                              final ModelManagerListener listener) {
        final String url = WebServiceConfig.FULL_PATH
                + WebServiceConfig.ACTION_GET_UPDATE_FAVOURITE;

        Map<String, String> params = new HashMap<>();
        params.put("user_id", userID);
        params.put("object_id", productID);
        params.put("type", "product");
        params.put("action", isFavourite ? "add" : "remove");

        new HttpGet(context, url, params, isProgress, new HttpListener() {
            @Override
            public void onHttpRespones(Object respones) {
                if (respones != null) {
                    listener.onSuccess(respones.toString());
                } else {
                    listener.onError(null);
                }
            }
        }, new HttpError() {
            @Override
            public void onHttpError(VolleyError volleyError) {
                listener.onError(volleyError);
            }
        });
    }

    public static void forgotPassword(final Context context, String email, boolean isProgress,
                                              final ModelManagerListener listener) {
        final String url = WebServiceConfig.FULL_PATH
                + WebServiceConfig.ACTION_FORGOT_PASSWORD;

        Map<String, String> params = new HashMap<>();
        params.put("email", email);


        new HttpGet(context, url, params, isProgress, new HttpListener() {
            @Override
            public void onHttpRespones(Object respones) {
                if (respones != null) {
                    listener.onSuccess(respones.toString());
                } else {
                    listener.onError(null);
                }
            }
        }, new HttpError() {
            @Override
            public void onHttpError(VolleyError volleyError) {
                listener.onError(volleyError);
            }
        });
    }
}