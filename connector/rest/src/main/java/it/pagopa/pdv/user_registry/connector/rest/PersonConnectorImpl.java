package it.pagopa.pdv.user_registry.connector.rest;

import it.pagopa.pdv.user_registry.connector.PersonConnector;
import it.pagopa.pdv.user_registry.connector.model.PersonGlobalId;
import it.pagopa.pdv.user_registry.connector.model.PersonResource;
import it.pagopa.pdv.user_registry.connector.model.SavePersonDto;
import it.pagopa.pdv.user_registry.connector.model.SavePersonNamespaceDto;
import it.pagopa.pdv.user_registry.connector.rest.client.PersonRestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class PersonConnectorImpl implements PersonConnector {

    private final PersonRestClient personRestClient;

    @Autowired
    PersonConnectorImpl(PersonRestClient personRestClient) {
        this.personRestClient = personRestClient;
    }

    @Override
    public void saveNamespacedId(String id, String namespace, SavePersonNamespaceDto request) {
        personRestClient.saveNamespacedId(id, namespace, request);
    }

    @Override
    public void save(String id, SavePersonDto request) {
        personRestClient.save(id, request);
    }

    @Override
    public PersonResource findById(String id, String namespace) {
        return personRestClient.findById(id, namespace);
    }

    @Override
    public PersonResource findById(String id) {
        return findById(id, null);
    }

    @Override
    public PersonGlobalId findIdByNamespacedId(String namespacedId, String namespace) {
        return personRestClient.findIdByNamespacedId(namespacedId, namespace);
    }
}
