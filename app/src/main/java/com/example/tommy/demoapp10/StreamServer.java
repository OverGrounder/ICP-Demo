package com.example.tommy.demoapp10;

import android.os.Parcel;
import android.os.Parcelable;

public class StreamServer implements Parcelable {
    public String IP, app_names;
    boolean in_use, req_auth;

    StreamServer() {}

    public static final Creator<StreamServer> CREATOR = new Creator<StreamServer>() {
        @Override
        public StreamServer createFromParcel(Parcel in) {
            return new StreamServer(in);
        }

        @Override
        public StreamServer[] newArray(int size) {
            return new StreamServer[size];
        }
    };

    protected StreamServer(Parcel in) {
        IP = in.readString();
        app_names = in.readString();
        in_use = in.readByte() != 0;
        req_auth = in.readByte() != 0;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(IP);
        parcel.writeString(app_names);
        parcel.writeByte((byte) (in_use ? 1 : 0));
        parcel.writeByte((byte) (req_auth ? 1 : 0));
    }
}