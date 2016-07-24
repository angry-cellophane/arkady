package org.ka.arkady.tree

import groovy.transform.CompileStatic
import org.ka.arkady.aggregator.FinalAggregator

@CompileStatic
class TreeFilteringAggregator<T> {

    void aggregate(T object) {

    }

    List<FinalAggregator<T>> getAggregators() {
        []
    }


}
