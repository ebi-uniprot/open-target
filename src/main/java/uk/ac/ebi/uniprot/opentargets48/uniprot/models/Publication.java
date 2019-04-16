package uk.ac.ebi.uniprot.opentargets48.uniprot.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.Map;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class Publication {
  private final String sourceId;
  private final String sourceName;
  private final String code;

  public static Publication from(Map<String, String> evidence) {
    String sourceId = evidence.get("sourceId");
    String sourceName = evidence.get("sourceName");
    String code = evidence.get("code");
    return new Publication(sourceId, sourceName, code);
  }
}
