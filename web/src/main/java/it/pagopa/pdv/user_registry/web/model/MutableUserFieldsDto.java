package it.pagopa.pdv.user_registry.web.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import lombok.Data;

import java.time.LocalDate;
import java.util.Map;

@Data
public class MutableUserFieldsDto {

    @Schema(ref = "NameCertifiableSchema")
    @Valid
    private CertifiableFieldResource<String> name;

    @Schema(ref = "FamilyNameCertifiableSchema")
    @Valid
    private CertifiableFieldResource<String> familyName;

    @Schema(ref = "EmailCertifiableSchema")
    @Valid
    private CertifiableFieldResource<String> email;

    @Schema(ref = "BirthDateCertifiableSchema")
    @Valid
    private CertifiableFieldResource<LocalDate> birthDate;

    @Schema(description = "${swagger.model.user.workContacts}")
    @Valid
    private Map<String, WorkContactResource> workContacts;

}
