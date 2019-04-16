package uk.ac.ebi.uniprot.opentargets48.uniprot.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class ProteinFunctions {
  private final List<ProteinFunction> functions;

  public static ProteinFunctions from(List<Map<String, Object>> items) {
    List<ProteinFunction> functions = new ArrayList<>();
    for (Map<String, Object> item : items) {
      String text = (String) item.get("text");
      Publications publications =
          Publications.from((List<Map<String, String>>) item.get("evidences"));
      functions.add(new ProteinFunction(text, publications));
    }
    return new ProteinFunctions(functions);
  }
}
