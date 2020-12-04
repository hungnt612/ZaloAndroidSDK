package com.hungnt.zaloandroidsdk;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;

import java.security.MessageDigest;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getBase64StringSha1(getApplicationContext());
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