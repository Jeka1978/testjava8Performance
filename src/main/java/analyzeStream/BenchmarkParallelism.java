package analyzeStream;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * Created by Jeka on 15/07/2014.
 */
public class BenchmarkParallelism {
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Benchmark
    public List<String> testMethodSequential(BenchmarkState state) throws Exception {
        return state.words.stream().sorted().collect(Collectors.toList());
    }

    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Benchmark
    public List<String> testMethodParallel(BenchmarkState state) throws Exception {
        return state.words.parallelStream().sorted().collect(Collectors.toList());
    }




    @State(Scope.Benchmark)
    public static class BenchmarkState {
        List<String> words;

        public BenchmarkState() {
            words = new ArrayList<>();
            for (int i = 0; i < 500000; i++) {
                words.add(String.valueOf(i));
            }
        }
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(".*" + BenchmarkParallelism.class.getSimpleName() + ".*")
                .forks(1).warmupIterations(15)
                .build();

        new Runner(opt).run();
    }
}
