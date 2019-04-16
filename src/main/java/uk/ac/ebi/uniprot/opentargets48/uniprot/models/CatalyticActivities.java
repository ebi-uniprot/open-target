package uk.ac.ebi.uniprot.opentargets48.uniprot.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class CatalyticActivities {
  private final List<CatalyticAcitivity> catalyticAcitivities;

  public static CatalyticActivities from(List<Map<String, Object>> activities) {
    List<CatalyticAcitivity> result = new ArrayList<>();
    for (Map<String, Object> activity: activities) {
      result.add(CatalyticAcitivity.from(activity));
    }
    return new CatalyticActivities(result);
  }
}
