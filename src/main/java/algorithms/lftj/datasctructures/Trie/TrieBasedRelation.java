package algorithms.lftj.datasctructures.Trie;

import algorithms.lftj0.modules.old.LFTrieBasedRelationIterator;
import query.Relation;
import query.Tuple;

public class TrieBasedRelation<T extends Comparable<T>> extends Trie<T> {

    private LFTrieBasedRelationIterator lfTrieBasedRelationIterator;

    public TrieBasedRelation(Relation<T> relation) {
        importFromRelation(relation);
    }

    public void add(Tuple<T> tuple) {
        this.insert(tuple.toArray());
    }

    public LFTrieBasedRelationIterator getLeapFrogTrieIterator() {
        if (lfTrieBasedRelationIterator == null)
            lfTrieBasedRelationIterator = new LFTrieBasedRelationIterator(this.getRoot());
        return lfTrieBasedRelationIterator;
    }

    @SuppressWarnings("unchecked")
    private void importFromRelation(Relation<T> relation) {
        for (Object aRelation : relation) {
            this.add((Tuple<T>) aRelation);
        }
    }
}
