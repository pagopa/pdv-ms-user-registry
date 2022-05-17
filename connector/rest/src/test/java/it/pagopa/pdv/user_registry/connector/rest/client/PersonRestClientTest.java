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
import it.pagopa.pdv.user_registry.connector.model.*;
import it.pagopa.pdv.user_registry.connector.rest.RestTestUtils;
import it.pagopa.pdv.user_registry.connector.rest.config.PersonRestClientTestConfig;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.http.HttpMessageConvertersAutoConfiguration;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.cloud.commons.httpclient.HttpClientConfiguration;
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

import static org.junit.jupiter.api.Assertions.*;

@ContextConfiguration(initializers = PersonRestClientTest.RandomPortInitializer.class,
        classes = {
                PersonRestClientTest.Config.class,
                PersonRestClientTestConfig.class,
                FeignAutoConfiguration.class,
                HttpMessageConvertersAutoConfiguration.class,
                HttpClientConfiguration.class})
@TestPropertySource(
        properties = {
                "logging.level.it.pagopa.pdv.user_registry.connector.rest=DEBUG",
                "spring.application.name=pdv-ms-user-registry-connector-rest"
        })
class PersonRestClientTest {

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
                    String.format("MS_PERSON_URL=%s",
                            wm.getRuntimeInfo().getHttpBaseUrl())
            );
        }
    }

    @Order(1)
    @RegisterExtension
    static WireMockExtension wm = WireMockExtension.newInstance()
            .options(RestTestUtils.getWireMockConfiguration("stubs/person"))
            .build();

    @Order(2)
    @RegisterExtension
    static SpringExtension springExtension = new SpringExtension();

    @Autowired
    private PersonRestClient restClient;


    @Test
    void saveNamespacedId() {
        // given
        String id = UUID.randomUUID().toString();
        String namespace = "selfcare";
        SavePersonNamespaceDto request = new SavePersonNamespaceDto();
        request.setNamespacedId("namespaceId");
        // when
        restClient.saveNamespacedId(id, namespace, request);
        // then do nothing
    }


    @Test
    void save() {
        // given
        String id = UUID.randomUUID().toString();
        SavePersonDto request = new DummySavePersonDto();
        // when
        final Executable executable = () -> restClient.save(id, request);
        // then do nothing
        assertDoesNotThrow(executable);
    }


    @Test
    void findById() {
        // given
        String id = UUID.randomUUID().toString();
        // when
        PersonResource response = restClient.findById(id, true);
        // then
        assertNotNull(response);
        assertNotNull(response.getId());
        assertCertifiableFieldNotNull(response.getName());
        assertCertifiableFieldNotNull(response.getFamilyName());
        assertCertifiableFieldNotNull(response.getEmail());
        assertCertifiableFieldNotNull(response.getBirthDate());
        assertNotNull(response.getWorkContacts());
        assertFalse(response.getWorkContacts().isEmpty());
        response.getWorkContacts().values().forEach(workContactResource -> assertCertifiableFieldNotNull(workContactResource.getEmail()));
    }


    private void assertCertifiableFieldNotNull(CertifiableField<?> actual) {
        assertNotNull(actual.getCertification());
        assertNotNull(actual.getValue());
    }


    @Test
    void findIdByNamespacedId() {
        // given
        String namespacedId = UUID.randomUUID().toString();
        // when
        PersonGlobalId response = restClient.findIdByNamespacedId(namespacedId);
        // then
        assertNotNull(response);
        assertNotNull(response.getId());
    }

}