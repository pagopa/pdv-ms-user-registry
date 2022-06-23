package it.pagopa.pdv.user_registry.web.converter;


import com.fasterxml.jackson.databind.util.StdConverter;

public class UpperCaseConverter extends StdConverter<String, String> {

    @Override
    public String convert(String s) {
        return s != null ? s.toUpperCase() : null;
    }

}
