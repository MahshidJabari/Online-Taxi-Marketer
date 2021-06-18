package com.jabari.marketer.activity.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.jabari.marketer.R;
import com.jabari.marketer.activity.main.MainActivity;
import com.jabari.marketer.controller.LoginController;
import com.jabari.marketer.custom.GeneralResponse;
import com.jabari.marketer.custom.GlobalVariables;
import com.jabari.marketer.custom.PrefManager;
import com.jabari.marketer.network.config.ApiInterface;
import com.jabari.marketer.network.model.User;

import es.dmoral.toasty.Toasty;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class VerifyActivity extends AppCompatActivity {


    private FloatingActionButton fab_login;
    private EditText et_password;
    private TextView tv_counter1, tv_counter2, tv_resend;
    private static final long START_TIME_IN_MILLIS = 600000;
    private CountDownTimer countDownTimer;
    private long timeLeftInMills = START_TIME_IN_MILLIS;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify);

        setView();
        startTimer();
        getVerifyCode();
        resend();
        login();

    }

    private void setView() {

        tv_counter1 = findViewById(R.id.tv_counter1);
        tv_counter2 = findViewById(R.id.tv_counter2);
        tv_resend = findViewById(R.id.tv_resend);
        fab_login = findViewById(R.id.btn_login);

    }

    private void startTimer() {

        countDownTimer = new CountDownTimer(timeLeftInMills, 1000) {
            @Override
            public void onTick(long milliTillFinish) {

                timeLeftInMills = milliTillFinish;
                updateCountDownText();
                resend();
            }

            @Override
            public void onFinish() {

                resend();
            }
        }.start();
    }

    private void updateCountDownText() {

        int seconds = (int) ((timeLeftInMills / 1000) % 60) / 10;
        int milsecond = (int) ((timeLeftInMills / 1000) % 60) % 10;
        tv_counter2.setText(String.valueOf(seconds));
        tv_counter1.setText(String.valueOf(milsecond));
    }

    private void resetTimer() {

        timeLeftInMills = START_TIME_IN_MILLIS;
        updateCountDownText();

    }

    private void pauseTimer() {
        countDownTimer.cancel();
    }

    private void resend() {
        tv_resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pauseTimer();
                resetTimer();
                startTimer();
                getVerifyCode();
            }
        });
    }

    private String getPhoneNum() {

        Intent intent = getIntent();
        return intent.getStringExtra("phone");
    }

    private void getVerifyCode() {

        LoginController loginController = new LoginController(new ApiInterface.UserVerifyCodeCallback() {
            @Override
            public void onResponse(GeneralResponse generalResponse) {
                GlobalVariables.getVerify = true;
                Toasty.success(VerifyActivity.this, "کد فعالسازی ارسال شد", Toast.LENGTH_LONG, true).show();

            }

            @Override
            public void onFailure(String error) {
                GlobalVariables.getVerify = false;
                Toasty.error(VerifyActivity.this, error, Toast.LENGTH_LONG, true).show();
            }
        });

        loginController.VerifyCode(getPhoneNum());

        if (!GlobalVariables.getVerify) {

        }
    }

    private void login() {
        fab_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (et_password.getText().toString() != null)
                    check_login(getPhoneNum(), et_password.getText().toString());
                else
                    Toasty.error(VerifyActivity.this, "لطفاکد تایید را وارد کنید! ", Toast.LENGTH_LONG, true).show();
            }
        });
    }

    private void check_login(final String phoneNum, final String verify_code) {


        ApiInterface.LoginUserCallback loginUserCallback = new ApiInterface.LoginUserCallback() {

            @Override
            public void onResponse(GeneralResponse generalResponse, User user, String token) {


                if (generalResponse.getSuccess()) {

                    saveLoginPreferences(token, String.valueOf(user.getMobileNum()));

                    startActivity(new Intent(VerifyActivity.this, MainActivity.class));
                }
                if (generalResponse.getMessage().equals("کاربری مورد نظر موجود نیست")) {

                    Toasty.error(VerifyActivity.this, "ثبت نام نکرده اید!", Toast.LENGTH_LONG, true).show();

                }

            }

            @Override
            public void onFailure(String error) {

                Toasty.error(VerifyActivity.this, "مجددا تلاش کنید!", Toast.LENGTH_LONG, true).show();
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
