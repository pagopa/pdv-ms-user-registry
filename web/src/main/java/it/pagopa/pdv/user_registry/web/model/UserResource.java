package it.pagopa.pdv.user_registry.web.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.FieldNameConstants;

import java.time.LocalDate;
import java.util.Map;
import java.util.UUID;

@Data
@FieldNameConstants(asEnum = true)
public class UserResource {

    @Schema(description = "${swagger.model.user.id}", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull
    @FieldNameConstants.Exclude
    private UUID id;

    @Schema(description = "${swagger.model.user.fiscalCode}")
    private String fiscalCode;

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
