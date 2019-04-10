package uk.ac.ebi.uniprot.opentargets48.uniprot.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class CatalyticAcitivity {
  private final String type;
  private final String name;
  private final String ecNumber;
  private final List<CrossRef> crossRefs;
}
