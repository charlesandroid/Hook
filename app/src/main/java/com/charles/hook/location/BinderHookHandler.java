package com.charles.hook.location;

import android.annotation.TargetApi;
import android.location.Location;
import android.os.Build;
import android.os.IBinder;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class BinderHookHandler implements InvocationHandler {

    private static final String TAG = "BinderHookHandler";

    Object base;

    public BinderHookHandler(IBinder base, Class<?> stubClass) {
        try {
            Method asInterfaceMethod = stubClass.getDeclaredMethod("asInterface", IBinder.class);
            this.base = asInterfaceMethod.invoke(null, base);
        } catch (Exception e) {
            throw new RuntimeException("hooked failed!");
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (method.getName().equals("getLastLocation")) {
            Location location = new Location("mylocation");
            location.setLatitude(100000.0d);
            location.setLongitude(100.0d);
            return location;
        }
        return method.invoke(base, args);
    }
}