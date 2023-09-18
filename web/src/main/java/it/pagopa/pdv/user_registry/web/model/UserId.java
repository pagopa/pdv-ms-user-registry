package it.pagopa.pdv.user_registry.web.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
public class UserId {

    @Schema(description = "${swagger.model.user.id}", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull
    private UUID id;

}
