package uk.ac.ebi.uniprot.opentargets48.uniprot.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Value;


@JsonIgnoreProperties(ignoreUnknown = true)
@Value
public class Protein {
    private String Id;
    private String accession;
}
