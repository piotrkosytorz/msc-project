package hashjoin;

import algorithms.hashjoin.HashJoinQueryResolver;
import org.junit.Test;
import query.Atom;
import query.Query;
import query.Relation;
import query.Tuple;

import java.util.List;
import java.util.Map;

public class JoinPlanReducerTest {

    @Test
    public void reduceTest() throws Exception {

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

        Query<Integer> query = new Query<>(
                new Atom<>(relR, "a", "b"),
                new Atom<>(relS, "b", "c"),
                new Atom<>(relT, "a", "c")
        );

        HashJoinQueryResolver resolver = new HashJoinQueryResolver<Integer>();
        resolver.bootstrap(query);
        List<Map<String, Integer>> result = resolver.getFullResult();

        System.out.println(result);

    }

}
