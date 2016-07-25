package org.ka.arkady

import org.ka.arkady.tree.TreeFilteringAggregator
import spock.lang.Shared
import spock.lang.Specification

import java.util.concurrent.TimeUnit


class FilterTreeBuilderTest extends Specification {

    @Shared TreeFilteringAggregator tree = testTree()

    def 'test aggregators'() {
        when:
        def aggregators = tree.aggregators
        then:
        aggregators.size() == 7
    }

    def 'test fitlers'() {
        given:
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
        then:
        aggregators.find { it.name == 'goodFruitsAgg' }.objects.size() == 2
        aggregators.find { it.name == 'potatoMan1Agg' }.objects.size() == 1
        aggregators.find { it.name == 'fails' }.objects.size() == 1
    }

    TreeFilteringAggregator testTree() {
        def builder = new TreeFilteringAggregatorBuilder()

        def potatoAggregator = builder.newFilter {
            when { !(it.country in ['BG', 'PL', 'DE']) } aggregateBy fails
            when { it.manufacturer == 'man1' } aggregateBy newAggregator('potatoMan1Agg')
            when { it.manufacturer == 'man2' } aggregateBy newAggregator('potatoMan2Agg')
            when all aggregateBy newAggregator('otherPotatoAgg')
        }

        builder.newTree {
            when { System.currentTimeMillis() - it.expireDate < TimeUnit.DAYS.toMillis(5) } then {
                match { it.foodClass } {
                    when 'vegetables' then {
                        match { it.foodType } {
                            when 'carrot' aggregateBy newAggregator('carrotAgg')
                            when 'potato' aggregateBy potatoAggregator
//                            by default all other food go to the fails aggregator
                        }
                    }
                    when 'fruits' then {
                        match { it.foodType } {
                            when('apple') then {
                                when { it.name in ['Red Apple', 'Green Apple'] } aggregateBy newAggregator('goodFruitsAgg')
                            }
                            when('orange') aggregateBy findByName('goodFruitsAgg')
                            when('grapes') aggregateBy newAggregator('grapesAgg')
                        }
                    }
                }
            }
        }
    }

}
