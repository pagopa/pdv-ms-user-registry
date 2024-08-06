package it.pagopa.pdv.user_registry.web.model.mapper;

import it.pagopa.pdv.user_registry.connector.model.CertifiableField;
import it.pagopa.pdv.user_registry.core.model.DummyUser;
import it.pagopa.pdv.user_registry.core.model.User;
import it.pagopa.pdv.user_registry.web.model.*;
import org.junit.jupiter.api.Test;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.EnumSet;

import static org.junit.jupiter.api.Assertions.*;

class UserMapperTest {

    @Test
    void mapUserToUserResource_nullUser() {
        // given
        User user = null;
        EnumSet<UserResource.Fields> fields = EnumSet.noneOf(UserResource.Fields.class);
        // when
        UserResource result = UserMapper.map(user, fields);
        // then
        assertNull(result);
    }


    @Test
    void mapUserToUserResource_nullFields() {
        // given
        User user = new DummyUser();
        EnumSet<UserResource.Fields> fields = null;
        // when
        UserResource result = UserMapper.map(user, fields);
        // then
        assertNotNull(result);
        assertEquals(user.getId(), result.getId().toString());
        ReflectionUtils.doWithLocalMethods(UserResource.class,
                method -> {
                    if (method.getName().startsWith("get")
                            && !method.getName().equals("getId")
                            || method.getName().startsWith("is")) {
                        try {
                            assertNull(method.invoke(result));
                        } catch (InvocationTargetException e) {
                            throw new RuntimeException(e);
                        }
                    }
                });
    }


    @Test
    void mapUserToUserResource() {
        // given
        User user = new DummyUser();
        EnumSet<UserResource.Fields> fields = EnumSet.allOf(UserResource.Fields.class);
        // when
        UserResource result = UserMapper.map(user, fields);
        // then
        assertNotNull(result);
        assertEquals(user.getId(), result.getId().toString());
        assertEquals(user.getFiscalCode(), result.getFiscalCode());
        assertCertifiableFieldEquals(user.getName(), result.getName());
        assertCertifiableFieldEquals(user.getFamilyName(), result.getFamilyName());
        assertCertifiableFieldEquals(user.getEmail(), result.getEmail());
        assertCertifiableFieldEquals(user.getBirthDate(), result.getBirthDate());
        assertNotNull(result.getWorkContacts());
        user.getWorkContacts().forEach((s, wc) ->
        {
            assertCertifiableFieldEquals(wc.getEmail(), result.getWorkContacts().get(s).getEmail());
            assertCertifiableFieldEquals(wc.getMobilePhone(), result.getWorkContacts().get(s).getMobilePhone());
            assertCertifiableFieldEquals(wc.getTelephone(), result.getWorkContacts().get(s).getTelephone());
        });
    }


    private void assertCertifiableFieldEquals(CertifiableField<?> expected, CertifiableFieldResource<?> actual) {
        assertEquals(expected.getCertification(), actual.getCertification().toString());
        assertEquals(expected.getValue(), actual.getValue());
    }


    @Test
    void mapMutableUserFieldsDtoToUser_nullInput() {
        // given
        MutableUserFieldsDto mutableUserFieldsDto = null;
        // when
        User result = UserMapper.map(mutableUserFieldsDto);
        // then
        assertNull(result);
    }


    @Test
    void mapMutableUserFieldsDtoToUser() {
        // given
        MutableUserFieldsDto mutableUserFieldsDto = new DummyMutableUserFieldsDto();
        // when
        User result = UserMapper.map(mutableUserFieldsDto);
        // then
        assertNotNull(result);
        assertNull(result.getId());
        assertNull(result.getFiscalCode());
        assertCertifiableFieldEquals(mutableUserFieldsDto.getName(), result.getName());
        assertCertifiableFieldEquals(mutableUserFieldsDto.getFamilyName(), result.getFamilyName());
        assertCertifiableFieldEquals(mutableUserFieldsDto.getEmail(), result.getEmail());
        assertCertifiableFieldEquals(mutableUserFieldsDto.getBirthDate(), result.getBirthDate());
        assertNotNull(result.getWorkContacts());
        mutableUserFieldsDto.getWorkContacts().forEach((s, wc) -> {
            assertCertifiableFieldEquals(wc.getEmail(), result.getWorkContacts().get(s).getEmail());
            assertCertifiableFieldEquals(wc.getMobilePhone(), result.getWorkContacts().get(s).getMobilePhone());
            assertCertifiableFieldEquals(wc.getTelephone(), result.getWorkContacts().get(s).getTelephone());
        });
    }


    private void assertCertifiableFieldEquals(CertifiableFieldResource<?> expected, CertifiableField<?> actual) {
        assertEquals(expected.getCertification().toString(), actual.getCertification());
        assertEquals(expected.getValue(), actual.getValue());
    }


    @Test
    void mapSaveUserDtoToUser_nullInput() {
        // given
        SaveUserDto saveUserDto = null;
        // when
        User result = UserMapper.map(saveUserDto);
        // then
        assertNull(result);
    }


    @Test
    void mapSaveUserDtoToUser() {
        // given
        SaveUserDto saveUserDto = new DummySaveUserDto();
        // when
        User result = UserMapper.map(saveUserDto);
        // then
        assertNotNull(result);
        assertNull(result.getId());
        assertEquals(saveUserDto.getFiscalCode(), result.getFiscalCode());
        assertCertifiableFieldEquals(saveUserDto.getName(), result.getName());
        assertCertifiableFieldEquals(saveUserDto.getFamilyName(), result.getFamilyName());
        assertCertifiableFieldEquals(saveUserDto.getEmail(), result.getEmail());
        assertCertifiableFieldEquals(saveUserDto.getBirthDate(), result.getBirthDate());
        assertNotNull(result.getWorkContacts());
        saveUserDto.getWorkContacts().forEach((s, wc) -> {
            assertCertifiableFieldEquals(wc.getEmail(), result.getWorkContacts().get(s).getEmail());
            assertCertifiableFieldEquals(wc.getMobilePhone(), result.getWorkContacts().get(s).getMobilePhone());
            assertCertifiableFieldEquals(wc.getTelephone(), result.getWorkContacts().get(s).getTelephone());
        });
    }

}