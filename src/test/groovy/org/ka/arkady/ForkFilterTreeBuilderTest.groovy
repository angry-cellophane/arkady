package org.ka.arkady

import org.ka.arkady.tree.TreeFilteringAggregator
import spock.lang.Specification

import java.util.concurrent.TimeUnit


class ForkFilterTreeBuilderTest extends Specification {

    TreeFilteringAggregator testTree() {
        def builder = new TreeFilteringAggregatorBuilder()

        builder.newTree {
            when { System.currentTimeMillis() - it.expireDate < TimeUnit.DAYS.toMillis(5) } then {
                match { it.foodClass } {
                    when 'fruits' then {
                        match { it.foodType } {
                            when('apple') forks {
                                fork {
                                    when { it.name in ['Red Apple', 'Green Apple'] } aggregateBy newAggregator('goodFruitsAgg1')
                                    when { it.country == 'PL' } aggregateBy findByName('goodFruitsAgg1')
                                }
                                fork {
                                    when { it.name == 'Red Apple' } aggregateBy newAggregator('goodFruitsAgg2')
                                    when { it.country == 'DE' } aggregateBy findByName('goodFruitsAgg2')
                                }
                            }
                            when('orange') aggregateBy newAggregator('otherAgg')
                            when('grapes') aggregateBy findByName('otherAgg')
                        }
                    }
                }
            }
        }
    }

    def 'test aggregators'() {
        given:
        def tree = testTree()
        when:
        def aggregators = tree.aggregators
        then:
        aggregators.size() == 4
    }

    def 'test fitlers'() {
        given:
        def tree = testTree()

        def food = [
                new Food(name: 'Red Apple',
                        foodClass: 'fruits',
                        foodType: 'apple',
                        expireDate: System.currentTimeMillis(),
                        country: 'PL',
                        manufacturer: 'man3'),
                new Food(name: 'Orange',
                        foodClass: 'fruits',
                        foodType: 'orange',
                        expireDate: System.currentTimeMillis(),
                        country: 'EG',
                        manufacturer: 'man1'),
                new Food(name: 'Tasty potato',
                        foodClass: 'vegetables',
                        foodType: 'potato',
                        expireDate: System.currentTimeMillis(),
                        country: 'PL',
                        manufacturer: 'man1'),
                new Food(name: 'Spoiled potato',
                        foodClass: 'vegetables',
                        foodType: 'potato',
                        expireDate: System.currentTimeMillis() - TimeUnit.DAYS.toMillis(1_000),
                        country: 'FR',
                        manufacturer: 'man1'),
        ]

        def aggregators = tree.aggregators

        when:
        food.each tree.&aggregate

        and:
        def objects1 = aggregators.find { it.name == 'goodFruitsAgg1' }.objects
        def objects2 = aggregators.find { it.name == 'goodFruitsAgg2' }.objects

        then:
        objects1.size() == 1
        objects2.size() == 1
        objects1[0] != objects2[0]
        objects1[0].properties == objects2[0].properties
    }

}
