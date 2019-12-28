package com.jabari.marketer.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.jabari.marketer.custom.GeneralResponse;
import com.jabari.marketer.custom.GlobalVariables;
import com.jabari.marketer.network.config.ApiClient;
import com.jabari.marketer.network.config.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainController {

    ApiInterface.MainInformationCallBack mainInformationCallBack;

    public MainController(ApiInterface.MainInformationCallBack mainInformationCallBack){
        this.mainInformationCallBack = mainInformationCallBack;
    }
    public void Do(){

        Retrofit retrofit = ApiClient.getClient();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        Call<JsonObject> call = apiInterface.getMainInformation(GlobalVariables.tok);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                if(response.isSuccessful()){

                    String registeredDriver = new Gson().fromJson(response.body().get("registeredDriver"),String.class);
                    String registeredMarketer = new Gson().fromJson(response.body().get("registeredMarketer"),String.class);
                    String credit = new Gson().fromJson(response.body().get("balance"),String.class);

                    mainInformationCallBack.onResponse(registeredMarketer,registeredDriver,credit);
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

                mainInformationCallBack.onFailure("اختلال در بارگیری اطلاعات");

            }
        });
    }
}
