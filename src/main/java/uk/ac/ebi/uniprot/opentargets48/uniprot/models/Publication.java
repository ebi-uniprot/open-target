package uk.ac.ebi.uniprot.opentargets48.uniprot.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import java.util.List;
import lombok.Data;

/*
evidence: [
         {
           code: “ECO:000000269”,
           sources: [
             {
               name: “PubMed”,
               ids: [“11566027”, “1234567”]
             }
           ]
         }
       ]

*/

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class Publication {
  private final String code;
  @JsonUnwrapped private final PublicationSources sources;

  public static Publication from(String code, List<EvidenceDescription> evidences) {
    PublicationSources sourceList = PublicationSources.from(evidences);
    return new Publication(code, sourceList);
  }
}
