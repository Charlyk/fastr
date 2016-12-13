package com.softrangers.fastr.ui;


import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.softrangers.fastr.util.FastRApp;
import com.softrangers.fastr.R;
import com.softrangers.fastr.adapter.SchedulesAdapter;
import com.softrangers.fastr.model.Schedule;
import com.softrangers.fastr.model.Status;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * A simple {@link Fragment} subclass.
 */
public class SchedulesFragment extends Fragment implements Callback<ArrayList<Schedule>>,
        SchedulesAdapter.OnItemInteractionListener, DatePickerDialog.OnDateSetListener {

    public static final int PHONE_PERMISSION_REQUEST = 2236;

    private Unbinder mUnbinder;
    private MainActivity mActivity;
    private SchedulesAdapter mAdapter;
    private ArrayList<Schedule> mSchedules;
    private SimpleDateFormat mDateFormat;
    private Date mToday;
    private boolean mIsToday;
    private Date mSelectedDate;

    private Schedule mSelectedSchedule;
    private Call<ArrayList<Schedule>> mSchedulesCall;
    private static final ArrayList<Status> STATUSES = new ArrayList<Status>() {{
        add(new Status("Cancelled", "3"));
        add(new Status("Customer No-Show", "11"));
        add(new Status("Walk away", "23"));
        add(new Status("Completed", "22"));
    }};

    @BindView(R.id.schedulesRexyclerView) RecyclerView mRecyclerView;
    @BindView(R.id.animatedText) TextView mWelcomeMessage;
    @BindView(R.id.pickDateButton) Button mDateBtn;

    public SchedulesFragment() {
        // Required empty public constructor
    }

    public static SchedulesFragment newInstance() {
        Bundle args = new Bundle();
        SchedulesFragment fragment = new SchedulesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_schedules, container, false);

        mToday = new Date();
        mDateFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault());

        mActivity = (MainActivity) getActivity();
        mUnbinder = ButterKnife.bind(this, view);
        mSchedules = new ArrayList<>();

        mAdapter = new SchedulesAdapter(mSchedules);
        mAdapter.setItemInteractionListener(this);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        mRecyclerView.setAdapter(mAdapter);

        getTodaySchedules();
        return view;
    }

    private void getSchedulesFromServer(@NonNull Date date) {
        mDateBtn.setText(mDateFormat.format(date));
        mSelectedDate = date;
        mSchedules.clear();
        mActivity.toggleLoading(true);
        mSchedulesCall = FastRApp.apiInterface().getSchedules(FastRApp.user().getProgramId(),
                FastRApp.user().getUserId(), mDateFormat.format(date));
        mSchedulesCall.enqueue(this);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mSchedulesCall != null) mSchedulesCall.cancel();
        mUnbinder.unbind();
    }

    @Override
    public void onResponse(Call<ArrayList<Schedule>> call, Response<ArrayList<Schedule>> response) {
        if (response.body() != null) {
            mSchedules.addAll(response.body());
            mActivity.runOnUiThread(() -> mAdapter.notifyDataSetChanged());
            if (mIsToday) {
                mWelcomeMessage.setText(String.format(getString(R.string.welcome), String.valueOf(mSchedules.size()),
                        mDateFormat.format(mToday)));
                mIsToday = false;
            }
        } else if (response.errorBody() != null) {
            try {
                Toast.makeText(mActivity, response.errorBody().string(), Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        mActivity.toggleLoading(false);
    }

    @Override
    public void onFailure(Call<ArrayList<Schedule>> call, Throwable t) {
        if (isAdded()) {
            mActivity.toggleLoading(false);
            Toast.makeText(mActivity, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onItemCLicked(Schedule schedule, int position) {
        Intent submitIntent = new Intent(mActivity, SubmitActivity.class);
        submitIntent.putExtra("schedule", schedule);
        mActivity.startActivity(submitIntent);
    }

    @Override
    public void callClient(Schedule schedule, int position) {
        mSelectedSchedule = schedule;
        String phone = "+" + mSelectedSchedule.getPhoneNumber();
        final Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone));
        if (ActivityCompat.checkSelfPermission(mActivity.getApplicationContext(),
                Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(mActivity, new String[]{Manifest.permission.CALL_PHONE}, PHONE_PERMISSION_REQUEST);
        } else {
            mActivity.startActivity(callIntent);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PHONE_PERMISSION_REQUEST:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    try {
                        String phone = "+" + mSelectedSchedule.getPhoneNumber();
                        Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone));
                        if (ActivityCompat.checkSelfPermission(mActivity.getApplicationContext(),
                                Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                            return;
                        } else {
                            mActivity.startActivity(callIntent);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
        }
    }

    @Override
    public void changeStatus(Schedule schedule, int position) {
        buildPeopleListDialog(schedule, position);
    }

    private void buildPeopleListDialog(Schedule schedule, int position) {
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(mActivity);
        builderSingle.setTitle(getString(R.string.select_status));

        ArrayAdapter<String> arrayAdapter = buildDialogList();

        builderSingle.setNegativeButton(
                getString(R.string.cancel),
                (dialog, which) -> dialog.dismiss());

        builderSingle.setAdapter(
                arrayAdapter,
                (dialog, which) -> {
                    Status status = STATUSES.get(which);
                    FastRApp.apiInterface().changeScheduleStatus(FastRApp.user().getProgramId(),
                            FastRApp.user().getUserId(), schedule.getBookDispatchId(),
                            status.getId()).subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(o -> {
                                schedule.setStatusId(status.getName().toUpperCase());
                                mAdapter.notifyItemChanged(position);
                            }, error -> {
                                Toast.makeText(mActivity, getString(R.string.cant_change_status), Toast.LENGTH_SHORT).show();
                            });
                });
        builderSingle.show();
    }

    private ArrayAdapter<String> buildDialogList() {
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(mActivity,
                android.R.layout.select_dialog_singlechoice);

        for (Status status : STATUSES) {
            arrayAdapter.add(status.getName());
        }
        return arrayAdapter;
    }

    @Override
    public void showRouteToClient(Schedule schedule, int position) {
        String uri = "geo:0,0?q=" + schedule.getFullAddress();
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
        mActivity.startActivity(intent);
    }

    @OnClick(R.id.todayButton)
    void getTodaySchedules() {
        mIsToday = true;
        getSchedulesFromServer(mToday);
    }

    @OnClick(R.id.pickDateButton)
    void pickANewDate() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(mActivity, this, mSelectedDate.getYear() + 1900,
                mSelectedDate.getMonth(), mSelectedDate.getDate());
        datePickerDialog.show();
    }

    @OnClick(R.id.logoutButton)
    void logout() {
        FastRApp.getInstance().setLoginStatus(false);
        FastRApp.getInstance().saveUserCredentials("", "");
        mActivity.startActivity(new Intent(mActivity, LoginActivity.class));
        mActivity.finish();
    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        Date date = new Date();
        date.setYear(i - 1900);
        date.setMonth(i1);
        date.setDate(i2);
        getSchedulesFromServer(date);
    }
}
