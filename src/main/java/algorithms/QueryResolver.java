package algorithms;

import query.Query;

import java.util.List;
import java.util.Map;

public interface QueryResolver<T> {

    /**
     * Gets QueryResolverIterator that allows to iterate through the algorithm step by step.
     * @param query
     * @return
     */
    QueryResolverIterator<T> getQueryResolverIterator(Query query);

    /**
     * As a result, a join method (algorithm) should return a list of values assigned to the variables that match
     * the original conjunctive query.
     *
     * @return
     */
    List<Map<String, T>> getFullResult(Query query) throws Exception;
}
