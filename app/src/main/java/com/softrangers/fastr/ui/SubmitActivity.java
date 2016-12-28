package com.softrangers.fastr.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.softrangers.fastr.R;
import com.softrangers.fastr.adapter.PagerAdapter;
import com.softrangers.fastr.model.Schedule;
import com.softrangers.fastr.util.ParentActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SubmitActivity extends ParentActivity {

    private PagerAdapter mPagerAdapter;
    @BindView(R.id.tabDots) TabLayout mTabLayout;
    @BindView(R.id.submitFieldsPager) ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit);
        ButterKnife.bind(this);

        Intent submitIntent = getIntent();
        if (submitIntent.hasExtra("schedule")) {
            initTabs();
        } else {
            finish();
        }
    }

    private void initTabs() {
        mPagerAdapter = new PagerAdapter(this, getSupportFragmentManager());
        mPagerAdapter.addFragment(ProductFragment.newInstance(), "");
        mViewPager.setAdapter(mPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    public void addFields() {
        mPagerAdapter.addFragment(ProductFragment.newInstance(), "");
    }

    @OnClick(R.id.submitButton)
    void submitAllPages() {
        Intent intent = new Intent(this, ConfirmActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        for (Fragment fragment : fragments) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }
}
