package uk.ac.ebi.uniprot.opentargets48.interpro.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class ProteinFamilies {
  private final List<ProteinFamily> families;

  public static ProteinFamilies from(ProteinFamilyResponse response) {
    List<Map<String, Object>> results = response.getResults();
    List<ProteinFamily> families = new ArrayList<>();
    for (Map<String, Object> result : results) {
      Map<String, Object> metadata = (Map<String, Object>) result.get("metadata");
      Map<String, Object> extraFields = (Map<String, Object>) result.get("extra_fields");
      String accession = (String) metadata.get("accession");
      List<String> descriptions = (List<String>) extraFields.get("description");
      String description = "";
      for (String str : descriptions) {
        description += str;
      }
      families.add(new ProteinFamily(accession, description));
    }
    return new ProteinFamilies(families);
  }
}
