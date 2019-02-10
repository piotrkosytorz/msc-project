package lftj;

import algorithms.lftj.LeapFrogJoin;
import algorithms.lftj.datasctructures.Trie.TrieRelation;
import algorithms.lftj.iterators.LeapFrogIterator;
import algorithms.lftj.iterators.TrieIterator;
import org.junit.Test;
import query.Relation;
import query.Tuple;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class LeapFrogJoinTest {

    @Test
    public void lfjBootstrapTest() throws Exception {

        Relation<Integer>[] relations = new Relation[5];
        relations[0] = new Relation<>(new Tuple<>(1, 2, 3), new Tuple<>(1, 5, 2));
        relations[1] = new Relation<>(new Tuple<>(6, 3, 8), new Tuple<>(8, 65, 2));
        relations[2] = new Relation<>(new Tuple<>(8, 4, 4), new Tuple<>(4, 82, 20));
        relations[3] = new Relation<>(new Tuple<>(9, 2, 4), new Tuple<>(1, 4, 2));
        relations[4] = new Relation<>(new Tuple<>(8, 2, 7), new Tuple<>(1, 2, 2));

        TrieRelation<Integer>[] trieRelations = new TrieRelation[5];
        trieRelations[0] = new TrieRelation<>(relations[0]);
        trieRelations[1] = new TrieRelation<>(relations[1]);
        trieRelations[2] = new TrieRelation<>(relations[2]);
        trieRelations[3] = new TrieRelation<>(relations[3]);
        trieRelations[4] = new TrieRelation<>(relations[4]);

        LeapFrogIterator[] iterators = new TrieIterator[trieRelations.length];

        for (int i = 0; i < relations.length; i++) {
            iterators[i] = new TrieIterator(trieRelations[i].getRoot());
        }

        new LeapFrogJoin(iterators);
    }

    @Test
    public void simpleJoinTest() throws Exception {

        Relation<Integer>[] relations = new Relation[3];
        List<Tuple<Integer>> results = new ArrayList<>();

        relations[0] = new Relation<>(
                new Tuple<>(3),
                new Tuple<>(9),
                new Tuple<>(0),
                new Tuple<>(8),
                new Tuple<>(6),
                new Tuple<>(4),
                new Tuple<>(5),
                new Tuple<>(1),
                new Tuple<>(11),
                new Tuple<>(7)
        );
        relations[1] = new Relation<>(
                new Tuple<>(2),
                new Tuple<>(5),
                new Tuple<>(0),
                new Tuple<>(9),
                new Tuple<>(8),
                new Tuple<>(7),
                new Tuple<>(11)
        );
        relations[2] = new Relation<>(
                new Tuple<>(10),
                new Tuple<>(5),
                new Tuple<>(8),
                new Tuple<>(2),
                new Tuple<>(11),
                new Tuple<>(4),
                new Tuple<>(0)
        );


        TrieRelation<Integer>[] trieRelations = new TrieRelation[3];
        trieRelations[0] = new TrieRelation<>(relations[0]);
        trieRelations[1] = new TrieRelation<>(relations[1]);
        trieRelations[2] = new TrieRelation<>(relations[2]);

        LeapFrogIterator[] iterators = new TrieIterator[trieRelations.length];

        for (int i = 0; i < trieRelations.length; i++) {
            iterators[i] = trieRelations[i].getTrieIterator();
        }

        for (int i = 0; i < iterators.length; i++) {
            iterators[i].open();
        }

        LeapFrogJoin leapFrogJoin = new LeapFrogJoin(iterators);

        leapFrogJoin.leapfrogInit();

        while (!leapFrogJoin.isAtEnd()) {
            Integer key = (Integer) leapFrogJoin.getKey();
            results.add(new Tuple<>(key));
            leapFrogJoin.leapfrogNext();
        }

        Collections.sort(results);
        List<Tuple<Integer>> expectedResult = new ArrayList<>();
        expectedResult.add(new Tuple<>(0));
        expectedResult.add(new Tuple<>(5));
        expectedResult.add(new Tuple<>(8));
        expectedResult.add(new Tuple<>(11));

        assertEquals(expectedResult.toString(), results.toString());

    }

    @Test
    public void edgeCaseJoinTest() throws Exception {

        Relation<Integer>[] relations = new Relation[3];
        List<Tuple<Integer>> results = new ArrayList<>();

        relations[0] = new Relation<>(
                new Tuple<>(3),
                new Tuple<>(6),
                new Tuple<>(7)
        );
        relations[1] = new Relation<>(
                new Tuple<>(6),
                new Tuple<>(7)
        );


        TrieRelation<Integer>[] trieRelations = new TrieRelation[2];
        trieRelations[0] = new TrieRelation<>(relations[0]);
        trieRelations[1] = new TrieRelation<>(relations[1]);

        LeapFrogIterator[] iterators = new TrieIterator[trieRelations.length];

        for (int i = 0; i < trieRelations.length; i++) {
            iterators[i] = trieRelations[i].getTrieIterator();
        }

        LeapFrogJoin leapFrogJoin = new LeapFrogJoin(iterators);

        for (int i = 0; i < iterators.length; i++) {
            iterators[i].open();
        }

        leapFrogJoin.leapfrogInit();

        while (!leapFrogJoin.isAtEnd()) {
            results.add(new Tuple<>((Integer) leapFrogJoin.getKey()));
            leapFrogJoin.leapfrogNext();
        }

        Collections.sort(results);
        List<Tuple<Integer>> expectedResult = new ArrayList<>();
        expectedResult.add(new Tuple<>(6));
        expectedResult.add(new Tuple<>(7));

        System.out.println(results);

        assertEquals(expectedResult.toString(), results.toString());

    }
}
