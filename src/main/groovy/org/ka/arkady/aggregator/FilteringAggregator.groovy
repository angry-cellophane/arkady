package org.ka.arkady.aggregator

import org.ka.arkady.Food
import org.ka.arkady.spec.TreeFilteringAggregatorSpecification


class FilteringAggregator implements Aggregator {

    final Closure filter

    FilteringAggregator(Closure filter) {
        this.filter = filter
    }

    @Override
    void aggregate(Food food) {
        filter(food)
    }
}
