package org.ka.arkady.aggregator

import org.ka.arkady.Food


class FilteringAggregator implements Aggregator {

    final List<Case> cases = []

    @Override
    void aggregate(Food food) {
        for (Case c: cases) {
            if (c.condition(food)) {
                c.aggregator.aggregate(food)
                return
            }
        }

        throw new RuntimeException("No aggregator found for ${food}")
    }
}
