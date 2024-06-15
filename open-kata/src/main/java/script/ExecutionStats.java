package script;

import lombok.Builder;
import lombok.Data;
import lombok.Singular;

import java.util.List;

@Data
@Builder
public class ExecutionStats {
  @Singular("collectionName")
  private List<String> collectionNames;

  private Long impactedDocumentCount;
  private List<String> impactedDocumentIds;
}
