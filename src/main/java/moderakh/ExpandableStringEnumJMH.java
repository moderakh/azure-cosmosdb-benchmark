package moderakh;

import com.azure.core.util.Configuration;
import com.azure.core.util.ExpandableStringEnum;
import com.azure.core.util.logging.ClientLogger;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.slf4j.LoggerFactory;
@State(Scope.Thread)

public class ExpandableStringEnumJMH {

    public static class ConsistencyExpandableEnumString extends com.azure.core.util.ExpandableStringEnum {
        public static final ConsistencyExpandableEnumString Strong = fromString("Strong");
        public static final ConsistencyExpandableEnumString Eventual = fromString("Eventual");

        public static ConsistencyExpandableEnumString fromString(String name) {
            return ExpandableStringEnum.fromString(name, ConsistencyExpandableEnumString.class);
        }
    }

    @Setup(Level.Trial)
    public void setUp() {
        ConsistencyExpandableEnumString eec = new ConsistencyExpandableEnumString();
        eec.fromString("Strong");
    }

    @Benchmark
    @BenchmarkMode({Mode.Throughput})
    public void cosmosStringEnum() {
        String requestConsistencyLevelAsString = "Eventual";
        ConsistencyLevel requestConsistencyLevel = ConsistencyLevel.readFromStringUsingHashMapImplementation(requestConsistencyLevelAsString);
    }

    @Benchmark
    @BenchmarkMode({Mode.Throughput})
    public void azureCoreExpandableEnum() {
        String requestConsistencyLevelAsString = "Eventual";
        ConsistencyExpandableEnumString requestConsistencyLevel = ConsistencyExpandableEnumString.fromString(requestConsistencyLevelAsString);
    }

    /**
     * To run this benchmark:
     *
     * java -jar target/azure-cosmosdb-benchmarks.jar moderakh.ExpandableStringEnumJMH   -wi 5 -i 5 -f 1 -t 100
     *
     * @param args
     * @throws RunnerException
     */
    public static void main(String[] args) throws RunnerException {
        Options options = new OptionsBuilder()
                .include(ExpandableStringEnumJMH.class.getSimpleName())
                .forks(1)
                .build();

        new Runner(options).run();
    }
}
