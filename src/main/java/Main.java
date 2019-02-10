import algorithms.lftj.LeapFrogTrieJoin;
import algorithms.nestedloop.NestedLoopJoin;
import query.Atom;
import query.Query;
import query.Relation;
import query.Tuple;

import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) throws Exception {

        Relation<String> relR = new Relation<>(2);
        Relation<String> relS = new Relation<>(2);
        Relation<String> relT = new Relation<>(2);

        relR.addAll(
                new Tuple<>("3", "6"),
                new Tuple<>("6", "4"),
                new Tuple<>("7", "4")
        );

        relS.addAll(
                new Tuple<>("3", "1"),
                new Tuple<>("3", "2"),
                new Tuple<>("3", "3"),
                new Tuple<>("4", "0"),
                new Tuple<>("4", "1"),
                new Tuple<>("4", "2"),
                new Tuple<>("4", "3"),
                new Tuple<>("4", "4"),
                new Tuple<>("4", "5"),
                new Tuple<>("4", "9")
        );

        relT.addAll(
                new Tuple<>("6", "2"),
                new Tuple<>("6", "3"),
                new Tuple<>("7", "0"),
                new Tuple<>("7", "1"),
                new Tuple<>("7", "2"),
                new Tuple<>("7", "3"),
                new Tuple<>("7", "5")
        );

        Query<String> query = new Query<>(
                new Atom<>(relR, "a", "b"),
                new Atom<>(relS, "b", "c"),
                new Atom<>(relT, "a", "c")
        );

        List<Map<String, String>> res;
        long startTime;
        long endTime;

        try {
            startTime = System.currentTimeMillis();
            res = query.resolve(new NestedLoopJoin());
            endTime = System.currentTimeMillis();
            System.out.println("Nested loop total execution time: \n\t" + (endTime - startTime) + "ms\n");
            System.out.println("Nested loop result: \n\t" + res);
        } catch (Exception e) {
            System.out.println("Nested loop fail!: \n\t" + e.getLocalizedMessage());
        }

        System.out.println("\n======================================\n");

//        try {
        startTime = System.currentTimeMillis();
        res = query.resolve(new LeapFrogTrieJoin());
        endTime = System.currentTimeMillis();

        System.out.println("LFTJ total execution time: \n\t" + (endTime - startTime) + "ms\n");
        System.out.println("LFTJ result: \n\t" + res);
//        } catch (Exception e) {
//            System.out.println("LFTJ fail!: \n\t" + e.getLocalizedMessage());
//        }
    }

}
