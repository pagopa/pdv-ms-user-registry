package it.pagopa.pdv.user_registry.connector.rest.client;

import com.amazonaws.xray.spring.aop.XRayEnabled;
import it.pagopa.pdv.user_registry.connector.PersonConnector;
import it.pagopa.pdv.user_registry.connector.model.PersonGlobalId;
import it.pagopa.pdv.user_registry.connector.model.PersonResource;
import it.pagopa.pdv.user_registry.connector.model.SavePersonDto;
import it.pagopa.pdv.user_registry.connector.model.SavePersonNamespaceDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "${rest-client.person.serviceCode}", url = "${rest-client.person.base-url}")
@XRayEnabled
public interface PersonRestClient extends PersonConnector {

    @PutMapping(value = "${rest-client.person.saveNamespacedId.path}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    void saveNamespacedId(@PathVariable("id") String id,
                          @PathVariable("namespace") String namespace,
                          @RequestBody SavePersonNamespaceDto request);


    @PatchMapping(value = "${rest-client.person.save.path}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    void save(@PathVariable("id") String id,
              @RequestBody SavePersonDto request);


    @GetMapping(value = "${rest-client.person.findById.path}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    PersonResource findById(@PathVariable("id") String id,
                            @RequestParam(value = "isNamespaced") boolean isNamespaced, @RequestParam(value = "namespace", required = false) String namespace);

    default PersonResource findById(String id, boolean isNamespaced) {
        return findById(id, isNamespaced, null);
    }

    @GetMapping(value = "${rest-client.person.findIdByNamespacedId.path}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    PersonGlobalId findIdByNamespacedId(@RequestParam("namespacedId") String namespacedId, @RequestParam("namespace") String namespace);

}
