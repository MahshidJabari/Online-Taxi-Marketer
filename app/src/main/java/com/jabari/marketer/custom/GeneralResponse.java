package com.jabari.marketer.custom;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class GeneralResponse {

    public GeneralResponse(JsonObject body){
        Message = new Gson().fromJson(body.get("message"),String.class);
        success = new Gson().fromJson(body.get("success"),Boolean.class);
    }

    private Boolean success;
    private String Message;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        success = success;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }
}
