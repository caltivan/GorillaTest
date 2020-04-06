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
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gorillatest.model.Item;
import com.example.gorillatest.viewmodel.ItemViewModel;
import com.example.gorillatest.R;
import com.google.gson.Gson;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    private static final int RECEIPT_REQUEST_CODE = 1000;
    private static final String ORDER_EXTRAS = "orderExtras";
    private Context mContext;
    private RecyclerView itemsRecycleView;
    private ItemAdapter itemAdapter;
    private ItemViewModel viewModel;
    private Button placeOrderButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;

        setContentView(R.layout.activity_main);
        itemsRecycleView = findViewById(R.id.itemsRecycleView);
        placeOrderButton = findViewById(R.id.placeOrderButton);

        viewModel = ViewModelProviders.of(this).get(ItemViewModel.class);
        viewModel.loadItems();

        viewModel.items.observeForever(new Observer<ArrayList<Item>>() {
            @Override
            public void onChanged(ArrayList<Item> items) {
                int numberOfColumns = 2;
                itemsRecycleView.setLayoutManager(new GridLayoutManager(mContext, numberOfColumns));
                itemAdapter = new ItemAdapter(MainActivity.this);
                itemsRecycleView.setAdapter(itemAdapter);
                String buttonText = String.format(mContext.getString(R.string.order_button_text), String.valueOf(viewModel.getTotalOrders()));
                placeOrderButton.setText(buttonText);

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            viewModel.loadItems();
        }
    }


    public void onOrderClickAction(View v) {
        if (viewModel.getTotalOrders() > 0) {
            Intent intent = new Intent(mContext, ReceiptActivity.class);
            Gson gson = new Gson();
            intent.putExtra(ORDER_EXTRAS, gson.toJson(viewModel.getOrderItems()));
            startActivityForResult(intent, RECEIPT_REQUEST_CODE);
        } else {
            Toast.makeText(mContext, "ORDER IS EMPTY", Toast.LENGTH_SHORT).show();
        }
    }

}
