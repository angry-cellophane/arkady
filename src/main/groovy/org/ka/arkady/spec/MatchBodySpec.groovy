package org.ka.arkady.spec

import groovy.transform.stc.ClosureParams
import groovy.transform.stc.SimpleType
import org.ka.arkady.aggregator.Aggregator

interface MatchBodySpec {
    AfterWhenSpec when(Object assertion)
    Aggregator newAggregator(Map<String, Object> params)
    Aggregator findByName(String aggregatorName)
}