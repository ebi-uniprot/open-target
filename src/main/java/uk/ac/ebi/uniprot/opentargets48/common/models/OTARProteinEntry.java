package uk.ac.ebi.uniprot.opentargets48.common.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIgnoreType;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import lombok.Data;
import uk.ac.ebi.uniprot.opentargets48.uniprot.models.CatalyticAcitivity;
import uk.ac.ebi.uniprot.opentargets48.uniprot.models.Complex;
import uk.ac.ebi.uniprot.opentargets48.uniprot.models.CrossRef;
import uk.ac.ebi.uniprot.opentargets48.uniprot.models.ProteinFunction;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class OTARProteinEntry {
  private String Id;
  private String accession;
  private List<ProteinFunction> functions;
  private List<Complex> complexIds;
  private List<CatalyticAcitivity> activities;

  @JsonIgnoreType
  public static class Builder {
    private String Id;
    private String accession;
    private List<Map<String, String>> activities;
    private List<String> functions;
    private List<String> complexIds;

    public Builder(String Id) {
      this.Id = Id;
      this.activities = new ArrayList<>();
      this.functions = new ArrayList<>();
      this.complexIds = new ArrayList<>();
    }

    public Builder withAccession(String accession) {
      this.accession = accession;
      return this;
    }

    public Builder withFunctions(List<String> functions) {
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

    public OTARProteinEntry build() {
      OTARProteinEntry entry = new OTARProteinEntry();
      entry.Id = this.Id;
      entry.accession = this.accession;
      entry.functions = new ArrayList<>();
      for (String function : this.functions) {
        entry.functions.add(new ProteinFunction(function));
      }
      entry.complexIds = new ArrayList<>();
      for (String Id : this.complexIds) {
        entry.complexIds.add(new Complex(Id));
      }
      entry.activities = new ArrayList<>();
      for (Map<String, String> activity : this.activities) {
        entry.activities.add(createCatalyticActivity(activity));
      }
      return entry;
    }

    private CatalyticAcitivity createCatalyticActivity(Map<String, String> activity) {
      List<CrossRef> refs = new ArrayList<>();
      for (String ref : activity.get("references").split(",")) {
        if (ref.indexOf('-') >= 0) {
          refs.add(new CrossRef(ref.split("-")[0], ref.split("-")[1]));
        } else {
          refs.add(new CrossRef(ref, ref));
        }
      }
      return new CatalyticAcitivity(
          activity.get("type"), activity.get("name"), activity.get("ecNumber"), refs);
    }
  }

  private OTARProteinEntry() {}
}
