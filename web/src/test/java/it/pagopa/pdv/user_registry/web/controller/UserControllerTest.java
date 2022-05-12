package it.pagopa.pdv.user_registry.web.controller;

import com.jayway.jsonpath.JsonPath;
import it.pagopa.pdv.user_registry.core.UserService;
import it.pagopa.pdv.user_registry.core.model.DummyUser;
import it.pagopa.pdv.user_registry.core.model.User;
import it.pagopa.pdv.user_registry.web.config.WebTestConfig;
import it.pagopa.pdv.user_registry.web.handler.RestExceptionsHandler;
import it.pagopa.pdv.user_registry.web.model.UserResource;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.EnumSet;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = {UserController.class}, excludeAutoConfiguration = SecurityAutoConfiguration.class)
@ContextConfiguration(classes = {
        UserController.class,
        RestExceptionsHandler.class,
        WebTestConfig.class
})
class UserControllerTest {

    private static final String BASE_URL = "/users";
    private static final String NAMESPACE_HEADER_NAME = "x-pagopa-namespace";

    @MockBean
    private UserService userServiceMock;

    @Autowired
    protected MockMvc mvc;


    @Test
    void findById_noFiscalCodeRequested_flAsCsvFormat() throws Exception {
        // given
        UUID id = UUID.randomUUID();
        EnumSet<UserResource.Fields> fields = EnumSet.of(UserResource.Fields.name);
        User user = new DummyUser();
        Mockito.when(userServiceMock.findById(Mockito.anyString(), Mockito.anyBoolean()))
                .thenReturn(user);
        // when
        mvc.perform(MockMvcRequestBuilders
                .get(BASE_URL + "/{id}", id)
                .queryParam("fl", fields.stream()
                        .map(Enum::toString)
                        .collect(Collectors.joining(",")))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.name", notNullValue()))
                .andExpect(jsonPath("$.name.certification", notNullValue()))
                .andExpect(jsonPath("$.name.value", notNullValue()))
                .andExpect(jsonPath("$", not(hasProperty("fiscalCode"))))
                .andExpect(jsonPath("$", not(hasProperty("familyName"))))
                .andExpect(jsonPath("$", not(hasProperty("email"))))
                .andExpect(jsonPath("$", not(hasProperty("birthDate"))))
                .andExpect(jsonPath("$", not(hasProperty("workContacts"))));
        // then
        verify(userServiceMock, times(1))
                .findById(id.toString(), false);
        verifyNoMoreInteractions(userServiceMock);
    }


    @Test
    void findById_fiscalCodeRequested_flAsMultiValueFormat() throws Exception {
        // given
        UUID id = UUID.randomUUID();
        EnumSet<UserResource.Fields> fields = EnumSet.of(UserResource.Fields.name, UserResource.Fields.fiscalCode);
        User user = new DummyUser();
        Mockito.when(userServiceMock.findById(Mockito.anyString(), Mockito.anyBoolean()))
                .thenReturn(user);
        // when
        mvc.perform(MockMvcRequestBuilders
                .get(BASE_URL + "/{id}", id)
                .queryParam("fl", fields.stream()
                        .map(Enum::toString)
                        .collect(Collectors.toList())
                        .toArray(new String[fields.size()]))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.name", notNullValue()))
                .andExpect(jsonPath("$.name.certification", notNullValue()))
                .andExpect(jsonPath("$.name.value", notNullValue()))
                .andExpect(jsonPath("$.fiscalCode", notNullValue()))
                .andExpect(jsonPath("$", not(hasProperty("familyName"))))
                .andExpect(jsonPath("$", not(hasProperty("email"))))
                .andExpect(jsonPath("$", not(hasProperty("birthDate"))))
                .andExpect(jsonPath("$", not(hasProperty("workContacts"))));
        // then
        verify(userServiceMock, times(1))
                .findById(id.toString(), true);
        verifyNoMoreInteractions(userServiceMock);
    }

    @Test
    void search(@Value("classpath:stubs/userSearchDto.json") Resource userSearchDto) throws Exception {
        // given
        String namespace = "selfcare";
        EnumSet<UserResource.Fields> fields = EnumSet.of(UserResource.Fields.name);
        User user = new DummyUser();
        Mockito.when(userServiceMock.search(any(), any()))
                .thenReturn(user);
        // when
        mvc.perform(MockMvcRequestBuilders
                .post(BASE_URL + "/search")
                .header(NAMESPACE_HEADER_NAME, namespace)
                .queryParam("fl", fields.stream()
                        .map(Enum::toString)
                        .collect(Collectors.joining(",")))
                .content(userSearchDto.getInputStream().readAllBytes())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.name", notNullValue()))
                .andExpect(jsonPath("$.name.certification", notNullValue()))
                .andExpect(jsonPath("$.name.value", notNullValue()))
                .andExpect(jsonPath("$", not(hasProperty("fiscalCode"))))
                .andExpect(jsonPath("$", not(hasProperty("familyName"))))
                .andExpect(jsonPath("$", not(hasProperty("email"))))
                .andExpect(jsonPath("$", not(hasProperty("birthDate"))))
                .andExpect(jsonPath("$", not(hasProperty("workContacts"))));
        // then
        String stubbedFiscalCode = JsonPath.parse(userSearchDto.getInputStream()).read("$.fiscalCode", String.class);
        verify(userServiceMock, times(1))
                .search(stubbedFiscalCode, namespace);
        verifyNoMoreInteractions(userServiceMock);
    }


    @Test
    void update(@Value("classpath:stubs/mutableUserFieldsDto.json") Resource mutableUserFieldsDto) throws Exception {
        // given
        UUID uuid = UUID.randomUUID();
        Mockito.doNothing().when(userServiceMock)
                .update(any(), any());
        // when
        mvc.perform(MockMvcRequestBuilders
                .patch(BASE_URL + "/{id}", uuid)
                .content(mutableUserFieldsDto.getInputStream().readAllBytes())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNoContent());
        // then
        verify(userServiceMock, times(1))
                .update(eq(uuid.toString()), any(User.class));
        verifyNoMoreInteractions(userServiceMock);
    }


    @Test
    void save(@Value("classpath:stubs/saveUserDto.json") Resource saveUserDto) throws Exception {
        // given
        String namespace = "selfcare";
        EnumSet<UserResource.Fields> fields = EnumSet.of(UserResource.Fields.name);
        UUID uuid = UUID.randomUUID();
        Mockito.when(userServiceMock.save(any(), any()))
                .thenReturn(uuid.toString());
        // when
        mvc.perform(MockMvcRequestBuilders
                .patch(BASE_URL + "/")
                .header(NAMESPACE_HEADER_NAME, namespace)
                .queryParam("fl", fields.stream()
                        .map(Enum::toString)
                        .collect(Collectors.joining(",")))
                .content(saveUserDto.getInputStream().readAllBytes())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", notNullValue()));
        // then
        verify(userServiceMock, times(1))
                .save(any(User.class), eq(namespace));
        verifyNoMoreInteractions(userServiceMock);
    }


    @Test
    void deleteById() throws Exception {
        // given
        UUID uuid = UUID.randomUUID();
        // when
        mvc.perform(MockMvcRequestBuilders
                .delete(BASE_URL + "/{id}", uuid)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is5xxServerError());
        // then
        verifyNoInteractions(userServiceMock);
    }

}