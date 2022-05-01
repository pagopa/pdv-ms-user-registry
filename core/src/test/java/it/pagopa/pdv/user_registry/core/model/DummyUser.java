package it.pagopa.pdv.user_registry.core.model;

import it.pagopa.pdv.user_registry.connector.model.DummyCertifiableField;
import it.pagopa.pdv.user_registry.connector.model.DummyPersonResource;

import java.time.LocalDate;
import java.util.Map;
import java.util.UUID;

public class DummyUser extends User {

    public DummyUser() {
        setId(UUID.randomUUID().toString());
        setFiscalCode("fiscalCode");
        setName(new DummyCertifiableField<>(String.class));
        setFamilyName(new DummyCertifiableField<>(String.class));
        setEmail(new DummyCertifiableField<>(String.class));
        setBirthDate(new DummyCertifiableField<>(LocalDate.class));
        setWorkContacts(Map.of("inst-1", new DummyPersonResource.DummyWorkContact()));
    }

}