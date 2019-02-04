package com.example.gigstartup.dtos;

import android.os.Parcel;
import android.os.Parcelable;

public class DtoUserInfo implements Parcelable {
    private int id;
    private String userImage;
    private String address;
    private double ratePerHour;
    private float rating;
    private String discription;
    private String skill;
    private String name;
    private String fromTime;
    private String toTime;
    private String status;
    private String prefernce;
    private String mobileNumber;
    private String emailId;
    private String rePassword;
    private String password;

    public DtoUserInfo() {}

    protected DtoUserInfo(Parcel in) {
        id = in.readInt();
        userImage = in.readString();
        address = in.readString();
        ratePerHour = in.readDouble();
        rating = in.readFloat();
        discription = in.readString();
        skill = in.readString();
        name = in.readString();
        fromTime = in.readString();
        toTime = in.readString();
        status = in.readString();
        prefernce = in.readString();
        mobileNumber = in.readString();
        emailId = in.readString();
        rePassword = in.readString();
        password = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(userImage);
        dest.writeString(address);
        dest.writeDouble(ratePerHour);
        dest.writeFloat(rating);
        dest.writeString(discription);
        dest.writeString(skill);
        dest.writeString(name);
        dest.writeString(fromTime);
        dest.writeString(toTime);
        dest.writeString(status);
        dest.writeString(prefernce);
        dest.writeString(mobileNumber);
        dest.writeString(emailId);
        dest.writeString(rePassword);
        dest.writeString(password);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<DtoUserInfo> CREATOR = new Creator<DtoUserInfo>() {
        @Override
        public DtoUserInfo createFromParcel(Parcel in) {
            return new DtoUserInfo(in);
        }

        @Override
        public DtoUserInfo[] newArray(int size) {
            return new DtoUserInfo[size];
        }
    };

    public String getRePassword() {
        return rePassword;
    }

    public void setRePassword(String rePassword) {
        this.rePassword = rePassword;
    }

    public String getPrefernce() {
        return prefernce;
    }

    public void setPrefernce(String prefernce) {
        this.prefernce = prefernce;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFromTime() {
        return fromTime;
    }

    public void setFromTime(String fromTime) {
        this.fromTime = fromTime;
    }

    public String getToTime() {
        return toTime;
    }

    public void setToTime(String toTime) {
        this.toTime = toTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setRatePerHour(double ratePerHour) {
        this.ratePerHour = ratePerHour;
    }

    public double getRatePerHour() {
        return ratePerHour;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getDiscription() {
        return discription;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
    }

    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
