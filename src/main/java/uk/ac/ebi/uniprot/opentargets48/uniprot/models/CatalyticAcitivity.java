package uk.ac.ebi.uniprot.opentargets48.uniprot.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Value;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Value
public class CatalyticAcitivity {
    String type;
    String name;
    String ecNumber;
    List<CrossRef> crossRefs;
}
