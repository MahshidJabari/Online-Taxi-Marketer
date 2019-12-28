package com.jabari.marketer.controller;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.jabari.marketer.custom.GlobalVariables;
import com.jabari.marketer.network.config.ApiClient;
import com.jabari.marketer.network.config.ApiInterface;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class UploadController {


    ApiInterface.UploadFileCallback uploadFileCallback;

    public UploadController(ApiInterface.UploadFileCallback uploadFileCallback) {

        this.uploadFileCallback = uploadFileCallback;
    }

    public void Do(String s) {

        Retrofit retrofit = ApiClient.getClient();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);

    /*    for (String s : list) {
*/

            File f = new File(s);
        RequestBody token = RequestBody.create(MultipartBody.FORM, GlobalVariables.tok);

        RequestBody reqFile = RequestBody.create(MediaType.parse("img/jpeg"), f);
            MultipartBody.Part body = MultipartBody.Part.createFormData("image", f.getName(), reqFile);
            Call<JsonObject> call = apiInterface.uploadPhotos(body,token);
            call.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                    Log.d("response",response.toString());
                    String url = new Gson().fromJson(response.body().get("url"),String.class);
                    GlobalVariables.urls.add("digipeyk.com/"+url);
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {

                }
            });

        }

}


