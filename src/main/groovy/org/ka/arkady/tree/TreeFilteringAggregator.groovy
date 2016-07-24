package org.ka.arkady.tree

import groovy.transform.CompileStatic
import org.ka.arkady.Food
import org.ka.arkady.aggregator.Aggregator
import org.ka.arkady.aggregator.FinalAggregator

@CompileStatic
class TreeFilteringAggregator {

    final Aggregator root
    final List<FinalAggregator> aggregators

    TreeFilteringAggregator(Aggregator root, List<FinalAggregator> aggregators) {
        this.root = root
        this.aggregators = aggregators
    }

    void aggregate(Food food) {
        root.aggregate(food)
    }

    List<FinalAggregator> getAggregators() {
        aggregators
    }


}
