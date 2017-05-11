package com.charles.hook;

import android.app.Activity;
import android.app.Instrumentation;
import android.os.Bundle;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * com.charles.sample.main.InstrumentationProxy
 *
 * @author Just.T
 * @since 17/4/21
 */
public class InstrumentationProxy extends Instrumentation {

    private Instrumentation mBase;

    public InstrumentationProxy(Instrumentation instrumentation) {
        mBase = instrumentation;
    }

    @Override
    public void callActivityOnCreate(Activity activity, Bundle icicle) {
        System.out.println("activity oncreate!!!!!!!!!!!!!");
        try {
            Method callActivityOnCreate = Instrumentation.class.getDeclaredMethod(
                    "callActivityOnCreate",
                    Activity.class, Bundle.class);
            callActivityOnCreate.setAccessible(true);
            callActivityOnCreate.invoke(mBase, activity, icicle);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void init() {
        try {
            Class<?> aClass = Class.forName("android.app.ActivityThread");
            Method currentActivityThread = aClass.getDeclaredMethod("currentActivityThread");
            currentActivityThread.setAccessible(true);
            Object activityThread = currentActivityThread.invoke(null);
            Field mInstrumentation = aClass.getDeclaredField("mInstrumentation");
            mInstrumentation.setAccessible(true);
            Object o = mInstrumentation.get(activityThread);
            mInstrumentation.set(activityThread, new InstrumentationProxy((Instrumentation) o));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
