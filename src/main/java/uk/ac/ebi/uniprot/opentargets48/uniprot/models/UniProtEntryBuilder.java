package uk.ac.ebi.uniprot.opentargets48.uniprot.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import uk.ac.ebi.kraken.interfaces.uniprot.DatabaseCrossReference;
import uk.ac.ebi.kraken.interfaces.uniprot.DatabaseType;
import uk.ac.ebi.kraken.interfaces.uniprot.EvidencedValue;
import uk.ac.ebi.kraken.interfaces.uniprot.UniProtEntry;
import uk.ac.ebi.kraken.interfaces.uniprot.comments.Cofactor;
import uk.ac.ebi.kraken.interfaces.uniprot.comments.Comment;
import uk.ac.ebi.kraken.interfaces.uniprot.comments.CommentText;
import uk.ac.ebi.kraken.interfaces.uniprot.comments.CommentType;
import uk.ac.ebi.kraken.interfaces.uniprot.comments.MichaelisConstant;
import uk.ac.ebi.kraken.interfaces.uniprot.comments.ReactionReference;
import uk.ac.ebi.kraken.interfaces.uniprot.evidences.EvidenceId;
import uk.ac.ebi.kraken.model.uniprot.comments.BioPhysicoChemicalPropertiesCommentImpl;
import uk.ac.ebi.kraken.model.uniprot.comments.CatalyticActivityCommentStructuredImpl;
import uk.ac.ebi.kraken.model.uniprot.comments.CofactorCommentStructuredImpl;
import uk.ac.ebi.kraken.model.uniprot.comments.EnzymeRegulationCommentImpl;
import uk.ac.ebi.kraken.model.uniprot.comments.FunctionCommentImpl;
import uk.ac.ebi.kraken.model.uniprot.comments.KineticParametersImpl;

public class UniProtEntryBuilder {
  private String Id;
  private String accession;
  private List<String> complexIds = new ArrayList<>();
  private List<Map<String, Object>> functions = new ArrayList<>();
  private List<Map<String, Object>> catalyticActivities = new ArrayList<>();
  private List<Map<String, Object>> enzymeRegulations = new ArrayList<>();
  private List<Map<String, Object>> bpcProperties = new ArrayList<>();
  private Map<String, Object> cofactorGroup = new HashMap<>();

  public UniProtEntryBuilder(String Id, String accession) {
    this.Id = Id;
    this.accession = accession;
  }

  public UniProtEntryBuilder withFunctions(UniProtEntry entry) {
    for (Comment comment : entry.getComments(CommentType.FUNCTION)) {
      Map<String, Object> map = new HashMap<>();
      map.put("text", ((FunctionCommentImpl) comment).getValue());
      map.put("evidences", getEvidences(comment.getEvidenceIds()));
      this.functions.add(map);
    }
    return this;
  }

  public UniProtEntryBuilder withComplexIds(UniProtEntry entry) {
    List<DatabaseCrossReference> references =
        entry.getDatabaseCrossReferences(DatabaseType.COMPLEXPORTAL);
    for (DatabaseCrossReference r : references) {
      this.complexIds.add(r.getPrimaryId().toString());
    }
    return this;
  }

  public UniProtEntryBuilder withCatalyticActivities(UniProtEntry entry) {
    for (Comment c : entry.getComments(CommentType.CATALYTIC_ACTIVITY)) {
      CatalyticActivityCommentStructuredImpl fc = (CatalyticActivityCommentStructuredImpl) c;
      Map<String, Object> activity = new HashMap<>();
      activity.put("type", "reaction");
      activity.put("name", fc.getReaction().getName());
      activity.put("ecNumber", fc.getReaction().getECNumber());
      activity.put("xrefs", getReferences(fc.getReaction().getReactionReferences()));
      this.catalyticActivities.add(activity);
    }
    return this;
  }

