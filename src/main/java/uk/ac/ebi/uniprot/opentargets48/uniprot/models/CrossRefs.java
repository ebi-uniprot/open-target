package uk.ac.ebi.uniprot.opentargets48.uniprot.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
@Data
public class CrossRefs {
  private final List<CrossRef> xrefs;

  public static final CrossRefs from(List<String> refs) {
    List<CrossRef> xrefs = new ArrayList<>();
    for (String ref : refs) {
      if (ref.indexOf('-') >= 0) {
        xrefs.add(new CrossRef(ref.split("-")[0], ref.split("-")[1]));
      } else {
        xrefs.add(new CrossRef(ref, ref));
      }
    }
    return new CrossRefs(xrefs);
  }
}
