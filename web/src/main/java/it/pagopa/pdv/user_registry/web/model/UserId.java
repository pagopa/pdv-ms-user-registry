package it.pagopa.pdv.user_registry.web.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
public class UserId {

    @ApiModelProperty(value = "${swagger.model.user.id}", required = true)
    @NotNull
    private UUID id;

}
