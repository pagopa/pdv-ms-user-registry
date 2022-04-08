package it.pagopa.pdv.user_registry.web.model;

import lombok.Data;

@Data
public class SaveUserDto extends MutableUserFieldsDto {

    private String fiscalCode;

}
