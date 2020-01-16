package moderakh;

import com.azure.core.util.Configuration;
import com.azure.core.util.logging.ClientLogger;
import com.azure.core.util.logging.LogLevel;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.slf4j.LoggerFactory;

@State(Scope.Thread)
public class AzureCoreLoggerVsSlf4jPerf {

    org.slf4j.Logger slf4jLogger;
    ClientLogger azureLogger;

    @Setup(Level.Trial)
    public void setUp() {
        slf4jLogger = LoggerFactory.getLogger(AzureCoreLoggerVsSlf4jPerf.class);
        azureLogger = new ClientLogger(AzureCoreLoggerVsSlf4jPerf.class);

        Configuration.getGlobalConfiguration().put("AZURE_LOG_LEVEL", "INFORMATIONAL");
    }

    @TearDown
    public void cleanUp() {
    }

    @Benchmark
    @BenchmarkMode({Mode.Throughput})
    public void logInfo_InfoIsEnabled_AzureLogger() {
        azureLogger.info("hello, world");
    }

    @Benchmark
    @BenchmarkMode({Mode.Throughput})
    public void logInfo_InfoIsEnabled_sf4j() {
        slf4jLogger.info("hello, world");
    }

    @Benchmark
    @BenchmarkMode({Mode.Throughput})
    public void logDebug_DebugIsDisabled_AzureLogger() {
        azureLogger.verbose("hello, world");
    }

    @Benchmark
    @BenchmarkMode({Mode.Throughput})
    public void logDebug_DebugIsDisabled_SLF4J() {
        slf4jLogger.debug("hello, world");
    }

    @Benchmark
    @BenchmarkMode({Mode.Throughput})
    public void logInfo_CheckLevel_InfoIsEnabled_AzureLogger() {
        if (azureLogger.canLogAtLevel(LogLevel.INFORMATIONAL)) {
            azureLogger.info("hello, world");
        }
    }

    @Benchmark
    @BenchmarkMode({Mode.Throughput})
    public void logInfo_CheckLevel_InfoIsEnabled_SLF4J() {
        if (slf4jLogger.isInfoEnabled()) {
            slf4jLogger.info("hello, world");
        }
    }

    @Benchmark
    @BenchmarkMode({Mode.Throughput})
    public void logDebug_CheckLevel_DebugIsDisabled_AzureLogger() {
        if (azureLogger.canLogAtLevel(LogLevel.VERBOSE)) {
            azureLogger.verbose("hello, world");
        }
    }

    @Benchmark
    @BenchmarkMode({Mode.Throughput})
    public void logDebug_CheckLevel_DebugIsDisabled_SLF4J() {
        if (slf4jLogger.isDebugEnabled()) {
            slf4jLogger.debug("hello, world");
        }
    }

    @Benchmark
    @BenchmarkMode({Mode.Throughput})
    public void logInfoWithArgs_InfoIsEnabled_AzureLogger() {
        azureLogger.info("hello, world {}, {}, {}", 245786498797L, this, "xyz");
    }

    @Benchmark
    @BenchmarkMode({Mode.Throughput})
    public void logInfoWithArgs_InfoIsEnabled_SLF4J() {
        slf4jLogger.info("hello, world {}", 245786498797L, this, "xyz");
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
            .include(AzureCoreLoggerVsSlf4jPerf.class.getSimpleName())
            .forks(1)
            .build();

        new Runner(options).run();
    }
}
