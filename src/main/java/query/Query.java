package query;

import algorithms.QueryResolver;

import java.util.List;
import java.util.Map;

/**
 * class Query (Conjunctive query)
 *
 * A query is an ordered list of atoms.
 *
 * @param <T>
 */
public class Query<T> {

    private final Atom[] atoms;

    public Query(Atom... atoms) {
        this.atoms = atoms;
    }

    @SuppressWarnings("unchecked")
    public List<Map<String, T>> resolve(QueryResolver queryResolver) throws Exception {
        return queryResolver.getFullResult(this);
    }

    public Atom[] getAtoms() {
        return atoms;
    }
}
