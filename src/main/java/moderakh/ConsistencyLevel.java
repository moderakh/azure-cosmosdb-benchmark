// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package moderakh;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * Represents the consistency levels supported for Cosmos DB client operations in the Azure Cosmos DB database service.
 * <p>
 * The requested ConsistencyLevel must match or be weaker than that provisioned for the database account. Consistency
 * levels by order of strength are STRONG, BOUNDED_STALENESS, SESSION and EVENTUAL.
 */
public enum ConsistencyLevel {

    /**
     * STRONG Consistency guarantees that read operations always return the value that was last written.
     */
    STRONG ("Strong"),

    /**
     * Bounded Staleness guarantees that reads are not too out-of-date. This can be configured based on number of
     * operations (MaxStalenessPrefix) or time (MaxStalenessIntervalInSeconds)
     */
    BOUNDED_STALENESS("BoundedStaleness"),

    /**
     * SESSION Consistency guarantees monotonic reads (you never read old data, then new, then old again), monotonic
     * writes (writes are ordered) and read your writes (your writes are immediately visible to your reads) within
     * any single session.
     */
    SESSION("Session"),

    /**
     * EVENTUAL Consistency guarantees that reads will return a subset of writes. ALL writes will be eventually be
     * available for reads.
     */
    EVENTUAL("Eventual"),

    /**
     * CONSISTENT_PREFIX Consistency guarantees that reads will return some prefix of all writes with no gaps. ALL
     * writes
     * will be eventually be available for reads.
     */
    CONSISTENT_PREFIX ("ConsistentPrefix");

    ConsistencyLevel(String overWireValue) {
        this.overWireValue = overWireValue;
    }

    private final String overWireValue;

    @Override
    public String toString() {
        return this.overWireValue;
    }

    static private Map<String, ConsistencyLevel> consistencyLevelHashMap = new HashMap<>();

    static private Map<String, ConsistencyLevel> consistencyLevelTreeMap = new TreeMap<>();

    static {
        for(ConsistencyLevel cl: ConsistencyLevel.values()) {
            consistencyLevelHashMap.put(cl.toString(), cl);
        }
        for(ConsistencyLevel cl: ConsistencyLevel.values()) {
            consistencyLevelTreeMap.put(cl.toString(), cl);
        }
    }

    public static ConsistencyLevel readFromStringUsingHashMapImplementation(String value) {
        ConsistencyLevel cl =  consistencyLevelHashMap.get(value);
        if (cl == null) {
            System.out.println("couldn't resolve: " + value);
            throw new RuntimeException("couldn't resolve: " + value);
        }

        return cl;
    }

    public static ConsistencyLevel readFromStringUsingTreeMapImplementation(String value) {
        ConsistencyLevel cl =  consistencyLevelTreeMap.get(value);
        if (cl == null) {
            System.out.println("couldn't resolve: " + value);
            throw new RuntimeException("couldn't resolve: " + value);
        }

        return cl;
    }
}
