package algorithms;

import java.util.List;
import java.util.Map;

public interface Algorithm<T> {

    /**
     * A conjunctive query consists of a list of atoms that are universally declared in this framework, however
     * different join algorithms may have their own native data structures that they work on. This method is
     * supposed to perform the traversal operations to algorithm's native data structure.
     *
     * @param atoms
     */
    void prepareQuery(Atom... atoms);

    /**
     * As a result, a join method (algorithm) should return a list of values assigned to the variables that match
     * the original conjunctive query
     *
     * @return
     */
    List<Map<String, T>> run();
}
