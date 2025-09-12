package it.pagopa.pdv.user_registry.core.model.mapper;

import it.pagopa.pdv.user_registry.connector.model.PersonResource;
import it.pagopa.pdv.user_registry.connector.model.SavePersonDto;
import it.pagopa.pdv.user_registry.connector.model.WorkContactResource;
import it.pagopa.pdv.user_registry.core.model.User;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserMapper {

    public static SavePersonDto map(User user) {
        SavePersonDto savePersonDto = null;
        if (user != null) {
            savePersonDto = new SavePersonDto();
            savePersonDto.setName(user.getName());
            savePersonDto.setFamilyName(user.getFamilyName());
            savePersonDto.setEmail(user.getEmail());
            savePersonDto.setBirthDate(user.getBirthDate());
            savePersonDto.setSpidCode(user.getSpidCode());
            savePersonDto.setPlaceOfBirth(user.getPlaceOfBirth());
            savePersonDto.setCountyOfBirth(user.getCountyOfBirth());
            savePersonDto.setGender(user.getGender());
            savePersonDto.setCompanyName(user.getCompanyName());
            savePersonDto.setRegisteredOffice(user.getRegisteredOffice());
            savePersonDto.setIvaCode(user.getIvaCode());
            savePersonDto.setIdCard(user.getIdCard());
            savePersonDto.setMobilePhone(user.getMobilePhone());
            savePersonDto.setAddress(user.getAddress());
            savePersonDto.setExpirationDate(user.getExpirationDate());
            savePersonDto.setDigitalAddress(user.getDigitalAddress());
            savePersonDto.setDomicileAddress(user.getDomicileAddress());
            savePersonDto.setDomicilePlace(user.getDomicilePlace());
            savePersonDto.setDomicilePostalCode(user.getDomicilePostalCode());
            savePersonDto.setDomicileProvince(user.getDomicileProvince());
            savePersonDto.setDomicileCountry(user.getDomicileCountry());
            savePersonDto.setQualification(user.getQualification());
            savePersonDto.setCommonName(user.getCommonName());
            savePersonDto.setSurname(user.getSurname());
            savePersonDto.setGivenName(user.getGivenName());
            savePersonDto.setPreferredUsername(user.getPreferredUsername());
            savePersonDto.setTitle(user.getTitle());
            savePersonDto.setUserCertificate(user.getUserCertificate());
            savePersonDto.setEmployeeNumber(user.getEmployeeNumber());
            savePersonDto.setOrgUnitName(user.getOrgUnitName());
            savePersonDto.setPreferredLanguage(user.getPreferredLanguage());
            savePersonDto.setCountry(user.getCountry());
            savePersonDto.setStateOrProvince(user.getStateOrProvince());
            savePersonDto.setCity(user.getCity());
            savePersonDto.setPostalCode(user.getPostalCode());
            savePersonDto.setStreet(user.getStreet());
            if (user.getWorkContacts() != null) {
                savePersonDto.setWorkContacts(user.getWorkContacts().entrySet().stream()
                        .map(entry -> Map.entry(entry.getKey(), map(entry.getValue())))
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));
            }
        }
        return savePersonDto;
    }


    public static User assembles(String id, PersonResource personResource) {
        return assembles(id, personResource, null);
    }


    public static User assembles(String id, PersonResource personResource, String fiscalCode) {
        User user = null;
        if (personResource != null) {
            user = new User();
            user.setId(id);
            user.setFiscalCode(fiscalCode);
            user.setName(personResource.getName());
            user.setFamilyName(personResource.getFamilyName());
            user.setEmail(personResource.getEmail());
            user.setBirthDate(personResource.getBirthDate());
            user.setSpidCode(personResource.getSpidCode());
            user.setPlaceOfBirth(personResource.getPlaceOfBirth());
            user.setCountyOfBirth(personResource.getCountyOfBirth());
            user.setGender(personResource.getGender());
            user.setCompanyName(personResource.getCompanyName());
            user.setRegisteredOffice(personResource.getRegisteredOffice());
            user.setIvaCode(personResource.getIvaCode());
            user.setIdCard(personResource.getIdCard());
            user.setMobilePhone(personResource.getMobilePhone());
            user.setAddress(personResource.getAddress());
            user.setExpirationDate(personResource.getExpirationDate());
            user.setDigitalAddress(personResource.getDigitalAddress());
            user.setDomicileAddress(personResource.getDomicileAddress());
            user.setDomicilePlace(personResource.getDomicilePlace());
            user.setDomicilePostalCode(personResource.getDomicilePostalCode());
            user.setDomicileProvince(personResource.getDomicileProvince());
            user.setDomicileCountry(personResource.getDomicileCountry());
            user.setQualification(personResource.getQualification());
            user.setCommonName(personResource.getCommonName());
            user.setSurname(personResource.getSurname());
            user.setGivenName(personResource.getGivenName());
            user.setPreferredUsername(personResource.getPreferredUsername());
            user.setTitle(personResource.getTitle());
            user.setUserCertificate(personResource.getUserCertificate());
            user.setEmployeeNumber(personResource.getEmployeeNumber());
            user.setOrgUnitName(personResource.getOrgUnitName());
            user.setPreferredLanguage(personResource.getPreferredLanguage());
            user.setCountry(personResource.getCountry());
            user.setStateOrProvince(personResource.getStateOrProvince());
            user.setCity(personResource.getCity());
            user.setPostalCode(personResource.getPostalCode());
            user.setStreet(personResource.getStreet());
            if (personResource.getWorkContacts() != null) {
                user.setWorkContacts(personResource.getWorkContacts().entrySet().stream()
                        .map(entry -> Map.entry(entry.getKey(), map(entry.getValue())))
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));
            }
        }
        return user;
    }


    private static WorkContactResource map(WorkContactResource workContactResource) {
        WorkContactResource workContact = null;
        if (workContactResource != null) {
            workContact = new WorkContactResource();
            workContact.setEmail(workContactResource.getEmail());
            workContact.setMobilePhone(workContactResource.getMobilePhone());
            workContact.setTelephone(workContactResource.getTelephone());
        }
        return workContact;
    }

}
