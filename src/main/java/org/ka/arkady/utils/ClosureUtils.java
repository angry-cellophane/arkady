package org.ka.arkady.utils;

import groovy.lang.Closure;
import groovy.transform.CompileStatic;
import org.ka.arkady.Food;

@CompileStatic
public class ClosureUtils {

    public static final Closure<Boolean> TRUE = new ConstantClosure<Boolean>(null, null, Boolean.TRUE);
    public static final Closure<Boolean> FALSE = new ConstantClosure<Boolean>(null, null, Boolean.FALSE);

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
