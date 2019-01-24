package algorithms.lftj;

import algorithms.lftj.iterators.LinearIterator;
import query.Relation;

public class LeapFrogJoin {

    LinearIterator[] iterators;

    public LeapFrogJoin(Relation<Integer>... relations) {
        this.iterators = new LinearIterator[relations.length];
        for (int i = 0; i < relations.length; i++) {
            relations[i].toArray();
        }
    }
}
