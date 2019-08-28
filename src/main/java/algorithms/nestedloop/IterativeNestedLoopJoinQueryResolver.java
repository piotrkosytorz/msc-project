package algorithms.nestedloop;

import algorithms.QueryResolver;
import algorithms.QueryResolverIterator;
import query.Query;

import java.util.ArrayList;
import java.util.Iterator;
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

    IterativeNestedLoopJoin nestedLoopJoin;

    @Override
    public void bootstrap(Query query) {
        nestedLoopJoin = new IterativeNestedLoopJoin(query);
    }

    @Override
    public List<Map<String, T>> getFullResult() {

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

    @Override
    public Iterator<Map<String, T>> getIterator(Query query) {

        return new Iterator<Map<String, T>>() {

            IterativeNestedLoopJoin nestedLoopJoin = new IterativeNestedLoopJoin(query);
            Map<String, T> nextResult = null;

            @Override
            public boolean hasNext() {

                boolean atEnd = false;

                while (!atEnd) {
                    atEnd = nestedLoopJoin.moveOneForward();
                    Map<String, T> result = nestedLoopJoin.getResultOrNull();
                    if (result != null) {
                        nextResult = result;
                        return true;
                    }
                }

                return false;
            }

            @Override
            public Map<String, T> next() {
                return nextResult;
            }
        };
    }
}
