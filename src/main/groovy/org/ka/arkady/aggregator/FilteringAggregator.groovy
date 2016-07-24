package org.ka.arkady.aggregator

import org.ka.arkady.spec.TreeFilteringAggregatorSpecification


class FilteringAggregator<T> implements Aggregator<T> {

    final Closure filter

    FilteringAggregator(Closure filter) {
        this.filter = filter
    }

    @Override
    void aggregate(T object) {
        filter(object)
    }
}
