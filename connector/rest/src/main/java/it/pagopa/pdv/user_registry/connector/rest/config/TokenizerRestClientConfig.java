package it.pagopa.pdv.user_registry.connector.rest.config;

import it.pagopa.pdv.user_registry.connector.rest.client.TokenizerRestClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

@Configuration
@Import(RestClientBaseConfig.class)
@EnableFeignClients(clients = TokenizerRestClient.class)
@PropertySource("classpath:config/tokenizer-rest-client.properties")
class TokenizerRestClientConfig {
}
