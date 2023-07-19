package it.pagopa.pdv.user_registry.web.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.Valid;

@Data
public class WorkContactResource {

    @Schema(description = "${swagger.model.user.workContact.email}")
    @Valid
    private CertifiableFieldResource<String> email;

}
