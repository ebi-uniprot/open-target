package uk.ac.ebi.uniprot.opentargets48.interpro.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;
import java.util.Map;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class ProteinFamily {
  private final String accession;
  private final String description;
  private final GoTerms goTerms;

  public static ProteinFamily from(Map<String, Object> result) {
    Map<String, Object> metadata = (Map<String, Object>) result.get("metadata");
    Map<String, Object> extraFields = (Map<String, Object>) result.get("extra_fields");
    String accession = (String) metadata.get("accession");
    List<String> descriptions = (List<String>) extraFields.get("description");
    String description = "";
    for (String str : descriptions) {
      description += str;
    }
    return new ProteinFamily(
        accession, description, GoTerms.from((List<Map<String, Object>>) metadata.get("go_terms")));
  }
}
