package org.ka.arkady

import groovy.transform.CompileStatic
import groovy.transform.stc.ClosureParams
import groovy.transform.stc.FromString
import org.ka.arkady.aggregator.FilteringAggregator
import org.ka.arkady.spec.BaseSpec
import org.ka.arkady.spec.MatchBodySpec
import org.ka.arkady.spec.TreeFilteringAggregatorSpecification
import org.ka.arkady.tree.TreeFilteringAggregator

@CompileStatic
class TreeFilteringAggregatorBuilder<T> {

    private final def spec = new TreeFilteringAggregatorSpecification()

    TreeFilteringAggregator<T> newTree(@DelegatesTo(value = BaseSpec, strategy = Closure.DELEGATE_FIRST) Closure c) {
        new TreeFilteringAggregator<T>()
    }

    FilteringAggregator newFilter(@DelegatesTo(value = BaseSpec, strategy = Closure.DELEGATE_FIRST) Closure c) {
        c.setDelegate(spec)
        c.setResolveStrategy(Closure.DELEGATE_FIRST)
        c()
        new FilteringAggregator(c)
    }
}
