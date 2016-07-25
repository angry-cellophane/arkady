package org.ka.arkady.aggregator

import groovy.transform.CompileStatic
import org.ka.arkady.Food

@CompileStatic
class ExecuteFirstFilteringAggregator implements FilteringAggregator {

    final List<Case> cases = []

    @Override
    void aggregate(Food food) {
        for (Case c: cases) {
            if (Boolean.TRUE.equals(c.condition.call(food))) {
                c.aggregator.aggregate(food)
                return
            }
        }

        throw new RuntimeException("No aggregator found for ${food}")
    }
}
