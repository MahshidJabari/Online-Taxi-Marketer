package com.jabari.marketer.activity.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.jabari.marketer.R;

import java.util.regex.Pattern;

import es.dmoral.toasty.Toasty;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class LoginActivity extends AppCompatActivity {
    private EditText et_phone;
    private FloatingActionButton fab_login;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setUpView();
    }

    private void setUpView() {

        et_phone = findViewById(R.id.et_phoneNum);
        fab_login = findViewById(R.id.btn_login);
        fab_login.bringToFront();
    }

    public void onClickFab(View view) {

        if (!et_phone.getText().toString().isEmpty()) {
            if (!Pattern.matches("^0(9)\\d{9}$", et_phone.getText().toString()))
                Toasty.error(LoginActivity.this, "شماره ی موبایل اشتباه وارد شده!", Toast.LENGTH_LONG).show();
            else {
                Intent intent = new Intent(LoginActivity.this, VerifyActivity.class);
                intent.putExtra("phone", et_phone.getText().toString());
                startActivity(intent);
            }
        } else
            Toasty.error(LoginActivity.this, "لطفا شماره موبایل خود را وارد کنید!", Toast.LENGTH_LONG).show();

    }


}
