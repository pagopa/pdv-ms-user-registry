package it.pagopa.pdv.user_registry.web.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.Map;

@Data
public class MutableUserFieldsDto {

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
