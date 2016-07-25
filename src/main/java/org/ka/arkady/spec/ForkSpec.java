package org.ka.arkady.spec;

import groovy.lang.Closure;
import groovy.lang.DelegatesTo;

public interface ForkSpec {
    BaseSpec fork(@DelegatesTo(value = BaseSpec.class, strategy = Closure.DELEGATE_FIRST) Closure body);
}
