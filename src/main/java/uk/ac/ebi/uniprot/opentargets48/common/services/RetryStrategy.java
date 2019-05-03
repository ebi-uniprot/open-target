package uk.ac.ebi.uniprot.opentargets48.common.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RetryStrategy<T> {
  private final RetryTemplate template;
  private final SimpleRetryPolicy policy;

  public RetryStrategy() {
    this.policy = new SimpleRetryPolicy();
    this.policy.setMaxAttempts(5);
    this.template = new RetryTemplate();
    this.template.setRetryPolicy(policy);
  }

  public T execute(RetryCallback callback) {
    return (T) template.execute(callback);
  }
}
