package com.example.tommy.demoapp10;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class CustomerMainFragment extends Fragment {
    private String USER_EMAIL;
    private String USER_NAME;

    //Activity
    Activity mActivity;

    // UI referencers

    SearchView searchView;
    TextView liveTextView;
    ImageView sessionThumbNailView;

    // Seller List RecyclerView;
    RecyclerView sellerListView;

    // Seller Session List RecyclerView
    RecyclerView sellerSessionListView;
    RecyclerView.Adapter<SellerSessionViewAdapter.ViewHolder> sellerSessionViewAdapter;

    // Session Product List RecyclerView
    RecyclerView sessionProductListView;


    // RecyclerView LayoutManager
    RecyclerView.LayoutManager layoutManager;

    // Firebase
    private FirebaseStorage firebaseStorage = FirebaseStorage.getInstance("gs://fir-app-21034.appspot.com");
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private StorageReference storageReference = firebaseStorage.getReference();
    private DatabaseReference databaseReference =firebaseDatabase.getReference(), userRef;

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private User user;

    private Session activeSession;

    public CustomerMainFragment(){};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){

        View view = inflater.inflate(R.layout.fragment_customer_main, container, false);

        //Get Activity
        mActivity = getActivity();

        sellerListView = (RecyclerView)view.findViewById(R.id.seller_list);
        sellerSessionListView = (RecyclerView)view.findViewById(R.id.seller_session_list);
        sessionProductListView = (RecyclerView)view.findViewById(R.id.session_product_list);
        sessionThumbNailView = (ImageView)view.findViewById(R.id.session_thumbnail);

        // Set ArrayLists
        ArrayList<SellerSessionItem> sellerSessionItems = new ArrayList<>();

        final FirebaseUser firebaseUser = mAuth.getCurrentUser();

        userRef = databaseReference.child("users").child(firebaseUser.getUid());
        Log.d("ICP_SignIn", "UID: " + userRef.getKey());
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                user = dataSnapshot.getValue(User.class);
                assert user != null;
                USER_NAME = user.getNAME();
                USER_EMAIL = firebaseUser.getEmail();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        databaseReference.child("session").child("-LJAHYA32TLRuNKkoO23").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                activeSession = dataSnapshot.getValue(Session.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        // Set session thumbnail onclicklistener
        sessionThumbNailView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(activeSession==null)return;
                Intent cisIntent = new Intent(getActivity(), CustomerInSessionActivity.class);
                cisIntent.putExtra("url",activeSession.getUrl());
                cisIntent.putExtra("session_name",activeSession.getSESSION_NAME());
                cisIntent.putExtra("user_name",USER_NAME);
                cisIntent.putExtra( "user_email", USER_EMAIL);
                startActivity(cisIntent);
            }
        });

        return view;
    }
}
