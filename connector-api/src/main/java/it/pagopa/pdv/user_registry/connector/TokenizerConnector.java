package it.pagopa.pdv.user_registry.connector;

import it.pagopa.pdv.user_registry.connector.model.CreateTokenDto;
import it.pagopa.pdv.user_registry.connector.model.PiiResource;
import it.pagopa.pdv.user_registry.connector.model.TokenResource;

public interface TokenizerConnector {

    TokenResource save(CreateTokenDto request, String namespace);

//    String save(String pii, Namespace namespace);
//
//    String findById(String pii, Namespace namespace);

    PiiResource findPiiByToken(String token);

}
