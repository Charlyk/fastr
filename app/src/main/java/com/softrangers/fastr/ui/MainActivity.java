package com.softrangers.fastr.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.softrangers.fastr.R;
import com.softrangers.fastr.util.ParentActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends ParentActivity {

    @BindView(R.id.mainActivityProgressBar) ProgressBar mProgressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setContainer(R.id.maiFragmentContainer);
        addFirstFragment(SchedulesFragment.newInstance());
    }

    public void toggleLoading(boolean show) {
        runOnUiThread(() -> mProgressBar.setVisibility(show ? View.VISIBLE : View.GONE));
    }
}
