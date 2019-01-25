package query;

/**
 * class Atom
 *
 * An atom is the smallest complete sub-element of a query. For current purposes of my implementation of LFTJ,
 * an atom is a component of a conjunctive query. An atom contains a pointer to relation and a list of variables
 * names for the query.
 *
 * @param <T>
 */
public final class Atom<T extends Comparable<T>> {

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
