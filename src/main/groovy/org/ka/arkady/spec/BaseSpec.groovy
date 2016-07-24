package org.ka.arkady.spec

import org.ka.arkady.aggregator.Aggregator


interface BaseSpec extends WhenSpec, MatchSpec, UtilSpec {
    Aggregator getFails()
}