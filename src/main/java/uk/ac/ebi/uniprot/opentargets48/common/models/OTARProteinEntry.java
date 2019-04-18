package uk.ac.ebi.uniprot.opentargets48.common.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIgnoreType;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Data;
import uk.ac.ebi.uniprot.opentargets48.interpro.models.ProteinFamilies;
import uk.ac.ebi.uniprot.opentargets48.uniprot.models.BiophysicochemicalProperties;
import uk.ac.ebi.uniprot.opentargets48.uniprot.models.CatalyticActivities;
import uk.ac.ebi.uniprot.opentargets48.uniprot.models.CofactorGroup;
import uk.ac.ebi.uniprot.opentargets48.uniprot.models.Complexes;
import uk.ac.ebi.uniprot.opentargets48.uniprot.models.EnzymeRegulations;
import uk.ac.ebi.uniprot.opentargets48.uniprot.models.ProteinFunctions;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class OTARProteinEntry {
  private String Id;
  private String accession;
  @JsonUnwrapped private BiophysicochemicalProperties biophysicochemicalProperties;
  @JsonUnwrapped private ProteinFunctions functions;
  @JsonUnwrapped private Complexes complexIds;
  @JsonUnwrapped private EnzymeRegulations enzymeRegulations;
  @JsonUnwrapped private CatalyticActivities catalyticAcitivities;
  @JsonUnwrapped private OTARProteinFamilies families;
  private CofactorGroup cofactorGroups;

  @JsonIgnoreType
  public static class Builder {
    private String Id;
    private String accession;
    private List<String> complexIds = new ArrayList<>();
    private List<Map<String, Object>> activities = new ArrayList<>();
    private List<Map<String, Object>> functions = new ArrayList<>();
    private List<Map<String, Object>> enzymeRegulations = new ArrayList<>();
    private List<Map<String, Object>> bpcProperties = new ArrayList<>();
    private Map<String, Object> cofactorGroup = new HashMap<>();
    private ProteinFamilies families;

    public Builder(String Id) {
      this.Id = Id;
    }

    public Builder withAccession(String accession) {
      this.accession = accession;
      return this;
    }

    public Builder withFunctions(List<Map<String, Object>> functions) {
      this.functions.addAll(functions);
      return this;
    }

    public Builder withComplexIds(List<String> complexIds) {
      this.complexIds.addAll(complexIds);
      return this;
    }

    public Builder withActivities(List<Map<String, Object>> activities) {
      this.activities.addAll(activities);
      return this;
    }

    public Builder withEnzymeRegulations(List<Map<String, Object>> regulations) {
      this.enzymeRegulations.addAll(regulations);
      return this;
    }

    public Builder withBpcProperties(List<Map<String, Object>> properties) {
      this.bpcProperties.addAll(properties);
      return this;
    }

    public Builder withCofactors(Map<String, Object> cofactorGroup) {
      this.cofactorGroup = cofactorGroup;
      return this;
    }

    public Builder withFamilies(ProteinFamilies families) {
      this.families = families;
      return this;
    }

    public OTARProteinEntry build() {
      OTARProteinEntry entry = new OTARProteinEntry();
      entry.Id = this.Id;
      entry.accession = this.accession;
      entry.biophysicochemicalProperties = BiophysicochemicalProperties.from(this.bpcProperties);
      entry.functions = ProteinFunctions.from(this.functions);
      entry.complexIds = Complexes.from(this.complexIds);
      entry.enzymeRegulations = EnzymeRegulations.from(this.enzymeRegulations);
      entry.catalyticAcitivities = CatalyticActivities.from(this.activities);
      entry.cofactorGroups = CofactorGroup.from(this.cofactorGroup);
      entry.families = OTARProteinFamilies.from(this.families);
      return entry;
    }
  }

  private OTARProteinEntry() {}
}
