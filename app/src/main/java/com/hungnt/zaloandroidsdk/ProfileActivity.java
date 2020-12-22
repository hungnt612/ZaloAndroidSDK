package com.hungnt.zaloandroidsdk;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.hungnt.zaloandroidsdk.databinding.ActivityProfileBinding;
import com.squareup.picasso.Picasso;
import com.zing.zalo.zalosdk.oauth.ZaloOpenAPICallback;
import com.zing.zalo.zalosdk.oauth.ZaloSDK;

import org.json.JSONObject;

public class ProfileActivity extends AppCompatActivity {

    ActivityProfileBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ZaloSDK.Instance.getProfile(ProfileActivity.this,apiCallback,filters);
        addEvents();
    }

    private void addEvents() {
        binding.btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ZaloSDK.Instance.unauthenticate();
                Intent intent=new Intent(ProfileActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
    }

    ZaloOpenAPICallback apiCallback=new ZaloOpenAPICallback() {
        @Override
        public void onResult(JSONObject jsonObject) {
            binding.txtUserid.setText(jsonObject.optString("id"));
            binding.txtName.setText(jsonObject.optString("name"));

            JSONObject pic=jsonObject.optJSONObject("picture");
            JSONObject picData=pic.optJSONObject("data");
            String url = picData.optString("url");
            if (!TextUtils.isEmpty(url))
            {
                Picasso.get().load(url).into(binding.imgUseravatar);
            }
        }
    };



    String []filters={"id","name","picture.type(large)"};
}