package com.dhiman_da.leaky;

import android.app.Application;
import android.content.Context;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

/**
 * Created by dhiman_da on 7/19/2015.
 */
public class LeakyApplication extends Application {
    private RefWatcher sRefWatcher;

    public static RefWatcher getRefWatcher(final Context context) {
        LeakyApplication application = (LeakyApplication) context.getApplicationContext();
        return application.sRefWatcher;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        sRefWatcher = LeakCanary.install(this);
    }
}
