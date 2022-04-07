package it.pagopa.pdv.user_registry.web.model;

import lombok.Data;

import java.util.Map;

@Data
public class MutableUserFieldsDto {

    private String givenName;
    private String familyName;
    private Map<String, UserResource.WorkContactResource> workContacts;


    @Data
    public static class WorkContactResource {
        private String email;
    }

}
