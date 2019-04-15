package uk.ac.ebi.uniprot.opentargets48.common.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIgnoreType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Data;
import uk.ac.ebi.uniprot.opentargets48.uniprot.models.BiophysicochemicalProperties;
import uk.ac.ebi.uniprot.opentargets48.uniprot.models.CatalyticAcitivity;
import uk.ac.ebi.uniprot.opentargets48.uniprot.models.CofactorGroup;
import uk.ac.ebi.uniprot.opentargets48.uniprot.models.Complex;
import uk.ac.ebi.uniprot.opentargets48.uniprot.models.EnzymeRegulation;
import uk.ac.ebi.uniprot.opentargets48.uniprot.models.Kinetic;
import uk.ac.ebi.uniprot.opentargets48.uniprot.models.ProteinFunction;
import uk.ac.ebi.uniprot.opentargets48.uniprot.models.Publication;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class OTARProteinEntry {
  private String Id;
  private String accession;
  private List<ProteinFunction> functions = new ArrayList<>();
  private List<Complex> complexIds = new ArrayList<>();
  private List<CatalyticAcitivity> catalyticAcitivities = new ArrayList<>();
  private List<EnzymeRegulation> enzymeRegulations = new ArrayList<>();
  private List<BiophysicochemicalProperties> biophysicochemicalProperties = new ArrayList<>();
  private CofactorGroup cofactorGroups;

  @JsonIgnoreType
  public static class Builder {
    private String Id;
    private String accession;
    private List<String> complexIds = new ArrayList<>();
    private List<Map<String, String>> activities = new ArrayList<>();
    private List<Map<String, Object>> functions = new ArrayList<>();
    private List<Map<String, Object>> enzymeRegulations = new ArrayList<>();
    private List<Map<String, Object>> bpcProperties = new ArrayList<>();
    private Map<String, Object> cofactorGroup = new HashMap<>();

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

    public Builder withActivities(List<Map<String, String>> activities) {
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

    public OTARProteinEntry build() {
      OTARProteinEntry entry = new OTARProteinEntry();
      entry.Id = this.Id;
      entry.accession = this.accession;
      for (Map<String, Object> function : this.functions) {
        entry.functions.add(createProteinFunction(function));
      }
      for (String Id : this.complexIds) {
        entry.complexIds.add(new Complex(Id));
      }
      for (Map<String, String> activity : this.activities) {
        entry.catalyticAcitivities.add(CatalyticAcitivity.from(activity));
      }
      for (Map<String, Object> enzyme : this.enzymeRegulations) {
        entry.enzymeRegulations.add(createEnzymeRegulation(enzyme));
      }
      for (Map<String, Object> property : this.bpcProperties) {
        entry.biophysicochemicalProperties.add(createBpcProperty(property));
      }
      entry.cofactorGroups = CofactorGroup.from(this.cofactorGroup);
      return entry;
    }

    private ProteinFunction createProteinFunction(Map<String, Object> function) {
      return new ProteinFunction(
          (String) function.get("text"),
          getPublications((List<Map<String, String>>) function.get("evidences")));
    }

    private EnzymeRegulation createEnzymeRegulation(Map<String, Object> enzyme) {
      return EnzymeRegulation.builder()
          .text((String) enzyme.get("text"))
          .publications(getPublications((List<Map<String, String>>) enzyme.get("evidences")))
          .build();
    }

    private BiophysicochemicalProperties createBpcProperty(Map<String, Object> property) {
      return BiophysicochemicalProperties.builder()
          .type((String) property.get("type"))
          .kinetics(
              new HashMap<String, List<Kinetic>>() {
                {
                  put("km", getKinetics((List<Map<String, Object>>) property.get("km")));
                }
              })
          .build();
    }

    private List<Publication> getPublications(List<Map<String, String>> evidences) {
      List<Publication> publications = new ArrayList<>();
      for (Map<String, String> evidence : evidences) {
        publications.add(Publication.from(evidence));
      }
      return publications;
    }

    private List<Kinetic> getKinetics(List<Map<String, Object>> kinetics) {
      List<Kinetic> result = new ArrayList<>();
      for (Map<String, Object> kinetic : kinetics) {
        result.add(
            new Kinetic(
                (String) kinetic.get("value"),
                getPublications((List<Map<String, String>>) kinetic.get("evidences"))));
      }
      return result;
    }
  }

  private OTARProteinEntry() {}
}
