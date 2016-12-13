package com.softrangers.fastr.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

/**
 * Created by eduard on 10.12.16.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class Schedule implements Parcelable {
    @JsonProperty("bookingclaimid") private String mBookClaimId;
    @JsonProperty("bookingdispatchid") private String mBookDispatchId;
    @JsonProperty("firstname") private String mFirstName;
    @JsonProperty("lastname") private String mLastName;
    @JsonProperty("address1") private String mAddress1;
    @JsonProperty("address2") private String mAddress2;
    @JsonProperty("phoneno") private String mPhoneNumber;
    @JsonProperty("state") private String mState;
    @JsonProperty("city") private String mCity;
    @JsonProperty("zip") private String mZip;
    @JsonProperty("statusid") private String mStatusId;
    @JsonProperty("schedtime") private String mScheduleTime;
    @JsonProperty("time") private Date mTime;
    @JsonProperty("scheddate") private Date mScheduleDate;
    @JsonProperty("ftid") private String mFtId;
    @JsonProperty("ftfname") private String mFtFirstName;
    @JsonProperty("ftlname") private String mFtLastName;
    @JsonProperty("ftaction") private String mFtAction;
    @JsonProperty("ftreturnreason") private String mFtReturnReason;
    @JsonProperty("ftnote") private String mFtNote;
    @JsonProperty("qareviewer") private String mQareViewer;
    @JsonProperty("updateon") private Date mDate;
    @JsonProperty("gobackflag") private String mGoBackFlag;
    @JsonProperty("gobacknote") private String mGoBackNote;
    @JsonProperty("programid") private String mProgramId;
    @JsonProperty("programname") private String mProgramName;
    @JsonProperty("programeligibility") private String mProgramEligibility;
    @JsonProperty("precallaction") private String mPreCallaction;

    public Schedule() {

    }

    protected Schedule(Parcel in) {
        mBookClaimId = in.readString();
        mBookDispatchId = in.readString();
        mFirstName = in.readString();
        mLastName = in.readString();
        mAddress1 = in.readString();
        mAddress2 = in.readString();
        mPhoneNumber = in.readString();
        mState = in.readString();
        mCity = in.readString();
        mZip = in.readString();
        mStatusId = in.readString();
        mScheduleTime = in.readString();
        mFtId = in.readString();
        mFtFirstName = in.readString();
        mFtLastName = in.readString();
        mFtAction = in.readString();
        mFtReturnReason = in.readString();
        mFtNote = in.readString();
        mQareViewer = in.readString();
        mGoBackFlag = in.readString();
        mGoBackNote = in.readString();
        mProgramId = in.readString();
        mProgramName = in.readString();
        mProgramEligibility = in.readString();
        mPreCallaction = in.readString();
        mTime = (Date) in.readSerializable();
        mScheduleDate = (Date) in.readSerializable();
        mDate = (Date) in.readSerializable();
    }

    public static final Creator<Schedule> CREATOR = new Creator<Schedule>() {
        @Override
        public Schedule createFromParcel(Parcel in) {
            return new Schedule(in);
        }

        @Override
        public Schedule[] newArray(int size) {
            return new Schedule[size];
        }
    };

    public String getFtFullName() {
        return mFtFirstName + " " + mFtLastName;
    }

    public String getBookClaimId() {
        return mBookClaimId;
    }

    public String getBookDispatchId() {
        return mBookDispatchId;
    }

    public String getFirstName() {
        return mFirstName;
    }

    public String getLastName() {
        return mLastName;
    }

    public String getAddress1() {
        return mAddress1;
    }

    public String getAddress2() {
        return mAddress2;
    }

    public String getPhoneNumber() {
        return mPhoneNumber;
    }

    public String getState() {
        return mState;
    }

    public String getCity() {
        return mCity;
    }

    public String getZip() {
        return mZip;
    }

    public String getFullAddress() {
        return getAddress1() + ", " +
                getCity() + ", " +
                getZip() + ", " +
                getState();
    }

    public String getStatusId() {
        return mStatusId;
    }

    public void setStatusId(String statusId) {
        mStatusId = statusId;
    }

    public String getScheduleTime() {
        return mScheduleTime;
    }

    public Date getTime() {
        return mTime;
    }

    public Date getScheduleDate() {
        return mScheduleDate;
    }

    public String getFtId() {
        return mFtId;
    }

    public String getFtFirstName() {
        return mFtFirstName;
    }

    public String getFtLastName() {
        return mFtLastName;
    }

    public String getFtAction() {
        return mFtAction;
    }

    public String getFtReturnReason() {
        return mFtReturnReason;
    }

    public String getFtNote() {
        return mFtNote;
    }

    public String getQareViewer() {
        return mQareViewer;
    }

    public Date getDate() {
        return mDate;
    }

    public String getGoBackFlag() {
        return mGoBackFlag;
    }

    public String getGoBackNote() {
        return mGoBackNote;
    }

    public String getProgramId() {
        return mProgramId;
    }

    public String getProgramName() {
        return mProgramName;
    }

    public String getProgramEligibility() {
        return mProgramEligibility;
    }

    public String getPreCallaction() {
        return mPreCallaction;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mBookClaimId);
        parcel.writeString(mBookDispatchId);
        parcel.writeString(mFirstName);
        parcel.writeString(mLastName);
        parcel.writeString(mAddress1);
        parcel.writeString(mAddress2);
        parcel.writeString(mPhoneNumber);
        parcel.writeString(mState);
        parcel.writeString(mCity);
        parcel.writeString(mZip);
        parcel.writeString(mStatusId);
        parcel.writeString(mScheduleTime);
        parcel.writeString(mFtId);
        parcel.writeString(mFtFirstName);
        parcel.writeString(mFtLastName);
        parcel.writeString(mFtAction);
        parcel.writeString(mFtReturnReason);
        parcel.writeString(mFtNote);
        parcel.writeString(mQareViewer);
        parcel.writeString(mGoBackFlag);
        parcel.writeString(mGoBackNote);
        parcel.writeString(mProgramId);
        parcel.writeString(mProgramName);
        parcel.writeString(mProgramEligibility);
        parcel.writeString(mPreCallaction);
        parcel.writeSerializable(mTime);
        parcel.writeSerializable(mScheduleDate);
        parcel.writeSerializable(mDate);
    }
}
