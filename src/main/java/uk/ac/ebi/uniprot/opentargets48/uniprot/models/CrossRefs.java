package uk.ac.ebi.uniprot.opentargets48.uniprot.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
@Data
public class CrossRefs {
  private final List<CrossRef> xrefs;

  public static final CrossRefs from(List<Map<String, String>> refs) {
    List<CrossRef> xrefs = new ArrayList<>();
    for (Map<String, String> ref : refs) {
      xrefs.add(CrossRef.from(ref));
    }
    return new CrossRefs(xrefs);
  }
}
