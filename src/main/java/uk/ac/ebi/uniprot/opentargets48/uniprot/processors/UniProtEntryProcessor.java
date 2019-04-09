package uk.ac.ebi.uniprot.opentargets48.uniprot.processors;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import uk.ac.ebi.uniprot.opentargets48.common.models.OTARProteinEntry;
import uk.ac.ebi.uniprot.opentargets48.uniprot.models.UniProtEntry;

@Slf4j
public class UniProtEntryProcessor implements ItemProcessor<UniProtEntry, OTARProteinEntry> {

  public OTARProteinEntry process(UniProtEntry entry) {
    log.debug("Processing data");
    String id = entry.getId();
    String accession = entry.getAccession();
    return createProteinEntry(id, accession, entry.getFunctions(), entry.getComplexIds());
  }

  private OTARProteinEntry createProteinEntry(
      String id, String accession, List<String> functions, List<String> complexIds) {
    OTARProteinEntry.Builder builder =
        new OTARProteinEntry.Builder(id)
            .withAccession(accession)
            .withFunctions(functions)
            .withComplexIds(complexIds);
    return builder.build();
  }
}
