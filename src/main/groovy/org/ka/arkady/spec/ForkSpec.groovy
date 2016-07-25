package org.ka.arkady.spec

interface ForkSpec {
    BaseSpec fork(@DelegatesTo(value = BaseSpec, strategy = Closure.DELEGATE_FIRST) Closure body)
}