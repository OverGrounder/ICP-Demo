package com.example.tommy.demoapp10;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.VideoView;

public class StreamSubscribeFragment extends Fragment {

    public StreamSubscribeFragment(){};

    private VideoView videoView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){

        View view = inflater.inflate(R.layout.fragment_stream_subscribe, container, false);

        // Reference widget ID
        videoView = (VideoView)view.findViewById(R.id.stream_player);
        videoView.setVideoURI(Uri.parse("rtsp://13.209.74.49:1935/live/stream1"));
        videoView.start();

        return view;
    }
}
