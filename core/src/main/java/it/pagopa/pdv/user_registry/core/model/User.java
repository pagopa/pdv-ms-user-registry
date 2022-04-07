package it.pagopa.pdv.user_registry.core.model;

import it.pagopa.pdv.user_registry.connector.model.WorkContactResource;
import lombok.Data;

import java.util.Map;

@Data
public class User {

    private String id;
    private String fiscalCode;
    private String givenName;
    private String familyName;
    private Map<String, WorkContactResource> workContacts;
    //TODO certification field

}
