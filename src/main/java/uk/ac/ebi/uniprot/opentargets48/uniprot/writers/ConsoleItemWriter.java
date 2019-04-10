package uk.ac.ebi.uniprot.opentargets48.uniprot.writers;

import lombok.extern.slf4j.Slf4j;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.batch.item.ItemWriter;
import uk.ac.ebi.uniprot.opentargets48.common.models.OTARProteinEntry;

import javax.annotation.PreDestroy;
import java.io.*;
import java.util.List;


@Slf4j
public class ConsoleItemWriter implements ItemWriter<OTARProteinEntry>, Closeable {
    private final PrintWriter writer;
    private final ObjectMapper mapper;

    public ConsoleItemWriter() {
        OutputStream out = System.out;
        this.writer = new PrintWriter(out);
        this.mapper = new ObjectMapper();
    }

    @Override
    public void write(final List<? extends OTARProteinEntry> items) {
        for (OTARProteinEntry item : items) {
          try {
              writer.println(mapper.writeValueAsString(item));
          } catch (Exception ex) {
              writer.println("Encountered exception");
          }
        }
    }

    @PreDestroy
    @Override
    public void close() {
        writer.close();
    }
}
