package org.ka.arkady.aggregator;

import groovy.lang.Closure;
import groovy.transform.CompileStatic;

public class Case {

    private Closure<Boolean> condition;
    private Aggregator aggregator;

    public Case(Closure<Boolean> condition, Aggregator aggregator) {
        this.condition = condition;
        this.aggregator = aggregator;
    }

    public Closure<Boolean> getCondition() {
        return condition;
    }

    public Aggregator getAggregator() {
        return aggregator;
    }

    public void setAggregator(Aggregator aggregator) {
        this.aggregator = aggregator;
    }
}
