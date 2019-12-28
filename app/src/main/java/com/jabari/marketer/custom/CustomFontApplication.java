package com.jabari.marketer.custom;

import android.app.Application;

import com.jabari.marketer.R;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class CustomFontApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        CalligraphyConfig.initDefault(
                new CalligraphyConfig.Builder()
                .setDefaultFontPath("font/bnazanin.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
    }
}
