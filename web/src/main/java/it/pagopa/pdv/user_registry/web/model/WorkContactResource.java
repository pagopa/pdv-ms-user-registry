package it.pagopa.pdv.user_registry.web.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.Valid;

@Data
public class WorkContactResource {

    @Schema(ref = "EmailCertifiableSchema")
    @Valid
    private CertifiableFieldResource<String> email;

}
