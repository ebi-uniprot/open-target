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
  private final List<GoTerm> goTerms;
  private final Publications publications;

  public static ProteinFamily from(Result result) {
    String accession = result.getMetadata().getAccession();
    List<String> descriptions = result.getExtra_fields().getDescription();
    StringBuilder description = new StringBuilder();
    for (String str : descriptions) {
      description.append(str);
    }
    List<EvidenceDescription> evidences = new ArrayList<>();
    LinkedHashMap<String, Object> literature = result.getExtra_fields().getLiterature();
    if (literature != null) {
      for (Map.Entry<String, Object> entry : literature.entrySet()) {
        String code = entry.getKey();
        Integer Id = ((Map<String, Integer>) entry.getValue()).get("PMID");
        String name = "PMID";
        evidences.add(new EvidenceDescription(code, Id.toString(), name));
      }
    }
    return new ProteinFamily(
        accession,
        description.toString(),
        result.getMetadata().getGo_terms(),
        Publications.from(evidences)
    );
  }
}
