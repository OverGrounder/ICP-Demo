package com.example.tommy.demoapp10;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class PendingSessionViewAdapter extends RecyclerView.Adapter<PendingSessionViewAdapter.ViewHolder> {
    Context mContext;
    private SessionList mItems;

    public PendingSessionViewAdapter(SessionList mItems, Context mContext) {
        this.mItems = mItems;
        this.mContext = mContext;
    }

    // Must have Override
    @Override
    public int getItemCount() {
        if (mItems != null) {
            return mItems.sessionArrayList.size();
        } else {
            return 0;
        }
    }

    @Override
    public PendingSessionViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        // Make a new View
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_seller_session, parent, false);
        PendingSessionViewAdapter.ViewHolder holder = new PendingSessionViewAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull PendingSessionViewAdapter.ViewHolder holder, int position) {
        holder.sessionName.setText(mItems.sessionArrayList.get(position));

        // Set Item Click Listener
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView sessionName;

        public ViewHolder(View view) {
            super(view);
            sessionName = view.findViewById(R.id.session_date_item);
        }
    }
}
