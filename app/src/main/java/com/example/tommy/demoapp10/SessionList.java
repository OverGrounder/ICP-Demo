package com.example.tommy.demoapp10;

import java.util.ArrayList;

public class SessionList {
    ArrayList<String> sessionArrayList = new ArrayList<>();

    public SessionList() {
        sessionArrayList.add("dummy");
    }

    public ArrayList<String> getSessionArrayList() {
        return this.sessionArrayList;
    }

    public void setSessionArrayList(ArrayList<String> sessionArrayList) {
        this.sessionArrayList = sessionArrayList;
    }

    public void concatenateSessionArrayList(ArrayList<String> sessionArrayList) {
        this.sessionArrayList.addAll(sessionArrayList);
    }

    public void addToSessionArrayList(String sessionID) {
        this.sessionArrayList.add(sessionID);
    }
}
