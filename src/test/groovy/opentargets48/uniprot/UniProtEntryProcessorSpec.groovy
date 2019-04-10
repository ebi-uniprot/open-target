package uk.ac.ebi.uniprot.opentargets48.uniprot

import spock.lang.Specification
import uk.ac.ebi.uniprot.opentargets48.common.models.OTARProteinEntry
import uk.ac.ebi.uniprot.opentargets48.uniprot.models.UniProtEntry
import uk.ac.ebi.uniprot.opentargets48.uniprot.processors.UniProtEntryProcessor


class UniProtEntryProcessorSpec extends Specification {
    UniProtEntryProcessor processor

    def setup() {
        processor = new UniProtEntryProcessor()
    }

    def "should populate new object"() {
        given:
        UniProtEntry entry = new UniProtEntry("1", "1234", Arrays.asList("Foo", "Bar"))
        OTARProteinEntry expected = createEntry("1", "1234", Arrays.asList("Foo", "Bar"))

        when:
        OTARProteinEntry actual = processor.process(entry)

        then:
        actual != null
        actual == expected
    }

    OTARProteinEntry createEntry(String id, String accession, List<String> functions) {
        OTARProteinEntry.Builder builder = new OTARProteinEntry.Builder(id)
                .withAccession(accession)
                .withFunctions(functions)
        return builder.build()
    }
}

