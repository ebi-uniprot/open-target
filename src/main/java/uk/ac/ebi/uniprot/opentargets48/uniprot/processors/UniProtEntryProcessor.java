package uk.ac.ebi.uniprot.opentargets48.uniprot.processors;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import uk.ac.ebi.uniprot.opentargets48.common.models.OTARProteinEntry;
import uk.ac.ebi.uniprot.opentargets48.uniprot.models.OTARUniProtEntry;

@Slf4j
public class UniProtEntryProcessor implements ItemProcessor<OTARUniProtEntry, OTARProteinEntry> {

  public OTARProteinEntry process(OTARUniProtEntry entry) {
    log.debug("Processing data");
    return createProteinEntry(entry);
  }

  private OTARProteinEntry createProteinEntry(OTARUniProtEntry entry) {
    OTARProteinEntry.Builder builder =
        new OTARProteinEntry.Builder(entry.getId())
            .withAccession(entry.getAccession())
            .withFunctions(entry.getFunctions())
            .withComplexIds(entry.getComplexIds())
            .withActivities(entry.getCatalyticActivities())
            .withEnzymeRegulations(entry.getEnzymeRegulations())
            .withBpcProperties(entry.getBpcProperties())
            .withCofactors(entry.getCofactorGroup());
    return builder.build();
  }
}
