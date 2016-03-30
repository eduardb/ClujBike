package com.deveddy.clujbike.data;

import dagger.Module;

@Module(includes = DataModule.class)
public class DebugDataModule {

    /**
     * Provides the Timber.Tree dependency used for logging
     *
     * @return The application's Timber.Tree
     */
    @Provides
    @Singleton
    Timber.Tree provideTimberTree() {
        /** A tree which logs important information for crash reporting. */
        return new Timber.Tree() {
            @Override
            protected void log(int priority, String tag, String message, Throwable t) {
                if (priority == Log.VERBOSE || priority == Log.DEBUG) {
                    return;
                }

//                Crashlytics.getInstance().core.log(priority, tag, message);
//
//                if (t != null) {
//                    if (priority == Log.ERROR) {
//                        Crashlytics.getInstance().core.logException(t);
//                    }
//                }
            }
        };
    }
}
