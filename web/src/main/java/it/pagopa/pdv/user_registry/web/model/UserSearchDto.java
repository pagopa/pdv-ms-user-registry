package it.pagopa.pdv.user_registry.web.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.v3.oas.annotations.media.Schema;
import it.pagopa.pdv.user_registry.web.converter.UpperCaseConverter;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UserSearchDto {

    @Schema(description = "${swagger.model.user.fiscalCode}", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonDeserialize(converter = UpperCaseConverter.class)
    @NotBlank
    private String fiscalCode;

}
