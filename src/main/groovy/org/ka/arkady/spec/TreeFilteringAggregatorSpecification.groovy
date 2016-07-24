package org.ka.arkady.spec

import groovy.transform.CompileStatic
import groovy.transform.stc.ClosureParams
import groovy.transform.stc.SimpleType
import org.ka.arkady.aggregator.Aggregator

@CompileStatic
class TreeFilteringAggregatorSpecification implements BaseSpec, MatchBodySpec, AfterWhenSpec {

    @Override
    Aggregator newAggregator(Map<String, Object> params) {
        return null
    }

    @Override
    Aggregator aggregatorByName(String name) {
        return null
    }

    @Override
    Aggregator aggregator(Aggregator aggregator) {
        return null
    }

    @Override
    BaseSpec then(@DelegatesTo(value = BaseSpec, strategy = Closure.DELEGATE_FIRST) Closure body) {
        return null
    }

    @Override
    Aggregator getFails() {
        return null
    }

    @Override
    AfterWhenSpec when(@ClosureParams(value = SimpleType, options = 'org.ka.arkady.Food') Closure<Boolean> condition) {
        return null
    }

    @Override
    AfterWhenSpec getAll() {
        return null
    }

    @Override
    AfterWhenSpec when(Object assertion) {
        return null
    }

    @Override
    BaseSpec match(
            @ClosureParams(value = SimpleType, options = 'org.ka.arkady.Food') Closure condition,
            @DelegatesTo(value = MatchBodySpec, strategy = Closure.DELEGATE_FIRST) Closure body) {
        return null
    }

    @Override
    long now() {
        return System.currentTimeMillis()
    }
}
