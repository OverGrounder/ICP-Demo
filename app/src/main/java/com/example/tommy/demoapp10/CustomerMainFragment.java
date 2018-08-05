package com.example.tommy.demoapp10;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class CustomerMainFragment extends Fragment {
    private String userEmail;
    private String userPassword;

    //Activity
    Activity mActivity;

    // Context
    Context mContext;

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
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference =firebaseDatabase.getReference();

    public CustomerMainFragment(){};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){

        View view = inflater.inflate(R.layout.fragment_customer_main, container, false);

        //Get Activity
        mActivity = getActivity();

        // Get context
        mContext = mActivity.getApplicationContext();

        sellerListView = (RecyclerView)view.findViewById(R.id.seller_list);
        sellerSessionListView = (RecyclerView)view.findViewById(R.id.seller_session_list);
        sessionProductListView = (RecyclerView)view.findViewById(R.id.session_product_list);

        // Set ArrayLists
        ArrayList<SellerSessionItem> sellerSessionItems = new ArrayList<>();




        return view;
    }
}
