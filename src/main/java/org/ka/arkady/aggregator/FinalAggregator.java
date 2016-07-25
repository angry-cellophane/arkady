package org.ka.arkady.aggregator;

import org.codehaus.groovy.runtime.DefaultGroovyMethods;
import org.ka.arkady.Food;

import java.util.ArrayList;
import java.util.List;

public class FinalAggregator implements Aggregator {
    private final String name;
    private final List<Food> objects;

    public FinalAggregator(String name) {
        this.name = name;
        this.objects = new ArrayList<>();
    }

    public void aggregate(Food food) {
        objects.add(food);
    }

    public final String getName() {
        return name;
    }

    public final List<Food> getObjects() {
        return objects;
    }
}
