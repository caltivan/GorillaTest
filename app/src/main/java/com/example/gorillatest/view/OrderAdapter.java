package com.example.gorillatest.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gorillatest.R;
import com.example.gorillatest.model.Item;

import java.util.ArrayList;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {

    private final Context mContext;
    private ArrayList<Item> mData;
    private LayoutInflater mInflater;

    // data is passed into the constructor
    OrderAdapter(Context context, ArrayList<Item> data) {
        mContext = context;
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    // inflates the cell layout from xml when needed
    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.order_item_layout, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each cell
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        String name = String.format("%s %s", mData.get(position).name1, mData.get(position).name2);
        if (mData.get(position).selection > 1) {
            name = String.format("%s %s (%s)", mData.get(position).name1, mData.get(position).name2, mData.get(position).selection);
        }
        holder.mItemNameTextView.setText(name);
        holder.mItemPriceTextView.setText(mData.get(position).price);
    }

    // total number of cells
    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView mItemNameTextView;
        TextView mItemPriceTextView;


        ViewHolder(View itemView) {
            super(itemView);
            mItemNameTextView = itemView.findViewById(R.id.itemName);
            mItemPriceTextView = itemView.findViewById(R.id.itemPrice);
        }

    }

}
