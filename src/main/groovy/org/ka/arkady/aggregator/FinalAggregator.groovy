package org.ka.arkady.aggregator

class FinalAggregator<T> implements Aggregator<T> {
    final String name
    final List<T> objects

    FinalAggregator(String name) {
        this.name = name
        this.objects = []
    }

    void aggregate(T object) {
        objects << object
    }
}
