package query;

import algorithms.QueryResolver;

import java.util.List;
import java.util.Map;

/**
 * class Query (Conjunctive query)
 * <p>
 * A query is an ordered list of atoms.
 *
 * @param <T>
 */
public class Query<T> {

    private final Atom[] atoms;

    QueryResolver queryResolver;

    public Query(Atom... atoms) {
        this.atoms = atoms;
    }

    @SuppressWarnings("unchecked")
    public void bootstrap(QueryResolver queryResolver) throws Exception {
        this.queryResolver = queryResolver;
        queryResolver.bootstrap(this);
    }

    @SuppressWarnings("unchecked")
    public List<Map<String, T>> resolve() throws Exception {
        return this.queryResolver.getFullResult();
    }

    public Atom[] getAtoms() {
        return atoms;
    }
}
