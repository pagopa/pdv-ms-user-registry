package it.pagopa.pdv.user_registry.connector.model;

import lombok.Data;

@Data
public class WorkContactResource {

    private CertifiableField<String> email;
    private CertifiableField<String> mobilePhone;
    private CertifiableField<String> telephone;

}
