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
    user.getWorkContacts().forEach((s, wc) -> assertCertifiableFieldEquals(wc.getEmail(),
        result.getWorkContacts().get(s).getEmail()));
  }


  private void assertCertifiableFieldEquals(CertifiableField<?> expected,
      CertifiableField<?> actual) {
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
    assertCertifiableFieldEquals(personResource.getSpidCode(), result.getSpidCode());
    assertCertifiableFieldEquals(personResource.getPlaceOfBirth(), result.getPlaceOfBirth());
    assertCertifiableFieldEquals(personResource.getCountyOfBirth(), result.getCountyOfBirth());
    assertCertifiableFieldEquals(personResource.getGender(), result.getGender());
    assertCertifiableFieldEquals(personResource.getCompanyName(), result.getCompanyName());
    assertCertifiableFieldEquals(personResource.getRegisteredOffice(),
        result.getRegisteredOffice());
    assertCertifiableFieldEquals(personResource.getIvaCode(), result.getIvaCode());
    assertCertifiableFieldEquals(personResource.getIdCard(), result.getIdCard());
    assertCertifiableFieldEquals(personResource.getMobilePhone(), result.getMobilePhone());
    assertCertifiableFieldEquals(personResource.getAddress(), result.getAddress());
    assertCertifiableFieldEquals(personResource.getExpirationDate(), result.getExpirationDate());
    assertCertifiableFieldEquals(personResource.getDigitalAddress(), result.getDigitalAddress());
    assertCertifiableFieldEquals(personResource.getDomicileAddress(), result.getDomicileAddress());
    assertCertifiableFieldEquals(personResource.getDomicilePlace(), result.getDomicilePlace());
    assertCertifiableFieldEquals(personResource.getDomicilePostalCode(),
        result.getDomicilePostalCode());
    assertCertifiableFieldEquals(personResource.getDomicileProvince(),
        result.getDomicileProvince());
    assertCertifiableFieldEquals(personResource.getDomicileCountry(), result.getDomicileCountry());
    assertCertifiableFieldEquals(personResource.getQualification(), result.getQualification());
    assertCertifiableFieldEquals(personResource.getCommonName(), result.getCommonName());
    assertCertifiableFieldEquals(personResource.getSurname(), result.getSurname());
    assertCertifiableFieldEquals(personResource.getGivenName(), result.getGivenName());
    assertCertifiableFieldEquals(personResource.getPreferredUsername(),
        result.getPreferredUsername());
    assertCertifiableFieldEquals(personResource.getTitle(), result.getTitle());
    assertCertifiableFieldEquals(personResource.getUserCertificate(), result.getUserCertificate());
    assertCertifiableFieldEquals(personResource.getEmployeeNumber(), result.getEmployeeNumber());
    assertCertifiableFieldEquals(personResource.getOrgUnitName(), result.getOrgUnitName());
    assertCertifiableFieldEquals(personResource.getPreferredLanguage(),
        result.getPreferredLanguage());
    assertCertifiableFieldEquals(personResource.getCountry(), result.getCountry());
    assertCertifiableFieldEquals(personResource.getStateOrProvince(), result.getStateOrProvince());
    assertCertifiableFieldEquals(personResource.getCity(), result.getCity());
    assertCertifiableFieldEquals(personResource.getPostalCode(), result.getPostalCode());
    assertCertifiableFieldEquals(personResource.getStreet(), result.getStreet());

    assertNotNull(result.getWorkContacts());
    personResource.getWorkContacts().forEach((s, wc) -> {

      assertCertifiableFieldEquals(wc.getEmail(),
          result.getWorkContacts().get(s).getEmail());
      assertCertifiableFieldEquals(wc.getMobilePhone(),
          result.getWorkContacts().get(s).getMobilePhone());
      assertCertifiableFieldEquals(wc.getTelephone(),
          result.getWorkContacts().get(s).getTelephone());
    });
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
    assertCertifiableFieldEquals(personResource.getSpidCode(), result.getSpidCode());
    assertCertifiableFieldEquals(personResource.getPlaceOfBirth(), result.getPlaceOfBirth());
    assertCertifiableFieldEquals(personResource.getCountyOfBirth(), result.getCountyOfBirth());
    assertCertifiableFieldEquals(personResource.getGender(), result.getGender());
    assertCertifiableFieldEquals(personResource.getCompanyName(), result.getCompanyName());
    assertCertifiableFieldEquals(personResource.getRegisteredOffice(),
        result.getRegisteredOffice());
    assertCertifiableFieldEquals(personResource.getIvaCode(), result.getIvaCode());
    assertCertifiableFieldEquals(personResource.getIdCard(), result.getIdCard());
    assertCertifiableFieldEquals(personResource.getMobilePhone(), result.getMobilePhone());
    assertCertifiableFieldEquals(personResource.getAddress(), result.getAddress());
    assertCertifiableFieldEquals(personResource.getExpirationDate(), result.getExpirationDate());
    assertCertifiableFieldEquals(personResource.getDigitalAddress(), result.getDigitalAddress());
    assertCertifiableFieldEquals(personResource.getDomicileAddress(), result.getDomicileAddress());
    assertCertifiableFieldEquals(personResource.getDomicilePlace(), result.getDomicilePlace());
    assertCertifiableFieldEquals(personResource.getDomicilePostalCode(),
        result.getDomicilePostalCode());
    assertCertifiableFieldEquals(personResource.getDomicileProvince(),
        result.getDomicileProvince());
    assertCertifiableFieldEquals(personResource.getDomicileCountry(), result.getDomicileCountry());
    assertCertifiableFieldEquals(personResource.getQualification(), result.getQualification());
    assertCertifiableFieldEquals(personResource.getCommonName(), result.getCommonName());
    assertCertifiableFieldEquals(personResource.getSurname(), result.getSurname());
    assertCertifiableFieldEquals(personResource.getGivenName(), result.getGivenName());
    assertCertifiableFieldEquals(personResource.getPreferredUsername(),
        result.getPreferredUsername());
    assertCertifiableFieldEquals(personResource.getTitle(), result.getTitle());
    assertCertifiableFieldEquals(personResource.getUserCertificate(), result.getUserCertificate());
    assertCertifiableFieldEquals(personResource.getEmployeeNumber(), result.getEmployeeNumber());
    assertCertifiableFieldEquals(personResource.getOrgUnitName(), result.getOrgUnitName());
    assertCertifiableFieldEquals(personResource.getPreferredLanguage(),
        result.getPreferredLanguage());
    assertCertifiableFieldEquals(personResource.getCountry(), result.getCountry());
    assertCertifiableFieldEquals(personResource.getStateOrProvince(), result.getStateOrProvince());
    assertCertifiableFieldEquals(personResource.getCity(), result.getCity());
    assertCertifiableFieldEquals(personResource.getPostalCode(), result.getPostalCode());
    assertCertifiableFieldEquals(personResource.getStreet(), result.getStreet());
    assertNotNull(result.getWorkContacts());
    personResource.getWorkContacts().forEach((s, wc) -> assertCertifiableFieldEquals(wc.getEmail(),
        result.getWorkContacts().get(s).getEmail()));
  }

}