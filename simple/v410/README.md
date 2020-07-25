how to repro:

# Build:

```bash
git clone git@github.com:moderakh/azure-cosmosdb-benchmark.git
cd ~/azure-cosmosdb-benchmark/simple/v410
mvn clean package
```

The above command will create a fat jar
Create a file with the following content as `~/cosmos-v4.properties`

```
ACCOUNT_HOST=https://test.documents.azure.com:443/
ACCOUNT_KEY=XYZ
```

# Run
```
java -jar target/simple-test-1.0-SNAPSHOT-jar-with-dependencies.jar
```
