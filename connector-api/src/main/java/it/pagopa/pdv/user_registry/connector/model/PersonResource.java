package it.pagopa.pdv.user_registry.connector.model;

import lombok.Data;

import java.time.LocalDate;
import java.util.Map;

@Data
public class PersonResource {

    private String id;
    private CertifiableField<String> name;
    private CertifiableField<String> familyName;
    private CertifiableField<String> email;
    private CertifiableField<LocalDate> birthDate;
    private Map<String, WorkContactResource> workContacts;

}
