package algorithms;

import query.Query;

import java.util.List;
import java.util.Map;

public interface QueryResolver<T> {

    /**
     * Bootstraping for LFTJ is mainly indexig. The reason for splitting it from getFullResult is that I want to
     * be able to benchmark it separately.
     *
     * @param query
     */
    void bootstrap(Query query);

    /**
     * As a result, a join method (algorithm) should return a list of values assigned to the variables that match
     * the original conjunctive query.
     *
     * @return
     */
    List<Map<String, T>> getFullResult() throws Exception;
}
