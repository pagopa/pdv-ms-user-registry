package it.pagopa.pdv.user_registry.connector.rest.client;

import it.pagopa.pdv.user_registry.connector.TokenizerConnector;
import it.pagopa.pdv.user_registry.connector.model.CreateTokenDto;
import it.pagopa.pdv.user_registry.connector.model.PiiResource;
import it.pagopa.pdv.user_registry.connector.model.TokenResource;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "${rest-client.tokenizer.serviceCode}", url = "${rest-client.tokenizer.base-url}")
public interface TokenizerRestClient extends TokenizerConnector {

    @PutMapping(value = "${rest-client.tokenizer.save.path}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    TokenResource save(@RequestBody CreateTokenDto request, @RequestHeader("namespace") String namespace);

    @GetMapping(value = "${rest-client.tokenizer.findPiiByToken.path}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    PiiResource findPiiByToken(@PathVariable("token") String token);

}
