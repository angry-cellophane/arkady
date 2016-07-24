package org.ka.arkady

import groovy.transform.CompileStatic
import groovy.transform.stc.ClosureParams
import groovy.transform.stc.FromString
import org.ka.arkady.aggregator.Aggregator
import org.ka.arkady.aggregator.Case
import org.ka.arkady.aggregator.FilteringAggregator
import org.ka.arkady.aggregator.FinalAggregator
import org.ka.arkady.spec.BaseSpec
import org.ka.arkady.spec.MatchBodySpec
import org.ka.arkady.spec.TreeFilteringAggregatorSpecification
import org.ka.arkady.tree.TreeFilteringAggregator
import org.ka.arkady.utils.ClosureUtils

@CompileStatic
class TreeFilteringAggregatorBuilder {

    final Case failsCase
    final Map<String, Aggregator> aggregatorByName

    TreeFilteringAggregatorBuilder() {
        failsCase = new Case(condition: ClosureUtils.TRUE, aggregator: new FinalAggregator('fails'))
        aggregatorByName = ['fails': failsCase.aggregator]
    }

    TreeFilteringAggregator newTree(@DelegatesTo(value = BaseSpec, strategy = Closure.DELEGATE_FIRST) Closure c) {
        def rootAgg = new FilteringAggregator()
        def spec = new TreeFilteringAggregatorSpecification(failsCase, rootAgg, aggregatorByName)

        c.setDelegate(spec)
        c.setResolveStrategy(Closure.DELEGATE_FIRST)
        c()

        rootAgg.cases << failsCase
        new TreeFilteringAggregator(rootAgg, spec.aggregatorByName.values().collect { (FinalAggregator)it })
    }

    FilteringAggregator newFilter(@DelegatesTo(value = BaseSpec, strategy = Closure.DELEGATE_FIRST) Closure c) {
        def rootAgg = new FilteringAggregator()
        def spec = new TreeFilteringAggregatorSpecification(failsCase, rootAgg, aggregatorByName)

        c.setDelegate(spec)
        c.setResolveStrategy(Closure.DELEGATE_FIRST)
        c()

        rootAgg.cases << failsCase
        rootAgg
    }
}
