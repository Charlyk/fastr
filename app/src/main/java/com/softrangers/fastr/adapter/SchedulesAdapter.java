package com.softrangers.fastr.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.softrangers.fastr.R;
import com.softrangers.fastr.model.Schedule;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by eduard on 10.12.16.
 */

public class SchedulesAdapter extends RecyclerView.Adapter<SchedulesAdapter.ViewHolder> {

    private ArrayList<Schedule> mSchedules;
    private OnItemInteractionListener mItemInteractionListener;

    public SchedulesAdapter(ArrayList<Schedule> schedules) {
        mSchedules = schedules;
    }

    public void setItemInteractionListener(OnItemInteractionListener itemInteractionListener) {
        mItemInteractionListener = itemInteractionListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.schedule_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mSchedule = mSchedules.get(position);
        holder.mClientName.setText(holder.mSchedule.getFtFullName());
        holder.mTime.setText(holder.mSchedule.getScheduleTime());
        holder.mAddress.setText(holder.mSchedule.getFullAddress());
        holder.mCallBtn.setText(holder.mSchedule.getPhoneNumber());
        holder.mStatusBtn.setText(holder.mSchedule.getStatusId());
    }

    @Override
    public int getItemCount() {
        return mSchedules.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.clientNameLabel) TextView mClientName;
        @BindView(R.id.timeLabel) TextView mTime;
        @BindView(R.id.addressLabel) TextView mAddress;
        @BindView(R.id.scheduleCallBtn) Button mCallBtn;
        @BindView(R.id.scheduleStatusBtn) Button mStatusBtn;
        Schedule mSchedule;
        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(view -> mItemInteractionListener.onItemCLicked(mSchedule, getAdapterPosition()));
            ButterKnife.bind(this, itemView);
        }

        @SuppressWarnings("unused")
        @OnClick({R.id.scheduleCallBtn, R.id.scheduleStatusBtn, R.id.scheduleMapItBtn})
        void clickedItemButtons(View view) {
            switch (view.getId()) {
                case R.id.scheduleCallBtn:
                    mItemInteractionListener.callClient(mSchedule, getAdapterPosition());
                    break;
                case R.id.scheduleStatusBtn:
                    mItemInteractionListener.changeStatus(mSchedule, getAdapterPosition());
                    break;
                case R.id.scheduleMapItBtn:
                    mItemInteractionListener.showRouteToClient(mSchedule, getAdapterPosition());
                    break;
            }
        }
    }

    public interface OnItemInteractionListener {
        void onItemCLicked(Schedule schedule, int position);
        void callClient(Schedule schedule, int position);
        void changeStatus(Schedule schedule, int position);
        void showRouteToClient(Schedule schedule, int position);
    }
}
