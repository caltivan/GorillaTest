package com.example.gorillatest.utils;

import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Helper class that handles and manage the different threads execution
 */
public class MyAppExecutors {

    private static final Object LOCK = new Object();
    private static final int THREAD_COUNT = 3;
    private static MyAppExecutors sInstance;

    public final Executor mainThread;
    public final Executor networkThread;

    /**
     * Constructor
     *
     * @param networkThread     executor on charge of the access to the external storage
     * @param mainThread executor on charge to notify to the ui thread
     */
    private MyAppExecutors(Executor networkThread, Executor mainThread) {
        this.networkThread = networkThread;
        this.mainThread = mainThread;
    }

    public static MyAppExecutors getInstance() {
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = new MyAppExecutors(Executors.newFixedThreadPool(THREAD_COUNT), new MainThreadExecutor());
            }
        }
        return sInstance;
    }


    private static class MainThreadExecutor implements Executor {
        private Handler mainThreadHandler = new Handler(Looper.getMainLooper());

        @Override
        public void execute(@NonNull Runnable command) {
            mainThreadHandler.post(command);
        }
    }

}