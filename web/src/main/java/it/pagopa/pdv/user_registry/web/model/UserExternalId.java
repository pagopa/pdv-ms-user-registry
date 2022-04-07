package it.pagopa.pdv.user_registry.web.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UserExternalId {

    @ApiModelProperty(value = "${swagger.model.user.fiscalCode}", required = true)
    private String fiscalCode;

}
