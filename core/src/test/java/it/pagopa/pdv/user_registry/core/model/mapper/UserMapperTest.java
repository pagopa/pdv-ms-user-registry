package it.pagopa.pdv.user_registry.core.model.mapper;

import it.pagopa.pdv.user_registry.connector.model.CertifiableField;
import it.pagopa.pdv.user_registry.connector.model.DummyPersonResource;
import it.pagopa.pdv.user_registry.connector.model.PersonResource;
import it.pagopa.pdv.user_registry.connector.model.SavePersonDto;
import it.pagopa.pdv.user_registry.core.model.DummyUser;
import it.pagopa.pdv.user_registry.core.model.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserMapperTest {

    @Test
    void mapUserToSavePersonDto_nullInput() {
        // given
        User user = null;
        // when
        SavePersonDto result = UserMapper.map(user);
        // then
        assertNull(result);
    }


    @Test
    void mapUserToSavePersonDto() {
        // given
        User user = new DummyUser();
        // when
        SavePersonDto result = UserMapper.map(user);
        // then
        assertNotNull(result);
        assertCertifiableFieldEquals(user.getName(), result.getName());
        assertCertifiableFieldEquals(user.getFamilyName(), result.getFamilyName());
        assertCertifiableFieldEquals(user.getEmail(), result.getEmail());
        assertCertifiableFieldEquals(user.getBirthDate(), result.getBirthDate());
        assertNotNull(result.getWorkContacts());
        user.getWorkContacts().forEach((s, wc) -> assertCertifiableFieldEquals(wc.getEmail(), result.getWorkContacts().get(s).getEmail()));
    }


    private void assertCertifiableFieldEquals(CertifiableField<?> expected, CertifiableField<?> actual) {
        assertEquals(expected.getCertification(), actual.getCertification());
        assertEquals(expected.getValue(), actual.getValue());
    }


    @Test
    void assembles_nullPerson() {
        // given
        String id = null;
        PersonResource personResource = null;
        // when
        User result = UserMapper.assembles(id, personResource);
        // then
        assertNull(result);
    }


    @Test
    void assembles_withoutFiscalCode() {
        // given
        String id = "id";
        PersonResource personResource = new DummyPersonResource();
        // when
        User result = UserMapper.assembles(id, personResource);
        // then
        assertNotNull(result);
        assertEquals(id, result.getId());
        assertNull(result.getFiscalCode());
        assertCertifiableFieldEquals(personResource.getName(), result.getName());
        assertCertifiableFieldEquals(personResource.getFamilyName(), result.getFamilyName());
        assertCertifiableFieldEquals(personResource.getEmail(), result.getEmail());
        assertCertifiableFieldEquals(personResource.getBirthDate(), result.getBirthDate());
        assertNotNull(result.getWorkContacts());
        personResource.getWorkContacts().forEach((s, wc) -> assertCertifiableFieldEquals(wc.getEmail(), result.getWorkContacts().get(s).getEmail()));
    }

    @Test
    void assembles_withFiscalCode() {
        // given
        String id = "id";
        PersonResource personResource = new DummyPersonResource();
        String fiscalCode = "fiscalCode";
        // when
        User result = UserMapper.assembles(id, personResource, fiscalCode);
        // then
        assertNotNull(result);
        assertEquals(id, result.getId());
        assertEquals(fiscalCode, result.getFiscalCode());
        assertCertifiableFieldEquals(personResource.getName(), result.getName());
        assertCertifiableFieldEquals(personResource.getFamilyName(), result.getFamilyName());
        assertCertifiableFieldEquals(personResource.getEmail(), result.getEmail());
        assertCertifiableFieldEquals(personResource.getBirthDate(), result.getBirthDate());
        assertNotNull(result.getWorkContacts());
        personResource.getWorkContacts().forEach((s, wc) -> assertCertifiableFieldEquals(wc.getEmail(), result.getWorkContacts().get(s).getEmail()));
    }

}