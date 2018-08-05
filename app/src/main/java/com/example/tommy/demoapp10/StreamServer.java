package com.example.tommy.demoapp10;

import java.io.Serializable;

public class StreamServer implements Serializable {
    public String IP, app_names;
    boolean in_use, req_auth;

    StreamServer() {}
}