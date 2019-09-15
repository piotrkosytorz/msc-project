package main;

import algorithms.joinplan.hashjoin.HashJoinQueryResolver;
import algorithms.lftj.LeapFrogTrieJoinQueryResolver;
import managers.DataManager;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.RunnerException;
import query.Atom;
import query.Query;
import query.Relation;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Benchmark)
@Fork(value = 1)
@Warmup(iterations = 1)
@Measurement(iterations = 2)
public class BenchmarkRunner2 {

    @Param({"10", "100", "1000", "2000", "3000", "4000", "5000", "6000", "7000", "8000", "9000", "10000"})
    private int N;

    String localDir = System.getProperty("user.dir");
    String path = localDir + "/src/main/resources/Wiki-Vote.txt";

    Relation<Integer> relA;
    Relation<Integer> relB;
    Relation<Integer> relC;

    Query<Integer> query;

    public static void main(String[] args) throws IOException, RunnerException {
        org.openjdk.jmh.Main.main(args);
    }

    @Setup
    public void setup() throws Exception {
        List<String[]> dataArray = DataManager.importFromFile(path).subList(0, N);
        relA = DataManager.convertToIntegerRelation(dataArray);
        relB = relA.clone();
        relC = relA.clone();
        query = new Query<>(
                new Atom<>(relA, "a", "b"),
                new Atom<>(relB, "b", "c"),
                new Atom<>(relC, "a", "c")
        );
    }

    @Benchmark
    public void leapFrogTrieJoin(Blackhole bh) throws Exception {

        // bootstrap
        query.bootstrap(new LeapFrogTrieJoinQueryResolver());
        // resolve
        List<Map<String, Integer>> res = query.resolve();

        bh.consume(res);
    }

    @Benchmark
    public void hash_join(Blackhole bh) throws Exception {

        // bootstrap
        query.bootstrap(new HashJoinQueryResolver());
        // resolve
        List<Map<String, Integer>> res = query.resolve();

        bh.consume(res);
    }
}
