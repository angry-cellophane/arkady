package org.ka.arkady.spec;

import groovy.lang.Closure;
import groovy.lang.DelegatesTo;
import groovy.transform.stc.ClosureParams;
import groovy.transform.stc.SimpleType;
import org.codehaus.groovy.runtime.DefaultGroovyMethods;
import org.ka.arkady.Food;
import org.ka.arkady.aggregator.*;
import org.ka.arkady.utils.ClosureUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TreeFilteringAggregatorSpecification implements BaseSpec, MatchBodySpec, AfterWhenSpec, ForkSpec {

    private static class AssertingClosure extends Closure<Boolean> {

        final Object assertion;
        final Closure transformer;

        AssertingClosure(Object assertion, Closure transformer) {
            super(null, null);
            this.assertion = assertion;
            this.transformer = transformer;
        }

        @Override
        public Boolean call(Object arg) {
            Object actual = transformer.call(arg);
            return (actual == null && assertion == null)
                    || (assertion != null && assertion.equals(actual));
        }
    }

    private final List<FilteringAggregator> stack;
    private final Case failsCase;
    private final Map<String, FinalAggregator> aggregatorByName;
    private final Closure<Food> foodCopier;
    private final List<Closure> matchConditionsStack;

    public TreeFilteringAggregatorSpecification(Case failsCase,
                                                FilteringAggregator rootAgg,
                                                Map<String, FinalAggregator> aggregatorByName,
                                                Closure<Food> foodCopier) {
        this.failsCase = failsCase;
        this.stack = new ArrayList<>();
        this.stack.add(rootAgg);
        this.aggregatorByName = aggregatorByName;
        this.matchConditionsStack = new ArrayList<>();
        this.foodCopier = foodCopier;
    }

    @Override
    public Aggregator newAggregator(final String name) {
        if (aggregatorByName.containsKey(name)) {
            throw new RuntimeException("Aggregator " + name + " already exists");
        }

        FinalAggregator agg = new FinalAggregator(name);
        aggregatorByName.put(name, agg);
        return agg;
    }

    @Override
    public Aggregator findByName(final String aggregatorName) {
        FinalAggregator agg = aggregatorByName.get(aggregatorName);
        if (agg == null) {
            throw new RuntimeException("No aggregator with name " + aggregatorName
                                        + " found. You can define a new with the newAggregator method");
        }

        return agg;
    }

    @Override
    public BaseSpec aggregateBy(Aggregator aggregator) {
        List<Case> cases = stack.get(stack.size() - 1).getCases();
        lastOf(cases).setAggregator(aggregator);
        return this;
    }

    @Override
    public BaseSpec then(@DelegatesTo(value = BaseSpec.class, strategy = Closure.DELEGATE_FIRST) Closure body) {
        Closure c = adjustDelegate(body);
        stack.add(new ExecuteFirstFilteringAggregator());

        c.call();

        FilteringAggregator agg = stack.remove(stack.size() - 1);
        agg.getCases().add(failsCase);
        lastOf(lastOf(stack).getCases()).setAggregator(agg);

        return this;
    }

    @Override
    public ForkSpec forks(@DelegatesTo(value = ForkSpec.class, strategy = Closure.DELEGATE_FIRST) Closure body) {
        Closure c = adjustDelegate(body);
        stack.add(new ExecuteAllFilteringAggregator());

        c.call();

        FilteringAggregator agg = stack.remove(stack.size() - 1);
        lastOf(lastOf(stack).getCases()).setAggregator(agg);

        return this;
    }

    @Override
    public Aggregator getFails() {
        return aggregatorByName.get("fails");
    }

    @Override
    public AfterWhenSpec when(@ClosureParams(value = SimpleType.class, options = "org.ka.arkady.Food") Closure<Boolean> condition) {
        lastOf(stack).getCases().add(new Case(condition, null));

        return this;
    }

    @Override
    public Closure<Boolean> getAll() {
        return ClosureUtils.TRUE;
    }

    @Override
    public AfterWhenSpec when(final Object assertion) {
        final Closure c = lastOf(matchConditionsStack);
        lastOf(stack).getCases().add(new Case(new AssertingClosure(assertion, c), null));
        return this;
    }

    @Override
    public BaseSpec match(@ClosureParams(value = SimpleType.class, options = "org.ka.arkady.Food") Closure condition,
                          @DelegatesTo(value = MatchBodySpec.class, strategy = Closure.DELEGATE_FIRST) Closure body) {

        Closure c = adjustDelegate(body);
        matchConditionsStack.add(condition.memoizeAtMost(1));
        c.call();
        matchConditionsStack.remove(matchConditionsStack.size() - 1);

        return this;
    }

    private Closure adjustDelegate(Closure c) {
        c.setDelegate(this);
        c.setResolveStrategy(Closure.DELEGATE_FIRST);
        return c;
    }

    @Override
    public BaseSpec fork(@DelegatesTo(value = BaseSpec.class, strategy = Closure.DELEGATE_FIRST) Closure body) {
        Closure c = adjustDelegate(body);
        stack.add(new FoodObjectCopyAggregator(new ExecuteFirstFilteringAggregator(), foodCopier));
        c.call();
        FilteringAggregator agg = DefaultGroovyMethods.pop(stack);
        agg.getCases().add(failsCase);
        lastOf(stack).getCases().add(new Case(ClosureUtils.TRUE, agg));
        return this;
    }

    private static <T> T lastOf(List<T> list) {
        return list.get(list.size() - 1);
    }

    public final Map<String, FinalAggregator> getAggregatorByName() {
        return aggregatorByName;
    }
}
