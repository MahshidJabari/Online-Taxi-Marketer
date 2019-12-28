package com.jabari.marketer.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.jabari.marketer.R;
import com.jabari.marketer.controller.LoginController;
import com.jabari.marketer.custom.GeneralResponse;
import com.jabari.marketer.custom.GlobalVariables;
import com.jabari.marketer.custom.PrefManager;
import com.jabari.marketer.network.config.ApiInterface;
import com.jabari.marketer.network.model.User;

import java.util.regex.Pattern;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class LoginActivity extends AppCompatActivity {
    private EditText et_phone, et_code;
    private Button btn_send;
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
        onClickFabLogin();
    }

    private void setUpView() {

        et_phone = findViewById(R.id.et_phoneNum);
        et_code = findViewById(R.id.et_verification_code);
        btn_send = findViewById(R.id.btn_send_code);
        fab_login = findViewById(R.id.btn_login);
        fab_login.bringToFront();
    }

    private boolean isValidPhone(String phone) {

        if (!Pattern.matches("^0(9)\\d{9}$", phone)) {
            Toast.makeText(LoginActivity.this, "شماره ی موبایل اشتباه وارد شده!", Toast.LENGTH_LONG).show();
            et_phone.getText().clear();
            return false;
        } else
            return true;
    }

    private void onClickFabLogin() {

        if (GlobalVariables.getVerify)
            fab_login.setClickable(true);
        else
            fab_login.setClickable(false);
        fab_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                check_login(et_phone.getText().toString()
                        , et_code.getText().toString());

/*
                if (GlobalVariables.getVerify) {
                    check_login(et_phone.getText().toString()
                            , et_code.getText().toString());
                } else {
                    Toast.makeText(LoginActivity.this, "کد فعالسازی به درستی وارد نشده!", Toast.LENGTH_SHORT).show();
                }
*/
            }
        });

    }

    public void OnClickSendVerifyCode(View view) {

        btn_send.setBackground(getResources().getDrawable(R.drawable.back_thirty_radius_gray));
        btn_send.setClickable(false);
        final String phoneNumber = et_phone.getText().toString();
        if (!isValidPhone(phoneNumber))
            Toast.makeText(this, "شماره ی تلفن وارد شده معتبر نیست!", Toast.LENGTH_SHORT);


        LoginController loginController = new LoginController(new ApiInterface.UserVerifyCodeCallback() {
            @Override
            public void onResponse(GeneralResponse generalResponse) {
                GlobalVariables.getVerify = true;
                Toast.makeText(LoginActivity.this, "کد فعالسازی ارسال شد", Toast.LENGTH_LONG).show();
                Toast.makeText(LoginActivity.this, phoneNumber.substring(6, 11), Toast.LENGTH_LONG).show();

            }

            @Override
            public void onFailure(String error) {
                GlobalVariables.getVerify = false;
                btn_send.setText(getString(R.string.resend_validation));
                btn_send.setBackground(getResources().getDrawable(R.drawable.back_thirty_radius_blue));
                Toast.makeText(LoginActivity.this, "مجددا تلاش کنید", Toast.LENGTH_LONG).show();
            }
        });

        loginController.VerifyCode(phoneNumber);

        if (!GlobalVariables.getVerify) {

            btn_send.setText(getString(R.string.send_validation));
            btn_send.setBackground(getResources().getDrawable(R.drawable.back_thirty_radius_blue));
            btn_send.setClickable(true);

        }
    }

    private void check_login(final String phoneNum, final String verify_code) {


        ApiInterface.LoginUserCallback loginUserCallback = new ApiInterface.LoginUserCallback() {

            @Override
            public void onResponse(GeneralResponse generalResponse, User user, String token) {


                if (generalResponse.getSuccess()) {

                    saveLoginPreferences(token, String.valueOf(user.getMobileNum()));

                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                }
                if(generalResponse.getMessage().equals("کاربری مورد نظر موجود نیست")){

                    Toast.makeText(LoginActivity.this, "ثبت نام نکرده اید!", Toast.LENGTH_LONG).show();

                }

            }

            @Override
            public void onFailure(String error) {

                Toast.makeText(LoginActivity.this, "مجددا تلاش کنید!", Toast.LENGTH_LONG).show();
            }

        };

        LoginController loginUserController = new LoginController(loginUserCallback);
        loginUserController.Do(phoneNum, verify_code);
    }

    private void saveLoginPreferences(String token, String user) {

        PrefManager prefManager = new PrefManager(getBaseContext());
        prefManager.setToken(token);
        prefManager.setPhoneNum(user);
        GlobalVariables.tok = token;
        GlobalVariables.phoneUser = user;
        GlobalVariables.isLogin = true;


    }


}
