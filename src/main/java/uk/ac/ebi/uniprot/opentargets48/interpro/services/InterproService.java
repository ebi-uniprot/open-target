package uk.ac.ebi.uniprot.opentargets48.interpro.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import uk.ac.ebi.uniprot.opentargets48.interpro.models.ProteinFamilyResponse;

@Slf4j
public class InterproService {
  private final RestTemplate restTemplate;
  static final String URL =
      "https://www.ebi.ac.uk/interpro/beta/api/entry/interpro/protein/uniprot/%s";
  static final String Params = "?type=family&extra_fields=description,literature";

  public InterproService(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  public ProteinFamilyResponse fetch(String accession) {
    ResponseEntity<ProteinFamilyResponse> responseEntity =
        restTemplate.getForEntity(
            String.format(URL + Params, accession), ProteinFamilyResponse.class);
    return responseEntity.getBody();
  }
}
