package algorithms.nestedloop;

import algorithms.QueryResolver;
import algorithms.QueryResolverIterator;
import com.sun.tools.javac.util.Pair;
import query.Atom;
import query.Query;
import query.Relation;

import java.util.*;

/**
 * class NestedLoopJoinQueryResolver
 * <p>
 * Nested loop join implementation according to the QueryResolver interface.
 * The result is a list of join results, where a join result is a list of pairs [matched variable name : value]
 *
 * @param <T>
 */
public class NestedLoopJoinQueryResolver<T extends Comparable> implements QueryResolver<T> {

    @Override
    public QueryResolverIterator<T> getQueryResolverIterator(Query query) {
        return null;
    }

    @Override
    public List<Map<String, T>> getFullResult(Query query) throws Exception {
       return null;
    }
}
