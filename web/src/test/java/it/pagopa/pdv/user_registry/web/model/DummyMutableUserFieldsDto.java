package it.pagopa.pdv.user_registry.web.model;

import java.time.LocalDate;
import java.util.Map;

public class DummyMutableUserFieldsDto extends MutableUserFieldsDto {

  public DummyMutableUserFieldsDto() {
    setName(new DummyCertifiableFieldResource<>(String.class));
    setFamilyName(new DummyCertifiableFieldResource<>(String.class));
    setEmail(new DummyCertifiableFieldResource<>(String.class));
    setBirthDate(new DummyCertifiableFieldResource<>(LocalDate.class));
    setSpidCode(new DummyCertifiableFieldResource<>(String.class));
    setPlaceOfBirth(new DummyCertifiableFieldResource<>(String.class));
    setCountyOfBirth(new DummyCertifiableFieldResource<>(String.class));
    setGender(new DummyCertifiableFieldResource<>(String.class));
    setCompanyName(new DummyCertifiableFieldResource<>(String.class));
    setRegisteredOffice(new DummyCertifiableFieldResource<>(String.class));
    setIvaCode(new DummyCertifiableFieldResource<>(String.class));
    setIdCard(new DummyCertifiableFieldResource<>(String.class));
    setMobilePhone(new DummyCertifiableFieldResource<>(String.class));
    setAddress(new DummyCertifiableFieldResource<>(String.class));
    setExpirationDate(new DummyCertifiableFieldResource<>(LocalDate.class));
    setDigitalAddress(new DummyCertifiableFieldResource<>(String.class));
    setDomicileAddress(new DummyCertifiableFieldResource<>(String.class));
    setDomicilePlace(new DummyCertifiableFieldResource<>(String.class));
    setDomicilePostalCode(new DummyCertifiableFieldResource<>(String.class));
    setDomicileProvince(new DummyCertifiableFieldResource<>(String.class));
    setDomicileCountry(new DummyCertifiableFieldResource<>(String.class));
    setQualification(new DummyCertifiableFieldResource<>(String.class));
    setCommonName(new DummyCertifiableFieldResource<>(String.class));
    setSurname(new DummyCertifiableFieldResource<>(String.class));
    setGivenName(new DummyCertifiableFieldResource<>(String.class));
    setPreferredUsername(new DummyCertifiableFieldResource<>(String.class));
    setTitle(new DummyCertifiableFieldResource<>(String.class));
    setUserCertificate(new DummyCertifiableFieldResource<>(String.class));
    setEmployeeNumber(new DummyCertifiableFieldResource<>(String.class));
    setOrgUnitName(new DummyCertifiableFieldResource<>(String.class));
    setPreferredLanguage(new DummyCertifiableFieldResource<>(String.class));
    setCountry(new DummyCertifiableFieldResource<>(String.class));
    setStateOrProvince(new DummyCertifiableFieldResource<>(String.class));
    setCity(new DummyCertifiableFieldResource<>(String.class));
    setPostalCode(new DummyCertifiableFieldResource<>(String.class));
    setStreet(new DummyCertifiableFieldResource<>(String.class));
    setWorkContacts(Map.of("inst-1", new DummyMutableUserFieldsDto.DummyWorkContact(),
        "inst-2", new DummyMutableUserFieldsDto.DummyWorkContact()));
  }


  public static class DummyWorkContact extends WorkContactResource {

    public DummyWorkContact() {
      setEmail(new DummyCertifiableFieldResource<>(String.class));
      setMobilePhone(new DummyCertifiableFieldResource<>(String.class));
      setTelephone(new DummyCertifiableFieldResource<>(String.class));
    }
  }
}