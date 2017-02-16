package analyzeStream;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Created by Evegeny on 16/02/2017.
 */
public class BenchmarkSum {



    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Benchmark
    public int testParallelUnorderedSum(BenchmarkState state) throws Exception {
        List<Employee> employees = state.employees;
       return employees.parallelStream().unordered().mapToInt(Employee::getSalary).sum();
      /*  int total=0;
        for (Employee employee : employees) {
            total+= employee.getSalary();
        }
        return total;*/
    }


    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(".*" + BenchmarkSum.class.getSimpleName() + ".*")
                .forks(1).warmupIterations(15)
                .build();

        new Runner(opt).run();
    }


    @State(Scope.Benchmark)
    public static class BenchmarkState {
        List<Employee> employees;

        public BenchmarkState() {
            employees = new ArrayList<>();
            Random random = new Random();

            int iterNum = 10000000;
            for (int i = 0; i < iterNum; i++) {
                employees.add(new Employee(random.nextInt(100)));

            }

        }
    }
}
