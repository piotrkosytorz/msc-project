import algorithms.lftj.LeapFrogTrieJoinQueryResolver;
import algorithms.nestedloop.IterativeNestedLoopJoinQueryResolver;
import algorithms.nestedloop.RecursiveNestedLoopJoinQueryResolver;
import managers.DataManager;
import query.Atom;
import query.Query;
import query.Relation;

import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) throws Exception {

//        Relation<String> relR = new Relation<>(2);
//        Relation<String> relS = new Relation<>(2);
//        Relation<String> relT = new Relation<>(2);
//
//        relR.addAll(
//                new Tuple<>("3", "6"),
//                new Tuple<>("6", "4"),
//                new Tuple<>("7", "4")
//        );
//
//        relS.addAll(
//                new Tuple<>("3", "1"),
//                new Tuple<>("3", "2"),
//                new Tuple<>("3", "3"),
//                new Tuple<>("4", "0"),
//                new Tuple<>("4", "1"),
//                new Tuple<>("4", "2"),
//                new Tuple<>("4", "3"),
//                new Tuple<>("4", "4"),
//                new Tuple<>("4", "5"),
//                new Tuple<>("4", "9")
//        );
//
//        relT.addAll(
//                new Tuple<>("6", "2"),
//                new Tuple<>("6", "3"),
//                new Tuple<>("7", "0"),
//                new Tuple<>("7", "1"),
//                new Tuple<>("7", "2"),
//                new Tuple<>("7", "3"),
//                new Tuple<>("7", "5")
//        );
//
//        Query<String> query = new Query<>(
//                new Atom<>(relR, "a", "b"),
//                new Atom<>(relS, "b", "c"),
//                new Atom<>(relT, "a", "c")
//        );

        String localDir = System.getProperty("user.dir");

        String path = localDir + "/src/main/resources/Wiki-Vote.txt";


        long startTime;
        long endTime;

        // data import

        startTime = System.currentTimeMillis();
        List<String[]> dataArray = DataManager.importFromFile(path);
        endTime = System.currentTimeMillis();
        System.out.println("File import time " + (endTime - startTime) + "ms\n");

        // conversion to relations

        long importersStartTime = System.currentTimeMillis();
        startTime = System.currentTimeMillis();
        Relation<Integer> relA = DataManager.convertToIntegerRelation(dataArray);
        endTime = System.currentTimeMillis();
        System.out.println("Data converted " + (endTime - startTime) + "ms\n");

        startTime = System.currentTimeMillis();
        Relation<Integer> relB = relA.clone();
        endTime = System.currentTimeMillis();

        System.out.println("Rel cloned: " + (endTime - startTime) + "ms\n");

        startTime = System.currentTimeMillis();
        Relation<Integer> relC = relB.clone();
        endTime = System.currentTimeMillis();

        System.out.println("Rel cloned: " + (endTime - startTime) + "ms\n");

        System.out.println("Importers execution time: \n\t" + (endTime - importersStartTime) + "ms\n");

        startTime = System.currentTimeMillis();
        Query<Integer> query = new Query<>(
                new Atom<>(relA, "a", "b"),
                new Atom<>(relB, "b", "c"),
                new Atom<>(relC, "a", "c")
        );
        endTime = System.currentTimeMillis();
        System.out.println("Query builder time: \n\t" + (endTime - startTime) + "ms\n");

        List<Map<String, Integer>> res;
//
//
//        try {
//            startTime = System.currentTimeMillis();
//            res = query.resolve(new RecursiveNestedLoopJoinQueryResolver());
//            endTime = System.currentTimeMillis();
//            System.out.println("Recursive nested loop total execution time: \n\t" + (endTime - startTime) + "ms\n");
//            System.out.println("Recursive nested loop result: \n\t" + res);
//        } catch (Exception e) {
//            System.out.println("Recursive nested loop fail!: \n\t" + e.getLocalizedMessage());
//        }
//
//        System.out.println("\n======================================\n");
//
//        try {
//            startTime = System.currentTimeMillis();
//            res = query.resolve(new IterativeNestedLoopJoinQueryResolver());
//            endTime = System.currentTimeMillis();
//            System.out.println("Iterative nested loop total execution time: \n\t" + (endTime - startTime) + "ms\n");
//            System.out.println("Iterative nested loop result: \n\t" + res);
//        } catch (Exception e) {
//            System.out.println("Iterative nested loop fail!: \n\t" + e.getLocalizedMessage());
//        }
//
//        System.out.println("\n======================================\n");


        try {
            startTime = System.currentTimeMillis();
            res = query.resolve(new LeapFrogTrieJoinQueryResolver());
            endTime = System.currentTimeMillis();

            System.out.println("LFTJ number of results: " + res.size());
            System.out.println("LFTJ total execution time: \n\t" + (endTime - startTime) + "ms\n");
//            System.out.println("LFTJ result: \n\t" + res);
        } catch (Exception e) {
            System.out.println("LFTJ fail!: \n\t" + e.getLocalizedMessage());
            System.out.println(e.getStackTrace());
        }

    }

}
