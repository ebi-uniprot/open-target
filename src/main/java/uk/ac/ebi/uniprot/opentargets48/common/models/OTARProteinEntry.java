package uk.ac.ebi.uniprot.opentargets48.common.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIgnoreType;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import uk.ac.ebi.uniprot.opentargets48.uniprot.models.Complex;
import uk.ac.ebi.uniprot.opentargets48.uniprot.models.ProteinFunction;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class OTARProteinEntry {
  private String Id;
  private String accession;
  private List<ProteinFunction> functions;
  private List<Complex> complexIds;

  @JsonIgnoreType
  public static class Builder {
    private String Id;
    private String accession;
    private List<String> functions;
    private List<String> complexIds;

    public Builder(String Id) {
      this.Id = Id;
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
      this.complexIds = complexIds;
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
      return entry;
    }
  }

  private OTARProteinEntry() {}
}
