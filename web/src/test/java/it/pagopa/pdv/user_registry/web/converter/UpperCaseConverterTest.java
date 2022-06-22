package it.pagopa.pdv.user_registry.web.converter;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class UpperCaseConverterTest {

    private UpperCaseConverter upperCaseConverter = new UpperCaseConverter();


    @Test
    void convert_nullInput() {
        // given
        final String input = null;
        // when
        final String result = upperCaseConverter.convert(input);
        // then
        assertNull(result);
    }


    @Test
    void convert_notNullInput() {
        // given
        final String input = "TeSt";
        // when
        final String result = upperCaseConverter.convert(input);
        // then
        assertEquals("TEST", result);
    }

}