package moderakh;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.nio.CharBuffer;

public class Utils {

    public static boolean startsWith(CharSequence str, char expectedChar) {
        if (str.length() == 0) {
            return false;
        }

        return str.charAt(0) == expectedChar;
    }

    public static boolean endsWith(CharSequence str, char expectedChar) {
        if (str.length() == 0) {
            return false;
        }

        return str.charAt(str.length()-1) == expectedChar;
    }

    public static CharBuffer trimBeginningAndEndingSlashes(CharSequence path) {
        if(path == null) {
            return null;
        }

        int startIndex;
        if (startsWith(path, '/')) {
            startIndex = 1;
        } else {
            startIndex = 0;
        }

        int endIndex;
        if (endsWith(path, '/')) {
            endIndex = path.length() - 1;
        } else {
            endIndex = path.length();
        }

        return CharBuffer.wrap(path, startIndex, endIndex);
    }


    public static String trimBeginningAndEndingSlashesOld(String path) {
        if(path == null) {
            return null;
        }

        if (path.startsWith("/")) {
            path = path.substring(1);
        }

        if (path.endsWith("/")) {
            path = path.substring(0, path.length() - 1);
        }

        return path;
    }


    @Benchmark
    @BenchmarkMode({Mode.Throughput})
    public void trimBeginningAndEndingSlashes_CharBuff() {
        CharSequence value = Utils.trimBeginningAndEndingSlashes("/dbs/xyz/rsv/");
    }

    @Benchmark
    @BenchmarkMode({Mode.Throughput})
    public void trimBeginningAndEndingSlashes_Substring() {
        CharSequence value = Utils.trimBeginningAndEndingSlashesOld("/dbs/xyz/rsv/");
    }

    public static void main(String[] args) throws RunnerException {
        Options options = new OptionsBuilder()
                .include(Utils.class.getSimpleName())
                .forks(1)
                .build();

        new Runner(options).run();
    }
}
