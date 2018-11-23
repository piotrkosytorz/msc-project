import algorithms.Atom;
import algorithms.nestedloop.NestedLoopJoin;
import joiner.Joiner;
import joiner.datastructures.Relation;
import joiner.datastructures.Tuple;

import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) {

        Relation<Integer> relR = new Relation<>(2);
        Relation<Integer> relS = new Relation<>(2);
        Relation<Integer> relT = new Relation<>(2);

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

        Joiner<Integer> joiner = new Joiner<>(
                new Atom<>(relR, "a", "b"),
                new Atom<>(relS, "b", "c"),
                new Atom<>(relT, "a", "c")
        );

        long startTime = System.currentTimeMillis();
        List<Map<String, Integer>> res = joiner.run(new NestedLoopJoin());
        long endTime = System.currentTimeMillis();

        System.out.println("Nested loop total execution time: \n\t" + (endTime - startTime) + "ms\n");
        System.out.println("Nested loop result: \n\t" + res);

    }

}
