package it.pagopa.pdv.user_registry.web.model.mapper;

import it.pagopa.pdv.user_registry.connector.model.CertifiableField;
import it.pagopa.pdv.user_registry.core.model.User;
import it.pagopa.pdv.user_registry.web.model.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserMapper {

  public static UserResource map(User user, Set<UserResource.Fields> fields) {
    UserResource userResource = null;
    if (user != null) {
      userResource = new UserResource();
      userResource.setId(UUID.fromString(user.getId()));
      if (fields != null) {
        for (UserResource.Fields field : fields) {
          switch (field) {
            case fiscalCode:
              userResource.setFiscalCode(user.getFiscalCode());
              break;
            case name:
              userResource.setName(map(user.getName()));
              break;
            case familyName:
              userResource.setFamilyName(map(user.getFamilyName()));
              break;
            case email:
              userResource.setEmail(map(user.getEmail()));
              break;
            case birthDate:
              userResource.setBirthDate(map(user.getBirthDate()));
              break;
            case spidCode:
              userResource.setSpidCode(map(user.getSpidCode()));
              break;
            case placeOfBirth:
              userResource.setPlaceOfBirth(map(user.getPlaceOfBirth()));
              break;
            case countyOfBirth:
              userResource.setCountyOfBirth(map(user.getCountyOfBirth()));
              break;
            case gender:
              userResource.setGender(map(user.getGender()));
              break;
            case companyName:
              userResource.setCompanyName(map(user.getCompanyName()));
              break;
            case registeredOffice:
              userResource.setRegisteredOffice(map(user.getRegisteredOffice()));
              break;
            case ivaCode:
              userResource.setIvaCode(map(user.getIvaCode()));
              break;
            case idCard:
              userResource.setIdCard(map(user.getIdCard()));
              break;
            case mobilePhone:
              userResource.setMobilePhone(map(user.getMobilePhone()));
              break;
            case address:
              userResource.setAddress(map(user.getAddress()));
              break;
            case expirationDate:
              userResource.setExpirationDate(map(user.getExpirationDate()));
              break;
            case digitalAddress:
              userResource.setDigitalAddress(map(user.getDigitalAddress()));
              break;
            case domicileAddress:
              userResource.setDomicileAddress(map(user.getDomicileAddress()));
              break;
            case domicilePlace:
              userResource.setDomicilePlace(map(user.getDomicilePlace()));
              break;
            case domicilePostalCode:
              userResource.setDomicilePostalCode(map(user.getDomicilePostalCode()));
              break;
            case domicileProvince:
              userResource.setDomicileProvince(map(user.getDomicileProvince()));
              break;
            case domicileCountry:
              userResource.setDomicileCountry(map(user.getDomicileCountry()));
              break;
            case qualification:
              userResource.setQualification(map(user.getQualification()));
              break;
            case commonName:
              userResource.setCommonName(map(user.getCommonName()));
              break;
            case surname:
              userResource.setSurname(map(user.getSurname()));
              break;
            case givenName:
              userResource.setGivenName(map(user.getGivenName()));
              break;
            case preferredUsername:
              userResource.setPreferredUsername(map(user.getPreferredUsername()));
              break;
            case title:
              userResource.setTitle(map(user.getTitle()));
              break;
            case userCertificate:
              userResource.setUserCertificate(map(user.getUserCertificate()));
              break;
            case employeeNumber:
              userResource.setEmployeeNumber(map(user.getEmployeeNumber()));
              break;
            case orgUnitName:
              userResource.setOrgUnitName(map(user.getOrgUnitName()));
              break;
            case preferredLanguage:
              userResource.setPreferredLanguage(map(user.getPreferredLanguage()));
              break;
            case country:
              userResource.setCountry(map(user.getCountry()));
              break;
            case stateOrProvince:
              userResource.setStateOrProvince(map(user.getStateOrProvince()));
              break;
            case city:
              userResource.setCity(map(user.getCity()));
              break;
            case postalCode:
              userResource.setPostalCode(map(user.getPostalCode()));
              break;
            case street:
              userResource.setStreet(map(user.getStreet()));
              break;
            case workContacts:
              if (user.getWorkContacts() != null) {
                userResource.setWorkContacts(user.getWorkContacts().entrySet().stream()
                    .map(entry -> Map.entry(entry.getKey(), map(entry.getValue())))
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));
              }
              break;
          }
        }
      }
    }
    return userResource;
  }


  private static WorkContactResource map(
      it.pagopa.pdv.user_registry.connector.model.WorkContactResource workContact) {
    WorkContactResource workContactResource = null;
    if (workContact != null) {
      workContactResource = new WorkContactResource();
      workContactResource.setEmail(map(workContact.getEmail()));
      workContactResource.setMobilePhone(map(workContact.getMobilePhone()));
      workContactResource.setTelephone(map(workContact.getTelephone()));
    }
    return workContactResource;
  }


  private static it.pagopa.pdv.user_registry.connector.model.WorkContactResource map(
      WorkContactResource workContact) {
    it.pagopa.pdv.user_registry.connector.model.WorkContactResource workContactResource = null;
    if (workContact != null) {
      workContactResource = new it.pagopa.pdv.user_registry.connector.model.WorkContactResource();
      workContactResource.setEmail(map(workContact.getEmail()));
      workContactResource.setMobilePhone(map(workContact.getMobilePhone()));
      workContactResource.setTelephone(map(workContact.getTelephone()));
    }
    return workContactResource;
  }


  public static User map(MutableUserFieldsDto mutableUserFieldsDto) {
    User user = null;
    if (mutableUserFieldsDto != null) {
      user = new User();
      user.setName(map(mutableUserFieldsDto.getName()));
      user.setFamilyName(map(mutableUserFieldsDto.getFamilyName()));
      user.setEmail(map(mutableUserFieldsDto.getEmail()));
      user.setBirthDate(map(mutableUserFieldsDto.getBirthDate()));
      user.setSpidCode(map(mutableUserFieldsDto.getSpidCode()));
      user.setPlaceOfBirth(map(mutableUserFieldsDto.getPlaceOfBirth()));
      user.setCountyOfBirth(map(mutableUserFieldsDto.getCountyOfBirth()));
      user.setGender(map(mutableUserFieldsDto.getGender()));
      user.setCompanyName(map(mutableUserFieldsDto.getCompanyName()));
      user.setRegisteredOffice(map(mutableUserFieldsDto.getRegisteredOffice()));
      user.setIvaCode(map(mutableUserFieldsDto.getIvaCode()));
      user.setIdCard(map(mutableUserFieldsDto.getIdCard()));
      user.setMobilePhone(map(mutableUserFieldsDto.getMobilePhone()));
      user.setAddress(map(mutableUserFieldsDto.getAddress()));
      user.setExpirationDate(map(mutableUserFieldsDto.getExpirationDate()));
      user.setDigitalAddress(map(mutableUserFieldsDto.getDigitalAddress()));
      user.setDomicileAddress(map(mutableUserFieldsDto.getDomicileAddress()));
      user.setDomicilePlace(map(mutableUserFieldsDto.getDomicilePlace()));
      user.setDomicilePostalCode(map(mutableUserFieldsDto.getDomicilePostalCode()));
      user.setDomicileProvince(map(mutableUserFieldsDto.getDomicileProvince()));
      user.setDomicileCountry(map(mutableUserFieldsDto.getDomicileCountry()));
      user.setQualification(map(mutableUserFieldsDto.getQualification()));
      user.setCommonName(map(mutableUserFieldsDto.getCommonName()));
      user.setSurname(map(mutableUserFieldsDto.getSurname()));
      user.setGivenName(map(mutableUserFieldsDto.getGivenName()));
      user.setPreferredUsername(map(mutableUserFieldsDto.getPreferredUsername()));
      user.setTitle(map(mutableUserFieldsDto.getTitle()));
      user.setUserCertificate(map(mutableUserFieldsDto.getUserCertificate()));
      user.setEmployeeNumber(map(mutableUserFieldsDto.getEmployeeNumber()));
      user.setOrgUnitName(map(mutableUserFieldsDto.getOrgUnitName()));
      user.setPreferredLanguage(map(mutableUserFieldsDto.getPreferredLanguage()));
      user.setCountry(map(mutableUserFieldsDto.getCountry()));
      user.setStateOrProvince(map(mutableUserFieldsDto.getStateOrProvince()));
      user.setCity(map(mutableUserFieldsDto.getCity()));
      user.setPostalCode(map(mutableUserFieldsDto.getPostalCode()));
      user.setStreet(map(mutableUserFieldsDto.getStreet()));

      if (mutableUserFieldsDto.getWorkContacts() != null) {
        user.setWorkContacts(mutableUserFieldsDto.getWorkContacts().entrySet().stream()
            .map(entry -> Map.entry(entry.getKey(), map(entry.getValue())))
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));
      }
    }
    return user;
  }


  public static User map(SaveUserDto saveUserDto) {
    User user = null;
    if (saveUserDto != null) {
      user = map((MutableUserFieldsDto) saveUserDto);
      user.setFiscalCode(saveUserDto.getFiscalCode());
    }
    return user;
  }


  private static <T> CertifiableFieldResource<T> map(CertifiableField<T> certifiableField) {
    CertifiableFieldResource<T> certifiableFieldResource = null;
    if (certifiableField != null) {
      certifiableFieldResource = new CertifiableFieldResource<>();
      certifiableFieldResource.setValue(certifiableField.getValue());
      certifiableFieldResource.setCertification(
          Certification.valueOf(certifiableField.getCertification()));
    }
    return certifiableFieldResource;
  }


  private static <T> CertifiableField<T> map(CertifiableFieldResource<T> certifiableFieldResource) {
    CertifiableField<T> certifiableField = null;
    if (certifiableFieldResource != null) {
      certifiableField = new CertifiableField<>();
      certifiableField.setValue(certifiableFieldResource.getValue());
      certifiableField.setCertification(certifiableFieldResource.getCertification().toString());
    }
    return certifiableField;
  }

}
