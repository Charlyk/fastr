package com.softrangers.fastr.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.softrangers.fastr.R;

import butterknife.ButterKnife;

public class ConfirmActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm);
        ButterKnife.bind(this);
        findViewById(R.id.submitBackButton).setOnClickListener(view -> {
            onBackPressed();
        });
    }
}
