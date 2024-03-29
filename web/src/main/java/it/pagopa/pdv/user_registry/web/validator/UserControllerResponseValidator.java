package it.pagopa.pdv.user_registry.web.validator;

import it.pagopa.pdv.user_registry.web.exception.ResponseValidationException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.*;

@Slf4j
@Aspect
@Component
public class UserControllerResponseValidator {

    private final Validator validator;


    public UserControllerResponseValidator(Validator validator) {
        log.trace("Initializing {}", UserControllerResponseValidator.class.getSimpleName());
        Assert.notNull(validator, "Validator is required");
        this.validator = validator;
    }


    @AfterReturning(pointcut = "controllersPointcut()", returning = "result")
    public void validateResponse(JoinPoint joinPoint, Object result) {
        log.trace("[validateResponse] start");
        log.debug("[validateResponse] inputs: result = {}", result);
        if (result != null) {
            if (Collection.class.isAssignableFrom(result.getClass())) {
                ((Collection<?>) result).forEach(this::validate);
            } else {
                validate(result);
            }
        }
        log.trace("[validateResponse] end");
    }

    private void validate(Object result) {
        Set<ConstraintViolation<Object>> validationResults = validator.validate(result);
        if (!validationResults.isEmpty()) {
            Map<String, List<String>> errorMessage = new HashMap<>();
            validationResults.forEach(error -> {
                String fieldName = error.getPropertyPath().toString();
                errorMessage.computeIfAbsent(fieldName, s -> new ArrayList<>())
                        .add(error.getConstraintDescriptor().getAnnotation().annotationType().getSimpleName() + " constraint violation");
            });
            throw new ResponseValidationException(errorMessage.toString());
        }
    }


    @Pointcut("execution(* it.pagopa.pdv.user_registry.web.controller.*.*(..))")
    public void controllersPointcut() {
        // Do nothing because is a pointcut
    }

}
