package it.pagopa.pdv.user_registry.connector.model;

import java.time.LocalDate;
import java.util.Map;

public class DummySavePersonDto extends SavePersonDto {

  public DummySavePersonDto() {
    setName(new DummyCertifiableField<>(String.class));
    setFamilyName(new DummyCertifiableField<>(String.class));
    setEmail(new DummyCertifiableField<>(String.class));
    setBirthDate(new DummyCertifiableField<>(LocalDate.class));
    setSpidCode(new DummyCertifiableField<>(String.class));
    setPlaceOfBirth(new DummyCertifiableField<>(String.class));
    setCountyOfBirth(new DummyCertifiableField<>(String.class));
    setGender(new DummyCertifiableField<>(String.class));
    setCompanyName(new DummyCertifiableField<>(String.class));
    setRegisteredOffice(new DummyCertifiableField<>(String.class));
    setIvaCode(new DummyCertifiableField<>(String.class));
    setIdCard(new DummyCertifiableField<>(String.class));
    setMobilePhone(new DummyCertifiableField<>(String.class));
    setAddress(new DummyCertifiableField<>(String.class));
    setExpirationDate(new DummyCertifiableField<>(LocalDate.class));
    setDigitalAddress(new DummyCertifiableField<>(String.class));
    setDomicileAddress(new DummyCertifiableField<>(String.class));
    setDomicilePlace(new DummyCertifiableField<>(String.class));
    setDomicilePostalCode(new DummyCertifiableField<>(String.class));
    setDomicileProvince(new DummyCertifiableField<>(String.class));
    setDomicileCountry(new DummyCertifiableField<>(String.class));
    setQualification(new DummyCertifiableField<>(String.class));
    setCommonName(new DummyCertifiableField<>(String.class));
    setSurname(new DummyCertifiableField<>(String.class));
    setGivenName(new DummyCertifiableField<>(String.class));
    setPreferredUsername(new DummyCertifiableField<>(String.class));
    setTitle(new DummyCertifiableField<>(String.class));
    setUserCertificate(new DummyCertifiableField<>(String.class));
    setEmployeeNumber(new DummyCertifiableField<>(String.class));
    setOrgUnitName(new DummyCertifiableField<>(String.class));
    setPreferredLanguage(new DummyCertifiableField<>(String.class));
    setCountry(new DummyCertifiableField<>(String.class));
    setStateOrProvince(new DummyCertifiableField<>(String.class));
    setCity(new DummyCertifiableField<>(String.class));
    setPostalCode(new DummyCertifiableField<>(String.class));
    setStreet(new DummyCertifiableField<>(String.class));
    setWorkContacts(Map.of("inst-1", new DummyWorkContact(),
        "inst-2", new DummyWorkContact()));
  }


  public static class DummyWorkContact extends WorkContactResource {

    public DummyWorkContact() {
      setEmail(new DummyCertifiableField<>(String.class));
      setMobilePhone(new DummyCertifiableField<>(String.class));
      setTelephone(new DummyCertifiableField<>(String.class));
    }
  }

}
