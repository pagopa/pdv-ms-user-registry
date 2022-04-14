package it.pagopa.pdv.user_registry.web.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDate;
import java.util.Map;

@Data
public class MutableUserFieldsDto {

    @ApiModelProperty(value = "${swagger.model.user.name}")
    private CertifiableFieldResource<String> name;
    @ApiModelProperty(value = "${swagger.model.user.familyName}")
    private CertifiableFieldResource<String> familyName;
    @ApiModelProperty(value = "${swagger.model.user.email}")
    private CertifiableFieldResource<String> email;
    @ApiModelProperty(value = "${swagger.model.user.birthDate}")
    private CertifiableFieldResource<LocalDate> birthDate;
    @ApiModelProperty(value = "${swagger.model.user.workContacts}")
    private Map<String, WorkContactResource> workContacts;

}
