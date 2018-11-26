package algorithms.lftj.datastructures;

import algorithms.lftj.modules.LFTrieBasedRelationIterator;
import joiner.datastructures.Relation;
import joiner.datastructures.Tuple;

public class TrieBasedRelation<T> extends Trie<T> {

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
    private void importFromRelation(Relation<T> relation    ) {
        for (Object aRelation : relation) {
            this.add((Tuple<T>) aRelation);
        }
    }
}
