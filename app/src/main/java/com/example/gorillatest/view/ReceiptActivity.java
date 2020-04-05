package com.example.gorillatest.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.gorillatest.R;

public class ReceiptActivity extends AppCompatActivity {

    private Context mContext;
    private static final int RECEIPT_REQUEST_CODE = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt);
        mContext = this;
    }

    public void startOrderClick(View v) {
        setResult(RECEIPT_REQUEST_CODE);
        finish();
    }
}
