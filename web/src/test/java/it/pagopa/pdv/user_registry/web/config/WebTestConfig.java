package it.pagopa.pdv.user_registry.web.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Import;

@TestConfiguration
@Import(WebConfig.class)
public class WebTestConfig {
}