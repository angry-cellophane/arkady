package org.ka.arkady.spec

import org.ka.arkady.aggregator.Aggregator


interface AggregatorSpec {
    Aggregator newAggregator(Map<String, Object> params)
    Aggregator findByName(String aggregatorName)
}