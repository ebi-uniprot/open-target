package uk.ac.ebi.uniprot.opentargets48.uniprot.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Value;
import uk.ac.ebi.uniprot.services.data.serializer.model.ref.Publication;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Value
public class Cofactor {
    String value;
    List<CrossRef> crossRefs;
    List<Publication> publications;
}
