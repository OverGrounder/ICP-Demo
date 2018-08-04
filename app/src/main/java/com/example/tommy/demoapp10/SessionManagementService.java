package com.example.tommy.demoapp10;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.NonNull;

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
    /* The way of getting serverRef can be modified for later service scaling. */
    private DatabaseReference serverRef = databaseReference.child("stream_servers").child("server1");
    private DatabaseReference sessionRef;

    private Session currentSession = null;
    private boolean isSessionActive = false;
    private boolean isBroadcasting = false;

    public SessionListener sessionListener = null;


    public final int CREATE_FAIL = 0x0,
                    ALREADY_EXIST = 0x1;


    public interface SessionListener {
        void onSessionCreated(Session createdSession);
        void onSessionCreateFailed(int result_code, Session session);
        void onBroadcastAccepted();
        void onBroadcastDenied();
        void onSessionDestroyed();

        void onSessionConnected();
        void onSessionDisconnected();
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
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    public void createSession(final String sessionName) {
        if (isSessionActive && currentSession != null) {
            sessionListener.onSessionCreateFailed(ALREADY_EXIST, currentSession);
        }

        serverRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                StreamServer server = dataSnapshot.getValue(StreamServer.class);

                String sessionKey = databaseReference.child("session").push().getKey();
                assert sessionKey != null;
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

                isSessionActive = true;
                currentSession = session;
                sessionListener.onSessionCreated(session);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                sessionListener.onSessionCreateFailed(CREATE_FAIL, null);
            }
        });
    }

    public void requestBroadcast() {
        serverRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                StreamServer server = dataSnapshot.getValue(StreamServer.class);
                assert server != null;
                if (!server.in_use) {
                    serverRef.child("in_use").setValue("true");
                    isBroadcasting = true;
                    sessionListener.onBroadcastAccepted();
                } else {
                    sessionListener.onBroadcastDenied();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                sessionListener.onBroadcastDenied();
            }
        });
    }

    public void finishBroadcast() {
        serverRef.child("in_use").setValue("false");
    }

    public void endSession(Session session) {

    }

}
