package algorithms.lftj.datastructures;

import joiner.datastructures.Relation;
import joiner.datastructures.Tuple;

import java.util.Iterator;

public class TrieBasedRelation<T> extends Trie<T> {

    public void add(Tuple<T> tuple) {
        this.insert(tuple.toArray());
    }

    @SuppressWarnings("unchecked")
    public void importFromRelation(Relation<T> relation) {
        Iterator iterator = relation.iterator();
        while (iterator.hasNext()) {
            this.add((Tuple<T>) iterator.next());
        }
    }

    

}
