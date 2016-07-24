package org.ka.arkady.spec

import groovy.transform.stc.ClosureParams
import groovy.transform.stc.SimpleType

interface MatchBodySpec {
    AfterWhenSpec when(Object assertion)
}