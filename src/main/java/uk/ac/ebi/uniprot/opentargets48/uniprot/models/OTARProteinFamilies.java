package uk.ac.ebi.uniprot.opentargets48.uniprot.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import uk.ac.ebi.uniprot.opentargets48.interpro.models.ProteinFamilies;
import uk.ac.ebi.uniprot.opentargets48.interpro.models.ProteinFamily;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class OTARProteinFamilies {
  private final List<OTARProteinFamily> families;

  public static OTARProteinFamilies from(ProteinFamilies items) {
    List<OTARProteinFamily> families = new ArrayList<>();
    for (ProteinFamily family : items.getFamilies()) {
      families.add(new OTARProteinFamily(family.getAccession(), family.getDescription()));
    }
    return new OTARProteinFamilies(families);
  }
}
