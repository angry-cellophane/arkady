package org.ka.arkady.spec

import groovy.transform.stc.ClosureParams
import groovy.transform.stc.SimpleType

interface WhenSpec {
    AfterWhenSpec when(@ClosureParams(value = SimpleType, options = 'org.ka.arkady.Food') Closure<Boolean> condition)
}