package it.pagopa.pdv.user_registry.web.config;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import springfox.documentation.service.ResolvedMethodParameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.ParameterBuilderPlugin;
import springfox.documentation.spi.service.contexts.ParameterContext;
import springfox.documentation.swagger.common.SwaggerPluginSupport;

//@Component
@Order(SwaggerPluginSupport.SWAGGER_PLUGIN_ORDER)
public class ParameterIndexOrderReader implements ParameterBuilderPlugin {

    private static final int PARAMETER_INITIAL_ORDER = Ordered.HIGHEST_PRECEDENCE;

    @Override
    public boolean supports(DocumentationType delimiter) {
        return true;
    }

    @Override
    public void apply(ParameterContext context) {
        ResolvedMethodParameter methodParameter = context.resolvedMethodParameter();
        context.parameterBuilder().order(PARAMETER_INITIAL_ORDER + methodParameter.getParameterIndex());
    }
}
