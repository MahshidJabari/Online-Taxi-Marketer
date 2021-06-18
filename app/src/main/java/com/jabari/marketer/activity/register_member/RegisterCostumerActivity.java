package com.jabari.marketer.activity.register_member;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.jabari.marketer.R;
import com.jabari.marketer.activity.main.MarketerActivity;
import com.jabari.marketer.controller.RegisterController;
import com.jabari.marketer.network.config.ApiInterface;

import es.dmoral.toasty.Toasty;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class RegisterCostumerActivity extends AppCompatActivity {

    private EditText et_phone;
    private ProgressBar pb_load;


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_costumer);

        setView();
    }

    public void OnClickBack(View view) {
        startActivity(new Intent(RegisterCostumerActivity.this, MarketerActivity.class));
    }

    private void setView() {
        et_phone = findViewById(R.id.et_phoneNum);
        pb_load = findViewById(R.id.pb_load);
    }

    public void onClickRegister(View view) {

        pb_load.setVisibility(View.VISIBLE);
        ApiInterface.Register_USerCallBack register_uSerCallBack = new ApiInterface.Register_USerCallBack() {
            @Override
            public void onResponse(String message) {

                pb_load.setVisibility(View.GONE);
                if (message.equals("ok"))
                    startActivity(new Intent(RegisterCostumerActivity.this, MarketerActivity.class));

            }

            @Override
            public void onFailure(String error) {

                pb_load.setVisibility(View.GONE);
                Toasty.error(RegisterCostumerActivity.this, getResources().getString(R.string.failed), Toast.LENGTH_LONG).show();


            }
        };

        RegisterController registerController = new RegisterController(register_uSerCallBack);
        registerController.RegisterCostumer(et_phone.getText().toString());


    }


}
