package org.ka.arkady.spec

import org.ka.arkady.aggregator.Aggregator

interface AfterWhenSpec {
    Aggregator aggregateBy(Aggregator aggregator)
    BaseSpec then(@DelegatesTo(value = BaseSpec, strategy = Closure.DELEGATE_FIRST) Closure body)
}