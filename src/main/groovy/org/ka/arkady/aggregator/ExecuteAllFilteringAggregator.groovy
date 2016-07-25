package org.ka.arkady.aggregator

import groovy.transform.CompileStatic
import org.ka.arkady.Food

@CompileStatic
class ExecuteAllFilteringAggregator implements FilteringAggregator {

    final List<Case> cases = []

    @Override
    void aggregate(Food food) {
        boolean isFound = false
        for (Case c : cases) {
            if (Boolean.TRUE.equals(c.condition.call(food))) {
                isFound = true
                c.aggregator.aggregate(food)
            }
        }

        if (!isFound) throw new RuntimeException("No aggregator found for ${food}")
    }
}
