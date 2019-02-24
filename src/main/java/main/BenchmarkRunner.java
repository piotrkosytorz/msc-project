package main;

import algorithms.lftj.LeapFrogTrieJoinQueryResolver;
import algorithms.nestedloop.IterativeNestedLoopJoinQueryResolver;
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
@Fork(value = 1, jvmArgs = {"-Xms2G", "-Xmx2G"})
public class BenchmarkRunner {

    @Param({"10", "20", "30", "50", "100"}) //, "700", "2000", "5000", "20000", "50000", "100000"})
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
        List<Map<String, Integer>> res = query.resolve(new LeapFrogTrieJoinQueryResolver());
        bh.consume(res);
    }

    @Benchmark
    public void nestedLoopJoin(Blackhole bh) throws Exception {
        List<Map<String, Integer>> res = query.resolve(new IterativeNestedLoopJoinQueryResolver());
        bh.consume(res);
    }
}
