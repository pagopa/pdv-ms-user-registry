package it.pagopa.pdv.user_registry.core.model;

import it.pagopa.pdv.user_registry.connector.model.PersonResource;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class User {

    public User(PersonResource person) {
        id = person.getId();
        givenName = person.getGivenName();
        familyName = person.getFamilyName();
        if (person.getWorkContacts() != null) {
            workContacts = person.getWorkContacts().entrySet().stream()
                    .map(entry -> Map.entry(entry.getKey(), new WorkContact(entry.getValue())))
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        }
    }

    private String id;
    private String fiscalCode;
    private String givenName;
    private String familyName;
    private Map<String, WorkContact> workContacts;
    //TODO certification field


    @Data
    @NoArgsConstructor
    public static class WorkContact {

        public WorkContact(PersonResource.WorkContactResource workContact) {
            email = workContact.getEmail();
        }

        private String email;
    }

}
