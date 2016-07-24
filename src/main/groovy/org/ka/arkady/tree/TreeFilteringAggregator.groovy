package org.ka.arkady.tree

import groovy.transform.CompileStatic
import org.ka.arkady.aggregator.Aggregator

@CompileStatic
class TreeFilteringAggregator<T> {

    void aggregate(T object) {

    }

    List<Aggregator<T>> getAggregators() {
        []
    }


}
