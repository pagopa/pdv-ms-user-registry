package it.pagopa.pdv.user_registry.web.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UserSearchDto {

    @ApiModelProperty(value = "${swagger.model.user.fiscalCode}", required = true)
    @NotBlank
    private String fiscalCode;

}
