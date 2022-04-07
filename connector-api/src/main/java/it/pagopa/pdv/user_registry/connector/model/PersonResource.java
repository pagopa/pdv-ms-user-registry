package it.pagopa.pdv.user_registry.connector.model;

import lombok.Data;

import java.util.Map;

@Data
public class PersonResource {

    private String id;
    private String givenName;
    private String familyName;
    private Map<String, WorkContactResource> workContacts;
    //TODO certification field


    @Data
    public static class WorkContactResource {
        private String email;
    }

}
