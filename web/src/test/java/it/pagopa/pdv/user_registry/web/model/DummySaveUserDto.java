package it.pagopa.pdv.user_registry.web.model;

public class DummySaveUserDto extends SaveUserDto {

    public DummySaveUserDto() {
        this(new DummyMutableUserFieldsDto());
        setFiscalCode("fiscalCode");
    }

    public DummySaveUserDto(MutableUserFieldsDto mutableUserFieldsDto) {
        setName(mutableUserFieldsDto.getName());
        setFamilyName(mutableUserFieldsDto.getFamilyName());
        setEmail(mutableUserFieldsDto.getEmail());
        setBirthDate(mutableUserFieldsDto.getBirthDate());
        setWorkContacts(mutableUserFieldsDto.getWorkContacts());
    }

}