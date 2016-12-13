package com.softrangers.fastr.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.softrangers.fastr.util.FastRApp;
import com.softrangers.fastr.R;
import com.softrangers.fastr.model.User;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements Callback<ArrayList<User>> {

    @BindView(R.id.emailAddressInput) EditText mEmailInput;
    @BindView(R.id.pinInput) EditText mPinInput;
    @BindView(R.id.loginProgressBar) ProgressBar mProgressBar;
    @BindView(R.id.loginButton) Button mLoginButton;

    private Call<ArrayList<User>> mUserDataCall;
    private String mEmail, mPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        // TODO: 12/13/16 remove this when tests are done
        mEmailInput.setText("bbrown@exec.com");
        mPinInput.setText("7788");
    }

    @OnClick(R.id.loginButton)
    void login() {
        mEmail = String.valueOf(mEmailInput.getText());
        mPassword = String.valueOf(mPinInput.getText());

        if (mEmail.length() > 0 && isEmailValid(mEmail) && mPassword.length() > 0) {
            toggleLoading(true);
            mUserDataCall = FastRApp.apiInterface().getUserInfo(mEmail, mPassword);
            mUserDataCall.enqueue(this);
        } else {
            Toast.makeText(this, getString(R.string.provide_data), Toast.LENGTH_SHORT).show();
        }
    }

    private void toggleLoading(boolean show) {
        mLoginButton.setClickable(!show);
        mLoginButton.setVisibility(show ? View.INVISIBLE : View.VISIBLE);
        mProgressBar.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    private boolean isEmailValid(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    @Override
    public void onResponse(Call<ArrayList<User>> call, Response<ArrayList<User>> response) {
        ArrayList<User> users = response.body();
        if (users != null && users.size() > 0) {
            FastRApp.setUser(users.get(0));
            FastRApp.getInstance().saveUserCredentials(mEmail, mPassword);
            FastRApp.getInstance().setLoginStatus(true);
            startActivity(new Intent(this, MainActivity.class));
            finish();
        } else if (response.errorBody() != null) {
            try {
                Toast.makeText(this, response.errorBody().string(), Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        toggleLoading(false);
    }

    @Override
    public void onFailure(Call<ArrayList<User>> call, Throwable t) {
        Toast.makeText(this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        toggleLoading(false);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mUserDataCall != null)
            mUserDataCall.cancel();
    }
}
