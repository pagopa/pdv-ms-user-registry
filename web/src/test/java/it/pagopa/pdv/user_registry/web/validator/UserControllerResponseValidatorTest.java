package it.pagopa.pdv.user_registry.web.validator;

import it.pagopa.pdv.user_registry.web.controller.DummyController;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.validation.ValidationAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

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
        controller.notVoidMethod();
        Mockito.verify(validatorSpy, Mockito.times(1))
                .validateResponse(Mockito.any(), Mockito.any());
        Mockito.verifyNoMoreInteractions(validatorSpy);
    }


    @Test
    void controllersPointcut_returnVoid() {
        controller.voidMethod();
        Mockito.verify(validatorSpy, Mockito.times(1))
                .validateResponse(Mockito.any(), Mockito.any());
        Mockito.verifyNoMoreInteractions(validatorSpy);
    }


    @Test
    void controllersPointcut() {
        validatorSpy.controllersPointcut();
    }

}