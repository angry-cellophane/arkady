package org.ka.arkady.spec

import groovy.transform.stc.ClosureParams
import groovy.transform.stc.FromString
import groovy.transform.stc.SimpleType


interface MatchSpec {
    BaseSpec match(@ClosureParams(value = SimpleType, options = 'org.ka.arkady.Food') Closure condition,
                   @DelegatesTo(value = MatchBodySpec, strategy = Closure.DELEGATE_FIRST) Closure body)

}