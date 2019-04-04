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


@Slf4j
public class UniProtEntryReader implements ItemReader<UniProtEntry> {
    static final int HUMAN_TAXONOMY_ID = 9606;
    private final UniProtService service;
    private Iterator<UniProtEntry> iterator;

    public UniProtEntryReader(UniProtService service) {
        this.service = service;
    }

    @Override
    public UniProtEntry read() throws ServiceException {
        if (iterator == null) {
            iterator = entries();
        }
        if (iterator.hasNext()) {
            return iterator.next();
        } else {
            service.stop();
            return null;
        }
    }

    public Query getQuery() {
        return UniProtQueryBuilder.taxonID(HUMAN_TAXONOMY_ID)
                .and(UniProtQueryBuilder.comments(CommentType.COFACTOR,"*")
                        .or(UniProtQueryBuilder.comments(CommentType.FUNCTION,"*"))
                        .or(UniProtQueryBuilder.comments(CommentType.CATALYTIC_ACTIVITY,"*"))
                        .or(UniProtQueryBuilder.comments(CommentType.BIOPHYSICOCHEMICAL_PROPERTIES, "*")));
    }

    private Iterator<UniProtEntry> entries() throws ServiceException {
        log.debug("Reading data");
        service.start();
        return service.getEntries(getQuery());
    }
}
