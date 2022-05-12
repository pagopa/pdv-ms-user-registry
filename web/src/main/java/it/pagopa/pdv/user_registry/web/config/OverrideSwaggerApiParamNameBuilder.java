package it.pagopa.pdv.user_registry.web.config;

import io.swagger.annotations.ApiParam;
import org.springframework.core.annotation.Order;
import org.springframework.util.StringUtils;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.ExpandedParameterBuilderPlugin;
import springfox.documentation.spi.service.contexts.ParameterExpansionContext;
import springfox.documentation.swagger.common.SwaggerPluginSupport;

import java.util.Optional;

import static springfox.documentation.swagger.annotations.Annotations.findApiParamAnnotation;

//@Component
@Order(SwaggerPluginSupport.SWAGGER_PLUGIN_ORDER)
public class OverrideSwaggerApiParamNameBuilder implements ExpandedParameterBuilderPlugin {

    @Override
    public boolean supports(DocumentationType type) {
        return true;
    }

    @Override
    public void apply(ParameterExpansionContext context) {
        Optional<ApiParam> apiParamOptional = findApiParamAnnotation(context.getField().getRawMember());
        if (apiParamOptional.isPresent()) {
            fromApiParam(context, apiParamOptional.get());
        }
    }

    private void fromApiParam(ParameterExpansionContext context, ApiParam apiParam) {
        context.getParameterBuilder()
                .name(emptyToNull(apiParam.name()));
    }

    private String emptyToNull(String str) {
        return StringUtils.hasText(str) ? str : null;
    }
}
