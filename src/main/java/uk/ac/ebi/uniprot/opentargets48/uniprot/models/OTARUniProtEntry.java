package uk.ac.ebi.uniprot.opentargets48.uniprot.models;

import java.util.List;
import java.util.Map;
import lombok.Data;

@Data
public class OTARUniProtEntry {
  private final String Id;
  private final String accession;
  private final List<String> complexIds;
  private final List<Map<String, Object>> functions;
  private final List<Map<String, Object>> catalyticActivities;
  private final List<Map<String, Object>> enzymeRegulations;
  private final List<Map<String, Object>> bpcProperties;
  private final Map<String, Object> cofactorGroup;

  public OTARUniProtEntry(
      String Id,
      String accession,
      List<String> complexIds,
      List<Map<String, Object>> functions,
      List<Map<String, Object>> catalyticActivities,
      List<Map<String, Object>> enzymeRegulations,
      List<Map<String, Object>> bpcProperties,
      Map<String, Object> cofactorGroup) {

    this.Id = Id;
    this.accession = accession;
    this.complexIds = complexIds;
    this.functions = functions;
    this.catalyticActivities = catalyticActivities;
    this.enzymeRegulations = enzymeRegulations;
    this.bpcProperties = bpcProperties;
    this.cofactorGroup = cofactorGroup;
  }
}
