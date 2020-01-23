package moderakh;

import org.apache.commons.lang3.EnumUtils;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

public class Enums {


    @Benchmark
    @BenchmarkMode({Mode.Throughput})
    public void enumUtils_BoundedStaleness() {
        String requestConsistencyLevelAsString = "BoundedStaleness";
        ConsistencyLevel requestConsistencyLevel = EnumUtils.getEnum(ConsistencyLevel.class, Strings.fromCamelCaseToUpperCase(requestConsistencyLevelAsString));
    }

    @Benchmark
    @BenchmarkMode({Mode.Throughput})
    public void enumUtils_Session() {
        String requestConsistencyLevelAsString = "Session";
        ConsistencyLevel requestConsistencyLevel = EnumUtils.getEnum(ConsistencyLevel.class, Strings.fromCamelCaseToUpperCase(requestConsistencyLevelAsString));
    }

    @BenchmarkMode({Mode.Throughput})
    public void enumUtils_Eventual() {
        String requestConsistencyLevelAsString = "Eventual";
        ConsistencyLevel requestConsistencyLevel = EnumUtils.getEnum(ConsistencyLevel.class, Strings.fromCamelCaseToUpperCase(requestConsistencyLevelAsString));
    }

    @Benchmark
    @BenchmarkMode({Mode.Throughput})
    public void cachedHashMap_Eventual() {
        String requestConsistencyLevelAsString = "Eventual";
        ConsistencyLevel requestConsistencyLevel = ConsistencyLevel.readFromStringUsingHashMapImplementation(requestConsistencyLevelAsString);
    }

    @Benchmark
    @BenchmarkMode({Mode.Throughput})
    public void cachedHashMap_Session() {
        String requestConsistencyLevelAsString = "Session";
        ConsistencyLevel requestConsistencyLevel = ConsistencyLevel.readFromStringUsingHashMapImplementation(requestConsistencyLevelAsString);
    }

    @Benchmark
    @BenchmarkMode({Mode.Throughput})
    public void cachedHashMap_Strong() {
        String requestConsistencyLevelAsString = "Strong";
        ConsistencyLevel requestConsistencyLevel = ConsistencyLevel.readFromStringUsingHashMapImplementation(requestConsistencyLevelAsString);
    }

    @Benchmark
    @BenchmarkMode({Mode.Throughput})
    public void cachedTreeMap_Session() {
        String requestConsistencyLevelAsString = "Session";
        ConsistencyLevel requestConsistencyLevel = ConsistencyLevel.readFromStringUsingTreeMapImplementation(requestConsistencyLevelAsString);
    }

    @Benchmark
    @BenchmarkMode({Mode.Throughput})
    public void cachedTreeMap_Eventual() {
        String requestConsistencyLevelAsString = "Eventual";
        ConsistencyLevel requestConsistencyLevel = ConsistencyLevel.readFromStringUsingTreeMapImplementation(requestConsistencyLevelAsString);
    }

    @Benchmark
    @BenchmarkMode({Mode.Throughput})
    public void cachedTreeMap_Strong() {
        String requestConsistencyLevelAsString = "Strong";
        ConsistencyLevel requestConsistencyLevel = ConsistencyLevel.readFromStringUsingTreeMapImplementation(requestConsistencyLevelAsString);
    }

    @Benchmark
    @BenchmarkMode({Mode.Throughput})
    public void cachedHashMap_BoundedStaleness() {
        String requestConsistencyLevelAsString = "BoundedStaleness";
        ConsistencyLevel requestConsistencyLevel = ConsistencyLevel.readFromStringUsingHashMapImplementation(requestConsistencyLevelAsString);
    }

    @Benchmark
    @BenchmarkMode({Mode.Throughput})
    public void cachedTreeMap_BoundedStaleness() {
        String requestConsistencyLevelAsString = "BoundedStaleness";
        ConsistencyLevel requestConsistencyLevel = ConsistencyLevel.readFromStringUsingTreeMapImplementation(requestConsistencyLevelAsString);
    }

    public static void main(String[] args) throws RunnerException {




        Options options = new OptionsBuilder()
                .include(Enums.class.getSimpleName())
                .forks(1)
                .build();

        new Runner(options).run();
    }
}
