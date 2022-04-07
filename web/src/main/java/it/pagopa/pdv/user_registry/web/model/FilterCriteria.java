package it.pagopa.pdv.user_registry.web.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class FilterCriteria {

    @ApiModelProperty(value = "${swagger.tokenizer.token.model.pii}", required = true)
    private String pii;

}
