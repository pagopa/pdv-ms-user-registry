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
