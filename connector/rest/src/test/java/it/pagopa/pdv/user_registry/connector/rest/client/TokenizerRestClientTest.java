package it.pagopa.pdv.user_registry.connector.rest.client;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import it.pagopa.pdv.user_registry.connector.model.CreateTokenDto;
import it.pagopa.pdv.user_registry.connector.model.PiiResource;
import it.pagopa.pdv.user_registry.connector.model.SearchTokenFilterCriteria;
import it.pagopa.pdv.user_registry.connector.model.TokenResource;
import it.pagopa.pdv.user_registry.connector.rest.RestTestUtils;
import it.pagopa.pdv.user_registry.connector.rest.config.TokenizerRestClientTestConfig;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.http.HttpMessageConvertersAutoConfiguration;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.TestPropertySourceUtils;

import java.util.TimeZone;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ContextConfiguration(initializers = TokenizerRestClientTest.RandomPortInitializer.class,
        classes = {
                TokenizerRestClientTest.Config.class,
                TokenizerRestClientTestConfig.class,
                FeignAutoConfiguration.class,
                HttpMessageConvertersAutoConfiguration.class})
@TestPropertySource(
        properties = {
                "logging.level.it.pagopa.pdv.user_registry.connector.rest=DEBUG",
                "spring.application.name=pdv-ms-user-registry-connector-rest"
        })
class TokenizerRestClientTest {

    @TestConfiguration
    public static class Config {
        @Bean
        @Primary
        public ObjectMapper objectMapper() {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            mapper.registerModule(new Jdk8Module());
            mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
            mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE);
            mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
            mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            mapper.setTimeZone(TimeZone.getDefault());
            return mapper;
        }
    }

    public static class RandomPortInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        @SneakyThrows
        @Override
        public void initialize(ConfigurableApplicationContext applicationContext) {
            TestPropertySourceUtils.addInlinedPropertiesToEnvironment(applicationContext,
                    String.format("MS_TOKENIZER_URL=%s",
                            wm.getRuntimeInfo().getHttpBaseUrl())
            );
        }
    }

    @Order(1)
    @RegisterExtension
    static WireMockExtension wm = WireMockExtension.newInstance()
            .options(RestTestUtils.getWireMockConfiguration("stubs/tokenizer"))
            .build();

    @Order(2)
    @RegisterExtension
    static SpringExtension springExtension = new SpringExtension();

    @Autowired
    private TokenizerRestClient restClient;


    @Test
    void save() {
        // given
        String namespace = "selfcare";
        CreateTokenDto request = new CreateTokenDto();
        request.setPii("pii");
        // when
        TokenResource response = restClient.save(namespace, request);
        // then
        assertNotNull(response);
        assertNotNull(response.getRootToken());
        assertNotNull(response.getToken());
    }


    @Test
    void findPiiByToken() {
        // given
        String token = UUID.randomUUID().toString();
        String namespace = "namespace";
        // when
        PiiResource response = restClient.findPiiByToken(token, namespace);
        // then
        assertNotNull(response);
        assertNotNull(response.getPii());
    }


    @Test
    void search() {
        // given
        String namespace = "selfcare";
        SearchTokenFilterCriteria request = new SearchTokenFilterCriteria();
        request.setPii("pii");
        // when
        TokenResource response = restClient.search(namespace, request);
        // then
        assertNotNull(response);
        assertNotNull(response.getRootToken());
        assertNotNull(response.getToken());
    }

}