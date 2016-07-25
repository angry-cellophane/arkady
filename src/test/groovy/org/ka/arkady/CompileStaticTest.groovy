package org.ka.arkady

import groovy.transform.CompileStatic
import org.junit.Assert
import org.junit.Test

@CompileStatic
class CompileStaticTest {

    @Test
    void testCompileStatic() {
        def food = [
                new Food(foodType: 'vegetables'),
                new Food(foodType: 'fruits', name: 'apple'),
                new Food(foodType: 'fruits', name: 'orange'),
                new Food(foodType: 'fruits', name: 'grapes'),
                new Food(foodType: 'fruits', name: 'pineapple'),
        ]

        def builder = new TreeFilteringAggregatorBuilder()
        def treeFilter = builder.newTree {
            match { it.foodType } {
                when('vegetables').aggregateBy(newAggregator('vegetablesAgg'))
                when('fruits') then {
                    when { it.name == 'apple' } aggregateBy newAggregator('appleAgg')
                    when { it.name == 'orange' } aggregateBy newAggregator('orangeAgg')
                    when { it.name == 'pineapple' } aggregateBy findByName('appleAgg')
                }
            }
        }
        def aggregators = treeFilter.aggregators

        for (Food f: food) {
            treeFilter.aggregate(f)
        }

        Assert.assertEquals(1, aggregators.find { it.name == 'vegetablesAgg' }.objects.size())
        Assert.assertEquals(2, aggregators.find { it.name == 'appleAgg' }.objects.size())
        Assert.assertEquals(1, aggregators.find { it.name == 'orangeAgg' }.objects.size())
        Assert.assertEquals(1, aggregators.find { it.name == 'fails' }.objects.size())
    }

}
