package it.pagopa.pdv.user_registry.web.model.mapper;

import it.pagopa.pdv.user_registry.core.model.User;
import it.pagopa.pdv.user_registry.web.model.UserResource;

import java.util.Map;
import java.util.stream.Collectors;

public class UserMapper {

    public static UserResource toResource(User user) {
        UserResource userResource = null;
        if (user != null) {
            userResource = new UserResource();
            userResource.setId(user.getId());
            userResource.setFiscalCode(user.getFiscalCode());
            userResource.setGivenName(user.getGivenName());
            userResource.setFamilyName(user.getFamilyName());
            if (user.getWorkContacts() != null) {
                userResource.setWorkContacts(user.getWorkContacts().entrySet().stream()
                        .map(entry -> Map.entry(entry.getKey(), toResource(entry.getValue())))
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));
            }
        }
        return userResource;
    }


    public static UserResource.WorkContactResource toResource(User.WorkContact workContact) {
        UserResource.WorkContactResource workContactResource = null;
        if (workContact != null) {
            workContactResource = new UserResource.WorkContactResource();
            workContactResource.setEmail(workContact.getEmail());
        }
        return workContactResource;
    }

}
