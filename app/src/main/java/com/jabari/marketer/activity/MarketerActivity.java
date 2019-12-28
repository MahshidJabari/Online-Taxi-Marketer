package com.jabari.marketer.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.jabari.marketer.R;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MarketerActivity extends AppCompatActivity {

    private TextView tv_register_driver,tv_register_marketer;
    private FloatingActionButton fab_profile,fab_my_activity,fab_register;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marketer);

        setViews();
        onclickFab();
        register_driver_onclick();
        register_marketer_onclick();
    }

    private void setViews(){
        tv_register_driver = findViewById(R.id.tv_register_driver);
        tv_register_marketer = findViewById(R.id.tv_register_marketer);
        fab_my_activity = findViewById(R.id.fab_my_activity);
        fab_profile = findViewById(R.id.fab_profile);
        fab_register = findViewById(R.id.fab_register);

        fab_profile.bringToFront();
        fab_my_activity.bringToFront();
        fab_register.bringToFront();

    }
    private void onclickFab(){

        fab_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fab_profile.setBackgroundTintList(getResources().getColorStateList(R.color.green));
                fab_register.setBackgroundTintList(getResources().getColorStateList(R.color.blue));
                startActivity(new Intent(MarketerActivity.this,ProfileActivity.class));

            }
        });
        fab_my_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fab_my_activity.setBackgroundTintList(getResources().getColorStateList(R.color.green));
                fab_register.setBackgroundTintList(getResources().getColorStateList(R.color.blue));
                startActivity(new Intent(MarketerActivity.this,MainActivity.class));

            }
        });
    }
    private void register_driver_onclick(){
        tv_register_driver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MarketerActivity.this,RegisterDriverActivity.class));
            }
        });
    }

    private void register_marketer_onclick(){
        tv_register_marketer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MarketerActivity.this,RegisterMarketerActivity.class));
            }
        });
    }
}
