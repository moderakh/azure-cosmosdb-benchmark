package moderakh;

import com.google.common.collect.ImmutableMap;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.HashMap;

public class HashMapVsImmutableMap {

    @Benchmark
    @BenchmarkMode({Mode.Throughput})
    public static void hashMap() {
        HashMap<String, String> map = new HashMap<>(8);

        map.put("x-ms-eumkfrjikgfj", "x");
        map.put("x-ms-eumkds3ffrjikgfj", "y");
        map.put("x-ms-eumkf3htrjikgfj", "z");
        map.put("x-ms-eumkfrfghjikgfj", "g");
        map.put("x-ms-eumkff4ghrjikgfj", "e");
        map.put("x-ms-eumkferjikgfj", "a");
        map.put("x-ms-eumkf46dfrjikgfj", "c");
        map.put("x-ms-eumkfrdhjikgfj", "r");

        map.put("x-ms-eumkfrjikgfj1", "x");
        map.put("x-ms-eumkds3ffrjikgfj2", "y");
        map.put("x-ms-eumkf3htrjikgfj3", "z");
        map.put("x-ms-eumkfrfghjikgfj4", "g");
        map.put("x-ms-eumkff4ghrjikgfj5", "e");
        map.put("x-ms-eumkferjikgfj6", "a");
        map.put("x-ms-eumkf46dfrjikgfj7", "c");
        map.put("x-ms-eumkfrdhjikgfj8", "r");


        map.get("x-ms-eumkfrjikgfj");
        map.get("x-ms-eumkf46dfrjikgfj");
        map.get("x-ms-eumkfrdhjikgfj");
        map.get("x-ms-eumkfrdhjikgfj");
        map.get("x-ms-eumkfrdhjikgfj");
        map.get("x-ms-eumkfrdhjikgfj");
        map.get("x-ms-eumkfrdhjikgfj");
        map.get("x-ms-eumkf46dfrjikgfj");
        map.get("x-ms-eumkf46dfrjikgfj");
        map.get("x-ms-eumkf46dfrjikgfj");

    }

    @Benchmark
    @BenchmarkMode({Mode.Throughput})
    public static void immutableMap() {
        ImmutableMap.Builder<String, String> builder = ImmutableMap.builderWithExpectedSize(8);

        builder.put("x-ms-eumkfrjikgfj", "x");
        builder.put("x-ms-eumkds3ffrjikgfj", "y");
        builder.put("x-ms-eumkf3htrjikgfj", "z");
        builder.put("x-ms-eumkfrfghjikgfj", "g");
        builder.put("x-ms-eumkff4ghrjikgfj", "e");
        builder.put("x-ms-eumkferjikgfj", "a");
        builder.put("x-ms-eumkf46dfrjikgfj", "c");
        builder.put("x-ms-eumkfrdhjikgfj", "r");

        builder.put("x-ms-eumkfrjikgfj1", "x");
        builder.put("x-ms-eumkds3ffrjikgfj2", "y");
        builder.put("x-ms-eumkf3htrjikgfj3", "z");
        builder.put("x-ms-eumkfrfghjikgfj4", "g");
        builder.put("x-ms-eumkff4ghrjikgfj5", "e");
        builder.put("x-ms-eumkferjikgfj6", "a");
        builder.put("x-ms-eumkf46dfrjikgfj7", "c");
        builder.put("x-ms-eumkfrdhjikgfj8", "r");

        ImmutableMap<String, String> map = builder.build();

        map.get("x-ms-eumkfrjikgfj");
        map.get("x-ms-eumkf46dfrjikgfj");
        map.get("x-ms-eumkfrdhjikgfj");
        map.get("x-ms-eumkfrdhjikgfj");
        map.get("x-ms-eumkfrdhjikgfj");
        map.get("x-ms-eumkfrdhjikgfj");
        map.get("x-ms-eumkfrdhjikgfj");
        map.get("x-ms-eumkf46dfrjikgfj");
        map.get("x-ms-eumkf46dfrjikgfj");
        map.get("x-ms-eumkf46dfrjikgfj");
    }

    /**
     * To run this benchmark:
     *
     * java -jar target/azure-cosmosdb-benchmarks.jar moderakh.HashMapVsImmutableMap   -wi 5 -i 5 -f 1 -t 100
     *
     * @param args
     * @throws RunnerException
     */
    public static void main(String[] args) throws RunnerException {
        Options options = new OptionsBuilder()
                .include(HashMapVsImmutableMap.class.getSimpleName())
                .forks(1)
                .build();

        new Runner(options).run();
    }

}
