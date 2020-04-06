package com.example.gorillatest.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.gorillatest.model.Item;
import com.example.gorillatest.viewmodel.ItemViewModel;
import com.example.gorillatest.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ItemAdapter.ItemClickListener {


    private static final int RECEIPT_REQUEST_CODE = 1000;
    private Context mContext;
    private RecyclerView itemsRecycleView;
    private ItemAdapter itemAdapter;
    private ItemViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;

        setContentView(R.layout.activity_main);
        itemsRecycleView = findViewById(R.id.itemsRecycleView);

        viewModel = ViewModelProviders.of(this).get(ItemViewModel.class);
        viewModel.loadItems();

        viewModel.items.observeForever(new Observer<ArrayList<Item>>() {
            @Override
            public void onChanged(ArrayList<Item> items) {
                int numberOfColumns = 2;
                itemsRecycleView.setLayoutManager(new GridLayoutManager(mContext, numberOfColumns));
                itemAdapter = new ItemAdapter(mContext, viewModel.items.getValue());
                itemAdapter.setClickListener(MainActivity.this);
                itemsRecycleView.setAdapter(itemAdapter);
            }
        });
    }

    public void onOrderClickAction(View v) {
        Intent intent = new Intent(mContext, ReceiptActivity.class);
        startActivityForResult(intent, RECEIPT_REQUEST_CODE);

    }

    @Override
    public void onItemClick(View view, LinearLayout layout, int position) {
       // Log.i("TAG", "onItemClick " + itemAdapter.getItem(position) + ", which is at cell position " + position);
        ArrayList<Item> items = viewModel.items.getValue();
        Item item = items.get(position);
        item.selection++;
        if (item.selection > 2) {
            item.selection = 0;
            //layout.setBackgroundColor(Color.WHITE);
        } else {
            layout.setBackgroundColor(Color.RED);
        }
        Log.i("onItemClick", "onItemClick selection = " + item.selection);
        viewModel.items.postValue(items);
    }
}
