package uk.ac.ebi.uniprot.opentargets48.uniprot.writers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.json.JsonFileItemWriter;
import org.springframework.batch.item.json.JsonObjectMarshaller;
import org.springframework.core.io.Resource;
import uk.ac.ebi.uniprot.opentargets48.uniprot.models.Protein;

import java.util.List;


@Slf4j
public class JsonItemWriter extends JsonFileItemWriter<Protein> {
    public JsonItemWriter(Resource resource, JsonObjectMarshaller<Protein> jsonObjectMarshaller) {
        super(resource, jsonObjectMarshaller);
    }

    @Override
    public String doWrite(List<? extends Protein> items) {
        return super.doWrite(items);
    }
}
