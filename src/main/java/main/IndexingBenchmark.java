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
@Fork(value = 1)
public class IndexingBenchmark {

    @Param({"10", "50", "150" /*, "200", "250", "300", "350", "400", "450", "500" */})
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
    public void LFTJ_bootstrap(Blackhole bh) throws Exception {

        // bootstrap
        query.bootstrap(new LeapFrogTrieJoinQueryResolver());

    }

    @Benchmark
    public void nested_loop_bootstrap(Blackhole bh) throws Exception {

        // bootstrap
        query.bootstrap(new IterativeNestedLoopJoinQueryResolver());
    }

}
