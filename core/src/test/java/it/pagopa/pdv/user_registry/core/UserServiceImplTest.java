package it.pagopa.pdv.user_registry.core;

import it.pagopa.pdv.user_registry.TestUtils;
import it.pagopa.pdv.user_registry.connector.PersonConnector;
import it.pagopa.pdv.user_registry.connector.TokenizerConnector;
import it.pagopa.pdv.user_registry.connector.model.*;
import it.pagopa.pdv.user_registry.core.model.DummyUser;
import it.pagopa.pdv.user_registry.core.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private PersonConnector personConnectorMock;

    @Mock
    private TokenizerConnector tokenizerConnectorMock;

    @Captor
    private ArgumentCaptor<CreateTokenDto> createTokenDtoCaptor;

    @Captor
    private ArgumentCaptor<SavePersonNamespaceDto> savePersonNamespaceDtoCaptor;

    @Captor
    private ArgumentCaptor<SearchTokenFilterCriteria> searchTokenFilterCriteriaCaptor;


    @Test
    void save_nullUser() {
        // given
        User user = null;
        String namespace = "selfcare";
        // when
        Executable executable = () -> userService.save(user, namespace);
        // then
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, executable);
        assertEquals("A user is required", e.getMessage());
        verifyNoInteractions(personConnectorMock, tokenizerConnectorMock);
    }


    @Test
    void save_nullNamespace() {
        // given
        User user = new User();
        String namespace = null;
        // when
        Executable executable = () -> userService.save(user, namespace);
        // then
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, executable);
        assertEquals("A namespace is required", e.getMessage());
        verifyNoInteractions(personConnectorMock, tokenizerConnectorMock);
    }


    @Test
    void save() {
        // given
        User user = new DummyUser();
        String namespace = "selfcare";
        TokenResource tokenResource = TestUtils.mockInstance(new TokenResource());
        when(tokenizerConnectorMock.save(any(), any()))
                .thenReturn(tokenResource);
        // when
        String uuid = userService.save(user, namespace);
        // then
        verify(tokenizerConnectorMock, times(1))
                .save(eq(namespace), createTokenDtoCaptor.capture());
        assertEquals(user.getFiscalCode(), createTokenDtoCaptor.getValue().getPii());
        verify(personConnectorMock, times(1))
                .saveNamespacedId(eq(tokenResource.getRootToken()), eq(namespace), savePersonNamespaceDtoCaptor.capture());
        assertEquals(tokenResource.getToken(), savePersonNamespaceDtoCaptor.getValue().getNamespacedId());
        verify(personConnectorMock, times(1))
                .save(eq(tokenResource.getRootToken()), any(SavePersonDto.class));
        verifyNoMoreInteractions(personConnectorMock, tokenizerConnectorMock);
    }


    @Test
    void findById_nullId() {
        // given
        String id = null;
        String namespace = "namespace";
        boolean fetchFiscalCode = true;
        // when
        Executable executable = () -> userService.findById(id, namespace, fetchFiscalCode);
        // then
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, executable);
        assertEquals("A user id is required", e.getMessage());
        verifyNoInteractions(personConnectorMock, tokenizerConnectorMock);
    }


    @Test
    void findById_doNotFetchFiscalCode() {
        // given
        String id = "id";
        String namespace = "namespace";
        boolean fetchFiscalCode = false;
        DummyPersonResource personResource = new DummyPersonResource();
        when(personConnectorMock.findById(any(), any()))
                .thenReturn(personResource);
        // when
        User user = userService.findById(id, namespace, fetchFiscalCode);
        // then
        assertNotNull(user);
        verify(personConnectorMock, times(1))
                .findById(id, namespace);
        verifyNoMoreInteractions(personConnectorMock);
        verifyNoInteractions(tokenizerConnectorMock);
    }


    @Test
    void findById_fetchFiscalCode() {
        // given
        String id = "id";
        String namespace = "namespace";
        boolean fetchFiscalCode = true;
        DummyPersonResource personResource = new DummyPersonResource();
        when(personConnectorMock.findById(any(), any()))
                .thenReturn(personResource);
        PiiResource piiResource = TestUtils.mockInstance(new PiiResource());
        when(tokenizerConnectorMock.findPiiByToken(any(), any()))
                .thenReturn(piiResource);
        // when
        User user = userService.findById(id, namespace, fetchFiscalCode);
        // then
        assertNotNull(user);
        assertEquals(user.getFiscalCode(), piiResource.getPii());
        verify(personConnectorMock, times(1))
                .findById(id, namespace);
        verify(tokenizerConnectorMock, times(1))
                .findPiiByToken(id, namespace);
        verifyNoMoreInteractions(personConnectorMock, tokenizerConnectorMock);
    }


    @Test
    void update_nullId() {
        // given
        String id = null;
        String namespace = "namespace";
        User user = new User();
        // when
        Executable executable = () -> userService.update(id, user, namespace);
        // then
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, executable);
        assertEquals("A user id is required", e.getMessage());
        verifyNoInteractions(personConnectorMock, tokenizerConnectorMock);
    }


    @Test
    void update_nullUser() {
        // given
        String id = "id";
        String namespace = "namespace";
        User user = null;
        // when
        Executable executable = () -> userService.update(id, user, namespace);
        // then
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, executable);
        assertEquals("A user is required", e.getMessage());
        verifyNoInteractions(personConnectorMock, tokenizerConnectorMock);
    }


    @Test
    void update() {
        // given
        String id = "id";
        String namespace = "namespace";
        User user = new DummyUser();
        PersonGlobalId personGlobalId = TestUtils.mockInstance(new PersonGlobalId());
        when(personConnectorMock.findIdByNamespacedId(any(),any()))
                .thenReturn(personGlobalId);
        // when
        userService.update(id, user, namespace);
        // then
        verify(personConnectorMock, times(1))
                .findIdByNamespacedId(id,namespace);
        verify(personConnectorMock, times(1))
                .save(eq(personGlobalId.getId()), any(SavePersonDto.class));
        verifyNoMoreInteractions(personConnectorMock);
        verifyNoInteractions(tokenizerConnectorMock);
    }


    @Test
    void search_nullFiscalCode() {
        // given
        String fiscalCode = null;
        String namespace = "selfcare";
        // when
        Executable executable = () -> userService.search(fiscalCode, namespace);
        // then
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, executable);
        assertEquals("A fiscal code is required", e.getMessage());
        verifyNoInteractions(personConnectorMock, tokenizerConnectorMock);
    }


    @Test
    void search_nullNamespace() {
        // given
        String fiscalCode = "fiscalCode";
        String namespace = null;
        // when
        Executable executable = () -> userService.search(fiscalCode, namespace);
        // then
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, executable);
        assertEquals("A namespace is required", e.getMessage());
        verifyNoInteractions(personConnectorMock, tokenizerConnectorMock);
    }


    @Test
    void search() {
        // given
        String fiscalCode = "fiscalCode";
        String namespace = "selfcare";
        TokenResource tokenResource = TestUtils.mockInstance(new TokenResource());
        when(tokenizerConnectorMock.search(any(), any()))
                .thenReturn(tokenResource);
        DummyPersonResource personResource = new DummyPersonResource();
        when(personConnectorMock.findById(any()))
                .thenReturn(personResource);
        // when
        User user = userService.search(fiscalCode, namespace);
        // then
        assertNotNull(user);
        assertEquals(tokenResource.getToken(), user.getId());
        assertEquals(fiscalCode, user.getFiscalCode());
        verify(tokenizerConnectorMock, times(1))
                .search(eq(namespace), searchTokenFilterCriteriaCaptor.capture());
        verify(personConnectorMock, times(1))
                .findById(tokenResource.getRootToken());
        assertEquals(fiscalCode, searchTokenFilterCriteriaCaptor.getValue().getPii());
        verifyNoMoreInteractions(tokenizerConnectorMock, personConnectorMock);
    }

}