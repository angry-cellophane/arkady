package org.ka.arkady.aggregator;

import groovy.transform.CompileStatic;
import org.ka.arkady.Food;

import java.util.ArrayList;
import java.util.List;

public class ExecuteAllFilteringAggregator implements FilteringAggregator {

    private final List<Case> cases = new ArrayList<>();

    @Override
    public void aggregate(final Food food) {
        boolean isFound = false;
        for (Case c : cases) {
            if (Boolean.TRUE.equals(c.getCondition().call(food))) {
                isFound = true;
                c.getAggregator().aggregate(food);
            }

        }


        if (!isFound) throw new RuntimeException("No aggregator found for " + String.valueOf(food));
    }

    public final List<Case> getCases() {
        return cases;
    }
}
