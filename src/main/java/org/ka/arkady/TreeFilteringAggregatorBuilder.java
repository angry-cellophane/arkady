package org.ka.arkady;

import groovy.lang.Closure;
import groovy.lang.DelegatesTo;
import groovy.transform.CompileStatic;
import groovy.transform.stc.ClosureParams;
import groovy.transform.stc.SimpleType;
import org.codehaus.groovy.runtime.DefaultGroovyMethods;
import org.ka.arkady.aggregator.Aggregator;
import org.ka.arkady.aggregator.Case;
import org.ka.arkady.aggregator.ExecuteFirstFilteringAggregator;
import org.ka.arkady.aggregator.FinalAggregator;
import org.ka.arkady.spec.BaseSpec;
import org.ka.arkady.spec.TreeFilteringAggregatorSpecification;
import org.ka.arkady.tree.TreeFilteringAggregator;
import org.ka.arkady.utils.ClosureUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class TreeFilteringAggregatorBuilder {

    private final Case failsCase;
    private final Map<String, FinalAggregator> aggregatorByName;
    private final Closure<Food> foodCopier;

    public TreeFilteringAggregatorBuilder() {
        this(ClosureUtils.DEFAULT_COPIER);
    }

    public TreeFilteringAggregatorBuilder(@ClosureParams(value = SimpleType.class, options = "org.ka.arkady.Food") Closure<Food> copier) {
        foodCopier = copier;
        failsCase = new Case(ClosureUtils.TRUE, new FinalAggregator("fails"));
        Map<String, FinalAggregator> map = new HashMap<>(1);
        map.put("fails", (FinalAggregator) failsCase.getAggregator());
        aggregatorByName = map;
    }

    public TreeFilteringAggregator newTree(@DelegatesTo(value = BaseSpec.class, strategy = Closure.DELEGATE_FIRST) Closure c) {
        ExecuteFirstFilteringAggregator rootAgg = new ExecuteFirstFilteringAggregator();
        TreeFilteringAggregatorSpecification spec = new TreeFilteringAggregatorSpecification(failsCase, rootAgg, aggregatorByName, foodCopier);

        c.setDelegate(spec);
        c.setResolveStrategy(Closure.DELEGATE_FIRST);
        c.call();

        rootAgg.getCases().add(failsCase);
        return new TreeFilteringAggregator(rootAgg, new ArrayList<>(spec.getAggregatorByName().values()));
    }

    public ExecuteFirstFilteringAggregator newFilter(@DelegatesTo(value = BaseSpec.class, strategy = Closure.DELEGATE_FIRST) Closure c) {
        ExecuteFirstFilteringAggregator rootAgg = new ExecuteFirstFilteringAggregator();
        TreeFilteringAggregatorSpecification spec = new TreeFilteringAggregatorSpecification(failsCase, rootAgg, aggregatorByName, foodCopier);

        c.setDelegate(spec);
        c.setResolveStrategy(Closure.DELEGATE_FIRST);
        c.call();

        DefaultGroovyMethods.leftShift(rootAgg.getCases(), failsCase);
        return rootAgg;
    }
}
