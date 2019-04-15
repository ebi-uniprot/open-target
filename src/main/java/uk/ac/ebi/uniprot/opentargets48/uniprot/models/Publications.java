package uk.ac.ebi.uniprot.opentargets48.uniprot.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class Publications {
  private final List<Publication> publications;

  public static Publications from(List<Map<String, String>> list) {
    List<Publication> publications = new ArrayList<>();
    for (Map<String, String> item : list) {
      publications.add(Publication.from(item));
    }
    return new Publications(publications);
  }
}
