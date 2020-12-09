package com.hungnt.zaloandroidsdk;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.hungnt.zaloandroidsdk.databinding.ActivityMainBinding;
import com.zing.zalo.zalosdk.oauth.LoginVia;
import com.zing.zalo.zalosdk.oauth.OAuthCompleteListener;
import com.zing.zalo.zalosdk.oauth.OauthResponse;
import com.zing.zalo.zalosdk.oauth.ZaloSDK;

import java.security.MessageDigest;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());
        getBase64StringSha1(getApplicationContext());
        addEvent();
    }

    OAuthCompleteListener listener=new OAuthCompleteListener(){
        @Override
        public void onGetOAuthComplete(OauthResponse response) {
            super.onGetOAuthComplete(response);
            if (TextUtils.isEmpty(response.getOauthCode())){
                onLoginError(response.getErrorCode(),response.getErrorMessage());
                Log.d("Login_Error", "dang nhap khong thanh cong");
            }
            else {
                onLoginSuccess();
                Log.d("Login_Success", "dang nhap thanh cong");
            }
        }

        @Override
        public void onAuthenError(int errorCode, String message) {
            super.onAuthenError(errorCode, message);
        }
    };

    private void onLoginSuccess() {
        String msg="Dang nhap thanh cong";
        Toast.makeText(MainActivity.this,msg,Toast.LENGTH_SHORT).show(); //chua hieu sao deo len dcm -> su dung logd
    }

    private void onLoginError(int errorCode, String errorMessage) {
        String msg="Dang nhap that bai" +errorCode+ "voi loi" +errorMessage;
        Toast.makeText(MainActivity.this,msg,Toast.LENGTH_SHORT).show();
    }


    private void addEvent()
    {
        binding.btnloginwithzalo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginZalo();
            }
        });
    }

    private void loginZalo() {
        ZaloSDK.Instance.authenticate(this, LoginVia.APP_OR_WEB,listener);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ZaloSDK.Instance.onActivityResult(this,requestCode,resultCode,data);
    }

    public static void getBase64StringSha1(Context ctx)
    {
        try {
            PackageInfo info = ctx.getPackageManager().getPackageInfo(ctx.getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String sig = android.util.Base64.encodeToString(md.digest(), Base64.DEFAULT).trim();
                if (sig.trim().length() > 0) {
                    Log.d("MY_SHA", sig);
                }
            }
        }catch(Exception ex)
        {
            Log.e("LOI_SHA",ex.toString());
        }
    }

}