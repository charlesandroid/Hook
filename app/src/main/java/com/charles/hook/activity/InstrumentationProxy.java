package com.charles.hook.activity;

import android.app.Activity;
import android.app.Instrumentation;
import android.os.Bundle;
import android.util.Log;

import com.charles.hook.core.IProxy;
import com.charles.hook.core.ReflectUtils;

import java.lang.reflect.Method;

/**
 * com.charles.sample.main.InstrumentationProxy
 *
 * @author Just.T
 * @since 17/4/21
 */
public class InstrumentationProxy extends IProxy {
    private Object activityThread;
    private Instrumentation mBase;

    public InstrumentationProxy() {
        try {
            Class<?> aClass = Class.forName("android.app.ActivityThread");
            Method currentActivityThread = ReflectUtils.getMethod(aClass, "currentActivityThread");
            activityThread = ReflectUtils.invokeMethod(null, currentActivityThread);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean hook() {
        try {
            Object o = ReflectUtils.getObjectField(activityThread, "mInstrumentation");
            ReflectUtils.setObjectField(activityThread, "mInstrumentation", new InstrumentationProxy.Proxy((Instrumentation) o));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean revert() {
        return ReflectUtils.setObjectField(activityThread, "mInstrumentation", mBase);
    }

    private class Proxy extends Instrumentation {
        Proxy(Instrumentation instrumentation) {
            mBase = instrumentation;
        }

        @Override
        public void callActivityOnCreate(Activity activity, Bundle icicle) {
            Log.e("InstrumentationProxy", "Instrumentation Hook Success");
            try {
                Method callActivityOnCreate = ReflectUtils.getMethod(Instrumentation.class, "callActivityOnCreate", Activity.class, Bundle.class);
                ReflectUtils.invokeMethod(mBase, callActivityOnCreate, activity, icicle);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}

