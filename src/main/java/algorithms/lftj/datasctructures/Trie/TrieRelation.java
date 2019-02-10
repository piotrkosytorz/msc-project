package algorithms.lftj.datasctructures.Trie;

import algorithms.lftj.iterators.TrieIterator;
import query.Relation;
import query.Tuple;

public class TrieRelation<T extends Comparable<T>> extends Trie<T> {

    private TrieIterator trieIterator;

    public TrieRelation(Relation<T> relation) {
        importFromRelation(relation);
    }

    public TrieRelation(Tuple<T>... tuples) {
        for (Tuple tuple : tuples) {
            this.add(tuple);
        }
    }

    public void add(Tuple<T> tuple) {
        this.insert(tuple.toArray());
    }

    public TrieIterator getTrieIterator() {
        if (trieIterator == null) {
            trieIterator = new TrieIterator(this.getRoot());
        }
        return trieIterator;
    }

    @SuppressWarnings("unchecked")
    private void importFromRelation(Relation<T> relation) {
        for (Object aRelation : relation) {
            this.add((Tuple<T>) aRelation);
        }
    }
}
