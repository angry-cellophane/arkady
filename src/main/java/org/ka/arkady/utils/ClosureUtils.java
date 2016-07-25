package org.ka.arkady.utils;

import groovy.lang.Closure;
import groovy.transform.CompileStatic;
import org.ka.arkady.Food;

@CompileStatic
public class ClosureUtils {

    public static final Closure<Boolean> TRUE = new Closure<Boolean>(null, null) {
        public Boolean doCall(Object it) {
            return true;
        }

        public Boolean doCall() {
            return doCall(null);
        }

    };

    public static final Closure<Boolean> FALSE = new Closure<Boolean>(null, null) {
        public Boolean doCall(Object it) {
            return false;
        }

        public Boolean doCall() {
            return doCall(null);
        }

    };

    public static final Closure<Food> DEFAULT_COPIER = new Closure<Food>(null, null) {
        public Food doCall(Food f) {
            Food food = new Food();
            copy(f, food);
            return food;
        }

    };

    private static void copy(Food from, Food to) {
        to.setName(from.getName());
        to.setFoodType(from.getFoodType());
        to.setFoodClass(from.getFoodClass());
        to.setCountry(from.getCountry());
        to.setExpireDate(from.getExpireDate());
        to.setManufacturer(from.getManufacturer());
    }
}
