package com.jabari.marketer.network.config;

import com.google.gson.JsonObject;
import com.jabari.marketer.custom.GeneralResponse;
import com.jabari.marketer.network.model.User;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface ApiInterface {

    @FormUrlEncoded
    @POST("auth/marketer/login")
    Call<JsonObject> getVerifyCode(@Field("mobile") String PhoneNumber);

    interface UserVerifyCodeCallback{
        void onResponse(GeneralResponse generalResponse);
        void onFailure(String error);
    }

    @FormUrlEncoded
    @POST("auth/marketer/loginverify")
    Call<JsonObject> check_login(@Field("mobile") String PhoneNumber,
                                 @Field("verifyCode") String verifyCode);

    interface LoginUserCallback{
        void onResponse(GeneralResponse generalResponse, User user, String token);
        void onFailure(String error);
    }

    @GET("marketer/signup/status")
    @Headers({
            "Content-Type: application/x-www-form-urlencoded"
    })

    Call<JsonObject> getMainInformation(@Header("Authorization") String token);

    interface MainInformationCallBack {
        void onResponse(String RegisteredMarketer,String RegisteredDriver,String credit);
        void onFailure(String error);
    }

    @FormUrlEncoded
    @PUT("marketer/signup/marketer")
    @Headers({
            "Content-Type: application/x-www-form-urlencoded"
    })

    Call<JsonObject> Register_marketer(@Field ("name") String FirstName,
                                       @Field("mobile") String mobile,
                                       @Header("Authorization") String token);
    interface Register_MarketerCallback{
        void onResponse(String message);
        void onFailure(String error);
    }

    @FormUrlEncoded
    @PUT("marketer/signup/driver")
    Call<JsonObject> Register_driver(@Field ("name") String FirstName,
                                     @Field("mobile") String LastName,
                                     @Field("address") String mobile,
                                     @Field("documentMeli") String meli,
                                     @Field("documentId") String Id,
                                     @Field("documentLicense") String licence,
                                     @Field("documentMilitary") String military,
                                     @Field("documentGreenPaper") String greenPaper,
                                     @Field("documentWaterBill") String waterBill,
                                     @Field("documentElectricalBill") String electricalBill,
                                     @Header("Authorization") String token);
    interface Register_driverCallback{
        void onResponse(Boolean success);
        void onFailure(String error);
    }

    @Multipart
    @POST("image")
    @Headers({
            "Content-Type: application/x-www-form-urlencoded"
    })

    Call<JsonObject> uploadPhotos(@Part MultipartBody.Part file,@Part(".Authorization") RequestBody token);

    interface UploadFileCallback{
        void onResponse(String url);
        void onFailure(String error);

    }

}
