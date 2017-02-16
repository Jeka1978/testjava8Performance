package analyzeStream;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * Created by Jeka on 15/07/2014.
 */
public class BenchmarkMyStream {

    public static final int SIZE = 100000;

    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Benchmark
    public Optional<Integer> testGetPrimeNumbersWithIterate(BenchmarkState state) throws Exception {
        return state.primes.stream().parallel().reduce((integer, integer2) -> {
//            Blackhole.consumeCPU(10000);
            return integer + integer2;
        });
    }

    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Benchmark
    public List<Integer> testGetPrimeNumbersWithSpliteratorSeq() throws Exception {
        return getPrimeNumbersWithIteratorSpliterator().unordered().parallel().limit(SIZE).collect(Collectors.toList());
    }

    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
//    @Benchmark
    public static List<Integer> getWedNumbersOldSchool() {
        ArrayList<Integer> primes = new ArrayList<>();
        int candidate = 1;
        int sum = 0;
        while (primes.size()<SIZE){
            boolean isPrime = false;
            while (!isPrime) {
                candidate++;
                isPrime = true;
                for (int i = 2; i <= Math.sqrt(candidate); i++) {
                    if (candidate % i == 0) {
                        isPrime = false;
                        break;
                    }
                }
                primes.add(candidate);
            }
        }
       /* for (Integer prime : primes) {
            Blackhole.consumeCPU(10000);
            sum += prime;
        }
        return sum;*/
        return primes;
    }

    public static Stream<Integer> getPrimeNumbersWithIteratorSpliterator() {
        Iterator<Integer> primeNumIterator = new Iterator<Integer>() {

            private int last=1;
            @Override
            public boolean hasNext() {
                return true;
            }

            @Override
            public Integer next() {
                boolean isPrime = false;
                int candidate=last;
                while (!isPrime) {
                    candidate++;
                    isPrime=true;
                    for (int i = 2; i <= Math.sqrt(candidate); i++) {
                        if (candidate % i == 0) {
                            isPrime = false;
                            break;
                        }
                    }
                }
                last = candidate;
                return candidate;
            }

        };
        Spliterator<Integer> spliterator = Spliterators.spliterator(primeNumIterator, Integer.MAX_VALUE,
                Spliterator.NONNULL | Spliterator.IMMUTABLE);

        return StreamSupport.stream(spliterator, true);
    }

    public static Stream<Integer> getPrimeNumbersWithGenerate(){
        return Stream.generate(new Supplier<Integer>() {
            public Integer num = 1;

            @Override
            public Integer get() {
                boolean isPrime = false;
                int candidate = num;
                while (!isPrime) {
                    candidate++;
                    isPrime = true;
                    for (int i = 2; i <= Math.sqrt(candidate); i++) {
                        if (candidate % i == 0) {
                            isPrime = false;
                            break;
                        }
                    }
                }
                num = candidate;
                return candidate;
            }




        });
    }


    public static Stream<Integer> getPrimeNumbersWithIterator(){
        return Stream.iterate(2, num -> {
            boolean isPrime = false;
            int candidate = num;
            while (!isPrime) {
                candidate++;
                isPrime = true;
                for (int i = 2; i <= Math.sqrt(candidate); i++) {
                    if (candidate % i == 0) {
                        isPrime = false;
                        break;
                    }
                }
            }
            return candidate;
        });
    }



    @State(Scope.Benchmark)
    public static class BenchmarkState {
        List<Integer> primes;

        public BenchmarkState() {
            primes = getWedNumbersOldSchool();
        }
    }


    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(".*" + BenchmarkMyStream.class.getSimpleName() + ".*")
                .forks(1).warmupIterations(15)
                .build();

        new Runner(opt).run();
    }
}
