package moderakh;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.afterburner.AfterburnerModule;
import org.apache.commons.lang3.RandomStringUtils;
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

import java.util.Random;
import java.util.UUID;

@State(Scope.Benchmark)
public class PojoSerialization {

    ObjectMapper basicObjectMapper;
    ObjectMapper objectMapperWithAfterBurner;
    UUID uuid;
    String id;
    Random rand;

    JsonNode personAsJsonNode;

    byte[] personAsBytes;
    String personAsString;
    private Person person;

    private void preconfigure(ObjectMapper objectMapper) {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        objectMapper.configure(JsonParser.Feature.ALLOW_TRAILING_COMMA, true);
        objectMapper.configure(JsonParser.Feature.STRICT_DUPLICATE_DETECTION, true);

    }

    @Setup(Level.Trial)
    public void setUp() throws Exception {
        basicObjectMapper = new ObjectMapper();
        preconfigure(basicObjectMapper);

        objectMapperWithAfterBurner = new ObjectMapper();
        preconfigure(objectMapperWithAfterBurner);

        objectMapperWithAfterBurner.registerModule(new AfterburnerModule());


        uuid = UUID.randomUUID();
        id = RandomStringUtils.randomAlphanumeric(10);
        rand = new Random();

        person = generatePerson();
        personAsJsonNode = generatePersonAsJsonNode();

        personAsString = basicObjectMapper.writeValueAsString(getPerson());
        personAsBytes = personAsString.getBytes("UTF-8");
    }

    private Person generatePerson() {
        Person p = new Person();
        p.address = new Person.Address();
        p.pk = uuid;
        p.id = id;
        p.age = 5;
        p.address.street = "xyyzytujkljgfu sdf";
        p.address.zip = 6789;
        p.address.builtTime = 567968987;

        return p;
    }

    private Person getPerson() {
        Person p = new Person();
        p.address = new Person.Address();
        p.pk = person.pk;
        p.id = person.id;
        p.age = person.age;
        p.address.street = person.address.street;
        p.address.zip = person.address.zip;
        p.address.builtTime = person.address.builtTime;

        return p;
    }

    private JsonNode getJsonNode() {
        return personAsJsonNode.deepCopy();
    }

    private JsonNode generatePersonAsJsonNode() throws Exception {
        byte[] bytes = basicObjectMapper.writeValueAsBytes(getPerson());
        return  basicObjectMapper.readTree(bytes);
    }

    @Benchmark
    @BenchmarkMode({Mode.Throughput})
    public void serializationToByteArrayBasic() throws Exception {
        byte[] bytes = basicObjectMapper.writeValueAsBytes(getPerson());
    }

    @Benchmark
    @BenchmarkMode({Mode.Throughput})
    public void serializationToStringToByteArrayStringBasic() throws Exception {
        basicObjectMapper.writeValueAsString(getPerson()).getBytes("UTF-8");
    }

    @Benchmark
    @BenchmarkMode({Mode.Throughput})
    public void serializationToByteBufferBasic() throws Exception {
        ByteBufferOutputStream bbos = new ByteBufferOutputStream();

        basicObjectMapper.writeValue(bbos, getPerson());
        bbos.asByteBuffer();
    }

    @Benchmark
    @BenchmarkMode({Mode.Throughput})
    public void serializationToByteBufferAfterBurner() throws Exception {
        ByteBufferOutputStream bbos = new ByteBufferOutputStream();
        objectMapperWithAfterBurner.writeValue(bbos, getPerson());
        bbos.asByteBuffer();
    }

    @Benchmark
    @BenchmarkMode({Mode.Throughput})
    public void serializationJsonNodeToByteBufferBasic() throws Exception {
        ByteBufferOutputStream bbos = new ByteBufferOutputStream();
        basicObjectMapper.writeValue(bbos, getJsonNode());
        bbos.asByteBuffer();
    }

    @Benchmark
    @BenchmarkMode({Mode.Throughput})
    public void serializationJsonNodeToByteBufferAfterBurner() throws Exception {
        ByteBufferOutputStream bbos = new ByteBufferOutputStream();
        objectMapperWithAfterBurner.writeValue(bbos, getJsonNode());
        bbos.asByteBuffer();
    }


    @Benchmark
    @BenchmarkMode({Mode.Throughput})
    public void serializationToByteArrayAfterBurner() throws Exception {
        byte[] bytes = objectMapperWithAfterBurner.writeValueAsBytes(getPerson());
    }

    @Benchmark
    @BenchmarkMode({Mode.Throughput})
    public void serializationToStringToByteArrayAfterBurner() throws Exception {
        byte[] bytes = objectMapperWithAfterBurner.writeValueAsString(getPerson()).getBytes("UTF-8");
    }

    @Benchmark
    @BenchmarkMode({Mode.Throughput})
    public void deserializationFromStringBasic() throws Exception {
        Person p = basicObjectMapper.readValue(personAsString, Person.class);
    }

    @Benchmark
    @BenchmarkMode({Mode.Throughput})
    public void deserializationFromByteArrayBasic() throws Exception {
        Person p = basicObjectMapper.readValue(personAsBytes, Person.class);
    }

    @Benchmark
    @BenchmarkMode({Mode.Throughput})
    public void deserializationFromStringAfterBurner() throws Exception {
        Person p = objectMapperWithAfterBurner.readValue(personAsString, Person.class);
    }

    @Benchmark
    @BenchmarkMode({Mode.Throughput})
    public void deserializationFromByteArrayAfterBurner() throws Exception {
        Person p = objectMapperWithAfterBurner.readValue(personAsBytes, Person.class);
    }

    @Benchmark
    @BenchmarkMode({Mode.Throughput})
    public void deserializationToJsonNodeFromStringAfterBurner() throws Exception {
        JsonNode p = objectMapperWithAfterBurner.readTree(personAsString);
    }

    @Benchmark
    @BenchmarkMode({Mode.Throughput})
    public void deserializationToJsonNodeFromStringBasic() throws Exception {
        JsonNode p = basicObjectMapper.readTree(personAsString);
    }

    @Benchmark
    @BenchmarkMode({Mode.Throughput})
    public void deserializationToJsonNodeFromByteArrayAfterBurner() throws Exception {
        JsonNode p = objectMapperWithAfterBurner.readTree(personAsBytes);
    }

    @Benchmark
    @BenchmarkMode({Mode.Throughput})
    public void deserializationToJsonNodeFromByteArrayBasic() throws Exception {
        JsonNode p = basicObjectMapper.readTree(personAsBytes);
    }

    public static void main(String[] args) throws RunnerException {
        Options options = new OptionsBuilder()
                .include(PojoSerialization.class.getSimpleName())
                .forks(1)
                .build();

        new Runner(options).run();
    }

}
