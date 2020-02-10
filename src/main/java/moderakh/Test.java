package moderakh;

import org.apache.commons.lang3.RandomStringUtils;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

public class Test {

    @State(Scope.Thread)
    static public class PK {
        String partitionKeyJson = RandomStringUtils.randomAlphanumeric(20) + "سردرد";
    }


    @Benchmark
    @BenchmarkMode({Mode.Throughput})
    public static String escapeNonAsciiNew(PK pk) {
        String partitionKeyJson = pk.partitionKeyJson;
        // if all are ascii original string will be returned, and avoids copying data.
        StringBuilder sb = null;
        for(int i = 0; i < partitionKeyJson.length(); i++) {
            int val = partitionKeyJson.charAt(i);
            if (val > 127) {
                if (sb == null) {
                    sb = new StringBuilder(partitionKeyJson.length());
                    sb.append(partitionKeyJson.substring(0, i));
                }
                sb.append("\\u").append(String.format("%04X", val));
            } else {
                if (sb != null) {
                    sb.append(partitionKeyJson.charAt(i));
                }
            }
        }

        if (sb == null) {
            // all are ascii character
            return partitionKeyJson;
        } else {
            return sb.toString();
        }
    }

    @Benchmark
    @BenchmarkMode({Mode.Throughput})
    public static String escapeNonAscii(PK pk) {
        String partitionKeyJson = pk.partitionKeyJson;
        // if all are ascii original string will be returned, and avoids copying data.
        StringBuilder sb = new StringBuilder(partitionKeyJson.length());
        for(int i = 0; i < partitionKeyJson.length(); i++) {
            int val = partitionKeyJson.charAt(i);
            if (val > 127) {
                sb.append("\\u").append(String.format("%04X", val));
            } else {
                sb.append(partitionKeyJson.charAt(i));
            }
        }

        return sb.toString();
    }


    public static void main(String[] args) throws RunnerException {
        Options options = new OptionsBuilder()
                .include(Test.class.getSimpleName())
                .forks(1)
                .build();

        new Runner(options).run();
    }

}
