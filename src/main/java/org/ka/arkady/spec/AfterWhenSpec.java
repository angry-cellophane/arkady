package org.ka.arkady.spec;

import groovy.lang.Closure;
import groovy.lang.DelegatesTo;
import org.ka.arkady.aggregator.Aggregator;

public interface AfterWhenSpec {
    BaseSpec aggregateBy(Aggregator aggregator);
    BaseSpec then(@DelegatesTo(value = BaseSpec.class, strategy = Closure.DELEGATE_FIRST) Closure body);
    BaseSpec forks(@DelegatesTo(value = ForkSpec.class, strategy = Closure.DELEGATE_FIRST) Closure body);
}
