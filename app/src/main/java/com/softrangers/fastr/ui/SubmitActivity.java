package com.softrangers.fastr.ui;

import android.content.Intent;
import android.os.Bundle;

import com.softrangers.fastr.R;
import com.softrangers.fastr.model.Schedule;
import com.softrangers.fastr.util.ParentActivity;

public class SubmitActivity extends ParentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit);

        Intent submitIntent = getIntent();
        if (submitIntent.hasExtra("schedule")) {
            Schedule schedule = submitIntent.getExtras().getParcelable("schedule");
            setContainer(R.id.submitFragmentContainer);
            addFirstFragment(SubmitFragment.newInstance(schedule));
        } else {
            finish();
        }
    }
}
