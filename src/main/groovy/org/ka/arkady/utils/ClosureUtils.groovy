package org.ka.arkady.utils

import groovy.transform.CompileStatic
import org.ka.arkady.Food

@CompileStatic
class ClosureUtils {
    static final Closure<Boolean> TRUE = { true }
    static final Closure<Boolean> FALSE = { false }
    static final Closure<Food> DEFAULT_COPIER = { Food f ->
        new Food(name: f.name, foodType: f.foodType, foodClass: f.foodClass, country: f.country, expireDate: f.expireDate, manufacturer: f.manufacturer)
    }
}
