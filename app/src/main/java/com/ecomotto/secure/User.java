package com.ecomotto.secure;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Angelo on 11/14/15.
 */
public class User {

    @SerializedName("username")
    String mUsername;

    @SerializedName("access_token")
    String mAccessToken;

    public User(String username, String accessToken) {
        this.mUsername = username;
        this.mAccessToken = accessToken;
    }

    @Override
    public String toString() {
        return "{ username: " + this.mUsername + ", access_token: " + this.mAccessToken + " }";
    }
}
