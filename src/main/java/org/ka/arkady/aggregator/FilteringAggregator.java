package org.ka.arkady.aggregator;

import java.util.List;

public interface FilteringAggregator extends Aggregator {
    List<Case> getCases();
}
