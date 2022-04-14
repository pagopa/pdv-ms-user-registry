package it.pagopa.pdv.user_registry.web.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class CertifiableFieldResource<T> {

    @ApiModelProperty(value = "${swagger.model.certifiableField.certification}", required = true)
    @NotBlank
    private Certification certification;

    @ApiModelProperty(value = "${swagger.model.certifiableField.value}", required = true)
    @NotNull
    private T value;

}
