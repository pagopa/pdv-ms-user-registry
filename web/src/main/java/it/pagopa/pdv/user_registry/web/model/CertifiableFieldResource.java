package it.pagopa.pdv.user_registry.web.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class CertifiableFieldResource<T> {

    @Schema(description = "${swagger.model.certifiableField.certification}", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull
    private Certification certification;

    @Schema(description = "${swagger.model.certifiableField.value}", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull
    private T value;

}
