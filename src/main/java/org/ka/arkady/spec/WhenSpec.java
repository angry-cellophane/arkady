package org.ka.arkady.spec;

import groovy.lang.Closure;
import groovy.transform.stc.ClosureParams;
import groovy.transform.stc.SimpleType;

public interface WhenSpec {
    AfterWhenSpec when(@ClosureParams(value = SimpleType.class, options = "org.ka.arkady.Food") Closure<Boolean> condition);
    Closure<Boolean> getAll();
}
