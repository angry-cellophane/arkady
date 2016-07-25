package org.ka.arkady.utils;

import groovy.lang.Closure;

/**
 * Created by Александр on 26.07.2016.
 */
class ConstantClosure<T> extends Closure<T> {

    final T value;

    public ConstantClosure(Object owner, Object thisObject, T value) {
        super(owner, thisObject);
        this.value = value;
    }

    @Override
    public T call() {
        return value;
    }

    @Override
    public T call(Object... args) {
        return value;
    }

    @Override
    public T call(Object arguments) {
        return value;
    }
}
