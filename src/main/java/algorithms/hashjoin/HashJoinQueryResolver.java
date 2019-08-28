package algorithms.hashjoin;

import algorithms.QueryResolver;
import algorithms.QueryResolverIterator;
import query.Query;

import java.util.*;

public class HashJoinQueryResolver<T extends Comparable> implements QueryResolver<T>, QueryResolverIterator<T> {

    @Override
    public void bootstrap(Query query) {
        // bootstrap (and indexing)
    }

    @Override
    public List<Map<String, T>> getFullResult() throws Exception {
        // cummulative result
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