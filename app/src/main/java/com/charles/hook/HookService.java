package com.charles.hook;

import java.util.HashMap;
import java.util.Map;

/**
 * com.charles.hook.HookService
 *
 * @author Just.T
 * @since 17/5/11
 */
public class HookService {

    private static Map<Class<?>, IProxy> proxies = new HashMap<>();

    static {
        proxies.put(InstrumentationProxy.class, new InstrumentationProxy());
    }

    private HookService() {
    }

    public static void hook(Class clz) {
        IProxy iProxy = proxies.get(clz);
        if (iProxy.state) return;
        if (iProxy.hook()) {
            iProxy.state = true;
        }
    }

    public static void revert(Class clz) {
        IProxy iProxy = proxies.get(clz);
        if (!iProxy.state) return;
        if (iProxy.revert()) {
            iProxy.state = false;
        }
    }
}
