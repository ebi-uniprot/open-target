package uk.ac.ebi.uniprot.opentargets48.interpro.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.Map;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class GoTerm {
  private final String Id;
  private final String name;
  private final Map<String, String> category;

  public static GoTerm from(Map<String, Object> item) {
    String Id = (String) item.get("identifier");
    String name = (String) item.get("name");
    Map<String, String> category = (Map<String, String>) item.get("category");
    return new GoTerm(Id, name, category);
  }
}
