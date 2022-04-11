package it.pagopa.pdv.user_registry.web.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class UserSearchDto {

    @ApiModelProperty(value = "${swagger.model.user.fiscalCode}", required = true)
    @NotEmpty
    private String fiscalCode;

}
