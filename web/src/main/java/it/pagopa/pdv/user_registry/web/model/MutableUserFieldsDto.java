package it.pagopa.pdv.user_registry.web.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Map;

@Data
public class MutableUserFieldsDto {

    @ApiModelProperty(value = "${swagger.model.user.givenName}")
    private String givenName;
    @ApiModelProperty(value = "${swagger.model.user.familyName}")
    private String familyName;
    @ApiModelProperty(value = "${swagger.model.user.workContacts}")
    private Map<String, WorkContactResource> workContacts;

}
