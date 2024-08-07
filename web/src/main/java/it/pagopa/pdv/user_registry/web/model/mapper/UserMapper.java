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


    private static WorkContactResource map(it.pagopa.pdv.user_registry.connector.model.WorkContactResource workContact) {
        WorkContactResource workContactResource = null;
        if (workContact != null) {
            workContactResource = new WorkContactResource();
            workContactResource.setEmail(map(workContact.getEmail()));
            workContactResource.setMobilePhone(map(workContact.getMobilePhone()));
            workContactResource.setTelephone(map(workContact.getTelephone()));
        }
        return workContactResource;
    }


    private static it.pagopa.pdv.user_registry.connector.model.WorkContactResource map(WorkContactResource workContact) {
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
            certifiableFieldResource.setCertification(Certification.valueOf(certifiableField.getCertification()));
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
