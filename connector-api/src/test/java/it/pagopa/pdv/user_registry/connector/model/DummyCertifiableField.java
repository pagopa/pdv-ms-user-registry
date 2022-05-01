package it.pagopa.pdv.user_registry.connector.model;

import lombok.SneakyThrows;

import java.time.LocalDate;

public class DummyCertifiableField<T> extends CertifiableField<T> {

    @SneakyThrows
    public DummyCertifiableField(Class<T> clazz) {
        setCertification("NONE");
        if (String.class.isAssignableFrom(clazz)) {
            setValue((T) "value");
        } else if (LocalDate.class.isAssignableFrom(clazz)) {
            setValue((T) LocalDate.now());
        } else {
            throw new IllegalArgumentException();
        }
    }

}