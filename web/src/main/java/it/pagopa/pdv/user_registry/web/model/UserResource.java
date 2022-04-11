package it.pagopa.pdv.user_registry.web.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Map;
import java.util.UUID;

@Data
public class UserResource {

    @ApiModelProperty(value = "${swagger.model.user.id}", required = true)
    @
    @NotNull
    private UUID id;
    @ApiModelProperty(value = "${swagger.model.user.fiscalCode}")
    private String fiscalCode;
    @ApiModelProperty(value = "${swagger.model.user.givenName}")
    private String givenName;
    @ApiModelProperty(value = "${swagger.model.user.familyName}")
    private String familyName;
    @ApiModelProperty(value = "${swagger.model.user.workContacts}")
    private Map<String, WorkContactResource> workContacts;

}
