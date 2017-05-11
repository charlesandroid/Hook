package com.charles.hook;

import android.app.Application;

/**
 * com.charles.hook.HookApplication
 *
 * @author Just.T
 * @since 17/5/11
 */
public class HookApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        InstrumentationProxy.init();
    }
}
