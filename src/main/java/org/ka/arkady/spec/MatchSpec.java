package org.ka.arkady.spec;

import groovy.lang.Closure;
import groovy.lang.DelegatesTo;
import groovy.transform.stc.ClosureParams;
import groovy.transform.stc.SimpleType;

public interface MatchSpec {
    BaseSpec match(@ClosureParams(value = SimpleType.class, options = "org.ka.arkady.Food") Closure condition,
                   @DelegatesTo(value = MatchBodySpec.class, strategy = Closure.DELEGATE_FIRST) Closure body);
}
