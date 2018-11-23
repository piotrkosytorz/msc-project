package joiner;

import algorithms.Algorithm;
import algorithms.Atom;

import java.util.List;
import java.util.Map;

public class Joiner<T> {

    private final Atom[] atoms;

    public Joiner(Atom... atoms) {
        this.atoms = atoms;
    }

    @SuppressWarnings("unchecked")
    public List<Map<String, T>> run(Algorithm algorithm) {
        algorithm.prepareQuery(this.atoms);
        return algorithm.run();
    }

    public Atom[] getAtoms() {
        return atoms;
    }
}
