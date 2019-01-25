package lftj;

import algorithms.lftj.LeapFrogJoin;
import helpers.ConsoleColorsHelper;
import org.junit.Test;
import query.Relation;
import query.Tuple;

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
        System.out.println(ConsoleColorsHelper.BLUE + "head element: " + leapFrogJoin.getKey() + ConsoleColorsHelper.RESET);

        while (!leapFrogJoin.isAtEnd()) {
            leapFrogJoin.leapfrogNext();
            System.out.println(ConsoleColorsHelper.BLUE + "mid element: " + leapFrogJoin.getKey() + ConsoleColorsHelper.RESET);
        }

//        System.out.println("tail element: " + leapFrogJoin.getKey());
    }

}
