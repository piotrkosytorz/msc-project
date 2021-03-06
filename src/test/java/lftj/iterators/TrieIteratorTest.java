package lftj.iterators;

import algorithms.lftj.datasctructures.Trie.TrieRelation;
import algorithms.lftj.iterators.LinearIterator;
import algorithms.lftj.iterators.TrieIterator;
import org.junit.Test;
import query.Tuple;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

public class TrieIteratorTest {

    @Test
    public void iterationTest() throws Exception {

        TrieRelation<Integer> trieRelation = new TrieRelation<>(
                new Tuple<>(1),
                new Tuple<>(2),
                new Tuple<>(3),
                new Tuple<>(4),
                new Tuple<>(5),
                new Tuple<>(6),
                new Tuple<>(7),
                new Tuple<>(8),
                new Tuple<>(9),
                new Tuple<>(10)
        );

        //TrieIterator<Integer> iterator = new TrieIterator<>(trieRelation.getRoot());
        TrieIterator<Integer> iterator = trieRelation.getTrieIterator();
        iterator.open();

        List<Integer> elements2 = new ArrayList<>();

        // iterate until the last element
        while (!iterator.atEnd()) {
            elements2.add(iterator.key());
            iterator.next();
        }

        assertEquals("[1, 2, 3, 4, 5, 6, 7, 8, 9, 10]", elements2.toString());

    }

    @Test
    public void seekTest() {
        List<Integer> elements = new ArrayList<>(Arrays.asList(1, 2, 5, 10, 11, 12, 100, 101, 1000, 10000));
        LinearIterator<Integer> iterator = new LinearIterator<>(elements);

        iterator.seek(1);
        assertEquals(iterator.key(), new Integer(1));

        iterator.seek(2);
        assertEquals(iterator.key(), new Integer(2));

        iterator.seek(3);
        assertEquals(iterator.key(), new Integer(5));

        iterator.seek(6);
        assertEquals(iterator.key(), new Integer(10));

        iterator.seek(20);
        assertEquals(iterator.key(), new Integer(100));

        iterator.seek(1001);
        assertEquals(iterator.key(), new Integer(10000));

        assertTrue(iterator.atEnd());
    }

    @Test
    public void seekTest2() {
        List<Integer> elements = new ArrayList<>(Arrays.asList(2, 5, 0, 9, 8, 7, 11));
        Collections.sort(elements);
        LinearIterator<Integer> iterator = new LinearIterator<>(elements);

        iterator.seek(10);
        assertEquals(iterator.key(), new Integer(11));
    }

    @Test
    public void reachingEndTest() {
        List<Integer> elements = new ArrayList<>(Arrays.asList(1, 2, 5));
        LinearIterator<Integer> iterator = new LinearIterator<>(elements);

        // first key is 1
        assertEquals(iterator.key(), new Integer(1));

        // and you're not at the end yet
        assertFalse(iterator.atEnd());

        // move on
        iterator.next();

        // second key is 2
        assertEquals(iterator.key(), new Integer(2));

        // and you're still not at the end yet
        assertFalse(iterator.atEnd());

        // move on
        iterator.next();

        // third key is 5
        assertEquals(iterator.key(), new Integer(5));

        // now you should have reached the end
        assertTrue(iterator.atEnd());

    }

}
