package it.pagopa.pdv.user_registry.connector.rest.client;

import it.pagopa.pdv.user_registry.connector.PersonConnector;
import it.pagopa.pdv.user_registry.connector.model.PersonResource;
import it.pagopa.pdv.user_registry.connector.model.SavePersonNamespaceDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "${rest-client.person.serviceCode}", url = "${rest-client.person.base-url}")
public interface PersonRestClient extends PersonConnector {

    @PutMapping(value = "${rest-client.person.saveNamespacedId.path}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    void saveNamespacedId(@PathVariable("id") String id,
                          @PathVariable("namespace") String namespace,
                          @RequestBody
                                  SavePersonNamespaceDto request);

    @GetMapping(value = "${rest-client.person.getUserById.path}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    PersonResource getUserById(@PathVariable("id") String id,
                               @RequestParam("isNamespaced") boolean isNamespaced);

}
