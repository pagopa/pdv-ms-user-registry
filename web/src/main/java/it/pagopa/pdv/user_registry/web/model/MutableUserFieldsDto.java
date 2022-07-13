package it.pagopa.pdv.user_registry.web.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.Map;

@Data
public class MutableUserFieldsDto {

    @ApiModelProperty(value = "${swagger.model.user.name}")
    @Valid
    private CertifiableFieldResource<String> name;

    @ApiModelProperty(value = "${swagger.model.user.familyName}")
    @Valid
    private CertifiableFieldResource<String> familyName;

    @ApiModelProperty(value = "${swagger.model.user.email}")
    @Valid
    private CertifiableFieldResource<String> email;

    @ApiModelProperty(value = "${swagger.model.user.birthDate}")
    @Valid
    private CertifiableFieldResource<LocalDate> birthDate;

    @ApiModelProperty(value = "${swagger.model.user.workContacts}")
    @Valid
    private Map<String, WorkContactResource> workContacts;

}
