package nestedloop;

import algorithms.nestedloop.IterativeNestedLoopJoinQueryResolver;
import org.junit.Test;
import query.Atom;
import query.Query;
import query.Relation;
import query.Tuple;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class IterativeNestedLoopIteratorTest {

    Relation<Integer> relR = new Relation<>(2);
    Relation<Integer> relS = new Relation<>(2);
    Relation<Integer> relT = new Relation<>(2);

    Query<Integer> query;

    public IterativeNestedLoopIteratorTest() {


        relR.addAll(
                new Tuple<>(3, 6),
                new Tuple<>(6, 4),
                new Tuple<>(7, 4)
        );

        relS.addAll(
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

        relT.addAll(
                new Tuple<>(6, 2),
                new Tuple<>(6, 3),
                new Tuple<>(7, 0),
                new Tuple<>(7, 1),
                new Tuple<>(7, 2),
                new Tuple<>(7, 3),
                new Tuple<>(7, 5)
        );

        query = new Query<>(
                new Atom<>(relR, "a", "b"),
                new Atom<>(relS, "b", "c"),
                new Atom<>(relT, "a", "c")
        );
    }

    @Test
    public void iteratorTest() {

        IterativeNestedLoopJoinQueryResolver resolver = new IterativeNestedLoopJoinQueryResolver();

        Iterator<Map> iterator = resolver.getIterator(query);

        List<Map<String, Integer>> results = new ArrayList<>();

        while (iterator.hasNext()) {
            results.add(iterator.next());
        }

        assertEquals(results.size(), 7);

    }
}
