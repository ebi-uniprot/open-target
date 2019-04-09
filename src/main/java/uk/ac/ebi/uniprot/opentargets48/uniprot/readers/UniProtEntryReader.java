package uk.ac.ebi.uniprot.opentargets48.uniprot.readers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemReader;
import uk.ac.ebi.kraken.interfaces.uniprot.DatabaseCrossReference;
import uk.ac.ebi.kraken.interfaces.uniprot.DatabaseType;
import uk.ac.ebi.kraken.interfaces.uniprot.comments.Comment;
import uk.ac.ebi.kraken.interfaces.uniprot.comments.CommentType;
import uk.ac.ebi.kraken.model.uniprot.comments.FunctionCommentImpl;
import uk.ac.ebi.uniprot.dataservice.client.exception.ServiceException;
import uk.ac.ebi.uniprot.dataservice.client.uniprot.UniProtQueryBuilder;
import uk.ac.ebi.uniprot.dataservice.client.uniprot.UniProtService;
import uk.ac.ebi.uniprot.dataservice.query.Query;
import uk.ac.ebi.uniprot.opentargets48.uniprot.models.UniProtEntry;


@Slf4j
public class UniProtEntryReader implements ItemReader<UniProtEntry> {
    static final int HUMAN_TAXONOMY_ID = 9606;
    private final UniProtService service;
    private Iterator<uk.ac.ebi.kraken.interfaces.uniprot.UniProtEntry> iterator;

    public UniProtEntryReader(UniProtService service) {
        this.service = service;
    }

    @Override
    public UniProtEntry read() throws ServiceException {
        if (iterator == null) {
            iterator = entries();
        }
        if (iterator.hasNext()) {
            return convert(iterator.next());
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

    private Iterator<uk.ac.ebi.kraken.interfaces.uniprot.UniProtEntry> entries() throws ServiceException {
        log.debug("Reading data");
        service.start();
        return service.getEntries(getQuery());
    }

    private UniProtEntry convert(uk.ac.ebi.kraken.interfaces.uniprot.UniProtEntry entry) {
        String id = entry.getUniProtId().toString();
        String accession = entry.getPrimaryUniProtAccession().toString();
        List<Comment> comments = entry.getComments(CommentType.FUNCTION);
        List<String> cs = new ArrayList<>();
        for (Comment c : comments) {
            cs.add(((FunctionCommentImpl) c).getValue());
        }
        List<DatabaseCrossReference> references = entry.getDatabaseCrossReferences(DatabaseType.COMPLEXPORTAL);
        List<String> dbs = new ArrayList<>();
        for (DatabaseCrossReference r : references) {
            dbs.add(r.getDatabase().getName());
        }

        return new UniProtEntry(id, accession, cs, dbs);
    }
}
