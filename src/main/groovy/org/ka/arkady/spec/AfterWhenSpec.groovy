package org.ka.arkady.spec

import org.ka.arkady.aggregator.Aggregator

interface AfterWhenSpec {
    Aggregator newAggregator(Map<String, Object> params)
    Aggregator aggregatorByName(String name)
    Aggregator aggergator(Aggregator aggregator)
    BaseSpec then(@DelegatesTo(value = BaseSpec, strategy = Closure.DELEGATE_FIRST) Closure body)
}