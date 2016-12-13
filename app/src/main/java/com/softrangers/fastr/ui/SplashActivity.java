package com.softrangers.fastr.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.softrangers.fastr.util.FastRApp;
import com.softrangers.fastr.R;
import com.softrangers.fastr.model.User;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity extends AppCompatActivity implements Callback<ArrayList<User>> {

    private Call<ArrayList<User>> mUserDataCall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        if (FastRApp.getInstance().isLoggedIn()) {
            String email = FastRApp.getInstance().getEmail();
            String pass = FastRApp.getInstance().getPassword();
            mUserDataCall = FastRApp.apiInterface().getUserInfo(email, pass);
            mUserDataCall.enqueue(this);
        } else {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }
    }

    @Override
    public void onResponse(Call<ArrayList<User>> call, Response<ArrayList<User>> response) {
        ArrayList<User> users = response.body();
        if (users != null && users.size() > 0) {
            FastRApp.setUser(users.get(0));
            FastRApp.getInstance().setLoginStatus(true);
            startActivity(new Intent(this, MainActivity.class));
            finish();
        } else if (response.errorBody() != null) {
            try {
                Toast.makeText(this, response.errorBody().string(), Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, LoginActivity.class));
                finish();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onFailure(Call<ArrayList<User>> call, Throwable t) {
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mUserDataCall != null) mUserDataCall.cancel();
    }
}
