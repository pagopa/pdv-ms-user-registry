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
        setSpidCode(mutableUserFieldsDto.getSpidCode());
        setPlaceOfBirth(mutableUserFieldsDto.getPlaceOfBirth());
        setCountyOfBirth(mutableUserFieldsDto.getCountyOfBirth());
        setGender(mutableUserFieldsDto.getGender());
        setCompanyName(mutableUserFieldsDto.getCompanyName());
        setRegisteredOffice(mutableUserFieldsDto.getRegisteredOffice());
        setIvaCode(mutableUserFieldsDto.getIvaCode());
        setIdCard(mutableUserFieldsDto.getIdCard());
        setMobilePhone(mutableUserFieldsDto.getMobilePhone());
        setAddress(mutableUserFieldsDto.getAddress());
        setExpirationDate(mutableUserFieldsDto.getExpirationDate());
        setDigitalAddress(mutableUserFieldsDto.getDigitalAddress());
        setDomicileAddress(mutableUserFieldsDto.getDomicileAddress());
        setDomicilePlace(mutableUserFieldsDto.getDomicilePlace());
        setDomicilePostalCode(mutableUserFieldsDto.getDomicilePostalCode());
        setDomicileProvince(mutableUserFieldsDto.getDomicileProvince());
        setDomicileCountry(mutableUserFieldsDto.getDomicileCountry());
        setQualification(mutableUserFieldsDto.getQualification());
        setCommonName(mutableUserFieldsDto.getCommonName());
        setSurname(mutableUserFieldsDto.getSurname());
        setGivenName(mutableUserFieldsDto.getGivenName());
        setPreferredUsername(mutableUserFieldsDto.getPreferredUsername());
        setTitle(mutableUserFieldsDto.getTitle());
        setUserCertificate(mutableUserFieldsDto.getUserCertificate());
        setEmployeeNumber(mutableUserFieldsDto.getEmployeeNumber());
        setOrgUnitName(mutableUserFieldsDto.getOrgUnitName());
        setPreferredLanguage(mutableUserFieldsDto.getPreferredLanguage());
        setCountry(mutableUserFieldsDto.getCountry());
        setStateOrProvince(mutableUserFieldsDto.getStateOrProvince());
        setCity(mutableUserFieldsDto.getCity());
        setPostalCode(mutableUserFieldsDto.getPostalCode());
        setStreet(mutableUserFieldsDto.getStreet());
    }

}