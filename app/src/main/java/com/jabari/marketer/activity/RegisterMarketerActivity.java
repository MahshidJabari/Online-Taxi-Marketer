package com.jabari.marketer.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.jabari.marketer.R;
import com.jabari.marketer.controller.RegisterController;
import com.jabari.marketer.network.config.ApiInterface;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class RegisterMarketerActivity extends AppCompatActivity {

    private EditText et_name, et_phone;
    private String name, phone;
    private TextView tv_register;
    private ProgressBar pb_load;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_marketer);
        setView();
        setONclickRegister();
    }


    public void OnClickBack(View view) {
        startActivity(new Intent(RegisterMarketerActivity.this, MarketerActivity.class));
    }

    private void setView() {
        et_name = findViewById(R.id.et_name);
        et_phone = findViewById(R.id.et_phoneNum);
        tv_register = findViewById(R.id.tv_register);
        pb_load = findViewById(R.id.pb_load);
    }

    private void setONclickRegister() {
        tv_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                OnclickRegister();
            }
        });
    }

    public void OnclickRegister() {

        pb_load.setVisibility(View.VISIBLE);
        ApiInterface.Register_MarketerCallback registerMarketerCallback = new ApiInterface.Register_MarketerCallback() {
            @Override
            public void onResponse(String message) {

                pb_load.setVisibility(View.GONE);
                if (message.equals("ok"))
                    startActivity(new Intent(RegisterMarketerActivity.this, MarketerActivity.class));
                if(message.equals("conflict")){
                    Toast.makeText(RegisterMarketerActivity.this,getResources().getString(R.string.conflict),Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(String error) {

                pb_load.setVisibility(View.GONE);

            }
        };
        RegisterController registerController = new RegisterController(registerMarketerCallback);
        registerController.RegisterMarketer(et_name.getText().toString(), et_phone.getText().toString());
    }

}
