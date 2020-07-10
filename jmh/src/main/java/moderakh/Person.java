package moderakh;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

class Person {
    @JsonProperty("id")
    String id;

    @JsonProperty("pk")
    UUID pk;
    @JsonProperty("age")
    int age;

    @JsonProperty("address")
    Address address;
    static class Address {
        @JsonProperty("street")
        String street;

        @JsonProperty("zip")
        int zip;

        @JsonProperty("builtTime")
        long builtTime;

        @JsonProperty("noValue")
        String noValue;
    }
}