  public UniProtEntryBuilder withEnzymeRegulations(UniProtEntry entry) {
    List<Comment> regulations = entry.getComments(CommentType.ACTIVITY_REGULATION);
    for (Comment comment : regulations) {
      List<CommentText> texts = ((EnzymeRegulationCommentImpl) comment).getTexts();
      for (CommentText text : texts) {
        Map<String, Object> textToPublications = new HashMap<>();
        textToPublications.put("text", text.getValue());
        textToPublications.put("evidences", getEvidences(text.getEvidenceIds()));
        this.enzymeRegulations.add(textToPublications);
      }
    }
    return this;
  }

  public UniProtEntryBuilder withBpcProperties(UniProtEntry entry) {
    List<Comment> properties = entry.getComments(CommentType.BIOPHYSICOCHEMICAL_PROPERTIES);
    for (Comment comment : properties) {
      Map<String, Object> map = new HashMap<>();
      KineticParametersImpl kinetics =
          (KineticParametersImpl)
              ((BioPhysicoChemicalPropertiesCommentImpl) comment).getKineticParameters();
      map.put("type", "kinetics");
      map.put("km", new ArrayList<>());
      List<Map<String, Object>> list = (List) map.get("km");
      for (MichaelisConstant mc : kinetics.getMichaelisConstants()) {
        Map<String, Object> km = new HashMap<>();
        km.put(
            "value",
            mc.getConstant()
                + " "
                + mc.getUnit().getDisplayString()
                + " for "
                + mc.getSubstrate().getValue());
        km.put("evidences", getEvidences(mc.getEvidenceIds()));
        list.add(km);
      }
      map.put("km", list);
      this.bpcProperties.add(map);
    }
    return this;
  }

  public UniProtEntryBuilder withCofactors(UniProtEntry entry) {
    List<Comment> comments = entry.getComments(CommentType.COFACTOR);
    List<Map<String, Object>> cofactorList = new ArrayList<>();
    for (Comment comment : comments) {
      for (Cofactor cofactor : ((CofactorCommentStructuredImpl) comment).getCofactors()) {
        Map<String, Object> inner = new HashMap<>();
        inner.put("value", cofactor.getName());
        List<Map<String, String>> xrefs = new ArrayList<>();
        xrefs.add(new HashMap<String, String>(){{
          put("Id", cofactor.getCofactorReference().getReferenceId());
          put("name", cofactor.getCofactorReference().getCofactorReferenceType().name());
        }});
        inner.put("xrefs", xrefs);
        inner.put("evidences", getEvidences(cofactor.getEvidenceIds()));
        cofactorList.add(inner);
      }
      this.cofactorGroup.put("cofactors", cofactorList);

      List<Map<String, Object>> notes = new ArrayList<>();
      for (EvidencedValue ev : ((CofactorCommentStructuredImpl) comment).getNote().getTexts()) {
        Map<String, Object> map = new HashMap<>();
        map.put("text", ev.getValue());
        map.put("evidences", getEvidences(ev.getEvidenceIds()));
        notes.add(map);
      }
      this.cofactorGroup.put("notes", notes);
    }
    return this;
  }

  public OTARUniProtEntry build() {
    return new OTARUniProtEntry(
        Id,
        accession,
        complexIds,
        functions,
        catalyticActivities,
        enzymeRegulations,
        bpcProperties,
        cofactorGroup);
  }

  private List<Map<String, String>> getEvidences(List<EvidenceId> evidenceIds) {
    List<Map<String, String>> evidences = new ArrayList<>();
    for (EvidenceId evidence : evidenceIds) {
      Map<String, String> map = new HashMap<>();
      map.put("code", evidence.getEvidenceCode().getCodeValue());
      map.put("sourceName", evidence.getTypeValue());
      map.put("sourceId", evidence.getAttribute().getValue());
      evidences.add(map);
    }
    return evidences;
  }

  private List<Map<String, String>> getReferences(List<ReactionReference> references) {
    List<Map<String, String>> xrefs = new ArrayList<>();
    for (ReactionReference reference : references) {
      Map<String, String> ref = new HashMap<>();
      ref.put("Id", reference.getId());
      ref.put("name", reference.getType().name());
      xrefs.add(ref);
    }
    return xrefs;
  }
}
