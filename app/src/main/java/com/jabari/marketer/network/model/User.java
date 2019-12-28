package com.jabari.marketer.network.model;

import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("firstName")
    String FirstName;
    @SerializedName("mobile")
    String mobileNum;
    @SerializedName("lastName")
    String lastName;
    @SerializedName("id")
    String id;

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @SerializedName("jwtAccessToken")



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }


    public String getMobileNum() {
        return mobileNum;
    }

    public void setMobileNum(String mobileNum) {
        this.mobileNum = mobileNum;
    }


}
