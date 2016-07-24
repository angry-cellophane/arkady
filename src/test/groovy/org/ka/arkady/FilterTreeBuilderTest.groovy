package org.ka.arkady

import org.ka.arkady.tree.TreeFilteringAggregator
import spock.lang.Specification

import java.util.concurrent.TimeUnit


class FilterTreeBuilderTest extends Specification {


    TreeFilteringAggregator<Food> testTree() {
        def builder = new TreeFilteringAggregatorBuilder<Food>()

        def potatoAggregator = builder.newFilter {
            when { !(it.country in ['BG', 'PL', 'DE']) } aggregator fails
            when { it.manufacturer == 'man1' } newAggregator(name: 'potatoMan1Agg')
            when { it.manufacturer == 'man2' } newAggregator(name: 'potatoMan2Agg')
        }

        builder.newTree {
            when { now() - it.expireDate > TimeUnit.DAYS.toMillis(5) } then {
                match { it.foodClass } {
                    when 'vegetables' then {
                        match { it.foodType } {
                            when 'carrot' newAggregator(name: 'carrotAgg')
                            when 'potato' aggregator potatoAggregator
                        }
                    }
                    when 'fruits' then {
                        match { it.foodType } {
                            when('apple') then {
                                when { it.name in ['Red Apple', 'Green Apple'] } newAggregator(name: 'goodFruitsAgg')
                            }
                            when('orange') aggregatorByName('goodFruitsAgg')
                            when('grapes') newAggregator(name: 'grapesAgg')
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
        aggregators.size() == 6
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
                        country: 'FR',
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

}
