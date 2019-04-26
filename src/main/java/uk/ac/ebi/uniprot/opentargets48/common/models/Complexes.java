package uk.ac.ebi.uniprot.opentargets48.common.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class Complexes {
  private final List<Complex> complexes;

  public static Complexes from(List<String> ids) {
    List<Complex> result = new ArrayList<>();
    for (String Id : ids) {
      result.add(new Complex(Id));
    }
    return new Complexes(result);
  }
}
