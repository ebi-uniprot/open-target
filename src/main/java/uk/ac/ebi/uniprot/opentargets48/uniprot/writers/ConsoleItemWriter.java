package uk.ac.ebi.uniprot.opentargets48.uniprot.writers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemWriter;
import uk.ac.ebi.uniprot.opentargets48.uniprot.models.Protein;

import javax.annotation.PreDestroy;
import java.io.*;
import java.util.List;


@Slf4j
public class ConsoleItemWriter implements ItemWriter<Protein>, Closeable {
    private final PrintWriter writer;

    public ConsoleItemWriter() {
        OutputStream out = System.out;
        this.writer = new PrintWriter(out);
    }

    @Override
    public void write(final List<? extends Protein> items) {
        for (Protein item : items) {
            writer.println(item.toString());
        }
    }

    @PreDestroy
    @Override
    public void close() {
        writer.close();
    }
}
