package com.example.tommy.demoapp10;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class SellerSessionViewAdapter extends RecyclerView.Adapter<SellerSessionViewAdapter.ViewHolder> {
    private ArrayList<SellerSessionItem> mItems;
    Context mContext;

    public SellerSessionViewAdapter(ArrayList<SellerSessionItem> aItems, Context aContext){
        mItems = aItems;
        mContext = aContext;
    }

    // Must have Override
    @Override
    public int getItemCount(){
        return mItems.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){

        // Make a new View
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_seller_session, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position){
        holder.sessionImage.setImageBitmap(mItems.get(position).getSessionImage());
        holder.sessionDate.setText(mItems.get(position).getSessionDate());
        holder.sessionProductInfo.setText(mItems.get(position).getSessionProductInfo());
        holder.sessionTime.setText(mItems.get(position).getSessionTime());

        // Set Item Click Listener
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView sessionImage;
        public TextView sessionDate;
        public TextView sessionTime;
        public TextView sessionProductInfo;

        public ViewHolder(View view){
            super(view);
            sessionImage = (ImageView)view.findViewById(R.id.session_image_item);
            sessionDate = (TextView)view.findViewById(R.id.session_date_item);
            sessionTime = (TextView)view.findViewById(R.id.session_time_item);
            sessionProductInfo = (TextView)view.findViewById(R.id.session_product_info_item);
        }
    }
}