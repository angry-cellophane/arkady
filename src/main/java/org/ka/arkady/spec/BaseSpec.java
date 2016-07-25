package org.ka.arkady.spec;

import org.ka.arkady.aggregator.Aggregator;

public interface BaseSpec extends WhenSpec, MatchSpec, AggregatorSpec {
    Aggregator getFails();
}
