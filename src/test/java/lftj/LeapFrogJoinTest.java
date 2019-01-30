package lftj;

import algorithms.lftj.LeapFrogJoin;
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

        new LeapFrogJoin(relations);
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

        LeapFrogJoin leapFrogJoin = new LeapFrogJoin(relations);

        leapFrogJoin.leapfrogInit();
        leapFrogJoin.leapfrogSearch();


        while (!leapFrogJoin.isAtEnd()) {
            results.add((Tuple) leapFrogJoin.getKey());
            leapFrogJoin.leapfrogNext();
        }

        Collections.sort(results);
        List<Tuple<Integer>> expectedResult = new ArrayList<>();
        expectedResult.add(new Tuple<>(0));
        expectedResult.add(new Tuple<>(5));
        expectedResult.add(new Tuple<>(8));
        expectedResult.add(new Tuple<>(11));

        assertEquals(results.toString(), expectedResult.toString());

    }

}
