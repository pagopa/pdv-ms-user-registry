package it.pagopa.pdv.user_registry.web.handler;

import feign.FeignException;
import it.pagopa.pdv.user_registry.web.model.Problem;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import javax.servlet.ServletException;
import javax.validation.ValidationException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class RestExceptionsHandlerTest {

    private static final String DETAIL_MESSAGE = "detail message";

    private final RestExceptionsHandler handler;


    public RestExceptionsHandlerTest() {
        this.handler = new RestExceptionsHandler();
    }


    @Test
    void handleThrowable() {
        // given
        Throwable exceptionMock = Mockito.mock(Throwable.class);
        Mockito.when(exceptionMock.getMessage())
                .thenReturn(DETAIL_MESSAGE);
        // when
        Problem resource = handler.handleThrowable(exceptionMock);
        // then
        assertNotNull(resource);
        assertEquals(DETAIL_MESSAGE, resource.getMessage());
    }


    @Test
    void handleHttpMediaTypeNotAcceptableException() {
        // given
        HttpMediaTypeNotAcceptableException exceptionMock = Mockito.mock(HttpMediaTypeNotAcceptableException.class);
        Mockito.when(exceptionMock.getMessage())
                .thenReturn(DETAIL_MESSAGE);
        // when
        Problem resource = handler.handleHttpMediaTypeNotAcceptableException(exceptionMock);
        // then
        assertNotNull(resource);
        assertEquals(DETAIL_MESSAGE, resource.getMessage());
    }


    @Test
    void handleHttpRequestMethodNotSupportedException() {
        // given
        HttpRequestMethodNotSupportedException exceptionMock = Mockito.mock(HttpRequestMethodNotSupportedException.class);
        Mockito.when(exceptionMock.getMessage())
                .thenReturn(DETAIL_MESSAGE);
        // when
        Problem resource = handler.handleHttpRequestMethodNotSupportedException(exceptionMock);
        // then
        assertNotNull(resource);
        assertEquals(DETAIL_MESSAGE, resource.getMessage());
    }


    @ParameterizedTest
    @ValueSource(classes = {
            ValidationException.class,
            BindException.class,
            ServletException.class,
            MethodArgumentTypeMismatchException.class,
            MaxUploadSizeExceededException.class,
            HttpMessageNotReadableException.class
    })
    void handleBadRequestException(Class<?> clazz) {
        // given
        Exception exceptionMock = (Exception) Mockito.mock(clazz);
        Mockito.when(exceptionMock.getMessage())
                .thenReturn(DETAIL_MESSAGE);
        // when
        Problem resource = handler.handleBadRequestException(exceptionMock);
        // then
        assertNotNull(resource);
        assertEquals(DETAIL_MESSAGE, resource.getMessage());
    }


    @Test
    void handleMethodArgumentNotValidException() {
        // given
        MethodArgumentNotValidException exceptionMock = Mockito.mock(MethodArgumentNotValidException.class);
        Mockito.when(exceptionMock.getMessage())
                .thenReturn("{}");
        // when
        Problem resource = handler.handleMethodArgumentNotValidException(exceptionMock);
        // then
        assertNotNull(resource);
        assertEquals("{}", resource.getMessage());
    }


    @ParameterizedTest
    @EnumSource(value = HttpStatus.class, names = {"BAD_REQUEST", "INTERNAL_SERVER_ERROR", "OK"})
    void handleFeignException(HttpStatus httpStatus) {
        // given
        String content = "content";
        FeignException exceptionMock = Mockito.mock(FeignException.class);
        Mockito.when(exceptionMock.contentUTF8())
                .thenReturn(content);
        Mockito.when(exceptionMock.status())
                .thenReturn(httpStatus.value());
        // when
        ResponseEntity<String> responseEntity = handler.handleFeignException(exceptionMock);
        // then
        assertNotNull(responseEntity);
        assertEquals(content, responseEntity.getBody());
        assertEquals(httpStatus.is2xxSuccessful() ? HttpStatus.INTERNAL_SERVER_ERROR : httpStatus, responseEntity.getStatusCode());
    }

}