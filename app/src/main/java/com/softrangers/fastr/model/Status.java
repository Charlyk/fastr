package com.softrangers.fastr.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by eduard on 11.12.16.
 */

public class Status implements Parcelable {
    private String mName;
    private String mId;

    public Status(String name, String id) {
        mName = name;
        mId = id;
    }

    protected Status(Parcel in) {
        mName = in.readString();
        mId = in.readString();
    }

    public static final Creator<Status> CREATOR = new Creator<Status>() {
        @Override
        public Status createFromParcel(Parcel in) {
            return new Status(in);
        }

        @Override
        public Status[] newArray(int size) {
            return new Status[size];
        }
    };

    public String getName() {
        return mName;
    }

    public String getId() {
        return mId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mName);
        parcel.writeString(mId);
    }
}
