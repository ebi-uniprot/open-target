package uk.ac.ebi.uniprot.opentargets48.uniprot.models;

import com.fasterxml.jackson.annotation.JsonIgnoreType;
import java.util.List;
import lombok.Data;

@JsonIgnoreType()
@Data
public class Kinetic {
  private final String value;
  private final List<Publication> publications;
}
