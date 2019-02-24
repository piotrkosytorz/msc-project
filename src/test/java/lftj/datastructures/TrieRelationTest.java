package lftj.datastructures;

import algorithms.lftj.datasctructures.Trie.TrieRelation;
import org.junit.Test;
import query.Relation;
import query.Tuple;

public class TrieRelationTest {
    @Test
    public void relationImportTest() {

        Relation<Integer> relR = new Relation<>(2);
        relR.addAll(
                new Tuple<>(3, 1),
                new Tuple<>(3, 2),
                new Tuple<>(3, 3),
                new Tuple<>(4, 0),
                new Tuple<>(4, 1),
                new Tuple<>(4, 2),
                new Tuple<>(4, 3),
                new Tuple<>(4, 4),
                new Tuple<>(4, 5),
                new Tuple<>(4, 9)
        );

        TrieRelation<Integer> trieRelation = new TrieRelation<>(relR);

//        trieRelation.print();

//        assertEquals(elements, elements2);

    }

}
