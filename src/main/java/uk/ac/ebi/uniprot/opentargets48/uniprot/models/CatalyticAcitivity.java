package uk.ac.ebi.uniprot.opentargets48.uniprot.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class CatalyticAcitivity {
  private final String type;
  private final String name;
  private final String ecNumber;
  private final List<CrossRef> crossRefs;

  public static CatalyticAcitivity from(Map<String, String> activity) {
    List<CrossRef> refs = new ArrayList<>();
    for (String ref : activity.get("references").split(",")) {
      if (ref.indexOf('-') >= 0) {
        refs.add(new CrossRef(ref.split("-")[0], ref.split("-")[1]));
      } else {
        refs.add(new CrossRef(ref, ref));
      }
    }
    return new CatalyticAcitivity(
        activity.get("type"), activity.get("name"), activity.get("ecNumber"), refs);
  }
}
