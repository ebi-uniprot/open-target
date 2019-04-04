package uk.ac.ebi.uniprot.opentargets48.uniprot;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.json.JacksonJsonObjectMarshaller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import uk.ac.ebi.kraken.interfaces.uniprot.UniProtEntry;
import uk.ac.ebi.uniprot.dataservice.client.Client;
import uk.ac.ebi.uniprot.dataservice.client.ServiceFactory;
import uk.ac.ebi.uniprot.opentargets48.uniprot.models.Protein;
import uk.ac.ebi.uniprot.opentargets48.uniprot.processors.UniProtEntryProcessor;
import uk.ac.ebi.uniprot.opentargets48.uniprot.readers.UniProtEntryReader;
import uk.ac.ebi.uniprot.opentargets48.uniprot.writers.JsonItemWriter;


@Configuration
@EnableBatchProcessing
public class UniProtConfig {
    public static final int DEFAULT_CHUNK_SIZE = 10;
    public static final String JOB_NAME = "processUniProtEntries";

    @Autowired
    private JobBuilderFactory jobs;

    @Autowired
    private StepBuilderFactory steps;

    @Bean
    protected UniProtEntryReader itemReader() {
        ServiceFactory serviceFactoryInstance = Client.getServiceFactoryInstance();
        return new UniProtEntryReader(serviceFactoryInstance.getUniProtQueryService());
    }

    @Bean
    public UniProtEntryProcessor itemProcessor() {
        return new UniProtEntryProcessor();
    }


    @Bean
    public JsonItemWriter jsonFileItemWriter() {
        return new JsonItemWriter(new ClassPathResource("protein.json"), new JacksonJsonObjectMarshaller<>());
    }

    @Bean
    protected Step processEntries(
            UniProtEntryReader reader,
            UniProtEntryProcessor processor,
            ItemWriter<Protein> writer) {
        return steps.get("uniProtEntries").<UniProtEntry, Protein> chunk(DEFAULT_CHUNK_SIZE)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }

    @Bean
    public Job job() {
        return jobs
                .get(JOB_NAME)
                .start(processEntries(itemReader(), itemProcessor(), jsonFileItemWriter()))
                .build();
    }
}
