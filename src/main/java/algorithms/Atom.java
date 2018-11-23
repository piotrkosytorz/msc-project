package algorithms;

import joiner.datastructures.Relation;

public class Atom<T> {

    private final Relation<T> relation;
    private final String[] variables;

    public Atom(Relation<T> relation, String... variables) {
        this.relation = relation;
        this.variables = variables;
    }

    public Relation getRelation() {
        return relation;
    }

    public String[] getVariables() {
        return variables;
    }
}
