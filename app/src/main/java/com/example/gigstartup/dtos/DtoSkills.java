package com.example.gigstartup.dtos;

import android.os.Parcel;
import android.os.Parcelable;

public class DtoSkills implements Parcelable {
    private  int id;
    private String name;

    public  DtoSkills(){}
    protected DtoSkills(Parcel in) {
        id = in.readInt();
        name = in.readString();
    }

    public static final Creator<DtoSkills> CREATOR = new Creator<DtoSkills>() {
        @Override
        public DtoSkills createFromParcel(Parcel in) {
            return new DtoSkills(in);
        }

        @Override
        public DtoSkills[] newArray(int size) {
            return new DtoSkills[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
    }
}
