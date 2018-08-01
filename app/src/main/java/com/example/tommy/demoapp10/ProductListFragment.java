package com.example.tommy.demoapp10;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class ProductListFragment extends Fragment {
    private ListView productListView;
    //private StreamSubscribeFragment ssf;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){

        View view = inflater.inflate(R.layout.fragment_product_list, container, false);

        productListView = view.findViewById(R.id.product_list);

        return view;
    }
}
