package com.charles.hook;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;

import com.charles.hook.location.BinderProxyHookHandler;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;

/**
 * com.charles.hook.MainActivity
 *
 * @author Just.T
 * @since 17/5/12
 */
public class MainActivity extends Activity {
    Object base;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hook();
                LocationManager systemService = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                Location lastKnownLocation = systemService.getLastKnownLocation("");
                double latitude = lastKnownLocation.getLatitude();
                double longitude = lastKnownLocation.getLongitude();
                Log.e("log", latitude + ";" + longitude);
            }
        });


    }

    private void hook() {
        try {
            Class<?> aClass = Class.forName("android.os.ServiceManager");
            Method declaredMethod = aClass.getDeclaredMethod("getService", String.class);
            declaredMethod.setAccessible(true);
            Object binderProxy = declaredMethod.invoke(null, "location");
            Object o1 = Proxy.newProxyInstance(LocationManager.class.getClassLoader(), new Class[]{IBinder.class}, new BinderProxyHookHandler((IBinder) binderProxy));
            Log.e("log", o1.getClass().getName());
            Field sCache = aClass.getDeclaredField("sCache");
            sCache.setAccessible(true);
            HashMap map = (HashMap) sCache.get(null);
            map.put("location", o1);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}
