package it.pagopa.pdv.user_registry.web.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.annotations.ApiModelProperty;
import it.pagopa.pdv.user_registry.web.converter.UpperCaseConverter;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UserSearchDto {

    @ApiModelProperty(value = "${swagger.model.user.fiscalCode}", required = true)
    @JsonDeserialize(converter = UpperCaseConverter.class)
    @NotBlank
    private String fiscalCode;

}
