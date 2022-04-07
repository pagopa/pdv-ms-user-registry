package it.pagopa.pdv.user_registry.connector.model;

import lombok.Data;

import java.util.Map;

@Data
public class SavePersonDto {

    private String givenName;
    private String familyName;
    private Map<String, WorkContactResource> workContacts;

}
