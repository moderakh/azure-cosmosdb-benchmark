package moderakh;

import com.azure.core.util.Configuration;
import com.azure.core.util.logging.ClientLogger;
import com.azure.core.util.logging.LogLevel;
import com.azure.cosmos.implementation.HttpConstants;
import com.azure.cosmos.implementation.Utils;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.slf4j.LoggerFactory;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;

@State(Scope.Thread)
public class AuthTokenStringBuilder {
    String key = "C2y6yDjf5/R+ob0N8A7Cgv30VRDJIWEHLM+4QDU5DE2nQ9nDuVTqobD4b8mGGyPMbIZnqyMsEcaGQy67XIw/Jw==";
    private Mac mac;
    private final Charset charset = StandardCharsets.UTF_8;
    private final CharsetEncoder encoder = charset.newEncoder();

    @Setup(Level.Trial)
    public void setUp() {
        mac = initializeMac();
    }

    private Mac initializeMac() {

        byte[] masterKeyBytes = key.getBytes();
        byte[] masterKeyDecodedBytes = Utils.Base64Decoder.decode(masterKeyBytes);
        SecretKey signingKey = new SecretKeySpec(masterKeyDecodedBytes, "HMACSHA256");
        try {
            Mac macInstance = Mac.getInstance("HMACSHA256");
            macInstance.init(signingKey);
            //  Update the master key hash code
            return macInstance;
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            throw new IllegalStateException(e);
        }
    }

    @TearDown
    public void cleanUp() {
    }

    @Benchmark
    @BenchmarkMode({Mode.Throughput})
    public void stringBuilderToStringToUtf8BytesMacFinal() {
        StringBuilder sb = new StringBuilder();

        String resourceSegment = "docs";
        String resourceIdOrFullName = "dbs/testdb2/colls/testcol2";
        // Skipping lower casing of resourceId since it may now contain "ID" of the resource as part of the FullName
        StringBuilder body = new StringBuilder();
        body.append("POST")
                .append('\n')
                .append(resourceSegment)
                .append('\n')
                .append(resourceIdOrFullName)
                .append('\n');

        body.append("mon, 10 feb 2020 06:07:20 gmt".toLowerCase());
        body.append('\n');
        body.append('\n');

        String payload = sb.toString();
        byte[] results = mac.doFinal(payload.getBytes(StandardCharsets.UTF_8));
    }

    @Benchmark
    @BenchmarkMode({Mode.Throughput})
    public void stringBuilderToCharBufferToByteBufferMacFinal() throws Exception {
        StringBuilder sb = new StringBuilder();

        String resourceSegment = "docs";
        String resourceIdOrFullName = "dbs/testdb2/colls/testcol2";
        // Skipping lower casing of resourceId since it may now contain "ID" of the resource as part of the FullName
        StringBuilder body = new StringBuilder();
        body.append("POST")
                .append('\n')
                .append(resourceSegment)
                .append('\n')
                .append(resourceIdOrFullName)
                .append('\n');

        body.append("mon, 10 feb 2020 06:07:20 gmt".toLowerCase());
        body.append('\n');
        body.append('\n');

        CharBuffer charBuffer = CharBuffer.wrap(sb);
        ByteBuffer byteBuffer = encoder.encode(charBuffer);
        mac.update(byteBuffer);
        byte[] results = mac.doFinal();
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
                .include(AuthTokenStringBuilder.class.getSimpleName())
                .forks(1)
                .build();

        new Runner(options).run();
    }
}
