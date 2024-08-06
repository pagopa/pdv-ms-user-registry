package it.pagopa.pdv.user_registry.web.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import lombok.Data;

@Data
public class WorkContactResource {

    @Schema(ref = "EmailCertifiableSchema")
    @Valid
    private CertifiableFieldResource<String> email;
    @Schema(ref = "MobilePhoneCertifiableSchema")
    @Valid
    private CertifiableFieldResource<String> mobilePhone;
    @Schema(ref = "TelephoneCertifiableSchema")
    @Valid
    private CertifiableFieldResource<String> telephone;

}
