package com.dg.info.civildesk.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import com.dg.info.civildesk.BuildConfig;
import com.dg.info.civildesk.R;
import com.dg.info.civildesk.utils.NetworkUtil;

public class SplashScreenActivity extends BaseActivity {
    private static int SPLASH_TIME_OUT = 3000;
    private float version;
    private String updateUrl;
    private Dialog dialog;
    private TextView tvVersion;
    String TAG="TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        tvVersion=findViewById(R.id.tv_version);
        tvVersion.setText(BuildConfig.VERSION_NAME);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
               // startActivity(new Intent(SplashScreenActivity.this,LoginScreenActivity.class));

                if (NetworkUtil.getConnectivityStatus(SplashScreenActivity.this))
                {
                    Intent intent;
                    if (localData.isLoggedIn()) {
                        intent = new Intent(SplashScreenActivity.this, HomeActivity.class);
                    }
                    else {
                        intent = new Intent(SplashScreenActivity.this, LoginScreenActivity.class);

                    }
                    startActivity(intent);
                    finish();
                    //checkAppVersion();

                }
                else
                {
                    //startNextActivity();
                    showToast("Please Check Your Internet Connection!");


                }
            }
        }, SPLASH_TIME_OUT);

    }
}