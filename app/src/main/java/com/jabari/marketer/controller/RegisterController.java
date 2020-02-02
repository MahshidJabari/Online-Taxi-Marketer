package com.jabari.marketer.controller;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.jabari.marketer.custom.GlobalVariables;
import com.jabari.marketer.network.config.ApiClient;
import com.jabari.marketer.network.config.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RegisterController {

    ApiInterface.Register_driverCallback register_driverCallback;
    ApiInterface.Register_MarketerCallback register_marketerCallback;
    ApiInterface.Register_USerCallBack registerUSerCallBack;

    public RegisterController(ApiInterface.Register_driverCallback registerDriverCallback) {

        this.register_driverCallback = registerDriverCallback;
    }

    public RegisterController(ApiInterface.Register_MarketerCallback registerMarketerCallback) {
        this.register_marketerCallback = registerMarketerCallback;
    }

    public RegisterController(ApiInterface.Register_USerCallBack registerUserCallback) {
        this.registerUSerCallBack = registerUserCallback;
    }


    public void RegisterDriver(String name, String mobile, String address,
                               String meli, String Id, String license,
                               String military, String greenPaper,
                               String waterBill, String electricBill) {

        Retrofit retrofit = ApiClient.getClient();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);

        Call<JsonObject> call = apiInterface.Register_driver(name, mobile, address, meli, Id, license, military, greenPaper, waterBill, electricBill, GlobalVariables.tok);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                Boolean success = new Gson().fromJson(response.body().get("success"), Boolean.class);
                if (success) {

                    register_driverCallback.onResponse(success);
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });
    }

    public void RegisterMarketer(String name, String phone) {

        Retrofit retrofit = ApiClient.getClient();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        Call<JsonObject> call = apiInterface.Register_marketer(name, phone, GlobalVariables.tok);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                Log.d("response", "registered!");
                if (response.body() != null) {
                    register_marketerCallback.onResponse("ok");
                } else register_marketerCallback.onResponse("conflict");

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });

    }

    public void RegisterCostumer(String phone) {

        Retrofit retrofit = ApiClient.getClient();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        Call<JsonObject> call = apiInterface.Register_USer(phone);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                registerUSerCallBack.onResponse("ok");
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });


    }
}
