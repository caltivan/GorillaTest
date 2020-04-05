package com.example.gorillatest.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.gorillatest.model.Item;
import com.example.gorillatest.viewmodel.ItemViewModel;
import com.example.gorillatest.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final int RECEIPT_REQUEST_CODE = 1000;
    private Context mContext;
    private RecyclerView itemsRecycleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;

        setContentView(R.layout.activity_main);
        itemsRecycleView = findViewById(R.id.itemsRecycleView);

        final ItemViewModel viewModel = ViewModelProviders.of(this).get(ItemViewModel.class);
        viewModel.loadItems();

        viewModel.items.observeForever(new Observer<ArrayList<Item>>() {
            @Override
            public void onChanged(ArrayList<Item> items) {
                int numberOfColumns = 2;
                itemsRecycleView.setLayoutManager(new GridLayoutManager(mContext, numberOfColumns));
                ItemAdapter itemAdapter = new ItemAdapter(mContext, viewModel.items.getValue());
                itemsRecycleView.setAdapter(itemAdapter);

            }
        });

    }

    public  void onOrderClickAction(View v) {
        Intent intent = new Intent(mContext, ReceiptActivity.class);
        startActivityForResult(intent,RECEIPT_REQUEST_CODE);

    }

}
