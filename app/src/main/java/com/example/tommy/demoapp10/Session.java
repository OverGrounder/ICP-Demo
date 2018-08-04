package com.example.tommy.demoapp10;

import android.os.Parcel;
import android.os.Parcelable;

public class Session implements Parcelable {
    private String port_NUM;
    private String session_NAME;
    private String stream_KEY;
    private String host_ADDRESS;
    private String application_NAME;
    public static final Creator<Session> CREATOR = new Creator<Session>() {
        @Override
        public Session createFromParcel(Parcel in) {
            return new Session(in);
        }

        @Override
        public Session[] newArray(int size) {
            return new Session[size];
        }
    };
    private String SessionID;
    public Session (String port_num, String session_name, String stream_name, String host_address,
                    String application_name) {

        this.application_NAME = application_name;
        this.session_NAME = session_name;
        this.host_ADDRESS = host_address;
        this.port_NUM = port_num;
        this.stream_KEY = stream_name;

    }

    Session() {
    }

    private Session(Parcel parcel) {
        this.port_NUM = parcel.readString();
        this.session_NAME = parcel.readString();
        this.stream_KEY = parcel.readString();
        this.host_ADDRESS = parcel.readString();
        this.application_NAME = parcel.readString();
        this.SessionID = parcel.readString();
    }

    public void setPORT_NUM(String port_NUM) { this.port_NUM = port_NUM; }
    public void setSESSION_NAME(String chat_NAME) { this.session_NAME = chat_NAME; }
    public void setHOST_ADDRESS(String host_ADDRESS) { this.host_ADDRESS=host_ADDRESS; }
    public void setSTREAM_KEY(String stream_NAME) { this.stream_KEY =stream_NAME; }
    public void setAPPLICATION_NAME(String application_NAME) { this.application_NAME = application_NAME; }

    public String getPORT_NUM() {
        return port_NUM;
    }
    public String getSTREAM_KEY() {
        return stream_KEY;
    }
    public String getAPPLICATION_NAME() {
        return application_NAME;
    }
    public String getHOST_ADDRESS() {
        return host_ADDRESS;
    }
    public String getSESSION_NAME() {
        return session_NAME;
    }

    public String getUrl() { return "rtmp://" + host_ADDRESS + ":" + port_NUM + "/" + application_NAME + "/" + stream_KEY; }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.port_NUM);
        parcel.writeString(this.stream_KEY);
        parcel.writeString(this.application_NAME);
        parcel.writeString(this.host_ADDRESS);
        parcel.writeString(this.session_NAME);
    }
}
