package uk.ac.ebi.uniprot.opentargets48.uniprot.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;
import lombok.Data;
import uk.ac.ebi.uniprot.services.data.serializer.model.ref.Publication;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class Cofactor {
  String value;
  List<CrossRef> crossRefs;
  List<Publication> publications;
}
