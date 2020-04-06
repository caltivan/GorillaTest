package com.example.gorillatest.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.gorillatest.R;
import com.example.gorillatest.model.Item;
import com.example.gorillatest.viewmodel.ItemViewModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class ReceiptActivity extends AppCompatActivity {

    private Context mContext;
    private static final int RECEIPT_REQUEST_CODE = 1000;
    private static final String ORDER_EXTRAS = "orderExtras";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt);
        mContext = this;
        TextView totalTextView = findViewById(R.id.totalTextView);

        if (getIntent() != null) {

            String extras = getIntent().getStringExtra(ORDER_EXTRAS);
            Gson gson = new Gson();
            ArrayList<Item> items = gson.fromJson(extras, new TypeToken<ArrayList<Item>>() {
            }.getType());
            float totalValue = 0.00f;
            for (Item item : items) {
                totalValue += item.selection * Float.parseFloat(item.price.replace("$", ""));
            }
            totalTextView.setText(String.format("Total = $%.2f", totalValue));

        }
    }


    public void startOrderClick(View v) {
        setResult(RECEIPT_REQUEST_CODE);
        finish();
    }
}
