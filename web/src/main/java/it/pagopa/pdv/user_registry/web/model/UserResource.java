package it.pagopa.pdv.user_registry.web.model;

import lombok.Data;

import java.util.Map;

@Data
public class UserResource {

    private String id;
    private String fiscalCode;
    private String givenName;
    private String familyName;
    private Map<String, WorkContactResource> workContacts;


    @Data
    public static class WorkContactResource {
        private String email;
    }

}
