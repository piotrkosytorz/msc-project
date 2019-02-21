package algorithms.nestedloop;

import algorithms.QueryResolver;
import algorithms.QueryResolverIterator;
import query.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * class IterativeNestedLoopJoinQueryResolver
 * <p>
 * Nested loop join implementation according to the QueryResolver interface.
 * The result is a list of join results, where a join result is a list of pairs [matched variable name : value]
 *
 * @param <T>
 */
public class IterativeNestedLoopJoinQueryResolver<T extends Comparable> implements QueryResolver<T>, QueryResolverIterator<T> {

    @Override
    public QueryResolverIterator<T> getQueryResolverIterator(Query query) {
        return null;
    }

    @Override
    public List<Map<String, T>> getFullResult(Query query) {

        IterativeNestedLoopJoin nestedLoopJoin = new IterativeNestedLoopJoin(query);
        List<Map<String, T>> cumulativeResult = new ArrayList<>();

        boolean atEnd = false;

        while (!atEnd) {
            atEnd = nestedLoopJoin.moveOneForward();
            Map<String, T> result = nestedLoopJoin.getResultOrNull();
            if (result != null) {
                cumulativeResult.add(result);
            }
        }

        return cumulativeResult;
    }
}
