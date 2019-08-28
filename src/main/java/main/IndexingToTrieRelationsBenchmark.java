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
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Benchmark)
@Fork(value = 1)
public class IndexingToTrieRelationsBenchmark {

    @Param({"10", "10000", "10000", "20000", "30000", "40000", "50000", "60000", "70000", "80000", "90000", "100000"})
    private int N;

    String localDir = System.getProperty("user.dir");
    String path = localDir + "/src/main/resources/Wiki-Vote.txt";

    List<String[]> dataArray;

    public static void main(String[] args) throws IOException, RunnerException {
        org.openjdk.jmh.Main.main(args);
    }

    @Setup
    @SuppressWarnings("Duplicates")
    public void setup() throws Exception {
        this.dataArray = DataManager.importFromFile(path).subList(0, N);
    }

    @Benchmark
    public void indexRelation(Blackhole bh) throws Exception {
        relA = DataManager.convertToIntegerRelation(dataArray);
        bh.consume(relA);
    }

}
