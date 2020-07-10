package kuthapar;

import com.fasterxml.uuid.EthernetAddress;
import com.fasterxml.uuid.Generators;
import com.fasterxml.uuid.impl.TimeBasedGenerator;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.UUID;

public class JugUUIDGenerator {

    @Benchmark
    @BenchmarkMode({ Mode.Throughput})
    public static void jugUUIDGenerator() {
        TimeBasedGenerator timeBasedGenerator = Generators.timeBasedGenerator(EthernetAddress.constructMulticastAddress());
        UUID generate = timeBasedGenerator.generate();
    }

    /**
     * To run this benchmark:
     *
     * By default it runs with version 4.0.1 - specified in pom.xml
     * To run it again with version v3.2.0 - update pom.xml and run it again
     *
     * java -jar target/azure-cosmosdb-benchmarks.jar kuthapar.JugUUIDGenerator   -wi 5 -i 5 -f 1 -t 100
     *
     * @param args
     * @throws RunnerException
     */
    public static void main(String[] args) throws RunnerException {
        Options options = new OptionsBuilder()
            .include(JugUUIDGenerator.class.getSimpleName())
            .forks(1)
            .build();

        new Runner(options).run();
    }
}
