package it.pagopa.pdv.user_registry.web.model;

import java.time.LocalDate;
import java.util.Map;

public class DummyMutableUserFieldsDto extends MutableUserFieldsDto {

    public DummyMutableUserFieldsDto() {
        setName(new DummyCertifiableFieldResource<>(String.class));
        setFamilyName(new DummyCertifiableFieldResource<>(String.class));
        setEmail(new DummyCertifiableFieldResource<>(String.class));
        setBirthDate(new DummyCertifiableFieldResource<>(LocalDate.class));
        setWorkContacts(Map.of("inst-1", new DummyMutableUserFieldsDto.DummyWorkContact(),
                "inst-2", new DummyMutableUserFieldsDto.DummyWorkContact()));
    }


    public static class DummyWorkContact extends WorkContactResource {

        public DummyWorkContact() {
            setEmail(new DummyCertifiableFieldResource<>(String.class));
            setMobilePhone(new DummyCertifiableFieldResource<>(String.class));
            setTelephone(new DummyCertifiableFieldResource<>(String.class));
        }
    }
}