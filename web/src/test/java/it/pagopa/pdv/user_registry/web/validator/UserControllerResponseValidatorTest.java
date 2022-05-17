package it.pagopa.pdv.user_registry.web.validator;

import it.pagopa.pdv.user_registry.web.controller.DummyController;
import it.pagopa.pdv.user_registry.web.exception.ResponseValidationException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.validation.ValidationAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@SpringBootTest(classes = {
        ValidationAutoConfiguration.class,
        DummyController.class,
        UserControllerResponseValidator.class})
@EnableAspectJAutoProxy
class UserControllerResponseValidatorTest {

    @Autowired
    private DummyController controller;

    @SpyBean
    private UserControllerResponseValidator validatorSpy;


    @Test
    void controllersPointcut_returnNotVoid() {
        assertDoesNotThrow(() -> controller.notVoidMethodValidResult());
        verify(validatorSpy, Mockito.times(1))
                .validateResponse(any(), any());
        verifyNoMoreInteractions(validatorSpy);
    }


    @Test
    void controllersPointcut_returnNotVoidButInvalid() {
        assertThrows(ResponseValidationException.class, () -> controller.notVoidMethodInvalidResult());
        verify(validatorSpy, Mockito.times(1))
                .validateResponse(any(), any());
        verifyNoMoreInteractions(validatorSpy);
    }


    @Test
    void controllersPointcut_returnVoid() {
        assertDoesNotThrow(() -> controller.voidMethod());
        verify(validatorSpy, Mockito.times(1))
                .validateResponse(any(), any());
        verifyNoMoreInteractions(validatorSpy);
    }


    @Test
    void controllersPointcut() {
        assertDoesNotThrow(() -> validatorSpy.controllersPointcut());
    }

}