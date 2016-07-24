package org.ka.arkady

import org.ka.arkady.aggregator.Aggregator
import org.ka.arkady.test.Food
import org.ka.arkady.tree.TreeFilteringAggregator
import spock.lang.Specification

import java.util.concurrent.TimeUnit


class FilterTreeBuilderTest extends Specification {


    TreeFilteringAggregator<Food> testTree() {
        def builder = new TreeFilteringAggregatorBuilder<Food>()

        def potatoFilter = builder.newFilter {
            if (!(it.country in ['BG', 'PL', 'DE'])) {
                fails
            } else {
                filter {
                    switch (it.manufacturer) {
                        case 'man1':
                            aggregator(name: 'potatoMan1Agg')
                            break
                        case 'man2':
                            aggregator(name: 'potatoMan2Agg')
                            break
                        default:
                            fails
                    }
                }
            }
        }

        builder.newTree {
            if (now.toMillis() - it.expireDate > TimeUnit.DAYS.toMillis(5)) {
                fails
            } else {
                switch (it.foodClass) {
                    case 'vegetables':
                        filter {
                            if (it.foodType == 'carrot') {
                                filter {
                                    aggregator(name: 'carrotAgg')
                                }
                            } else if (it.foodType == 'potato') {
                                filter potatoFilter
                            } else {
                                fails
                            }
                        }
                        break
                    case 'fruits':
                        filter {
                            if (it.foodType == 'apple') {
                                if (it.name in ['Red Apple', 'Green Apple']) {
                                    aggregator(name: 'goodFruitsAgg')
                                } else {
                                    fails
                                }
                            } else if (it.foodType == 'orange') {
                                aggregator(name: 'goodFruitsAgg')
                            } else if (it.foodType == 'grapes') {
                                aggregator(name: 'grapesAgg')
                            } else {
                                fails
                            }
                        }
                        break
                    default:
                        fails
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
        aggregators.find {it.name == 'goodFruitsAgg'}.objects.size() == 2
        aggregators.find {it.name == 'potatoMan1Agg'}.objects.size() == 1
        aggregators.find {it.name == 'fails'}.objects.size() == 1
    }
}
