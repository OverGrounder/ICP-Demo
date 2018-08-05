package com.example.tommy.demoapp10;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class SellerSessionItem {
    private String sessionDate;
    private String sessionTime;
    private String sessionProductInfo;
    private Bitmap sessionImage;

    public Bitmap getSessionImage() {
        return sessionImage;
    }

    public String getSessionDate() {
        return sessionDate;
    }

    public String getSessionProductInfo() {
        return sessionProductInfo;
    }

    public String getSessionTime() {
        return sessionTime;
    }

    public SellerSessionItem(String sessionDate, String sessionTime,
                             String sessionProductInfo, Bitmap sessionImage){

        this.sessionDate = sessionDate;
        this.sessionTime = sessionTime;
        this.sessionImage = sessionImage;
        this.sessionProductInfo = sessionProductInfo;
    }
}



