package it.pagopa.pdv.user_registry.web.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.UUID;

@Data
public class UserInternalId {

    @JsonProperty(required = true)
    private UUID internalId;

}
