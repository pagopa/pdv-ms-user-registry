package it.pagopa.pdv.user_registry.web.model;

import io.swagger.annotations.ApiModelProperty;
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

    @ApiModelProperty(value = "${swagger.model.user.id}", required = true)
    @NotNull
    @FieldNameConstants.Exclude
    private UUID id;

    @ApiModelProperty(value = "${swagger.model.user.fiscalCode}")
    private String fiscalCode;

    @ApiModelProperty(value = "${swagger.model.user.name}")
    @Valid
    private CertifiableFieldResource<String> name;

    @ApiModelProperty(value = "${swagger.model.user.familyName}")
    @Valid
    private CertifiableFieldResource<String> familyName;

    @ApiModelProperty(value = "${swagger.model.user.email}")
    @Valid
    private CertifiableFieldResource<String> email;

    @ApiModelProperty(value = "${swagger.model.user.birthDate}")
    @Valid
    private CertifiableFieldResource<LocalDate> birthDate;

    @ApiModelProperty(value = "${swagger.model.user.workContacts}")
    @Valid
    private Map<String, WorkContactResource> workContacts;

}
