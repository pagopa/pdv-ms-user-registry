package it.pagopa.pdv.user_registry.core.model.mapper;

import it.pagopa.pdv.user_registry.connector.model.PersonResource;
import it.pagopa.pdv.user_registry.connector.model.SavePersonDto;
import it.pagopa.pdv.user_registry.connector.model.WorkContactResource;
import it.pagopa.pdv.user_registry.core.model.User;

import java.util.Map;
import java.util.stream.Collectors;

public class UserMapper {

    public static SavePersonDto map(User user) {
        SavePersonDto savePersonDto = null;
        if (user != null) {
            savePersonDto = new SavePersonDto();
            savePersonDto.setGivenName(user.getGivenName());
            savePersonDto.setFamilyName(user.getFamilyName());
            if (user.getWorkContacts() != null) {
                savePersonDto.setWorkContacts(user.getWorkContacts().entrySet().stream()
                        .map(entry -> Map.entry(entry.getKey(), map(entry.getValue())))
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));
            }
        }
        return savePersonDto;
    }


    public static User map(PersonResource personResource) {
        User user = null;
        if (personResource != null) {
            user = new User();
            user.setId(personResource.getId());
            user.setGivenName(personResource.getGivenName());
            user.setFamilyName(personResource.getFamilyName());
            if (personResource.getWorkContacts() != null) {
                user.setWorkContacts(personResource.getWorkContacts().entrySet().stream()
                        .map(entry -> Map.entry(entry.getKey(), map(entry.getValue())))
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));
            }
        }
        return user;
    }


    public static WorkContactResource map(WorkContactResource workContactResource) {
        WorkContactResource workContact = null;
        if (workContactResource != null) {
            workContact = new WorkContactResource();
            workContact.setEmail(workContactResource.getEmail());
        }
        return workContact;
    }

}
