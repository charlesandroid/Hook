package com.charles.hook.location;

import android.os.IBinder;
import android.os.IInterface;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class BinderProxyHookHandler implements InvocationHandler {

    IBinder base;
    Class<?> stub;
    Class<?> iinterface;

    public BinderProxyHookHandler(IBinder base) {
        this.base = base;
        try {
            this.stub = Class.forName("android.location.ILocationManager$Stub");
            this.iinterface = Class.forName("android.location.ILocationManager");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if ("queryLocalInterface".equals(method.getName())) {
            return Proxy.newProxyInstance(proxy.getClass().getClassLoader(),
                    new Class[] { IBinder.class, IInterface.class, this.iinterface },
                    new BinderHookHandler(base, stub));
        }

        return method.invoke(base, args);
    }
}