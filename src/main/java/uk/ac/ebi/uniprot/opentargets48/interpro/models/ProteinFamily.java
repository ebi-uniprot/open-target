package uk.ac.ebi.uniprot.opentargets48.interpro.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import lombok.Data;
import uk.ac.ebi.uniprot.opentargets48.common.models.Publications;
import uk.ac.ebi.uniprot.opentargets48.uniprot.models.EvidenceDescription;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class ProteinFamily {
  private final String accession;
  private final String description;
  private final GoTerms goTerms;
  private final Publications publications;

  public static ProteinFamily from(Map<String, Object> result) {
    Map<String, Object> metadata = (Map<String, Object>) result.get("metadata");
    Map<String, Object> extraFields = (Map<String, Object>) result.get("extra_fields");
    String accession = (String) metadata.get("accession");
    List<String> descriptions = (List<String>) extraFields.get("description");
    StringBuilder description = new StringBuilder();
    for (String str : descriptions) {
      description.append(str);
    }
    LinkedHashMap<String, Object> literature = (LinkedHashMap<String, Object>) extraFields.get("literature");
    List<EvidenceDescription> evidences = new ArrayList<>();
    for (Map.Entry<String, Object> entry : literature.entrySet()) {
      String code = entry.getKey();
      Integer Id = ((Map<String, Integer>) entry.getValue()).get("PMID");
      String name = "PMID";
      evidences.add(new EvidenceDescription(code, Id.toString(), name));
    }
    return new ProteinFamily(
        accession,
        description.toString(),
        GoTerms.from((List<Map<String, Object>>) metadata.get("go_terms")),
        Publications.from(evidences)
    );
  }
}
