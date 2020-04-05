package com.example.gorillatest.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.gorillatest.model.Item;
import com.example.gorillatest.utils.MyAppExecutors;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

public class ItemViewModel extends AndroidViewModel {

    public MutableLiveData<ArrayList<Item>> items = new MutableLiveData<>();

    public ItemViewModel(@NonNull Application application) {
        super(application);
        items.setValue(new ArrayList<Item>());
    }

    public void loadItems() {
        MyAppExecutors.getInstance().networkThread.execute(new Runnable() {
            @Override
            public void run() {
                Gson gson = new Gson();
                String response = getItemsService();
                if(response != null){
                    ArrayList<Item> newItems = gson.fromJson(response, new TypeToken<ArrayList<Item>>() {
                    }.getType());
                    items.postValue(newItems);
                }
            }
        });
    }

    private String getItemsService() {
        URL url;
        HttpsURLConnection urlConnection = null;
        try {
            url = new URL("https://gl-endpoint.herokuapp.com/products");
            urlConnection = (HttpsURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");

            InputStream in = urlConnection.getInputStream();
            InputStreamReader isw = new InputStreamReader(in);

            BufferedReader br = new BufferedReader(isw);
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line + "\n");
            }
            br.close();
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        return null;
    }

}
