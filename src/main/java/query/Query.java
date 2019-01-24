package query;

import algorithms.Algorithm;

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
    public List<Map<String, T>> resolve(Algorithm algorithm) throws Exception {
        algorithm.prepareQuery(this);
        return algorithm.run();
    }

    public Atom[] getAtoms() {
        return atoms;
    }
}
