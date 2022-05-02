package it.pagopa.pdv.user_registry.connector.model;

import java.time.LocalDate;
import java.util.Map;

public class DummySavePersonDto extends SavePersonDto {

    public DummySavePersonDto() {
        setName(new DummyCertifiableField<>(String.class));
        setFamilyName(new DummyCertifiableField<>(String.class));
        setEmail(new DummyCertifiableField<>(String.class));
        setBirthDate(new DummyCertifiableField<>(LocalDate.class));
        setWorkContacts(Map.of("inst-1", new DummyWorkContact(),
                "inst-2", new DummyWorkContact()));
    }


    public static class DummyWorkContact extends WorkContactResource {

        public DummyWorkContact() {
            setEmail(new DummyCertifiableField<>(String.class));
        }
    }

}
