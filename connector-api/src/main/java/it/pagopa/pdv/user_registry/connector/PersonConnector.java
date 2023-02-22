package it.pagopa.pdv.user_registry.connector;

import it.pagopa.pdv.user_registry.connector.model.PersonGlobalId;
import it.pagopa.pdv.user_registry.connector.model.PersonResource;
import it.pagopa.pdv.user_registry.connector.model.SavePersonDto;
import it.pagopa.pdv.user_registry.connector.model.SavePersonNamespaceDto;

public interface PersonConnector {

    void saveNamespacedId(String id, String namespace, SavePersonNamespaceDto request);

    void save(String id, SavePersonDto request);

    PersonResource findById(String id, String namespace);

    PersonGlobalId findIdByNamespacedId(String namespacedId, String namespace);

}