package uk.ac.ebi.uniprot.opentargets48.interpro.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class GoTerms {
  private final List<GoTerm> goTerms;

  public static GoTerms from(List<Map<String, Object>> items) {
    List<GoTerm> goTerms = new ArrayList<>();
    for (Map<String, Object> item : items) {
      goTerms.add(GoTerm.from(item));
    }
    return new GoTerms(goTerms);
  }
}
