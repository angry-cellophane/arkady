package org.ka.arkady.aggregator;

import groovy.lang.Closure;
import org.ka.arkady.Food;

import java.util.List;

public class FoodObjectCopyAggregator implements FilteringAggregator {

    private final FilteringAggregator delegate;
    private final Closure<Food> copyFunction;

    public FoodObjectCopyAggregator(FilteringAggregator delegate, Closure<Food> copyFunction) {
        this.delegate = delegate;
        this.copyFunction = copyFunction;
    }

    @Override
    public void aggregate(Food object) {
        Food copy = copyFunction.call(object);
        delegate.aggregate(copy);
    }

    @Override
    public List<Case> getCases() {
        return delegate.getCases();
    }
}
