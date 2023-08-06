package com.dg.info.civildesk.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dg.info.civildesk.R;
import com.dg.info.civildesk.api.RetrofitClient;
import com.dg.info.civildesk.models.LoginResponse;
import com.dg.info.civildesk.models.UserData;
import com.dg.info.civildesk.utils.CustPrograssbar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginScreenActivity extends BaseActivity {
    EditText edUsername;
    EditText edPassword;
    CustPrograssbar custPrograssbar;
    TextView btnlogin;
    TextView tvforgotpswd;
    String email_mobile,password;

    String deviceimei="dnqwiodhqw8d",TAG="TAG";
    private static final int PERMISSIONS_REQUEST_READ_PHONE_STATE =999 ;
     TelephonyManager telephonyManager;
    private static final int REQUEST_CODE = 101;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        edUsername=findViewById(R.id.ed_username);
        edPassword=findViewById(R.id.ed_password);
        btnlogin=findViewById(R.id.btn_login);
        tvforgotpswd=findViewById(R.id.txt_forgotpassword);
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(LoginScreenActivity.this,HomeActivity.class));
                //startActivity(new Intent(LoginScreenActivity.this, HomeActivity.class));

                validate();

            }
        });
        tvforgotpswd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

 custPrograssbar = new CustPrograssbar();

    }

    private void validate() {
        email_mobile = edUsername.getText().toString();
        password = edPassword.getText().toString();


        if (TextUtils.isEmpty(email_mobile)) {
            edUsername.setError("Please enter your mobile number!");
            edUsername.requestFocus();
            return;
        }  if (TextUtils.isEmpty(password)) {
            edPassword.setError("Please enter your password!");
            edPassword.requestFocus();
            return;
        } if (!isValidMobile(email_mobile)) {
            showToast("Please enter valid email mobile number");
            return;
        }
        else  {

            callLoginApi();


        }

    }

    private void callLoginApi() {
        custPrograssbar.prograssCreate(LoginScreenActivity.this);


        Log.d(TAG, "callLoginApi:email_mobile "+email_mobile);
        Log.d(TAG, "callLoginApi:password "+password);
        Call<LoginResponse> call = RetrofitClient.getRetrofitClient().login(email_mobile,password,deviceimei);
        call.enqueue(new Callback<LoginResponse>() {


            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                custPrograssbar.closePrograssBar();

                if (response.body() != null ) {

                    if (response.body().getResponseCode().toString().matches("0")) {
                        Toast.makeText(LoginScreenActivity.this, response.body().getResponseMessage(), Toast.LENGTH_LONG).show();
                        UserData userDetail = response.body().getData();
                        // save user data
                        localData.setLoggedIn(true);
                        localData.setSignIn(userDetail);
                        localData.setUserId(userDetail.getUserId());
                       // localData.setUserRole(userDetail.getAdmin());
                        localData.setAssignComanyName(userDetail.getParty());
                        Log.d(TAG, "onResponse: party"+userDetail.getParty());
                        //localData.setAssignComanyName(userDetail.getParty());




                        startActivity(new Intent(LoginScreenActivity.this, HomeActivity.class));
                        finishAffinity();
                    } else {
                        //showAlertForEnquiry();
                        Toast.makeText(LoginScreenActivity.this, response.body().getResponseMessage(), Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(LoginScreenActivity.this, getString(R.string.error_general), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                custPrograssbar.closePrograssBar();

                Log.d(TAG, "" + t.getMessage());
                Toast.makeText(LoginScreenActivity.this, "Invalid Data..", Toast.LENGTH_SHORT).show();
            }
        });
    }
}