package org.ka.arkady.aggregator;

import groovy.transform.CompileStatic;
import org.ka.arkady.Food;

import java.util.ArrayList;
import java.util.List;

public class ExecuteFirstFilteringAggregator implements FilteringAggregator {

    private final List<Case> cases = new ArrayList<>();

    @Override
    public void aggregate(final Food food) {
        for (Case c : cases) {
            if (Boolean.TRUE.equals(c.getCondition().call(food))) {
                c.getAggregator().aggregate(food);
                return;

            }

        }

        throw new RuntimeException("No aggregator found for " + String.valueOf(food));
    }

    public final List<Case> getCases() {
        return cases;
    }
}
