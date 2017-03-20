package or;

import analyzeStream.BenchmarkMyStream;
import org.fluttercode.datafactory.impl.DataFactory;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static analyzeStream.BenchmarkMyStream.getPrimeNumbersWithIteratorSpliterator;

/**
 * Created by Evegeny on 20/03/2017.
 */
public class BenchmarkOr {


    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    @Benchmark
    public void testOldSchool(BenchmarkState state) throws Exception {
        state.service.doForeachOldSchool(state.persons);
    }

    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    @Benchmark
    public void testWithStream(BenchmarkState state) throws Exception {
        state.service.doForeachWithStream(state.persons);
    }

    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    @Benchmark
    public void testParallelStream(BenchmarkState state) throws Exception {
        state.service.doForeachWithParallelStream(state.persons);
    }


    @State(Scope.Benchmark)
    public static class BenchmarkState {
        List<Person> persons;
        ListService service;

        public BenchmarkState() {
            persons = new ArrayList<>();
            for (int i = 0; i < 1_000_000; i++) {
                persons.add(new Person("jeka"));
            }
            service = new ListService();
        }
    }


    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(".*" + BenchmarkOr.class.getSimpleName() + ".*")
                .forks(1).warmupIterations(15)
                .build();

        new Runner(opt).run();
    }
}
