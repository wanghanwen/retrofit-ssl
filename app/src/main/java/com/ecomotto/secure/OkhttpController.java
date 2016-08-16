package com.ecomotto.secure;

import android.content.Context;
import android.util.Base64;

import com.ecomotto.secure.cer.VolleySSLSocketFactory;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.logging.HttpLoggingInterceptor;

import java.io.IOException;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * Created by Angelo on 11/8/15.
 */
public class OkhttpController {

    public static final String BASE_URL = "https://172.168.10.141:8443";
    private static Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
            .create();
    private static Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson));

    private static OkHttpClient httpClient = new OkHttpClient();

    public OkhttpController(Context context) {
        setupLog();
        setupSecureClient(context);
    }

    private void setupLog() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        httpClient.interceptors().add(interceptor);
    }

    private void setupSecureClient(Context ctx) {
        try {
            SSLSocketFactory sslSocketFactory = VolleySSLSocketFactory.getSSLSocketFactory(ctx);
            httpClient.setSslSocketFactory(sslSocketFactory);
            httpClient.setHostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public <S> S createService(Class<S> serviceClass) {
        return createService(serviceClass, null, null);
    }

    public <S> S createService(Class<S> serviceClass, String username, String password) {
        if (username != null && password != null) {
            String credentials = username + ":" + password;
            final String basic =
                    "Basic " + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);

            httpClient.interceptors().clear();
            httpClient.interceptors().add(new Interceptor() {
                @Override
                public Response intercept(Interceptor.Chain chain) throws IOException {
                    Request original = chain.request();

                    Request.Builder requestBuilder = original.newBuilder()
                            .header("Authorization", basic)
                            .method(original.method(), original.body());

                    Request request = requestBuilder.build();
                    return chain.proceed(request);
                }
            });
        }

        Retrofit retrofit = builder.client(httpClient).build();
        return retrofit.create(serviceClass);
    }
}
