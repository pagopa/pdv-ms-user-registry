package it.pagopa.pdv.user_registry.core.model;

import it.pagopa.pdv.user_registry.connector.model.CertifiableField;
import it.pagopa.pdv.user_registry.connector.model.WorkContactResource;
import lombok.Data;

import java.time.LocalDate;
import java.util.Map;

@Data
public class User {

    private String id;
    private String fiscalCode;
    private CertifiableField<String> name;
    private CertifiableField<String> familyName;
    private CertifiableField<String> email;
    private CertifiableField<LocalDate> birthDate;
    private Map<String, WorkContactResource> workContacts;

}
