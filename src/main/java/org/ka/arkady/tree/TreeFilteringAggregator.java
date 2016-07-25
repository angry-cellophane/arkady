package org.ka.arkady.tree;

import org.ka.arkady.Food;
import org.ka.arkady.aggregator.Aggregator;
import org.ka.arkady.aggregator.FinalAggregator;

import java.util.List;

public class TreeFilteringAggregator {

    private final Aggregator root;
    private final List<FinalAggregator> aggregators;

    public TreeFilteringAggregator(Aggregator root, List<FinalAggregator> aggregators) {
        this.root = root;
        this.aggregators = aggregators;
    }

    public void aggregate(Food food) {
        root.aggregate(food);
    }

    public List<FinalAggregator> getAggregators() {
        return aggregators;
    }

    public final Aggregator getRoot() {
        return root;
    }
}
