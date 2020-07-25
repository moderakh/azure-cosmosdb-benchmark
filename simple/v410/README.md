how to repro:

# Build:

```bash
git clone git@github.com:moderakh/azure-cosmosdb-benchmark.git
cd ~/azure-cosmosdb-benchmark/simple/v410
mvn clean package
```

put cosmosdb crendentials file with the following content in your ~/cosmos-v4.properties

```
ACCOUNT_HOST=https://test.documents.azure.com:443/
ACCOUNT_KEY=XYZ
```

# Run
```
java -jar azure-cosmosdb-benchmark/simple/v410/target/simple-test-1.0-SNAPSHOT-jar-with-dependencies.jar
```
