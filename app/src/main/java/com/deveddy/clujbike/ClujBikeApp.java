package com.deveddy.clujbike;

import com.deveddy.clujbike.di.AppComponent;
import com.deveddy.clujbike.di.AppModule;
import com.deveddy.clujbike.di.DaggerAppComponent;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;

import javax.inject.Inject;

import timber.log.Timber;

public class ClujBikeApp extends Application {

    @Inject
    Timber.Tree mTimberTree;

    private AppComponent mAppComponent;

    private RefWatcher mRefWatcher;

    @Override
    public void onCreate() {
        super.onCreate();

        mRefWatcher = LeakCanary.install(this);

        if (mRefWatcher == RefWatcher.DISABLED && "leak".equals(BuildConfig.BUILD_TYPE)) {
            return;
        }

        // build the dependency graph
        mAppComponent = getAppComponentBuilder().build();
        mAppComponent.inject(this);

        Timber.plant(mTimberTree);
    }

    @NonNull
    @VisibleForTesting
    public DaggerAppComponent.Builder getAppComponentBuilder() {
        return DaggerAppComponent.builder().appModule(new AppModule(this));
    }

    /**
     * @return The application class from a given context
     */
    public static ClujBikeApp get(Context context) {
        return ((ClujBikeApp) context.getApplicationContext());
    }

    public static RefWatcher getRefWatcher(Context context) {
        return get(context).mRefWatcher;
    }
}
