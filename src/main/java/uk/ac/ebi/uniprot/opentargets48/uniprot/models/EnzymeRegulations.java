package uk.ac.ebi.uniprot.opentargets48.uniprot.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class EnzymeRegulations {
  private final List<EnzymeRegulation> enzymeRegulations;

  public static EnzymeRegulations from(List<Map<String, Object>> items) {
    List<EnzymeRegulation> result = new ArrayList<>();
    for (Map<String, Object> item : items) {
      Publications publications =
          Publications.from((List<Map<String, String>>) item.get("evidences"));
      String text = (String) item.get("text");
      result.add(new EnzymeRegulation(text, publications));
    }
    return new EnzymeRegulations(result);
  }
}
