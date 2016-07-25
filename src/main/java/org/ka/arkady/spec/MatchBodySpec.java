package org.ka.arkady.spec;

import org.ka.arkady.aggregator.Aggregator;

public interface MatchBodySpec {
    AfterWhenSpec when(Object assertion);
    Aggregator newAggregator(String name);
    Aggregator findByName(String aggregatorName);
}
