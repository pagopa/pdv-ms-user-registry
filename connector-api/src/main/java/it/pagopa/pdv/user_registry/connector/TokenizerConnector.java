package it.pagopa.pdv.user_registry.connector;

import it.pagopa.pdv.user_registry.connector.model.CreateTokenDto;
import it.pagopa.pdv.user_registry.connector.model.PiiResource;
import it.pagopa.pdv.user_registry.connector.model.SearchTokenFilterCriteria;
import it.pagopa.pdv.user_registry.connector.model.TokenResource;

public interface TokenizerConnector {

    TokenResource save(String namespace, CreateTokenDto request);

    PiiResource findPiiByToken(String token, String namespace);

    TokenResource search(String namespace, SearchTokenFilterCriteria request);

}
