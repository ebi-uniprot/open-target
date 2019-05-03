package uk.ac.ebi.uniprot.opentargets48.uniprot.readers;

import java.util.Iterator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemReader;
import uk.ac.ebi.kraken.interfaces.uniprot.UniProtEntry;
import uk.ac.ebi.kraken.interfaces.uniprot.comments.CommentType;
import uk.ac.ebi.uniprot.dataservice.client.exception.ServiceException;
import uk.ac.ebi.uniprot.dataservice.client.uniprot.UniProtQueryBuilder;
import uk.ac.ebi.uniprot.dataservice.client.uniprot.UniProtService;
import uk.ac.ebi.uniprot.dataservice.query.Query;
import uk.ac.ebi.uniprot.opentargets48.common.services.RetryStrategy;
import uk.ac.ebi.uniprot.opentargets48.uniprot.models.OTARUniProtEntry;
import uk.ac.ebi.uniprot.opentargets48.uniprot.models.UniProtEntryBuilder;

@Slf4j
public class UniProtEntryReader implements ItemReader<OTARUniProtEntry> {
  static final int HUMAN_TAXONOMY_ID = 9606;
  private final UniProtService service;
  private Iterator<UniProtEntry> iterator;
  private RetryStrategy<OTARUniProtEntry> retryStrategy;
  private int total;

  public UniProtEntryReader(UniProtService service, RetryStrategy retryStrategy) {
    this.service = service;
    this.total = 0;
    this.retryStrategy = retryStrategy;
  }

  @Override
  public OTARUniProtEntry read() throws ServiceException {
    if (iterator == null) {
      iterator = entries();
    }
    return retryStrategy.execute(
        context -> {
          if (context.getRetryCount() > 0) {
            log.warn("Retrying again with count :" + context.getRetryCount());
          }
          // TODO(vpoddar): For testing purpose only.
          if (total == 20) {
            return null;
          }
          if (iterator.hasNext()) {
            total++;
            log.info("Entry count so far: " + total);
            return convert(iterator.next());
          } else {
            log.info("Finished reading " + total + " entries in UniProt");
            service.stop();
            return null;
          }
        });
  }

  public Query getQuery() {
    return UniProtQueryBuilder.taxonID(HUMAN_TAXONOMY_ID)
        .and(
            UniProtQueryBuilder.comments(CommentType.COFACTOR, "*")
                .or(UniProtQueryBuilder.comments(CommentType.FUNCTION, "*"))
                .or(UniProtQueryBuilder.comments(CommentType.CATALYTIC_ACTIVITY, "*"))
                .or(UniProtQueryBuilder.comments(CommentType.BIOPHYSICOCHEMICAL_PROPERTIES, "*")));
        // .and(UniProtQueryBuilder.accession("P27815"));
        // .and(UniProtQueryBuilder.accession("P05067"));
        //.and(UniProtQueryBuilder.accession("P69892"));
  }

  private Iterator<UniProtEntry> entries() throws ServiceException {
    service.start();
    return service.getEntries(getQuery());
  }

  private OTARUniProtEntry convert(UniProtEntry entry) {
    String id = entry.getUniProtId().toString();
    String accession = entry.getPrimaryUniProtAccession().toString();

    return new UniProtEntryBuilder(id, accession)
        .withFunctions(entry)
        .withComplexIds(entry)
        .withCatalyticActivities(entry)
        .withEnzymeRegulations(entry)
        .withBpcProperties(entry)
        .withCofactors(entry)
        .build();
  }
}
