package uk.ac.ebi.uniprot.opentargets48.uniprot.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class CofactorGroup {
  private final List<Cofactor> cofactors;
  private final List<CofactorNote> notes;

  @JsonIgnoreProperties(ignoreUnknown = true)
  @Data
  public static class Cofactor {
    private final String value;
    @JsonUnwrapped
    private final CrossRefs crossRefs;
    @JsonUnwrapped
    private final Publications publications;
  }

  @JsonIgnoreProperties(ignoreUnknown = true)
  @Data
  public static class CofactorNote {
    private final String text;
    @JsonUnwrapped
    private final Publications publications;
  }

  public static CofactorGroup from(Map<String, Object> cofactorGroup) {
    List<Map<String, Object>> cofactors =
        (List<Map<String, Object>>) cofactorGroup.get("cofactors");
    List<Map<String, Object>> notes = (List<Map<String, Object>>) cofactorGroup.get("notes");

    List<Cofactor> cfs = new ArrayList<>();
    for (Map<String, Object> cofactor : cofactors) {
      String text = (String) cofactor.get("text");
      CrossRefs xrefs = CrossRefs.from((List<String>) cofactor.get("xrefs"));
      Publications publications =
          Publications.from((List<Map<String, String>>) cofactor.get("publications"));
      cfs.add(new Cofactor(text, xrefs, publications));
    }

    List<CofactorNote> cofactorNotes = new ArrayList<>();
    for (Map<String, Object> note : notes) {
      Publications publications =
          Publications.from((List<Map<String, String>>) note.get("publications"));
      cofactorNotes.add(new CofactorNote((String) note.get("text"), publications));
    }
    return new CofactorGroup(cfs, cofactorNotes);
  }
}
