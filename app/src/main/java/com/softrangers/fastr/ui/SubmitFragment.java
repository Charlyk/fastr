package com.softrangers.fastr.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.softrangers.fastr.R;
import com.softrangers.fastr.model.Schedule;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class SubmitFragment extends Fragment {

    private Unbinder mUnbinder;
    private Schedule mSchedule;

    public SubmitFragment() {
        // Required empty public constructor
    }

    public static SubmitFragment newInstance(Schedule schedule) {
        Bundle args = new Bundle();
        args.putParcelable("schedule", schedule);
        SubmitFragment fragment = new SubmitFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_submit, container, false);
        mUnbinder = ButterKnife.bind(this, view);
        mSchedule = getArguments().getParcelable("schedule");
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }
}
