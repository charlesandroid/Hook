package com.charles.hook.core;

/**
 * com.charles.hook.core.IProxy
 *
 * @author Just.T
 * @since 17/5/11
 */
public abstract class IProxy {
    boolean state;

    protected abstract boolean revert();

    public abstract boolean hook();
}
