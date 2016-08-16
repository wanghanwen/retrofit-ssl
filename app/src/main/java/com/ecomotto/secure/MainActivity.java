package com.ecomotto.secure;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.google.gson.JsonElement;

import java.util.HashMap;
import java.util.Map;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    /** okhttp控制器 **/
    private OkhttpController serviceGenerator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        serviceGenerator = new OkhttpController(this);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String, String> param = new HashMap<>();
                param.put("id", "1");
                Call<JsonElement> call = serviceGenerator.createService(Api.class).basicLogin(param);
                call.enqueue(new Callback<JsonElement>() {

                    @Override
                    public void onResponse(Response<JsonElement> response, Retrofit retrofit) {
                        Log.i(TAG, "success? " + response.isSuccess());
                        Log.i(TAG, "message: " + response.message());
                        Log.i(TAG, "headers: " + response.headers().toString());
                        Log.i(TAG, "code: " + response.code());
                        Log.i(TAG, "errorBody: " + response.errorBody());
                        Log.i(TAG, String.valueOf(response.body()));
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        t.printStackTrace();
                    }
                });
            }
        });
    }

}
