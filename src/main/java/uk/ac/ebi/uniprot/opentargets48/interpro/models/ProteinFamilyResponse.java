package uk.ac.ebi.uniprot.opentargets48.interpro.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import lombok.Value;

@JsonIgnoreProperties(ignoreUnknown = true)
@Value
public class ProteinFamilyResponse {
  private int count = 0;
  private List<Map<String, Object>> results = new ArrayList<>();
}
