import com.azure.cosmos.CosmosAsyncClient;
import com.azure.cosmos.CosmosAsyncContainer;
import com.azure.cosmos.CosmosClientBuilder;
import com.azure.cosmos.implementation.TestConfigurations;
import com.azure.cosmos.implementation.guava25.base.Stopwatch;
import com.azure.cosmos.models.CosmosAsyncItemResponse;
import com.azure.cosmos.models.CosmosContainerProperties;
import com.azure.cosmos.models.PartitionKey;

import java.util.UUID;

public class V401beta2 {

    public static  String CONTAINER_NAME = "testcol";
    public static  String DATABASE_NAME = "testdb";

    public static class POJO {
        public String id;
        public String prop;
    }

    public static void main(String[] args) {

        CosmosAsyncClient client = new CosmosClientBuilder()
                .endpoint(TestConfigurations.HOST)
                .key(TestConfigurations.MASTER_KEY)
                .buildAsyncClient();

        client.createDatabaseIfNotExists(DATABASE_NAME).block();
        CosmosContainerProperties cosmosContainerProperties = new CosmosContainerProperties(CONTAINER_NAME, "/id");
        client.getDatabase(DATABASE_NAME).createContainerIfNotExists(cosmosContainerProperties, 10_000).block();


        CosmosAsyncContainer container = client.getDatabase(DATABASE_NAME).getContainer(CONTAINER_NAME);

        POJO pojo = new POJO();
        pojo.id = UUID.randomUUID().toString();
        pojo.prop = UUID.randomUUID().toString();


        container.createItem(pojo).block();
        int cnt = 0;
        for(int i = 0; i < 1_000; i++) {

            Stopwatch stopwatch = Stopwatch.createStarted();
            CosmosAsyncItemResponse<POJO> response = container.readItem(pojo.id, new PartitionKey(pojo.id), POJO.class).block();
            stopwatch.stop();
            if (stopwatch.elapsed().getSeconds() > 1 && i > 10) {
                System.out.println("request " + i + " took " + stopwatch.elapsed() + "diagnostics" + response.getResponseDiagnostics());
                cnt++;
            } else {
                System.out.println("request " + i + " took " + stopwatch.elapsed());
            }

        }

        System.out.println("#" + cnt + " requests took more than 1 second");

        client.close();


    }

}
