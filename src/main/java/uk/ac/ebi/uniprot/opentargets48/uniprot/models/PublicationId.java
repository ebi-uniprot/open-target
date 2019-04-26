package uk.ac.ebi.uniprot.opentargets48.uniprot.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class PublicationId {
  private final String Id;
}
