package org.ka.arkady.spec

import org.ka.arkady.aggregator.Aggregator


interface AggregatorSpec {
    Aggregator newAggregator(String name)
    Aggregator findByName(String aggregatorName)
}