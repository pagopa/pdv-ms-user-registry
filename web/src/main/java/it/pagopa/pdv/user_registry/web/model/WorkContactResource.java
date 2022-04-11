package it.pagopa.pdv.user_registry.web.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class WorkContactResource {

    @ApiModelProperty(value = "${swagger.model.user.workContact.email}")
    private String email;

}
