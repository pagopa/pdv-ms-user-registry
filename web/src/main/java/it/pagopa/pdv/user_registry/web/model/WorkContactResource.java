package it.pagopa.pdv.user_registry.web.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;

@Data
public class WorkContactResource {

    @ApiModelProperty(value = "${swagger.model.user.workContact.email}")
    @Valid
    private CertifiableFieldResource<String> email;

}
