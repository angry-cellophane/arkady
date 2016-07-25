package org.ka.arkady.aggregator

import groovy.transform.CompileStatic

@CompileStatic
class Case {
    Closure<Boolean> condition
    Aggregator aggregator

    Case(Closure<Boolean> condition,Aggregator aggregator) {
        this.condition = condition
        this.aggregator = aggregator
    }
}
