package com.example.tommy.demoapp10;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SessionManagementService extends Service {
    private final IBinder mBinder = new SessionManagementBinder();

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference =firebaseDatabase.getReference();
    /* The way of getting serverRef can be modified for later service scaling. */
    private DatabaseReference serverRef = databaseReference.child("stream_servers").child("server1");
    private DatabaseReference sessionListRef = databaseReference.child("active_sessions");
    private DatabaseReference sessionRef, userRef = null, pendingSessionsRef = null, prevSessionsRef = null, activeSessionsRef = null;

    private SessionList pendingSessions = null;
    private SessionList prevSessions = null;
    private SessionList activeSessions = null;
    private Session currentSession = null;
    private boolean isSessionActive = false;
    private boolean isBroadcasting = false;

    public SessionListener sessionListener = null;


    public final int CREATE_FAIL = 0x0,
            ALREADY_EXIST = 0x1;

    @Override
    public IBinder onBind(Intent intent) {
        if (userRef == null) {
            userRef = databaseReference.child("users").child(mAuth.getCurrentUser().getUid());
        }
        if (pendingSessions == null) {
            Log.d("ICP_SMS", "Getting pendingSessions");
            pendingSessionsRef = userRef.child("pending_sessions");
            pendingSessionsRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    pendingSessions = dataSnapshot.getValue(SessionList.class);
                    if (pendingSessions == null) {
                        pendingSessions = new SessionList();
                    }
                    pendingSessionsRef.setValue(pendingSessions);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // TODO: 에러 처리
                }
            });
        }
        if (prevSessions == null) {
            prevSessionsRef = userRef.child("prev_sessions");
            prevSessionsRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    prevSessions = dataSnapshot.getValue(SessionList.class);
                    if (prevSessions == null) {
                        prevSessions = new SessionList();
                    }
                    prevSessionsRef.setValue(prevSessions);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // TODO: 에러 처리
                }
            });
        }
        if (activeSessions == null) {
            activeSessionsRef = databaseReference.child("active_sessions");
            activeSessionsRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    activeSessions = dataSnapshot.getValue(SessionList.class);
                    if (activeSessions == null) activeSessions = new SessionList();
                    activeSessionsRef.setValue(activeSessions);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        return mBinder;
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

    public void createSession(final String sessionName) {
        if (isSessionActive && currentSession != null) {
            sessionListener.onSessionCreateFailed(ALREADY_EXIST, currentSession);
        }

        serverRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                /*
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

                pendingSessions.addToSessionArrayList(currentSession.getSESSIONID());
                pendingSessionsRef.setValue(pendingSessions);

                sessionListener.onSessionCreated(session); */
                DatabaseReference sessionRef2 = databaseReference.child("session").child("-LJAHYA32TLRuNKkoO23");
                sessionRef2.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        currentSession = dataSnapshot.getValue(Session.class);
                        sessionListener.onSessionCreated(currentSession);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                sessionListener.onSessionCreateFailed(CREATE_FAIL, null);
            }
        });
    }

    public void requestBroadcast() {
        if (currentSession == null) return;
        serverRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                StreamServer server = dataSnapshot.getValue(StreamServer.class);
                assert server != null;
                if (!server.in_use) {
                    serverRef.child("in_use").setValue(true);
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
        SessionList activeSessions = new SessionList();
        activeSessions.addToSessionArrayList(currentSession.getSESSIONID());
        sessionListRef.setValue(activeSessions);
        isBroadcasting = true;
    }

    public void finishBroadcast() {
        serverRef.child("in_use").setValue(false);
        sessionListRef.setValue(new ArrayList<Session>());
        isBroadcasting = false;
    }

    public SessionList getPendingSessions() {
        return pendingSessions;
    }

    public void endSession(Session session) {

    }

    public interface SessionListener {
        void onSessionCreated(Session createdSession);

        void onSessionCreateFailed(int result_code, Session session);

        void onBroadcastAccepted();

        void onBroadcastDenied();

        void onSessionDestroyed();

        void onBindFinished();

        void onSessionConnected();

        void onSessionDisconnected();
    }

}