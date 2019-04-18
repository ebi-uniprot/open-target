package uk.ac.ebi.uniprot.opentargets48.interpro.readers;

import java.util.Iterator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemReader;
import uk.ac.ebi.uniprot.dataservice.client.exception.ServiceException;
import uk.ac.ebi.uniprot.opentargets48.interpro.models.ProteinFamily;
import uk.ac.ebi.uniprot.opentargets48.interpro.models.ProteinFamilyResponse;
import uk.ac.ebi.uniprot.opentargets48.interpro.services.InterproService;

@Slf4j
public class InterproEntryReader implements ItemReader<ProteinFamily> {
  private final InterproService service;
  private Iterator<ProteinFamilyResponse> iterator;

  public InterproEntryReader(InterproService service) {
    this.service = service;
  }

  @Override
  public ProteinFamily read() throws ServiceException {
    ProteinFamilyResponse response = service.fetch("12344");
    return null;
  }
}
