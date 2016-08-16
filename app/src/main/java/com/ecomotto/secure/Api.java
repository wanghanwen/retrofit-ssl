package com.ecomotto.secure;

import com.google.gson.JsonElement;

import java.util.Map;

import retrofit.Call;
import retrofit.http.POST;
import retrofit.http.QueryMap;

/**
 * Created by Angelo on 11/8/15.
 */
public interface Api {

    @POST("/ydWallet/user/test")
    Call<JsonElement> basicLogin(@QueryMap Map<String, String> options);
}
