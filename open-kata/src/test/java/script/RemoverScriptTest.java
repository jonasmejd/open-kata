package script;

import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import net.joshka.junit.json.params.JsonFileSource;
import org.bson.Document;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.junit.jupiter.params.ParameterizedTest;
import org.mockito.junit.jupiter.MockitoExtension;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import javax.json.JsonObject;
import java.util.Collections;
import java.util.List;


@Testcontainers
@ExtendWith(MockitoExtension.class)
class RemoverScriptTest {

    RemoverScript removerScript;

    MongoDatabase mongoDatabase;

    @Container
    final MongoDBContainer databasesContainer =
            new MongoDBContainer(DockerImageName.parse("mongo:5.0.13"))
                    .withLabel("name", RemoverScriptTest.class.getName());


    @BeforeEach
    void setup() {
        mongoDatabase =
                MongoClients.create(databasesContainer.getConnectionString()).getDatabase("mongoAdmin");

        removerScript = new RemoverScript(mongoDatabase);
    }


    @ParameterizedTest
    @JsonFileSource(
            resources = "/removerScript/should_delete_wrong_prices_by_br_market.json")
    void should_delete_wrong_prices_by_br_market(JsonObject params) {
        // Given
        initCollections(params);

        ExecutionStats executionStats = removerScript.launch();

        // Then
        assertThat(executionStats.getCollectionNames()).isEqualTo(List.of("prices"));
        assertThat(executionStats.getImpactedDocumentCount()).isEqualTo(2);
    }

    private void initCollections(JsonObject params) {
        Document inputData = Document.parse(params.toString());
        List<Document> prices =
                inputData.getList("prices", Document.class, Collections.emptyList());

        MongoCollection<Document> priceCollections =
                mongoDatabase.getCollection(
                        "prices"
                );

        priceCollections.insertMany(prices);
    }




}