package algorithms;

import query.Query;

public interface QueryResolverIterator<T> {
    /**
     * Gets QueryResolverIterator that allows to iterate through the algorithm step by step.
     *
     * @param query
     * @return
     */
    QueryResolverIterator<T> getQueryResolverIterator(Query query);
}
