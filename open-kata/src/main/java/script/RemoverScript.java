package script;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.DeleteResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class RemoverScript implements ChangeLogScript{

 private final MongoDatabase mongoDatabase;
    @Override
    public  ExecutionStats launch() {
        log.info(
                "****************************** "
                        + this.getClass().getSimpleName()
                        + " ******************************");

        log.info(">>>>>>>>>>  Deleting BR market prices by product ids from Mongo...");

        List<String> productIds =
                List.of("A58601Y01864C3906", "A58600Y01864C3906", "A01113Y01864C3906", "A01112Y0129594305");
        Criteria priceCriteria =
                Criteria.where("entityId")
                        .in(productIds)
                        .and("entityType")
                        .is("FshProductVariant")
                        .and("market")
                        .is("BR");
        DeleteResult mongoDeleteResult =
                deleteFromDatabase(mongoDatabase, new Query(priceCriteria).getQueryObject());
        log.debug(
                "Deleted {} BR market prices by product ids from Mongo",
                mongoDeleteResult.getDeletedCount());
        return ExecutionStats.builder()
                .collectionNames(List.of("prices"))
                .impactedDocumentCount(mongoDeleteResult.getDeletedCount())
                .impactedDocumentIds(productIds)
                .build();
    }
    private DeleteResult deleteFromDatabase(MongoDatabase database, Bson deleteQuery) {
        MongoCollection<Document> collection = database.getCollection("prices");
        return collection.deleteMany(deleteQuery);
    }
}
