package org.ka.arkady.aggregator

import groovy.transform.stc.ClosureParams
import groovy.transform.stc.SimpleType

class Case {
    Closure<Boolean> condition
    Aggregator aggregator
}
