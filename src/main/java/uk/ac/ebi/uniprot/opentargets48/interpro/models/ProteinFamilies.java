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
    List<ProteinFamily> families = new ArrayList<>();
    List<Map<String, Object>> results = response.getResults();
    for (Map<String, Object> result : results) {
      families.add(ProteinFamily.from(result));
    }
    return new ProteinFamilies(families);
  }
}
