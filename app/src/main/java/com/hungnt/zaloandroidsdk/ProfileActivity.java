package com.hungnt.zaloandroidsdk;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

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
        logOut();

    }

    private void logOut() {
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.function_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==R.id.actionCreatePost)
        {
            Toast.makeText(ProfileActivity.this,"Ban vua chon dang bai viet",Toast.LENGTH_LONG).show();
            Intent intent=new Intent(ProfileActivity.this,PostActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}