package uk.ac.ebi.uniprot.opentargets48.uniprot.models;

import com.fasterxml.jackson.annotation.JsonIgnoreType;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Data;

@JsonIgnoreType()
@Data
public class Kinetic {
  private final String value;
  @JsonUnwrapped private final Publications publications;
}
