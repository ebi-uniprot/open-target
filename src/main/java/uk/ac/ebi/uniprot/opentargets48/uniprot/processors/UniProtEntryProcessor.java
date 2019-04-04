package uk.ac.ebi.uniprot.opentargets48.uniprot.processors;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import uk.ac.ebi.kraken.interfaces.uniprot.UniProtEntry;
import uk.ac.ebi.uniprot.opentargets48.uniprot.models.Protein;


@Slf4j
public class UniProtEntryProcessor implements ItemProcessor<UniProtEntry, Protein> {
    public Protein process(UniProtEntry entry) {
        log.debug("Processing data");
        return new Protein(entry.getUniProtId().toString(), entry.getPrimaryUniProtAccession().toString());
    }
}
