package it.pagopa.pdv.user_registry.connector.model;

import java.time.LocalDate;
import java.util.Map;
import java.util.UUID;

public class DummyPersonResource extends PersonResource {

    public DummyPersonResource() {
        setId(UUID.randomUUID().toString());
        setName(new DummyCertifiableField<>(String.class));
        setFamilyName(new DummyCertifiableField<>(String.class));
        setEmail(new DummyCertifiableField<>(String.class));
        setBirthDate(new DummyCertifiableField<>(LocalDate.class));
        setWorkContacts(Map.of("inst-1", new DummyWorkContact()));
    }


    public static class DummyWorkContact extends WorkContactResource {

        public DummyWorkContact() {
            setEmail(new DummyCertifiableField<>(String.class));
        }
    }

}
