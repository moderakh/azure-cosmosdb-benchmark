package com.azure.cosmos.implementation.directconnectivity.rntbd;

import com.google.common.collect.ImmutableMap;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

public class RntbdTokenStreamJMH {



    @Benchmark
    @BenchmarkMode({Mode.Throughput})
    public void collector(MyState myState) {
        ImmutableMap<RntbdConstants.RntbdRequestHeader, RntbdToken> results = myState.set.stream().collect(myState.collector);
        results.get(RntbdConstants.RntbdRequestHeader.PartitionKey);
    }

    @Benchmark
    @BenchmarkMode({Mode.Throughput})
    public void builder(MyState myState) {
        ImmutableMap.Builder builder = ImmutableMap.builderWithExpectedSize(myState.set.size());
        for (RntbdConstants.RntbdRequestHeader e : myState.set) {
            builder.put(e, RntbdToken.create(e));
        }

        ImmutableMap<RntbdConstants.RntbdRequestHeader, RntbdToken> results = builder.build();
        results.get(RntbdConstants.RntbdRequestHeader.PartitionKey);
    }

    /**
     * To run this benchmark:
     *
     * java -jar target/benchmarks.jar -wi 2 -i 2 -f 1 -t 100
     *
     * @param args
     * @throws RunnerException
     */
    public static void main(String[] args) throws RunnerException {
        Options options = new OptionsBuilder()
                .include(RntbdTokenStreamJMH.class.getSimpleName())
                .forks(1)
                .build();

        new Runner(options).run();
    }
}
