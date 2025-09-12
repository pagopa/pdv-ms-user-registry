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
    assertCertifiableFieldEquals(user.getSpidCode(), result.getSpidCode());
    assertCertifiableFieldEquals(user.getPlaceOfBirth(), result.getPlaceOfBirth());
    assertCertifiableFieldEquals(user.getCountyOfBirth(), result.getCountyOfBirth());
    assertCertifiableFieldEquals(user.getGender(), result.getGender());
    assertCertifiableFieldEquals(user.getCompanyName(), result.getCompanyName());
    assertCertifiableFieldEquals(user.getRegisteredOffice(), result.getRegisteredOffice());
    assertCertifiableFieldEquals(user.getIvaCode(), result.getIvaCode());
    assertCertifiableFieldEquals(user.getIdCard(), result.getIdCard());
    assertCertifiableFieldEquals(user.getMobilePhone(), result.getMobilePhone());
    assertCertifiableFieldEquals(user.getAddress(), result.getAddress());
    assertCertifiableFieldEquals(user.getExpirationDate(), result.getExpirationDate());
    assertCertifiableFieldEquals(user.getDigitalAddress(), result.getDigitalAddress());
    assertCertifiableFieldEquals(user.getDomicileAddress(), result.getDomicileAddress());
    assertCertifiableFieldEquals(user.getDomicilePlace(), result.getDomicilePlace());
    assertCertifiableFieldEquals(user.getDomicilePostalCode(), result.getDomicilePostalCode());
    assertCertifiableFieldEquals(user.getDomicileProvince(), result.getDomicileProvince());
    assertCertifiableFieldEquals(user.getDomicileCountry(), result.getDomicileCountry());
    assertCertifiableFieldEquals(user.getQualification(), result.getQualification());
    assertCertifiableFieldEquals(user.getCommonName(), result.getCommonName());
    assertCertifiableFieldEquals(user.getSurname(), result.getSurname());
    assertCertifiableFieldEquals(user.getGivenName(), result.getGivenName());
    assertCertifiableFieldEquals(user.getPreferredUsername(), result.getPreferredUsername());
    assertCertifiableFieldEquals(user.getTitle(), result.getTitle());
    assertCertifiableFieldEquals(user.getUserCertificate(), result.getUserCertificate());
    assertCertifiableFieldEquals(user.getEmployeeNumber(), result.getEmployeeNumber());
    assertCertifiableFieldEquals(user.getOrgUnitName(), result.getOrgUnitName());
    assertCertifiableFieldEquals(user.getPreferredLanguage(), result.getPreferredLanguage());
    assertCertifiableFieldEquals(user.getCountry(), result.getCountry());
    assertCertifiableFieldEquals(user.getStateOrProvince(), result.getStateOrProvince());
    assertCertifiableFieldEquals(user.getCity(), result.getCity());
    assertCertifiableFieldEquals(user.getPostalCode(), result.getPostalCode());
    assertCertifiableFieldEquals(user.getStreet(), result.getStreet());
    assertNotNull(result.getWorkContacts());
    user.getWorkContacts().forEach((s, wc) ->
    {
      assertCertifiableFieldEquals(wc.getEmail(), result.getWorkContacts().get(s).getEmail());
      assertCertifiableFieldEquals(wc.getMobilePhone(),
          result.getWorkContacts().get(s).getMobilePhone());
      assertCertifiableFieldEquals(wc.getTelephone(),
          result.getWorkContacts().get(s).getTelephone());
    });
  }


  private void assertCertifiableFieldEquals(CertifiableField<?> expected,
      CertifiableFieldResource<?> actual) {
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
    assertCertifiableFieldEquals(mutableUserFieldsDto.getSpidCode(), result.getSpidCode());
    assertCertifiableFieldEquals(mutableUserFieldsDto.getPlaceOfBirth(), result.getPlaceOfBirth());
    assertCertifiableFieldEquals(mutableUserFieldsDto.getCountyOfBirth(), result.getCountyOfBirth());
    assertCertifiableFieldEquals(mutableUserFieldsDto.getGender(), result.getGender());
    assertCertifiableFieldEquals(mutableUserFieldsDto.getCompanyName(), result.getCompanyName());
    assertCertifiableFieldEquals(mutableUserFieldsDto.getRegisteredOffice(), result.getRegisteredOffice());
    assertCertifiableFieldEquals(mutableUserFieldsDto.getIvaCode(), result.getIvaCode());
    assertCertifiableFieldEquals(mutableUserFieldsDto.getIdCard(), result.getIdCard());
    assertCertifiableFieldEquals(mutableUserFieldsDto.getMobilePhone(), result.getMobilePhone());
    assertCertifiableFieldEquals(mutableUserFieldsDto.getAddress(), result.getAddress());
    assertCertifiableFieldEquals(mutableUserFieldsDto.getExpirationDate(), result.getExpirationDate());
    assertCertifiableFieldEquals(mutableUserFieldsDto.getDigitalAddress(), result.getDigitalAddress());
    assertCertifiableFieldEquals(mutableUserFieldsDto.getDomicileAddress(), result.getDomicileAddress());
    assertCertifiableFieldEquals(mutableUserFieldsDto.getDomicilePlace(), result.getDomicilePlace());
    assertCertifiableFieldEquals(mutableUserFieldsDto.getDomicilePostalCode(), result.getDomicilePostalCode());
    assertCertifiableFieldEquals(mutableUserFieldsDto.getDomicileProvince(), result.getDomicileProvince());
    assertCertifiableFieldEquals(mutableUserFieldsDto.getDomicileCountry(), result.getDomicileCountry());
    assertCertifiableFieldEquals(mutableUserFieldsDto.getQualification(), result.getQualification());
    assertCertifiableFieldEquals(mutableUserFieldsDto.getCommonName(), result.getCommonName());
    assertCertifiableFieldEquals(mutableUserFieldsDto.getSurname(), result.getSurname());
    assertCertifiableFieldEquals(mutableUserFieldsDto.getGivenName(), result.getGivenName());
    assertCertifiableFieldEquals(mutableUserFieldsDto.getPreferredUsername(), result.getPreferredUsername());
    assertCertifiableFieldEquals(mutableUserFieldsDto.getTitle(), result.getTitle());
    assertCertifiableFieldEquals(mutableUserFieldsDto.getUserCertificate(), result.getUserCertificate());
    assertCertifiableFieldEquals(mutableUserFieldsDto.getEmployeeNumber(), result.getEmployeeNumber());
    assertCertifiableFieldEquals(mutableUserFieldsDto.getOrgUnitName(), result.getOrgUnitName());
    assertCertifiableFieldEquals(mutableUserFieldsDto.getPreferredLanguage(), result.getPreferredLanguage());
    assertCertifiableFieldEquals(mutableUserFieldsDto.getCountry(), result.getCountry());
    assertCertifiableFieldEquals(mutableUserFieldsDto.getStateOrProvince(), result.getStateOrProvince());
    assertCertifiableFieldEquals(mutableUserFieldsDto.getCity(), result.getCity());
    assertCertifiableFieldEquals(mutableUserFieldsDto.getPostalCode(), result.getPostalCode());
    assertCertifiableFieldEquals(mutableUserFieldsDto.getStreet(), result.getStreet());
    assertNotNull(result.getWorkContacts());
    mutableUserFieldsDto.getWorkContacts().forEach((s, wc) -> {
      assertCertifiableFieldEquals(wc.getEmail(), result.getWorkContacts().get(s).getEmail());
      assertCertifiableFieldEquals(wc.getMobilePhone(),
          result.getWorkContacts().get(s).getMobilePhone());
      assertCertifiableFieldEquals(wc.getTelephone(),
          result.getWorkContacts().get(s).getTelephone());
    });
  }


  private void assertCertifiableFieldEquals(CertifiableFieldResource<?> expected,
      CertifiableField<?> actual) {
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
    assertCertifiableFieldEquals(saveUserDto.getSpidCode(), result.getSpidCode());
    assertCertifiableFieldEquals(saveUserDto.getPlaceOfBirth(), result.getPlaceOfBirth());
    assertCertifiableFieldEquals(saveUserDto.getCountyOfBirth(), result.getCountyOfBirth());
    assertCertifiableFieldEquals(saveUserDto.getGender(), result.getGender());
    assertCertifiableFieldEquals(saveUserDto.getCompanyName(), result.getCompanyName());
    assertCertifiableFieldEquals(saveUserDto.getRegisteredOffice(), result.getRegisteredOffice());
    assertCertifiableFieldEquals(saveUserDto.getIvaCode(), result.getIvaCode());
    assertCertifiableFieldEquals(saveUserDto.getIdCard(), result.getIdCard());
    assertCertifiableFieldEquals(saveUserDto.getMobilePhone(), result.getMobilePhone());
    assertCertifiableFieldEquals(saveUserDto.getAddress(), result.getAddress());
    assertCertifiableFieldEquals(saveUserDto.getExpirationDate(), result.getExpirationDate());
    assertCertifiableFieldEquals(saveUserDto.getDigitalAddress(), result.getDigitalAddress());
    assertCertifiableFieldEquals(saveUserDto.getDomicileAddress(), result.getDomicileAddress());
    assertCertifiableFieldEquals(saveUserDto.getDomicilePlace(), result.getDomicilePlace());
    assertCertifiableFieldEquals(saveUserDto.getDomicilePostalCode(), result.getDomicilePostalCode());
    assertCertifiableFieldEquals(saveUserDto.getDomicileProvince(), result.getDomicileProvince());
    assertCertifiableFieldEquals(saveUserDto.getDomicileCountry(), result.getDomicileCountry());
    assertCertifiableFieldEquals(saveUserDto.getQualification(), result.getQualification());
    assertCertifiableFieldEquals(saveUserDto.getCommonName(), result.getCommonName());
    assertCertifiableFieldEquals(saveUserDto.getSurname(), result.getSurname());
    assertCertifiableFieldEquals(saveUserDto.getGivenName(), result.getGivenName());
    assertCertifiableFieldEquals(saveUserDto.getPreferredUsername(), result.getPreferredUsername());
    assertCertifiableFieldEquals(saveUserDto.getTitle(), result.getTitle());
    assertCertifiableFieldEquals(saveUserDto.getUserCertificate(), result.getUserCertificate());
    assertCertifiableFieldEquals(saveUserDto.getEmployeeNumber(), result.getEmployeeNumber());
    assertCertifiableFieldEquals(saveUserDto.getOrgUnitName(), result.getOrgUnitName());
    assertCertifiableFieldEquals(saveUserDto.getPreferredLanguage(), result.getPreferredLanguage());
    assertCertifiableFieldEquals(saveUserDto.getCountry(), result.getCountry());
    assertCertifiableFieldEquals(saveUserDto.getStateOrProvince(), result.getStateOrProvince());
    assertCertifiableFieldEquals(saveUserDto.getCity(), result.getCity());
    assertCertifiableFieldEquals(saveUserDto.getPostalCode(), result.getPostalCode());
    assertCertifiableFieldEquals(saveUserDto.getStreet(), result.getStreet());
    assertNotNull(result.getWorkContacts());
    saveUserDto.getWorkContacts().forEach((s, wc) -> {
      assertCertifiableFieldEquals(wc.getEmail(), result.getWorkContacts().get(s).getEmail());
      assertCertifiableFieldEquals(wc.getMobilePhone(),
          result.getWorkContacts().get(s).getMobilePhone());
      assertCertifiableFieldEquals(wc.getTelephone(),
          result.getWorkContacts().get(s).getTelephone());
    });
  }

}