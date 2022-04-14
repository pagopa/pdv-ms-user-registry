package it.pagopa.pdv.user_registry.web.model.mapper;

import it.pagopa.pdv.user_registry.connector.model.CertifiableField;
import it.pagopa.pdv.user_registry.core.model.User;
import it.pagopa.pdv.user_registry.web.model.*;

import java.util.EnumSet;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class UserMapper {

    public static UserResource map(User user, EnumSet<UserResource.Fields> fields) {
        UserResource userResource = null;
        if (user != null) {
            userResource = new UserResource();
            userResource.setId(UUID.fromString(user.getId()));
            if (fields == null || fields.contains(UserResource.Fields.fiscalCode)) {
                userResource.setFiscalCode(user.getFiscalCode());
            }
            if (fields == null || fields.contains(UserResource.Fields.name)) {
                userResource.setName(map(user.getName()));
            }
            if (fields == null || fields.contains(UserResource.Fields.familyName)) {
                userResource.setFamilyName(map(user.getFamilyName()));
            }
            if (fields == null || fields.contains(UserResource.Fields.email)) {
                userResource.setEmail(map(user.getEmail()));
            }
            if (fields == null || fields.contains(UserResource.Fields.birthDate)) {
                userResource.setBirthDate(map(user.getBirthDate()));
            }
            if (fields == null || (fields.contains(UserResource.Fields.workContacts) && user.getWorkContacts() != null)) {
                userResource.setWorkContacts(user.getWorkContacts().entrySet().stream()
                        .map(entry -> Map.entry(entry.getKey(), map(entry.getValue())))
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));
            }
        }
        return userResource;
    }


    public static WorkContactResource map(it.pagopa.pdv.user_registry.connector.model.WorkContactResource workContact) {
        WorkContactResource workContactResource = null;
        if (workContact != null) {
            workContactResource = new WorkContactResource();
            workContactResource.setEmail(map(workContact.getEmail()));
        }
        return workContactResource;
    }


    public static it.pagopa.pdv.user_registry.connector.model.WorkContactResource map(WorkContactResource workContact) {
        it.pagopa.pdv.user_registry.connector.model.WorkContactResource workContactResource = null;
        if (workContact != null) {
            workContactResource = new it.pagopa.pdv.user_registry.connector.model.WorkContactResource();
            workContactResource.setEmail(map(workContact.getEmail()));
        }
        return workContactResource;
    }


    public static User map(MutableUserFieldsDto mutableUserFieldsDto) {
        User user = null;
        if (mutableUserFieldsDto != null) {
            user = new User();
            user.setName(map(mutableUserFieldsDto.getName()));
            user.setFamilyName(map(mutableUserFieldsDto.getFamilyName()));
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
