package com.example.gorillatest.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gorillatest.R;
import com.example.gorillatest.model.Item;
import com.example.gorillatest.viewmodel.ItemViewModel;

import java.util.ArrayList;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {

    private final ItemViewModel viewModel;
    private final Context mContext;
    private ArrayList<Item> mData;
    private LayoutInflater mInflater;

    // data is passed into the constructor
    ItemAdapter(MainActivity context) {
        mContext = context;
        this.mInflater = LayoutInflater.from(context);
        viewModel = ViewModelProviders.of(context).get(ItemViewModel.class);
        this.mData = viewModel.items.getValue();
    }

    // inflates the cell layout from xml when needed
    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_grid_layout, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each cell
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        String name = String.format("%s %s", mData.get(position).name1, mData.get(position).name2);
        holder.mItemNameTextView.setText(name);
        holder.mItemPriceTextView.setText(mData.get(position).price);
        if (mData.get(position).selection != 0) {
            holder.itemLayout.setBackground(ContextCompat.getDrawable(mContext, R.drawable.grid_select_background));
            holder.mCounterTextView.setVisibility(View.VISIBLE);
            holder.mCounterTextView.setText(String.valueOf(mData.get(position).selection));

        } else {
            holder.itemLayout.setBackground(ContextCompat.getDrawable(mContext, R.drawable.grid_background));
            holder.mCounterTextView.setVisibility(View.GONE);

        }
        holder.itemLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mData.get(position).selection++;
                if (mData.get(position).selection > 2) {
                    mData.get(position).selection = 0;
                }
                viewModel.items.setValue(mData);
                Log.i("onItemClick", "onItemClick selection = " + mData.get(position).selection);
                Log.i("onItemClick", "onItemClick  which is at cell position " + position);
            }
        });

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
        TextView mCounterTextView;

        LinearLayout itemLayout;

        ViewHolder(View itemView) {
            super(itemView);
            mItemNameTextView = itemView.findViewById(R.id.itemNameTextView);
            mItemPriceTextView = itemView.findViewById(R.id.priceTextView);
            itemLayout = itemView.findViewById(R.id.itemLayout);
            mCounterTextView = itemView.findViewById(R.id.countTextView);
        }

    }

}
