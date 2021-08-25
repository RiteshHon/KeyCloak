package com.app.keycloak;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.net.HttpURLConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    TextInputLayout et_UserName, et_Password;
    Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_UserName = findViewById(R.id.et_UserName);
        et_Password = findViewById(R.id.et_Password);
        loginButton = findViewById(R.id.loginButton);

        loginButton.setOnClickListener(v -> {
            getAccessToken();
        });

    }

    public void getAccessToken() {
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);

        String userName = et_UserName.getEditText().getText().toString();
        String password = et_Password.getEditText().getText().toString();

        Call<AccessToken> call = service.getAccessToken("Login", "password", "673a93c7-dbcd-4aab-b109-bce48b113ee4","openid",userName,password);
        call.enqueue(new Callback<AccessToken>() {
            @Override
            public void onResponse(Call<AccessToken> call, Response<AccessToken> response) {

                try {
                    if (response.isSuccessful()){
//                    AccessToken accessToken = response.body();
                        startActivity(new Intent(MainActivity.this, HomeActivity.class));
                    } else {
                        Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e){
                    Toast.makeText(MainActivity.this, "Error"+e, Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<AccessToken> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error: "+t, Toast.LENGTH_SHORT).show();
            }
        });

    }

}