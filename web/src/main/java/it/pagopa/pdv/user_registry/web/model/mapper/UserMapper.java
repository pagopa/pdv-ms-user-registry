package it.pagopa.pdv.user_registry.web.model.mapper;

import it.pagopa.pdv.user_registry.core.model.User;
import it.pagopa.pdv.user_registry.web.model.MutableUserFieldsDto;
import it.pagopa.pdv.user_registry.web.model.SaveUserDto;
import it.pagopa.pdv.user_registry.web.model.UserResource;
import it.pagopa.pdv.user_registry.web.model.WorkContactResource;

import java.util.Map;
import java.util.stream.Collectors;

public class UserMapper {

    public static UserResource map(User user) {
        UserResource userResource = null;
        if (user != null) {
            userResource = new UserResource();
            userResource.setId(user.getId());
            userResource.setFiscalCode(user.getFiscalCode());
            userResource.setGivenName(user.getGivenName());
            userResource.setFamilyName(user.getFamilyName());
            if (user.getWorkContacts() != null) {
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
            workContactResource.setEmail(workContact.getEmail());
        }
        return workContactResource;
    }


    public static it.pagopa.pdv.user_registry.connector.model.WorkContactResource map(WorkContactResource workContact) {
        it.pagopa.pdv.user_registry.connector.model.WorkContactResource workContactResource = null;
        if (workContact != null) {
            workContactResource = new it.pagopa.pdv.user_registry.connector.model.WorkContactResource();
            workContactResource.setEmail(workContact.getEmail());
        }
        return workContactResource;
    }


    public static User map(MutableUserFieldsDto mutableUserFieldsDto) {
        User user = null;
        if (mutableUserFieldsDto != null) {
            user = new User();
            user.setGivenName(mutableUserFieldsDto.getGivenName());
            user.setFamilyName(mutableUserFieldsDto.getFamilyName());
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

}
