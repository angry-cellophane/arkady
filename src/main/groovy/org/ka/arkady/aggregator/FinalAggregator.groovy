package org.ka.arkady.aggregator

import org.ka.arkady.Food

class FinalAggregator implements Aggregator {
    final String name
    final List<Food> objects

    FinalAggregator(String name) {
        this.name = name
        this.objects = []
    }

    void aggregate(Food food) {
        objects << food
    }
}
