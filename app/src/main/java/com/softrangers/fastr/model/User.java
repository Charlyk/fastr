package com.softrangers.fastr.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by eduard on 11.12.16.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class User implements Parcelable {
    @JsonProperty("userid") private int mUserId;
    @JsonProperty("key") private String mKey;
    @JsonProperty("firstname") private String mFirstName;
    @JsonProperty("lastname") private String mLastName;
    @JsonProperty("email") private String mEmail;
    @JsonProperty("usertypeid") private String mUserTypeId;
    @JsonProperty("programid") private int mProgramId;
    @JsonProperty("programname") private String mProgramName;

    public User() {

    }

    protected User(Parcel in) {
        mUserId = in.readInt();
        mKey = in.readString();
        mFirstName = in.readString();
        mLastName = in.readString();
        mEmail = in.readString();
        mUserTypeId = in.readString();
        mProgramId = in.readInt();
        mProgramName = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public String getFullName() {
        return mFirstName + " " + mLastName;
    }

    public int getUserId() {
        return mUserId;
    }

    public String getKey() {
        return mKey;
    }

    public String getFirstName() {
        return mFirstName;
    }

    public String getLastName() {
        return mLastName;
    }

    public String getEmail() {
        return mEmail;
    }

    public String getUserTypeId() {
        return mUserTypeId;
    }

    public int getProgramId() {
        return mProgramId;
    }

    public String getProgramName() {
        return mProgramName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(mUserId);
        parcel.writeString(mKey);
        parcel.writeString(mFirstName);
        parcel.writeString(mLastName);
        parcel.writeString(mEmail);
        parcel.writeString(mUserTypeId);
        parcel.writeInt(mProgramId);
        parcel.writeString(mProgramName);
    }
}
