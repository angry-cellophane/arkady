package org.ka.arkady

import groovy.transform.CompileStatic
import groovy.transform.stc.ClosureParams
import groovy.transform.stc.FromString
import org.ka.arkady.tree.TreeFilteringAggregator

@CompileStatic
class TreeFilteringAggregatorBuilder<T> {

    TreeFilteringAggregator<T> newTree(@ClosureParams(value = FromString, options = "T") Closure c) {

    }

    TreeFilteringAggregator<T> newFilter(@ClosureParams(value = FromString, options = "T") Closure c) {

    }

    TreeFilteringAggregator<T> newAggregator(@ClosureParams(value = FromString, options = "T") Closure c) {

    }
}
