package com.example.gigstartup.dtos;

import android.os.Parcel;
import android.os.Parcelable;

public class DtoNotification implements Parcelable {
    private int id;
    private String title;
    private String body;
    public DtoNotification(){}
    protected DtoNotification(Parcel in) {
        id = in.readInt();
        title = in.readString();
        body = in.readString();
    }

    public static final Creator<DtoNotification> CREATOR = new Creator<DtoNotification>() {
        @Override
        public DtoNotification createFromParcel(Parcel in) {
            return new DtoNotification(in);
        }

        @Override
        public DtoNotification[] newArray(int size) {
            return new DtoNotification[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(body);
    }
}
