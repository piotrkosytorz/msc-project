package algorithms;

import algorithms.lftj.datastructures.TrieBasedRelation;
import joiner.datastructures.Relation;

public final class Atom<T> {

    private final Relation<T> relation;
    private TrieBasedRelation<T> trieBasedRelation;
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

    public TrieBasedRelation getTrieBasedRelation() {
        if (trieBasedRelation == null)
            trieBasedRelation = new TrieBasedRelation<>(this.relation);
        return trieBasedRelation;
    }
}
