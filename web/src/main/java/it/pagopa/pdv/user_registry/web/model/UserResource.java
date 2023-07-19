package it.pagopa.pdv.user_registry.web.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.FieldNameConstants;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Map;
import java.util.UUID;

@Data
@FieldNameConstants(asEnum = true)
public class UserResource {

    @Schema(description = "${swagger.model.user.id}", required = true)
    @NotNull
    @FieldNameConstants.Exclude
    private UUID id;

    @Schema(description = "${swagger.model.user.fiscalCode}")
    private String fiscalCode;

    @Schema(description = "${swagger.model.user.name}")
    @Valid
    private CertifiableFieldResource<String> name;

    @Schema(description = "${swagger.model.user.familyName}")
    @Valid
    private CertifiableFieldResource<String> familyName;

    @Schema(description = "${swagger.model.user.email}")
    @Valid
    private CertifiableFieldResource<String> email;

    @Schema(description = "${swagger.model.user.birthDate}")
    @Valid
    private CertifiableFieldResource<LocalDate> birthDate;

    @Schema(description = "${swagger.model.user.workContacts}")
    @Valid
    private Map<String, WorkContactResource> workContacts;

}
