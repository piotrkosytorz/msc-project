package main;

import algorithms.lftj.LeapFrogTrieJoinQueryResolver;
import managers.DataManager;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.RunnerException;
import query.Atom;
import query.Query;
import query.Relation;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Benchmark)
@Fork(value = 1)
@Warmup(iterations = 0)
@Measurement(iterations = 5)
public class BenchmarkIndexing {

    @Param({"1", "5", "10", "20", "30", "40", "50", "100", "200", "300", "400", "500", "600", "700", "800", "900", "1000", "2000", "3000", "4000", "5000", "6000", "7000", "8000", "9000", "10000", "20000", "30000", "40000", "50000", "60000", "70000", "80000", "90000", "100000"})
    private int N;

    String localDir = System.getProperty("user.dir");
    String path = localDir + "/src/main/resources/Wiki-Vote.txt";

    Relation<Integer> relA;
    Relation<Integer> relB;
    Relation<Integer> relC;

    Query<Integer> query;
    Query<Integer> query2;

    public static void main(String[] args) throws IOException, RunnerException {
        org.openjdk.jmh.Main.main(args);
    }

    @Setup
    public void setup() throws Exception {
        List<String[]> dataArray = DataManager.importFromFile(path).subList(0, N);

        Collections.shuffle(dataArray);

        relA = DataManager.convertToIntegerRelation(dataArray);
        relB = relA.clone();
        relC = relA.clone();

        query = new Query<>(
                new Atom<>(relA, "a", "b"),
                new Atom<>(relB, "b", "c"),
                new Atom<>(relC, "a", "c")
        );

        query2 = new Query<>(
                new Atom<>(relA.clone(), "a", "b"),
                new Atom<>(relB.clone(), "b", "c"),
                new Atom<>(relC.clone(), "a", "c")
        );

    }

    @Benchmark
    public void LFTJ_indexing(Blackhole bh) throws Exception {
        // bootstrap
        query2.bootstrap(new LeapFrogTrieJoinQueryResolver());
    }

    @Benchmark
    public void LFTJ(Blackhole bh) throws Exception {

        // bootstrap
        query.bootstrap(new LeapFrogTrieJoinQueryResolver());
        // resolve
        List<Map<String, Integer>> res = query.resolve();

        bh.consume(res);
    }

}
