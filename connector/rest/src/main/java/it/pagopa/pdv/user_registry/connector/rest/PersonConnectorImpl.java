package it.pagopa.pdv.user_registry.connector.rest;

import it.pagopa.pdv.user_registry.connector.PersonConnector;
import it.pagopa.pdv.user_registry.connector.model.PersonGlobalId;
import it.pagopa.pdv.user_registry.connector.model.PersonResource;
import it.pagopa.pdv.user_registry.connector.model.SavePersonDto;
import it.pagopa.pdv.user_registry.connector.model.SavePersonNamespaceDto;
import it.pagopa.pdv.user_registry.connector.rest.client.PersonRestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Service
public class PersonConnectorImpl implements PersonConnector {

    private final PersonRestClient personRestClient;

    @Autowired
    PersonConnectorImpl(PersonRestClient personRestClient) {
        this.personRestClient = personRestClient;
    }

    @Override
    @ResponseBody
    @PutMapping(value = "${rest-client.person.saveNamespacedId.path}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void saveNamespacedId(String id, String namespace, SavePersonNamespaceDto request) {
        personRestClient.saveNamespacedId(id, namespace, request);
    }

    @Override
    @ResponseBody
    @PatchMapping(value = "${rest-client.person.save.path}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void save(String id, SavePersonDto request) {
        personRestClient.save(id, request);
    }

    @Override
    @ResponseBody
    @GetMapping(value = "${rest-client.person.findById.path}", produces = MediaType.APPLICATION_JSON_VALUE)
    public PersonResource findById(String id, String namespace) {
        return personRestClient.findById(id, namespace);
    }

    @Override
    public PersonResource findById(String id) {
        return findById(id, "");
    }

    @Override
    @ResponseBody
    @GetMapping(value = "${rest-client.person.findIdByNamespacedId.path}", produces = MediaType.APPLICATION_JSON_VALUE)
    public PersonGlobalId findIdByNamespacedId(String namespacedId, String namespace) {
        return personRestClient.findIdByNamespacedId(namespacedId, namespace);
    }
}
