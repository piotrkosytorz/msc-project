package algorithms;

import query.Query;

import java.util.Iterator;
import java.util.Map;

public interface QueryResolverIterator<T> {
    /**
     * Gets QueryResolverIterator that allows to iterate through the algorithm step by step.
     *
     * @param query
     * @return
     */
    Iterator<Map<String, T>> getIterator(Query query);
}
