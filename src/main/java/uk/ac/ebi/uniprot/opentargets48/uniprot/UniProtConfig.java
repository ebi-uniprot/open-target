package uk.ac.ebi.uniprot.opentargets48.uniprot;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.json.JacksonJsonObjectMarshaller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import uk.ac.ebi.uniprot.dataservice.client.Client;
import uk.ac.ebi.uniprot.dataservice.client.ServiceFactory;
import uk.ac.ebi.uniprot.opentargets48.common.models.OTARProteinEntry;
import uk.ac.ebi.uniprot.opentargets48.interpro.services.InterproService;
import uk.ac.ebi.uniprot.opentargets48.uniprot.models.OTARUniProtEntry;
import uk.ac.ebi.uniprot.opentargets48.uniprot.processors.UniProtEntryProcessor;
import uk.ac.ebi.uniprot.opentargets48.uniprot.readers.UniProtEntryReader;
import uk.ac.ebi.uniprot.opentargets48.uniprot.writers.JsonItemWriter;

@Configuration
@EnableBatchProcessing
public class UniProtConfig {
  public static final int DEFAULT_CHUNK_SIZE = 10;
  public static final String JOB_NAME = "processUniProtEntries";
  public static final String JSON_FILE_NAME = "protein.json";

  @Autowired private JobBuilderFactory jobs;

  @Autowired private StepBuilderFactory steps;

  @Bean
  protected UniProtEntryReader itemReader() {
    ServiceFactory serviceFactoryInstance = Client.getServiceFactoryInstance();
    return new UniProtEntryReader(serviceFactoryInstance.getUniProtQueryService());
  }

  @Bean
  public UniProtEntryProcessor itemProcessor() {
    InterproService interproService = new InterproService(new RestTemplateBuilder());
    return new UniProtEntryProcessor(interproService);
  }

  @Bean
  public JsonItemWriter jsonFileItemWriter() {
    return new JsonItemWriter(
        new ClassPathResource(JSON_FILE_NAME), new JacksonJsonObjectMarshaller<>());
  }

  @Bean
  protected Step processEntries(
      UniProtEntryReader reader,
      UniProtEntryProcessor processor,
      ItemWriter<OTARProteinEntry> writer) {
    return steps
        .get("uniProtEntries")
        .<OTARUniProtEntry, OTARProteinEntry>chunk(DEFAULT_CHUNK_SIZE)
        .reader(reader)
        .processor(processor)
        .writer(writer)
        .build();
  }

  @Bean
  public Job job() {
    return jobs.get(JOB_NAME)
        .start(processEntries(itemReader(), itemProcessor(), jsonFileItemWriter()))
        .build();
  }
}
