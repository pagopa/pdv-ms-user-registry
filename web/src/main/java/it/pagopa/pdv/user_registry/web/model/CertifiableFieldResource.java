package it.pagopa.pdv.user_registry.web.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

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
