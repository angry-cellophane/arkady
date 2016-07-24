package org.ka.arkady.aggregator

class Aggregator<T> {
    final String name
    final List<T> objects

    Aggregator(String name) {
        this.name = name
        this.objects = []
    }

    void aggregate(T object) {
        objects << object
    }
}
