package com.charles.hook;

/**
 * com.charles.hook.IProxy
 *
 * @author Just.T
 * @since 17/5/11
 */
public abstract class IProxy {
    boolean state;

    abstract boolean revert();

    abstract boolean hook();
}
