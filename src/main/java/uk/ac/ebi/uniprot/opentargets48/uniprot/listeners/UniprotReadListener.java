package uk.ac.ebi.uniprot.opentargets48.uniprot.listeners;


import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ItemReadListener;
import uk.ac.ebi.uniprot.opentargets48.uniprot.models.OTARUniProtEntry;

@Slf4j
public class UniprotReadListener implements ItemReadListener<OTARUniProtEntry> {
  @Override
  public void beforeRead() {
    log.info("ItemReadListener - beforeRead");
  }

  @Override
  public void afterRead(OTARUniProtEntry item) {
    log.info("ItemReadListener - afterRead");
  }

  @Override
  public void onReadError(Exception e) {
    log.error(e.getMessage());
  }
}
