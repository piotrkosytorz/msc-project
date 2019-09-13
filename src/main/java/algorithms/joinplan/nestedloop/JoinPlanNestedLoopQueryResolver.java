package algorithms.joinplan.nestedloop;

import algorithms.QueryResolver;
import algorithms.QueryResolverIterator;
import algorithms.joinplan.BinaryJoinAlgorithm;
import algorithms.joinplan.NaiveJoinPlan;
import query.Query;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class JoinPlanNestedLoopQueryResolver<T extends Comparable> implements QueryResolver<T>, QueryResolverIterator<T> {

    private Query query;

    @Override
    public void bootstrap(Query query) {
        this.query = query;
        // bootstrap (and indexing)
    }

    @Override
    public List<Map<String, T>> getFullResult() throws Exception {

        NaiveJoinPlan plan = new NaiveJoinPlan();
        BinaryJoinAlgorithm algorithm = new BinaryNestedLoopJoin();

        return plan.resolveQuery(query, algorithm);
    }

    @Override
    public Iterator<Map<String, T>> getIterator(Query query) {
        return new Iterator<Map<String, T>>() {
            @Override
            public boolean hasNext() {
                return false;
            }

            @Override
            public Map<String, T> next() {
                return null;
            }
        };
    }
}