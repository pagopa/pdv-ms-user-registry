package it.pagopa.pdv.user_registry.connector.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CertifiableField<T> {

    private String certification;
    private T value;

}
