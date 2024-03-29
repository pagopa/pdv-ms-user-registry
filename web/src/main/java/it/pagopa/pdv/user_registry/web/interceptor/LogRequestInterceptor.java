package it.pagopa.pdv.user_registry.web.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Collection;
import java.util.List;

@Slf4j
@Component
public class LogRequestInterceptor implements HandlerInterceptor {

    private static final Collection<String> URI_PREFIX_WHITELIST = List.of(
            "/swagger",
            "/v3/api-docs"
    );


    public LogRequestInterceptor() {
        log.trace("Initializing {}", LogRequestInterceptor.class.getSimpleName());
    }


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object controller) {
        boolean skipLog = URI_PREFIX_WHITELIST.stream()
                .anyMatch(request.getRequestURI()::startsWith);
        if (!skipLog) {
            log.info("Requested {} {}", request.getMethod(), request.getRequestURI());
            if (!request.getParameterMap().isEmpty()) {
                log.debug("Requested {} {}?{}", request.getMethod(), request.getRequestURI(), request.getQueryString());
            }
        }

        return true;
    }

}
