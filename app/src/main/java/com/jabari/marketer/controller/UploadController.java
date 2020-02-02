package com.jabari.marketer.controller;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.jabari.marketer.custom.GlobalVariables;
import com.jabari.marketer.network.config.ApiClient;
import com.jabari.marketer.network.config.ApiInterface;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class UploadController {


    ApiInterface.UploadFileCallback uploadFileCallback;
    Context context;

    public UploadController(ApiInterface.UploadFileCallback uploadFileCallback, Context context) {

        this.uploadFileCallback = uploadFileCallback;
        this.context = context;
    }

    public void Do(String s) {


        Retrofit retrofit = ApiClient.getClient();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);


        File f = new File(s);
        Log.d("s", s);
        RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), f);
        MultipartBody.Part image = MultipartBody.Part.createFormData("image", f.getName(), reqFile);


        Call<JsonObject> call = apiInterface.uploadPhotos(image, GlobalVariables.tok);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                Log.d("response", response.toString());
                if (response.code() == 200) {
                    String url = new Gson().fromJson(response.body().get("url"), String.class);
                    GlobalVariables.urls.add("digipeyk.com/" + url);
                    uploadFileCallback.onResponse(true);
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });

    }
}