package com.charles.hook;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * com.charles.hook.ReflectUtils
 *
 * @author Just.T
 * @since 17/5/11
 */
public class ReflectUtils {

    public static Object invokeMethod(Object o, Method method, Object... args) {
        if (method == null) return false;
        try {
            method.setAccessible(true);
            return method.invoke(o, args);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Method getMethod(Class<?> clz, String name, Class<?>... args) {
        if (clz == null) return null;
        Method method = null;
        try {
            method = clz.getDeclaredMethod(name, args);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return method;
    }

    public static Method getMethod(Object o, String name, Class<?>... args) {
        return getMethod(o.getClass(), name, args);
    }

    public static Object getStaticField(Class<?> clz, String name) {
        Object object = null;
        try {
            Field declaredField = clz.getDeclaredField(name);
            object = declaredField.get(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return object;
    }


    public static Object getObjectField(Object o, String fieldName) {
        Object object = null;
        try {
            Class<?> aClass = o.getClass();
            Field field = aClass.getDeclaredField(fieldName);
            field.setAccessible(true);
            object = field.get(o);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return object;
    }

    public static boolean setObjectField(Object o, String fieldName, Object value) {
        try {
            Class<?> aClass = o.getClass();
            Field field = aClass.getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(o, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
