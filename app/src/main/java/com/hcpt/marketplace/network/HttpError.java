package com.hcpt.marketplace.network;

import com.android.volley.VolleyError;


public interface HttpError {
    void onHttpError(VolleyError volleyError);
}
