package com.jabari.marketer.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.jabari.marketer.R;
import com.jabari.marketer.controller.MainController;
import com.jabari.marketer.network.config.ApiInterface;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends AppCompatActivity {

    private TextView tv_credit, tv_registered_marketer, tv_registered_driver;
    private FloatingActionButton fab_profile, fab_my_activity, fab_register;
    private String registeredDriver, registeredMarketer;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setViews();
        getInformation();
        onclickFab();
        registered_driver_onclick();
        registered_marketer_onclick();
        DriverOnClick();
        MarketerOnClick();
    }

    private void setViews() {
        fab_my_activity = findViewById(R.id.fab_my_activity);
        fab_profile = findViewById(R.id.fab_profile);
        fab_register = findViewById(R.id.fab_register);
        tv_credit = findViewById(R.id.tv_credit);
        tv_registered_driver = findViewById(R.id.tv_registered_driver);
        tv_registered_marketer = findViewById(R.id.tv_registered_marketer);

        fab_profile.bringToFront();
        fab_my_activity.bringToFront();
        fab_register.bringToFront();

    }

    private void onclickFab() {

        fab_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fab_profile.setBackgroundTintList(getResources().getColorStateList(R.color.green));
                fab_my_activity.setBackgroundTintList(getResources().getColorStateList(R.color.blue));
                startActivity(new Intent(MainActivity.this, ProfileActivity.class));
            }
        });
        fab_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fab_register.setBackgroundTintList(getResources().getColorStateList(R.color.green));
                fab_my_activity.setBackgroundTintList(getResources().getColorStateList(R.color.blue));
                startActivity(new Intent(MainActivity.this, MarketerActivity.class));

            }
        });
    }

    private void registered_driver_onclick() {

        tv_registered_driver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private void registered_marketer_onclick() {
        tv_registered_marketer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    public void getInformation() {

        ApiInterface.MainInformationCallBack mainInformationCallBack = new ApiInterface.MainInformationCallBack() {
            @Override
            public void onResponse(String RegisteredMarketer, String RegisteredDriver, String credit) {

                tv_credit.setText(credit);
                registeredDriver = RegisteredDriver;
                registeredMarketer = RegisteredMarketer;
            }

            @Override
            public void onFailure(String error) {

                Log.d("main", "failed");
            }
        };
        MainController mainController = new MainController(mainInformationCallBack);
        mainController.Do();
    }

    private void DriverOnClick() {
        tv_registered_driver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDriverDialog(registeredDriver);
            }
        });
    }

    private void MarketerOnClick() {
        tv_registered_marketer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showMarketerDialog(registeredMarketer);
            }
        });
    }

    public void showDriverDialog(String driver) {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.alertdialog);
        TextView body = dialog.findViewById(R.id.tv_dialog);
        body.setText("تعداد راننده هایی که ثبت کرده اید: " + driver);
        Button button = dialog.findViewById(R.id.btn_ok);
        dialog.show();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

    }

    public void showMarketerDialog(String marketer) {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.alertdialog);
        TextView body = dialog.findViewById(R.id.tv_dialog);
        body.setText("تعداد بازاریاب هایی که ثبت کرده اید: " + marketer);

        Button button = dialog.findViewById(R.id.btn_ok);
        dialog.show();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

    }

}
