package com.jabari.marketer.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.jabari.marketer.R;
import com.jabari.marketer.controller.RegisterController;
import com.jabari.marketer.custom.GlobalVariables;
import com.jabari.marketer.network.config.ApiInterface;

import java.util.List;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class RegisterDriverActivity extends AppCompatActivity {

    EditText et_name,et_mobile,et_address;
    TextView tv_register,tv_upload;
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_driver);

        setView();
        OnClickSend();
        OnCLickUpload();
    }

    public void OnClickBack(View view){
        startActivity(new Intent(RegisterDriverActivity.this,MarketerActivity.class));
    }

    private void setView(){

        et_name = findViewById(R.id.et_name);
        et_mobile = findViewById(R.id.et_phoneNum);
        et_address = findViewById(R.id.et_address1);
        tv_register = findViewById(R.id.tv_register);
        tv_upload = findViewById(R.id.tv_upload_photo);

    }
    private void OnClickSend(){

        tv_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                OnClickRegister(et_name.getText().toString(),et_mobile.getText().toString(),et_address.getText().toString(), GlobalVariables.urls);
            }
        });
    }
    private void OnCLickUpload(){
        tv_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterDriverActivity.this,UploadActivity.class));
            }
        });
    }
    private void OnClickRegister(String name, String phone, String address, List list){

        ApiInterface.Register_driverCallback registerDriverCallback = new ApiInterface.Register_driverCallback() {
            @Override
            public void onResponse(Boolean success) {

                if(success){
                    Toast.makeText(RegisterDriverActivity.this, "ثبت نام با موفقیت انجام شد!", Toast.LENGTH_LONG).show();

                }
                else
                    Toast.makeText(RegisterDriverActivity.this, "ثبت نام با خطا مواجه شد!", Toast.LENGTH_LONG).show();

            }

            @Override
            public void onFailure(String error) {

            }
        };

        RegisterController registerController = new RegisterController(registerDriverCallback);
        registerController.RegisterDriver(name,phone,address,list.get(0).toString(),list.get(1).toString()
                ,list.get(2).toString(),list.get(3).toString(),list.get(4).toString(),list.get(5).toString(),list.get(6).toString());
    }
}
