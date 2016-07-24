package org.ka.arkady.spec

import groovy.transform.CompileStatic
import groovy.transform.stc.ClosureParams
import groovy.transform.stc.SimpleType
import org.ka.arkady.aggregator.Aggregator
import org.ka.arkady.aggregator.Case
import org.ka.arkady.aggregator.FilteringAggregator
import org.ka.arkady.aggregator.FinalAggregator
import org.ka.arkady.utils.ClosureUtils

@CompileStatic
class TreeFilteringAggregatorSpecification implements BaseSpec, MatchBodySpec, AfterWhenSpec {

    final List<FilteringAggregator> stack
    final Case failsCase
    final Map<String, Aggregator> aggregatorByName

    private final List<Closure> matchConditionsStack

    TreeFilteringAggregatorSpecification(Case failsCase, FilteringAggregator rootAgg, Map<String, Aggregator> aggregatorByName) {
        this.failsCase = failsCase
        this.stack = [rootAgg]
        this.aggregatorByName = aggregatorByName
        this.matchConditionsStack = []
    }

    @Override
    Aggregator newAggregator(Map<String, Object> params) {
        def name = params['name'].toString()
        if (name in aggregatorByName) throw new RuntimeException("Aggregator ${name} already exists")

        def agg = new FinalAggregator(name)
        aggregatorByName[name] = agg
        return agg
    }

    @Override
    Aggregator findByName(String aggregatorName) {
        def agg = aggregatorByName[aggregatorName]
        if (agg == null) throw new RuntimeException("No aggregator with name ${aggregatorName} found. You can define a new with the newAggregator method")

        return agg
    }

    @Override
    BaseSpec aggregateBy(Aggregator aggregator) {
        stack.last().cases.last().aggregator = aggregator
        return this
    }

    @Override
    BaseSpec then(@DelegatesTo(value = BaseSpec, strategy = Closure.DELEGATE_FIRST) Closure body) {
        Closure c = adjustDelegate(body)
        stack << new FilteringAggregator()
        c()
        def agg = stack.pop()
        agg.cases << failsCase
        stack.last().cases.last().aggregator = agg
        return this
    }

    @Override
    Aggregator getFails() {
        return aggregatorByName['fails']
    }

    @Override
    AfterWhenSpec when(@ClosureParams(value = SimpleType, options = 'org.ka.arkady.Food') Closure<Boolean> condition) {
        stack.last().cases << new Case(condition: condition)
        return this
    }

    @Override
    Closure<Boolean> getAll() {
        return ClosureUtils.TRUE
    }

    @Override
    AfterWhenSpec when(Object assertion) {
        def c = matchConditionsStack.last()
        stack.last().cases << new Case(condition: { c(it) == assertion })
        return this
    }

    @Override
    BaseSpec match(
            @ClosureParams(value = SimpleType, options = 'org.ka.arkady.Food') Closure condition,
            @DelegatesTo(value = MatchBodySpec, strategy = Closure.DELEGATE_FIRST) Closure body) {

        def c = adjustDelegate(body)
        matchConditionsStack.push(condition.memoizeAtMost(1))
        c()
        matchConditionsStack.pop()

        return this
    }

    Closure adjustDelegate(Closure c) {
        c.setDelegate(this)
        c.setResolveStrategy(Closure.DELEGATE_FIRST)
        c
    }
}
