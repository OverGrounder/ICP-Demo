package com.example.tommy.demoapp10;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class SessionManagementService extends Service {
    private final IBinder mBinder = new SessionManagementBinder();
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference =firebaseDatabase.getReference();
    /* Getting serverRef can be modified for later service scaling. */
    private DatabaseReference serverRef = databaseReference.child("stream_servers").child("server1");
    private DatabaseReference sessionRef;

    private Session currentSession;
    private boolean isBroadcasting = false;

    public SessionListener sessionListener = null;

    public class StreamServer {
        public String IP, app_names;
        boolean in_use, req_auth;
    }


    public interface SessionListener {
        public void onSessionCreated();
        public void onSessionCreateFailed();
        public void onStreamAccepted();
        public void onStreamDenied();
        public void onSessionDestroyed();

        public void onSessionConnected();
        public void onSessionDisconnected();
    }

    public void setSessionListener(SessionListener listener) {
        sessionListener = listener;
    }

    public class SessionManagementBinder extends Binder {
        SessionManagementService getService() {
            return SessionManagementService.this;
        }
    }

    public SessionManagementService() {

    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    public void createSession(final String sessionName) {

        serverRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                StreamServer server = dataSnapshot.getValue(StreamServer.class);

                String sessionKey = databaseReference.child("session").push().getKey();
                sessionRef = databaseReference.child("session").child(sessionKey);

                DateFormat df = new SimpleDateFormat("yyyyMMdd_HHmmssSSS", Locale.KOREA);
                String dateTime = df.format(Calendar.getInstance().getTime());

                Session session = new Session();
                session.setPORT_NUM("1935");
                session.setSTREAM_KEY(dateTime);
                session.setSESSION_NAME(sessionName);
                assert server != null;
                session.setAPPLICATION_NAME(server.app_names);
                session.setHOST_ADDRESS(server.IP);

                sessionRef.setValue(session);
                currentSession = session;
                sessionListener.onSessionCreated();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                sessionListener.onSessionCreateFailed();
            }
        });


    }

    public Session getCurrentSession() {
        return currentSession;
    }
}
