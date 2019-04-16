package uk.ac.ebi.uniprot.opentargets48.uniprot.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import java.util.List;
import java.util.Map;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class CatalyticAcitivity {
  private final String type;
  private final String name;
  private final String ecNumber;
  @JsonUnwrapped private final CrossRefs crossRefs;

  public static CatalyticAcitivity from(Map<String, Object> activity) {
    CrossRefs xrefs = CrossRefs.from((List<Map<String, String>>) activity.get("xrefs"));
    String type = (String) activity.get("type");
    String name = (String) activity.get("name");
    String ecNumber = (String) activity.get("ecNumber");
    return new CatalyticAcitivity(type, name, ecNumber, xrefs);
  }
}
